/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer;

/**
 *
 * @author Heraclito
 * @version 2.0
 */
public class Intersection {
    private double distance;
    private Vector3D intersectionNormal;
    private Boolean isIntersection;
    private Vector3D intersectionPosition;

    public Intersection(Boolean isIntersection) {
        this.isIntersection = isIntersection;
        if (!isIntersection)
        {
            this.distance = -1;
            this.intersectionNormal = null;
            this.intersectionPosition = null;
        }
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Vector3D getIntersectionNormal() {
        return intersectionNormal;
    }

    public void setIntersectionNormal(Vector3D intersectionNormal) {
        this.intersectionNormal = Vector3D.normalize(intersectionNormal);
    }

    public Boolean isIntersection() {
        return isIntersection;
    }

    public void setIntersection(Boolean isIntersection) {
        this.isIntersection = isIntersection;
    }

    public Vector3D getIntersectionPosition() {
        return intersectionPosition;
    }

    public void setIntersectionPosition(Vector3D intersectionPosition) {
        this.intersectionPosition = intersectionPosition;
    }
    
    
}
