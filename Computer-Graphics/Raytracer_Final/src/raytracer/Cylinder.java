/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer;

/**
 *
 * @author Heraclito
 * @version 1.4
 */
public class Cylinder extends Polygon{
    private float height;
    private float radius;
    private int sides;
    private int segments;

    public Cylinder(float height, float radius, Vector3D position, Material material, int sides, int segments) {
        super(position, material);
        this.height = height;
        this.radius = radius;
        this.sides = sides;
        this.segments = segments;  
        createGeometry();
    }
    
    private void createGeometry()
    {
        float segStep = (height/(getSegments()*1.00f));
        float angleStep = 2*(float)Math.PI/(getSides()*1.00f);
        this.addVertex(new Vector3D(getPosition().getX(), getPosition().getY()- (height/2), getPosition().getZ()));
        for (int i = 0; i <= getSegments(); i++)
        {
            for (int j = 1; j <= getSides(); j++)
            {
                this.addVertex(Vector3D.add(new Vector3D(radius*(float)Math.cos(j*angleStep), segStep*i, radius*(float)Math.sin(j*angleStep)), getVerts().get(0)));
            }
        }
        
        this.addVertex(new Vector3D(getPosition().getX(), getPosition().getY()+(height/2), getPosition().getZ()));
        
        for (int i = 1; i<getSides(); i++)
        {
            addFace(i, i+1, 0);
            addFace(getSegments()*getSides()+i, getSegments()*getSides()+i+1, (getSegments()+1)*getSides()+1);
        }
        
        addFace(getSides(), 1, 0);
        addFace(((getSegments()+1)*getSides()), (getSegments()*getSides())+1, ((getSegments()+1)*getSides())+1);
        
        for (int i = 0; i < getSegments(); i++)
        {
            for (int j = 1; j < getSides(); j++)
            {
                addFace((i*getSides())+j+1, (i+1)*getSides()+j, (i*getSides())+j);
                addFace((i*getSides())+j+1, (i+1)*getSides()+j+1, (i+1)*getSides()+j);
            }
            addFace((i*getSides())+1, (i+2)*getSides(), (i+1)*getSides());
            addFace((i*getSides())+1, (i+1)*getSides()+1, (i+2)*getSides());
        }
    }

    public int getSides() {
        return sides;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }

    public int getSegments() {
        return segments;
    }

    public void setSegments(int segments) {
        this.segments = segments;
    }
    /*@Override
    public void addVertex(Vector3D vertex)
    {
        getVerts().add(vertex);
    }*/
}
