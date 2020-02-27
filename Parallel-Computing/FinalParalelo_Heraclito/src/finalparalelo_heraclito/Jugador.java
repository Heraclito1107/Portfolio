/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalparalelo_heraclito;

import java.awt.Color;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Heraclito
 */
public class Jugador extends Observable implements Runnable{

    public static enum EstadoJugador {ESPERANDO, MOVIENDO, DISPARANDO, MUERTO};
    Color color;
    int xPos, yPos, puntos, id;
    String nombre;
    EstadoJugador estado;
    Marcador marcador;
    public Jugador(String n, Color c, int x, int y, int id, Marcador m)
    {
        this.marcador = m;
        this.estado = EstadoJugador.ESPERANDO;
        this.nombre = n;
        this.color = c;
        this.xPos = x;
        this.yPos = y;
        this.id = id;
        puntos = 0;
    }
    
    public void run() {
        while(marcador.leerMarcador() > 0)
        {
            cambiarEstado(EstadoJugador.ESPERANDO);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        cambiarEstado(EstadoJugador.MUERTO);
    }
    public void mover(int dir)
    {
       cambiarEstado(EstadoJugador.MOVIENDO);
       switch(dir)
       {
           case 0: //abajo
               if(yPos < 590)
               {
                   yPos+=5;
               }
               break;
           case 1: //derecha
               if(xPos < 790)
               {
                   xPos+=5;
               }
               break;
           case 2: //arriba
               if(yPos > 10)
               {
                   yPos-=5;
               }
               break;
           case 3: //izquierda
               if(xPos > 10)
               {
                   xPos-=5;
               }
               break;
       }
    }
    public void cambiarEstado(EstadoJugador nuevo)
    {
        estado = nuevo;
        this.setChanged();
        this.notifyObservers();
    }
    
    public void disparar()
    {
        cambiarEstado(EstadoJugador.DISPARANDO);
    }
    
    public int estadoNum()
    {
        switch(estado)
        {
            case ESPERANDO:
                return 0;
            case MOVIENDO:
                return 1;
            case DISPARANDO:
                return 2;
            case MUERTO:
                return 3;
            default:
                return 0;
        }
    }
}
