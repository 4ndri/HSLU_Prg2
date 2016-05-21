/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.engine;

import battleship.engine.actions.*;
import battleship.grid.*;
import battleship.gui.*;
import battleship.net.*;
import battleship.oponent.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Andri
 */
public class Engine {
    private static Engine mengine;
    
    
       
    private GUIShipPlacement guiShipPlacement;
    private GUIInitialize guiInit;
    private GUIPlaying guiPlaying;
    private GUIGameOver guiGameOver;
    private final ArrayList<IEngineAction> actionList=new ArrayList<>();
    public Game game;
    public boolean isHost=false;
    public IOponent oponent;
    public Connection connection;
    public ArrayList<IView>  views;
    private Engine(){
        views=new ArrayList<>();
        game=Game.getTheGame();
        
    }
    public void start(){
        gameStateChanged();
        handleActions();
    }
    
    public static Engine getEngine(){
        if(mengine==null){
            mengine=new Engine();
        }
        return mengine;
    }
    private void handleActions(){
        while(true){
            try {
                IEngineAction action=waitForNextAction();
                boolean success;
                try{
                    success=action.execute();
                    
                }catch(Exception ex){
                    success=false;
                    handleError("Could not execute action: Exception:"+ex.getMessage() +" | Name:" + action.getName()+"  |  Class: "+action.getClass());
                }
                if(success){
                    updateViews();
                }else{
                    if(action.getCounter()<100){
                        pushAction(action);
                    }else{
                        handleError("Can't execute action: Name:" + action.getName()+"  | Class: "+action.getClass());
                    }
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public synchronized void pushAction(IEngineAction action){
        actionList.add(action);
        notifyAll();
    }
    
    public synchronized IEngineAction waitForNextAction() throws InterruptedException{
        while(actionList.isEmpty()){
            wait();
        }
        return actionList.remove(0);
    }
    
    public void gameStateChanged(){
        switch(game.getGameState()){
            case 0:
//                if(guiShipPlacement==null){
//                    guiShipPlacement=new GUIShipPlacement();
//                }
//                guiShipPlacement.guiGameState=0;
//                addView(guiShipPlacement);
                if(oponent!=null){
//                    oponent=null;
                }
                if(connection!=null){
                    connection.interrupt();
                    connection=null;
                }
                if(guiInit==null){
                    guiInit=new GUIInitialize();
                    addView(guiInit);
                }
                guiInit.guiGameState=0;
                updateViews();
                break;
            case 1:
                if(guiShipPlacement==null){
                    guiShipPlacement=new GUIShipPlacement();
                    addView(guiShipPlacement);
                }
                guiShipPlacement.guiGameState=1;
                updateViews();
                break;
            case 2:
                if(guiPlaying==null){
                    guiPlaying=new GUIPlaying();
                    addView(guiPlaying);
                }
                updateViews();
                
                break;
            case 3:
                if(guiGameOver==null){
                    guiGameOver=new GUIGameOver();
                    addView(guiGameOver);
                }
                updateViews();
                break;
            default:
                
                break;
        }
    }
    
    public void addView(IView view){
        views.add(view);
    }
    public boolean removeView(IView view){
        views.remove(view);
        return true;
    }
    
    public void updateViews(){
        for(IView view:views){
            if(view!=null){
                view.updateView();
            }
        }
    }
    public void handleError(String error){
        System.out.println(error);
        game.handleError(error);
    }
}
