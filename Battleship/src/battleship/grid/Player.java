/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.grid;

import battleship.engine.*;
import battleship.engine.actions.*;
import java.util.ArrayList;

/**
 *
 * @author Andri
 */
public class Player {

    public Grid grid;
    public Grid shootingGrid;
    public Ship[] enemyShips;
    public Ship[] ships;
    private int destroyedShips = 0;
    public boolean yourTurn = false;
    public boolean shipsPlaced = false;
    public boolean ready;
    public boolean oponentReady = false;
    public boolean haswon=false;
    
    private Bomb lastBombOnTarget;
//    Game game;

    public Player() {

//        game=Game.getTheGame();
        initPlayer();
//        ready=false;
//        shipsPlaced=false;
//        grid=new Grid();
//        shootingGrid=new Grid();
//        ships=Ship.getShipsTemplate();
    }

    public BombReport receiveBomb(Bomb aBomb) {
        if (!ready) {
            return null;
        }
        BombReport report;
        report = grid.receiveBomb(aBomb);
        if (report.shipDestroyed) {
            destroyedShips++;
            if (destroyedShips >= ships.length) {

                report.gameOver = true;
                Game game = Game.getTheGame();
                haswon = false;
                Game.getTheGame().setGameOver();
            }
        }
        if (!report.bombOnShip) {
            yourTurn = true;
        }
        return report;
    }

    public boolean receiveBombReport(BombReport areport) {
        if (!ready) {
            return false;
        }
        if (areport.bombOnShip) {
            GridField afield = shootingGrid.getField(areport.bomb.x, areport.bomb.y);
            if (afield != null) {
                lastBombOnTarget = areport.bomb;
                afield.bombed = true;
                afield.hasShip = true;
                if (areport.shipDestroyed) {
                    markDestroyedShip(areport, 0);
                }
            }
            yourTurn = true;
        }
        if (areport.gameOver) {
            Game game = Game.getTheGame();
            haswon = true;
            game.setGameOver();

        }
        return true;
    }

    public void sendBomb(Bomb aBomb) {
        if (sendBombShootingGrid(aBomb)) {
            Engine.getEngine().pushAction(new ActSendBombToOponent(aBomb));
        } else {
            Game.getTheGame().handleError("Field already bombed!");
        }
    }

    public boolean sendBombShootingGrid(Bomb aBomb) {
        GridField afield = shootingGrid.getField(aBomb.x, aBomb.y);
        if (afield.bombed) {
            return false;
        }
        afield.bombed = true;
        yourTurn = false;
        return true;
    }

    public Ship getYourPlaceableShip(int shipLength) {
        return Ship.getPlaceableShip(ships, shipLength);
    }

    public Ship getEnemyPlaceableShip(int shipLength) {
        return Ship.getPlaceableShip(enemyShips, shipLength);
    }

    public boolean placeShip(int x, int y, boolean ishorizontal, int shipLength) {
        Ship aship = getYourPlaceableShip(shipLength);
        if (aship == null) {
            return false;
        }
        boolean placed = grid.placeShip(x, y, ishorizontal, aship);
        shipsPlaced = true;
        for (Ship ship : ships) {
            if (!ship.isShipPlaced) {
                shipsPlaced = false;
                break;
            }
        }
        return placed;
    }

    public boolean setPlayerReady() {
        for (Ship ship : ships) {
            if (!ship.isShipPlaced) {
                return false;
            }
        }
        ready = true;
        Engine.getEngine().pushAction(new EngineAction(2));
        Game.getTheGame().calcGameState();
//        if(oponentReady){
//            Game.getTheGame().setGameState(2);
//        }
        return ready;
    }

    public boolean setOponentReady() {
        oponentReady = true;
        Game game = Game.getTheGame();
        game.setConnectionOpened(true);
        return false;
    }

    public void initPlayer() {
        haswon=false;
        ready = false;
        shipsPlaced = false;
        grid = new Grid();
        shootingGrid = new Grid();
        ships = Ship.getShipsTemplate();
        enemyShips = Ship.getShipsTemplate();
    }

