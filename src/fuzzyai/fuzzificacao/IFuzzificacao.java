package fuzzyai.fuzzificacao;

import fuzzyai.ModeloFuzzy;
import fuzzyai.inferencia.VariavelFuzzyficada;
import fuzzyai.variavel.VariavelFuzzy;
import java.util.List;

/**
 * Interface que deve ser implementada por qualquer classe que queira realizar a etapa de fuzzyficação
 */
public interface IFuzzificacao {
    /**
     * Deve ser implementado um metodo que realiza a fuzzyficação
     * @param valoresEntrada Valores de entrada para a fuzzyficação
     * @param modeloFuzzy Modelo fuzzy contendo todas as informações do JSON de configuração do modelo
     * @return Retorna uma lista de variaveis fuzzyficadas
     */
    public List<VariavelFuzzyficada> fuzzificar(List<Double> valoresEntrada, ModeloFuzzy modeloFuzzy);  
    
    /**
     * Realiza a fuzzyficação de uma variavel
     * @param variavelFuzzy Variavel a ser fuzzyficada
     * @param valorEntrada Valor de entrada
     * @return Retorna um objeto com a variavel fuzzyficada
     */
    public VariavelFuzzyficada fuzzificarVariavel(VariavelFuzzy variavelFuzzy, double valorEntrada);
}
