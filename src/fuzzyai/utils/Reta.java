package fuzzyai.utils;

import java.awt.geom.Point2D;

/**
 * Classe que representa uma reta
 */
public final class Reta {
    
    /**
     * Inicio da reta
     */
    private Point2D.Double inicioReta;
    
    /**
     * Fim da reta
     */
    private Point2D.Double fimReta;
    
    /**
     * Construtor da classe
     * @param inicioReta Inicio da reta
     * @param fimReta Fim da reta
     */
    public Reta(Point2D.Double inicioReta, Point2D.Double fimReta) {
        this.inicioReta = inicioReta;
        this.fimReta = fimReta;
    }
    
    /**
     * Recupera o inicio da reta
     * @return Retorna o inicio da reta
     */
    public Point2D.Double getInicioReta() {
        return inicioReta;
    }
    
    /**
     * Seta o inicio da reta
     * @param inicioReta Inicio da reta
     */
    public void setInicioReta(Point2D.Double inicioReta) {
        this.inicioReta = inicioReta;
    }
    
    /**
     * Recupera o fim da reta
     * @return Retorna o fim da reta
     */
    public Point2D.Double getFimReta() {
        return fimReta;
    }
    
    /**
     * Seta o fim da reta
     * @param fimReta Fim da reta
     */
    public void setFimReta(Point2D.Double fimReta) {
        this.fimReta = fimReta;
    }
    
    /**
     * Calcula o coeficiente angular de uma reta
     * @return Retorna o coeficiente angular da reta
     */
    public double getCoeficienteAngular() {
        return (this.getFimReta().getY() - this.getInicioReta().getY()) / (this.getFimReta().getX() - this.getInicioReta().getX());
    }
    
    /**
     * Aplica a equação da reta para encontrar o ponto Y dado o ponto X
     * @param x Ponto X que se quer saber o valor de Y
     * @return Retorna o ponto Y da reta
     */
    public double obterPontoYPorX(double x) {
        return ((this.getCoeficienteAngular() * x) - (this.getCoeficienteAngular() * this.getInicioReta().getX())) + this.getInicioReta().getY();
    }
    
    /**
     * Aplica a equação da reta para encontrar o ponto X dado o ponto Y
     * @param y Ponto Y que se quer saber o valor de X
     * @return Retorna o ponto X da reta
     */
    public double obterPontoXPorY(double y) {
        return (this.getCoeficienteAngular() * this.getInicioReta().getX() + y - this.getInicioReta().getY()) / (this.getCoeficienteAngular());
    }
}
