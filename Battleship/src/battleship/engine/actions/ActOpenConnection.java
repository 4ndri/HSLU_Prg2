/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.engine.actions;

import battleship.engine.Engine;
import battleship.net.Connection;
import battleship.oponent.NetOponent;

/**
 *
 * @author Andri
 */
public class ActOpenConnection extends ActionCounter implements IEngineAction{

    private final int port;
    public ActOpenConnection(int aport){
        port=aport;
    }

    @Override
    public boolean execute() {
        try{
            counter++;
            Engine engine=Engine.getEngine();
            engine.connection=Connection.openConnection(port);
            NetOponent netOponent=new NetOponent(engine.connection);
            netOponent.init();
            engine.oponent=netOponent;
            engine.isHost=true;
            return true;
        }catch(Exception ex){
            return false;
        }
        
    }
    
}
