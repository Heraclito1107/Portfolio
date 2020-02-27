/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclientui;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import static chatclientui.ChatClientUI.menu;
import static chatclientui.ChatClientUI.nickname;
import java.awt.Rectangle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Heraclito
 */
public class ChatRoom {
    //ArrayList<Label> messages;
    //ArrayList<Button> members;
    public VBox messages;
    public VBox members;
    private Label newMessages;
    private Boolean admin;
    private String name;
    DataOutputStream outSocket;
    public ChatRoom(String name, DataOutputStream outSocket) {
        this.name = name;
        this.outSocket = outSocket;
        messages = new VBox();
        members = new VBox();
        admin = false;
        
        newMessages = new Label();
        newMessages.setTextFill(Paint.valueOf("black"));
        newMessages.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        newMessages.setText("New messages");
        newMessages.setStyle("-fx-font-weight: bold");
        newMessages.setAlignment(Pos.CENTER_RIGHT);
        newMessages.setVisible(false);
    }

    public String getName() {
        return name;
    }
    
    public void getNotification(String notification, Boolean alert)
    {
        Label l = new Label(notification);
        if(alert)
            l.setTextFill(Paint.valueOf("RED"));
        else
            l.setTextFill(Paint.valueOf("GOLD"));
        l.setStyle("-fx-font-weight: bold;");
        l.setAlignment(Pos.CENTER);
        HBox hb = new HBox();
        hb.setPadding(new Insets(5, 10, 5, 10));
        hb.setAlignment(Pos.CENTER);
        hb.autosize();
        hb.getChildren().add(l);
        messages.getChildren().add(hb);
    }
    
    public void updateMessages(String message)
    {
        Label l = new Label(message);
        l.setTextFill(Paint.valueOf("blue"));
        l.setStyle("-fx-font-weight: bold;");
        l.setAlignment(Pos.CENTER_LEFT);
        HBox hb = new HBox();
        hb.setPadding(new Insets(5, 10, 5, 10));
        hb.setAlignment(Pos.CENTER_LEFT);
        hb.autosize();
        hb.getChildren().add(l);
        messages.getChildren().add(hb);
    }
    
    public void myMessage(String message)
    {
        Label l = new Label("You:" + message);
        l.setTextFill(Paint.valueOf("LIGHTSEAGREEN"));
        l.setStyle("-fx-font-weight: bold;");
        l.setAlignment(Pos.CENTER_RIGHT);
        HBox hb = new HBox();
        hb.setPadding(new Insets(5, 10, 5, 10));
        hb.setAlignment(Pos.CENTER_RIGHT);
        hb.autosize();
        hb.getChildren().add(l);
        messages.getChildren().add(hb);
    }
    
    public void updateMembers(String memberName)
    {
        Button l = new Button(memberName);
        if(getAdmin() && memberName.equals(nickname))
            l.setText(memberName + " (admin)");
        l.setTextFill(Paint.valueOf("black"));
        l.setStyle("-fx-font-weight: bold;");
        l.setAlignment(Pos.CENTER_LEFT);
        l.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if(getAdmin())
                {
                    try {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Delete user");
                        alert.setHeaderText("Do you want to delete " + memberName + " from " + getName() + "?");
                        ButtonType okButton = new ButtonType("Delete", ButtonBar.ButtonData.YES);
                        ButtonType noButton = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
                        alert.getButtonTypes().setAll(okButton, noButton);
                        Boolean response = alert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES;
                        if(response)
                        {
                            outSocket.writeInt(108);
                            outSocket.writeUTF(getName());
                            outSocket.writeUTF(memberName);
                        }
                        
                    } catch (IOException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        HBox hb = new HBox();
        hb.setId(memberName);
        hb.setPadding(new Insets(10, 10, 10, 10));
        hb.setAlignment(Pos.CENTER_LEFT);
        hb.autosize();
        hb.getChildren().add(l);
        members.getChildren().add(hb);
    }
    
    public void deleteMember(String memberName)
    {
        for(int i = 0; i < members.getChildren().size(); i++)
        {
            if(members.getChildren().get(i).getId().equals(memberName))
            {
                members.getChildren().remove(i);
                break;
            }
        }
    }
    
    public Scene chatScene()
    {
        ScrollPane msgPane = new ScrollPane();
        msgPane.setFitToWidth(true);
        msgPane.setPrefSize(600, 350);
        msgPane.setContent(messages);
        
        ScrollPane membersPane = new ScrollPane();
        membersPane.setFitToWidth(true);
        membersPane.setPrefSize(200, 350);
        membersPane.setContent(members);
        
        VBox membersBox = new VBox();
        membersBox.setPadding(new Insets(10, 10, 10, 10));
        membersBox.setSpacing(10);
        membersBox.getChildren().add(new Label("Room members"));
        membersBox.getChildren().add(membersPane);
        
        VBox msgBox = new VBox();
        msgBox.setPadding(new Insets(10, 10, 10, 10));
        msgBox.setSpacing(10);
        msgBox.getChildren().add(new Label("Messages"));
        msgBox.getChildren().add(msgPane);
        
        HBox hb = new HBox();
        hb.setPadding(new Insets(15, 15, 15, 15));
        hb.setSpacing(10);
        hb.getChildren().add(membersBox);
        hb.getChildren().add(msgBox);
        
        TextField message = new TextField();
        message.setOnKeyPressed(sendMessage());
        
        Button menuBtn = new Button("Back to menu");
        menuBtn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                alertNewMessages(false);
                menu.stage.setTitle("Menu");
                menu.stage.setScene(menu.menuScene());
                menu.stage.show();
            }
        });
        
