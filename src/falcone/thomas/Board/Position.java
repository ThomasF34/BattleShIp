package falcone.thomas.Board;

import java.util.ArrayList;

public class Position {

    private Coord startCoord;
    private Coord endCoord;
    private ArrayList<Coord> pos = new ArrayList<>();


    /**
     * Creation of the ship's position. Position contains both starting and ending coordinates, as well as a list of coordinates that are occupied by the given ship.
     * @param startCoord
     * @param endCoord
     *
     * Here, we suppose that the coordinates have been verified
     */
    public Position(Coord startCoord, Coord endCoord){
        this.startCoord = startCoord;
        this.endCoord = endCoord;

        //We check if the order of the given coordinates are corrects and we fill the list with occupied coordinates.
        if(isVertical()){
            if(startCoord.getFigure() > endCoord.getFigure()){
                this.startCoord = endCoord;
                this.endCoord = startCoord;
            }

            for(int i = this.startCoord.getFigure(); i <= this.endCoord.getFigure(); i++){
                pos.add(new Coord(this.startCoord.getChar(),i));
            }

        } else {
            if(startCoord.getChar() > endCoord.getChar()){
                this.startCoord = endCoord;
                this.endCoord = startCoord;
            }

            for(char i = this.startCoord.getChar(); i <= this.endCoord.getChar(); i++){
                pos.add(new Coord(i,this.startCoord.getFigure()));
            }
        }

    }

    public boolean isHit(Coord missilCoord){
        for(Coord i : pos){
            if(missilCoord.equals(i)){
                return true;
            }
        }
        return false;
    }

    public boolean isVertical() {
        return startCoord.getChar() == endCoord.getChar();
    }

    @Override
    public String toString() {
        String msg =  "Le bateau commence à la coordonnée " + startCoord.toString() + " et se termine à la coordonnée " + endCoord.toString() +"\n Il occupe donc les cases : ";
        for(Coord i : pos){
            msg += i.toString();
            msg += " - ";
        }
        return msg;
    }

    public boolean isDestroyed() {
        for(Coord c : pos){
            if(!c.getHit()){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Coord> getPos() {
        return pos;
    }
}

