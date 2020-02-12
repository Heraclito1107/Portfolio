/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer;

import java.util.ArrayList;
/**
 *
 * @author Heraclito
 * @version 1.2
 */
public class Polygon extends Object3D
{
    private ArrayList<Vector3D> verts;
    private ArrayList<Triangle> faces;
    private ArrayList<Vector3D> vertNormals;
    public Polygon(Vector3D position, Material material) {
        super(position, material);
        verts = new ArrayList();
        faces = new ArrayList();
    }
    public void addVertex(Vector3D vertex)
    {
        getVerts().add(vertex);
    }
    
    public void addFace(int v1, int v2, int v3)
    {
        Triangle triangle = new Triangle(getMaterial(), getVerts().get(v1), getVerts().get(v2), getVerts().get(v3));
        getFaces().add(triangle);
        getVerts().get(v1).addTriangleNormal(triangle.getNormal());
        getVerts().get(v2).addTriangleNormal(triangle.getNormal());
        getVerts().get(v3).addTriangleNormal(triangle.getNormal());
    }
    
    public ArrayList<Vector3D> getVerts() {
        return verts;
    }

    public void setVerts(ArrayList<Vector3D> verts) {
        this.verts = verts;
    }

    public ArrayList<Triangle> getFaces() {
        return faces;
    }

    public void setFaces(ArrayList<Triangle> faces) {
        this.faces = faces;
    }
    
    @Override
    public void changePosition(Vector3D deltaPosition)
    {
        this.getVerts().forEach((vert) -> {
            Vector3D newPos = Vector3D.add(vert, deltaPosition);
            vert.setX(newPos.getX());
            vert.setY(newPos.getY());
            vert.setZ(newPos.getZ());
        });
        for(Triangle triangle: this.getFaces())
        {
            triangle.setPosition();
        }
    }
}
