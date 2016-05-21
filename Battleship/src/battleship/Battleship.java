/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship;

import battleship.engine.Engine;
import static battleship.engine.Engine.getEngine;

/**
 *
 * @author bruno
 */
public class Battleship {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Engine engine=getEngine();
        
        engine.start();
    }
    
}
