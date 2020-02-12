/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Heraclito
 * @version 2.0
 */
public class Vector3D 
{
    private float x, y, z;
    private Set<Vector3D> triangleNormals;
    private Vector3D vertexNormal;

    public Vector3D(float x, float y, float z) 
    {
        this.triangleNormals = new HashSet<Vector3D>();
        setX(x);
        setY(y);
        setZ(z);
    }

    public static float dotProduct(Vector3D vectorA, Vector3D vectorB) 
    {
        return (vectorA.x * vectorB.x) + (vectorA.y * vectorB.y) + (vectorA.z * vectorB.z);
    }

    public static float magnitude(Vector3D vectorA) 
    {
        return (float) Math.sqrt(dotProduct(vectorA, vectorA));
    }
    
    public static Vector3D crossProduct(Vector3D vectorA, Vector3D vectorB)
    {
        return new Vector3D((vectorA.getY()*vectorB.getZ()-vectorB.getY()*vectorA.getZ()),
                            -(vectorA.getX()*vectorB.getZ()-vectorB.getX()*vectorA.getZ()),
                            (vectorA.getX()*vectorB.getY()-vectorB.getX()*vectorA.getY()));
    }

    public static Vector3D add(Vector3D vectorA, Vector3D vectorB) 
    {
        return new Vector3D(vectorA.x + vectorB.x, vectorA.y + vectorB.y, vectorA.z + vectorB.z);
    }
    
    public static Vector3D substract(Vector3D vectorA, Vector3D vectorB) 
    {
        return new Vector3D(vectorA.x - vectorB.x, vectorA.y - vectorB.y, vectorA.z - vectorB.z);
    }

    public static Vector3D scalarProduct(float scalar, Vector3D vector)
    {
        return new Vector3D(vector.getX()*scalar, vector.getY()*scalar, vector.getZ()*scalar);
    }
    
    public static Vector3D normalize(Vector3D vector)
    {
        float magn = Vector3D.magnitude(vector);
        return new Vector3D(vector.getX()/magn, vector.getY()/magn, vector.getZ()/magn);  
    }
    
    public float getX() 
    {
        return x;
    }

    public void setX(float x) 
    {
        this.x = x;
    }

    public float getY() 
    {
        return y;
    }

    public void setY(float y) 
    {
        this.y = y;
    }

    public float getZ() 
    {
        return z;
    }

    public void setZ(float z) 
    {
        this.z = z;
    }

    public Set<Vector3D> getTriangleNormals() 
    {
        return triangleNormals;
    }
    
    public void addTriangleNormal(Vector3D tNormal)
    {
        getTriangleNormals().add(tNormal);
        calculateVertexNormal();
    }
    
    private void calculateVertexNormal()
    {
        this.vertexNormal = new Vector3D(0f, 0f, 0f);
        for (int i = 0; i < getTriangleNormals().size(); i++ )
        {
            this.vertexNormal = Vector3D.add(this.vertexNormal, (Vector3D) getTriangleNormals().toArray()[i]);
        }
        this.vertexNormal = Vector3D.normalize(Vector3D.scalarProduct(1.0f/getTriangleNormals().size(), this.vertexNormal));
    }

    public Vector3D getVertexNormal() {
        return vertexNormal;
    }


}
