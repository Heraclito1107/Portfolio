import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;

public class ChatClient
{
  public static Socket socket;
  public static DataInputStream inSocket;
  public static DataOutputStream outSocket;
  public static void main(String[] args)
  {
    try
    {

    socket = new Socket("127.0.0.1", 5000);
    inSocket = new DataInputStream(socket.getInputStream());
    outSocket = new DataOutputStream(socket.getOutputStream());
    Scanner scan = new Scanner(System.in);
    String name = scan.nextLine();
    outSocket.writeInt(101);
    outSocket.writeUTF(name);
    int n = inSocket.readInt();
    String a;
    for(int i = 0; i < n; i++)
      a = inSocket.readUTF();
    n = inSocket.readInt();
    for(int i = 0; i < n; i++)
      a = inSocket.readUTF();
    outSocket.writeInt(103);
    outSocket.writeUTF("Prueba");
    do {
      System.out.println("1. Message");
      System.out.println("2. New ChatRoom");
      System.out.println("3. Join Room");
      System.out.println("4. Leave room");
      System.out.println("5. Reject join request");
      System.out.println("6. Accept join request");
      System.out.println("7. Delete user from room");
      System.out.println("8. Add user to room");
      action(scan.nextInt());

    } while (true);
  }
      catch(Exception ex){
          System.out.println("You must first start the server socket");
          System.out.println("(YourServer.java) at the command prompt.");
          System.out.println(ex);
      }

  }

  public static void action(int code) throws IOException
  {
    Scanner scan = new Scanner(System.in);
    if(code == 1)
    {
      String s = scan.nextLine();
      String m = scan.nextLine();
      outSocket.writeInt(107);
      outSocket.writeUTF(s);
      outSocket.writeUTF(m);
    }
    if(code == 2)
    {
      String m = scan.nextLine();
      outSocket.writeInt(102);
      outSocket.writeUTF(m);
    }
    if(code == 3)
    {
      String m = scan.nextLine();
      outSocket.writeInt(103);
      outSocket.writeUTF(m);
    }
    if(code == 4)
    {
      String m = scan.nextLine();
      outSocket.writeInt(104);
      outSocket.writeUTF(m);
    }
    if(code == 5)
    {
      while(inSocket.available()!=0)
      {
        System.out.print((char)inSocket.read());
      }
      System.out.println("ready");
      int a = inSocket.readInt();
      String user = inSocket.readUTF();
      String room = inSocket.readUTF();
      outSocket.writeInt(109);
      outSocket.writeUTF(room);
      outSocket.writeUTF(user);
      outSocket.writeBoolean(false);
    }
    if(code == 6)
    {
      while(inSocket.available()!=0)
      {
        System.out.print((char)inSocket.read());
      }
      System.out.println("ready");
      int a = inSocket.readInt();
      String user = inSocket.readUTF();
      String room = inSocket.readUTF();
      outSocket.writeInt(109);
      outSocket.writeUTF(room);
      outSocket.writeUTF(user);
      outSocket.writeBoolean(true);
    }
    if(code == 7)
    {
      String s = scan.nextLine();
      String m = scan.nextLine();
      outSocket.writeInt(108);
      outSocket.writeUTF(s);
      outSocket.writeUTF(m);
    }
    if(code == 8)
    {
      String s = scan.nextLine();
      String m = scan.nextLine();
      outSocket.writeInt(105);
      outSocket.writeUTF(s);
      outSocket.writeUTF(m);
    }
  }
}
