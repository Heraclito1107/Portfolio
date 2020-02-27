/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalparalelo_heraclito;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Heraclito
 */
public class ControlTeclas implements KeyListener{
    Jugador j1, j2;
    public ControlTeclas(Jugador j1, Jugador j2)
    {
        this.j1 = j1;
        this.j2 = j2;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_UP:
                j2.mover(2);
                break;
            case KeyEvent.VK_RIGHT:
                j2.mover(1);
                break;
            case KeyEvent.VK_DOWN:
                j2.mover(0);
                break;
            case KeyEvent.VK_LEFT:
                j2.mover(3);
                break;
            case KeyEvent.VK_CONTROL:
                j2.disparar();
                break;
            case KeyEvent.VK_W:
                j1.mover(2);
                break;
            case KeyEvent.VK_D:
                j1.mover(1);
                break;
            case KeyEvent.VK_S:
                j1.mover(0);
                break;
            case KeyEvent.VK_A:
                j1.mover(3);
                break;
            case KeyEvent.VK_SPACE:
                j1.disparar();
                break;
            
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
