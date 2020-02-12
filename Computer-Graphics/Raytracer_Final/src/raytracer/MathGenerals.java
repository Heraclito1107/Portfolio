/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer;

/**
 *
 * @author Heraclito
 * @version 1.0
 */
public abstract class MathGenerals {
    
    public static int clamp(int number, int min, int max)
    {
        if(number < min)
            return min;
        if (number > max)
            return max;
        return number;
    }
}
