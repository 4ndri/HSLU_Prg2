/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.gui;

import battleship.engine.*;
import battleship.engine.actions.*;
import battleship.grid.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;



/**
 *
 * @author Andri
 */
public class GUIInitialize  extends JFrame implements ActionListener, IView {
    Game game;//nur lesen
    public int guiGameState = 0;
    private JPanel leftPanel=new JPanel();
    private JPanel rightPanel=new JPanel();
    
    private JButton btnCompOponent = new JButton("Computer");
    private JButton btnNetOponent = new JButton("Play online");
    
    private JButton btnOpenConnection = new JButton("Open Game");
    private JButton btnJoinConnection = new JButton("Join Game");
    
    private JButton btnStart=new JButton("Start");
    
    private JTextField ip = new JTextField("0.0.0.0");
    private JTextField port = new JTextField("12345");
    
    private JLabel lblError=new JLabel();
    public GUIInitialize(){
        super("Battleship Ship Placement");
        setSize(500, 250);
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new GridLayout(1, 2));
        init();
    }
    private void init(){
        game = Engine.getEngine().game;
        if (game.getGameState() != guiGameState) {
            setVisible(false);
        }
        leftPanel.setLayout(new GridLayout(1, 0));
        leftPanel.add(new JLabel("Wählen Sie den Computer als Gegner oder spielen Sie online."));
        JPanel panelInitOponent=new JPanel();
        panelInitOponent.setLayout(new GridLayout(0,1));
        panelInitOponent.add(btnCompOponent);
        panelInitOponent.add(btnNetOponent);
        leftPanel.add(panelInitOponent);
        
        btnCompOponent.addActionListener(new InitCompOponent());
        btnNetOponent.addActionListener(new InitNetOponent());
        
        btnStart.addActionListener(this);
        
        getContentPane().add(leftPanel);
        
        
        rightPanel.setLayout(new GridLayout(0,1));
        rightPanel.add(new JLabel("Host oder Client"));
        rightPanel.add(ip);
        rightPanel.add(port);
        rightPanel.add(btnOpenConnection);
        rightPanel.add(btnJoinConnection);
        rightPanel.add(lblError);
        rightPanel.setVisible(false);
        btnOpenConnection.addActionListener(new OpenConnection());
        btnJoinConnection.addActionListener(new JoinConnection());
        getContentPane().add(rightPanel);
        updateView();
    }
    
    
    public int getPort(){
        int aport=-1;
        String strPort=port.getText();
        if(strPort.isEmpty()){
            return -1;
        }
        try{
            aport=Integer.parseInt(strPort);
        }catch(Exception ex){}
        return aport;
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateView() {
        if (game.getGameState() != guiGameState) {
            setVisible(false);
            return;
        }
        Engine engine=Engine.getEngine();
        if(engine.isHost){
            try {
                ip.setText(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(GUIInitialize.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(engine.connection!=null && engine.connection.isRunning()){
                port.setText(engine.connection.port + "");
            }
        }
        
        if(!game.error.isEmpty()){
            lblError.setText(lblError.getText()+ " |  \n" + game.error);
            
        }
        setVisible(true);
    }
    class InitNetOponent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            rightPanel.setVisible(true);
        }
    }
    class InitCompOponent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            rightPanel.setVisible(false);
            Engine engine=Engine.getEngine();
            engine.pushAction(new ActSetCompOponent());
        }
    }
    
    class OpenConnection implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int aport=getPort();
            if(aport<0){
                lblError.setText("Please chose a port!");
                return;
            }
            try {
                ip.setText(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(GUIInitialize.class.getName()).log(Level.SEVERE, null, ex);
            }
            Engine engine=Engine.getEngine();
            engine.pushAction(new ActOpenConnection(aport));
        }
    }
    class JoinConnection implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int aport=getPort();
            if(aport<0){
                return;
            }
            String strIP=ip.getText();
            if(strIP.isEmpty()){
                return;
            }
            
            InetAddress opip=null;
            try {
                opip = InetAddress.getByName(strIP);
//                if(!opip.isReachable(5000)){
//                    lblError.setText("IP not reachable!");
//                    return;
//                }
            } catch (Exception ex){
                
            }
            if(opip==null){
                lblError.setText("IP not reachable");
                return;
            }
            Engine engine=Engine.getEngine();
            engine.pushAction(new ActJoinConnection(aport, opip));
        }
            
            
    }
}
