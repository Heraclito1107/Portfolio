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
 * @version 1.5
 */
public class Ray {

    private Vector3D origin;
    private Vector3D direction;

    public Ray(Vector3D origin, Vector3D direction) {
        setOrigin(origin);
        setDirection(direction);
    }

    public static Intersection getIntersectionSphere(Ray ray, Sphere sphere) {
        Vector3D directionSphereRay = Vector3D.substract(ray.getOrigin(), sphere.getPosition());
        double firstP = Vector3D.dotProduct(ray.getDirection(), directionSphereRay);
        double secondP = Math.pow(Vector3D.magnitude(directionSphereRay), 2);
        double intersection = Math.pow(firstP, 2) - secondP + Math.pow(sphere.getRadius(), 2);

        if (intersection < 0) {
            return new Intersection(false);
        }

        double part1 = -firstP + Math.sqrt(intersection);
        double part2 = -firstP - Math.sqrt(intersection);
        Intersection sphereInt = new Intersection(true);
        //if((part1 >= nearPane) && (part2 >= nearPane))
            sphereInt.setDistance(Math.min(part1, part2));
        //else
          //  sphereInt.setDistance(Math.max(part1, part2));
        Vector3D intPosition = Vector3D.add(ray.getOrigin(), Vector3D.scalarProduct((float)sphereInt.getDistance(), ray.getDirection()));
        sphereInt.setIntersectionPosition(intPosition);
        sphereInt.setIntersectionNormal(Vector3D.substract(sphere.getPosition(), intPosition));
        return sphereInt;
    }
    
    public static Intersection getIntersectionTriangle(Ray ray, Object3D object, int index)
    {
        Triangle triangle;
        Vector3D intNormal;
        if (Triangle.class == object.getClass())
            triangle = (Triangle) object;
        else
            triangle = ((Polygon) object).getFaces().get(index);
        float distance;
        
        Intersection triangleInt = new Intersection(false);
        Vector3D intersectionPoint;
        float rayTriangleAngle = Vector3D.dotProduct(ray.getDirection(), triangle.getNormal());
        if (rayTriangleAngle == 0)
            return triangleInt;

        distance =  Vector3D.dotProduct(Vector3D.substract(triangle.getPosition(), ray.getOrigin()), triangle.getNormal())/rayTriangleAngle;
        intersectionPoint = Vector3D.add(Vector3D.scalarProduct(distance, ray.getDirection()), ray.getOrigin());
        float u = (Vector3D.dotProduct(Vector3D.crossProduct(Vector3D.substract(triangle.getVertices()[1], intersectionPoint),Vector3D.substract(triangle.getVertices()[1], triangle.getVertices()[0])), triangle.getNormal()));
        float v = (Vector3D.dotProduct(Vector3D.crossProduct(Vector3D.substract(triangle.getVertices()[0], intersectionPoint),Vector3D.substract(triangle.getVertices()[0], triangle.getVertices()[2])), triangle.getNormal()));
        if((u < 0) || (v < 0))
            return triangleInt;
        u = Vector3D.magnitude(Vector3D.crossProduct(Vector3D.substract(triangle.getVertices()[0], intersectionPoint),Vector3D.substract(triangle.getVertices()[1], intersectionPoint)))/(2*triangle.getArea());
        v = Vector3D.magnitude(Vector3D.crossProduct(Vector3D.substract(triangle.getVertices()[0], intersectionPoint),Vector3D.substract(triangle.getVertices()[2], intersectionPoint)))/(2*triangle.getArea());
        
        float w = 1-u-v;
        if (w < 0)
            return triangleInt;
        if (object.getClass() != Triangle.class)
        {
            Vector3D uNormal = ((Polygon) object).getVerts().get(((Polygon) object).getVerts().indexOf(triangle.getVertices()[2])).getVertexNormal();
            Vector3D vNormal = ((Polygon) object).getVerts().get(((Polygon) object).getVerts().indexOf(triangle.getVertices()[1])).getVertexNormal();
            Vector3D wNormal = ((Polygon) object).getVerts().get(((Polygon) object).getVerts().indexOf(triangle.getVertices()[0])).getVertexNormal();
            intNormal = Vector3D.add(Vector3D.add(Vector3D.scalarProduct(u, uNormal), Vector3D.scalarProduct(v, vNormal)), Vector3D.scalarProduct(w, wNormal));
        }
        else
        {
            intNormal = triangle.getNormal();  
        }
        triangleInt.setIntersection(true);
        triangleInt.setDistance(distance);
        triangleInt.setIntersectionPosition(intersectionPoint);
        triangleInt.setIntersectionNormal(Vector3D.normalize(intNormal));
        return triangleInt;
    }
    
    public static Color getDiffuseColor(Color color, Light light, Intersection intersection, float ambient)
    {
        float angle = (Vector3D.dotProduct(intersection.getIntersectionNormal(), light.getDirection(intersection.getIntersectionPosition()))/255f);
        return new Color(MathGenerals.clamp((int)(color.getRed()*((light.getColor().getRed()*angle*light.getIntensity(intersection.getIntersectionPosition())) + ambient)), 0, 255),
                         MathGenerals.clamp((int)(color.getGreen()*((light.getColor().getGreen()*angle*light.getIntensity(intersection.getIntersectionPosition())) + ambient)), 0, 255),
                         MathGenerals.clamp((int)(color.getBlue()*((light.getColor().getBlue()*angle*light.getIntensity(intersection.getIntersectionPosition())) + ambient)), 0, 255));
    }
   
     public static Color getShadowColor(Color color, float ambient)
    {
        return new Color((int)(color.getRed()*ambient),
                         (int)(color.getGreen()*ambient),
                         (int)(color.getBlue()*ambient));
    }
     
    public static Ray refractedRay(Ray incidenceRay, Intersection intersection, float ior)
    {
        float nl = Vector3D.dotProduct(incidenceRay.getDirection(), intersection.getIntersectionNormal());
        float nArg = (float) ((float)(ior*nl)-Math.sqrt((double)(1f-((float)Math.pow((double)ior, 2))*(1f-Math.pow((double)nl, 2)))));
        Vector3D refDirection = Vector3D.add(Vector3D.scalarProduct(ior, incidenceRay.getDirection()),
                                             Vector3D.scalarProduct(nArg, intersection.getIntersectionNormal()));
                                
        return new Ray(intersection.getIntersectionPosition(), refDirection);
    }
    
    public static Ray reflectedRay(Ray incidenceRay, Intersection intersection)
    {
        float nl = (-12f)*Vector3D.dotProduct(incidenceRay.getDirection(), intersection.getIntersectionNormal());
        Vector3D refDirection = Vector3D.add(incidenceRay.getDirection(), 
                                             Vector3D.scalarProduct(nl, intersection.getIntersectionNormal()));
        return new Ray(intersection.getIntersectionPosition(), refDirection);
    }
    public Vector3D getOrigin() 
    {
        return origin;
    }

    public void setOrigin(Vector3D origin) 
    {
        this.origin = origin;
    }

    public Vector3D getDirection() 
    {
        float mag = Vector3D.magnitude(direction);
        return new Vector3D(direction.getX()/mag, direction.getY()/mag, direction.getZ()/mag);
    }

    public void setDirection(Vector3D direction) 
    {
        this.direction = direction;
    }
    
    

}
