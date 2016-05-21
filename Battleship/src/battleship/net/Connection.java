/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.net;

import battleship.engine.Engine;
import battleship.oponent.*;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Andri
 */

public class Connection {
    public NetOponent oponent;
    protected MailBox mailbox;
    boolean mrunning;
    boolean online;
    private boolean misServer;
    
    
    public boolean isRunning(){
        return mrunning;
    }
    public boolean isOnline(){
        return online;
    }
    public synchronized void setOnline(boolean value){
        online=value;
        Engine engine=Engine.getEngine();
        engine.game.setConnectionOpened(online);
    }
    public synchronized void setRunning(boolean value){
        mrunning=value;
    }
    public boolean isServer() {
        return misServer;
    }
    private Server server;
    private Client client;
    
    public MessageListener messageListener = null;
    public MessageSender messageSender = null;
    private MessageProcessor messageProcessor=null;
    
    Socket socket;
    InetAddress ip;
    public int port;

    
    private Connection(int port){
        online=false;
        this.port=port;
        mailbox=new MailBox();
    }
    private Connection(int port,InetAddress ip){
        this(port);
        this.ip=ip;
    }
    
    public static Connection openConnection(int port){
        Connection aConnection=new Connection(port);
        aConnection.messageProcessor=new MessageProcessor(aConnection);
        aConnection.messageProcessor.start();
        
        aConnection.misServer=true;
        aConnection.server=new Server(aConnection);
        try{
            aConnection.server.start();
        }catch(Exception ex){
            aConnection.mrunning=false;
        }
        return aConnection;
    }
    
    public static Connection joinConnection(int port,InetAddress ip){
        Connection aConnection=new Connection(port,ip);
        aConnection.messageProcessor=new MessageProcessor(aConnection);
        aConnection.messageProcessor.start();
        aConnection.misServer=false;
        aConnection.client=new Client(aConnection);
        try{
            aConnection.client.start();
        }catch(Exception ex){
            aConnection.mrunning=false;
        }
        return aConnection;
    }
    
    public boolean sendMessage(Message message){
        if(!online){
            return false;
        }
        this.mailbox.sendMessage(message);
        
        return true;
    }
    
    
    public synchronized void interrupt(){
        online=false;
        if(messageListener!=null){
            messageListener.interrupt();
        }
        if(messageSender!=null){
            messageSender.interrupt();
        }
        if(messageProcessor!=null){
            messageProcessor.interrupt();
        }
        if(socket!=null){
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(server!=null){
            server.close();
        }
    }
    
    
    
    
}
