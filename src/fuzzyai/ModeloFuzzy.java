package fuzzyai;

import org.json.*;

import fuzzyai.abstracoes.AFuncaoPertinencia;
import fuzzyai.implementacoes.funcoespertinencia.Trapezio;
import fuzzyai.implementacoes.funcoespertinencia.Triangulo;
import fuzzyai.utils.VariavelFuzzyficada;
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
     * Tipo do modelo fuzzy
     * Aceita apenas mandani no momento
     */
    private String tipoModelo;
    
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
     */
    public ModeloFuzzy() {
        this.variaveisFuzzy = new ArrayList<>();
        this.ordemEntrada = new ArrayList<>();
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
    
    public String getTipoModelo() {
        return this.tipoModelo;
    }

    public void setTipoModelo(String nome) {
        if(nome.trim().length() == 0) {
            throw new IllegalArgumentException("O nome do Tipo de Modelo Fuzzy não pode ser vazio");
        }
        
        if(!nome.trim().equals("mandani")) {
            throw new IllegalArgumentException("Apenas o modelo \"Mandani\" é suportado no momento");
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
    
    /**
     * Carrega o modelo fuzzy
     * @param caminho Caminho do JSON
     * @throws Exception Qualquer excessão será enviada para o chamador da função
     */
    public void carregar(String caminho) throws Exception {
        // Recupera o JSON escrito no arquivo
        JSONObject jsonObject = this.transformarArquivoEmJSON(caminho);
        
        // Carrega a ordem de inserção dos campos
        this.carregarOrdemEntrada(jsonObject);
        
        // Carrega as variaveis fuzzy
        this.carregarVariaveis(jsonObject);
    }
    
    /**
     * Realiza a etapa de fuzzyficação de todas as variaveis
     * @param valoresEntrada Valores recebidos na entrada
     */
    public ArrayList<VariavelFuzzyficada> fuzzyficar(ArrayList<Double> valoresEntrada) {
        ArrayList<VariavelFuzzyficada> variaveisFuzzyficadas = new ArrayList<>();
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
                    variaveisFuzzyficadas.add(variavelFuzzy.fuzzyficar(valorEntrada));
                }
            }  
        }
        
        return variaveisFuzzyficadas;
    }
    
    /**
     * Realiza a leitura do arquivo e transforma em um JSONObject
     * @param caminho Caminho para o arquivo
     * @return Retorna um JSONObject com o modelo carregado
     * @throws IOException, JSONException Qualquer excessão será enviada para o chamador da função
     */
    private JSONObject transformarArquivoEmJSON(String caminho) throws IOException, JSONException {
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
     * Carrega a ordem de entrada das variaveis
     * @param json Objeto JSON com o modelo fuzzy já carregado
     * @throws Exception Qualquer excessão será enviada para o chamador da função
     */
    private void carregarOrdemEntrada(JSONObject jsonObject) throws Exception {
        JSONArray ordemEntrada = jsonObject.getJSONArray("entryOrder");
        for(int i = 0; i < ordemEntrada.length(); i++) {
            this.ordemEntrada.add(ordemEntrada.getString(i));
        }
    }
    
    /**
     * Carrega as variaveis 
     * @param json Objeto JSON com o modelo fuzzy já carregado
     * @throws Exception Qualquer excessão será enviada para o chamador da função
     */
    private void carregarVariaveis(JSONObject jsonObject) throws Exception {        
        // Recupera todas as variaveis fuzzy de entrada do json
        JSONArray variaveis = jsonObject.getJSONObject("variables").getJSONArray("input");
        
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
                        // Recupera pontos e seta nos pontos da função de pertinencia
                        try {
                            double value = funcaoPertinenciaValores.getDouble(k);

                            switch(k) {
                                case 0:
                                    triangulo.setA(value);
                                    break;
                                case 1:
                                    triangulo.setB(value);
                                    break;
                                case 2:
                                    triangulo.setC(value);
                                    break;
                                default:
                                    throw new IllegalArgumentException("Um triangulo pode apenas ter três coordenadas");
                            } 
                            //System.out.println(nomeFuncaoPertinencia + ": " + x + " - " + y);
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                        // Recupera pontos e seta nos pontos da função de pertinencia
                        try {
                            double value = funcaoPertinenciaValores.getDouble(k);

                            switch(k) {
                                case 0:
                                    trapezio.setA(value);
                                    break;
                                case 1:
                                    trapezio.setB(value);
                                    break;
                                case 2:
                                    trapezio.setC(value);
                                    break;
                                case 3:
                                    trapezio.setD(value);
                                    break;
                                default:
                                    throw new IllegalArgumentException("Um trapezio pode apenas ter quatro coordenadas");
                            } 
                            //System.out.println(nomeFuncaoPertinencia + ": " + x + " - " + y);
                        } catch (JSONException e) {
                            e.printStackTrace();
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
