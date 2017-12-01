/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzyai;

import static fuzzyai.FuzzyAI.altura;
import static fuzzyai.FuzzyAI.largura;
import fuzzyai.inferencia.Imagem;
import fuzzyai.inferencia.VariavelFuzzyficada;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Monta a tela de resultado
 */
public final class TelaResultado extends JFrame {
    /**
     * Construtor da classe da tela de resultado
     * @param valoresEntrada Valores usados como entrada de dados
     * @param modeloFuzzy Modelo fuzzy com os dados carregados
     * @param resultadoFuzzificacao Resultado da fuzzificacao
     * @param resultadoInferencia Resultado da inferencia
     * @param resultadoDefuzzificacao Resultado da defuzzificacao
     */
    public TelaResultado(List<Double> valoresEntrada, ModeloFuzzy modeloFuzzy, List<VariavelFuzzyficada> resultadoFuzzificacao, Imagem resultadoInferencia, VariavelFuzzyficada resultadoDefuzzificacao) {
        this.setTitle("Resultado - " + modeloFuzzy.getNome());
        this.setSize(largura, altura);        
        this.setResizable(false);
        
        JPanel painel = new JPanel(new BorderLayout());
       
        JPanel painelFuzzificacao = new JPanel();
        painelFuzzificacao.setLayout(new BoxLayout(painelFuzzificacao, BoxLayout.Y_AXIS));
       
        JPanel painelInferencia = new JPanel();
        painelInferencia.setLayout(new BoxLayout(painelInferencia, BoxLayout.Y_AXIS));
       
        JPanel painelDefuzzificacao = new JPanel();
        painelDefuzzificacao.setLayout(new BoxLayout(painelDefuzzificacao, BoxLayout.Y_AXIS));
        
        String txt = "";
       
        txt += ("+-------------- Valores de Entrada --------------+%n");
        List<String> ordemEntrada = modeloFuzzy.getOrdemEntrada();
        for(int i = 0; i < ordemEntrada.size(); i++) {
            txt += ordemEntrada.get(i) + " - " + valoresEntrada.get(i) + "%n";
        }
        // Fuzzificacao
        txt += ("+--------------Fuzzificação--------------+%n");
        painelFuzzificacao.add(new JLabel("Fuzzificacao: "));
        for(VariavelFuzzyficada variavelFuzzyficada : resultadoFuzzificacao) {
            painelFuzzificacao.add(Box.createRigidArea(new Dimension(0,10)));
            painelFuzzificacao.add(new JLabel(variavelFuzzyficada.getVariavelFuzzy().getNome()));
            //GRAVA NOME DA VARIAVEL
            txt += (variavelFuzzyficada.getVariavelFuzzy().getNome()+"%n");
            painelFuzzificacao.add(Box.createRigidArea(new Dimension(0,10)));
            Object[] keys = variavelFuzzyficada.getResultado().keySet().toArray();
            for(int x = 0; x < variavelFuzzyficada.getResultado().size(); x++) {
                painelFuzzificacao.add(new JLabel(keys[x] + " - " + variavelFuzzyficada.getResultado().get(keys[x].toString())));
                txt += (keys[x] + " - " + variavelFuzzyficada.getResultado().get(keys[x].toString())+"%n");
            }
            txt += ("%n");
        }
        txt += ("+---------------------------------------+%n");
       
        // Inferencia
        txt += ("+--------------Inferencia---------------+%n");
        painelInferencia.add(new JLabel("Inferencia: "));
        painelInferencia.add(Box.createRigidArea(new Dimension(0,10)));
        for(Point2D.Double ponto : resultadoInferencia.getPontos()) {
            painelInferencia.add(new JLabel("[" + ponto.getX() + " , " + ponto.getY() + "]"));
            painelInferencia.add(Box.createRigidArea(new Dimension(0,10)));
            txt += ("[" + ponto.getX() + " , " + ponto.getY() + "]%n");
        }
        txt += ("+---------------------------------------+%n");
       
        // Defuzzificacao
        txt += ("+--------------Defuzzificação-----------+%n");
        painelDefuzzificacao.add(new JLabel("Defuzzificacao: "));
        painelDefuzzificacao.add(Box.createRigidArea(new Dimension(0,10)));
        painelDefuzzificacao.add(new JLabel(resultadoDefuzzificacao.getVariavelFuzzy().getNome()));
        txt += (resultadoDefuzzificacao.getVariavelFuzzy().getNome()+"%n");
        painelDefuzzificacao.add(Box.createRigidArea(new Dimension(0,10)));
        Object[] keys = resultadoDefuzzificacao.getResultado().keySet().toArray();
        for(int x = 0; x < resultadoDefuzzificacao.getResultado().size(); x++) {
            painelDefuzzificacao.add(new JLabel(keys[x] + " - " + resultadoDefuzzificacao.getResultado().get(keys[x].toString())));
            txt += (keys[x] + " - " + resultadoDefuzzificacao.getResultado().get(keys[x].toString())+"%n");
        }
        txt += ("+---------------------------------------+%n");
        //IMPRIME A DATA E HORA DA EXPORTAÇAO
        txt += ("+-----------Arquivo Exportado-----------+%n");        
        Date dataAtual = new Date();
        txt += (dataAtual.toString()+"%n");
        txt += ("+---------------------------------------+%n");
       
        JPanel painelCentro = new JPanel();
        painelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        painelCentro.add(painelFuzzificacao);
        painelCentro.add(painelInferencia);
        painelCentro.add(painelDefuzzificacao);
       
        //BOTAO DE EXPORTAR
        JPanel painelFim = new JPanel();
        painelFim.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton exportar = new JButton("Exportar");
        
        final String txtFinal = txt;
        exportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileWriter arq;
                try {
                    String caminho = System.getProperty("user.dir") + File.separator + "Resultado_Fuzzy" +new Date().getTime()  +".txt";
                    arq = new FileWriter(caminho);
                    PrintWriter gravarArq = new PrintWriter(arq);
                    gravarArq.printf(txtFinal);
                    arq.close();
                    JOptionPane.showMessageDialog(null, "Arquivo foi exportado com sucesso no diretorio " + caminho);
                } catch (IOException ex) {
                    Logger.getLogger(TelaResultado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        painelFim.add(exportar);
       
        painel.add(painelCentro, BorderLayout.CENTER);
        painel.add(painelFim, BorderLayout.SOUTH);
       
        this.add(painel);
        this.setVisible(true);
    }
   
}
