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
public class Ship {

    public int shipLength;
    public int bombsOnShip = 0;
    public boolean isShipPlaced;

    public Ship(int shipLength) {
        this.shipLength = shipLength;
    }

    public boolean isDestroyed() {
        if (bombsOnShip >= shipLength) {
            return true;
        }
        return false;
    }

    public boolean receiveBomb() {
        bombsOnShip++;
        if (bombsOnShip >= shipLength) {
            return true;
        }
        return false;
    }

    public void resetShip() {
        this.isShipPlaced = false;
        this.bombsOnShip = 0;
    }

    public static Ship[] getShipsTemplate() {

        Ship[] ships = new Ship[10];
        ships[0] = new Ship(5);
        ships[1] = new Ship(4);
        ships[2] = new Ship(4);
        for (int i = 3; i < 6; i++) {
            ships[i] = new Ship(3);
        }
        for (int i = 6; i < 10; i++) {
            ships[i] = new Ship(2);
        }
        return ships;
    }

    public static Ship getPlaceableShip(Ship[] ships, int shipLength) {
        Ship aship = null;
        if (ships != null) {
            for (int i = 0; i < ships.length; i++) {
                if (ships[i].shipLength == shipLength && !ships[i].isShipPlaced) {
                    aship = ships[i];
                    break;
                }
            }
        }
        return aship;
    }
}
