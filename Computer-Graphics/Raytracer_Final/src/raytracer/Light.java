/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer;

import java.awt.Color;

/**
 *
 * @author Heraclito
 * @version 2.3
 */
public class Light extends Object3D{
    private float intensity;

    public Light(float intensity, Vector3D position, Color color) {
        super(position, new Material(color, false, false, false, true, true));
        this.intensity = intensity;
    }

    public float getIntensity(Vector3D intPoint) {
        float distance = Vector3D.magnitude(Vector3D.substract(this.getPosition(), intPoint));
        //System.out.println(distance);
        if (distance > 1f)
            return intensity/((float)Math.pow(distance, 2));
            //return (float)Math.pow(intensity, (1f/(2f*distance)));
        else
            return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
    
    public Vector3D getDirection(Vector3D intersectionPoint)
    {return null;}
}
