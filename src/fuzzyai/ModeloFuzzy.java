package fuzzyai;

import org.json.*;

import fuzzyai.abstracoes.AFuncaoPertinencia;
import fuzzyai.implementacoes.funcoespertinencia.Trapezio;
import fuzzyai.implementacoes.funcoespertinencia.Triangulo;
import fuzzyai.utils.Coordenada;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class ModeloFuzzy {
    
    /**
     * Nome do modelo fuzzy
     */
    private String nome;
    
    /**
     * Lista com todas as variaveis do modelo
     */
    private ArrayList<VariavelFuzzy> variaveisFuzzy;
    
    /**
     * Define a ordem de entrada dos campos
     */
    private ArrayList<String> ordemEntrada;
    
    
    /**
     * Construtor do modelo fuzzy
     * @param nome Nome do modelo fuzzy
     */
    public ModeloFuzzy(String nome) {
        this.variaveisFuzzy = new ArrayList<>();
        this.ordemEntrada = new ArrayList<>();
        this.setNome(nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome.trim().length() == 0) {
            throw new IllegalArgumentException("O nome do Modelo Fuzzy não pode ser vazio");
        }
        this.nome = nome;
    }

    public ArrayList<VariavelFuzzy> getVariaveisFuzzy() {
        return variaveisFuzzy;
    }

    public void setVariaveisFuzzy(ArrayList<VariavelFuzzy> variaveisFuzzy) {
        this.variaveisFuzzy = variaveisFuzzy;
    }
    
    public ArrayList<String> getOrdemEntrada() {
        return this.ordemEntrada;
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void carregar(String caminho) throws Exception {
        // Recupera o JSON escrito no arquivo
        JSONObject jsonObject = this.getJson(caminho);
        
        // Carrega a ordem de inserção dos campos
        this.carregaOrdemEntrada(jsonObject);
        
        // Carrega as variaveis fuzzy
        this.carregaVariaveis(jsonObject);
    }
    
    public void fuzzyficar(ArrayList<Double> valoresEntrada) {
        /*
            A ordem de entrada nem sempre será igual a ordem que as variaveis fuzzy estão inseridas 
            no arraylist this.variaveisFuzzy. Por isso é necessário verificar a ordem de entrada e encontrar
            o indice da variavel fuzzy equivalente no arraylist
        */
        for(int i = 0; i < valoresEntrada.size(); i++) {
            // Nome da variavel de entrada
            String nomeValorEntrada = this.ordemEntrada.get(i);
            
            // Valor de Entrada
            double valorEntrada = valoresEntrada.get(i);
            
            // Verifica o indice da variavel fuzzy a ser fuzzyficada a partir do nome de entrada
            for(int j = 0; j < this.variaveisFuzzy.size(); j++) {
                VariavelFuzzy variavelFuzzy = this.variaveisFuzzy.get(j);
                if(variavelFuzzy.getNome().equals(nomeValorEntrada)) {
                    variavelFuzzy.fuzzyficar(valorEntrada);
                }
            }  
        }
    }
    
    /**
     * 
     * @param caminho
     * @return
     * @throws IOException 
     */
    private JSONObject getJson(String caminho) throws IOException, JSONException {
        // Inicializa variavel que guardará o json
        String json = "";
        
        // Abre o arquivo
        FileReader arquivo = new FileReader(caminho);
        
        // Instancia o leitor do arquivo
        BufferedReader leitor = new BufferedReader(arquivo);
       
        // Realiza a leitura da primeira linha
        String linha = leitor.readLine();
        
        // Adiciona a leitura da primeira linha a string json de retorno
        json = linha;
        
        // Realiza a leitura de todas as linhas do arquivo
        while(linha != null) {
            json += linha;
            linha = leitor.readLine();
        }
        
        // Fecha o arquivo
        arquivo.close();
        
        // Converte a string json em um JSONObject e retorna seu valor
        return new JSONObject(json);
    }
    
    /**
     * 
     * @param json
     * @throws Exception 
     */
    private void carregaOrdemEntrada(JSONObject jsonObject) throws Exception {
        JSONArray ordemEntrada = jsonObject.getJSONArray("entryOrder");
        for(int i = 0; i < ordemEntrada.length(); i++) {
            this.ordemEntrada.add(ordemEntrada.getString(i));
        }
    }
    
    /**
     * 
     * @param json
     * @throws Exception 
     */
    private void carregaVariaveis(JSONObject jsonObject) throws Exception {        
        // Recupera todas as variaveis fuzzy do json
        JSONArray variaveis = jsonObject.getJSONArray("variables");
        
        // Varre todo o array de variaveis
        for(int i = 0; i < variaveis.length(); i++) {
            
            // Recupera o JSONObject da variavel atual da iteração
            JSONObject variavel = variaveis.getJSONObject(i);
            
            // Extrai o nome da atual variavel
            String nomeVariavel = variavel.getString("name");
                    
            // Extrai todas as funções de pertinencia da variavel
            JSONArray funcoesPertinenciaVariavel = variavel.getJSONArray("values");
            
            // ArrayList com todas as funções de pertinencia da variavel
            ArrayList<AFuncaoPertinencia> funcoesPertinencia = new ArrayList<>();
            
            // Varre todas as funções de pertineicia da variavel
            for(int j = 0; j < funcoesPertinenciaVariavel.length(); j++) {
                
                // Recupera a função de pertinencia atual da iteração 
                JSONObject funcaoPertinenciaVariavel = funcoesPertinenciaVariavel.getJSONObject(j);
                
                // Recupera algumas informações da função de pertinencia
                String tipoFuncaoPertinencia = funcaoPertinenciaVariavel.getString("type");
                String nomeFuncaoPertinencia = funcaoPertinenciaVariavel.getString("name");
                
                // Verifica o tipo da variavel para saber qual tipo de funçao de pertinencia instanciar
                if(tipoFuncaoPertinencia.trim().toLowerCase().equals("triangle")) {
                    // Instancia um novo triangulo
                    Triangulo triangulo = new Triangulo(nomeFuncaoPertinencia);
                    
                    // Recupera os valores que montam a função de pertinencia
                    JSONArray funcaoPertinenciaValores = funcaoPertinenciaVariavel.getJSONArray("values");
                    
                    // Itera sobre os valores que montam a função de pertinencia
                    for(int k = 0; k < funcaoPertinenciaValores.length(); k++) {
                        // Recupera o valor da função de pertinencia
                        JSONObject funcaoPertinenciaValor = funcaoPertinenciaValores.getJSONObject(k);
                        
                        // Recupera X e Y e seta nos pontos da função de pertinencia
                        try {
                            double x = funcaoPertinenciaValor.getDouble("x");
                            double y = funcaoPertinenciaValor.getDouble("y");

                            switch(k) {
                                case 0:
                                    triangulo.setA(new Coordenada(x, y));
                                    break;
                                case 1:
                                    triangulo.setB(new Coordenada(x, y));
                                    break;
                                case 2:
                                    triangulo.setC(new Coordenada(x, y));
                                    break;
                                default:
                                    throw new IllegalArgumentException("Um triangulo pode apenas ter três coordenadas");
                            } 
                            //System.out.println(nomeFuncaoPertinencia + ": " + x + " - " + y);
                        } catch (JSONException e) {
                            //System.out.println("Null");
                        }
                    }
                    
                    // Adiciona a função de pertinencia a variavel
                    funcoesPertinencia.add(triangulo);
                    
                } 
                
                else if(tipoFuncaoPertinencia.trim().toLowerCase().equals("trapezio")) {
                    Trapezio trapezio = new Trapezio(nomeFuncaoPertinencia);
                    
                    // Recupera os valores que montam a função de pertinencia
                    JSONArray funcaoPertinenciaValores = funcaoPertinenciaVariavel.getJSONArray("values");
                    
                    // Itera sobre os valores que montam a função de pertinencia
                    for(int k = 0; k < funcaoPertinenciaValores.length(); k++) {
                        // Recupera o valor da função de pertinencia
                        JSONObject funcaoPertinenciaValor = funcaoPertinenciaValores.getJSONObject(k);
                        
                        // Recupera X e Y e seta nos pontos da função de pertinencia
                        try {
                            double x = funcaoPertinenciaValor.getDouble("x");
                            double y = funcaoPertinenciaValor.getDouble("y");

                            switch(k) {
                                case 0:
                                    trapezio.setA(new Coordenada(x, y));
                                    break;
                                case 1:
                                    trapezio.setB(new Coordenada(x, y));
                                    break;
                                case 2:
                                    trapezio.setC(new Coordenada(x, y));
                                    break;
                                case 3:
                                    trapezio.setD(new Coordenada(x, y));
                                    break;
                                default:
                                    throw new IllegalArgumentException("Um trapezio pode apenas ter quatro coordenadas");
                            } 
                            //System.out.println(nomeFuncaoPertinencia + ": " + x + " - " + y);
                        } catch (JSONException e) {
                            //System.out.println("Null");
                        }
                    }
                    
                    // Adiciona a função de pertinencia a variavel
                    funcoesPertinencia.add(trapezio);
                }
            }
            
            // Adiciona variavel fuzzy ao 
            this.variaveisFuzzy.add(new VariavelFuzzy(nomeVariavel, funcoesPertinencia));
        }
    }
    
}
