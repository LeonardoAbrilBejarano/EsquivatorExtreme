package Sockets.Server;
// ThreadedScoreHandler.java
// Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th

/* A threaded handler, called by ThreadedScoreServer to process 
   a client.

   Understood input messages:
	get						-- returns the high score list
    score name & score &	-- add the score for name
	bye						-- terminates the client link
*/

import Graficos.Universo;
import java.io.*;
import java.net.*;


public class ThreadedScoreHandler extends Thread
{
  private Socket clientSock;
  private String cliAddr;
  private Universo u;

  public ThreadedScoreHandler(Socket s, String cliAddr, Universo u)
  {
    clientSock = s;  
    this.cliAddr = cliAddr;
    this.u = u;
    System.out.println("Client connection from " + cliAddr);
  }


  public void run()
  {
    try {
      // Get I/O streams from the socket
      BufferedReader in  = new BufferedReader( 
	  		new InputStreamReader( clientSock.getInputStream() ) );
      PrintWriter out = 
			new PrintWriter( clientSock.getOutputStream(), true );  // autoflush

      // interact with a client
      processClient(in, out);
 
      // Close client connection
      clientSock.close();
      System.out.println("Client (" + cliAddr + ") connection closed\n");
    }
    catch(Exception e)
    {  System.out.println(e);  }
  }  // end of run()


   private void processClient(BufferedReader in, PrintWriter out)
   // Stop when the input stream closes (is null) or "bye" is sent
   // Otherwise pass the input to doRequest()
   {
     String line;
     boolean done = false;
     try {
       while (!done) {
         if((line = in.readLine()) == null)
           done = true;
         else {
           System.out.println("Client msg: " + line);
           if (line.trim().equals("bye"))
             done = true;
           else 
             doRequest(line, out);
         }
       }
     }
     catch(IOException e)
     {  System.out.println(e);  }
   }  // end of processClient()


  private void doRequest(String line, PrintWriter out)
  /*  The input line can be one of:
             "score name & score &"
      or     "get"
  */
  {
      boolean msg=false;
    if (line.trim().toLowerCase().equals("bot")) {
      System.out.println("Processing 'bot'");
      u.playermoveBot();
      msg=true;
    }
    if (line.trim().toLowerCase().equals("top")) {
      System.out.println("Processing 'top'");
      u.playermoveTop();
      msg=true;
    }
    if (line.trim().toLowerCase().equals("right")) {
      System.out.println("Processing 'right'");
      u.playermoveRight();
      msg=true;
    }
    if (line.trim().toLowerCase().equals("left")) {
      System.out.println("Processing 'left'");
      u.playermoveLeft();
      msg=true;
    }
    if(!msg){
        System.out.println("Ignoring input line");
    }
      
  }  // end of doRequest()


}  // end of ThreadedScoreHandler class