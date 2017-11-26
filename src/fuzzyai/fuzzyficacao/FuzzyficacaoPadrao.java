package fuzzyai.fuzzyficacao;

import fuzzyai.ModeloFuzzy;
import fuzzyai.variavel.VariavelFuzzy;
import fuzzyai.variavel.AFuncaoPertinencia;
import fuzzyai.inferencia.VariavelFuzzyficada;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe responsavel por realizar a etapa de fuzzyficação
 */
public final class FuzzyficacaoPadrao implements IFuzzyficacao {

    /**
     * Realiza a etapa de fuzzyficação da logica fuzzy
     * @param valoresEntrada Valores de entrada para a fuzzyficação
     * @param modeloFuzzy Modelo fuzzy contendo todas as informações do JSON de configuração do modelo
     * @return Retorna uma lista de variaveis fuzzyficadas
     */
    @Override
    public List<VariavelFuzzyficada> fuzzyficar(List<Double> valoresEntrada, ModeloFuzzy modeloFuzzy) {
        List<VariavelFuzzyficada> variaveisFuzzyficadas = new ArrayList<>();
        /*
            A ordem de entrada nem sempre será igual a ordem que as variaveis fuzzy estão inseridas 
            no arraylist this.variaveisFuzzy. Por isso é necessário verificar a ordem de entrada e encontrar
            o indice da variavel fuzzy equivalente no arraylist
        */
        for(int i = 0; i < valoresEntrada.size(); i++) {
            // Nome da variavel de entrada
            String nomeValorEntrada = modeloFuzzy.getOrdemEntrada().get(i);
            
            // Valor de Entrada
            double valorEntrada = valoresEntrada.get(i);
            
            // Verifica o indice da variavel fuzzy a ser fuzzyficada a partir do nome de entrada
            for(int j = 0; j < modeloFuzzy.getVariaveisFuzzy().size(); j++) {
                VariavelFuzzy variavelFuzzy = modeloFuzzy.getVariaveisFuzzy().get(j);
                if(variavelFuzzy.getNome().equals(nomeValorEntrada)) {
                    variaveisFuzzyficadas.add(this.fuzzyficarVariavel(variavelFuzzy, valorEntrada));
                }
            }  
        }
        
        return variaveisFuzzyficadas;
    }
    
    /**
     * Realiza a fuzzyficação de uma variavel
     * @param variavelFuzzy Variavel a ser fuzzyficada
     * @param valorEntrada Valor de entrada
     * @return Retorna um objeto com a variavel fuzzyficada
     */
    protected VariavelFuzzyficada fuzzyficarVariavel(VariavelFuzzy variavelFuzzy, double valorEntrada) {
        VariavelFuzzyficada variavelFuzzyficada = new VariavelFuzzyficada(variavelFuzzy, valorEntrada);
        HashMap<String, Double> resultado = new HashMap<>();
        
        // Varre todas as funções de pertinencia pra verificar em quais ela toca
        for(AFuncaoPertinencia funcaoPertinencia : variavelFuzzy.getFuncoesPertinencia()) {
            double primeiroPonto = funcaoPertinencia.getPrimeiroPonto();
            double ultimoPonto = funcaoPertinencia.getUltimoPonto();
            
            // Verifica se valor toca na atual iteração da função de pertinencia
            if((primeiroPonto != -1 && valorEntrada >= primeiroPonto) || (ultimoPonto != -1 && valorEntrada <= ultimoPonto)) {
                resultado.put(funcaoPertinencia.getNome().toLowerCase(), funcaoPertinencia.getValorPertinencia(valorEntrada));
            } else {
                resultado.put(funcaoPertinencia.getNome().toLowerCase(), 0d);
            }
        }
        
        variavelFuzzyficada.setResultado(resultado);
        return variavelFuzzyficada;
    }
}
