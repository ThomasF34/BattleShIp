package falcone.thomas.Board;

import java.util.ArrayList;

public class Ship {

    private Coord startCoord;
    private Coord endCoord;
    private ArrayList<Coord> position = new ArrayList<>();

    /**
     * This function creates the ship and fill the list Position.
     * @param startC departure coordinates (String with format 'LetterFigure')
     * @param endC End coordinates (String with format 'LetterFigure')
     *
     * Supoosing coordinates are checked (Not out of bound and not making diagonal)
     */
    public Ship(String startC, String endC){
        this.startCoord = new Coord(startC);
        this.endCoord = new Coord(endC);

        //We check if the order of the given coordinates are corrects and we fill the list with occupied coordinates.
        if(isVertical()){
            if(startCoord.getFigure() > endCoord.getFigure()){
                Coord tmp = startCoord;
                this.startCoord = endCoord;
                this.endCoord = tmp;
            }

            for(int i = this.startCoord.getFigure(); i <= this.endCoord.getFigure(); i++){
                position.add(new Coord(this.startCoord.getChar(),i));
            }

        } else {
            if(startCoord.getChar() > endCoord.getChar()){
                Coord tmp = startCoord;
                this.startCoord = endCoord;
                this.endCoord = tmp;
            }

            for(char i = this.startCoord.getChar(); i <= this.endCoord.getChar(); i++){
                position.add(new Coord(i,this.startCoord.getFigure()));
            }
        }
    }

    public boolean isHit(String missileCoord){
        /* Réponse du prof :  on DOIT prendre un string car c'est un point de contact avec l'exterieur.
        En revanche on peut tres bien prendre un String et le transformer immédiatement en Coord. ON EST DANS LE CONCEPT D'ENCAPSULATION */
        return isHit(new Coord(missileCoord));
    }

    private boolean isHit(Coord missilCoord){
        for(Coord i : position){
            if(missilCoord.equals(i)){
                return true;
            }
        }
        return false;
    }

    private boolean isVertical() {
        return startCoord.getChar() == endCoord.getChar();
    }

    public boolean isDestroyed() {
        for(Coord c : position){
            if(!c.getHit()){
                return false;
            }
        }
        return true;
    }

    /**
     * This function is triggered when a position of the ship is hit. The coordinates becomes "hitted".
     * @param coordHit
     */
    public void positionHit(String coordHit){
        Coord coord = new Coord(coordHit);
        for(Coord c : position){
            if(c.equals(coord)){
                c.setHit(true);
            }
        }
    }

    public ArrayList<Coord> getPosition() {
        return position;
    }

    public String toString() {
        StringBuilder msg = new StringBuilder();
        msg.append("Le bateau commence à la coordonnée ");
        msg.append(startCoord.toString());
        msg.append(" et se termine à la coordonnée ");
        msg.append(endCoord.toString());
        msg.append("\n Il occupe donc les cases : ");
        for(Coord i : position){
            msg.append(i.toString());
            msg.append(" - ");
        }
        return msg.toString();
    }
}
