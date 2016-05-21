package battleship.engine.actions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import battleship.engine.Engine;
import battleship.grid.*;

/**
 *
 * @author Andri
 */
public class ActReceiveBomb extends ActionCounter implements IEngineAction{
    Bomb bomb;
    
    public ActReceiveBomb(Bomb aBomb){
        super("ActReceiveBomb");
        bomb=aBomb;
    }
    
    @Override
    public boolean execute() {
        counter++;
        System.out.println(Thread.currentThread().getName()+ ":  We're bombed!!!");
        Engine engine=Engine.getEngine();
        BombReport report=engine.game.player.receiveBomb(bomb);
        if(report==null){
            return false;
        }
        return engine.oponent.sendBombReportToOponent(report);
    }
    
}
