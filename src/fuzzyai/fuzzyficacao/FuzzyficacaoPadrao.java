package fuzzyai.fuzzyficacao;

import fuzzyai.ModeloFuzzy;
import fuzzyai.VariavelFuzzy;
import fuzzyai.abstracoes.AFuncaoPertinencia;
import fuzzyai.abstracoes.AFuzzyficacao;
import fuzzyai.utils.VariavelFuzzyficada;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class FuzzyficacaoPadrao extends AFuzzyficacao {

    public FuzzyficacaoPadrao(ModeloFuzzy modeloFuzzy) {
        super(modeloFuzzy);
    }
    
    /**
     * Realiza a fuzzyficação
     * @param modeloFuzzy Objeto contendo os dados do modelo fuzzy
     * @param valoresEntrada ArrayList com os valores de entrada
     * @return Retorna um array list de variaveis fuzzyficadas
     */
    @Override
    public List<VariavelFuzzyficada> fuzzyficar(List<Double> valoresEntrada) {
        List<VariavelFuzzyficada> variaveisFuzzyficadas = new ArrayList<>();
        /*
            A ordem de entrada nem sempre será igual a ordem que as variaveis fuzzy estão inseridas 
            no arraylist this.variaveisFuzzy. Por isso é necessário verificar a ordem de entrada e encontrar
            o indice da variavel fuzzy equivalente no arraylist
        */
        for(int i = 0; i < valoresEntrada.size(); i++) {
            // Nome da variavel de entrada
            String nomeValorEntrada = this.getModeloFuzzy().getOrdemEntrada().get(i);
            
            // Valor de Entrada
            double valorEntrada = valoresEntrada.get(i);
            
            // Verifica o indice da variavel fuzzy a ser fuzzyficada a partir do nome de entrada
            for(int j = 0; j < this.getModeloFuzzy().getVariaveisFuzzy().size(); j++) {
                VariavelFuzzy variavelFuzzy = this.getModeloFuzzy().getVariaveisFuzzy().get(j);
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
                resultado.put(funcaoPertinencia.getNome(), funcaoPertinencia.getValorPertinencia(valorEntrada));
            } else {
                resultado.put(funcaoPertinencia.getNome(), 0d);
            }
        }
        
        variavelFuzzyficada.setResultado(resultado);
        return variavelFuzzyficada;
    }

}
