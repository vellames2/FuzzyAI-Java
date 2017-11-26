package fuzzyai.variavel;

/**
 * Implementação do Triangulo, um tipo de função de pertinencia
 */
public final class Triangulo extends AFuncaoPertinencia {
    
    /**
     * Pontos do triangulo
     */
    private double a,b,c;
    
    /**
     * Construtor do triangulo
     * @param nome Nome do triangulo
     * @throws IllegalArgumentException Exceções são enviadas para o chamador da funçao
     */
    public Triangulo(String nome) throws IllegalArgumentException{
        super(nome);
    }

    /**
     * Recupera o ponto A do triangulo
     * @return Retorna o ponto A do triangulo
     */
    public double getA() {
        return a;
    }
    
    /**
     * Seta o ponto A do triangulo
     * @param a Ponto A do triangulo
     * @throws IllegalArgumentException O triangulo aceita apenas valores positivos ou -1 para representar nulo
     */
    public void setA(double a) throws IllegalArgumentException {
        if(a < 0 && a != -1) {
            throw new IllegalArgumentException("Utilize -1 para representar um ponto nulo");
        }
        this.a = a;
    }
    
    /**
     * Recupera o ponto B do triangulo
     * @return Retorna o ponto B do triangulo
     */
    public double getB() {
        return b;
    }
    
    /**
     * Seta o ponto B do triangulo
     * @param b Ponto B do triangulo
     * @throws IllegalArgumentException O triangulo aceita apenas valores positivos ou -1 para representar nulo
     */
    public void setB(double b) {
        if(b < 0 && b != -1) {
            throw new IllegalArgumentException("Utilize -1 para representar um ponto nulo");
        }
        this.b = b;
    }
    
    /**
     * Recupera o ponto C do triangulo
     * @return Retorna o ponto C do triangulo
     */
    public double getC() {
        return c;
    }
    
    /**
     * Seta o ponto C do triangulo
     * @param c Ponto C do triangulo
     * @throws IllegalArgumentException O triangulo aceita apenas valores positivos ou -1 para representar nulo
     */
    public void setC(double c) {
        if(c < 0 && c != -1) {
            throw new IllegalArgumentException("Utilize -1 para representar um ponto nulo");
        }
        this.c = c;
    }
    
    /**
     * Recupera o primeiro ponto do triangulo
     * @return Retorna o primeiro ponto do triangulo
     */
    @Override
    public double getPrimeiroPonto() {
        return this.getA();
    }
    
    /**
     * Recupera o ultimo ponto do triangulo
     * @return Retorna o ultimo ponto do triangulo
     */
    @Override
    public double getUltimoPonto() {
        return this.getC();
    }
    
    /**
     * Calcula o valor de pertinencia da variavel
     * @param x Ponto X de entrada no grafico
     * @return Retorna um valor de pertinencia da variavel
     */
    @Override
    public double getValorPertinencia(double x) {
        boolean naoNulo;
        
        naoNulo = this.getA() != -1;
        if(naoNulo && (x < this.getA())) {
            return 0;
        }
        
        naoNulo = this.getA() != -1 && this.getB() != -1;
        if(naoNulo && (this.getA()<= x && x < this.getB())) {
            return (x - this.getA()) / (this.getB() - this.getA());
        }
        
        naoNulo = this.getB() != -1 && this.getC() != -1;
        if(naoNulo && (this.getB()<= x && x < this.getC())) {
            return (this.getC() - x) / (this.getC() - this.getB());
        }
        
        naoNulo = this.getC() != -1;
        if(naoNulo && (this.getC() <= x)) {
            return 0;
        }
        
        throw new IllegalArgumentException("O valor passado não contempla nenhuma formula do triangulo " + this.getNome());
    }
}
