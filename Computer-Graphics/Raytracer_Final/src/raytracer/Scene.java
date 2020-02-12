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
 * @version 1.5
 */
public class Scene {
    private Camera camera;
    private ArrayList<Light> lights;
    private ArrayList<Object3D> objects;
    private float ambientLight;
    public Scene(){
        setObjects(new ArrayList<Object3D>());
        setLights(new ArrayList<Light>());
    }
    
    public void addObject(Object3D object){
        getObjects().add(object);
    }
    
    public ArrayList<Object3D> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object3D> objects) {
        this.objects = objects;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void addLight(Light light) {
        getLights().add(light);
    }

    public ArrayList<Light> getLights() {
        return lights;
    }

    public void setLights(ArrayList<Light> lights) {
        this.lights = lights;
    }

    public float getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(float ambientLight) {
        this.ambientLight = ambientLight;
    }

    
}
