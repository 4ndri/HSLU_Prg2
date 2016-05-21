/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.net;

import battleship.grid.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andri
 */
public class TestClient {
    static int port=12345;
    static String strIP="127.0.0.1";
    static InetAddress ip;
    public static void main(String[] args){
        try {
            ip=InetAddress.getByName(strIP);
        } catch (UnknownHostException ex) {
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(ip!=null){
            Connection aConnection=Connection.joinConnection(port,ip);
            
            aConnection.sendMessage(new Message(0,"message from client"));
            
            aConnection.sendMessage(new MsgBomb(new Bomb(3,4)));
            BombReport report=new BombReport(new Bomb(3,4));
            report.bombOnShip=true;
            report.shipDestroyed=true;
            report.gameOver=false;
            aConnection.sendMessage(new MsgBombReport(report));
        }
    }
}
