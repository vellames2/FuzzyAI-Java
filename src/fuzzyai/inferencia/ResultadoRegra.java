package fuzzyai.inferencia;

/**
 * Classe responsavel por representar o resultado de uma regra da inferencia
 * @author Cassiano Vellames <c.vellames@outlook.com>
 */
public final class ResultadoRegra {
    /**
     * Nome do resultado
     */
    private String nomeResultado;
    
    /**
     * Valor do resultado
     */
    private double valorResultado;
    
    /**
     * Construtor da classe
     * @param nomeResultado Nome do Resultado
     * @param valorResultado  Valor do Resultado
     */
    public ResultadoRegra(String nomeResultado, double valorResultado) {
        this.nomeResultado = nomeResultado;
        this.valorResultado = valorResultado;
    }
    
    /**
     * 
     * @return 
     */
    public String getNomeResultado() {
        return nomeResultado;
    }

    public void setNomeResultado(String nomeResultado) {
        this.nomeResultado = nomeResultado;
    }

    public double getValorResultado() {
        return valorResultado;
    }

    public void setValorResultado(double valorResultado) {
        this.valorResultado = valorResultado;
    }
}
