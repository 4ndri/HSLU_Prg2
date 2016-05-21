/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.net;

import java.io.*;

/**
 *
 * @author Andri
 */
public class Message implements Serializable{
    
    public int type;
    public String textMessage;
    
    public Message(){
        
        
    }
    public Message(String text){
        this.type=0;
        this.textMessage=text;
    }
    public Message(int type){
        this.type=type;
        this.textMessage="";
    }
    public Message(int type,String textMessage){
        this.textMessage=textMessage;
        this.type=type;
    }    
}
