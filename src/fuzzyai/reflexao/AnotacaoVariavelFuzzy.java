package fuzzyai.reflexao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotações usadas nas variaveis fuzzy para definir informações adicionais dos
 * metodos
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AnotacaoVariavelFuzzy {
    /**
     * Define o numero do ponto da função de pertinencia ao qual o metodo pertence
     * @return Retorna o número do ponto da função de pertinencia ao qual o metodo pertence
     */
    int numeroPontoFuncaoPertinencia();
}
