package fuzzyai.defuzzificacao;

import fuzzyai.inferencia.VariavelFuzzyficada;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public final class Centroid implements IDefuzzificacao{

    @Override
    public VariavelFuzzyficada defuzzificar() throws Exception {
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
