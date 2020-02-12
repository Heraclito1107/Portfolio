    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Heraclito
 * @version 3.2
 */
public class Raytracer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Material defMat = new Material(Color.YELLOW, false, //refract
                                                  false, //reflect
                                                  false, //transparent
                                                  true, //specular
                                                  true);
        Material mat2 = new Material(Color.BLUE, false, true, false, true, true);
        Material mat3 = new Material(Color.RED, true, false, true, true, true);
        Material floorMat = new Material(Color.WHITE, false, true, false, false, true);
        mat2.setTransparencyAmount(0.08f);
        mat2.setRefractionAmount(1.1f);
        mat2.setReflectionAmount(0.5f);
        mat2.setSpecularConst(100f);
        floorMat.setReflectionAmount(0.9f);
        defMat.setReflectionAmount(0.8f);
        defMat.setSpecularConst(10f);
        mat3.setTransparencyAmount(0.9f);
        mat3.setRefractionAmount(1.1f);
        mat3.setReflectionAmount(0.8f);
        mat3.setSpecularConst(100f);
        Scene scene01 = new Scene();
        scene01.setAmbientLight(0.5f);
        scene01.setCamera(new Camera(new Vector3D(0, 0, 0), 60, 60, 1600, 1600, 1f, 30f));
        scene01.addLight(new PointLight(new Vector3D(0f,10f, 2f), Color.WHITE,100f));
        scene01.addLight(new DirectionalLight(new Vector3D(1f, -10f, 1f), Color.WHITE, 1f));
        scene01.addLight(new DirectionalLight(new Vector3D(-1f, -10f, 1f), Color.WHITE, 1f));
        scene01.addObject(new Sphere(new Vector3D(3f, 0f, 6f), 2f, mat2));
        scene01.addObject(new Sphere(new Vector3D(-3f, 0f, 6f), 2f, defMat));
        //scene01.addObject(new Sphere(new Vector3D(0f, 0f, 2f), 0.8f, mat3));
        //scene01.addObject(new Cylinder(8f, 2f, new Vector3D(0f, 4f, 7f), defMat, 3, 1));
        /*scene01.addObject(new Triangle(Color.YELLOW, new Vector3D(3f, -2f, 11f), 
                                                     new Vector3D(-3f, -2f, 11f),
                                                     new Vector3D(-3f, -2f, 5f)));
        scene01.addObject(new Triangle(Color.YELLOW, new Vector3D(-3f,-2f, 5f), 
                                                     new Vector3D(3f, -2f, 5f),
                                                     new Vector3D(3f, -2f, 11f)));*/
        scene01.addObject(new Triangle(floorMat, new Vector3D(5f, -3.5f, 13f), 
                                                   new Vector3D(-5f, -3.5f, 13f),
                                                   new Vector3D(-5f, -3.5f, 3f)));
        scene01.addObject(new Triangle(floorMat, new Vector3D(-5f,-3.5f, 3f), 
                                                   new Vector3D(5f, -3.5f, 3f),
                                                   new Vector3D(5f, -3.5f, 13f)));
        //ObjectOBJ obj = new ObjectOBJ("teapot1.obj");
        //ObjectOBJ obj = new ObjectOBJ("Torus3.obj");
        ObjectOBJ obj = new ObjectOBJ("Torus1.obj");
        obj.getPoly().assignMaterial(mat3);
        obj.changePosition(new Vector3D(0f, 1f, 2f));
        //obj1.getPoly().setColor(Color.CYAN);
        scene01.addObject(obj.getPoly());
        //scene01.addObject(obj1.getPoly());
        /*Polygon plane1 = new Polygon(new Vector3D(0f, -2f, 8f), Color.YELLOW);
        plane1.addVertex(new Vector3D(3f, -2f, 11f));
        plane1.addVertex(new Vector3D(-3f, -2f, 11f));
        plane1.addVertex(new Vector3D(-3f, -2f, 5f));
        plane1.addVertex(new Vector3D(3f, -2f, 5f));
        plane1.addFace(0, 1, 2);
        plane1.addFace(2, 3, 0);
        scene01.addObject(plane1);*/
        BufferedImage image = raytrace(scene01);
        File outputImage = new File("image.png");
        try 
        {
            ImageIO.write(image, "png", outputImage);
        } catch (IOException ex) 
        {
            System.out.println("Something failed");
        }
        
    }

    public static BufferedImage raytrace(Scene scene) 
    {
        Camera mainCamera = scene.getCamera();
        BufferedImage image = new BufferedImage(mainCamera.getResolution()[0], mainCamera.getResolution()[1], BufferedImage.TYPE_INT_RGB);
        ArrayList<Object3D> objects = scene.getObjects();

        Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
        for (int i = 0; i < positionsToRaytrace.length; i++) 
        {
            for (int j = 0; j < positionsToRaytrace[i].length; j++) 
            {
                float x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
                float y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
                float z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();
                Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));
                Color pixelColor = getColor((Object3D)null, ray, scene);
                image.setRGB(i, j, pixelColor.getRGB());
            }
        }

        return image;
    }
    public static Boolean isShadow(Object3D object, Ray ray, Scene scene)
    {
        double closestDistance = -1;
        for (int i = 0; i < scene.getObjects().size(); i++)
        {
            Object3D currentObject = scene.getObjects().get(i);
            if (currentObject.equals(object))
                continue;
            if (Sphere.class == currentObject.getClass()) 
            {
                Intersection intersection = Ray.getIntersectionSphere(ray, (Sphere)currentObject);
                double distance = intersection.getDistance();
                if (distance >= 0 && (closestDistance < 0 || distance < closestDistance)) 
                {
                    closestDistance = distance;
                }
            }
            if (Triangle.class == currentObject.getClass())
            {
                Intersection intersection = Ray.getIntersectionTriangle(ray, currentObject, 0);
                double distance = intersection.getDistance();
                if (distance >= 0 && (closestDistance < 0 || distance < closestDistance))
                {
                    closestDistance = distance;
                }
            }
            if (Cylinder.class == currentObject.getClass())
            {
                Cylinder cyl = (Cylinder)currentObject;
                for (int l = 0; l < cyl.getFaces().size(); l++)
                {
                    Intersection intersection = Ray.getIntersectionTriangle(ray, cyl, l);
                    double distance = intersection.getDistance();
                    if (distance >= 0 && (closestDistance < 0 || distance < closestDistance))
                    {
                        closestDistance = distance;
                    }
                }
            }
            if (Polygon.class == currentObject.getClass())
            {
                Polygon poly = (Polygon)currentObject;
                for (int l = 0; l < poly.getFaces().size(); l++)
                {
                    Intersection intersection = Ray.getIntersectionTriangle(ray, poly, l);
                    double distance = intersection.getDistance();
                    if (distance >= 0 && (closestDistance < 0 || distance < closestDistance))
                    {
                        closestDistance = distance;
                    }
                }
            }
        }
        return (closestDistance > 0);
    }
    
    public static Color getColor(Object3D object, Ray ray, Scene scene)
    {
        
        double closestDistance = -1;
        Object3D closestObject = null;
        Intersection currentInt = new Intersection(false);
        Intersection tempInt = null;
        for (int k = 0; k < scene.getObjects().size(); k++) 
        {
            Object3D currentObject = scene.getObjects().get(k);
            if (currentObject.equals(object))
                continue;
            if (Sphere.class == scene.getObjects().get(k).getClass()) 
            {
                tempInt = Ray.getIntersectionSphere(ray, (Sphere) scene.getObjects().get(k));
            }
            if (Triangle.class == scene.getObjects().get(k).getClass())
            {
                tempInt = Ray.getIntersectionTriangle(ray, scene.getObjects().get(k), 0);
            }
            if (Cylinder.class == scene.getObjects().get(k).getClass())
            {
                Cylinder cyl = (Cylinder)scene.getObjects().get(k);
                for (int l = 0; l < cyl.getFaces().size(); l++)
                {
                    tempInt = Ray.getIntersectionTriangle(ray, cyl, l);
                    double distance = tempInt.getDistance();
                    if (distance >= 0 && (closestDistance < 0 || distance < closestDistance))
                    {
                        closestDistance = distance;
                        closestObject = cyl;
                        currentInt = tempInt;
                    }
                }
            }
            if (Polygon.class == scene.getObjects().get(k).getClass())
            {
                Polygon poly = (Polygon)scene.getObjects().get(k);
                for (int l = 0; l < poly.getFaces().size(); l++)
                {
                    tempInt = Ray.getIntersectionTriangle(ray, poly, l);
                    double distance = tempInt.getDistance();
                    if (distance >= 0 && (closestDistance < 0 || distance < closestDistance))
                    {
                        closestDistance = distance;
                        closestObject = poly;
                        currentInt = tempInt;
                    }
                }
            }
            double distance = tempInt.getDistance();
            if (distance >= 0 && (closestDistance < 0 || distance < closestDistance))
            {
                closestDistance = distance;
                closestObject = scene.getObjects().get(k);
                currentInt = tempInt;
            }
        }
        if (closestObject == null)
        {
            return Color.BLACK;
        }
        else
        {
            Color pixelColor = Color.BLACK;
            if(currentInt.isIntersection())
            {
                for (Light light:scene.getLights())
                {
                    if ((closestDistance >= scene.getCamera().getNearClippingPane()) && (closestDistance <= scene.getCamera().getFarClippingPane())) 
                    {
                        Ray shadowRay = new Ray(currentInt.getIntersectionPosition(), Vector3D.scalarProduct(-1f, light.getDirection(currentInt.getIntersectionPosition())));
                        if(isShadow(closestObject, shadowRay, scene))
                        {
                            Color tempColor = Ray.getShadowColor(closestObject.getColor(), scene.getAmbientLight());
                            pixelColor = Material.addColors(tempColor, pixelColor);
                        }
                        else
                        {
                            pixelColor = Material.addColors(pixelColor, Ray.getDiffuseColor(closestObject.getColor(), light, currentInt, scene.getAmbientLight()));

                        }
                        if(closestObject.getMaterial().isTransparent())
                        {
                            Ray newRay;
                            if (closestObject.getMaterial().refracts())
                            {
                                newRay = Ray.refractedRay(ray, currentInt, closestObject.getMaterial().getRefractionAmount());
                            }
                            else
                            {
                                newRay = ray;
                            }
                            Color backColor = getColor(closestObject, newRay, scene);
                            pixelColor = Material.addColors(Material.scalarColorProduct(1f-closestObject.getMaterial().getTransparencyAmount(), pixelColor), 
                                                            Material.scalarColorProduct(closestObject.getMaterial().getTransparencyAmount(), backColor));
                        }
                        if(closestObject.getMaterial().reflects())
                        {
                            Ray newRay = Ray.reflectedRay(ray, currentInt);
                            Color frontColor = getColor(closestObject, newRay, scene);
                            pixelColor = Material.addColors(Material.scalarColorProduct(1f-closestObject.getMaterial().getReflectionAmount(), pixelColor), 
                                                            Material.scalarColorProduct(closestObject.getMaterial().getReflectionAmount(), frontColor)); 
                        }
                    }
                }
                for (Light light:scene.getLights())
                {
                        if(closestObject.getMaterial().isSpecular()) //(Math.abs((float)Vector3D.dotProduct(newRay.getDirection(), Vector3D.scalarProduct(-1f, light.getDirection(currentInt.getIntersectionPosition())))) > Math.cos(Math.toRadians(89)))
                        {
                            Ray lightReflected = Ray.reflectedRay(new Ray(light.getPosition(), Vector3D.scalarProduct(-1, light.getDirection(currentInt.getIntersectionPosition()))), currentInt);                    
                            if(Vector3D.dotProduct(ray.getDirection(), lightReflected.getDirection()) > 0)
                            pixelColor = Material.addColors(pixelColor, 
                                                            Material.scalarColorProduct((float)Math.pow((double)Vector3D.dotProduct(ray.getDirection(), lightReflected.getDirection()), (double)closestObject.getMaterial().getSpecularConst())*light.getIntensity(currentInt.getIntersectionPosition()), 
                                                                                        light.getColor()));
                        }
                }
            }
            return pixelColor;
        }     
    }
}
