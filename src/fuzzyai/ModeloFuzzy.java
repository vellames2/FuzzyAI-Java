package fuzzyai;

import fuzzyai.variavel.VariavelFuzzy;
import org.json.*;

import fuzzyai.configuracoes.CalculoConector;
import fuzzyai.configuracoes.Configuracoes;
import fuzzyai.inferencia.Regra;
import fuzzyai.reflexao.ReflexaoFuncaoPertinencia;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import fuzzyai.variavel.funcoespertinencia.IFuncaoPertinencia;

/**
 * Contem todas as informações referentes ao modelo fuzzy em questão
 */
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
     * Variavel usada na inferencia
     */
    private VariavelFuzzy variavelInferencia;
    
    /**
     * Configurações que serão usadas durante a inferencia e deffuzyficaçao
     */
    private Configuracoes configuracoes;
    
    /**
     * Lista com todas as regras que devem ser usadas na inferencia
     */
    private List<Regra> regras;
    
    /**
     * Classe usada para a fuzzificação
     */
    private String modoFuzzificacao;
    
    /**
     * Classe usada para a inferencia
     */
    private String modoInferencia;
    
    /**
     * Classe usada para a defuzzificacao
     */
    private String modoDefuzzificacao;
    
    /**
     * Precisão usada na defuzzificação
     * Quanto menor o valor, mais preciso e mais custoso o calculo será
     */
    private double precisaoDefuzzificacao;
    
    /**
     * Objeto JSON com todas as informações do modelo, essa variavel é preenchida
     * quando os campos são carregados.
     */
    private JSONObject jsonObject;
            
    /**
     * Construtor do modelo fuzzy
     */
    public ModeloFuzzy() {
        this.variaveisFuzzy = new ArrayList<>();
        this.ordemEntrada = new ArrayList<>();
    }
    
    /**
     * Recupera o nome do modelo
     * @return Retorna o nome do modelo
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Seta o nome do modelo
     * @param nome Nome do modelo fuzzy
     */
    public void setNome(String nome) {
        if(nome.trim().length() == 0) {
            throw new IllegalArgumentException("O nome do Modelo Fuzzy não pode ser vazio");
        }
        this.nome = nome;
    }
    
    /**
     * Recupera o tipo do modelo
     * @return Retorna o tipo do modelo
     */
    public String getTipoModelo() {
        return this.tipoModelo;
    }

    /**
     * Seta o tipo do modelo
     * @param nome Nome do modelo
     */
    public void setTipoModelo(String nome) {
        if(nome.trim().length() == 0) {
            throw new IllegalArgumentException("O nome do Tipo de Modelo Fuzzy não pode ser vazio");
        }
        
        if(!nome.trim().equals("mandani")) {
            throw new IllegalArgumentException("Apenas o modelo \"Mandani\" é suportado no momento");
        }
        this.nome = nome;
    }
    
    /**
     * Recupera as variavies fuzzy carregadas no modelo
     * @return Retorna as variaveis fuzzy carregadas no modelo
     */
    public ArrayList<VariavelFuzzy> getVariaveisFuzzy() {
        return this.variaveisFuzzy;
    }
    
    /**
     * Seta as variaveis fuzzy do modelo
     * @param variaveisFuzzy Lista de todas as variaveis fuzzy
     */
    public void setVariaveisFuzzy(ArrayList<VariavelFuzzy> variaveisFuzzy) {
        this.variaveisFuzzy = variaveisFuzzy;
    }
    
    /**
     * Recupera a ordem de entrada das variaveis
     * @return Retorna a ordem de entrada das variaveis
     */
    public ArrayList<String> getOrdemEntrada() {
        return this.ordemEntrada;
    }
    
    /**
     * Recupera as configurações do modelo
     * @return Retorna as configurações do modelo
     */
    public Configuracoes getConfiguracoes() {
        return this.configuracoes;
    }
    
    /**
     * Seta as configurações do modelo
     * @param configuracoes Objeto com as configurações do modelo
     */
    public void setConfiguracoes(Configuracoes configuracoes) {
        this.configuracoes = configuracoes;
    }
    
    /**
     * Recupera todas as regras da inferencia
     * @return Retorna todas as regras da inferencia
     */
    public List<Regra> getRegras() {
        return this.regras;
    }
    
    /**
     * Seta todas as regras da inferenca
     * @param regras Lista com todas as regras da inferencia
     */
    public void setRegras(List<Regra> regras) {
        this.regras = regras;
    }

    /**
     * Recupera a varaivel usada na inferencia
     * @return Retorna a variavel usada na inferencia
     */
    public VariavelFuzzy getVariavelInferencia() {
        return variavelInferencia;
    }
    
    /**
     * Seta a variavel usada na inferencia
     * @param variavelInferencia Variavel usada na inferencia
     */
    public void setVariavelInferencia(VariavelFuzzy variavelInferencia) {
        this.variavelInferencia = variavelInferencia;
    }
    
    /**
     * Recupera o nome da classe a ser usada na fuzzificacao
     * @return Retorna o nome da classe a ser usada na fuzzificacao
     */
    public String getModoFuzzificacao() {
        return modoFuzzificacao;
    }

    /**
     * Seta o nome da classe a ser usada na fuzzificacao
     * @param modoFuzzificacao Nome da classe a ser usada na fuzzificacao
     */
    public void setModoFuzzificacao(String modoFuzzificacao) {
        this.modoFuzzificacao = modoFuzzificacao;
    }
    
    /**
     * Recupera o nome da classe a ser usada na inferencia
     * @return Retorna o nome da classe a ser usada na inferencia
     */
    public String getModoInferencia() {
        return modoInferencia;
    }
    
     /**
     * Seta o nome da classe a ser usada na inferencia
     * @param modoInferencia Nome da classe a ser usada na inferencia
     */
    public void setModoInferencia(String modoInferencia) {
        this.modoInferencia = modoInferencia;
    }
    
    /**
     * Recupera o nome da classe a ser usada na defuzzificacao
     * @return Retorna o nome da classe a ser usada na defuzzificacao
     */
    public String getModoDefuzzificacao() {
        return modoDefuzzificacao;
    }
    
     /**
     * Seta o nome da classe a ser usada na inferencia
     * @param modoDefuzzificacao Nome da classe a ser usada na defuzzificacao
     */
    public void setModoDefuzzificacao(String modoDefuzzificacao) {
        this.modoDefuzzificacao = modoDefuzzificacao;
    }
    
    /**
     * Recupera o a precisão usada durante a defuzzificacao
     * @return Retorna o a precisão usada durante a defuzzificacao
     */
    public double getPrecisaoDefuzzificacao() {
        return precisaoDefuzzificacao;
    }
    
    /**
     * Seta a precisão usada na defuzzificacao
     * @param precisaoDefuzzificacao Precisão
     */
    public void setPrecisaoDefuzzificacao(double precisaoDefuzzificacao) {
        this.precisaoDefuzzificacao = precisaoDefuzzificacao;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Carrega o modelo fuzzy
     * @param caminho Caminho do JSON
     * @throws Exception Qualquer excessão será enviada para o chamador da função
     */
    public void carregarCampos(String caminho) throws Exception {
        // Recupera o JSON escrito no arquivo
        JSONObject jsonObject = this.transformarArquivoEmJSON(caminho);
        
        // Guarda resultado do jsonObject para nao precisar fazer a transformação novamente
        this.jsonObject = jsonObject;
        
        // Carrega a ordem de inserção dos campos
        this.carregarOrdemEntrada(jsonObject);
    }
    
    /**
     * Carrega todos os dados do modelo fuzzy
     * @throws Exception Qualquer excessão será enviada para o chamador da função
     */
    public void carregarModeloFuzzy() throws Exception {     
        
        if(this.jsonObject == null) {
            throw new IllegalArgumentException("O JSON ainda não foi carregado");
        }
        
        // Carrega as informações básicas do modelo
        this.carregarInformacoesBasicas(this.jsonObject);
        
        // Carrega as configuraçõs do modelo
        this.carregarConfiguracoes(this.jsonObject);
        
        // Carrega as variaveis fuzzy
        this.carregarVariaveis(this.jsonObject);
        
        // Carrega a variavel da inferencia
        this.carregarVariavelInferencia(this.jsonObject);
        
        // Carrega as regras da inferencia
        this.carregarRegras(this.jsonObject);
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
            ArrayList<IFuncaoPertinencia> funcoesPertinencia = new ArrayList<>();
            
            // Varre todas as funções de pertineicia da variavel
            for(int j = 0; j < funcoesPertinenciaVariavel.length(); j++) {
                
                // Recupera a função de pertinencia atual da iteração 
                JSONObject funcaoPertinenciaVariavel = funcoesPertinenciaVariavel.getJSONObject(j);
                
                // Recupera algumas informações da função de pertinencia
                String tipoFuncaoPertinencia = funcaoPertinenciaVariavel.getString("type");
                String nomeFuncaoPertinencia = funcaoPertinenciaVariavel.getString("name");
                
                // Instancia uma nova função de pertinencia e adiciona a lista de funções de pertinencia da variavel fuzzy
                IFuncaoPertinencia funcaoPertinencia = ReflexaoFuncaoPertinencia.getInstancia().criarFuncaoPertinencia(
                        tipoFuncaoPertinencia, 
                        nomeFuncaoPertinencia,
                        funcaoPertinenciaVariavel.getJSONArray("values")
                );
                funcoesPertinencia.add(funcaoPertinencia);
            }
            
            // Adiciona variavel fuzzy ao 
            this.variaveisFuzzy.add(new VariavelFuzzy(nomeVariavel, funcoesPertinencia));
        }
    }
    
    /**
     * Carrega as informações básicas do modelo (Nome e Tipo)
     * @param jsonObject Objeto JSON com o modelo fuzzy já carregado
     * @throws Exception QUalquer excessão será enviada para o chamador da função
     */
    private void carregarInformacoesBasicas(JSONObject jsonObject) throws Exception {
        this.setNome(jsonObject.getString("nameOfResult"));
        this.setTipoModelo(jsonObject.getString("modelType"));
    }
    
    /**
     * Carrega as configurações do modelo fuzzy (modo de calculo e deffuzificação)
     * @param jsonObject Objeto JSON com o modelo fuzzy já carregado
     * @throws Exception QUalquer excessão será enviada para o chamador da função
     */
    private void carregarConfiguracoes(JSONObject jsonObject) throws Exception {
        JSONObject configuracoes = jsonObject.getJSONObject("configurations");
        
        String modoDeffuzyficacao = configuracoes.getString("deffuzyfication");
        List<CalculoConector> calculoConectores = new ArrayList<>();
   
        JSONArray calculoConectoresJSON = configuracoes.getJSONArray("calcMode");
        for(int i = 0; i < calculoConectoresJSON.length(); i++) {
            JSONObject calculoConectorJSON = calculoConectoresJSON.getJSONObject(i);
            String conector = calculoConectorJSON.getString("type");
            String modo = calculoConectorJSON.getString("mode");
            calculoConectores.add(new CalculoConector(conector, modo));
        }
        
        this.setConfiguracoes(new Configuracoes(modoDeffuzyficacao, calculoConectores));
        this.setModoFuzzificacao(configuracoes.getString("fuzzyfication"));
        this.setModoInferencia(configuracoes.getString("inference"));
        this.setModoDefuzzificacao(configuracoes.getString("deffuzyfication"));
        this.setPrecisaoDefuzzificacao(configuracoes.getDouble("precision"));
    }
    
    /**
     * Carrega todas as regras que serão usadas na inferencia
     * @param jsonObject Objeto JSON com o modelo fuzzy já carregado
     * @throws Exception QUalquer excessão será enviada para o chamador da função
     */
    private void carregarRegras(JSONObject jsonObject) throws Exception {
        JSONArray regrasJSON = jsonObject.getJSONArray("rules");
        List<Regra> regras = new ArrayList<>();
        for(int i = 0; i < regrasJSON.length(); i++) {
            JSONObject regraJSON = regrasJSON.getJSONObject(i);
            Regra regra = new Regra();
            
            // Adicionando as regras do modelo
            JSONArray variaveisJSON = regraJSON.getJSONArray("variables");
            List<String> variaveis = new ArrayList<>();
            for(int x = 0; x < variaveisJSON.length(); x++) {
                variaveis.add(variaveisJSON.getString(x));
            }
            regra.setVariaveis(variaveis);
            
            // Adicionando os resultados das variaveis no modelo
            JSONArray resultadosJSON = regraJSON.getJSONArray("results");
            List<String> resultados = new ArrayList<>();
            for(int x = 0; x < resultadosJSON.length(); x++) {
                resultados.add(resultadosJSON.getString(x));
            }
            regra.setResultados(resultados);
            
            // Adicionando conectores
            JSONArray conectoresJSON = regraJSON.getJSONArray("connector");
            List<String> conectores = new ArrayList<>();
            for(int x = 0; x < conectoresJSON.length(); x++) {
                conectores.add(conectoresJSON.getString(x));
            }
            regra.setConectores(conectores);
            
            // Adicionando consequente
            String consequente = regraJSON.getString("consequent");
            regra.setConsequente(consequente);
            
            // Adicionando a lista
            regras.add(regra);
        }
        
        this.setRegras(regras);
    }
    
    /**
     * Carrega a variavel usada na inferencia
     * @param jsonObject Objeto JSON com o modelo fuzzy já carregado
     * @throws Exception QUalquer excessão será enviada para o chamador da função
     */
    private void carregarVariavelInferencia(JSONObject jsonObject) throws Exception {
        JSONObject variavelJSON = jsonObject.getJSONObject("variables").getJSONObject("output");
        JSONArray funcoesPertinenciaVariavel = variavelJSON.getJSONArray("values");
        
        // ArrayList com todas as funções de pertinencia da variavel
        ArrayList<IFuncaoPertinencia> funcoesPertinencia = new ArrayList<>();
        
        // Varre todas as funções de pertineicia da variavel
        for(int j = 0; j < funcoesPertinenciaVariavel.length(); j++) {
            // Recupera a função de pertinencia atual da iteração 
            JSONObject funcaoPertinenciaVariavel = funcoesPertinenciaVariavel.getJSONObject(j);

            // Recupera algumas informações da função de pertinencia
            String tipoFuncaoPertinencia = funcaoPertinenciaVariavel.getString("type");
            String nomeFuncaoPertinencia = funcaoPertinenciaVariavel.getString("name");

            // Instancia uma nova função de pertinencia e adiciona a lista de funções de pertinencia da variavel fuzzy
            IFuncaoPertinencia funcaoPertinencia = ReflexaoFuncaoPertinencia.getInstancia().criarFuncaoPertinencia(
                    tipoFuncaoPertinencia, 
                    nomeFuncaoPertinencia,
                    funcaoPertinenciaVariavel.getJSONArray("values")
            );
            funcoesPertinencia.add(funcaoPertinencia);
        }
        
        this.setVariavelInferencia(new VariavelFuzzy(variavelJSON.getString("name"), funcoesPertinencia));
    }
}
