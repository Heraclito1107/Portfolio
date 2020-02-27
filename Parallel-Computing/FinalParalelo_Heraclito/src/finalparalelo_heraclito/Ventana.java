/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalparalelo_heraclito;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Heraclito
 */
public class Ventana extends JFrame implements Observer{
    
    Canvas canvas;
    JLabel j1Lab, j2Lab, dLab;
    JPanel jp;
    public Ventana(Canvas c)
    {  
        this.setTitle("Shot the 'Disco'!!!");
        this.canvas = c;
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp = new JPanel();
        j1Lab = new JLabel("Jugador 1: ");
        j2Lab = new JLabel("Jugador 2: ");
        dLab = new JLabel("Discos restantes: ");
        jp.add(j1Lab, BorderLayout.EAST);
        jp.add(dLab, BorderLayout.CENTER);
        jp.add(j2Lab, BorderLayout.WEST);
        add(jp, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
       
        if(o instanceof Jugador)
        {
            Jugador j = (Jugador)o;
            canvas.cambioJugador(j);
            if(j.id == 0)
            {
                j1Lab.setText("Jugador 1: " + j.puntos);
            }
            if(j.id == 1)
            {
                j2Lab.setText("Jugador 2: " + j.puntos);
            }
        }
        if(o instanceof Marcador)
        {
            dLab.setText("Discos restantes: " + ((Marcador) o).leerMarcador());
        }
    }
    
}
