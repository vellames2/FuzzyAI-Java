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
        Imagem imagemSoma = this.somarImagens(resultadoRegras, modeloFuzzy);
        List<Point2D.Double> pontosUnicos = new ArrayList<>();
        for(Point2D.Double pontoSoma : imagemSoma.getPontos()) {
            boolean existePonto = false;
            for(Point2D.Double pontoUnico : pontosUnicos) {
                if(pontoUnico.getX() == pontoSoma.getX() && pontoUnico.getY() == pontoSoma.getY()) {
                    existePonto = true;
                    break;
                }
            }
            
            if(!existePonto) {
                pontosUnicos.add(pontoSoma);
            }
        }
        
        return new Imagem(pontosUnicos);
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
    
    /**
     * Realiza a soma das imagens enviadas pela inferencia
     * @param resultadoRegras Resultados entregados pela primeira parte da inferencia
     * @param modeloFuzzy Modelo fuzzy com o json preenchido
     * @return Retorna a soma das imagens enviadas pela primeira parte da inferencia
     */
    private Imagem somarImagens(List<ResultadoRegra> resultadoRegras, ModeloFuzzy modeloFuzzy) {
        List<Imagem> imagens = this.encontrarPontosImagem(resultadoRegras, modeloFuzzy);
        Imagem imagem = this.somarListaImagens(imagens);
        return imagem;
    }
    
    /**
     * Encontra os pontos restantes nas imagens geradas pelos resultados das regras
     * @param resultadoRegras Regras entregadas pela primeira parte da inferencia
     * @param modeloFuzzy Modelo fuzzy com o json preenchido
     * @return Retorna uma lista de imagens que representa todas as regras validas da inferencia
     * @throws IllegalArgumentException Caso alguma função de pertinencia não exista, uma exceção é lançada
     */
    private List<Imagem> encontrarPontosImagem(List<ResultadoRegra> resultadoRegras, ModeloFuzzy modeloFuzzy) throws IllegalArgumentException{
        // Lista de imagens que será retornada
        List<Imagem> imagens = new ArrayList<>();
        
        // Variavel de saida
        VariavelFuzzy variavelSaida = modeloFuzzy.getVariavelSaida();
        
        // Recupera a imagem gerada para cada resultado das regras da inferencia
        for(ResultadoRegra resultadoRegra : resultadoRegras) {
            
            // Recupera função de pertinencia referente ao resultado
            IFuncaoPertinencia funcaoPertinencia = VariavelFuzzyUtils.recuperarFuncaoPertinenciaPorNome(variavelSaida, resultadoRegra.getConsequente());
            
            // Recupera a imagem gerada
            Imagem imagem = new Imagem(funcaoPertinencia.pontosImagem(resultadoRegra.getValor()));
            
            // Adiciona imagem a lista
            imagens.add(imagem);
            
            // Saida no console
            System.out.println(imagem.getPontos().toString());
        }
        
        return imagens;
    }
    
    /**
     * Realiza a soma de uma lista de imagens
     * @param imagens Lista de imagem
     * @return Retorna a imagem somada
     */
    private Imagem somarListaImagens(List<Imagem> imagens){
        Imagem imagemSomada = null;

        int i = 0;
        for(Imagem x : imagens) {
            if(i == 0) {
                imagemSomada = x;
            } else {
                imagemSomada = somar(imagemSomada, x);               
            }    
            i++;
        }
        
        return imagemSomada;
    }
    
    /**
     * Realiza a soma de duas imagens
     * @param imagem1 Primeira imagem
     * @param imagem2 Segunda imagem
     * @return Retorna a soma das duas imagens
     */
    private Imagem somar(Imagem imagem1, Imagem imagem2){
        List<Point2D.Double> pontosSoma = new ArrayList<>();
        List<Point2D.Double> pontosImagemA = imagem1.getPontos();
        List<Point2D.Double> pontosImagemB = imagem2.getPontos();
        
        List<Point2D.Double> arrayMenorX = null;
        List<Point2D.Double> arrayMaiorX = null;
        
        // Verifica o menor X
        if(pontosImagemA.get(0).getX() < pontosImagemB.get(0).getX()) {
            pontosSoma.add(pontosImagemA.get(0));
            pontosImagemA.remove(0);
            
            arrayMenorX = pontosImagemA;
            arrayMaiorX = pontosImagemB;
        } else if(pontosImagemB.get(0).getX() < pontosImagemA.get(0).getX()) {
            pontosSoma.add(pontosImagemB.get(0));
            pontosImagemB.remove(0);
            
            arrayMenorX = pontosImagemB;
            arrayMaiorX = pontosImagemA;
        } else {
            if(pontosImagemA.get(0).getY() > pontosImagemB.get(0).getY()) {
                pontosSoma.add(pontosImagemA.get(0));
                
                arrayMenorX = pontosImagemA;
                arrayMaiorX = pontosImagemB;
            } else {
                pontosSoma.add(pontosImagemB.get(0));
                
                arrayMenorX = pontosImagemB;
                arrayMaiorX = pontosImagemA;
            }
            
            pontosImagemA.remove(0);
            pontosImagemB.remove(0);
        }
        
        for(int a = 0; a < arrayMenorX.size(); a++) {
            boolean continuarLaco = true;
            while(continuarLaco) {
                if (arrayMaiorX.isEmpty()) {
                    pontosSoma.add(arrayMenorX.get(a));
                    continuarLaco = false;
                } else if (arrayMenorX.get(a).getX() > arrayMaiorX.get(0).getX()) {
                    double x = arrayMaiorX.get(0).getX();
                    double y = (arrayMaiorX.get(0).getY() > arrayMenorX.get(a).getY() ? arrayMaiorX.get(0).getY() : arrayMenorX.get(a).getY());
                    pontosSoma.add(new Point2D.Double(x, y));
                    arrayMaiorX.remove(0);
                } else if (arrayMenorX.get(a).getX() < arrayMaiorX.get(0).getX()) {
                    double x = arrayMenorX.get(a).getX();
                    double y = (arrayMaiorX.get(0).getY() > arrayMenorX.get(a).getY() ? arrayMaiorX.get(0).getY() : arrayMenorX.get(a).getY());
                    pontosSoma.add(new Point2D.Double(x, y));
                    continuarLaco = false;
                } else {
                    if(arrayMenorX.get(a).getY() >= arrayMaiorX.get(0).getY()) {
                        double x = arrayMaiorX.get(0).getX();
                        double y = arrayMenorX.get(a).getY();
                        pontosSoma.add(new Point2D.Double(x, y));
                        arrayMaiorX.remove(0);
                    } else {
                        double x = arrayMaiorX.get(0).getX();
                        double y = arrayMaiorX.get(0).getY();
                        pontosSoma.add(new Point2D.Double(x, y));
                        continuarLaco = false;
                    }
                }
            }
        }
        
        for(Point2D.Double ponto : arrayMaiorX) {
            pontosSoma.add(ponto);
        }
        
        Imagem imagemSomada = new Imagem(pontosSoma);
        return imagemSomada;
    }
    
}
