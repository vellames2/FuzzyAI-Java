package fuzzyai.implementacoes.funcoespertinencia;

import fuzzyai.abstracoes.AFuncaoPertinencia;
import fuzzyai.utils.Coordenada;

public final class Trapezio extends AFuncaoPertinencia{
    private Coordenada a,b,c,d;
    
    public Trapezio(String nome) throws IllegalArgumentException{
        super(nome);
    }
    
    public Coordenada getA() {
        return a;
    }

    public void setA(Coordenada a) {
        this.a = a;
    }

    public Coordenada getB() {
        return b;
    }

    public void setB(Coordenada b) {
        this.b = b;
    }

    public Coordenada getC() {
        return c;
    }

    public void setC(Coordenada c) {
        this.c = c;
    }

    public Coordenada getD() {
        return d;
    }

    public void setD(Coordenada d) {
        this.d = d;
    }

    @Override
    public Coordenada getPrimeiroPonto() {
        return this.getA();
    }

    @Override
    public Coordenada getUltimoPonto() {
        return this.getD();
    }

    @Override
    public double getValorPertinencia(double x) {
        
        boolean naoNulo;
        
        naoNulo = this.getA() != null;
        if(naoNulo && x < this.getA().getX()) {
            return 0;
        }
        
        naoNulo = this.getA() != null && this.getB() != null;
        if(naoNulo && (this.getA().getX() <= x && x < this.getB().getX())) {
            return (x - this.getA().getX()) / (this.getB().getX() - this.getA().getX());
        }
        
        naoNulo = this.getB() != null && this.getC() != null;
        if(naoNulo && (this.getB().getX() <= x && x <= this.getC().getX())) {
            return 1;
        }
        
        naoNulo = this.getC() != null && this.getD() != null;
        if(naoNulo && (this.getC().getX() < x && x < this.getD().getX())) {
            return (this.getD().getX() - x) / (this.getD().getX() - this.getC().getX());
        }
        
        naoNulo = this.getD() != null;
        if(naoNulo && (x >= this.getD().getX())) {
            return 0;
        }
        
        throw new IllegalArgumentException("O valor passado não contempla nenhuma formula do trapézio " + this.getNome());
    }
}
