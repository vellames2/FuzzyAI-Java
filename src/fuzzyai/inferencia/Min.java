package fuzzyai.inferencia;

import fuzzyai.abstracoes.IModoCalculo;

public class Min implements IModoCalculo {

    @Override
    public double calcular(double valor1, double valor2) {
        return valor1 < valor2 ? valor1 : valor2;
    }

}
