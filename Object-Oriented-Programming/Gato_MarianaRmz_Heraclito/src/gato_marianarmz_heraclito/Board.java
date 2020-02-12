/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gato_marianarmz_heraclito;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author CIAC
 */
public class Board 
{
    private int sideSize = 3;
    private int revealedSquares = 0;;
    private Label[][] gameSquares = new Label[sideSize][sideSize];
    private Button[][] buttons = new Button[sideSize][sideSize];
    private StackPane[][] stack = new StackPane[sideSize][sideSize];
    private int[][] mineIndex = new int[sideSize][sideSize];
    private boolean lose = false;
    private Player player1 = new Player("X");
    private Player player2 = new Player("O");
    
    public GridPane createBoard()
    {
        GridPane gameBoard = new GridPane();
        
        for (int i = 0; i < sideSize; i++)
        {
            for (int j = 0; j < sideSize; j++)
            {
                gameSquares[i][j] = new Label("");
                gameSquares[i][j].setMinSize(100, 100);
                gameSquares[i][j].setMaxSize(100, 100);
                gameSquares[i][j].setBorder(Border.EMPTY);
                buttons[i][j] = new Button();
                buttons[i][j].setMinSize(100, 100);
                buttons[i][j].setMaxSize(100, 100);
                buttons[i][j].setId(i + "," + j);
                //buttons[i][j].setOnMouseClicked(clickCheck());
                buttons[i][j].setOnAction(pressButton());
                stack[i][j] = new StackPane();
                stack[i][j].setMaxSize(100, 100);
                
                stack[i][j].getChildren().add(buttons[i][j]);
            }
        }
        
        for (int i = 0; i < sideSize; i++)
        {
            for (int j = 0; j < sideSize; j++)
            {
                gameBoard.add(stack[i][j], i, j+2);
            }
        }
            
        
        return gameBoard;
    }
    
    public EventHandler pressButton()
    {
        EventHandler event = new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                
                int[] coords = getPressedCoords(((Button)event.getSource()).getId());
                
                if (buttons[coords[0]][coords[1]].isVisible())
                {
                    markSquare(coords[0], coords[1], getCurrentPlayer());
                }
               revealedSquares++;
            }
             
        };
        
        return event;
    }
    
    public void finishGame(boolean lose)
    {
        this.lose = lose;  
    }
    
    public void markSquare(int xCoord, int yCoord, String player)
    {
        String mark = "";
        buttons[xCoord][yCoord].setVisible(false);
        if (player.equals("jug1"))
        {
            mark = "O";
        }
        if (player.equals("jug2"))
        {
            mark = "X";
        }
        ImageView mineImage = new ImageView(new Image(mark + ".jpg"));
        
        mineImage.setFitHeight(100);
        mineImage.setFitWidth(100);
        Rectangle background = new Rectangle(100, 100);
        background.setStroke(Paint.valueOf("seashell"));
        stack[xCoord][yCoord].getChildren().add(background);
        stack[xCoord][yCoord].getChildren().add(mineImage);
    }
    
    public int[] getPressedCoords(String pressedLocation)
    {
        int[] pressedButtonCoords = new int[2];
        int commaIndex = 0;
        for (int i = 0; i < pressedLocation.length(); i++)
        {
            if (pressedLocation.charAt(i) == ',')
            {
                commaIndex = i;
            }
        }
        pressedButtonCoords[0] = Integer.parseInt(pressedLocation.substring(0, commaIndex));
        pressedButtonCoords[1] = Integer.parseInt(pressedLocation.substring((commaIndex + 1), pressedLocation.length()));
        
        return pressedButtonCoords;    
    }
    
    public String getCurrentPlayer()
    {
        return null;
    }
    
}
