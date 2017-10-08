package fuzzyai.abstracoes;

import fuzzyai.utils.Coordenada;

public abstract class AFuncaoPertinencia {

    private String nome;
    
    public AFuncaoPertinencia(String nome, Coordenada... coordenadas) throws IllegalArgumentException {
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
    
    
    public abstract Coordenada getPrimeiroPonto();
    public abstract Coordenada getUltimoPonto();
    public abstract double getValorPertinencia(double x);
}