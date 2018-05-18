package fr.battleShip;

import falcone.thomas.GameEngine;
import falcone.thomas.Players.IPlayer;

public class testIA {

    public static void main(String[] args){
        System.out.println(launchAIGame(0, 1, 100));
        System.out.println(launchAIGame(0, 2, 100));
        System.out.println(launchAIGame(1, 2, 100));
    }

    /**
     * @param lvlIA1 Level of first AI
     * @param lvlIA2 level of second AI
     * @param nbGame number of game simulation
     * @return String describing results
     */
    private static String launchAIGame(int lvlIA1, int lvlIA2, int nbGame) {
        int w = 0;
        int db = 0;
        GameEngine ge = new GameEngine(lvlIA1,lvlIA2);
        for(int i = 0; i < nbGame; i++){
            String winner = ge.begin();
            if(winner.equals("Watson")){
                w ++;
            }else{
                db ++;
            }
        }
        return "Resultat : Watson (Niv " + lvlIA1 + ") "+ w + " DeepBlue (Niv " + lvlIA2 + ") " + db ;/*GameEngine ge = new GameEngine(lvlIA1,lvlIA2);
        for(int i = 0; i < nbGame; i++){
            ge.begin();
        }
        return "Resultat : Watson (Niv " + lvlIA1 + ") "+ ge.getP1().getScore()  + " DeepBlue (Niv " + lvlIA2 + ") " + ge.getP2().getScore()  ;*/
    }
}
