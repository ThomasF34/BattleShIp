package falcone.thomas.Players;

import falcone.thomas.Board.Coord;
import falcone.thomas.Board.Ship;

import java.util.ArrayList;

public interface IPlayer {

    int[] getShipsToBeConstructed();

    /**
     * @return true if the player has any ship left to place on the grid, false if not
     */
    boolean hasShipsToBeConstructed();

    /**
     * @return true if the player has any ship that isn't sank, or false if all ships are sank.
     */
    boolean hasShipLeft();

    /**
     * Indicates if the player has already shot at this coordinate
     * @param coordMissil coordinate
     * @return true if player has already shot there, false if not.
     */
    boolean hasAlreadyShot(String coordMissil);

    /**
     * This function fires the missile to playerToAttack's grid. It returns an array of 2 booleans. First one indicates if
     * a ship has been hit and the second one if the ship is sank.
     * @param playerToAttack
     * @param coordMissil
     * @return
     */
    boolean[] fire(IPlayer playerToAttack, String coordMissil);

    String printShipGrid();

    String printHitGrid();

    ArrayList<Ship> getShips();

    String getName();

    ArrayList<Coord> getShots();

    int getNumberSankShip();

    int getScore();

    void placeShips();

    void placeShip(String coord1, String coord2);

    String giveShot();
}

