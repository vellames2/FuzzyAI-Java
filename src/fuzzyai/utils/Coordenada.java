package fuzzyai.utils;

public final class Coordenada {
    
    private double x;
    private double y;
    
    public Coordenada(double x, double y) throws IllegalArgumentException{
        this.setX(x);
        this.setY(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        if(x < 0) {
            throw new IllegalArgumentException("O valor de X não pode ser menor do que zero");
        }
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        if(y < 0) {
            throw new IllegalArgumentException("O valor de Y não pode ser menor do que zero");
        }
        else if(y > 1) {
            throw new IllegalArgumentException("O valor de Y não pode ser maior do que um");
        }
        this.y = y;
    }
    
    
}
