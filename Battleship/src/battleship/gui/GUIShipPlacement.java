/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.gui;

import battleship.engine.*;
import battleship.engine.actions.*;
import battleship.grid.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.*;

/**
 *
 * @author Somebody
 */
public class GUIShipPlacement extends JFrame implements ActionListener, IView {

    Game game;//nur lesen
    public int guiGameState = 1;
    private JPanel leftPanel = new JPanel();
    private JButton gridfields[][];

    private JPanel rightPanel = new JPanel();

    private JRadioButton ver = new JRadioButton("vertikal");
    private JRadioButton hor = new JRadioButton("horizontal");
    private ButtonGroup group = new ButtonGroup();

    private JButton btnReset = new JButton("zurücksetzen");
    private JButton btnNext = new JButton("Weiter");

    private Vector ships;
    private JComboBox shipchoose;

    private JLabel lblError;

    public GUIShipPlacement() {
        super("Battleship Ship Placement");
        setSize(500, 250);
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new GridLayout(1, 2));
        init();
    }

    public void init() {
        game = Engine.getEngine().game;
        if (game.getGameState() != guiGameState) {
            setVisible(false);
        }
        int xSize = game.player.grid.grid[0].length;
        int ySize = game.player.grid.grid.length;
        gridfields = new JButton[xSize][ySize];

        leftPanel.setLayout(new GridLayout(xSize, ySize));
        createPanel(gridfields, leftPanel);
        getContentPane().add(leftPanel);

        ships = getShipsToPlace(game.player.ships);
        shipchoose = new JComboBox(ships);

        rightPanel.setLayout(new GridLayout(0, 1));
        rightPanel.add(new JLabel("Schiffe:"));
        rightPanel.add(shipchoose);

        group.add(ver);
        group.add(hor);
        rightPanel.add(ver);
        rightPanel.add(hor);

        rightPanel.add(btnReset);
        btnReset.addActionListener(new ResetEvent());
        btnNext.setEnabled(false);
        rightPanel.add(btnNext);
        btnNext.addActionListener(new StartEvent());

        getContentPane().add(rightPanel);
        
        
        updateView();
    }

    public void createPanel(JButton[][] buttons, JPanel panel) {
        for (int y = 0; y < buttons.length; y++) {
            for (int x = 0; x < buttons.length; x++) {
                buttons[y][x] = new JButton();
                panel.add(buttons[y][x]);
                buttons[y][x].addActionListener(this);
                buttons[y][x].setText("");
            }
        }
    }

    private Vector getShipsToPlace(Ship[] ships) {
        Vector vecShips = new Vector();
        for (Ship ship : ships) {
            if (!ship.isShipPlaced) {
                vecShips.add(getShipNameByLength(ship.shipLength));
            }
        }
        return vecShips;
    }

    private ShipNameLength getShipNameByLength(int length) {
        switch (length) {
            case 1:
                return new ShipNameLength("Fisch[1]",1);
            case 2:
                return new ShipNameLength("U-Boot[2]",2);
            case 3:
                return new ShipNameLength("Zerstörer[3]",3);
            case 4:
                return new ShipNameLength("Kreuzer[4]",4);
            case 5:
                return new ShipNameLength("Schlachtschiff[5]",5);
            default:
                return new ShipNameLength("Schiffchen[" + length + "]",length);
        }

    }

    public boolean isVertikal() {
        boolean isVertikal;
        if (ver.isSelected()) {
            isVertikal = true;
        } else {
            isVertikal = false;
        }
        return (isVertikal);
    }

    public Position getPosition(JButton button) {
        for (int y = 0; y < gridfields.length; y++) {
            for (int x = 0; x < gridfields[0].length; x++) {
                if (button == gridfields[y][x]) {
                    return new Position(x, y);
                }
            }
        }
        return null;
    }

    private void placeShip(Position pos) {
        GridField afield=game.player.grid.getField(pos.x, pos.y);
        if (afield.hasShip) {
            return;
        }
        ShipNameLength shipObj = null;
        try{
            shipObj=(ShipNameLength)shipchoose.getSelectedItem();
        }catch(Exception ex){
            return;
        }
        boolean vertical=isVertikal();
        boolean horizontal=!vertical;
        Engine engine=Engine.getEngine();
        engine.pushAction(new ActPlaceShip(pos.x,pos.y,horizontal,shipObj.length));
    }

    @Override
    public synchronized void updateView() {
        game = Engine.getEngine().game;
        if (game.getGameState() != guiGameState) {
            setVisible(false);
            return;
        }
        setVisible(true);
        
        int xSize = game.player.grid.grid[0].length;
        int ySize = game.player.grid.grid.length;
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                GridField afield = game.player.grid.getField(x, y);
                if (afield.hasShip) {
                    gridfields[y][x].setBackground(Color.green);
                    
                } else {
                    gridfields[y][x].setBackground(null);
                }
            }
        }
        
        ships = getShipsToPlace(game.player.ships);
        shipchoose.removeAllItems();
        for(int i=0;i<ships.size();i++){
            shipchoose.addItem(ships.get(i));
        }
        shipchoose.repaint();
        
        if(game.player.shipsPlaced){
            btnNext.setEnabled(true);
        }else{
            btnNext.setEnabled(false);
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton abutton = (JButton) e.getSource();
            Position pos = getPosition(abutton);
            if (pos == null) {
                return;
            }
            placeShip(pos);
        }
    }

    class ResetEvent implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Engine engine = Engine.getEngine();
            engine.pushAction(new EngineAction(3));

        }
    }

    class StartEvent implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            btnReset.setEnabled(false);
            btnNext.setText("Wait");
            Engine.getEngine().pushAction(new EngineAction(22));

        }
    }

    class ShipNameLength {

        String name;
        int length;

        public ShipNameLength(String name, int length) {
            this.name = name;
            this.length = length;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
