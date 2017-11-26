package fuzzyai.inferencia;

/**
 * Classe responsavel por representar o resultado de uma regra da inferencia
 */
public final class ResultadoRegra {
    /**
     * Nome do consequente
     */
    private String consequente;
    
    /**
     * Valor do consequente
     */
    private double valor;
    
    /**
     * Construtor da classe
     * @param consequente Nome do consequente
     * @param valor Valor do consequente
     */
    public ResultadoRegra(String consequente, double valor) {
        this.consequente = consequente;
        this.valor = valor;
    }
    
    /**
     * Recupera o nome do consequente
     * @return Retorna o nome do consequente
     */
    public String getConsequente() {
        return consequente;
    }
    
    /**
     * Seta o nome do consequente
     * @param consequente Nome do consequente
     */
    public void setConsequente(String consequente) {
        this.consequente = consequente;
    }
    
    /**
     * Recupera o valor do consequente
     * @return Retorna o valor do consequente
     */
    public double getValor() {
        return valor;
    }

    /**
     * Seta o valor do consequente
     * @param valor Valor do consequente
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
    
}
