package fuzzyai.teste.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fuzzyai.utils.Reta;
import java.awt.geom.Point2D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cassiano.vellames
 */
public class RetaTeste {
    
    public RetaTeste() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void retaCoeficienteAngular() {
        Point2D.Double inicioReta = new Point2D.Double(2,7);
        Point2D.Double fimReta = new Point2D.Double(-1,-5);
        Reta reta = new Reta(inicioReta, fimReta);
        
        double coeficienteAngular = reta.getCoeficienteAngular();
        assertEquals(coeficienteAngular, 4, 0);
    }
    
    @Test
    public void retaRecuperarPonto() {
        Point2D.Double inicioReta = new Point2D.Double(2,7);
        Point2D.Double fimReta = new Point2D.Double(-1,-5);
        Reta reta = new Reta(inicioReta, fimReta);
        assertEquals(reta.obterPontoYPorX(2), 7 , 0);
        assertEquals(reta.obterPontoYPorX(-1), -5 , 0);
        
        assertEquals(reta.obterPontoXPorY(7), 2 , 0);
        assertEquals(reta.obterPontoXPorY(-5), -1 , 0);
    }
}
