/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.engine.actions;

import battleship.engine.Engine;
import battleship.oponent.CompOponent;

/**
 *
 * @author Andri
 */
public class ActSetCompOponent  extends ActionCounter implements IEngineAction{

    @Override
    public boolean execute() {
        counter++;
        Engine engine=Engine.getEngine();
        CompOponent compOponent=new CompOponent();
        compOponent.init();
        engine.oponent=compOponent;
        engine.isHost=true;
        return true;
    }
    
}
