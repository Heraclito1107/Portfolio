/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heraclito
 * @version 1.3
 */
public class ObjectOBJ {
    //private FileReader file;
    private Polygon poly;
    private String sourceFile;

    public ObjectOBJ(String sourceFile) {
        this.sourceFile = sourceFile;
        poly = new Polygon(new Vector3D(0f, 0f, 0f), new Material(Color.LIGHT_GRAY, false, false, false, true, true));
        getInfo();
    }
    private void getInfo()
    {
        Object[] rawLines;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
            rawLines = reader.lines().toArray();
            for (Object rawLine : rawLines) {
                String[] currentLine = rawLine.toString().split(" ");
                if (currentLine[0].equals("v"))
                {
                    poly.addVertex(new Vector3D(Float.parseFloat(currentLine[currentLine.length-3]), Float.parseFloat(currentLine[currentLine.length-2]), Float.parseFloat(currentLine[currentLine.length-1])));
                }
                else if (currentLine[0].equals("f"))
                {   
                    if(currentLine.length == 4)
                        poly.addFace(Integer.parseInt(currentLine[1].split("/")[0])-1, Integer.parseInt(currentLine[3].split("/")[0])-1, Integer.parseInt(currentLine[2].split("/")[0])-1);
                    if (currentLine.length == 5)
                    {
                        poly.addFace(Integer.parseInt(currentLine[2].split("/")[0])-1, Integer.parseInt(currentLine[1].split("/")[0])-1, Integer.parseInt(currentLine[3].split("/")[0])-1);
                        poly.addFace(Integer.parseInt(currentLine[3].split("/")[0])-1, Integer.parseInt(currentLine[1].split("/")[0])-1, Integer.parseInt(currentLine[4].split("/")[0])-1);
                        
                    }
                }
            }   
            
            calculatePosition();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ObjectOBJ.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    private void calculatePosition()
    {
        Vector3D pos = new Vector3D(0f, 0f, 0f);
        for(int i = 0; i < poly.getVerts().size(); i++)
        {
            pos = Vector3D.add(pos, poly.getVerts().get(i));
            //System.out.println(pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
        }
        poly.setPosition(Vector3D.scalarProduct((1f/poly.getVerts().size()), pos));
    }

    public Polygon getPoly() {
        return poly;
    }

    public void setColor(Color color) {
        poly.setColor(color);
    }
    
    public void changePosition(Vector3D deltaPosition)
    {
        this.getPoly().changePosition(deltaPosition);
        calculatePosition();
    }
    
}
