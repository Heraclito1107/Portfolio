/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heraclito
 */
public class Room {
    UserThread admin;
    List<UserThread> members;
    public String name;

    public Room(UserThread admin, String name) {
        this.admin = admin;
        this.name = name;
        members = Collections.synchronizedList(new ArrayList<UserThread>());
        members.add(admin);
    }
    
    public void sendMessage(String mess, UserThread author)
    {
        for(int i = 0; i < members.size(); i++)
        {
            if(!members.get(i).equals(author))
            {
                try {
                    members.get(i).outSocket.writeInt(14);
                    members.get(i).outSocket.writeUTF(name);
                    members.get(i).outSocket.writeUTF(author.name + ": " +mess);
                } catch (IOException ex) {
                    Logger.getLogger(UserThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void addMember(UserThread member)
    {
        members.add(member);
    }
    
    public void removeMember(UserThread member) //String name)
    {
        members.remove(member);
    }
    
    public Boolean isMember(UserThread user)
    {
        if(members.contains(user))
            return true;
        return false;
    }
}
