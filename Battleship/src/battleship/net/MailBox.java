/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.net;

import java.util.ArrayList;

/**
 *
 * @author Andri
 */
public class MailBox {
    
    private final ArrayList<Message> outbox;
    
    private final ArrayList<Message> inbox;
    
    public MailBox(){
        this.inbox=new ArrayList<>();
        this.outbox=new ArrayList<>();
    }
    
    
    public synchronized Message dequeueInboxMessage(){
        
        if(inbox.size()>0){
            return inbox.remove(0);
        }
        return null;
    }
    public synchronized Message waitForNextInboxMessage()throws InterruptedException{
        while(inbox.isEmpty()){
            wait();
        }
        return inbox.remove(0);
    }
    
    private synchronized void enqueueInboxMessage(Message aMessage){        
        inbox.add(aMessage);
        notifyAll();
    }
    public synchronized Message dequeueOutboxMessage(){
        
        if(outbox.size()>0){            
            return outbox.remove(0);
        }
        return null;
    }
    
    public synchronized Message waitForNextOutboxMessage()throws InterruptedException{
        while(outbox.isEmpty()){
            wait();
        }
        return outbox.remove(0);
    }
    
    
    private synchronized void enqueueOutboxMessage(Message aMessage){        
        outbox.add(aMessage);
        notifyAll();
    }
    
    public void sendMessage(Message aMessage){
        this.enqueueOutboxMessage(aMessage);
    }
    
    
    public void receiveMessage(Message aMessage){
        this.enqueueInboxMessage(aMessage);
    }
    
    
}
