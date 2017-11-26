package fuzzyai.inferencia;

import fuzzyai.ModeloFuzzy;
import fuzzyai.abstracoes.IInferencia;
import java.util.HashMap;
import java.util.List;

public class InferenciaPadrao implements IInferencia{

    @Override
    public void inferir(List<VariavelFuzzyficada> varaiveisFuzzyficadas, ModeloFuzzy modeloFuzzy) {
        HashMap<String, Double> resultadoRegras =  this.aplicarRegras(varaiveisFuzzyficadas);
        this.somarImagens(resultadoRegras, modeloFuzzy);
    }
    
    private HashMap<String, Double> aplicarRegras(List<VariavelFuzzyficada> varaiveisFuzzyficadas) {
        HashMap<String, Double> resultadoRegras = new HashMap<>();
        resultadoRegras.put("Baixo", .3);
        resultadoRegras.put("Médio", .4);
        resultadoRegras.put("Médio", .3);
        resultadoRegras.put("Alto", .6);
        return resultadoRegras;
    }
    
    private void somarImagens(HashMap<String, Double> resultadoRegras, ModeloFuzzy modeloFuzzy) {
        // AQUI LEO
    }
}
