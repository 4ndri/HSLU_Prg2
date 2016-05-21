/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.oponent;

import battleship.engine.*;
import battleship.engine.actions.*;
import battleship.grid.*;
import battleship.net.*;


/**
 *
 * @author Andri
 */
public class NetOponent implements IOponent{
    Connection aConnection;
    
    public NetOponent(Connection aConnection){
        this.aConnection=aConnection;        
    }
    
    @Override
    public void init(){
        this.aConnection.oponent=this;
        
        
    }
    
    
    @Override
    public boolean sendBombToOponent(Bomb aBomb) {
        return this.aConnection.sendMessage(new MsgBomb(aBomb));
    }
    
    @Override
    public boolean sendBombReportToOponent(BombReport aReport){
        return this.aConnection.sendMessage(new MsgBombReport(aReport));
    }
    
    @Override
    public void bombFromOponent(Bomb aBomb) {
        Engine engine = Engine.getEngine();
        engine.pushAction(new ActReceiveBomb(aBomb));
        
    }

    @Override
    public void bombReportFromOponent(BombReport aReport) {
        Engine engine = Engine.getEngine();
        engine.pushAction(new ActReceiveBombReport(aReport));
    }

    @Override
    public boolean sendReadyToOponent() {
        
        return this.aConnection.sendMessage(new Message(3));
    }

    @Override
    public void readyFromOponent() {
        Engine engine = Engine.getEngine();
        engine.pushAction(new EngineAction(1));
    }
    
    
}
