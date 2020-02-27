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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 *
 * @author Heraclito
 */
public class Tabla extends JFrame implements Observer{
    int rows;
    Object[][] contenido;
    Object[][] contenido2;
    JScrollPane sp, sp2;
    public Tabla(int r)
    {
        this.rows = r;
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        crearTabla();
        setVisible(true);
    }
    
    private void crearTabla()
    {
        contenido = new Object[rows][7];
        contenido2 = new Object[2][6];
        String [] colNames = {"Nombre",
                              "X",
                              "Y",
                              "Esperando", 
                              "En pantalla",
                              "Muriendo",
                              "Muerto"
                            };
        
        String [] colNames2 = {"Jugador",
                               "Puntos",
                               "Esperando", 
                               "Moviendo",
                               "Disparando",
                               "Muerto"};
        for(int i = 0; i < rows; i++)
        {
            contenido[i][0] = "Disco " + i;
        }
        contenido2[0][0] = "Jugador 1";
        contenido2[1][0] = "Jugador 2";
        JTable tabla = new JTable(contenido, colNames);
        JTable tabla2 = new JTable(contenido2, colNames2);
        sp = new JScrollPane(tabla);
        sp2 = new JScrollPane(tabla2);
        tabla.setFillsViewportHeight(true);
        tabla2.setFillsViewportHeight(true);
        
        JTextArea creditos = new JTextArea();
        creditos.setText("Ingeniería en Sistemas y Gráficas Computacionales\n"
                + "Profesores:\n"
                + "Juan Carlos López Pimentel\n"
                + "Javier González Sánchez\n"
                + "Alumno:\n"
                + "Daniel Heráclito Pérez Díaz\n"
                + "0190575\n"
                + "28 de noviembre de 2019"
        );
        creditos.setEditable(false);

        this.add(sp, BorderLayout.NORTH);
        this.add(sp2, BorderLayout.CENTER);
        this.add(creditos, BorderLayout.EAST);
        this.setVisible(false);
        this.setVisible(true);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Disco)
        {
            Disco d = (Disco)o;
            contenido[d.id][1] = d.posX;
            contenido[d.id][2] = d.posY;
            contenido[d.id][3] = "";
            contenido[d.id][4] = "";
            contenido[d.id][5] = "";
            contenido[d.id][6] = "";
            contenido[d.id][d.estadoNum()+3] = "X";
            
        sp.setVisible(false);
        sp.setVisible(true);
        }
        
        if(o instanceof Jugador)
        {
            Jugador j = (Jugador)o;
            contenido2[j.id][1] = j.puntos;
            contenido2[j.id][2] = "";
            contenido2[j.id][3] = "";
            contenido2[j.id][4] = "";
            contenido2[j.id][5] = "";
            contenido2[j.id][j.estadoNum()+2] = "X";
        sp2.setVisible(false);
        sp2.setVisible(true);
        }
    }
}
