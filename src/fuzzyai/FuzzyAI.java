package fuzzyai;

import fuzzyai.utils.VariavelFuzzyficada;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FuzzyAI {

    public static void main(String[] args){
        ArrayList<Double> valoresEntrada = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        try {
            ModeloFuzzy modeloFuzzy = new ModeloFuzzy();
            modeloFuzzy.carregar("/home/vellames/Documents/Projects/fuzzy-ai/fuzzy-model.json");
            
            ArrayList<String> ordemEntrada = modeloFuzzy.getOrdemEntrada();
            for(String nomeEntrada : ordemEntrada) {
                System.out.println("Digite o valor de entrada da variavel " + nomeEntrada + ": ");
                valoresEntrada.add(scanner.nextDouble());
            }
            
            ArrayList<VariavelFuzzyficada> variaveisFuzzyficadas = modeloFuzzy.fuzzyficar(valoresEntrada);
            for(VariavelFuzzyficada variavelFuzzyficada : variaveisFuzzyficadas) {
                System.out.println(variavelFuzzyficada.getVariavelFuzzy().getNome());
                Object[] keys = variavelFuzzyficada.getResultado().keySet().toArray();
                for(int i = 0; i < variavelFuzzyficada.getResultado().size(); i++) {
                    System.out.println(keys[i] + " - " + variavelFuzzyficada.getResultado().get(keys[i].toString()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
    
}
