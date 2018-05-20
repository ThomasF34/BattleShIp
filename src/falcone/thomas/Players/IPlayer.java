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

    String printShipGrid();

    String printHitGrid();

    ArrayList<Ship> getShips();

    String getName();

    ArrayList<Coord> getShots();

    int getNumberSankShip();

    void placeShips();

    void placeShip(String coord1, String coord2);

    String giveShot();

    int getScore();

    void incrScore();

    boolean isBeginner();

    void setBeginner(boolean value);

    void resetPlayer();

    boolean getVerbose();

    boolean shipHit(String coordMissil);

    boolean updateShit(String coordMissil);

    void resultOfShot(String coordMissil,boolean hit, boolean sank);

}

