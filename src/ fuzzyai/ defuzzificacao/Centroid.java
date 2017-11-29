package fuzzyai.defuzzificacao;

import fuzzyai.inferencia.VariavelFuzzyficada;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public final class Centroid implements IDefuzzificacao{

    @Override
    public VariavelFuzzyficada defuzzificar(List<Point2D.Double> pontos) throws Exception {
		
        Double result1=0;
		Double result2 = 0;
		Double resultFinal = 0;
		
		foreach(Point2D.Double ponto : pontos){
			
			result += ponto.valor * ponto.valor2;
			result2 += ponto.valor2;
		}
		
		resultFinal = result1/result2;
		
		
		List<Point2D.Double> pontos = new ArrayList<>();
        pontos.add(new Point2D.Double(0, 0.66));
        pontos.add(new Point2D.Double(30, 0.66));
        pontos.add(new Point2D.Double(40, 0.33));
        pontos.add(new Point2D.Double(70, 0.33));
        pontos.add(new Point2D.Double(80, 0));
        // AQUI ITALO
        return null;
    }

}
