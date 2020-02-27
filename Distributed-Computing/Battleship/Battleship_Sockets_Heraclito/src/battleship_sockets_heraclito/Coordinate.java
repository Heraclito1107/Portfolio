/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship_sockets_heraclito;

/**
 *
 * @author Heraclito
 */
public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString()
    {
        return String.format(x + "," + y);
    }
}
