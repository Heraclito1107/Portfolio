/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclientui;

import static chatclientui.ChatClientUI.nickname;
import java.io.DataInputStream;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
/**
 *
 * @author Heraclito
 */
public class Menu {
    ArrayList<ChatRoom> myChatRooms;
    VBox roomsBox;
    VBox myRoomsBox;
    VBox usersBox;
    public Stage stage;
    DataOutputStream outSocket;
    
    public Menu(Stage stage, DataOutputStream outSocket)
    {
        this.stage = stage;
        this.outSocket = outSocket;
        myChatRooms = new ArrayList<>();
        roomsBox = new VBox();
        myRoomsBox = new VBox();
        usersBox = new VBox();
    }
    
    public void addRoom(String roomName)
    {
        Button btn = new Button(roomName);
        btn.setId(roomName);
        btn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                try {
                    outSocket.writeInt(103);
                    outSocket.writeUTF(((Button)event.getSource()).getId());
                } catch (IOException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        HBox hb = new HBox();
        hb.setId(roomName);
        hb.setPadding(new Insets(10, 10, 10, 10));
        hb.setAlignment(Pos.CENTER_LEFT);
        hb.autosize();
        hb.getChildren().add(btn);
        roomsBox.getChildren().add(hb);
    }
    
    public void addUser(String userName)
    {
        Label l = new Label(userName);
        l.setId(userName);
        l.setTextFill(Paint.valueOf("black"));
        l.setStyle("-fx-font-weight: bold;");
        l.setAlignment(Pos.CENTER_LEFT);
        HBox hb = new HBox();
        hb.setId(userName);
        hb.setPadding(new Insets(10, 10, 10, 10));
        hb.setAlignment(Pos.CENTER_LEFT);
        hb.autosize();
        hb.getChildren().add(l);
        usersBox.getChildren().add(hb);
    }
    
    public void removeUser(String userName)
    {
        for(Node user: usersBox.getChildren())
        {
            if(user.getId().equals(userName))
            {
                usersBox.getChildren().remove(user);
                break;
            }
        }
    }
    
    public void removeRoom(String roomName)
    {
        for(Node room: roomsBox.getChildren())
        {
            if(room.getId().equals(roomName))
            {
                roomsBox.getChildren().remove(room);
                break;
            }
        }
    }
    
    public void addMyRoom(ChatRoom cr)
    {
        myChatRooms.add(cr);
        Button btn = new Button(cr.getName());
        btn.setId(cr.getName());
        btn.setOnAction(openChatRoom(cr.getName()));
        myRoomsBox.getChildren().add(btn);
    }
    
    public void leaveRoom(String roomName)
    {
        myChatRooms.remove(getChatRoombyName(roomName));
        for(Node room : myRoomsBox.getChildren())
        {
            if(room.getId().equals(roomName))
            {
                myRoomsBox.getChildren().remove(room);
                break;
            }
        }
    }
    
    private EventHandler openChatRoom(String roomName)
    {
        EventHandler event = new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                ChatRoom openCR = getChatRoombyName(((Button)event.getSource()).getId());
                stage.setTitle(openCR.getName());
                stage.setScene(openCR.chatScene());
                stage.show();
            }
             
        };
        
