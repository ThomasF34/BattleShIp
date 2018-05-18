package falcone.thomas;

import falcone.thomas.Board.Coord;
import falcone.thomas.Board.Position;
import falcone.thomas.Board.Ship;
import falcone.thomas.Players.Player;

public abstract class Checks {
    /**
     * This function check if the given coordinate is correct : Firstly concerning the syntax (a character then a figure)
     * Secondly concerning the semantic (a character in the grid and a figure in the grid)
     * Returns true if the coordinate is correct, false if not.
     * @param coord
     * @return
     */
    public static boolean checkCoord(String coord){
        if(coord.length()==2){
            if(GameEngine.LETTERS.indexOf(coord.charAt(0))>=0){
                if(Character.isDigit(coord.charAt(1)) && Character.getNumericValue(coord.charAt(1)) < GameEngine.LEN) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This function returns true if both coordinates are forming a diagonal, false if not.
     * WE ASSUME THAT THE COORDINATES ARE CORRECTS (meaning that checkCoord(coord1) = true and same with coord2)
     * @param coord1
     * @param coord2
     * @return
     */
    static boolean checkDiago(String coord1, String coord2){
        if(coord1.charAt(0) != coord2.charAt(0) && coord1.charAt(1)!= coord2.charAt(1)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * This function returns true if the player can build a shio of the given length, false if not.
     * @param player
     * @param length
     * @return
     */
    static boolean checkCanBuildLength(Player player, int length){
        int[] list = player.getShipsToBeConstructed();
        if(length >= list.length){
            return false;
        } else {
            return list[length] != 0;
        }

    }

    /**
     * This function returns true if a ship can be placed on given coordinates. It checks if any other player's ship is
     * already placed on any position between start and end coordinates.
     * @param player
     * @param startCoord
     * @param endCoord
     * @return true if ship can be placed, false if not
     */
    static boolean checkCanBePlaced(Player player, String startCoord, String endCoord){
        Position posFutureShip = new Position(new Coord(startCoord), new Coord(endCoord));
        for(Coord coordFutureShip : posFutureShip.getPos()){
            for(Ship playerShip : player.getShips()){
                for(Coord coord : playerShip.getPosition().getPos()){
                    if(coord.equals(coordFutureShip)){
                        //System.out.println("Ce bateau chevauche un autre bateau !! ");
                        return false;
                    }
                }

            }
        }
        return true;
    }
}
