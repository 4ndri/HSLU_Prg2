/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.grid;

import battleship.engine.Engine;
import battleship.engine.actions.ActGameStateChanged;
import battleship.engine.actions.ActStartPlaying;

/**
 *
 * @author Andri
 */
public class Game {
    
    private static Game theGame;
    
    private int gameState=0; //0:initializing, placing ships, 2:playing, 3: game over, 99: error
    private boolean connectionOpen=false;
    private boolean gameOver=false;
    private boolean startedPlaying=false;
    
    
    public String error="";
    
    public Player player;
    
    public boolean getStartedPlaying(){
        return startedPlaying;
    }
    
    public void setGameOver(){
        gameOver=true;
        System.out.println("GameOver");
        calcGameState();
    }
    public void setConnectionOpened(boolean val){
        connectionOpen=val;
        calcGameState();
    }
    
    public boolean calcGameState(){
        int state=0;
        if(connectionOpen){
            state=1;
            if(player.ready && player.oponentReady){
                state=2;
                if(gameOver){
                    state=3;
                }
            }
        }
        if(state==gameState){
            return true;
        }
        return setGameState(state);
    }
    
    public int getGameState() {
        return gameState;
    }
    public boolean setGameState(int aGameState) {
        switch(aGameState){
            case 0://initializing
                
                break;
            case 1://placing ships
                if(!connectionOpen){
                    return false;
                    
                }
                break;
            case 2://playing
                if(!player.ready || !player.oponentReady){
                    return false;
                }
                if(!startedPlaying){
                    startPlaying();
                }
                break;
            case 3://game oveer
                if(!gameOver){
                    return false;
                }
                break;
            default:
                return false;
        }
        if(gameState != aGameState){
            gameState = aGameState;
            Engine.getEngine().pushAction(new ActGameStateChanged());
        }
        return true;
    }

    
    public void reset(){
        gameOver=false;
        connectionOpen=false;
        startedPlaying=false;
        error="";
        initPlayer();
        
        setGameState(0);
    }
    public void initPlayer(){
        player=new Player();
    }
    private Game(){
        gameOver=false;
        connectionOpen=false;
        error="";
        gameState=0;
        initPlayer();
    }
    
    public static Game getTheGame(){
        if(theGame==null){
            theGame=new Game();
        }
        return theGame;
    }
    public void startPlaying(){
        startedPlaying=true;
        Engine.getEngine().pushAction(new ActStartPlaying());
    }
    public void handleError(String strError){
        error=strError;
        System.out.println(error);
    }
}
