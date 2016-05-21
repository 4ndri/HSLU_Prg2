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
public interface IEngineAction {
    public boolean execute();
    public int getCounter();
    public String getName();
}
