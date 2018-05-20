package falcone.thomas.Players;

import falcone.thomas.Board.Coord;
import falcone.thomas.Board.Ship;
import falcone.thomas.Checks;
import falcone.thomas.Inputs;

import java.util.ArrayList;
import java.util.Arrays;

import static falcone.thomas.GameEngine.LEN;
import static falcone.thomas.GameEngine.LETTERS;
import static falcone.thomas.GameEngine.rulesShip;


public class Human implements IPlayer {

    private String name;
    private ArrayList<Ship> ships = new ArrayList<>();
    private ArrayList<Coord> shots = new ArrayList<>();
    private int[] shipsToBeConstructed;
    private int score;
    private boolean beginner = false;

    public Human(String name){
        this.name = name;
        shipsToBeConstructed = Arrays.copyOf(rulesShip,rulesShip.length);
    }

    /**
     * In order to begin a new party, resetPlayer reset all needed items.
     */
    public void resetPlayer(){
        ships = new ArrayList<>();
        shots = new ArrayList<>();
        shipsToBeConstructed = Arrays.copyOf(rulesShip,rulesShip.length);
    }

    /**
     * Ask player to place each ship until there's no more ship to be placed
     */
    public void placeShips() {
        System.out.println(name+", c'est à votre tour de placer vos bateaux");
        System.out.println(printShipGrid());
        while(hasShipsToBeConstructed()) {
            System.out.println(shipsLeft());
            String coord1;
            String coord2;
            do{
                do {
                    do {
                        do {
                            coord1 = Inputs.askCoord("première");
                        }while(!Checks.checkCoord(coord1));
                        do {
                            coord2 = Inputs.askCoord("seconde");
                        } while (!Checks.checkCoord(coord2));
                    } while (Checks.checkDiago(coord1, coord2));
                    //Coord1 and 2 are both corects and aren't making a diagonal.
                    System.out.println("Vous tentez de placer un bateau de taille " + Coord.length(coord1,coord2));
                }while(!Checks.checkCanBuildLength(this,Coord.length(coord1,coord2)));
                //A ship of this length can be placed
            }while(!Checks.checkCanBePlaced(this,coord1,coord2));
            //Ship can be placed

            placeShip(coord1, coord2);
            System.out.println("Bateau placé");
            System.out.println(printShipGrid());
        }
    }

    /**
     * This function places the ship. We assume that the coordinates are both correct and aren't making a diagonal
     * Plus we are assuming that the ship can be placed by the player.
     * @param coord1
     * @param coord2
     */
    public void placeShip(String coord1, String coord2){
        Ship sh = new Ship(coord1,coord2);
        getShips().add(sh);
        getShipsToBeConstructed()[Coord.length(coord1,coord2)] -= 1;
    }

    /**
     * @return true if the player has any ship left to place on the grid, false if not
     */
    public boolean hasShipsToBeConstructed(){
        for(int i : shipsToBeConstructed) {
            if (i != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if the player has any ship that isn't sank, or false if all ships are sank.
     */
    public boolean hasShipLeft(){
        for(Ship ship : ships){
            if (!ship.isDestroyed()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Indicates if the player has already shot at this coordinate
     * @param coordMissil coordinate
     * @return true if player has already shot there, false if not.
     */
    public boolean hasAlreadyShot(String coordMissil) {
        Coord coord = new Coord(coordMissil);
        for(Coord c : shots){
            if(c.equals(coord)){
                return true;
            }
        }
        return false;
    }

    /**
     * @return This function asks the shot to the player and returns it
     */
    public String giveShot(){
        String coordMissil;
        do{
            coordMissil = Inputs.askShot(this);
        }while(!Checks.checkCoord(coordMissil));
        return coordMissil;
    }

    public boolean shipHit(String coordMissil) {
        for (Ship ship : getShips()) {
            if (ship.isHit(coordMissil)) {
                return true;
            }
        }
        return false;
    }

    public boolean updateShit(String coordMissil) {
        for (Ship ship : getShips()) {
            if(ship.isHit(coordMissil)){
                ship.positionHit(coordMissil);
                return ship.isDestroyed();
            }
        }
        return false;
    }

    public void resultOfShot(String coordMissil, boolean hit, boolean sank){
        Coord coord = new Coord(coordMissil);
        coord.setHit(hit);
        shots.add(coord);
    }


    public String printShipGrid(){
        StringBuffer msg = new StringBuffer();
        msg.append("  ");

        for(char i = LETTERS.charAt(0);i <= LETTERS.charAt(LETTERS.length()-1); i++) {
            msg.append(i);
            msg.append(" ");
        }
        msg.append("\n");

        int line = 0;
        for(int j = 0; j < LEN; j++){
            msg.append(line);
            msg.append(" ");
            for(char i = LETTERS.charAt(0);i <= LETTERS.charAt(LETTERS.length()-1); i++){
                Coord c = new Coord(i,j);
                boolean found = false;
                for(Ship ship : ships){
                    for(Coord coordShip : ship.getPosition()){
                        if(c.equals(coordShip)){
                            found = true;
                            if(coordShip.getHit()){
                                msg.append("X");
                            } else {
                                msg.append("O");
                            }
                        }
                    }
                }
                if(!found) {
                    msg.append("-");
                }
                msg.append(" ");
            }
            msg.append("\n");
            line++;
        }
        return msg.toString();
    }

    public String printHitGrid(){
        StringBuffer msg = new StringBuffer();
        msg.append("  ");

        for(char i = LETTERS.charAt(0);i <= LETTERS.charAt(LETTERS.length()-1); i++) {
            msg.append(i);
            msg.append(" ");
        }
        msg.append("\n");

        int line = 0;
        for(int j = 0; j < LEN; j++) {
            msg.append(line);
            msg.append(" ");
            for (char i = LETTERS.charAt(0); i <= LETTERS.charAt(LETTERS.length() - 1); i++) {
                Coord c = new Coord(i, j);
                boolean found = false;
                for (Coord shot : shots) {
                    if (shot.equals(c)) {
                        found = true;
                        if(shot.getHit()) {
                            msg.append("X");
                        } else {
                            msg.append("O");
                        }
                    }
                }
                if (!found) {
                    msg.append("-");
                }
                msg.append(" ");
            }
            msg.append("\n");
            line++;
        }
        return msg.toString();
    }

    /**
     * Shows a list of remaining ships to place
     */
    private String shipsLeft(){
        int[] list = this.getShipsToBeConstructed();
        StringBuffer msg = new StringBuffer();
        msg.append("----------- \nTaille des bateaux restant à placer : \n");
        for(int i = 1; i < list.length; i++){
            if(list[i]!=0){
                msg.append(list[i]) ;
                msg.append(" bateau(x) de taille ");
                msg.append(i);
                msg.append("\n");
            }
        }
        msg.append("----------");
        return msg.toString();
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Coord> getShots() {
        return shots;
    }

    public int getNumberSankShip(){
        int count = 0;
        for(Ship ship : ships){
            if(ship.isDestroyed()){
                count ++;
            }
        }
        return count;
    }

    public int[] getShipsToBeConstructed() {
        return shipsToBeConstructed;
    }

    public int getScore(){
        return score;
    }

    public void incrScore(){
        score++;
    }

    public boolean isBeginner() {
        return beginner;
    }

    public void setBeginner(boolean beginner) {
        this.beginner = beginner;
    }

    public boolean getVerbose() {
        return true;
    }
}
