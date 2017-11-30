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
import java.awt.geom.Point2D;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Monta a tela de resultado
 */
public final class TelaResultado extends JFrame {
    /**
     * Construtor da classe da tela de resultado
     * @param modeloFuzzy Modelo fuzzy com os dados carregados
     * @param resultadoFuzzificacao Resultado da fuzzificacao
     * @param resultadoInferencia Resultado da inferencia
     * @param resultadoDefuzzificacao Resultado da defuzzificacao
     */
    public TelaResultado(ModeloFuzzy modeloFuzzy, List<VariavelFuzzyficada> resultadoFuzzificacao, Imagem resultadoInferencia, VariavelFuzzyficada resultadoDefuzzificacao) {
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
        
        // Fuzzificacao
        painelFuzzificacao.add(new JLabel("Fuzzificacao: "));
        for(VariavelFuzzyficada variavelFuzzyficada : resultadoFuzzificacao) {
            painelFuzzificacao.add(Box.createRigidArea(new Dimension(0,10)));
            painelFuzzificacao.add(new JLabel(variavelFuzzyficada.getVariavelFuzzy().getNome()));
            painelFuzzificacao.add(Box.createRigidArea(new Dimension(0,10)));
            Object[] keys = variavelFuzzyficada.getResultado().keySet().toArray();
            for(int x = 0; x < variavelFuzzyficada.getResultado().size(); x++) {
                painelFuzzificacao.add(new JLabel(keys[x] + " - " + variavelFuzzyficada.getResultado().get(keys[x].toString())));
            }
        }
        
        // Inferencia
        painelInferencia.add(new JLabel("Inferencia: "));
        painelInferencia.add(Box.createRigidArea(new Dimension(0,10)));
        for(Point2D.Double ponto : resultadoInferencia.getPontos()) {
            painelInferencia.add(new JLabel("[" + ponto.getX() + " , " + ponto.getY() + "]"));
            painelInferencia.add(Box.createRigidArea(new Dimension(0,10)));
        }
        
        // Defuzzificacao
        painelDefuzzificacao.add(new JLabel("Defuzzificacao: "));
        painelDefuzzificacao.add(Box.createRigidArea(new Dimension(0,10)));
        painelDefuzzificacao.add(new JLabel(resultadoDefuzzificacao.getVariavelFuzzy().getNome()));
        painelDefuzzificacao.add(Box.createRigidArea(new Dimension(0,10)));
        Object[] keys = resultadoDefuzzificacao.getResultado().keySet().toArray();
        for(int x = 0; x < resultadoDefuzzificacao.getResultado().size(); x++) {
            painelDefuzzificacao.add(new JLabel(keys[x] + " - " + resultadoDefuzzificacao.getResultado().get(keys[x].toString())));
        }
        
        JPanel painelCentro = new JPanel();
        painelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        painelCentro.add(painelFuzzificacao);
        painelCentro.add(painelInferencia);
        painelCentro.add(painelDefuzzificacao);
        
        JPanel painelFim = new JPanel();
        painelFim.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton exportar = new JButton("Exportar");
        painelFim.add(exportar);
        
        painel.add(painelCentro, BorderLayout.CENTER);
        painel.add(painelFim, BorderLayout.SOUTH);
        
        this.add(painel);
        this.setVisible(true);
    }
}