    private void markDestroyedShip(BombReport areport, int direction) {
        if (areport.shipDestroyed && areport.bomb != null) {
            Bomb abomb = areport.bomb;
            int xmin = abomb.x-1;
            int xmax = abomb.x + 1;
            int ymin = abomb.y-1;
            int ymax = abomb.y + 1;
            boolean checkNext = true;
            switch (direction) {
                case 1:
                    while (checkNext) {
                        checkNext = false;
                        GridField afield = shootingGrid.getField(xmin, abomb.y);
                        if (afield != null) {

                            if (afield.hasShip) {
                                afield.bombed = true;
                                checkNext = true;
                                xmin--;
                            }

                        }
                        afield = shootingGrid.getField(xmax, abomb.y);
                        if (afield != null) {

                            if (afield.hasShip && afield.bombed) {
                                checkNext = true;
                                xmax++;
                            }
                        }
                    }
                    break;
                case 2:
                    while (checkNext) {
                        checkNext = false;
                        GridField afield = shootingGrid.getField(abomb.x, ymin);
                        if (afield != null) {
                            if (afield.hasShip) {
                                checkNext = true;
                                ymin--;
                            }

                        }
                        afield = shootingGrid.getField(abomb.x, ymax);
                        if (afield != null) {
                            if (afield.hasShip) {
                                checkNext = true;
                                ymax++;
                            }

                        }
                    }
                    break;
                case 0:
                default:
                    
                    GridField afield = shootingGrid.getField(xmin, abomb.y);
                    if (afield != null) {
                        
                        if (afield.hasShip) {
                            markDestroyedShip(areport, 1);
                            return;
                        }
                    }
                    afield = shootingGrid.getField(xmax, abomb.y);
                    if (afield != null) {
                        
                        if (afield.hasShip) {
                            markDestroyedShip(areport, 1);
                            return;
                        }
                    }
                    //vertical
                    afield = shootingGrid.getField(abomb.x, ymin);
                    if (afield != null) {
                        if (afield.hasShip) {
                            markDestroyedShip(areport, 2);
                            return;
                        }
                    }
                    afield = shootingGrid.getField(abomb.x, ymax);
                    if (afield != null) {
                        if (afield.hasShip) {
                            markDestroyedShip(areport, 2);
                            return;
                        }
                    }
                    break;
            }
            
            //mark fields
            GridField afield = null;
            int shipLength = Math.max(ymax - ymin - 2, xmax - xmin - 2);
            Ship aship = getEnemyPlaceableShip(shipLength);
            if(aship==null){
                aship=new Ship(shipLength);
            }
            aship.bombsOnShip = shipLength;
            for (int y = ymin; y <= ymax; y++) {
                for (int x = xmin; x <= xmax; x++) {
                    afield = shootingGrid.getField(x, y);
                    if (afield != null) {
                        afield.mark = true;
                        if (afield.hasShip) {
                            afield.ship = aship;
                        }
                    }
                }
            }
            lastBombOnTarget=null;
        }
    }

