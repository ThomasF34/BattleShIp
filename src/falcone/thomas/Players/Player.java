package falcone.thomas.Players;

import falcone.thomas.Board.Coord;
import falcone.thomas.Board.Ship;
import falcone.thomas.Checks;
import falcone.thomas.Inputs;

import java.util.ArrayList;
import java.util.Arrays;

import static falcone.thomas.GameEngine.LEN;
import static falcone.thomas.GameEngine.LETTERS;

public class Player {//implements IPlayer {

    /*

    public Player(String name, int[] rulesShip){

        this.name = name;
        shipsToBeConstructed = Arrays.copyOf(rulesShip,rulesShip.length);
    }


    *//**
     * This function places the ship. We assume that the coordinates are both correct and aren't making a diagonal
     * Plus we are assuming that the ship can be placed by the player.
     * @param coord1
     * @param coord2
     *//*
    public void placeShip(String coord1, String coord2){
        Ship sh = new Ship(coord1,coord2);
        getShips().add(sh);
        getShipsToBeConstructed()[Coord.length(coord1,coord2)] -= 1;
    }

    *//**
     * @return true if the player has any ship left to place on the grid, false if not
     *//*
    public boolean hasShipsToBeConstructed(){
        for(int i : shipsToBeConstructed) {
            if (i != 0) {
                return true;
            }
        }
        return false;
    }

    *//**
     * @return true if the player has any ship that isn't sank, or false if all ships are sank.
     *//*
    public boolean hasShipLeft(){
        for(Ship ship : ships){
            if (!ship.isDestroyed()) {
                return true;
            }
        }
        return false;
    }

    *//**
     * Indicates if the player has already shot at this coordinate
     * @param coordMissil coordinate
     * @return true if player has already shot there, false if not.
     *//*
    public boolean hasAlreadyShot(String coordMissil) {
        Coord coord = new Coord(coordMissil);
        for(Coord c : shots){
            if(c.equals(coord)){
                return true;
            }
        }
        return false;
    }

    *//**
     * @param LEN
     * @param LETTERS
     * @return This function asks the shot to the player and returns it
     *//*
    public String giveShot(int LEN, String LETTERS){
        String coordMissil;
        do{
            coordMissil = Inputs.askShot(this);
        }while(!Checks.checkCoord(coordMissil));
        return coordMissil;
    }

    *//**
     * This function fires the missile to playerToAttack's grid. It returns an array of 2 booleans. First one indicates if
     * a ship has been hit and the second one if the ship is sank.
     * @param playerToAttack
     * @param coordMissil
     * @return
     *//*
    public boolean[] fire(Player playerToAttack, String coordMissil){
        Coord coord = new Coord(coordMissil);
        for(Ship ship : playerToAttack.getShips()){
            if(ship.isHit(coordMissil)) {
                ship.positionHit(coordMissil);
                coord.setHit(true);
                shots.add(coord);
                return new boolean[]{true, ship.isDestroyed()};
            }
        }
        shots.add(coord);
        return new boolean[] {false,false};
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
    }*/

}
