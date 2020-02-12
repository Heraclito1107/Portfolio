/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer;

/**
 *
 * @author Heraclito
 * @version 2.3
 */
public class Triangle extends Object3D {
    private float area;
    private Vector3D normal;
    private Vector3D[] vertices = new Vector3D[3];
    /**
     * 
     * @param color
     * @param vert1
     * @param vert2
     * @param vert3 
     */
    public Triangle(Material material, Vector3D vert1, Vector3D vert2, Vector3D vert3) {
        super(new Vector3D(0f, 0f, 0f), material);
        vertices[0] = vert1;
        vertices[1] = vert2;
        vertices[2] = vert3;
        this.setPosition();
        setNormal();
        setArea();
    }
    
    public void setPosition()
    {
        Vector3D position = Vector3D.scalarProduct((1/3f), Vector3D.add(Vector3D.add(vertices[0], vertices[1]), vertices[2]));
        super.setPosition(position);
    }
    private void setArea()
    {
        area = Vector3D.magnitude(normal)/2;
    }
    private void setNormal()
    {
        normal = Vector3D.crossProduct(Vector3D.substract(vertices[0], vertices[1]), Vector3D.substract(vertices[1], vertices[2]));
    }

    public Vector3D getNormal() {
        float magnitude = Vector3D.magnitude(normal);
        return new Vector3D(normal.getX()/magnitude, normal.getY()/magnitude, normal.getZ()/magnitude);
    }

    public Vector3D[] getVertices() {
        return vertices;
    }

    public float getArea() {
        return area;
    }
}
