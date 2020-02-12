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
 * @version 1.6
 */
public class DirectionalLight extends Light{
    private float intensity;
    private Vector3D direction;
    
    public DirectionalLight(Vector3D direction, Color color, float intensity) 
    {
        super(intensity, new Vector3D(0.0f, 0.0f, 0.0f), color);
        this.direction = Vector3D.normalize(direction);
        this.intensity = intensity;
    }
  
    @Override
    public float getIntensity(Vector3D intPoint)
    {
        return this.intensity;
    }
    
    @Override
    public Vector3D getDirection(Vector3D intersectionPoint) {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = Vector3D.normalize(direction);
    }
}
