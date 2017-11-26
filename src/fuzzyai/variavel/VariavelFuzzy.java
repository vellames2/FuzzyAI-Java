package fuzzyai.variavel;

import fuzzyai.variavel.AFuncaoPertinencia;
import java.util.ArrayList;

/**
 * Representação de uma variavel fuzzy
 */
public final class VariavelFuzzy {
    
    /**
     * Nome da variavel fuzzy
     */
    private String nome;
    
    /**
     * Lista com todas as funções de pertinencia da variavel
     */
    private ArrayList<AFuncaoPertinencia> funcoesPertinencia;
    
    /**
     * Construtor da classe
     * @param nome Nome da varaivel fuzzy
     * @param funcoesPertinencia Funções de pertinencia
     * @throws IllegalArgumentException Exceções serão enviadas para o chamador da função
     */
    public VariavelFuzzy(String nome, ArrayList<AFuncaoPertinencia> funcoesPertinencia) throws IllegalArgumentException{
        this.setNome(nome);
        this.setFuncoesPertinencia(funcoesPertinencia);
    }
    
    /**
     * Recupera o nome da variavel fuzzy
     * @return Retorna o nome da variavel fuzzy
     */
    public String getNome() {
        return this.nome;
    }
    
    /**
     * Seta o nome da variavel fuzzy
     * @param nome Nome da variavel fuzzy
     * @throws IllegalArgumentException Uma exceção é lançada se o nome da variavel estiver vazio
     */
    public void setNome(String nome) throws IllegalArgumentException{
        if(nome.trim().length() == 0) {
            throw new IllegalArgumentException("O nome não pode ser vazio");
        }
        this.nome = nome;
    }
    
    /**
     * Recupera as funções de pertinencia da variavel
     * @return Retorna as funções de pertinencia da variavel
     */
    public ArrayList<AFuncaoPertinencia> getFuncoesPertinencia() {
        return funcoesPertinencia;
    }

    /**
     * Seta a lista de funções de pertinencia da variavel
     * @param funcoesPertinencia Lista com as funções de pertinencia da variavel
     * @throws IllegalArgumentException Uma exceção é lançada se a lista estiver nula ou vazia
     */
    public void setFuncoesPertinencia(ArrayList<AFuncaoPertinencia> funcoesPertinencia) throws IllegalArgumentException {
        if(funcoesPertinencia == null) {
            throw new IllegalArgumentException("A lista de funções de pertinencia não pode ser nula");
        }
        
        if(funcoesPertinencia.size() == 0) {
            throw new IllegalArgumentException("A lista de funções de pertinencia precisa ter pelo menos um item");
        }
        this.funcoesPertinencia = funcoesPertinencia;
    }
    
}