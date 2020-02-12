/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gato_marianarmz_heraclito;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author CIAC
 */
public class Main extends Application 
{
    /**
     * @throws java.lang.Exception
     */
    private Stage stage;
    private Scene scene1, scene2, scene3;
    Board gameBoard; = new Board();
    
    
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        stage = primaryStage;
        scene1 = new Scene(gameBoard.createBoard());
        stage.setScene(scene1);
        stage.show();
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
    
}
