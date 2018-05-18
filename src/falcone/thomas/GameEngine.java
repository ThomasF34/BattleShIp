package falcone.thomas;

import falcone.thomas.Board.Coord;
import falcone.thomas.Players.Human;
import falcone.thomas.Players.IA;
import falcone.thomas.Players.IPlayer;
import falcone.thomas.Players.Player;

import java.util.concurrent.TimeUnit;

public class GameEngine {

    //Rules
    public static int LEN = 10;
    public static String LETTERS = "ABCDEFGHIJ";
    private static int[] lengths = {0,0,1,2,1,1}; //Meaning the player can put 1 ship of length 5 (cause lengths[5] == 1)

    private boolean verbose = true;
    private IPlayer p1;
    private IPlayer p2;

    public GameEngine(String nameHuman1, String nameHuman2) {

        // -------------- Init of game with humans ---------------------

        //Creation of first player
        this.p1 = new Human(nameHuman1, lengths);
        System.out.println("Joueur " + p1.getName() + " créé");

        //Creation of P1's ships
        p1.placeShips();
        transition(5);

        //Creation of second player
        this.p2 = new Human(nameHuman2, lengths);
        System.out.println("Joueur " + p2.getName() + " créé");

        //Creation of P2's ships
        p2.placeShips();
    }

    public GameEngine(String nameHuman, int level){
        //Creation of first player
        this.p1 = new Human(nameHuman, lengths);
        System.out.println("Joueur " + p1.getName() + " créé");

        //Creation of P1's ships
        p1.placeShips();
        transition(5);

        p2 = new IA("Watson",lengths,level);
        p2.placeShips();
    }

    public GameEngine(int level1, int level2){
        verbose = false;
        p1 = new IA("Watson",lengths,level1);
        p1.placeShips();
        p2 = new IA("DeepBlue", lengths,level2);
        p2.placeShips();
    }

    public String begin(){

        // ---------------- Beginning of game -------------
        IPlayer currentPlayer = p1;
        IPlayer ennemy = p2;

        while (currentPlayer.hasShipLeft() && ennemy.hasShipLeft()) {

            if (verbose) {
                transition(5);
                System.out.println(currentPlayer.getName() + ", c'est à vous de jouer");
                System.out.println("Vous avez coulé " + ennemy.getNumberSankShip() + " bateau(x) de " + ennemy.getName());
                System.out.println("Voici l'état de vos bateaux (X Touché | O Intact | - Eau)");
                System.out.println(currentPlayer.printShipGrid());
                System.out.println("Voici votre grille de tir réalisé(s) (X Touché | O Raté | - Eau)");
                System.out.println(currentPlayer.printHitGrid());
            }

            //Fire section
            String coordMissil = currentPlayer.giveShot();

            if (currentPlayer.hasAlreadyShot(coordMissil)) {
                if (verbose) {
                    System.out.println("Dommage, vous avez déja tiré ici...");
                }
            } else {
                boolean[] result = currentPlayer.fire(ennemy, coordMissil);

                boolean hit, sank;
                hit = result[0];
                sank = result[1];

                if (verbose) {
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

        if(verbose) {
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
            return ennemy.getName();
        } else {
            return currentPlayer.getName();
        }
    }

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



}