        Button leaveBtn = new Button("Leave room");
        leaveBtn.setId(name);
        leaveBtn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                try {
                    outSocket.writeInt(104);
                    outSocket.writeUTF(name);
                    
                } catch (IOException ex) {
                    Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(((Button)event.getSource()).getId());
                menu.stage.setTitle("Menu");
                menu.stage.setScene(menu.menuScene());
                menu.stage.show();
                //Platform.runLater(()->{
                menu.leaveRoom(((Button)event.getSource()).getId());
            //});
            }
        });
        
        Button addBtn = new Button("Add user");
        addBtn.setOnAction(addMember());
        
        Button deleteBtn = new Button("Delete room");
        deleteBtn.setId(name);
        deleteBtn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if(admin)
                {
                    try {
                        outSocket.writeInt(106);
                        outSocket.writeUTF(name);

                    } catch (IOException ex) {
                        Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(((Button)event.getSource()).getId());
                    menu.stage.setTitle("Menu");
                    menu.stage.setScene(menu.menuScene());
                    menu.stage.show();
                    //Platform.runLater(()->{
                    menu.leaveRoom(name);
                    menu.removeRoom(name);
                }
            }
        });
        
        HBox bttns = new HBox();
        bttns.setPadding(new Insets(15, 15, 15, 15));
        bttns.setSpacing(100);
        bttns.getChildren().add(menuBtn);
        bttns.getChildren().add(leaveBtn);
        bttns.getChildren().add(addBtn);
        bttns.getChildren().add(deleteBtn);
        
        StackPane header = new StackPane();
        header.setAlignment(Pos.CENTER_RIGHT);
        header.getChildren().add(newMessages);
               
        VBox vb = new VBox();
        vb.setPadding(new Insets(15, 15, 15, 15));
        vb.setSpacing(10);
        vb.getChildren().add(header);
        vb.getChildren().add(hb);
        vb.getChildren().add(message);
        vb.getChildren().add(bttns);
        
        Scene newScene = new Scene(vb, 900, 600);
        return newScene;
    }
    
    private EventHandler sendMessage(){
        EventHandler enter = new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke) {
                if(ke.getCode().equals(KeyCode.ENTER))
                {
                    String msg = ((TextField)ke.getSource()).getText();
                    ((TextField)ke.getSource()).setText("");
                    if(!msg.equals(""))
                    {
                        myMessage(msg);
                        try {
                            outSocket.writeInt(107);
                            outSocket.writeUTF(name);
                            outSocket.writeUTF(msg);
                        } catch (IOException ex) {
                            Logger.getLogger(ChatClientUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        };
        return enter;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public void alertNewMessages(Boolean nm)
    {
        if(nm)        
            newMessages.setVisible(true);        
        else
            newMessages.setVisible(false);
    }

    public EventHandler addMember()
    {
        EventHandler event = new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                ScrollPane sp = new ScrollPane();
                VBox vb = new VBox();
                menu.getConnectedUsers().getChildren().forEach((user) -> {
                    if(!isMember(user.getId()))
                    {
                        Button btn = new Button();
                        btn.setText(user.getId());
                        btn.setId(user.getId());
                        btn.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                
                                try {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Add user");
                                    alert.setHeaderText("Do you want to add " + ((Button)event.getSource()).getId() + " to " + getName() + "?");
                                    ButtonType okButton = new ButtonType("Add", ButtonBar.ButtonData.YES);
                                    ButtonType noButton = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
                                    alert.getButtonTypes().setAll(okButton, noButton);
                                    Boolean response = alert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES;
                                    if(response)
                                    {
                                        outSocket.writeInt(105);
                                        outSocket.writeUTF(getName());
                                        outSocket.writeUTF(((Button)event.getSource()).getId());
                                        menu.stage.setTitle(getName());
                                        menu.stage.setScene(chatScene());
                                        menu.stage.show();
                                    }
                                    
                                } catch (IOException ex) {
                                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            
                        });
                        vb.getChildren().add(btn);
                    }
                    
                });
                
                Button cancelBtn = new Button("Cancel");
                cancelBtn.setOnAction(new EventHandler<ActionEvent>() 
                {
                    @Override
                    public void handle(ActionEvent event) 
                    {
                        menu.stage.setTitle(getName());
                        menu.stage.setScene(chatScene());
                        menu.stage.show();
                    }
                });
                
                vb.setPadding(new Insets(15, 15, 15, 15));
                vb.setSpacing(10);
                sp.setContent(vb);
                
                StackPane root = new StackPane();
                root.getChildren().add(cancelBtn);
                
                VBox vb2 = new VBox();
                vb2.setPadding(new Insets(15, 15, 15, 15));
                vb2.setSpacing(10);
                vb2.getChildren().add(sp);
                vb2.getChildren().add(root);
                
                Scene scene = new Scene(vb2, 300, 400);
                menu.stage.setTitle("Add User");
                menu.stage.setScene(scene);
                menu.stage.show();
            }
             
        };
        
        return event;
    }
    
    private Boolean isMember(String memberName)
    {
        for(Node member : members.getChildren())
        {
            if(memberName.equals(member.getId()))
                return true;
        }
        return false;
    }
}
