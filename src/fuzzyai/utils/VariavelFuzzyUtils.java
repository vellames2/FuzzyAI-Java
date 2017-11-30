package fuzzyai.utils;

import fuzzyai.variavel.VariavelFuzzy;
import fuzzyai.variavel.funcoespertinencia.IFuncaoPertinencia;

public class VariavelFuzzyUtils {
    public static IFuncaoPertinencia recuperarFuncaoPertinenciaPorNome(VariavelFuzzy variavelFuzzy, String nomeFuncaoPertinencia) throws IllegalArgumentException{
        for(IFuncaoPertinencia funcaoPertinencia : variavelFuzzy.getFuncoesPertinencia()) {
            if(funcaoPertinencia.getNome().toLowerCase().equals(nomeFuncaoPertinencia.toLowerCase())){
                return funcaoPertinencia;
            }
        }
        
        throw new IllegalArgumentException("A função de pertinencia " + nomeFuncaoPertinencia + " não existe");
    }
}
