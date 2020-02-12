/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper_marianarmz_heraclito;

import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Heraclito
 */
public class Board 
{
    private int size, mines, score = 0, revealedSquares = 0;;
    private Label[][] gameSquares;
    private Button[][] buttons;
    private StackPane[][] stack;
    private int[][] mineIndex;
    private TextField scoreDisplay = new TextField();
    private boolean lose = false;
    private Button newGame = new Button("New Game");
    private Button finishGame = new Button ("Finish Game");
    

    
    public Board(int size, int mines)
    {
        this.size = size;
        this.mines = mines;
    }
    
    
    
    public void finishGame(boolean lose)
    {
        this.lose = lose;
        revealMines();  
    }
    
    private void nonMineCounter()
    {
        revealedSquares++;
        if (revealedSquares == ((size * size)- mines))
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("C O N G R A T U L A T I O N S ! ! !");
            alert.setHeaderText(null);
            alert.setContentText("You win!");
            alert.showAndWait();
            lose = true;
        }
    }
    
    public void setUpperButtonsAction(EventHandler newGameEvent, EventHandler endGameEvent)
    {
        newGame.setOnAction(newGameEvent);
        finishGame.setOnAction(endGameEvent);
    }
    
    public void revealMines()
    {
        for (int i = 0; i < mines; i++)
        {
            buttons[mineIndex[i][0]][mineIndex[i][1]].setVisible(false);
        }
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
    
    public boolean isAMine (int xCoord, int yCoord)
    {
        boolean mine = false;
        for (int i = 0; i < mines; i++)
        {
            if ((mineIndex[i][0] == xCoord) && (mineIndex[i][1] == yCoord))
            {
                mine = true;
                i = mines;
            }
        }
        return mine;
    }
    
    public boolean isEmptySquare(int xCoord, int yCoord)
    {
        boolean emptySquare = false;
        if(surroundingMinesCount(xCoord, yCoord) == 0)
        {
            emptySquare = true;
            buttons[xCoord][yCoord].setVisible(false);
            emptySquaresReveal(xCoord, yCoord);
        }    
        return emptySquare;
    }
    
    public void emptySquaresReveal(int xCoord, int yCoord)
    {
             
        if((xCoord > 0) && (yCoord > 0) && (buttons[xCoord - 1][yCoord - 1].isVisible()))
        {
            buttons[xCoord - 1][yCoord - 1].fire();
        }
        if((xCoord > 0) && (buttons[xCoord - 1][yCoord].isVisible()))
        {
            buttons[xCoord - 1][yCoord].fire();
        }
        if((xCoord > 0) && (yCoord < (size - 1)) && (buttons[xCoord - 1][yCoord + 1].isVisible()))
        {
            buttons[xCoord - 1][yCoord + 1].fire();
        }
        if((yCoord > 0) && (buttons[xCoord][yCoord - 1].isVisible()))
        {
            buttons[xCoord][yCoord - 1].fire();
        }
        if((yCoord < (size - 1)) && (buttons[xCoord][yCoord + 1].isVisible()))
        {
            buttons[xCoord][yCoord + 1].fire();
        }
        if((xCoord < (size - 1)) && (yCoord > 0) && (buttons[xCoord + 1][yCoord - 1].isVisible()))
        {
            buttons[xCoord + 1][yCoord - 1].fire();
        }
        if((xCoord < (size - 1)) && (buttons[xCoord + 1][yCoord].isVisible()))
        {
            buttons[xCoord + 1][yCoord].fire();
        }
        if((xCoord < (size - 1)) && (yCoord < (size - 1)) && (buttons[xCoord + 1][yCoord + 1].isVisible()))
        {
            buttons[xCoord + 1][yCoord + 1].fire();
        }
        
        
    }
    
    public int surroundingMinesCount(int xCoord, int yCoord)
    {
        int surroundingMines = 0;
        
        for (int i = 0; i < mines; i++)
        {
            if (((mineIndex[i][0] == (xCoord - 1)) && (mineIndex[i][1] == (yCoord - 1))) ||
                ((mineIndex[i][0] == (xCoord - 1)) && (mineIndex[i][1] == yCoord)) ||
                ((mineIndex[i][0] == (xCoord - 1)) && (mineIndex[i][1] == (yCoord + 1))) ||
                ((mineIndex[i][0] == xCoord) && (mineIndex[i][1] == (yCoord - 1))) ||
                ((mineIndex[i][0] == xCoord) && (mineIndex[i][1] == (yCoord + 1))) ||
                ((mineIndex[i][0] == (xCoord + 1)) && (mineIndex[i][1] == (yCoord - 1))) ||
                ((mineIndex[i][0] == (xCoord + 1)) && (mineIndex[i][1] == yCoord)) ||
                ((mineIndex[i][0] == (xCoord + 1)) && (mineIndex[i][1] == (yCoord + 1))))
            {
                surroundingMines++;
            }
        }
        return surroundingMines;
    }
    
    public EventHandler clickCheck()
    {
        EventHandler event = new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
                if(event.getButton() == MouseButton.SECONDARY)
                {
                    if (((Button)event.getSource()).getText().equals(""))
                    {
                        ((Button)event.getSource()).setText("M");
                        ((Button)event.getSource()).setStyle("-fx-background-color: red");
                        ((Button)event.getSource()).setOnAction(null);
                    }
                    else
                    {
                        ((Button)event.getSource()).setText("");
                        ((Button)event.getSource()).setStyle(null);
                        ((Button)event.getSource()).setOnAction(pressButton());
                    }
                }
            }
        };
        return event;
    }
    public EventHandler pressButton()
    {
        EventHandler event = new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                int[] coords = getPressedCoords(((Button)event.getSource()).getId());

                if ((isAMine(coords[0], coords[1])) && (lose == false))
                {
                    revealMines();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("G A M E  O V E R ! ! !");
                    alert.setHeaderText(null);
                    alert.setContentText("You lose!");
                    alert.showAndWait();
                    lose = true;
                }
                else if ((lose != true) && (!isEmptySquare(coords[0], coords[1])))
                {
                    ((Button)event.getSource()).setVisible(false);
                    score+= surroundingMinesCount(coords[0], coords[1]);
                    scoreDisplay.setText("   " + score + ""); 
                    
                }
                nonMineCounter();
            }
             
        };
        
        return event;
    }
    
    private void generateMines(int i)
    {
        Random rand = new Random();
        rand.setSeed(rand.nextInt());
        mineIndex[i][0] = rand.nextInt(size);
        rand = new Random();
        rand.setSeed(rand.nextInt());
        mineIndex[i][1] = rand.nextInt(size);
    }
    
    private void mineOverlappingValidation()
    {
        for (int i = 0; i < mines; i++)
        {
            for (int j = 0; j < mines; j++)
            {
                if (j != i)
                {
                    if ((mineIndex[i][0] == mineIndex[j][0]) && (mineIndex[i][1] == mineIndex[j][1]))
                    {
                        generateMines(j);
                    }
                    
                }
            }
        }
    }
    
    public GridPane createBoard()
    {
        GridPane gameBoard = new GridPane();
        
        gameSquares = new Label[size][size];
        buttons = new Button[size][size];
        stack = new StackPane[size][size];
        mineIndex = new int[mines][2];
        
        for (int i = 0; i < mines; i++)
        {
            generateMines(i);
        }
        mineOverlappingValidation();
        
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                gameSquares[i][j] = new Label("");
                gameSquares[i][j].setMinSize(25, 25);
                gameSquares[i][j].setMaxSize(25, 25);
                gameSquares[i][j].setBorder(Border.EMPTY);
                buttons[i][j] = new Button();
                buttons[i][j].setMinSize(25, 25);
                buttons[i][j].setMaxSize(25, 25);
                buttons[i][j].setId(i + "," + j);
                buttons[i][j].setOnMouseClicked(clickCheck());
                buttons[i][j].setOnAction(pressButton());
                stack[i][j] = new StackPane();
                stack[i][j].setMaxSize(25, 25);
                if (isAMine(i, j))
                {
                    ImageView mineImage = new ImageView(new Image("mina.png"));
                    mineImage.setFitHeight(25);
                    mineImage.setFitWidth(25);
                    Rectangle background = new Rectangle(25, 25, Paint.valueOf("crimson"));
                    background.setStroke(Paint.valueOf("seashell"));
                    stack[i][j].getChildren().add(background);
                    stack[i][j].getChildren().add(mineImage);
                }
                else
                {
                    if(surroundingMinesCount(i, j) != 0)
                    {
                        gameSquares[i][j].setText("  " + surroundingMinesCount(i, j));   
                    }
                    Rectangle background = new Rectangle(25, 25, Paint.valueOf("wheat"));  
                    background.setStroke(Paint.valueOf("seashell"));
                    stack[i][j].getChildren().add(background);
                    stack[i][j].getChildren().add(gameSquares[i][j]);
                }
                stack[i][j].getChildren().add(buttons[i][j]);
            }
        }
        
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                gameBoard.add(stack[i][j], i, j+2);
            }
        }
            System.out.println("Mines = " + mines );
            System.out.println("Size = " + size);
        
        for (int i = 0; i < mines; i++)
        {
            System.out.println(mineIndex[i][0] + " , " + mineIndex[i][1] );
        }
        
        scoreDisplay.setMaxSize(50, 30);
        scoreDisplay.setMinSize(50, 30);
        finishGame.setMaxSize(100, 30);
        finishGame.setMinSize(100, 30);
        newGame.setMaxSize(100, 30);
        newGame.setMinSize(100, 30);
        gameBoard.add(newGame, 0, 0, 4, 2);
        gameBoard.add(finishGame, (size - 4), 0, 4, 2);
        gameBoard.add(scoreDisplay,((size/2)-1), 0, 2, 2);
        
        return gameBoard;
    }

}
