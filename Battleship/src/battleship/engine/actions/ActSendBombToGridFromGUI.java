/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.engine.actions;

import battleship.engine.Engine;
import battleship.grid.Bomb;

/**
 *
 * @author Andri
 */
public class ActSendBombToGridFromGUI extends ActionCounter implements IEngineAction {
    Bomb bomb;
    public ActSendBombToGridFromGUI(Bomb abomb){
        super("ActSendBombToGridFromGUI");
        bomb=abomb;
    }

    @Override
    public boolean execute() {
        counter++;
        Engine engine=Engine.getEngine();
        engine.game.player.sendBomb(bomb);
        return true;
    }
    
    
}
