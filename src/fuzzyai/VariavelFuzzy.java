package fuzzyai;

import fuzzyai.abstracoes.AFuncaoPertinencia;
import fuzzyai.utils.VariavelFuzzyficada;
import java.util.ArrayList;
import java.util.HashMap;

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
    
    public VariavelFuzzyficada fuzzyficar(double x) {
        VariavelFuzzyficada variavelFuzzyficada = new VariavelFuzzyficada(this, x);
        HashMap<String, Double> resultado = new HashMap<>();
        
        // Varre todas as funções de pertinencia pra verificar em quais ela toca
        for(AFuncaoPertinencia funcaoPertinencia : this.funcoesPertinencia) {
            double primeiroPonto = funcaoPertinencia.getPrimeiroPonto();
            double ultimoPonto = funcaoPertinencia.getUltimoPonto();
            
            // Verifica se valor toca na atual iteração da função de pertinencia
            if((primeiroPonto != -1 && x >= primeiroPonto) || (ultimoPonto != -1 && x <= ultimoPonto)) {
                resultado.put(funcaoPertinencia.getNome(), funcaoPertinencia.getValorPertinencia(x));
            } else {
                resultado.put(funcaoPertinencia.getNome(), 0d);
            }
        }
        
        variavelFuzzyficada.setResultado(resultado);
        return variavelFuzzyficada;
    }
}