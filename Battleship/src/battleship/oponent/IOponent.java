/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.oponent;

import battleship.grid.*;

/**
 *
 * @author Andri
 */
public interface IOponent {
    public void init();
    
    /**
     * Sendet dem Gegner eine Bombe, der Gegner wertet diese Bombe dann auf seinem Grid aus.
     * @param aBomb
     * @return 
     */
    public boolean sendBombToOponent(Bomb aBomb);
    
    /**
     * Über diese Funktion empfängt der Spieler eine Bombe vom Gegner
     * @param aBomb 
     */
    public void bombFromOponent(Bomb aBomb);
    
    /**
     * Funktion um dem Gegner einen Bericht über die vom Gegner empfangene Bombe zu senden. Der Bericht (BombReport) beinhaltet Angaben über Treffer, ist das Schiff zerstört, und ob das Spiel vorbei ist.
     * @param aReport
     * @return 
     */
    public boolean sendBombReportToOponent(BombReport aReport);
    
    /**
     * Der Spieler empfängt den Bericht.
     * @param aReport 
     */
    public void bombReportFromOponent(BombReport aReport);
    
    
    /**
     * Dem Gegner wird mittgeteilt, dass der Spieler bereit ist und die Schiffe platziert sind.
     * @return 
     */
    public boolean sendReadyToOponent();
    /**
     * Der Spieler wird informiert, dass der Gegner bereit ist.
     */
    public void readyFromOponent();
    
}
