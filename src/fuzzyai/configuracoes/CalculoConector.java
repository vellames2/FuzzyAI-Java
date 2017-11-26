package fuzzyai.configuracoes;

/**
 * Classe responsavel por especificar qual algoritimo deve ser usado para um
 * determinado conector durante a fase de inferencia
 */
public final class CalculoConector {
    /**
     * Tipo do conector (and, or ...)
     */
    private String conector;
    
    /**
     * Modulo de calculo para o conector (min, max, prod ...)
     */
    private String modo;

    /**
     * Construtor da classe
     * @param conector Nome do conector
     * @param modo Funcao usada para esse conector
     */
    public CalculoConector(String conector, String modo) {
        this.setConector(conector);
        this.setModo(modo);
    }

    /**
     * Recupera o nome do conector
     * @return Retorna o nome do conector
     */
    public String getConector() {
        return conector;
    }
    
    /**
     * Seta o nome do conector
     * @param conector Nome do Conector
     */
    public void setConector(String conector) {
        this.conector = conector;
    }
    
    /**
     * Recuperao modo de calculo para esse conector
     * @return Retorna o modo de calculo para esse conector
     */
    public String getModo() {
        return modo;
    }

    /**
     * Seta o modo de calculo para esse conector
     * @param modo Nome do modo de calculo
     */
    public void setModo(String modo) {
        this.modo = modo;
    }
}
