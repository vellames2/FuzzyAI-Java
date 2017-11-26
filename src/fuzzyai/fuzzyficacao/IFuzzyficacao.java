package fuzzyai.fuzzyficacao;

import fuzzyai.ModeloFuzzy;
import fuzzyai.inferencia.VariavelFuzzyficada;
import java.util.List;

/**
 * Interface que deve ser implementada por qualquer classe que queira realizar a etapa de fuzzyficação
 */
public interface IFuzzyficacao {
    /**
     * Deve ser implementado um metodo que realiza a fuzzyficação
     * @param valoresEntrada Valores de entrada para a fuzzyficação
     * @param modeloFuzzy Modelo fuzzy contendo todas as informações do JSON de configuração do modelo
     * @return Retorna uma lista de variaveis fuzzyficadas
     */
    public List<VariavelFuzzyficada> fuzzyficar(List<Double> valoresEntrada, ModeloFuzzy modeloFuzzy);  
}
