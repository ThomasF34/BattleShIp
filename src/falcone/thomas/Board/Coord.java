package falcone.thomas.Board;

public class Coord {
    private char letter;
    private int figure;
    private boolean hit;

    /**
     * Création des coordonnées. Renvoie des exceptions si la coordonnée est en dehors de la grille
     * @param letter
     * @param figure
     */
    public Coord(char letter, int figure) {
        this.letter = letter;
        this.figure = figure;
        this.hit = false;
    }

    public Coord(String coord){
        this.letter = coord.charAt(0);
        this.figure = Integer.parseInt(coord.substring(1));
    }

    public char getChar(){
        return this.letter;
    }

    public int getFigure(){
        return this.figure;
    }

    @Override
    public String toString() {
        return letter + String.valueOf(figure);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coord){
            return ((Coord) obj).getFigure() == this.figure && ((Coord) obj).getChar() == this.letter;
        } else {
            return false;
        }
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean getHit() {
        return hit;
    }

    /**
     * @return length of the potential ship
     */
    public static int length(String startCoord, String endCoord){
        if(startCoord.charAt(0) == endCoord.charAt(0)){
            //Vertical
            return Math.abs(startCoord.charAt(1)-endCoord.charAt(1))+1;
        }else{
            //Horizontal
            return Math.abs(startCoord.charAt(0)-endCoord.charAt(0))+1;
        }
    }

}
