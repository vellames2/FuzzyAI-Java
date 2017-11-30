package fuzzyai.inferencia;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa os pontos de uma imagem
 */
public final class Imagem {
    
    /**
     * Pontos da Imagem
     */
    public List<Point2D.Double> pontos = new ArrayList<>();

    /**
     * Construtor com os pontos da imagem
     * @param pontos Pontos da imagem
     */
    public Imagem(List<Point2D.Double> pontos) {
        this.setPontos(pontos);
    }
    
    /**
     * Construtor sem atributos
     */
    public Imagem() {
    }
    
    /**
     * Recupera os pontos da imagem
     * @return Retorna os pontos da imagem
     */
    public List<Point2D.Double> getPontos() {
        return pontos;
    }
    
    /**
     * Seta os pontos da imagem
     * @param pontos Lista com os pontos da imagem
     */
    public void setPontos(List<Point2D.Double> pontos) {
        this.pontos = pontos;
    }
   
    /**
     * Adiciona um novo ponto a imagem
     * @param ponto Ponto a ser adicionado
     */
    public void addPonto(Point2D.Double ponto) {
        if(this.getPontos() == null) {
            this.setPontos(new ArrayList<>());
        }
        this.pontos.add(ponto);
    }
}
