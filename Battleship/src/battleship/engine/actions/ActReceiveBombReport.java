/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.engine.actions;

import battleship.engine.Engine;
import battleship.grid.BombReport;

/**
 *
 * @author Andri
 */
public class ActReceiveBombReport extends ActionCounter implements IEngineAction{
    BombReport report;
    
    public ActReceiveBombReport(BombReport areport){
        super("ActReceiveBombReport");
        report=areport;
    }
    
    @Override
    public boolean execute() {
        counter++;
        System.out.println(Thread.currentThread().getName()+ ":  Got BombReport!!!");
        Engine engine=Engine.getEngine();
        return engine.game.player.receiveBombReport(report);
    }
    
}
