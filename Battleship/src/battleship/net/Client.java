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
public class Client {
    
    Connection aConnection;

    public Client(Connection aConnection) {
        this.aConnection = aConnection;
    }
    
    public void start() throws IOException{
        Socket socket = new Socket(aConnection.ip, aConnection.port);
        aConnection.socket=socket;
        System.out.println("Client opened socket!");
        aConnection.messageSender=new MessageSender(aConnection);
        aConnection.messageListener=new MessageListener(aConnection);
        aConnection.messageSender.start();
        aConnection.messageListener.start();
        
        aConnection.setOnline(true);
        Runtime.getRuntime().addShutdownHook(
            new Thread() {
                public void run() {
                    System.out.println("Strg+C, cancel connection");
                    aConnection.interrupt();
                }
            }
        );
        
    }
    
}

