package fuzzyai.abstracoes;

public abstract class AFuncaoPertinencia {

    private String nome;
    
    public AFuncaoPertinencia(String nome) throws IllegalArgumentException {
        this.setNome(nome);
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        if(nome.trim().length() == 0) {
            throw new IllegalArgumentException("O nome não pode ser vazio");
        }
        this.nome = nome;
    }
    
    
    public abstract double getPrimeiroPonto();
    public abstract double getUltimoPonto();
    public abstract double getValorPertinencia(double x);
}