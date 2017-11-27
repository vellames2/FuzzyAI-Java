package fuzzyai.reflexao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import fuzzyai.variavel.funcoespertinencia.IFuncaoPertinencia;

/**
 * Classe de reflexão criada para instanciar automaticamente as funções de pertinencia
 */
public final class ReflexaoFuncaoPertinencia {
    
    /**
     * Instancia da classe (Singleton)
     */
    private static ReflexaoFuncaoPertinencia instancia;
    
    /**
     * Pacote onde se encontram as funções de pertinencia
     */
    private final String pacoteVariaveis = "fuzzyai.variavel.funcoespertinencia";
    
    /**
     * Construtor privado (Singleton)
     */
    private ReflexaoFuncaoPertinencia(){}
    
    /**
     * Recupera instancia da classe (Singleton)
     * @return Retorna a instancia da classe
     */
    public static ReflexaoFuncaoPertinencia getInstancia() {
        if(instancia == null) {
            instancia = new ReflexaoFuncaoPertinencia();
        }
        return instancia;
    }
    
    /**
     * Cria uma função de inferencia por meio de reflexão
     * @param tipo Tipo da função de inferencia
     * @param nome Nome da função de inferencia
     * @param pontosFuncaoPertinencia Nome da Função de inferencia
     * @return Retorna a funçao de inferencia com todos os pontos inseridos
     * @throws Exception Retorna exceção caso não encontre a classe, metodo, caso os parametros estejam errados ou caso
     * nenhuma anotação seja encontrada para um determinado ponto da função de inferencia
     */
    public IFuncaoPertinencia criarFuncaoPertinencia(String tipo, String nome, JSONArray pontosFuncaoPertinencia) throws Exception{
        // Instancia uma nova função de pertinencia
        Class<?> classe = Class.forName(this.pacoteVariaveis + "." + tipo);
        Constructor<?> construtor = classe.getConstructor(String.class);
        Object funcaoPertinencia = construtor.newInstance(new Object[] { nome });
        
        // Recupera apenas os metodos com as anotações
        List<Method> metodosPontosFuncaoPertinencia = this.recuperarMetodosComAnotacoesVariaveis(funcaoPertinencia.getClass());
        
        // Chama os metodos da função de pertinencia baseados nos valores dos pontos
        for(int i = 0; i < pontosFuncaoPertinencia.length(); i++) {
            // Encontra o metodo referente ao ponto na classe
            boolean metodoEncontrado = false;
            for(final Method metodo: metodosPontosFuncaoPertinencia) {
                AnotacaoVariavelFuzzy anotacao = metodo.getAnnotation(AnotacaoVariavelFuzzy.class);
                if(anotacao.numeroPontoFuncaoPertinencia() == i) {
                    metodo.invoke(funcaoPertinencia, pontosFuncaoPertinencia.getDouble(i));
                    metodoEncontrado = true;
                    break;
                }
            }
            
            // Lança uma exceção se nenhum equivalente for encontrado
            if(!metodoEncontrado) {
                throw new IllegalArgumentException("Nenhum metodo encontrado para o ponto " + (i) + " da função de"
                        + " pertinencia " + nome);
            }
        }
        return (IFuncaoPertinencia) funcaoPertinencia;
    }
    
    /**
     * Recupera apenas os metodos com a anotação da função de pertinencia
     * @param classe Classe onde será procurada as anotações
     * @return Retorna uma lista com os metodos que possuem a anotação da função de pertinencia
     */
    private List<Method> recuperarMetodosComAnotacoesVariaveis(final Class<?> classe) {
        List<Method> metodos = new ArrayList<>();
        for(final Method metodo: Arrays.asList(classe.getDeclaredMethods())) {
            if(metodo.isAnnotationPresent(AnotacaoVariavelFuzzy.class)) {
                metodos.add(metodo);
            }
        }
        return metodos;
    }
}
