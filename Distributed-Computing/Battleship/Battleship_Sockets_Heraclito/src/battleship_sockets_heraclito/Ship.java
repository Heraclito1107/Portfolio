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
public class Ship {
    private int dimension;
    private String name;
    private char orientation;
    private Coordinate[] location;
    private boolean destroyed;
    private int damage;

    public Ship(int dimension, String name) {
        this.dimension = dimension;
        this.name = name;
        location = new Coordinate[dimension];
        destroyed = false;
        damage = 0;
        
    }
    
    public boolean receiveDamage(Coordinate shot)
    {
        if(destroyed)
            return false;
        for (Coordinate location1 : location) {
            if ((location1.x == shot.x) && (location1.y == shot.y)) {
                damage++;
                if(damage == dimension)
                    destroyed = true;
                return true;
            }
        }
        return false;
    }

    public char getOrientation() {
        return orientation;
    }

    public void setPosition(char orientation, Coordinate startCoord) {
        this.orientation = orientation;
        setLocation(startCoord);
    }

    public Coordinate[] getLocation() {
        return location;
    }

    private void setLocation(Coordinate startCoord) 
    {
        for(int i = 0; i < dimension; i++)
        {
            if(orientation == 'h')
            {
                location[i] = new Coordinate(startCoord.x + i, startCoord.y);
            }
            else
            {
                location[i] = new Coordinate(startCoord.x, startCoord.y + i);
            }
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public int getDimension() {
        return dimension;
    }

    public String getName() {
        return name;
    }

}
