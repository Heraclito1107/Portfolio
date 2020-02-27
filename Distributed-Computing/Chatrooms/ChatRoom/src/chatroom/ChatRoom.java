/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Heraclito
 */
public class ChatRoom {

    /**
     * @param args the command line arguments
     */
    public static List<UserThread> connectedUsers;
    public static List<Room> activeRooms;
    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5000);
        connectedUsers = Collections.synchronizedList(new ArrayList<UserThread>());
        activeRooms = Collections.synchronizedList(new ArrayList<Room>());
        while(true)
        {
            Socket ss = null;
            try {
                if(!server.isClosed())
                {
                    ss = server.accept();
                    UserThread current  = new UserThread(ss, new DataInputStream(ss.getInputStream()), new DataOutputStream(ss.getOutputStream()));
                    connectedUsers.add(current);
                    Thread t = new Thread(current);
                    //Thread t = new Thread(new UserThread(ss, new DataInputStream(ss.getInputStream()), new DataOutputStream(ss.getOutputStream())));
                    t.start();
                }
                else
                    break;
                
            } catch (IOException ex) {
            }
        }
    }
    
}
