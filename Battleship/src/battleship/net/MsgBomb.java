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
public class MsgBomb extends Message implements Serializable {
    public Bomb bomb;
    
    public MsgBomb(Bomb abomb){
        super(1,"BombMessage");
        bomb=abomb;
    }
    
}
