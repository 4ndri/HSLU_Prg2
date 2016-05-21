/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.grid;

import java.io.Serializable;

/**
 *Klasse für die Rückmeldung, ob Schiff getroffen, Schiff versenkt, Spiel vorbei
 * @author Andri
 */
public class BombReport implements Serializable{
    public boolean bombOnShip;
    public boolean shipDestroyed;
    public boolean gameOver;
    public Bomb bomb;
    public BombReport(Bomb abomb){
        bomb=abomb;
    }
}
