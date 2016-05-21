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
public class ActPlaceShip extends ActionCounter implements IEngineAction{
    int x,y,shipLength;
    boolean horizontal;
    public ActPlaceShip(int ax,int ay,boolean ahorizontal,int ashipLength){
        x=ax;
        y=ay;
        horizontal=ahorizontal;
        shipLength=ashipLength;
    }
    @Override
    public boolean execute() {
        counter++;
        Engine engine=Engine.getEngine();
        if(engine.game.player.placeShip(x, y, horizontal, shipLength)){
            
        }else{
            engine.handleError("Ship can't be placed there!");
        }
        
        return true;
        
    }
    
}
