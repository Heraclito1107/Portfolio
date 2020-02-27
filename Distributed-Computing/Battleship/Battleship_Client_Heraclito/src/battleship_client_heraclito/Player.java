/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship_client_heraclito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Heraclito
 */
public class Player {
    private String name, ip;
    private Board shipBoard, attackBoard;
    private Ship[] ships;
    private boolean vertical, playing;
    private int currentShip, enemyShipsDestroyed, shipsDestroyed;
    ServerSocket socket;
    Stage stage;
    public Player(String name, String ip, Stage stage) {
        this.stage = stage;
        this.name = name;
        this.ip = ip;
        shipBoard = new Board();
        attackBoard = new Board();
        ships = new Ship[5];
        vertical = true;
        playing = false;
        enemyShipsDestroyed = 0;
        shipsDestroyed = 0;
        createShips();
        try {
            socket = new ServerSocket(8765);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Board getShipBoard() {
        return shipBoard;
    }

    public Board getAttackBoard() {
        return attackBoard;
    }
    
    private void createShips()
    {
        ships[0] = new Ship(2, "Patrol Boat");
        ships[1] = new Ship(3, "Submarine");
        ships[2] = new Ship(3, "Destroyer");
        ships[3] = new Ship(4, "Battleship");
        ships[4] = new Ship(5, "Aircraft Carrier");
    }
    
    public int shipDeployment()
    {
        if(currentShip < 5)
        {
            Ship ship= ships[currentShip];
            stage.setTitle("Select a position to deploy your " + ship.getName());
            EventHandler changeOrientation = new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    for(int i = 0; i < shipBoard.size; i++)
                    {
                        for(int j = 0; j < shipBoard.size; j++)
                        {
                            shipBoard.gameSquares[i][j].setBackground(Background.EMPTY);
                        }
                    }
                    vertical = !vertical;
                }
            };
            
            EventHandler mouseClick = new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    String[] coords = ((Button)event.getSource()).getId().split(",");
                    Coordinate pCoord = new Coordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
                    if(vertical)
                    {
                        for(int i = 0; i < ship.getDimension(); i++)
                            shipBoard.gameSquares[pCoord.y + i][pCoord.x].setBackground(Background.EMPTY);
                        ship.setPosition('v', pCoord);
                    }
                    else
                    {
                        for(int i = 0; i < ship.getDimension(); i++)
                            shipBoard.gameSquares[pCoord.y][pCoord.x + i].setBackground(Background.EMPTY);
                        ship.setPosition('h', pCoord);
                    }
                    for(Coordinate coord: ship.getLocation())
                    {
                        shipBoard.background[coord.y][coord.x].setFill(Paint.valueOf("GREY"));
                    }
                    currentShip++;
                    shipDeployment();
                }
            };
            
