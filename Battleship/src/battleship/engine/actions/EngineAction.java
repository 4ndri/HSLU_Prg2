/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.engine.actions;

import battleship.engine.Engine;
import battleship.net.Message;

/**
 *
 * @author Andri
 */
public class EngineAction extends ActionCounter implements IEngineAction{
    int type;
    String text;
    public EngineAction(int aType){
        this.type=aType;
        
    }
    public EngineAction(int aType,String atext){
        this(aType);
        text=atext;
    }
    @Override
    public boolean execute() {
        counter++;
        Engine engine=Engine.getEngine();
        switch(type){
            case 0:
                break;
            case 1://oponent ready to play
                engine.game.player.setOponentReady();
                break;
            case 2://send ready to oponent
                engine.oponent.sendReadyToOponent();
                break;
            case 22:
                engine.game.player.setPlayerReady();
                break;
            case 3://reset player
                engine.game.player.initPlayer();
                break;
            case 4://connection is opened
                engine.game.setConnectionOpened(true);
                break;
            case 88://reset Game
                engine.game.reset();
                break;
            case 99://error handle
                engine.game.handleError(text);
                break;
            default:
                
                break;
        }
        return true;
    }
    
}
