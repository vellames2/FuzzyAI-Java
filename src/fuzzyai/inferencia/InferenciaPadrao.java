    package fuzzyai.inferencia;

import fuzzyai.ModeloFuzzy;
import fuzzyai.configuracoes.CalculoConector;
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
    public void inferir(List<VariavelFuzzyficada> varaiveisFuzzyficadas, ModeloFuzzy modeloFuzzy) throws Exception {
        List<ResultadoRegra> resultadoRegras =  this.aplicarRegras(varaiveisFuzzyficadas, modeloFuzzy);
        this.somarImagens(resultadoRegras, modeloFuzzy);
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
    
    private void somarImagens(List<ResultadoRegra> resultadoRegras, ModeloFuzzy modeloFuzzy) {
        // AQUI LEO
    }
}
