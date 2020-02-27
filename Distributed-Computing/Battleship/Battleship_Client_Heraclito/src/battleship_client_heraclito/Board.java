/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship_client_heraclito;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Heraclito
 */
public class Board {
    public static int size = 10;
    public Button[][] gameSquares;
    public Rectangle[][] background;
    private StackPane[][] stack;
    private GridPane grid;
    
    public Board()
    {
        gameSquares = new Button[size][size];
        stack = new StackPane[size][size];
        background = new Rectangle[size][size];
        createBoard();
    }
    
    private void createBoard()
    {
        grid = new GridPane();
       
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                gameSquares[i][j] = new Button("");
                gameSquares[i][j].setMinSize(40, 40);
                gameSquares[i][j].setMaxSize(40, 40);
                gameSquares[i][j].setBackground(Background.EMPTY);
                gameSquares[i][j].setId(j+ "," + i);
                background[i][j] = new Rectangle(40, 40, Paint.valueOf("blue"));  
                background[i][j].setStroke(Paint.valueOf("black"));
                stack[i][j] = new StackPane();
                stack[i][j].setMaxSize(40, 40);
                stack[i][j].getChildren().add(background[i][j]);
                stack[i][j].getChildren().add(gameSquares[i][j]);
                grid.add(stack[i][j], j, i);
            }
        }
    }

    public GridPane getGrid() {
        return grid;
    }
    
}
