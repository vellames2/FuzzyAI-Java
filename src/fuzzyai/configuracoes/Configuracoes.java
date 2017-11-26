package fuzzyai.configuracoes;

import java.util.List;

/**
 * Classe representando as configurações usadas na inferencia e o algoritimo
 * de deffuzyficação
 */
public final class Configuracoes {
    
    /**
     * Nome do algoritimo de deffuzyficação
     */
    private String deffuzyficacao;
    
    /**
     * Lista com as especificações de calculo para cada conector
     */
    private List<CalculoConector> calculoConectores;

    /**
     * Construtor da classe
     * @param deffuzyficacao Modo de deffuzyficaçao
     * @param calculoConectores Lista com os calculos a serem usados nos conectores
     * @throws java.lang.IllegalArgumentException Exceções de argumento invalido devem ser tratadas no
     * chamador do construtor
     */
    public Configuracoes(String deffuzyficacao, List<CalculoConector> calculoConectores) throws IllegalArgumentException{
        this.setDeffuzyficacao(deffuzyficacao);
        this.setCalculoConectores(calculoConectores);
    }
    
    /**
     * Recupera o modo de deffuzyficação
     * @return Retorna o modo de deffuzyficação
     */
    public String getDeffuzyficacao() {
        return deffuzyficacao;
    }
    
    /**
     * Seta o modo de deffuzyficação
     * Apenas "centroid" é aceito no momento
     * @param deffuzyficacao  Nome do modo de deffuzyficação
     */
    public void setDeffuzyficacao(String deffuzyficacao) {
        if(!deffuzyficacao.toLowerCase().equals("centroid")) {
            throw new IllegalArgumentException("Apenas o modo 'centroid' é aceito no momento");
        }
        this.deffuzyficacao = deffuzyficacao;
    }
    
    /**
     * Recupera a lista com o calculo referente aos conectores
     * @return Retorna uma lista com o calculo referente aos conectores
     */
    public List<CalculoConector> getCalculoConectores() {
        return calculoConectores;
    }
    
    /**
     * Seta lista com o calculo referente aos conectoresd
     * @param calculoConectores Lista com os calculos referentes aos conectores
     */
    public void setCalculoConectores(List<CalculoConector> calculoConectores) {
        this.calculoConectores = calculoConectores;
    }

}
