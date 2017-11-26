package fuzzyai.inferencia;

import fuzzyai.variavel.VariavelFuzzy;
import java.util.HashMap;

/**
 * Representa uma variavel após a sua fuzzyficação
 */
public final class VariavelFuzzyficada {
    /**
     * Apontamento para variavel original
     */
    private VariavelFuzzy variavelFuzzy;
    
    /**
     * Valor que foi usado na entrada para a fuzzyficação
     */
    private double entrada;
    
    /**
     * Resultados da fuzzyficação
     */
    private HashMap<String, Double> resultado;
    
    /**
     * Construtor da classe
     * @param variavelFuzzy Variavel que foi fuzzyficada
     * @param entrada Valor usado para a fuzzyficação
     */
    public VariavelFuzzyficada(VariavelFuzzy variavelFuzzy, double entrada) {
        this.setVariavelFuzzy(variavelFuzzy);
        this.setEntrada(entrada);
        this.resultado = new HashMap<>();
    }
    
    /**
     * Recupera a variavel original
     * @return Retorna a variavel original
     */
    public VariavelFuzzy getVariavelFuzzy() {
        return variavelFuzzy;
    }
    
    /**
     * Seta a variavel original
     * @param variavelFuzzy  Variavel original
     */
    public void setVariavelFuzzy(VariavelFuzzy variavelFuzzy) {
        this.variavelFuzzy = variavelFuzzy;
    }
    
    /**
     * Recupera o valor de entrada da fuzzyficação
     * @return Retorna o valor de entrada da fuzzyficação
     */
    public double getEntrada() {
        return entrada;
    }
    
    /**
     * Seta o valor de entrada da fuzzyficação
     * @param entrada Valor de entrada da fuzzyficação
     */
    public void setEntrada(double entrada) {
        this.entrada = entrada;
    }
    
    /**
     * Recupera os resultados da fuzzyficação da variavel
     * @return Retorna os resultados da fuzzyficação da variavel
     */
    public HashMap<String, Double> getResultado() {
        return resultado;
    }
    
    /**
     * Seta os resultados da fuzzyficação da variavel
     * @param resultado Resultados da fuzzyficação da variavel
     */
    public void setResultado(HashMap<String, Double> resultado) {
        this.resultado = resultado;
    }

}
