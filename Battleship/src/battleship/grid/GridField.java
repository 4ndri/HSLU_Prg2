/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.grid;

/**
 *
 * @author Andri
 */
public class GridField {
    public boolean hasShip=false;
    public boolean bombed=false;
    public boolean mark=false;
    public Ship ship=null;
    
    public void setShip(Ship aShip){
        hasShip=true;
        ship=aShip;
    }
    public BombReport receiveBomb(Bomb abomb){
        BombReport report=new BombReport(abomb);
        
        
        report.bombOnShip=hasShip;
        
        if(hasShip){
            if(!bombed){
                report.shipDestroyed=ship.receiveBomb();
            }else{
                report.shipDestroyed=ship.isDestroyed();
            }
            report.gameOver=false;
        }else{
            report.shipDestroyed=false;
            report.gameOver=false;
        }
        
        bombed=true;
        return report;
        
        
    }
    
}
