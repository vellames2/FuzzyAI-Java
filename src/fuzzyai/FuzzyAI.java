package fuzzyai;

import fuzzyai.abstracoes.AFuzzyficacao;
import fuzzyai.abstracoes.IInferencia;
import fuzzyai.fuzzyficacao.FuzzyficacaoPadrao;
import fuzzyai.inferencia.InferenciaPadrao;
import fuzzyai.inferencia.VariavelFuzzyficada;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
import java.text.ParseException;
import java.util.List;
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
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.ipadx = 30;
        constraints.ipady = 10;
        constraints.insets = new Insets(5, 10, 5, 0);
        constraints.anchor = GridBagConstraints.NORTHWEST;
        
        JPanel painelBotoes = new JPanel(new GridBagLayout());
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
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        painelBotoes.add(btnImportarJSON, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        painelBotoes.add(btnAjuda, constraints);
        
        // Adiciona paineis ao painel principal
        painelPrincipal.add(painelBotoes, BorderLayout.WEST);
        
        
        // Adiciona painel prinicpal ao JFrame
        telaPrincipal.add(painelPrincipal);
        telaPrincipal.setVisible(true);
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
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 0, 0, 0);
        constraints.anchor = GridBagConstraints.NORTH;
        JPanel importarJSON = new JPanel(new GridBagLayout());
        
        // Texto
        JLabel textoImportacao = new JLabel("Importar JSON");
        
        // Input para adicionar o caminho do JSON
        JTextField inputCaminhoJSON = new JTextField(40);
        inputCaminhoJSON.setText("/home/vellames/Documents/Faculdade/Inteligencia Artificial/FuzzyAI/FuzzyAI/config.min.json");
        inputCaminhoJSON.setEditable(false);
        
        // Botão de importação
        JButton btnImportar = new JButton("Importar");
        //btnImportar.setEnabled(false);
        btnImportar.addActionListener(new ActionListener() {
            // Carrega os campos de input baseados no JSON
            @Override
            public void actionPerformed(ActionEvent e) {
                String caminhoJSON = inputCaminhoJSON.getText();
                FuzzyAI.abrirTelaFuzzy(caminhoJSON);
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
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0;
        importarJSON.add(textoImportacao, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 1;
        importarJSON.add(inputCaminhoJSON, constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weighty = 1;
        constraints.insets = new Insets(0, 5, 0, 0);
        importarJSON.add(btnProcurar, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weighty = 45;
        importarJSON.add(btnImportar, constraints);
        
        return importarJSON;
    }
    
    public static JPanel painelAjuda() {
        JPanel painelAjuda = new JPanel();
        
        JLabel label = new JLabel("Ajuda");
        
        painelAjuda.add(label);
        
        return painelAjuda;
    }
    
    public static void abrirTelaFuzzy(String caminho){
        // Variaveis de controle
        JPanel painelFuzzy = new JPanel();
        painelFuzzy.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        ModeloFuzzy modeloFuzzy = new ModeloFuzzy();
        List<JTextField> campos = new ArrayList<>();
        
        // Carrega os campos
        try {
            modeloFuzzy.carregar(caminho);
        } catch (Exception e) {
            
        }
        
        // Adiciona o label e os campos no panel
        for(String campo : modeloFuzzy.getOrdemEntrada()) {
            JLabel jlabel = new JLabel(campo);
            JTextField textField = new JTextField(17);
            campos.add(textField);
            painelFuzzy.add(jlabel);
            painelFuzzy.add(textField);
        }
        
        // Cria JFrame
        JFrame telaFuzzy = new JFrame("Inserir Valores");
        telaFuzzy.setSize(largura, altura);        
        telaFuzzy.setResizable(false);
        
        // Cria Painel com os botões
        JPanel painelAcoes = new JPanel();
        JButton enviar = new JButton("Enviar");
        enviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean possuiErros = false;
                List<Double> valoresEntrada = new ArrayList<>();
                
                // Validação dos campos e inserção no array de valores de entrada;
                for(int i = 0; i < campos.size(); i++) {
                    // Verifica se os campos estão preenchidos
                    JTextField campo = campos.get(i);
                    if(campo.getText().isEmpty()) {
                        String msg = "Preencha o campo " + modeloFuzzy.getOrdemEntrada().get(i);
                        JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
                        possuiErros = true;
                        break;
                    }
                    
                    // Verifica se os campos são double
                    try {
                        valoresEntrada.add(Double.parseDouble(campo.getText()));
                    } catch (NumberFormatException ex) {
                        String msg = "O campo " + modeloFuzzy.getOrdemEntrada().get(i) + " deve ser numérico.";
                        JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
                        possuiErros = true;
                        break;
                    }
                }
                
                if(possuiErros) {
                    return;
                }
                
                // Fuzzyficação
                try {
                    AFuzzyficacao fuzzyficacao = new FuzzyficacaoPadrao(modeloFuzzy);
                    List<VariavelFuzzyficada> variaveisFuzzyficadas = fuzzyficacao.fuzzyficar(valoresEntrada);
                    String txt = "";
                    for(VariavelFuzzyficada variavelFuzzyficada : variaveisFuzzyficadas) {
                        txt += ("\n" + variavelFuzzyficada.getVariavelFuzzy().getNome() + "\n");
                        Object[] keys = variavelFuzzyficada.getResultado().keySet().toArray();
                        for(int x = 0; x < variavelFuzzyficada.getResultado().size(); x++) {
                            txt += (keys[x] + " - " + variavelFuzzyficada.getResultado().get(keys[x].toString()) + "\n");
                        }
                    }
                    //JOptionPane.showMessageDialog(null, txt, "Fuzzyficação", JOptionPane.INFORMATION_MESSAGE);
                    
                    IInferencia inferencia = new InferenciaPadrao();
                    inferencia.inferir(variaveisFuzzyficadas, modeloFuzzy);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaFuzzy.dispose();
            }
        });
        painelAcoes.add(enviar);
        painelAcoes.add(cancelar);
        
        // Cria painel que agrupa todos os layouts
        JPanel painelInserirValores = new JPanel(new BorderLayout());
        painelInserirValores.add(painelFuzzy, BorderLayout.CENTER);
        painelInserirValores.add(painelAcoes, BorderLayout.SOUTH);
        
        telaFuzzy.add(painelInserirValores);
        
        // Exibe JFrame
        telaFuzzy.setVisible(true);
    }
}