    public ArrayList<Bomb> getBestPossibleBombs(int bombingDirection) {
        ArrayList<Bomb> bombs = new ArrayList<>();
        if (lastBombOnTarget != null) {

            int xmin = lastBombOnTarget.x - 1;
            int xmax = lastBombOnTarget.x + 1;
            int ymin = lastBombOnTarget.y - 1;
            int ymax = lastBombOnTarget.y + 1;
            boolean checkNext = true;
            switch (bombingDirection) {
                case 1:
                    while (checkNext) {
                        checkNext = false;
                        GridField afield = shootingGrid.getField(xmin, lastBombOnTarget.y);
                        if (afield != null) {
                            if (afield.hasShip) {
                                checkNext = true;
                                xmin--;
                            } else if (!afield.bombed && !afield.mark) {
                                bombs.add(new Bomb(xmin, lastBombOnTarget.y));
                            }
                        }
                        afield = shootingGrid.getField(xmax, lastBombOnTarget.y);
                        if (afield != null) {
                            if (afield.hasShip) {
                                checkNext = true;
                                xmax++;
                            } else if (!afield.bombed && !afield.mark) {
                                bombs.add(new Bomb(xmax, lastBombOnTarget.y));
                            }
                        }
                    }
                    return bombs;
                case 2:
                    while (checkNext) {
                        checkNext = false;
                        GridField afield = shootingGrid.getField(lastBombOnTarget.x, ymin);
                        if (afield != null) {
                            if (afield.hasShip) {
                                checkNext = true;
                                ymin--;
                            } else if (!afield.bombed && !afield.mark) {
                                bombs.add(new Bomb(lastBombOnTarget.x, ymin));
                            }
                        }
                        afield = shootingGrid.getField(lastBombOnTarget.x, ymax);
                        if (afield != null) {
                            if (afield.hasShip) {
                                checkNext = true;
                                ymax++;
                            } else if (!afield.bombed && !afield.mark) {
                                bombs.add(new Bomb(lastBombOnTarget.x, ymax));
                            }
                        }
                    }
                    return bombs;
                case 0:
                default:
                    GridField afield = shootingGrid.getField(xmin, lastBombOnTarget.y);
                    if (afield != null) {
                        if (afield.hasShip) {
                            bombingDirection = 1;
                            return getBestPossibleBombs(1);
                        } else if (!afield.bombed && !afield.mark) {
                            bombs.add(new Bomb(xmin, lastBombOnTarget.y));
                        }
                    }
                    afield = shootingGrid.getField(xmax, lastBombOnTarget.y);
                    if (afield != null) {
                        if (afield.hasShip) {
                            bombingDirection = 1;
                            return getBestPossibleBombs(1);
                        } else if (!afield.bombed && !afield.mark) {
                            bombs.add(new Bomb(xmax, lastBombOnTarget.y));
                        }
                    }
                    afield = shootingGrid.getField(lastBombOnTarget.x, ymin);
                    if (afield != null) {
                        if (afield.hasShip) {
                            bombingDirection = 2;
                            return getBestPossibleBombs(2);
                        } else if (!afield.bombed && !afield.mark) {
                            bombs.add(new Bomb(lastBombOnTarget.x, ymin));
                        }
                    }
                    afield = shootingGrid.getField(lastBombOnTarget.x, ymax);
                    if (afield != null) {
                        if (afield.hasShip) {
                            bombingDirection = 2;
                            return getBestPossibleBombs(2);
                        } else if (!afield.bombed && !afield.mark) {
                            bombs.add(new Bomb(lastBombOnTarget.x, ymax));
                        }
                    }
            }

            return bombs;
        } else {
            for (int y = 0; y < shootingGrid.grid.length; y++) {
                for (int x = 0; x < shootingGrid.grid[0].length; x++) {
                    GridField afield = shootingGrid.getField(x, y);
                    if (afield != null && !afield.bombed && !afield.mark) {
                        bombs.add(new Bomb(x, y));
                    }
                }
            }
        }
        return bombs;
    }
    
    
    public void markToSmallFields(){
        int minLength=Integer.MAX_VALUE;
        for(Ship aship:enemyShips){
            if(!aship.isShipPlaced && aship.shipLength<minLength){
                minLength=aship.shipLength;
            }
        }
        
        int yLength=shootingGrid.grid.length;
        int xLength=shootingGrid.grid[0].length;
        boolean[][] horizontalMarks=new boolean[yLength][xLength];
        boolean[][] verticalMarks=new boolean[yLength][xLength];
        
        for(int y=0;y<yLength;y++){
            int testlength=0;
            for(int x=0;x<xLength;x++){
                GridField afield=shootingGrid.getField(x, y);
                if(!afield.mark && !afield.bombed){}
            }
        }
    }
}
