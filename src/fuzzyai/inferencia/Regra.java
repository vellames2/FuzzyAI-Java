package fuzzyai.inferencia;

import java.util.List;

/**
 * Classe que representa uma regra da inferencia
 * @author Cassiano Vellames <c.vellames@outlook.com>
 */
public final class Regra {
    /**
     * Lista com todas as variaveis da regra
     */
    private List<String> variaveis;
    
    /**
     * Lista com os resultados referentes as variaveis da regra
     */
    private List<String> resultados;
    
    /**
     * Lista com os conectores da regra
     */
    private List<String> conectores;
    
    /**
     * Resultado caso a regra seja verdadeira
     */
    private String consequente;

    /**
     * Construtor da classe
     * @param variaveis Lista de variaveis
     * @param resultados Lista de resultado das variaveis
     * @param conectores Lista de conectores da regra
     * @param consequente Resultado caso a regra seja verdadeira
     */
    public Regra(List<String> variaveis, List<String> resultados, List<String> conectores, String consequente) {
        this.setVariaveis(variaveis);
        this.setResultados(resultados);
        this.setConectores(conectores);
        this.setConsequente(consequente);
    }
    
    public Regra(){};
    
    /**
     * Recupera a lista de varaiveis da regra
     * @return Retorna a lista de variaveis da regra
     */
    public List<String> getVariaveis() {
        return variaveis;
    }
    
    /**
     * Seta a lista de variaveis da regra
     * @param variaveis Lista de variaveis da regra
     */
    public void setVariaveis(List<String> variaveis) {
        this.variaveis = variaveis;
    }
    
    /**
     * Recupera a lista de resultado das variaveis da regra
     * @return Retorna a lista de resultado das varaiveis da regra
     */
    public List<String> getResultados() {
        return resultados;
    }
    
    /**
     * Seta a lista de resultado das variaveis da regra
     * @param resultados Lista de resultado das variaveis da regra
     */
    public void setResultados(List<String> resultados) {
        this.resultados = resultados;
    }
    
    /**
     * Recupera a lista de conectores da regra
     * @return Retorna a lista de conectores da regra
     */
    public List<String> getConectores() {
        return conectores;
    }

    /**
     * Seta a lista de conectores da regra
     * @param conectores  Lista de conectores da regra
     */
    public void setConectores(List<String> conectores) {
        this.conectores = conectores;
    }
    
    /**
     * Recupera o resultado da regra caso ela seja verdadeira
     * @return Retorna o resultado da regra
     */
    public String getConsequente() {
        return consequente;
    }
    
    /**
     * Seta o resultado da regra
     * @param consequente Resultado da regra
     */
    public void setConsequente(String consequente) {
        this.consequente = consequente;
    }
    
}
