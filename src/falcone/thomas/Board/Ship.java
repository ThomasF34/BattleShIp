package falcone.thomas.Board;

public class Ship {

    private Position position;

    /**
     * Cette fonction crée un bateau remplissant la fourchette de coordonnées données en paramètre
     * @param startCoord Coordonnée de départ (sous forme 'LettreChiffre')
     * @param endCoord Coordonnée de fin (sous forme 'LettreChiffre')
     *
     * On suppose que les coordonées ont déjà été vérifiées (Pas en dehors de la grille & pas de diago)
     */
    public Ship(String startCoord, String endCoord){
        position = new Position(new Coord(startCoord), new Coord(endCoord));
    }

    public boolean isHit(String missileCoord){
        /* Réponse du prof :  on DOIT prendre un string car c'est un point de contact avec l'exterieur.
        En revanche on peut tres bien prendre un String et le transformer immédiatement en Coord. ON EST DANS LE CONCEPT D'ENCAPSULATION */
        return position.isHit(new Coord(missileCoord));
    }

    public boolean isDestroyed(){
        return position.isDestroyed();
    }

    /**
     * This function is triggered when a position of the ship is hit. The coordinates becomes "hitted".
     * @param coordHit
     */
    public void positionHit(String coordHit){
        Coord coord = new Coord(coordHit);
        for(Coord c : position.getPos()){
            if(c.equals(coord)){
                c.setHit(true);
            }
        }
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
