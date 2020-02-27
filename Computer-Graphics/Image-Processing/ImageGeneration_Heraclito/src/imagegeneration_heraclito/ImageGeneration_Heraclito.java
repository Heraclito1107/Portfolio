/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagegeneration_heraclito;

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
 */
public class ImageGeneration_Heraclito extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        
    BufferedImage graphic1 = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
    
        for (int i = 0; i < 600; i++)
        {
            for (int j = 0; j < 800; j++)
            {
                if (j > (799-(i*4/3)))
                    graphic1.setRGB(j, i, Color.MAGENTA.getRGB());
                else
                    graphic1.setRGB(j, i, Color.PINK.getRGB());
            }
        }
        
        BufferedImage graphic2 = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i < 800; i++)
        {
            for (int j = 0; j < 600; j++)
            {
                graphic2.setRGB(i, j, Color.LIGHT_GRAY.getRGB());
            }
        }
        
        for (int i = 0; i < 20000; i++)
        {
            int xPos = (int) (298*Math.cos(i*Math.PI/10000));
            int yPos = (int) (298*Math.sin(i*Math.PI/10000));
            graphic2.setRGB(xPos + 400, 300 - yPos, Color.BLACK.getRGB());
        }
        
        for (int i = 0; i < 12; i++)
        {
            int xPos = (int) (250*Math.cos(i*Math.PI/6));
            int yPos = (int) (250*Math.sin(i*Math.PI/6));
            for (int j = 0; j < 15; j++)
            {
                for (int k = 0; k < 20000; k++)
                {
                    int newX = (int) (j*Math.cos(k*Math.PI/10000));
                    int newY = (int) (j*Math.sin(k*Math.PI/10000));
                    graphic2.setRGB((xPos + 400)+ newX, (300 - yPos)-newY, Color.BLACK.getRGB());
                }
            }
        }
        
        for (int i = 0; i < 200; i++)
        {
            int xPos = (int) (i*Math.cos(Math.PI/6));
            int yPos = (int) (i*Math.sin( Math.PI/6));
            graphic2.setRGB(xPos + 400, 300 - yPos, Color.BLACK.getRGB());
        }
        
        for (int i = 0; i < 150; i++)
        {
            int xPos = (int) (i*Math.cos(5*Math.PI/6));
            int yPos = (int) (i*Math.sin(5*Math.PI/6));
            graphic2.setRGB(xPos + 400, 300 - yPos, Color.BLACK.getRGB());
        }
        
        BufferedImage graphic3 = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i < 800; i++)
        {
            for (int j = 0; j < 600; j++)
            {
                graphic3.setRGB(i, j, Color.CYAN.getRGB());
            }
        }
        
        for (int j = 0; j < 50; j++)
        {
            for (int k = 0; k < 20000; k++)
            {
                int newX = (int) (j*Math.cos(k*Math.PI/10000));
                int newY = (int) (j*Math.sin(k*Math.PI/10000));
                if (j < 48)
                    graphic3.setRGB(150+ newX, 100-newY, Color.YELLOW.getRGB());
                else
                    graphic3.setRGB(150+ newX, 100-newY, Color.ORANGE.getRGB());
            }
        }
        
        for (int i = 0; i < 12; i++)
        {
            for (int j = 0; j < 35; j++)
            {
                int xPos = (int) ((50+j)*Math.cos(i*Math.PI/6));
                int yPos = (int) ((50+j)*Math.sin(i*Math.PI/6));
                graphic3.setRGB(xPos + 150, 100 - yPos, Color.ORANGE.getRGB());
            }
        }
        
        for (int i = 0; i < 800; i++)
        {
            int yPos = (int) (450 - (30*Math.sin(i*Math.PI/100)));
            
            for (int j = yPos; j < 600; j++)
            {
                graphic3.setRGB(i, j, Color.BLUE.getRGB());
            }
        }
        

            
        File outputImage1 = new File("image1.bmp");
        try {
            ImageIO.write(graphic1, "bmp", outputImage1);
        } catch (Exception ex) {
            System.out.println("Something failed");
        }
        
        File outputImage2 = new File("image2.bmp");
        try {
            ImageIO.write(graphic2, "bmp", outputImage2);
        } catch (Exception ex) {
            System.out.println("Something failed");
        }
        
        File outputImage3 = new File("image3.bmp");
        try {
            ImageIO.write(graphic3, "bmp", outputImage3);
        } catch (Exception ex) {
            System.out.println("Something failed");
        }

        WritableImage image1 = SwingFXUtils.toFXImage(graphic1, null);
        WritableImage image2 = SwingFXUtils.toFXImage(graphic2, null);
        WritableImage image3 = SwingFXUtils.toFXImage(graphic3, null);
        
        StackPane root1 = new StackPane();
        root1.getChildren().add(new ImageView(image1));
        Scene scene1 = new Scene(root1, 850, 650);
        primaryStage.setTitle("Image 1");
        primaryStage.setScene(scene1);
        primaryStage.show();
        
        StackPane root2 = new StackPane();
        root2.getChildren().add(new ImageView(image2));
        Scene scene2 = new Scene(root2, 850, 650);
        Stage stage2 = new Stage();
        stage2.setTitle("Image 2");
        stage2.setScene(scene2);
        stage2.show();
        
        StackPane root3 = new StackPane();
        root3.getChildren().add(new ImageView(image3));
        Scene scene3 = new Scene(root3, 850, 650);
        Stage stage3 = new Stage();
        stage3.setTitle("Image 3");
        stage3.setScene(scene3);
        stage3.show();
    
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    

}