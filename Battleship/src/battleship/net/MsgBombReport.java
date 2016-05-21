/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.net;

import battleship.grid.*;
import java.io.Serializable;

/**
 *
 * @author Andri
 */
public class MsgBombReport  extends Message implements Serializable {
    public BombReport bombReport;
    
    public MsgBombReport(BombReport areport){
        super(2,"BombReportMessage");
        bombReport=areport;
    }
    
}