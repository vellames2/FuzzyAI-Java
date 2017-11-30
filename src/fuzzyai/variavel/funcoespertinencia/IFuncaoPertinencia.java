package fuzzyai.variavel.funcoespertinencia;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Abstração das funções de pertinencia de uma variavel fuzzy
 * Atualmente com suporte para triangulo e trapezio
 */
public interface IFuncaoPertinencia {
      
    /**
     * Recupera o nome da função de pertinencia
     * @return Retorna o nome da função de pertinencia
     */
    public String getNome();
    
    /**
     * Seta o nome da função de pertinencia
     * @param nome Nome da função de pertinencia
     * @throws IllegalArgumentException Uma exceção é lançada se o nome da função de pertinencia estiver vazia
     */
    public void setNome(String nome) throws IllegalArgumentException;
       
    
    /**
     * Toda função de pertinencia deve implementar um metodo retornando o valor do seu primeiro ponto
     * @return Retorna o valor do primeiro ponto da função de pertinencia
     */
    public double getPrimeiroPonto();
    
    /**
     * Toda função de pertinencia deve implementar um metodo retornando o valor do seu ultimo ponto
     * @return Retorna o valor do ultimo ponto da função de pertinencia
     */
    public double getUltimoPonto();
    
    /**
     * Toda função de pertinencia deve implementar um metodo que retorna o valor de pertinencia dado um ponto X
     * @param x Ponto X no gráfico da função de pertinencia
     * @return Retorna o valor de pertinencia dado um ponto x
     */
    public double getValorPertinencia(double x);
    
    public List<Point2D.Double> pontosY(double y);
}