        return event;
    }
    
    public Scene menuScene()
    {
        Label headerLab = new Label("Welcome " + nickname);
        headerLab.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
        StackPane header = new StackPane();
        header.getChildren().add(headerLab);
        
        ScrollPane roomList = new ScrollPane();
        roomList.setFitToWidth(true);
        roomList.setPrefWidth(180);
        roomList.setPrefHeight(250);
        roomList.setContent(roomsBox);
        
        ScrollPane userList = new ScrollPane();
        userList.setFitToWidth(true);
        userList.setPrefWidth(180);
        userList.setPrefHeight(250);
        userList.setContent(usersBox);
        
        ScrollPane myRoomsList = new ScrollPane();
        myRoomsList.setFitToWidth(true);
        myRoomsList.setPrefWidth(180);
        myRoomsList.setPrefHeight(250);
        myRoomsList.setContent(myRoomsBox);
        
        Button create = new Button("Create new ChatRoom");
        create.setOnAction(createChatRoom());
        
        VBox left = new VBox();
        left.setPadding(new Insets(10, 10, 10, 10));
        left.setSpacing(10);
        left.getChildren().add(new Label("Rooms"));
        left.getChildren().add(roomList);
        
        VBox center = new VBox();
        center.setPadding(new Insets(10, 10, 10, 10));
        center.setSpacing(10);
        center.getChildren().add(new Label("Your rooms"));
        center.getChildren().add(myRoomsList);
        center.getChildren().add(create);
        
        VBox right = new VBox();
        right.setPadding(new Insets(10, 10, 10, 10));
        right.setSpacing(10);
        right.getChildren().add(new Label("Online users"));
        right.getChildren().add(userList);
        
        HBox hb = new HBox();
        hb.setSpacing(10);
        hb.getChildren().add(left);
        hb.getChildren().add(center);
        hb.getChildren().add(right);
        
        VBox vb = new VBox();
        vb.setPadding(new Insets(15, 15, 15, 15));
        vb.setSpacing(10);
        vb.getChildren().add(header);
        vb.getChildren().add(hb);
        Scene newScene = new Scene(vb, 600, 600);
        return newScene;
    }
    
    public ChatRoom getChatRoombyName(String crName)
    {
        for(int i = 0; i < myChatRooms.size(); i++)
        {
            if(myChatRooms.get(i).getName().equals(crName))
                return myChatRooms.get(i);
        }
            
        return null;
    }
    
    public EventHandler createChatRoom()
    {
        EventHandler event = new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                Button btn = new Button();
                btn.setText("Create");
                Label nameLabel = new Label("ChatRoom's name");
                TextField name = new TextField();
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if(!name.getText().equals(""))
                        {
                            if(!roomExists(name.getText()))
                            {
                                try {
                                    outSocket.writeInt(102);
                                    outSocket.writeUTF(name.getText());
                                } catch (IOException ex) {
                                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ChatRoom newCR = new ChatRoom(name.getText(), outSocket);
                                newCR.setAdmin(true);
                                newCR.updateMembers(nickname);
                                addMyRoom(newCR);
                            }
                            else
                            {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Room name in use");
                                alert.setHeaderText("There's another room registered inder this name, please try again");
                                alert.showAndWait();
                            }
                            stage.setTitle("Menu");
                            stage.setScene(menuScene());
                            stage.show();
                        }
                    }
                });
                VBox vb = new VBox();
                StackPane root = new StackPane();
                root.getChildren().add(btn);
                vb.setPadding(new Insets(15, 15, 15, 15));
                vb.setSpacing(10);
                vb.getChildren().add(nameLabel);
                vb.getChildren().add(name);
                vb.getChildren().add(root);
                Scene scene = new Scene(vb, 300, 250);
                stage.setTitle("Create new ChatRoom");
                stage.setScene(scene);
                stage.show();
            }
             
        };
        
        return event;
    }
    
    public void joinRequest(String user, String room)
    {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Joining request");
            alert.setHeaderText(user + " wants to join " + room);
            ButtonType okButton = new ButtonType("Accept", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("Reject", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            Boolean response = alert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES;
            outSocket.writeInt(109);
            outSocket.writeUTF(room);
            outSocket.writeUTF(user);
            outSocket.writeBoolean(response);
            
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public VBox getConnectedUsers()
    {
        return usersBox;
    }
    
    private Boolean roomExists(String roomName)
    {
        for(Node room: roomsBox.getChildren())
        {
            if(room.getId().equals(roomName))
                return true;
        }
        return false;
    }
}
