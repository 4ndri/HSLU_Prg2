/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.oponent;

import battleship.engine.*;
import battleship.engine.actions.*;
import battleship.grid.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andri
 */
public class CompOponent implements IOponent {

    Player oponent;
    Bomb lastBombOnTarget;
    int bombingDirection = 0; //0: no direction, 1: horizontal, 2: vertical

    public CompOponent() {
        oponent = new Player();
    }

    @Override
    public void init() {
        if (placeShips()) {
            oponent.ready = true;
            readyFromOponent();
        } else {
            oponent = new Player();
            Engine.getEngine().pushAction(new EngineAction(99, "Computer couldn't place ships"));
        }
    }

    public boolean placeShips() {
        int placedShips = 0;
        int counter = 0;
        while (placedShips < oponent.ships.length && counter < 50000) {
            if (placeShipOnRandomField(oponent.ships[placedShips])) {
                placedShips++;
            }
            counter++;
        }
        return true;
    }

    private boolean placeShipOnRandomField(Ship aship) {
        Random generator = new Random();
        int x = generator.nextInt(oponent.grid.grid.length);
        int y = generator.nextInt(oponent.grid.grid.length);
        return oponent.placeShip(x, y, generator.nextBoolean(), aship.shipLength);
    }

    private Bomb getNextBomb() {
        Random generator = new Random();
        ArrayList<Bomb> bombs = oponent.getBestPossibleBombs(0);
        if (bombs.size() > 0) {
            return bombs.get(generator.nextInt(bombs.size()));
        } else {
            bombingDirection = 0;
            return null;
        }
    }

    @Override
    public boolean sendBombToOponent(Bomb aBomb) {
        if (!oponent.ready) {
            return false;
        }
        BombReport report = oponent.receiveBomb(aBomb);
        bombReportFromOponent(report);
        if (oponent.yourTurn) {
            bombFromOponent(getNextBomb());
        }
        return true;
    }

    @Override
    public boolean sendBombReportToOponent(BombReport aReport) {
        if (!oponent.ready) {
            return false;
        }

        oponent.receiveBombReport(aReport);
//        if (aReport.bombOnShip) {
//            if (oponent.grid.getField(aReport.bomb.x, aReport.bomb.y) != null) {
//                lastBombOnTarget = aReport.bomb;
//                if (aReport.shipDestroyed) {
//                    markDestroyedShip();
//                }
//            }
//        }

        if (oponent.yourTurn) {
            Bomb abomb = getNextBomb();

            bombFromOponent(abomb);
        }

        return true;
    }

    @Override
    public void bombFromOponent(Bomb aBomb) {
        oponent.sendBombShootingGrid(aBomb);

        Engine engine = Engine.getEngine();
        try {
            Thread.sleep(700);
        } catch (InterruptedException ex) {
            Logger.getLogger(CompOponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        engine.pushAction(new ActReceiveBomb(aBomb));
    }

    @Override
    public void bombReportFromOponent(BombReport aReport) {
        Engine engine = Engine.getEngine();
        engine.pushAction(new ActReceiveBombReport(aReport));
    }

    @Override
    public void readyFromOponent() {
        Engine engine = Engine.getEngine();
//        engine.pushAction(new ActGameStateChanged());
        engine.pushAction(new EngineAction(1));
    }

    @Override
    public boolean sendReadyToOponent() {
        if (oponent == null) {
            return false;
        }
        oponent.setOponentReady();
        return true;
    }
}
