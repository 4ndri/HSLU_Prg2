/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.gui;

import battleship.engine.Engine;
import battleship.engine.actions.ActSendBombToGridFromGUI;
import battleship.grid.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Andri
 */
public class GUIPlaying extends JFrame implements IView {

    Game game;//nur lesen
    boolean yourTurn=false;
    public int guiGameState = 2;
    private JPanel leftPanel = new JPanel();
    private JButton[][] yourgrid;
    private JButton[][] shootingGrid;

    private JPanel rightPanel = new JPanel();
    private JPanel middlePanel = new JPanel();

    private JLabel lblError;
    private JLabel lblInfo;
    public GUIPlaying() {
        super("Battleship Ship Placement");
        setSize(1000, 250);
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new GridLayout(1, 3));
        init();

    }

    public void init() {
        game = Engine.getEngine().game;
        if (game.getGameState() != guiGameState) {
            setVisible(false);
        }
        int xSize = game.player.grid.grid[0].length;
        int ySize = game.player.grid.grid.length;
        yourgrid = new JButton[xSize][ySize];
        shootingGrid = new JButton[xSize][ySize];
        leftPanel.setLayout(new GridLayout(xSize, ySize));
        rightPanel.setLayout(new GridLayout(xSize, ySize));
        createPanel(yourgrid, leftPanel,null);
        createPanel(shootingGrid, rightPanel,new SendBombEvent());
        getContentPane().add(leftPanel);

        middlePanel.setLayout(new GridLayout(0, 1));
        lblInfo=new JLabel("Achtung Bombe!");
        middlePanel.add(lblInfo);
        getContentPane().add(middlePanel);
        getContentPane().add(rightPanel);

        updateView();
    }

    public void createPanel(JButton[][] buttons, JPanel panel, ActionListener actionListener) {
        for (int y = 0; y < buttons.length; y++) {
            for (int x = 0; x < buttons.length; x++) {
                buttons[y][x] = new JButton();
                panel.add(buttons[y][x]);
                if(actionListener!=null){
                    buttons[y][x].addActionListener(actionListener);
                }
                buttons[y][x].setText("");
            }
        }
    }
    
    public Position getPosition(JButton button, JButton[][] buttons) {
        for (int y = 0; y < buttons.length; y++) {
            for (int x = 0; x < buttons[0].length; x++) {
                if (button == buttons[y][x]) {
                    return new Position(x, y);
                }
            }
        }
        return null;
    }

    public void sendBomb(Position pos) {
        if(yourTurn){
            yourTurn=false;
            if(!game.player.shootingGrid.getField(pos.x, pos.y).bombed){
            Engine.getEngine().pushAction(new ActSendBombToGridFromGUI(new Bomb(pos.x,pos.y)));
            }
        }
    }

    @Override
    public void updateView() {
        game = Engine.getEngine().game;
        if (game.getGameState() != guiGameState) {
            setVisible(false);
            return;
        }
        setVisible(true);
        yourTurn=game.player.yourTurn;
        if(yourTurn){
            lblInfo.setText("Du bist an der Reihe!");
        }else{
            lblInfo.setText("Du wirst gerade bombardiert!");
        }
        int xSize = game.player.grid.grid[0].length;
        int ySize = game.player.grid.grid.length;
        
        paintGrid(game.player.grid,yourgrid);
        
        paintGrid(game.player.shootingGrid,shootingGrid);
        
    }
    
    private void paintGrid(Grid agrid, JButton[][] buttons){
        int ySize=agrid.grid.length;
        int xSize=agrid.grid[0].length;
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                GridField afield = agrid.getField(x, y);
                if (afield.hasShip && afield.bombed) {
                    buttons[y][x].setText("Crash");
                    buttons[y][x].setBackground(Color.red);
                    if(afield.ship!=null && afield.ship.isDestroyed()){
                        buttons[y][x].setBackground(Color.PINK);
                        buttons[y][x].setForeground(Color.PINK);
                    }
                } else if(afield.hasShip){
                    buttons[y][x].setBackground(Color.green);
                }else if(afield.bombed){
                    buttons[y][x].setBackground(Color.black);
                }else{
                    buttons[y][x].setText("");
                }
            }
        }
    }
    
    
    class SendBombEvent implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                JButton abutton = (JButton) e.getSource();
                Position pos = getPosition(abutton, shootingGrid);
                if (pos == null) {
                    return;
                }
                sendBomb(pos);
            }

        }
    }
}
