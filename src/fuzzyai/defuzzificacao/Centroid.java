package fuzzyai.defuzzificacao;

import fuzzyai.ModeloFuzzy;
import fuzzyai.fuzzificacao.IFuzzificacao;
import fuzzyai.inferencia.VariavelFuzzyficada;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que realiza a defuzzificacao usando o algoritimo de centroid
 */
public final class Centroid implements IDefuzzificacao{
    
    /**
     * Realiza a defuzzificacao
     * @param modeloFuzzy Modelo fuzzy com todas as inforações carregadas
     * @param fuzzyFuzzificacao Objeto usado na fuzzificacao
     * @return Retorna a variavel de saida fuzzificada
     * @throws Exception Exceções serão lançadas para o chamador da funçao
     */
    @Override
    public VariavelFuzzyficada defuzzificar(ModeloFuzzy modeloFuzzy, IFuzzificacao fuzzyFuzzificacao) throws Exception {
        List<Point2D.Double> pontos = new ArrayList<>();
        pontos.add(new Point2D.Double(0, 0.66));
        pontos.add(new Point2D.Double(30, 0.66));
        pontos.add(new Point2D.Double(50, 0.33));
        pontos.add(new Point2D.Double(70, 0.33));
        pontos.add(new Point2D.Double(90, 0));
               
        List<Double> divisor = new ArrayList<>();
        List<Double> dividendo = new ArrayList<>();
        double precisaoCentroid = modeloFuzzy.getPrecisaoDefuzzificacao();
        
        for(int i = 0; i < pontos.size() - 1; i++) {
            Point2D.Double inicioReta = pontos.get(i);
            Point2D.Double fimReta = pontos.get(i + 1);
            
            // Recupera o coeficiente angular da reta a qual o ponto pertence
            double coeficienteAngular = this.calcularCoeficienteAngularReta(inicioReta, fimReta);
            
            for(double pontoAtual = inicioReta.getX(); pontoAtual <= fimReta.getX() - precisaoCentroid; pontoAtual+= precisaoCentroid) {
                double y = this.obterPontoYPorX(coeficienteAngular, inicioReta, pontoAtual);
                divisor.add(pontoAtual * y);
                dividendo.add(y);
            } 
        }
        
        // Realiza a divisão dos valores encontrados para finalizar o calculo da centroid
        double somaDivisor = divisor.stream().mapToDouble(Double::doubleValue).sum();
        double somaDividendo = dividendo.stream().mapToDouble(Double::doubleValue).sum();
        double somaImagem = somaDivisor / somaDividendo;
        
        // Exibicação da soma para debug
        System.out.println("---------");
        System.out.println("Soma imagem:");
        System.out.println(somaImagem);
        
        // Retorna a fuzzificacao da variavel de saida
        return fuzzyFuzzificacao.fuzzificarVariavel(modeloFuzzy.getVariavelInferencia(), somaImagem);
    }
    
    /**
     * Calcula o coeficiente angular de uma reta
     * @param inicioReta Ponto de inicio da reta
     * @param fimReta Ponto final da reta
     * @return Retorna o coeficiente angular da reta
     */
    private double calcularCoeficienteAngularReta(Point2D.Double inicioReta, Point2D.Double fimReta) {
        return (fimReta.getY() - inicioReta.getY()) / (fimReta.getX() - inicioReta.getX());
    }
    
    /**
     * Aplica a equação da reta para encontrar o ponto Y dado o ponto X
     * @param coeficienteAngular
     * @param inicioReta
     * @param x
     * @return 
     */
    private double obterPontoYPorX(double coeficienteAngular, Point2D.Double inicioReta, double x) {
        return ((coeficienteAngular * x) - (coeficienteAngular * inicioReta.getX())) + inicioReta.getY();
    }
}
