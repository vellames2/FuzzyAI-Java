package fuzzyai;

import fuzzyai.abstracoes.AFuzzyficacao;
import fuzzyai.fuzzyficacao.FuzzyficacaoPadrao;
import fuzzyai.utils.Coordenada;
import fuzzyai.utils.VariavelFuzzyficada;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

public final class FuzzyAI {
    
    public static int largura = 800;
    public static int altura = 600;

    public static void main(String[] args){
        // Tela da aplicação
        JFrame telaPrincipal = FuzzyAI.criarTelaPrincipal();
        
        // Painel principal da aplicação
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        
        // Painel com as ações de carregar JSON
        JPanel painelImportarJSON = FuzzyAI.painelImportarJSON();
        
        // Painel de ajuda
        JPanel painelAjuda = FuzzyAI.painelAjuda();
        
        // Painel com botões
        JPanel painelBotoes = new JPanel();
        JButton btnImportarJSON = new JButton("Importar JSON");
        btnImportarJSON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BorderLayout layout = (BorderLayout) painelPrincipal.getLayout();
                Component componente = layout.getLayoutComponent(BorderLayout.CENTER);
                if(componente != null) {
                    painelPrincipal.remove(componente);
                }

                painelPrincipal.add(painelImportarJSON, BorderLayout.CENTER);
                painelPrincipal.revalidate();
                painelPrincipal.repaint();                
            }
        });
        
        JButton btnAjuda = new JButton("Ajuda");
        btnAjuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BorderLayout layout = (BorderLayout) painelPrincipal.getLayout();
                Component componente = layout.getLayoutComponent(BorderLayout.CENTER);
                if(componente != null) {
                    painelPrincipal.remove(componente);
                }

                painelPrincipal.add(painelAjuda, BorderLayout.CENTER);
                painelPrincipal.revalidate();
                painelPrincipal.repaint();
            }
        });
        painelBotoes.add(btnImportarJSON);
        painelBotoes.add(btnAjuda);
        
        // Adiciona paineis ao painel principal
        painelPrincipal.add(painelBotoes, BorderLayout.WEST);
        
        
        // Adiciona painel prinicpal ao JFrame
        telaPrincipal.add(painelPrincipal);
        telaPrincipal.setVisible(true);
        
        
        /*ArrayList<Double> valoresEntrada = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        try {
            ModeloFuzzy modeloFuzzy = new ModeloFuzzy();
            modeloFuzzy.carregar("/home/vellames/Documents/Projects/fuzzy-ai/fuzzy-model.json");
            
            ArrayList<String> ordemEntrada = modeloFuzzy.getOrdemEntrada();
            for(String nomeEntrada : ordemEntrada) {
                System.out.println("Digite o valor de entrada da variavel " + nomeEntrada + ": ");
                valoresEntrada.add(scanner.nextDouble());
            }
            
            // Inferencia
            AFuzzyficacao fuzzyficacao = new FuzzyficacaoPadrao(modeloFuzzy);
            ArrayList<VariavelFuzzyficada> variaveisFuzzyficadas = fuzzyficacao.fuzzyficar(valoresEntrada);
            for(VariavelFuzzyficada variavelFuzzyficada : variaveisFuzzyficadas) {
                System.out.println(variavelFuzzyficada.getVariavelFuzzy().getNome());
                Object[] keys = variavelFuzzyficada.getResultado().keySet().toArray();
                for(int i = 0; i < variavelFuzzyficada.getResultado().size(); i++) {
                    System.out.println(keys[i] + " - " + variavelFuzzyficada.getResultado().get(keys[i].toString()));
                }
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    
    public static JFrame criarTelaPrincipal() {
        JFrame telaPrincipal = new JFrame("Sistema Fuzzy");
        telaPrincipal.setSize(largura, altura);        
        telaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        telaPrincipal.setResizable(false);
        return telaPrincipal;
    }
   
    /**
     * Monta o painel de importação do JSON
     * @return Retorna o painel do JSON
     */
    public static JPanel painelImportarJSON() {
        JPanel importarJSON = new JPanel();
        
        // Texto
        JLabel textoImportacao = new JLabel("Importar JSON");
        
        // Input para adicionar o caminho do JSON
        JTextField inputCaminhoJSON = new JTextField(40);
        inputCaminhoJSON.setEditable(false);
        
        // Botão de importação
        JButton btnImportar = new JButton("Importar");
        btnImportar.setEnabled(false);
        btnImportar.addActionListener(new ActionListener() {
            // Carrega os campos de input baseados no JSON
            @Override
            public void actionPerformed(ActionEvent e) {
                String caminhoJSON = inputCaminhoJSON.getText();
                // Verifica se o caminho é valido
                
            }
        });
         
        // Botão para procurar arquivo
        JButton btnProcurar = new JButton("Procurar");
        btnProcurar.addActionListener(new ActionListener() {
            // Evento de clique
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre o selecionador de arquivo
                JFileChooser selecionarArquivo = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int retorno = selecionarArquivo.showOpenDialog(null);
                if(retorno == JFileChooser.APPROVE_OPTION) {
                    File arquivoSelecionado = selecionarArquivo.getSelectedFile();
                    String caminhoAbsoluto = arquivoSelecionado.getAbsolutePath();
                    String nome = arquivoSelecionado.getName();
                    
                    // Verifica a extensão do arquivo, se valida, seta no Jtextinput
                    int i = nome.lastIndexOf('.');
                    if(i > 0) {
                        String extensao = nome.substring(i + 1);
                        if(extensao.toLowerCase().equals("json")) {
                            inputCaminhoJSON.setText(caminhoAbsoluto);
                            btnImportar.setEnabled(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Extensão invalida, selecione um arquivo JSON", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "O arquivo não possui extensão", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        // Adiciona elementos ao painel
        importarJSON.add(textoImportacao);
        importarJSON.add(inputCaminhoJSON);
        importarJSON.add(btnProcurar);
        importarJSON.add(btnImportar);
        
        return importarJSON;
    }
    
    public static JPanel painelAjuda() {
        JPanel painelAjuda = new JPanel();
        
        JLabel label = new JLabel("Ajuda");
        
        painelAjuda.add(label);
        
        return painelAjuda;
    }
}
