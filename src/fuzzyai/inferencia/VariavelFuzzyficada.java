package fuzzyai.inferencia;

import fuzzyai.VariavelFuzzy;
import java.util.HashMap;

public final class VariavelFuzzyficada {
    private VariavelFuzzy variavelFuzzy;
    private double entrada;
    private HashMap<String, Double> resultado;
    
    public VariavelFuzzyficada(VariavelFuzzy variavelFuzzy, double entrada) {
        this.setVariavelFuzzy(variavelFuzzy);
        this.setEntrada(entrada);
        this.resultado = new HashMap<>();
    }

    public VariavelFuzzy getVariavelFuzzy() {
        return variavelFuzzy;
    }

    public void setVariavelFuzzy(VariavelFuzzy variavelFuzzy) {
        this.variavelFuzzy = variavelFuzzy;
    }
    
    public double getEntrada() {
        return entrada;
    }

    public void setEntrada(double entrada) {
        this.entrada = entrada;
    }

    public HashMap<String, Double> getResultado() {
        return resultado;
    }

    public void setResultado(HashMap<String, Double> resultado) {
        this.resultado = resultado;
    }
    
    
}
