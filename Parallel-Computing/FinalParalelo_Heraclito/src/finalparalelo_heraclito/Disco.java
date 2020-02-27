/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalparalelo_heraclito;

import java.awt.Color;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heraclito
 */
public class Disco extends Observable implements Runnable{
    public static enum EstadoDisco {ESPERANDO, PANTALLA, MURIENDO, MUERTO};
    int posX, posY, id;
    boolean dibujable;
    EstadoDisco estado;
    Color color;
    Random rand;
    int counter;
    Semaphore sem;
    Marcador marcador;
    public Disco(int id, Semaphore s, Marcador m)
    {
        cambiarEstado(EstadoDisco.ESPERANDO);
        this.sem = s;
        this.marcador = m;
        dibujable = false;
        rand = new Random();
        this.id = id;
        posX = rand.nextInt(770);
        posY = rand.nextInt(580);
        counter = 5 + rand.nextInt(10);
        color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }
    @Override
    public void run()
    {
        cambiarEstado(EstadoDisco.ESPERANDO);
        counter *=2;
        try {
            sem.acquire();
            Thread.sleep(rand.nextInt(1000));
            cambiarEstado(EstadoDisco.PANTALLA);
            dibujable = true;
            while(counter > 0)
        {
            cambiarPos();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Disco.class.getName()).log(Level.SEVERE, null, ex);
            }
            counter--; 
            if(counter < 6)
            {
                color = Color.RED;
                cambiarEstado(EstadoDisco.MURIENDO);
            }
        }
        } catch (InterruptedException ex) {
            Logger.getLogger(Disco.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            dibujable = false;
            sem.release();
        }
        cambiarEstado(EstadoDisco.MUERTO);
        marcador.actualizarMarcador();
    }
    
    public void cambiarPos()
    {
        posX += -30 + rand.nextInt(60);
        posY += -20 + rand.nextInt(40);
        if(posX < 0)
            posX = 0;
        if(posX > 770)
            posX = 770;
        if(posY < 0)
            posY = 0;
        if(posY > 580)
            posY = 580;
        this.setChanged();
        this.notifyObservers();
    }
    
    public void cambiarEstado(EstadoDisco nuevo)
    {
        estado = nuevo;
        this.setChanged();
        this.notifyObservers();
    }
    
    public int estadoNum()
    {
        switch(estado)
        {
            case ESPERANDO:
                return 0;
            case PANTALLA:
                return 1;
            case MURIENDO:
                return 2;
            case MUERTO:
                return 3;
            default:
                return 3;
        }
    }
    
    
}
