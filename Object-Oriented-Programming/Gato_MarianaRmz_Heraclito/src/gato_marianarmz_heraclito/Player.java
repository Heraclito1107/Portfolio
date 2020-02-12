/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gato_marianarmz_heraclito;

/**
 *
 * @author CIAC
 */
public class Player {
    private String id;
    public int[][] ownedSquares = new int[5][2];
    private boolean isPlaying;
    
    public Player (String id)
    {
        this.id = id;
        this.isPlaying = false;
    }

    public String getId() 
    {
        return id;
    }

    public boolean isIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}
