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
 * @version 2.0
 */
public class PointLight extends Light
{
    
    public PointLight(Vector3D position, Color color, float intensity) {
        super(intensity, position, color);
    }
    
    public Vector3D getDirection(Vector3D intersectionPoint)
    {
        return Vector3D.normalize(Vector3D.substract(intersectionPoint, this.getPosition()));
    }
}
