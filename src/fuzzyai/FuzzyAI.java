package fuzzyai;

import java.util.ArrayList;
import java.util.Scanner;

public class FuzzyAI {

    public static void main(String[] args){
        ArrayList<Double> valoresEntrada = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        try {
            ModeloFuzzy modeloFuzzy = new ModeloFuzzy("Modelo Fuzzy");
            modeloFuzzy.carregar("/home/vellames/Documents/Projects/fuzzy-ai/fuzzy-model.json");
            
            ArrayList<String> ordemEntrada = modeloFuzzy.getOrdemEntrada();
            for(String nomeEntrada : ordemEntrada) {
                System.out.println("Digite o valor de entrada da variavel " + nomeEntrada + ": ");
                valoresEntrada.add(scanner.nextDouble());
            }
            
            
            modeloFuzzy.fuzzyficar(valoresEntrada);  
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
    
}
