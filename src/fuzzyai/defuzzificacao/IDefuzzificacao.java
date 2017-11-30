package fuzzyai.defuzzificacao;

import fuzzyai.ModeloFuzzy;
import fuzzyai.fuzzificacao.IFuzzificacao;
import fuzzyai.inferencia.Imagem;
import fuzzyai.inferencia.VariavelFuzzyficada;

/**
 * Interface que deve ser implementada por qualquer classe que queira realizar a defuzzificacao
 */
public interface IDefuzzificacao {
    /**
     * Realiza a defuzzificacao
     * @param imagem Imagem gerada pela inferencia
     * @param modeloFuzzy Modelo fuzzy com todas as inforações carregadas
     * @param fuzzyFuzzificacao Objeto usado na fuzzificacao
     * @return Retorna a variavel de saida fuzzificada
     * @throws Exception Exceções serão lançadas para o chamador da funçao
     */
    public VariavelFuzzyficada defuzzificar(Imagem imagem, ModeloFuzzy modeloFuzzy, IFuzzificacao fuzzyFuzzificacao) throws Exception;
}
