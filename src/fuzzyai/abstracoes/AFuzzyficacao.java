package fuzzyai.abstracoes;

import fuzzyai.ModeloFuzzy;
import fuzzyai.VariavelFuzzy;
import fuzzyai.utils.VariavelFuzzyficada;
import java.util.ArrayList;
import java.util.List;

public abstract class AFuzzyficacao {
    
    private ModeloFuzzy modeloFuzzy;

    public AFuzzyficacao(ModeloFuzzy modeloFuzzy) {
        this.setModeloFuzzy(modeloFuzzy);
    }
    
    public ModeloFuzzy getModeloFuzzy() {
        return modeloFuzzy;
    }

    public void setModeloFuzzy(ModeloFuzzy modeloFuzzy) {
        this.modeloFuzzy = modeloFuzzy;
    }
    
    public abstract List<VariavelFuzzyficada> fuzzyficar(List<Double> valoresEntrada);
    protected abstract VariavelFuzzyficada fuzzyficarVariavel(VariavelFuzzy variavelFuzzy, double valorEntrada);
}
