package fuzzyai.inferencia;

/**
 * Interface que deve ser implementada por qualquer função que queira realizar o calculo
 * da regra da inferencia. Essa classe deve possuir um metodo para extrair o valor da regra
 */
public interface IModoCalculo {
    /**
     * Realiza o calculo para extrair o valor da regra
     * @param valor1 Primeiro valor da comparação
     * @param valor2 Segundo valor da comparação
     * @return Retorna o resultado da comparação
     */
    public double calcular(double valor1, double valor2);
}
