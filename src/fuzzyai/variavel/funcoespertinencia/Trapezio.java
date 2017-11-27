package fuzzyai.variavel.funcoespertinencia;

import fuzzyai.reflexao.AnotacaoVariavelFuzzy;

/**
 * Implementação do Trapezio, um tipo de função de pertinencia
 */
public final class Trapezio implements IFuncaoPertinencia {
    /**
     * Pontos do trapezio
     */
    private double a,b,c,d;
    
    /**
     * Nome do Trapezio
     */
    private String nome;
    
    /**
     * Construtor do trapezio
     * @param nome Nome do trapezio
     * @throws IllegalArgumentException Exceções são enviadas para o chamador da funçao
     */
    public Trapezio(String nome) throws IllegalArgumentException{
        this.setNome(nome);
    }
    
    /**
     * Recupera o nome da função de pertinencia
     * @return Retorna o nome da função de pertinencia
     */
    @Override
    public String getNome() {
        return this.nome;
    }
    
    /**
     * Seta o nome da função de pertinencia
     * @param nome Nome da função de pertinencia
     * @throws IllegalArgumentException Uma exceção é lançada se o nome da função de pertinencia estiver vazia
     */
    @Override
    public void setNome(String nome) throws IllegalArgumentException {
        if(nome == null || nome.isEmpty()){
            throw new IllegalArgumentException("O nome da função de pertinencia não pode ser vazio");
        }
        this.nome = nome;
    }
    
    /**
     * Recupera o ponto A do trapezio
     * @return Retorna o ponto A do trapezio
     */
    public double getA() {
        return a;
    }
    
    /**
     * Seta o ponto A do trapezio
     * @param a Ponto A do trapezio
     * @throws IllegalArgumentException O trapezio aceita apenas valores positivos ou -1 para representar nulo
     */
    @AnotacaoVariavelFuzzy(numeroPontoFuncaoPertinencia = 0)
    public void setA(double a) throws IllegalArgumentException {
        if(a < 0 && a != -1) {
            throw new IllegalArgumentException("Utilize -1 para representar um ponto nulo");
        }
        this.a = a;
    }
    
    /**
     * Recupera o ponto B do trapezio
     * @return Retorna o ponto B do trapezio
     */
    public double getB() {
        return b;
    }
    
    /**
     * Seta o ponto B do trapezio
     * @param b Ponto B do trapezio
     * @throws IllegalArgumentException O trapezio aceita apenas valores positivos ou -1 para representar nulo
     */
    @AnotacaoVariavelFuzzy(numeroPontoFuncaoPertinencia = 1)
    public void setB(double b) {
        if(b < 0 && b != -1) {
            throw new IllegalArgumentException("Utilize -1 para representar um ponto nulo");
        }
        this.b = b;
    }
    
    /**
     * Recupera o ponto C do trapezio
     * @return Retorna o ponto C do trapezio
     */
    public double getC() {
        return c;
    }
    
    /**
     * Seta o ponto C do trapezio
     * @param c Ponto C do trapezio
     * @throws IllegalArgumentException O trapezio aceita apenas valores positivos ou -1 para representar nulo
     */
    @AnotacaoVariavelFuzzy(numeroPontoFuncaoPertinencia = 2)
    public void setC(double c) {
        if(c < 0 && c != -1) {
            throw new IllegalArgumentException("Utilize -1 para representar um ponto nulo");
        }
        this.c = c;
    }
    
    /**
     * Recupera o ponto D do trapezio
     * @return Retorna o ponto D do trapezio
     */
    public double getD() {
        return d;
    }
    
    /**
     * Seta o ponto D do trapezio
     * @param d Ponto D do trapezio
     * @throws IllegalArgumentException O trapezio aceita apenas valores positivos ou -1 para representar nulo
     */
    @AnotacaoVariavelFuzzy(numeroPontoFuncaoPertinencia = 3)
    public void setD(double d) {
        if(d < 0 && d != -1) {
            throw new IllegalArgumentException("Utilize -1 para representar um ponto nulo");
        }
        this.d = d;
    }
    
    /**
     * Recupera o primeiro ponto do trapezio
     * @return Retorna o primeiro ponto do trapezio
     */
    @Override
    public double getPrimeiroPonto() {
        return this.getA();
    }
    
    /**
     * Recupera o ultimo ponto do trapezio
     * @return Retorna o ultimo ponto do trapezio
     */
    @Override
    public double getUltimoPonto() {
        return this.getD();
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
        if(naoNulo && x < this.getA()) {
            return 0;
        }
        
        naoNulo = this.getA() != -1 && this.getB() != -1;
        if(naoNulo && (this.getA() <= x && x < this.getB())) {
            return (x - this.getA()) / (this.getB() - this.getA());
        }
        
        naoNulo = this.getB() != -1 && this.getC() != -1;
        if(naoNulo && (this.getB() <= x && x <= this.getC())) {
            return 1;
        }
        
        naoNulo = this.getC() != -1 && this.getD() != -1;
        if(naoNulo && (this.getC() < x && x < this.getD())) {
            return (this.getD() - x) / (this.getD() - this.getC());
        }
        
        naoNulo = this.getD() != -1;
        if(naoNulo && (x >= this.getD())) {
            return 0;
        }
        
        throw new IllegalArgumentException("O valor passado não contempla nenhuma formula do trapézio " + this.getNome());
    }
}
