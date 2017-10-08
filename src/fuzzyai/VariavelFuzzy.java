package fuzzyai;

import fuzzyai.abstracoes.AFuncaoPertinencia;
import fuzzyai.utils.Coordenada;
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
    
    public void fuzzyficar(double x) {
        System.out.println(this.nome);
        // Varre todas as funções de pertinencia pra verificar em quais ela toca
        for(AFuncaoPertinencia funcaoPertinencia : this.funcoesPertinencia) {
            Coordenada primeiroPonto = funcaoPertinencia.getPrimeiroPonto();
            Coordenada ultimoPonto = funcaoPertinencia.getUltimoPonto();
            
            // Verifica se valor toca na atual iteração da função de pertinencia
            System.out.println(funcaoPertinencia.getNome());
            if((primeiroPonto != null && x >= primeiroPonto.getX()) || (ultimoPonto != null && x <= ultimoPonto.getX())) {
                System.out.println(funcaoPertinencia.getValorPertinencia(x)); 
            } else {
                System.out.println(0);
            }
        }
    }
}