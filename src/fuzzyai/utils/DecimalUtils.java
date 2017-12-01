package fuzzyai.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Classe utilitaria para trabalhar com valores decimais
 */
public class DecimalUtils {
    
    /**
     * Arredonda um numero double
     * @param value Valor
     * @param places Casas decimais
     * @return Retorna o numero double arredondado
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
