package fuzzyai.inferencia;

/**
 * Classe que implementa o modo de calculo Max (Maximo)
 */
public class Min implements IModoCalculo {
    
    /**
     * Recupera o maior valor entre os valores passados
     * @param valor1 Primeiro valor da comparação
     * @param valor2 Segundo valor da comparação
     * @return Retorna o maior valor entre os dois valores passados
     */
    @Override
    public double calcular(double valor1, double valor2) {
        return valor1 < valor2 ? valor1 : valor2;
    }

}
