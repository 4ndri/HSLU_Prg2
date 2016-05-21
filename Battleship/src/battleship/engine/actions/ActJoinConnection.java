/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.engine.actions;

import battleship.engine.Engine;
import battleship.net.Connection;
import battleship.oponent.NetOponent;
import java.net.InetAddress;

/**
 *
 * @author Andri
 */
public class ActJoinConnection extends ActionCounter implements IEngineAction{

    private final int port;
    private final InetAddress ip;
    public ActJoinConnection(int aport, InetAddress aip){
        port=aport;
        ip=aip;
    }

    @Override
    public boolean execute() {
        try{
            counter++;
            Engine engine=Engine.getEngine();
            engine.connection=Connection.joinConnection(port,ip);
            NetOponent netOponent=new NetOponent(engine.connection);
            netOponent.init();
            engine.oponent=netOponent;
            engine.isHost=false;
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    
}

