/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.net;

import battleship.grid.*;

/**
 *
 * @author Andri
 */
public class TestServer {
    static int port=12345;
    public static void main(String[] args){
        
        Connection aConnection=Connection.openConnection(port);
        
        aConnection.sendMessage(new Message(0,"message from the server"));
        
        aConnection.sendMessage(new MsgBomb(new Bomb(1,2)));
        BombReport report=new BombReport(new Bomb(5,6));
        report.bombOnShip=true;
        report.shipDestroyed=false;
        report.gameOver=false;
        aConnection.sendMessage(new MsgBombReport(report));
        
    }
}
