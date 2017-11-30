package fuzzyai.utils;

import fuzzyai.variavel.VariavelFuzzy;
import fuzzyai.variavel.funcoespertinencia.IFuncaoPertinencia;

/**
 * Classe com utilidades para as variaveis fuzzy
 */
public class VariavelFuzzyUtils {
    
    /**
     * Retorna a função de pertinencia dado um nome
     * @param variavelFuzzy Variavel fuzzy
     * @param nomeFuncaoPertinencia Nome da função de pertinencia que se quer achar an variavel fuzzy
     * @return Retorna a função de pertinencia da variavel
     * @throws IllegalArgumentException Se a função de pertinencia não existe, uma exceção é retornada
     */
    public static IFuncaoPertinencia recuperarFuncaoPertinenciaPorNome(VariavelFuzzy variavelFuzzy, String nomeFuncaoPertinencia) throws IllegalArgumentException{
        for(IFuncaoPertinencia funcaoPertinencia : variavelFuzzy.getFuncoesPertinencia()) {
            if(funcaoPertinencia.getNome().toLowerCase().equals(nomeFuncaoPertinencia.toLowerCase())){
                return funcaoPertinencia;
            }
        }
        
        throw new IllegalArgumentException("A função de pertinencia " + nomeFuncaoPertinencia + " não existe");
    }
}
