    package fuzzyai.inferencia;

import fuzzyai.ModeloFuzzy;
import fuzzyai.configuracoes.CalculoConector;
import fuzzyai.utils.VariavelFuzzyUtils;
import fuzzyai.variavel.VariavelFuzzy;
import fuzzyai.variavel.funcoespertinencia.IFuncaoPertinencia;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe responsavel pela etapa de inferencia da logica fuzzy
 */
public class InferenciaPadrao implements IInferencia{
    /**
     * Realiza a etapa de inferencia da logica fuzzy
     * @param varaiveisFuzzyficadas Lista com as variaveis fuzzyficadas
     * @param modeloFuzzy Modelo fuzzy com todas as informações contidas no JSON de configuração
     * @throws Exception Toda exceção deve ser tratada pelo chamador da função
     */
    @Override
    public Imagem inferir(List<VariavelFuzzyficada> varaiveisFuzzyficadas, ModeloFuzzy modeloFuzzy) throws Exception {
        List<ResultadoRegra> resultadoRegras =  this.aplicarRegras(varaiveisFuzzyficadas, modeloFuzzy);
        return this.somarImagens(resultadoRegras, modeloFuzzy);
    }
    
    /**
     * Realiza a etapa de aplicação das regras da inferencia
     * @param variaveisFuzzyficadas Lista de variaveis fuzzyficadas
     * @param modeloFuzzy Modelo fuzzy com todas as informações contidas no JSON de configuração
     * @return Retorna uma lista com o resultado das regras
     * @throws Exception Diversas exceções podem ser lançadas durante essa etapa
     */
    private List<ResultadoRegra> aplicarRegras(List<VariavelFuzzyficada> variaveisFuzzyficadas, ModeloFuzzy modeloFuzzy) throws Exception{
        List<Regra> regrasValidas = this.extrairRegrasValidas(variaveisFuzzyficadas, modeloFuzzy);
        
        // Com as regras validas é necessário aplicar as regras
        List<ResultadoRegra> resultadoRegras = new ArrayList<>();
        for(Regra regra: regrasValidas) {
            List<Double> resultados = new ArrayList<>();
            List<String> conectores = regra.getConectores();
            String consequente = regra.getConsequente();

            // Recupera todos os resultados
            for(int i = 0; i < regra.getVariaveis().size(); i++) {
                String nomeVariavel = regra.getVariaveis().get(i).toLowerCase();
                String nomeResultado = regra.getResultados().get(i).toLowerCase();
                
                for(VariavelFuzzyficada variavelFuzzyficada : variaveisFuzzyficadas) {
                    String nomeVariavelFuzzy = variavelFuzzyficada.getVariavelFuzzy().getNome().toLowerCase();
                    if(nomeVariavelFuzzy.equals(nomeVariavel)) {
                        HashMap<String, Double> resultadoFuzzy = variavelFuzzyficada.getResultado();
                        
                        Object[] chaves = resultadoFuzzy.keySet().toArray();
                        for(int x = 0; x < resultadoFuzzy.size(); x++) {
                            if(chaves[x].toString().toLowerCase().equals(nomeResultado)) {
                               resultados.add(resultadoFuzzy.get(chaves[x]));
                            }
                        }
                    }
                }
            }
            
            // Com os resultados, realiza os calculos com base nos conectores
            double atual = 0;
            for(int i = 0; i < conectores.size(); i++) {
                
                // Verifica a função a ser usada
                List<CalculoConector> calculoConectores = modeloFuzzy.getConfiguracoes().getCalculoConectores();
                String modoCalculoConfig = "";
                for(CalculoConector calculoConector : calculoConectores) {
                    if(calculoConector.getConector().toLowerCase().equals(conectores.get(i).toLowerCase())) {
                        modoCalculoConfig = calculoConector.getModo();
                        break;
                    }
                }
                
                IModoCalculo modoCalculo = (IModoCalculo) Class.forName("fuzzyai.inferencia." + modoCalculoConfig).newInstance();
                double valor;
                if(i == 0) {
                    atual = resultados.get(0);
                    valor = resultados.get(1);
                } else {
                    valor = resultados.get(i);
                }
                
                atual = modoCalculo.calcular(atual, valor);
            }
            resultadoRegras.add(new ResultadoRegra(consequente, atual));
        }
        System.out.println(resultadoRegras.toString());
        for(ResultadoRegra regrat : resultadoRegras) {
            System.out.println(regrat.getConsequente() + " - " + regrat.getValor());
        }
        return resultadoRegras;
    }
    
