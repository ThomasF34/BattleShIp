package falcone.thomas.Players;

import falcone.thomas.Board.Coord;
import falcone.thomas.Board.Ship;
import falcone.thomas.Checks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static falcone.thomas.GameEngine.LEN;
import static falcone.thomas.GameEngine.LETTERS;
import static falcone.thomas.GameEngine.rulesShip;

public class IA2 implements IPlayer{

    private String name;
    private int score;
    private ArrayList<Ship> ships = new ArrayList<>();
    private ArrayList<Coord> shots = new ArrayList<>();
    private int[] shipsToBeConstructed;
    private ArrayList<String> shotQueue = new ArrayList<>();
    private boolean beginner = false;

    public IA2(String name){
        this.name = name;
        shipsToBeConstructed = Arrays.copyOf(rulesShip,rulesShip.length);
    }

    //IA0's Functions

    private String[] getSurroundingCoord(String coord){
        char c = coord.charAt(0);
        int i = Character.getNumericValue(coord.charAt(1));
        char nextChar = (char) (c+1);
        char precChar = (char) (c-1);
        return new String[] {c+String.valueOf(i+1),c+String.valueOf(i-1),nextChar+String.valueOf(i),precChar+String.valueOf(i)};
    }

    private void addSurroundingCoordToQueue(String coordMissil) {
        for(String str : getSurroundingCoord(coordMissil)){
            if(Checks.checkCoord(str)){
                shotQueue.add(str);
            }
        }
    }

    /**
     * We assume that this.hasShipToBeConstructed == true
     * @param startCoord
     */
    private String shipsCoordinates(String startCoord){
        int i = 0;
        while(i < getShipsToBeConstructed().length && getShipsToBeConstructed()[i] == 0){
            i++;
        }
        //i is now the index of the first non-null member of getShipsToBeConstructed. Thus, it's the length of the next
        //ship to build
        int newInt;
        char newChar;
        String orient;
        String res;
        do{
            orient = randomOrient();
            switch(orient) {
                case ("up"):
                    newInt = Character.getNumericValue(startCoord.charAt(1)) - i + 1;
                    newChar = startCoord.charAt(0);
                    break;
                case ("down"):
                    newInt = Character.getNumericValue(startCoord.charAt(1)) + i - 1;
                    newChar = startCoord.charAt(0);
                    break;
                case ("left"):
                    newInt = Character.getNumericValue(startCoord.charAt(1));
                    int c = startCoord.charAt(0);
                    c -= i - 1;
                    newChar = (char) c;
                    break;
                default: //right
                    newInt = Character.getNumericValue(startCoord.charAt(1));
                    int c1 = startCoord.charAt(0);
                    c1 += i - 1;
                    newChar = (char) c1;
                    break;
            }
            res = "";
            res += newChar;
            res += newInt;

        }while(!Checks.checkCoord(res));
        return res;

    }

    private String randomOrient(){
        Random rand = new Random();
        int i = rand.nextInt(4);
        switch (i){
            case(0):
                return "down";
            case(1):
                return "up";
            case (2):
                return "left";
            default:
                return "right";
        }
    }

    private String randomCoord() {
        Random r = new Random();
        int i = r.nextInt(LEN);
        char c = LETTERS.charAt(r.nextInt(LETTERS.length()));
        return c + String.valueOf(i);
    }

    //IPlayer's functions

    /**
     * In order to begin a new party, resetPlayer reset all needed items.
     */
    public void resetPlayer(){
        ships = new ArrayList<>();
        shots = new ArrayList<>();
        shotQueue = new ArrayList<>();
        shipsToBeConstructed = Arrays.copyOf(rulesShip,rulesShip.length);
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
     * Automatically places the ship of IA0
     */
    public void placeShips(){
        while(hasShipsToBeConstructed()){
            String start = "";
            String end = "";
            do{
                start = randomCoord();
                end = shipsCoordinates(start);
            }while(!Checks.checkCanBePlaced(this,start,end));
            placeShip(start,end);
        }

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

    public String giveShot(){
        if(shotQueue.isEmpty()){
            //as level 1
            String shot;
            do{
                shot = randomCoord();
            }while(hasAlreadyShot(shot));
            return shot;
        } else {
            return shotQueue.remove(0);
        }
    }

    /**
     * This function fires the missile to playerToAttack's grid. It returns an array of 2 booleans. First one indicates if
     * a ship has been hit and the second one if the ship is sank.
     * @param playerToAttack
     * @param coordMissil
     * @return
     */
    public boolean[] fire(IPlayer playerToAttack, String coordMissil){
        Coord coord = new Coord(coordMissil);
        for(Ship ship : playerToAttack.getShips()){
            if(ship.isHit(coordMissil)){
                ship.positionHit(coordMissil);
                coord.setHit(true);
                addSurroundingCoordToQueue(coordMissil);
                getShots().add(coord);
                if(ship.isDestroyed()){
                    shotQueue = new ArrayList<>();
                    return new boolean[]{true, true};
                } else {
                    return new boolean[]{true, false};
                }
            }
        }
        getShots().add(coord);
        return new boolean[] {false,false};
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

    //Printing informations

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
                    for(Coord coordShip : ship.getPosition().getPos()){
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

    //Getter - Setter

    public int[] getShipsToBeConstructed() {
        return shipsToBeConstructed;
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
}
