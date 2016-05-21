/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.grid;

import java.io.Serializable;

/**
 * Klasse für die Bombe, welche dann zum Gegner versendet werden kann
 * @author Andri
 */
public class Bomb implements Serializable{

    public final int x;
    public final int y;
    
    public Bomb(int x,int y){

        this.x=x;
        this.y=y;
    }
}
