/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalparalelo_heraclito;

import java.util.Observable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Heraclito
 */
public class Marcador extends Observable{
    Lock candado = new ReentrantLock();
    int discos;
    public Marcador(int d)
    {
        this.discos = d;
    }
    
    public void actualizarMarcador()
    {
        candado.lock();
        try
        {
            discos--;
            this.setChanged();
            this.notifyObservers();
        } finally{
            candado.unlock();
        }
    }
    
    public synchronized int leerMarcador()
    {
        return discos;
    }
    
    public void iniciar()
    {
        this.setChanged();
        this.notifyObservers();
    }
}
