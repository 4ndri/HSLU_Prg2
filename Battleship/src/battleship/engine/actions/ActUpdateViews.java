/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.engine.actions;

import battleship.engine.Engine;
import battleship.gui.IView;

/**
 *
 * @author Andri
 */
public class ActUpdateViews  extends ActionCounter implements IEngineAction {

    @Override
    public boolean execute() {
        counter++;
        Engine engine=Engine.getEngine();
        for(int i=0;i<engine.views.size();i++){
            IView view=engine.views.get(i);
            view.updateView();
        }
        return true;
    }
    
}
