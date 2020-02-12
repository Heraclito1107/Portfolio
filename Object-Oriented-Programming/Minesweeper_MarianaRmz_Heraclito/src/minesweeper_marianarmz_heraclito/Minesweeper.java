/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper_marianarmz_heraclito;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Heraclito
 */
public class Minesweeper extends Application 
{
    private Stage stage;
    private Scene scene;
    private Board gameBoard;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        launch(args);
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        stage = primaryStage;
        createMenu();
        stage.show();
    }
    
    public void createMenu()
    { 
        stage.setTitle("Mine Sweeper");
        Button easy = new Button("    Easy  ");
        Button medium = new Button("Medium");
        Button hard = new Button("   Hard  ");
        
        easy.setId("1");
        medium.setId("2");
        hard.setId("3");
        
        EventHandler event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String id = ((Button)event.getSource()).getId();
                
                switch (id){
                    case "1": 
                        gameBoard = new Board(10, 15);
                        break;
                    case "2": 
                        gameBoard = new Board(20, 60);
                        break; 
                    case "3": 
                        gameBoard = new Board(30, 150);
                        break;
                }
                generateGameBoard();
            }
        };
        easy.setId("1");
        medium.setId("2");
        hard.setId("3");
        
        easy.setOnAction(event);
        medium.setOnAction(event);
        hard.setOnAction(event);
        
        VBox menu = new VBox();
        menu.setPadding(new Insets(15, 12, 15, 12));
        menu.getChildren().add(easy);
        menu.getChildren().add(medium);
        menu.getChildren().add(hard);
        scene = new Scene(menu, 250, 250);
        stage.setScene(scene);
    }
    
    public void generateGameBoard()
    {
        Button newGame = new Button("New Game");
        Button finishGame = new Button ("Finish Game");
        
        EventHandler newGameEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createMenu();
            }
        };
        
        EventHandler endGameEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameBoard.finishGame(true); 
            }
        };
        
        gameBoard.setUpperButtonsAction(newGameEvent, endGameEvent);
        scene = new Scene(gameBoard.createBoard());
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }
}
