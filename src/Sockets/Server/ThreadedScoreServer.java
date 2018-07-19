package Sockets.Server;
// ThreadedScoreServer.java
// Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th

/* A threaded server that stores a client's score (and name) in a
   list of top-10 high scores.

   The list is maintained in a file SCORFN, and loaded when the
   server starts.
   The server is terminated with a ctrl-C

   The server processes a client by creating a 
   ThreadedScoreHandler object.

   Derived from ThreadedEchoServer.java (Listing 3-5, p.161) in
      Core Java 2, Volume II -- Advanced Features
      Horstmann and Cornell
      Sun Microsystems Press, 2000, 4th Edition
 */
// import java.io.*;
import Graficos.Universo;
import java.net.*;

public class ThreadedScoreServer implements Runnable {

    private static final int PORT = 1234;
    private Universo u;

    public ThreadedScoreServer(Universo u) // Concurrently process clients forever
    {
        this.u = u;

    }  // end of ThreadedScoreServer()

    // -----------------------------------
    @Override
    public void run() {

        try {
            ServerSocket serverSock = new ServerSocket(PORT);
            Socket clientSock;
            String cliAddr;

            while (true) {
                System.out.println("Waiting for a client...");
                clientSock = serverSock.accept();
                cliAddr = clientSock.getInetAddress().getHostAddress();
                new ThreadedScoreHandler(clientSock, cliAddr, u).start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

} // end of ThreadedScoreServer class

