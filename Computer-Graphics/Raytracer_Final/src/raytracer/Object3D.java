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
public abstract class Object3D {
    private Vector3D position;
    private Material material;
    public Object3D(Vector3D position, Material material){
        this.position = position;
        this.material = material;
    }
    
    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Color getColor() {
        return material.getColor();
    }

    public void setColor(Color color) {
        material.setColor(color);
    }
    
    public void changePosition(Vector3D deltaPosition)
    {
        setPosition(Vector3D.add(deltaPosition, position));
    }
    
    public void assignMaterial(Material newMaterial)
    {
        this.material = newMaterial;
    }

    public Material getMaterial() {
        return material;
    }
    
    
}
