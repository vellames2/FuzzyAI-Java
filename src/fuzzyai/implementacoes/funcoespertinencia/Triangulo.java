package fuzzyai.implementacoes.funcoespertinencia;

import fuzzyai.abstracoes.AFuncaoPertinencia;
import fuzzyai.utils.Coordenada;

public class Triangulo extends AFuncaoPertinencia {
   
    private Coordenada a,b,c;
    
    public Triangulo(String nome) throws IllegalArgumentException{
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
    
    @Override
    public Coordenada getPrimeiroPonto() {
        return this.getA();
    }

    @Override
    public Coordenada getUltimoPonto() {
        return this.getC();
    }

    @Override
    public double getValorPertinencia(double x) {
        boolean naoNulo;
        
        naoNulo = this.getA() != null;
        if(naoNulo && (x < this.getA().getX())) {
            return 0;
        }
        
        naoNulo = this.getA() != null && this.getB() != null;
        if(naoNulo && (this.getA().getX() <= x && x < this.getB().getX())) {
            return (x - this.getA().getX()) / (this.getB().getX() - this.getA().getX());
        }
        
        naoNulo = this.getB() != null && this.getC() != null;
        if(naoNulo && (this.getB().getX() <= x && x < this.getC().getX())) {
            return (this.getC().getX() - x) / (this.getC().getX() - this.getB().getX());
        }
        
        naoNulo = this.getC() != null;
        if(naoNulo && (this.getC().getX() <= x)) {
            return 0;
        }
        
        throw new IllegalArgumentException("O valor passado não contempla nenhuma formula do triangulo " + this.getNome());
    }
}
