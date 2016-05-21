/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.engine.actions;

/**
 *
 * @author Andri
 */
public class ActionCounter{
    int counter=0;
    String name="";
    public ActionCounter(){
        
    }
    public ActionCounter(String aName){
        name=aName;
    }
    public int getCounter(){
        return counter;
    }
    public String getName(){
        return name;
    }
    
}
