/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.engine.actions;

import battleship.engine.Engine;
import battleship.grid.Bomb;
import battleship.grid.BombReport;
import java.util.Random;

/**
 *
 * @author Andri
 */
public class ActStartPlaying extends ActionCounter implements IEngineAction{

    @Override
    public boolean execute() {
        counter++;
        Engine engine=Engine.getEngine();
        if(!engine.isHost){
            return true;
        }
        Random generator = new Random();
        boolean turn=generator.nextBoolean();
        
        if(turn){
            engine.game.player.yourTurn=true;
        }else{
            BombReport report=new BombReport(new Bomb(-1,-1));
            report.bombOnShip=true;
            return engine.oponent.sendBombReportToOponent(report);
        }
        
        return true;
    }
    
}
