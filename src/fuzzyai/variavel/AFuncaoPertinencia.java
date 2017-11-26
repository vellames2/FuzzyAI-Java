package fuzzyai.variavel;

/**
 * Abstração das funções de pertinencia de uma variavel fuzzy
 * Atualmente com suporte para triangulo e trapezio
 */
public abstract class AFuncaoPertinencia {
    
    /**
     * Nome da função de pertinencia
     */
    private String nome;
    
    /**
     * Construtor da classe
     * @param nome Nome da função de pertinencia
     * @throws IllegalArgumentException Exceções serão enviadas para o chamador da função
     */
    public AFuncaoPertinencia(String nome) throws IllegalArgumentException {
        this.setNome(nome);
    }
    
    /**
     * Recupera o nome da função de pertinencia
     * @return Retorna o nome da função de pertinencia
     */
    public String getNome() {
        return this.nome;
    }
    
    /**
     * Seta o nome da função de pertinencia
     * @param nome Nome da função de pertinencia
     * @throws IllegalArgumentException Uma exceção é lançada se o nome da função de pertinencia estiver vazia
     */
    public void setNome(String nome) throws IllegalArgumentException {
        if(nome.trim().length() == 0) {
            throw new IllegalArgumentException("O nome não pode ser vazio");
        }
        this.nome = nome;
    }
    
    /**
     * Toda função de pertinencia deve implementar um metodo retornando o valor do seu primeiro ponto
     * @return Retorna o valor do primeiro ponto da função de pertinencia
     */
    public abstract double getPrimeiroPonto();
    
    /**
     * Toda função de pertinencia deve implementar um metodo retornando o valor do seu ultimo ponto
     * @return Retorna o valor do ultimo ponto da função de pertinencia
     */
    public abstract double getUltimoPonto();
    
    /**
     * Toda função de pertinencia deve implementar um metodo que retorna o valor de pertinencia dado um ponto X
     * @param x Ponto X no gráfico da função de pertinencia
     * @return Retorna o valor de pertinencia dado um ponto x
     */
    public abstract double getValorPertinencia(double x);
}