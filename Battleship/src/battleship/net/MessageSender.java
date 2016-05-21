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


public class MessageSender extends Thread {

    private final Connection aConnection;
    private final ObjectOutputStream mOut;
    
    public MessageSender(Connection aConnection) throws IOException {
        this.aConnection = aConnection;
        Socket socket = aConnection.socket;
        mOut = new ObjectOutputStream(socket.getOutputStream());
    }
    
    
    /**
     *
     * Sends given message to the client's socket.
     *
     */
    private void sendMessageToClient(Message aMessage) throws IOException  {
        
        mOut.writeObject(aMessage);
        mOut.flush();
    }

    /**
     *
     * Until interrupted, reads messages from the message queue
     *
     * and sends them to the client's socket.
     *
     */
    @Override
    public void run() {

        try {
            System.out.println("MessageSender started");
            while (!isInterrupted()) {
                Message message=aConnection.mailbox.waitForNextOutboxMessage();
//                Message message = getNextMessageFromQueue();
                System.out.println("Sending Message!");
                sendMessageToClient(message);

            }

        } catch (Exception e) {
            System.out.println("Error in MessageSender");
           // Commuication problem
        }

        // Communication is broken. Interrupt both listener and sender threads
        aConnection.interrupt();
    }

}