            EventHandler mouseOver = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String[] coords = ((Button)event.getSource()).getId().split(",");
                    Coordinate pCoord = new Coordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));

                    if((vertical) && (pCoord.y + ship.getDimension() <= shipBoard.size))
                    {
                        for(int i = 0; i < ship.getDimension(); i++)
                            shipBoard.gameSquares[pCoord.y + i][pCoord.x].setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                    if((!vertical) && (pCoord.x + ship.getDimension() <= shipBoard.size))
                    {
                        for(int i = 0; i < ship.getDimension(); i++)
                            shipBoard.gameSquares[pCoord.y][pCoord.x + i].setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                }
            };
            
            EventHandler mouseExit = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String[] coords = ((Button)event.getSource()).getId().split(",");
                    Coordinate pCoord = new Coordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
                    if((vertical) && (pCoord.y + ship.getDimension() <= shipBoard.size))
                    {
                        for(int i = 0; i < ship.getDimension(); i++)
                            shipBoard.gameSquares[pCoord.y + i][pCoord.x].setBackground(Background.EMPTY);
                    }
                    if((!vertical) && (pCoord.x + ship.getDimension() <= shipBoard.size))
                    {
                        for(int i = 0; i < ship.getDimension(); i++)
                            shipBoard.gameSquares[pCoord.y][pCoord.x + i].setBackground(Background.EMPTY);
                    }
                }
            };

            for(int i = 0; i < shipBoard.size; i++)
            {
                for(int j = 0; j < shipBoard.size; j++)
                {
                    shipBoard.gameSquares[i][j].setOnMouseClicked(mouseClick);
                    shipBoard.gameSquares[i][j].setOnMouseEntered(mouseOver);
                    shipBoard.gameSquares[i][j].setOnMouseExited(mouseExit);
                    shipBoard.gameSquares[i][j].setOnKeyReleased(changeOrientation);
                }
            }
        }
        else
        {
            EventHandler gameAction = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ((Button)event.getSource()).setOnAction(null);
                    if(isPlaying())
                    {
                        String[] coords = ((Button)event.getSource()).getId().split(",");
                        Coordinate pCoord = new Coordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
                        attack(pCoord);
                        if(!isWinner())
                            getAttacked(event);
                        else
                            event.consume();
                    }
                    else
                    {
                        event.consume();
                    }
                }
            };
            for(int i = 0; i < shipBoard.size; i++)
            {
                for(int j = 0; j < shipBoard.size; j++)
                {
                    shipBoard.gameSquares[i][j].setOnMouseClicked(null);
                    shipBoard.gameSquares[i][j].setOnMouseEntered(null);
                    shipBoard.gameSquares[i][j].setOnMouseExited(null);
                    shipBoard.gameSquares[i][j].setOnKeyReleased(null);
                    attackBoard.gameSquares[i][j].setOnAction(gameAction);
                }
            }
            VBox shipBox = new VBox();
            VBox attBox = new VBox();
            shipBox.setPadding(new Insets(15, 15, 15, 15));
            shipBox.setSpacing(10);
            attBox.setPadding(new Insets(15, 15, 15, 15));
            attBox.setSpacing(10);
            shipBox.getChildren().add(shipBoard.getGrid());
            shipBox.getChildren().add(new Label("Ship board \n Just for reference\n\n How to win?\n Be the first to destroy all the enemy's ships"));
            attBox.getChildren().add(attackBoard.getGrid());
            attBox.getChildren().add(new Label("Attack board\n On your turn, click on any blue square to attack\n\n Color reference:\n Blue: available to attack\n Red: ship hitted!\n Wheat: shot missed!"));
            HBox hb = new HBox();
            hb.setCenterShape(true);
            hb.setPadding(new Insets(15, 10, 15, 10));
            hb.setSpacing(30);
            hb.getChildren().add(shipBox);
            hb.getChildren().add(attBox);
            Scene playerScreen = new Scene(hb);
            stage.setTitle("Time to play, " + name + "!");
            stage.setScene(playerScreen);
            getAttacked(null);
        }
        return 0;
    }

    public void attack(Coordinate shot)
    {
        setPlaying(false);        
        try
        {
            int hit = 0, destroy = 0, destroyedShip = 0;
            Socket socket = new Socket(ip, 8755);
            InputStream inSocket = socket.getInputStream();
            OutputStream outSocket = socket.getOutputStream();
            String str = shot.toString() + "\n\n";
            byte buffer[] = str.getBytes();
            outSocket.write(buffer);
            while(inSocket.available()==0){}
            hit = inSocket.read()-48;
            destroy = inSocket.read()-48;
            destroyedShip = inSocket.read() - 48;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if(hit == 0)
            {
                attackBoard.background[shot.y][shot.x].setFill(Paint.valueOf("wheat"));
                alert.setTitle("Shot!");
                alert.setHeaderText("W A T E R !");
            }
            else
            {
                attackBoard.background[shot.y][shot.x].setFill(Paint.valueOf("red"));
                if(destroy == 1)
                {
                    enemyShipsDestroyed++;
                    alert.setTitle("Ship destroyed");
                    alert.setHeaderText("The enemy has lost its " + ships[destroyedShip].getName());
                }
                else
                {
                    alert.setTitle("Shot!");
                    alert.setHeaderText("H I T !");
                }
            }     
            alert.showAndWait();
            socket.close();
        }
        catch(Exception ex){
            System.out.println("You must first start the server socket");
            System.out.println("(YourServer.java) at the command prompt.");
            System.out.println(ex);
        }
        if(isWinner())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);  
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("W I N N E R ! ! !");
            alert.setHeaderText("Congratulations " + this.name + "!");
            alert.show();
            stage.close();
        }   
    }
    
    public void getAttacked(ActionEvent event)
    {
        if(event != null)
        {
            event.consume();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Waiting");
        alert.setHeaderText("Wait for the other player to attack");
        alert.showAndWait();
        try {
            boolean shoted = false, destroyed = false;
            int hitted = 0;
            String response = "";
            //Accepting the conection, it means that it is TCP
            Socket insocket = socket.accept( );

            BufferedReader in = new BufferedReader (new
                InputStreamReader(insocket.getInputStream()));
            PrintWriter out = new PrintWriter (insocket.getOutputStream(),
                true);

            String instring = in.readLine();
            String[] coords = instring.split(",");
            Coordinate shot = new Coordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
            
            for(int i = 0; i < 5; i++)
            {
                Ship ship = ships[i];
                if(ship.receiveDamage(shot))
                {
                    shipBoard.background[shot.y][shot.x].setFill(Paint.valueOf("RED"));
                    shoted = true;
                    destroyed = ship.isDestroyed();
                    hitted = i;
                    break;
                }
            }
            if(shoted)
            {
                if(destroyed)
                {
                    shipsDestroyed++;
                    response = "11" + hitted;
                }
                else
                {
                    response = "100";
                }
            }
            else
            {
                shipBoard.background[shot.y][shot.x].setFill(Paint.valueOf("wheat"));
                response = "000";
            }
            out.println(response);
            insocket.close();
            setPlaying(true);
        }catch (Exception e) {
            System.out.print(e);
        }
        if(isLoser())
        {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);  
            alert1.initModality(Modality.APPLICATION_MODAL);
            alert1.setTitle("G A M E  O V E R ! ! !");
            alert1.setHeaderText("You lose, " + this.name + "!");
            alert1.showAndWait();
            stage.close();
        }
    }
    
    private boolean isWinner()
    {
        if(enemyShipsDestroyed == 5)
        {
            return true;
        }
        return false;
    }
    
    private boolean isLoser()
    {
        if(shipsDestroyed == 5)
        {
            return true;
        }
        return false;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
    
    
}