    /**
     * Função responsavel por eliminar as regras invalidas
     * @param variaveisFuzzyficadas Lista de variaveis fuzzyficadas
     * @param modeloFuzzy Modelo fuzzy com todas as informações contidas no JSON de configuração
     * @return Retorna apenas as regras validas para terem seus valores extraidos
     */
    private List<Regra> extrairRegrasValidas(List<VariavelFuzzyficada> variaveisFuzzyficadas, ModeloFuzzy modeloFuzzy) {
        List<Regra> regras = modeloFuzzy.getRegras();
        List<Regra> regrasValidas = new ArrayList<>();
        for(Regra regra: regras) {
            
            // Valida a estrutura das regras
            if(regra.getVariaveis().size() < 2) {
                throw new IllegalArgumentException("É preciso que exista pelo menos duas variaveis na regra");
            }
            
            if(regra.getVariaveis().size() != regra.getResultados().size()){
                throw new IllegalArgumentException("O numero de variaveis não condiz com o número de resultados");
            }
            
            if(regra.getConectores().size() + 1 != regra.getVariaveis().size()) {
                throw new IllegalArgumentException("O numero de consequentes precisa ser o número de variaveis menos um");
            }
            
            boolean regraValida = true;
            // Verifica a validade das regras:
            // Varre todas as variaveis da regra
            for(int i = 0; i < regra.getVariaveis().size(); i++){
                String nomeVariavelRegra = regra.getVariaveis().get(i).toLowerCase();
                String resultadoRegra = regra.getResultados().get(i).toLowerCase();
                
                // Encontra a variavel fuzzyficada referente a variavel da regra
                for(VariavelFuzzyficada variavelFuzzyficada: variaveisFuzzyficadas) {
                    String nomeVariavelFuzzy = variavelFuzzyficada.getVariavelFuzzy().getNome().toLowerCase();
                    if(nomeVariavelRegra.equals(nomeVariavelFuzzy)) {
                        HashMap<String, Double> resultadoFuzzy = variavelFuzzyficada.getResultado();
                        
                        Object[] chaves = resultadoFuzzy.keySet().toArray();
                        for(int x = 0; x < resultadoFuzzy.size(); x++) {
                            if(chaves[x].toString().toLowerCase().equals(resultadoRegra) && resultadoFuzzy.get(chaves[x]) == 0) {
                                regraValida = false;
                                break;
                            }
                        }
                    }
                }
            }
            
            if(regraValida) {
                regrasValidas.add(regra);
            }
        }
        
        return regrasValidas;
    }
    
    private Imagem somarImagens(List<ResultadoRegra> resultadoRegras, ModeloFuzzy modeloFuzzy) {
        List<Imagem> imagens = this.encontrarPontosImagem(resultadoRegras, modeloFuzzy);
        Imagem imagem = this.somarImagens(imagens);
        return imagem;
    }
    
    private List<Imagem> encontrarPontosImagem(List<ResultadoRegra> resultadoRegras, ModeloFuzzy modeloFuzzy) throws IllegalArgumentException{
        // Lista de imagens que será retornada
        List<Imagem> imagens = new ArrayList<>();
        
        // Variavel de saida
        VariavelFuzzy variavelSaida = modeloFuzzy.getVariavelInferencia();
        
        // Recupera a imagem gerada para cada resultado das regras da inferencia
        for(ResultadoRegra resultadoRegra : resultadoRegras) {
            
            // Recupera função de pertinencia referente ao resultado
            IFuncaoPertinencia funcaoPertinencia = VariavelFuzzyUtils.recuperarFuncaoPertinenciaPorNome(variavelSaida, resultadoRegra.getConsequente());
            
            // Recupera a imagem gerada
            Imagem imagem = new Imagem(funcaoPertinencia.pontosY(resultadoRegra.getValor()));
            
            // Adiciona imagem a lista
            imagens.add(imagem);
            
            // Saida no console
            System.out.println(imagem.getPontos().toString());
        }
        
        return imagens;
    }
    
