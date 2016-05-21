/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.gui;

import battleship.engine.Engine;
import battleship.grid.Game;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Andri
 */
public class GUIGameOver extends JFrame implements IView{
    Game game;//nur lesen
    public int guiGameState = 3;
    
    private JLabel lblResult=new JLabel();
    
    
    public GUIGameOver(){
        super("Battleship Game Over");
        setSize(500, 250);
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new GridLayout(1, 2));
        init();
    }
    private void init(){
        JLabel lblFinished=new JLabel("Game is finished");
        getContentPane().add(lblFinished);
        getContentPane().add(lblResult);
        
        updateView();
    }
    @Override
    public void updateView() {
        game = Engine.getEngine().game;
        if (game.getGameState() != guiGameState) {
            setVisible(false);
            return;
        }
        if(game.player.haswon){
            lblResult.setText("Congratulation! You've won the Ship-Fight!");
        }else{
            lblResult.setText("Glugluglug!! You lost!");
        }
        setVisible(true);
    }
    
}
