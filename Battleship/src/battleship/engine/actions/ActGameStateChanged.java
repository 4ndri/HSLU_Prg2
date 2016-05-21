/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.engine.actions;

import battleship.engine.Engine;

/**
 *
 * @author Andri
 */
public class ActGameStateChanged  extends ActionCounter implements IEngineAction{
    
    
    
    @Override
    public boolean execute() {
        counter++;
        Engine engine=Engine.getEngine();
        engine.gameStateChanged();
        
        return true;
    }
    
}
