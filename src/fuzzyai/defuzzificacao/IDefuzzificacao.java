package fuzzyai.defuzzificacao;

import fuzzyai.inferencia.VariavelFuzzyficada;

public interface IDefuzzificacao {
    public VariavelFuzzyficada defuzzificar() throws Exception;
}
