package fuzzyai.defuzzificacao;

import fuzzyai.ModeloFuzzy;
import fuzzyai.fuzzificacao.IFuzzificacao;
import fuzzyai.inferencia.VariavelFuzzyficada;

/**
 * Interface que deve ser implementada por qualquer classe que queira realizar a defuzzificacao
 */
public interface IDefuzzificacao {
    /**
     * Realiza a defuzzificacao
     * @param modeloFuzzy Modelo fuzzy com todas as inforações carregadas
     * @param fuzzyFuzzificacao Objeto usado na fuzzificacao
     * @return Retorna a variavel de saida fuzzificada
     * @throws Exception Exceções serão lançadas para o chamador da funçao
     */
    public VariavelFuzzyficada defuzzificar(ModeloFuzzy modeloFuzzy, IFuzzificacao fuzzyFuzzificacao) throws Exception;
}
