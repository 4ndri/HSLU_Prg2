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
public class Grid {
    public static int defaultSize=10;
    public GridField[][] grid;
    public Grid(){
        this(defaultSize);
    }
    public Grid(int size){
        grid=new GridField[size][size];
        for(int y=0;y<size;y++){
            for(int x=0;x<size;x++){
                grid[y][x]=new GridField();
            }
        }
    }
    
    public BombReport receiveBomb(Bomb aBomb){
        BombReport report;
        GridField afield=getField(aBomb.x, aBomb.y);
        if(afield!=null){
            report=afield.receiveBomb(aBomb);
        }else{
            report=new BombReport(aBomb);
            report.bombOnShip=false;
            report.gameOver=false;
            report.shipDestroyed=false;
        }
        
        return report;
    }
    
    public GridField getField(int x, int y){
        if(y>=0 && y<grid.length && x<grid[0].length && x>=0){
            return grid[y][x];
        }
        return null;
    }
    
    public boolean placeShip(int x,int y,boolean isHorizontal, Ship aship){
        int xmax=x;
        int ymax=y;
        int checkx=Math.max(0,x-1);
        int checky=Math.max(0,y-1);
        int checkxmax=Math.min(xmax+1, grid[0].length-1);
        int checkymax=Math.min(ymax+1, grid.length-1);
        
        //calculate ship length
        if(isHorizontal){
            xmax+=aship.shipLength-1;
            if(xmax>=grid[0].length){
                return false;
            }
            checkxmax=Math.min(xmax+1, grid[0].length-1);
        }else{
            ymax+=aship.shipLength-1;
            if(ymax>=grid.length){
                return false;
            }
            checkymax=Math.min(ymax+1, grid.length-1);
        }
        
        //check is allowed to place ship
        for(int ix=checkx;ix<=checkxmax;ix++){
            for(int iy=checky;iy<=checkymax;iy++){
                GridField afield=getField(ix,iy);
                if(afield.hasShip){
                    return false;
                }
            }
        }
        
        //place ship
        for(int ix=x;ix<=xmax;ix++){
            for(int iy=y;iy<=ymax;iy++){
                GridField afield=getField(ix,iy);
                afield.setShip(aship);
            }
        }
        aship.isShipPlaced=true;
        return true;
    }
    
    public void removeShip(int x,int y){
        GridField afield=getField(x,y);
        if(!afield.hasShip){
            return;
        }else{
            afield.hasShip=false;
            afield.ship.resetShip();
        }
        int xmin=x-1;
        int xmax=x+1;
        int ymin=y-1;
        int ymax=y+1;
        boolean removing=true;
        boolean horizontal=false;
        boolean vertical=false;
        while(removing){
            removing=false;
            if(!vertical){
                afield=getField(xmin,y);
                if(afield.hasShip){
                    afield.hasShip=false;
                    horizontal=true;
                    removing=true;
                    xmin--;
                }
                afield=getField(xmax,y);
                if(afield.hasShip){
                    afield.hasShip=false;
                    horizontal=true;
                    removing=true;
                    xmax++;
                }
            }
            if(!horizontal){
                afield=getField(x,ymin);
                if(afield.hasShip){
                    afield.hasShip=false;
                    vertical=true;
                    removing=true;
                    ymin--;
                }
                afield=getField(x,ymax);
                if(afield.hasShip){
                    afield.hasShip=false;
                    vertical=true;
                    removing=true;
                    ymax++;
                }
            }
        }
    }
    
    
    
}
