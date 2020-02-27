/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.UP.MCG.coordinates;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Heraclito
 * @version 1.0
 */
public class CartesianToBaycentric_Heraclito extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        int width = 1000;
        int height = 1000;
        
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
       
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                image.setRGB(j, i, calculatedColor(width, height, j, i).getRGB());
            }
        }
        File outputImage1 = new File("image1.png");
        try {
            ImageIO.write(image, "png", outputImage1);
        } catch (Exception ex) {
            System.out.println("Something failed");
        }
        WritableImage outImage = SwingFXUtils.toFXImage(image, null);
        StackPane root = new StackPane();
        root.getChildren().add(new ImageView(outImage));
        
        Scene scene = new Scene(root, width + 50, height + 50);
        
        primaryStage.setTitle("Image1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public Color calculatedColor(int imageWidth, int imageHeight, int x, int y)
    {
        Coordinate a = new Coordinate((imageWidth/2), 50);
        Coordinate b = new Coordinate(50, imageHeight - 50);
        Coordinate c = new Coordinate(imageWidth - 50, imageHeight - 50);
        Coordinate l = new Coordinate(x, y);
        float[] barycentric = new float[3];
        Color newColor;
        barycentric[0] = ((b.getY()-c.getY())*(l.getX()-c.getX()) + (c.getX()-b.getX())*(l.getY()-c.getY())) /
                         ((b.getY()-c.getY())*(a.getX()-c.getX()) + (c.getX()-b.getX())*(a.getY()-c.getY()));
        
        barycentric[1] = ((c.getY()-a.getY())*(l.getX()-c.getX()) + (a.getX()-c.getX())*(l.getY()-c.getY())) /
                         ((b.getY()-c.getY())*(a.getX()-c.getX()) + (c.getX()-b.getX())*(a.getY()-c.getY()));
        
        barycentric[2] = 1 - barycentric[0] - barycentric[1];
        if ((barycentric[0] < 0) ||(barycentric[1] < 0) || (barycentric[2] < 0))
            newColor = Color.WHITE;
        else
            newColor = new Color(barycentric[0], barycentric[1], barycentric[2]);
        
        return newColor;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
