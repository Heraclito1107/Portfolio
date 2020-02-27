 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalparalelo_heraclito;

import finalparalelo_heraclito.Jugador.EstadoJugador;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 *
 * @author Heraclito
 */
public class Canvas extends JPanel{
    Disco[] discos;
    Jugador j1, j2;
     
    public Canvas(Disco[] discos, Jugador j1, Jugador j2)
    {
        
        setSize(800, 600);
        this.discos = discos;
        this.j1 = j1;
        this.j2 = j2;
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
      for(int i = 0; i < discos.length; i++)
      {
          if(discos[i].dibujable)
          {
            g.setColor(discos[i].color);
            g.fillOval(discos[i].posX, discos[i].posY, 30, 30);//g.drawOval(getWidth()/2+discos[i].posX, -getHeight()/2 +discos[i].posY, 15, 10);
            g.drawString(discos[i].id+ "", discos[i].posX, discos[i].posY);
          }
            
      }
      g.setColor(j1.color);
      g.drawOval(j1.xPos, j1.yPos, 20, 20);
      g.setColor(j2.color);
      g.drawOval(j2.xPos, j2.yPos, 20, 20);
    }

    public void cambioJugador(Jugador j)
    {
        if(j.estado == EstadoJugador.DISPARANDO)
        {
            for(Disco d : discos)
            {
                if(d.dibujable)
                {

                    float distancia = (float)Math.sqrt(Math.pow((j.xPos-d.posX),2) + Math.pow((j.yPos - d.posY),2));
                    if(distancia < 25.0f)
                    {
                        d.counter = 0;
                        j.puntos++;
                        break;
                    }
                }

            }
        }
    }
    
}
