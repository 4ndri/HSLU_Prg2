/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.net;

import java.io.Serializable;

/**
 *
 * @author Andri
 */
public class MsgError extends Message implements Serializable {    
    
    public MsgError(String strError){
        super(99,strError);
    }
    
}
