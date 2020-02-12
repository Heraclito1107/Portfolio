/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer;

/**
 *
 * @author Jafet
 * @version 1.0
 * @author Heraclito
 * @version 2.0
 */
public class Sphere extends Object3D{
    private float radius;

    public Sphere(Vector3D center, float radius, Material material){
        super(center, material);
        setRadius(radius);
    }
    
    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
    
    
}
