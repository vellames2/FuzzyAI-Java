package fuzzyai;

import fuzzyai.abstracoes.AFuncaoPertinencia;
import java.util.ArrayList;

public final class VariavelFuzzy {
    private String nome;
    private ArrayList<AFuncaoPertinencia> funcoesPertinencia;
    
    public VariavelFuzzy(String nome, ArrayList<AFuncaoPertinencia> funcoesPertinencia) throws IllegalArgumentException{
        this.setNome(nome);
        this.setFuncoesPertinencia(funcoesPertinencia);
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

    public ArrayList<AFuncaoPertinencia> getFuncoesPertinencia() {
        return funcoesPertinencia;
    }

    public void setFuncoesPertinencia(ArrayList<AFuncaoPertinencia> funcoesPertinencia) {
        this.funcoesPertinencia = funcoesPertinencia;
    }
    
}