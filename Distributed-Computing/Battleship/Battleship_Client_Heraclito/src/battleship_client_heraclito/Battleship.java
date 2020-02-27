/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship_client_heraclito;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Heraclito
 */
public class Battleship extends Application {
    Player player2;
    @Override
    public void start(Stage primaryStage) {
               Button btn = new Button();
        btn.setText("START");
        Label nameLabel = new Label("Write your name");
        Label ipLabel = new Label("Write the IP address of your opponent");
        TextField ip = new TextField();
        TextField name = new TextField();
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                player2 = new Player(name.getText(), ip.getText(), primaryStage);
                int port = 5000;
                final int MAX_LEN = 20;
                // This is the assumed maximum byte length of the datagram to be received.
                try {
                  DatagramSocket  mySocket = new DatagramSocket(port);
                  // instantiates a datagram socket for receiving the data
                  byte[ ] buffer = new byte[MAX_LEN];
                  DatagramPacket datagram = new DatagramPacket(buffer, MAX_LEN);
                  mySocket.receive(datagram);
                  String message = new String(buffer);
                  System.out.println(message);
                  mySocket.close( );
                    mySocket = null;
                    datagram = null;
                } // end try
                catch (Exception ex) {
                  ex.printStackTrace( );
                }
                VBox vb = new VBox();
                vb.setPadding(new Insets(15, 15, 15, 15));
                vb.setSpacing(10);
                vb.getChildren().add(player2.getShipBoard().getGrid());
                vb.getChildren().add(new Label("Click to set the position of your ship\n\n Press SPACE to change the orientation"));
                Scene scene2 = new Scene(vb);
                primaryStage.setScene(scene2);
                int a = player2.shipDeployment();
            }
        });
        VBox vb = new VBox();
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        vb.setPadding(new Insets(15, 15, 15, 15));
        vb.setSpacing(10);
        vb.getChildren().add(nameLabel);
        vb.getChildren().add(name);
        vb.getChildren().add(ipLabel);
        vb.getChildren().add(ip);
        vb.getChildren().add(root);
        Scene scene = new Scene(vb, 300, 250);
        primaryStage.setTitle("1 BATTLESHIP");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
