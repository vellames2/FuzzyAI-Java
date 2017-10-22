package fuzzyai.implementacoes.funcoespertinencia;

import fuzzyai.abstracoes.AFuncaoPertinencia;

public final class Trapezio extends AFuncaoPertinencia {
    private double a,b,c,d;

    public Trapezio(String nome) throws IllegalArgumentException{
        super(nome);
    }
    
    public double getA() {
        return a;
    }

    public void setA(double a) {
        if(a < -1) {
            throw new IllegalArgumentException("Utilize -1 para representar um ponto nulo");
        }
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        if(b < -1) {
            throw new IllegalArgumentException("Utilize -1 para representar um ponto nulo");
        }
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        if(c < -1) {
            throw new IllegalArgumentException("Utilize -1 para representar um ponto nulo");
        }
        this.c = c;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        if(d < -1) {
            throw new IllegalArgumentException("Utilize -1 para representar um ponto nulo");
        }
        this.d = d;
    }
    
    @Override
    public double getPrimeiroPonto() {
        return this.getA();
    }

    @Override
    public double getUltimoPonto() {
        return this.getD();
    }

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
