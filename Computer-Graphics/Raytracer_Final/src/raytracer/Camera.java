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
 * @version 1.2
 */
public class Camera extends Object3D{
    //0 is fovh
    //1 is fovv
    private float[] fieldOfView = new float[2];
    private float defaultZ = 15f;
    private int[] resolution;
    private float nearClippingPane;
    private float farClippingPane;
    public Camera(Vector3D position, float fieldOfViewHorizontal, float fieldOfViewVertical, int widthResolution, int heightResolution, float nearClippingPane, float farClippingPane){
        super(position, new Material(Color.BLACK, false, false, false, true, true));
        setFieldOfViewHorizontal(fieldOfViewHorizontal);
        setFieldOfViewVertical(fieldOfViewVertical);
        setResolution(new int[]{widthResolution, heightResolution});
        setNearClippingPane(nearClippingPane);
        setFarClippingPane(farClippingPane);
    }

    public float getDefaultZ() {
        return defaultZ;
    }

    public void setDefaultZ(float defaultZ) {
        this.defaultZ = defaultZ;
    }

    public int[] getResolution() {
        return resolution;
    }

    public void setResolution(int[] resolution) {
        this.resolution = resolution;
    }

    public float getFieldOfViewHorizontal(){
        return fieldOfView[0];
    }
    
    public float getFieldOfViewVertical(){
        return fieldOfView[1];
    }
    
    public void setFieldOfViewHorizontal(float fov){
        fieldOfView[0] = fov;
    }
    
    public void setFieldOfViewVertical(float fov){
        fieldOfView[1] = fov;
    }
    
    public float[] getFieldOfView() {
        return fieldOfView;
    }
    
    
    public Vector3D[][] calculatePositionsToRay(){
        float angleMaxX = 90 - (getFieldOfViewHorizontal()/2f);
        float radiusMaxX = getNearClippingPane() / (float)Math.cos(Math.toRadians(angleMaxX));
        
        float maxX = (float)Math.sin(Math.toRadians(angleMaxX)) * radiusMaxX;
        float minX = -maxX;
        
        float angleMaxY = 90 - (getFieldOfViewVertical()/2f);
        float radiusMaxY = getNearClippingPane() / (float)Math.cos(Math.toRadians(angleMaxY));
        
        float maxY = (float)Math.sin(Math.toRadians(angleMaxY)) * radiusMaxY;
        float minY = -maxY;
        
        Vector3D[][] positions = new Vector3D[getResolution()[0]][getResolution()[1]];
        for(int x = 0; x < positions.length; x++){
            for(int y = 0; y < positions[x].length; y++){
                float posX = (float)minX + (((float)(maxX-minX)/(float)positions.length) * (float)x);
                float posY = (float)maxY - (((float)(maxY-minY)/(float)positions[x].length) * (float)y);
                float posZ = getNearClippingPane();
                positions[x][y] = new Vector3D(posX, posY, posZ);
            }
        }
        
        return positions;
    }

    public float getNearClippingPane() {
        return nearClippingPane;
    }

    public void setNearClippingPane(float nearClippingPane) {
        this.nearClippingPane = nearClippingPane;
    }

    public float getFarClippingPane() {
        return farClippingPane;
    }

    public void setFarClippingPane(float farClippingPane) {
        this.farClippingPane = farClippingPane;
    }
    
    
}
