package fuzzyai.defuzzificacao;

import fuzzyai.ModeloFuzzy;
import fuzzyai.fuzzificacao.IFuzzificacao;
import fuzzyai.inferencia.Imagem;
import fuzzyai.inferencia.VariavelFuzzyficada;
import fuzzyai.utils.Reta;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que realiza a defuzzificacao usando o algoritimo de centroid
 */
public final class Centroid implements IDefuzzificacao{
    
    /**
     * Realiza a defuzzificacao
     * @param imagem Imagem gerada pela inferencia
     * @param modeloFuzzy Modelo fuzzy com todas as inforações carregadas
     * @param fuzzyFuzzificacao Objeto usado na fuzzificacao
     * @return Retorna a variavel de saida fuzzificada
     * @throws Exception Exceções serão lançadas para o chamador da funçao
     */
    @Override
    public VariavelFuzzyficada defuzzificar(Imagem imagem, ModeloFuzzy modeloFuzzy, IFuzzificacao fuzzyFuzzificacao) throws Exception {
        List<Point2D.Double> pontos = imagem.getPontos();
               
        List<Double> divisor = new ArrayList<>();
        List<Double> dividendo = new ArrayList<>();
        double precisaoCentroid = modeloFuzzy.getPrecisaoDefuzzificacao();
        
        for(int i = 0; i < pontos.size() - 1; i++) {
            Point2D.Double inicioReta = pontos.get(i);
            Point2D.Double fimReta = pontos.get(i + 1);
            Reta reta = new Reta(inicioReta, fimReta);
            for(double pontoAtual = inicioReta.getX(); pontoAtual <= fimReta.getX() - precisaoCentroid; pontoAtual+= precisaoCentroid) {
                double y = reta.obterPontoYPorX(pontoAtual);
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
        return fuzzyFuzzificacao.fuzzificarVariavel(modeloFuzzy.getVariavelSaida(), somaImagem);
    }
}
