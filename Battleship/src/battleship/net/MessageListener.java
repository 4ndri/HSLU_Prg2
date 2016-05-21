/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.net;

import java.io.*;
import java.net.*;

/**
 *
 * @author Andri
 */


public class MessageListener extends Thread {
    private final MailBox mailbox;
    private final Connection aConnection;
    private final ObjectInputStream mIn;
    
    public MessageListener(Connection aConnection) throws IOException {
        this.aConnection = aConnection;
        mailbox = aConnection.mailbox;
        Socket socket = aConnection.socket;
        InputStream is=socket.getInputStream();
        mIn = new ObjectInputStream(is);
    }
    /**
     * Until interrupted, reads messages from the client socket, forwards them
     * to the server dispatcher's queue and notifies the server dispatcher.
     */
    @Override
    public void run() {
        try {
            System.out.println("MessageListener started");
            while (!isInterrupted()) {
                
                Message message=null;
                try{
                    message=(Message)mIn.readObject();
                }catch (ClassNotFoundException ex) {
                    
                }
                
                
                if (message == null) {
                    break;
                }
                System.out.println("receiving message");
                mailbox.receiveMessage(message);
            }
        } catch (IOException ioex) {
            System.out.println("Error in MessageListener");
           // Problem reading from socket (communication is broken)
        } 
        // Communication is broken. Interrupt both listener and sender threads
        aConnection.interrupt();
    }
}
