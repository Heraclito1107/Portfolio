/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalparalelo_heraclito;

import java.awt.Color;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Heraclito
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String totalStr = JOptionPane.showInputDialog("Numero total de discos");
        int total = Integer.parseInt(totalStr);
        int cant;
        do
        {
            String cantStr = JOptionPane.showInputDialog("Numero de discos en pantalla");
            cant = Integer.parseInt(cantStr);
        }while (cant > total);
        
        Tabla tabla = new Tabla(total);
        Semaphore sem = new Semaphore(cant);        
        Disco[] discos = new Disco[total];
        Marcador marcador = new Marcador(total);
        Jugador j1 = new Jugador("Jugador 1", Color.MAGENTA, 200, 300, 0, marcador);
        Jugador j2 = new Jugador("Jugador 2", Color.BLUE, 600, 300, 1, marcador);
        
        j1.addObserver(tabla);
        j2.addObserver(tabla);
        
        Thread t1 = new Thread(j1);
        Thread t2 = new Thread(j2);
        t1.start();
        t2.start();
        
        for(int i = 0; i < total; i++)
        {
            discos[i] = new Disco(i, sem, marcador);
            discos[i].addObserver(tabla);
            Thread t = new Thread(discos[i]);
            t.start();
        }
        
        ControlTeclas ct = new ControlTeclas(j1, j2);
        Canvas canvas = new Canvas(discos, j1, j2);
        Ventana ventana = new Ventana(canvas);
        ventana.addKeyListener(ct);
        marcador.addObserver(ventana);
        j1.addObserver(ventana);
        j2.addObserver(ventana);
        marcador.iniciar();
        Reproductor r = new Reproductor();
        r.start();
        
        while(marcador.leerMarcador() > 0)
        {
            canvas.repaint();
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        canvas.repaint();
        if(j1.puntos > j2.puntos)
        {
            JOptionPane.showMessageDialog(null, "Gana el jugador 1");
        }
        if(j1.puntos < j2.puntos)
        {
            JOptionPane.showMessageDialog(null, "Gana el jugador 2");
        }
        if(j1.puntos == j2.puntos)
        {
            JOptionPane.showMessageDialog(null, "Empate");
        }
        r.parar();
        ventana.setVisible(false);
    }
    
}
