package Sockets.Cliente;
// ScoreClient.java
// Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th
/*
  A test rig for ScoreServer with a GUI interface
  The client can send a name/score, and ask for the 
  current high scores. Clicking the close box of the
  window breaks the network link.

  Or we could just use:
     telnet localhost 1234
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class ScoreClient 
{
  private static final int PORT = 1234;     // server details
  private static final String HOST = "172.16.6.173";

  private Socket sock;
  private BufferedReader in;     // i/o for the client
  private PrintWriter out;  




  public ScoreClient()
  {
      Scanner sc= new Scanner(System.in);
      
     makeContact();
     while(true){
         System.out.println("Waiting for response");
         this.sendGet(sc.next());
     }

  } // end of ScoreClient();



  private void closeLink()
  {
    try {
      out.println("bye");    // tell server that client is disconnecting
      sock.close();
    }
    catch(Exception e)
    {  System.out.println( e );  }

    System.exit( 0 ); 
  }

    
  private void makeContact()
  {
    try {
      sock = new Socket(HOST, PORT);
      in  = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
      out = new PrintWriter( sock.getOutputStream(), true );  // autoflush
    }
    catch(Exception e)
    {  System.out.println(e);  }
  }  // end of makeContact()

   private void sendGet(String txt)
   {
   // Send "get" command, read response and display it
   // Response should be "HIGH$$ n1 & s1 & .... nN & sN & "
     try {
       out.println(txt);
     }
     catch(Exception ex)
     {  
       System.out.println("Problem obtaining high scores\n");  
     }
   }  // end of sendGet()



  // ------------------------------------

  public static void main(String args[]) 
  {  new ScoreClient();  }

 

} // end of ScoreClient class

