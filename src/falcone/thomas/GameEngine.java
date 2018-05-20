package falcone.thomas;

import falcone.thomas.Players.*;

import java.util.concurrent.TimeUnit;

public class GameEngine {

    //Rules
    public static int LEN = 10;
    public static String LETTERS = "ABCDEFGHIJ";
    public static int[] rulesShip = {0,0,1,2,1,1}; //Meaning the player can put 1 ship of length 5 (cause lengths[5] == 1)

    private IPlayer p1;
    private IPlayer p2;

    public GameEngine(String nameHuman1, String nameHuman2) {

        // -------------- Init of game with humans ---------------------

        //Creation of first player
        this.p1 = new Human(nameHuman1);
        System.out.println("Joueur " + p1.getName() + " créé");

        //Creation of second player
        this.p2 = new Human(nameHuman2);
        System.out.println("Joueur " + p2.getName() + " créé");

        p1.setBeginner(true);
    }

    public GameEngine(String nameHuman, int level){
        //Creation of first player
        this.p1 = new Human(nameHuman);
        System.out.println("Joueur " + p1.getName() + " créé");

        switch(level){
            case 0:
                p2 = new AIBeginner("Watson");

                break;
            case 1:
                p2 = new AIMedium("Watson");

                break;
            default: //level 2
                p2 = new AIHardcore("Watson");
                break;
        }

        p1.setBeginner(true);
    }

    public GameEngine(int level1, int level2){
        switch(level1){
            case 0:
                p1 = new AIBeginner("Watson");

                break;
            case 1:
                p1 = new AIMedium("Watson");

                break;
            default: //level 2
                p1 = new AIHardcore("Watson");
                break;
        }

        switch(level2){
            case 0:
                p2 = new AIBeginner("DeepBlue");

                break;
            case 1:
                p2 = new AIMedium("DeepBlue");

                break;
            default: //level 2
                p2 = new AIHardcore("DeepBlue");
                break;
        }

        p1.setBeginner(true);
    }

    public IPlayer[] begin(){

        initGame();

        // ---------------- Beginning of game -------------
        IPlayer currentPlayer = getBeginner();
        IPlayer ennemy = getNotBeginner();

        while (currentPlayer.hasShipLeft() && ennemy.hasShipLeft()) {

            if (currentPlayer.getVerbose()) {
                transition(5);
                System.out.println(currentPlayer.getName() + ", c'est à vous de jouer");
                System.out.println("Vous avez coulé " + ennemy.getNumberSankShip() + " bateau(x) de " + ennemy.getName());
                System.out.println("Voici l'état de vos bateaux (X Touché | O Intact | - Eau)");
                System.out.println(currentPlayer.printShipGrid());
                System.out.println("Voici votre grille de tir réalisé(s) (X Touché | O Raté | - Eau)");
                System.out.println(currentPlayer.printHitGrid());
            }

            //Fire Section
            String coordMissil = currentPlayer.giveShot();

            if (currentPlayer.hasAlreadyShot(coordMissil)) {
                if (currentPlayer.getVerbose()) {
                    System.out.println("Dommage, vous avez déja tiré ici...");
                }
            } else {

                boolean hit = ennemy.shipHit(coordMissil);
                boolean sank = false;
                if(hit){
                    sank = ennemy.updateShit(coordMissil);
                }
                currentPlayer.resultOfShot(coordMissil,hit,sank);


                if (currentPlayer.getVerbose()) {
                    if (hit) {
                        System.out.println("Bravo !! Vous avez touché un des bateaux de " + ennemy.getName());
                        if (sank) {
                            System.out.println("Incroyable !! Vous venez de couler un des bateaux de " + ennemy.getName());
                        }
                    } else {
                        System.out.println("Raté !! Votre missile est tombé à l'eau");
                    }
                }
            }
            //Change of role
            IPlayer tmp = currentPlayer;
            currentPlayer = ennemy;
            ennemy = tmp;
        }

        changeBeginner();

        if(ennemy.getVerbose() || currentPlayer.getVerbose()) {
            if (!currentPlayer.hasShipLeft()) {
                System.out.println("Félicitations " + ennemy.getName() + ". Vous avez coulé tous les bateaux de " + currentPlayer.getName() + ".\n" +
                        "C'est à vous que revient la victoire.");
            }

            if (!ennemy.hasShipLeft()) {
                System.out.println("Félicitations " + currentPlayer.getName() + ". Vous avez coulé tous les bateaux de " + ennemy.getName() + ".\n" +
                        "C'est à vous que revient la victoire.");
            }
        }
        if (!currentPlayer.hasShipLeft()) {
            ennemy.incrScore();
        } else {
            currentPlayer.incrScore();
        }

        return new IPlayer[]{p1,p2};
    }

    /**
     * Initialization of the game : It resets Players and places ships
     */
    private void initGame() {
        p1.resetPlayer();
        p2.resetPlayer();
        p1.placeShips();
        //If 2nd player is human and infos are printed, we use transition to hide a little
        //the ships grid of first player
        if(p2.getVerbose()){
            transition(5);
        }
        p2.placeShips();
    }

    /**
     * @return Returns a String to describe the actual score of players
     */
    public String printScore() {
        return "Résultat : " + p1.getName() + " " + p1.getScore() + " | " + p2.getName() + " " + p2.getScore();
    }

    /**
     * Purely optionnal, this function is only here to delay the display and print new line.
     * Thus, the first player will have time to see the text printed and let the second one take
     * place without seeing the text of first player.
     * @param numNewLine Number of line you want to print to hide text
     */
    private static void transition(int numNewLine) {
        for(int i = 0; i <= 5; i++){
            try{
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.print(".");
            } catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println(".");

        for(int i = 1; i <= numNewLine; i++){
            System.out.println("\n");
        }
    }

    private IPlayer getBeginner() {
        if(p1.isBeginner()){
            return p1;
        }else{
            return p2;
        }
    }

    private IPlayer getNotBeginner(){
        if(p1.isBeginner()){
            return p2;
        } else {
            return p1;
        }
    }

    private void changeBeginner(){
        p1.setBeginner(!p1.isBeginner());
        p2.setBeginner(!p2.isBeginner());
    }
}


