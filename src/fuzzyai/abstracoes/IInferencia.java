package fuzzyai.abstracoes;

import fuzzyai.ModeloFuzzy;
import fuzzyai.inferencia.VariavelFuzzyficada;
import java.util.List;

public interface IInferencia {
        public void inferir(List<VariavelFuzzyficada> varaiveisFuzzyficadas, ModeloFuzzy modeloFuzzy) throws Exception;
}