    private Imagem somarImagens(List<Imagem> imagens){
        Imagem imagemSomada = null;
        int i = 0;
        for(Imagem x : imagens){
            if(i==0){
                imagemSomada = x;
            }else{
                imagemSomada = somar(imagemSomada, x);               
            }    
            i++;
        }
        
        return imagemSomada;
    }
    
    private Imagem somar(Imagem imagem1, Imagem imagem2){
        Imagem imagemFinal = new Imagem();
  
        List<Point2D.Double> pontos1 = imagem1.getPontos();
        List<Point2D.Double> pontos2 = imagem2.getPontos();

        Point2D.Double menorX = new Point2D.Double();
        
        //ACHA O MENOR X NA PRIMEIRA IMAGEM
        for(int i = 0; i < pontos1.size(); i++){
            if(i==0){
                menorX = pontos1.get(i);
            }else{
                if(menorX.getX() > pontos1.get(i).getX()){
                    menorX = pontos1.get(i);
                }
            }            
        }
        
        //ACHA O MENOR X NA SEGUNDA IMAGEM
        for(int i = 0; i < pontos2.size(); i++){
                if(menorX.getX() > pontos2.get(i).getX()){
                    menorX = pontos2.get(i);
            }            
        }
        
        imagemFinal.addPonto(menorX);
        
        //ENCONTRA OUTRO PONTO COM MESMA ALTURA NA PRIMEIRA IMAGEM
         Point2D.Double primeiroY = menorX;
         Point2D.Double segundoPonto = new Point2D.Double();
        for(int i = 0; i < pontos1.size(); i++){
            if(primeiroY.getY() < pontos1.get(i).getY() && menorX.getX() < pontos1.get(i).getX()){
                segundoPonto = pontos1.get(i);
            }
        }
        //ENCONTRA OUTRO PONTO COM MESMA ALTURA NA SEGUNDA IMAGEM
        for(int i = 0; i < pontos2.size(); i++){
            if(primeiroY.getY() <= pontos2.get(i).getY() && menorX.getX() < pontos2.get(i).getX()){
                segundoPonto = pontos2.get(i);
            }
        }
        
        imagemFinal.addPonto(segundoPonto);
        Point2D.Double maiorY = new Point2D.Double();
        //ENCONTRA PROXIMA MAIOR ALTURA NA PRIMEIRA IMAGEM
        for(int i = 0; i < pontos1.size(); i++){            
            if(maiorY.getY() < pontos1.get(i).getY() && segundoPonto.getX() < pontos1.get(i).getX()){
                maiorY = pontos1.get(i);
            }
        }
        //ENCONTRA PROXIMA MAIOR ALTURA NA SEGUNDA IMAGEM
        for(int i = 0; i < pontos2.size(); i++){
            if(maiorY.getY() < pontos2.get(i).getY() && segundoPonto.getX() < pontos2.get(i).getX()){
                maiorY = pontos2.get(i);
            }
        }
        imagemFinal.addPonto(maiorY);
        
        Point2D.Double maiorX = new Point2D.Double();
        //ENCONTRA PROXIMO PONTO COM MAIOR X E Y NA PRIMEIRA IMAGEM
        for(int i = 0; i < pontos1.size(); i++){
            if(maiorY.getX() < pontos1.get(i).getX() || (maiorY.getX() == pontos1.get(i).getX() && maiorY.getY() > pontos1.get(i).getY() )){
                maiorX = pontos1.get(i);
            }
        }
        //ENCONTRA PROXIMO PONTO COM MAIOR X E Y NA SEGUNDA IMAGEM
        for(int i = 0; i < pontos2.size(); i++){
            if(maiorY.getX() < pontos2.get(i).getX() || (maiorY.getX() == pontos2.get(i).getX() && maiorY.getY() > pontos2.get(i).getY() )){
                maiorX = pontos2.get(i);
            }
        }
        imagemFinal.addPonto(maiorX);
        System.out.println("Imagem de entrada 1: "+pontos1);
        System.out.println("Imagem de entrada 2: "+pontos2);
        System.out.println("Imagem de saida: "+imagemFinal.pontos);
        return imagemFinal;
    }
    
}
