/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.UP.MCG.coordinates;

/**
 *
 * @author Heraclito
 */
public class Coordinate {
    private float x;
    private float y;

    public Coordinate(int x, int y) {
        this.x = x * 1.00f;
        this.y = y * 1.00f;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    
}
