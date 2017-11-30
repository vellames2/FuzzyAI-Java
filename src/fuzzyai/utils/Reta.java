package fuzzyai.utils;

import java.awt.geom.Point2D;

public final class Reta {
    private Point2D.Double inicioReta;
    private Point2D.Double fimReta;

    public Reta(Point2D.Double inicioReta, Point2D.Double fimReta) {
        this.inicioReta = inicioReta;
        this.fimReta = fimReta;
    }
    
    public Point2D.Double getInicioReta() {
        return inicioReta;
    }

    public void setInicioReta(Point2D.Double inicioReta) {
        this.inicioReta = inicioReta;
    }

    public Point2D.Double getFimReta() {
        return fimReta;
    }

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
