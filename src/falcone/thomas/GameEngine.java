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
        placePlayerShips(p1);
        transition(5);

        //Creation of second player
        this.p2 = new Player(nameHuman2, lengths);
        System.out.println("Joueur " + p2.getName() + " créé");

        //Creation of P2's ships
        placePlayerShips(p2);
    }

    public GameEngine(String nameHuman, int level){
        //Creation of first player
        this.p1 = new Player(nameHuman, lengths);
        System.out.println("Joueur " + p1.getName() + " créé");

        //Creation of P1's ships
        placePlayerShips(p1);
        transition(5);

        p1 = new IA("Watson",lengths,level);
        placeIAShip((IA) p1);
    }

    public GameEngine(int level1, int level2){
        verbose = false;
        p1 = new IA("Watson",lengths,level1);
        placeIAShip((IA) p1);
        p2 = new IA("DeepBlue", lengths,level2);
        placeIAShip((IA) p2);
    }

    public String begin(){

        // ---------------- Beginning of game -------------
        Player currentPlayer = p1;
        Player ennemy = p2;

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
            String coordMissil = currentPlayer.giveShot(LEN, LETTERS);

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
            Player tmp = currentPlayer;
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

    /**
     * Automatically places the ship of IA
     * @param ia
     */
    private static void placeIAShip(IA ia){
        while(ia.hasShipsToBeConstructed()){
            String start = "";
            String end = "";
            do{
                start = ia.randomCoord(LEN,LETTERS);
                end = ia.shipsCoordinates(start);
            }while(!Checks.checkCanBePlaced(ia,start,end));
            ia.placeShip(start,end);
        }

    }

    /**
     * Ask player to place each ship until there's no more ship to be placed
     * @param player
     */
    private static void placePlayerShips(Player player) {
        System.out.println(player.printShipGrid());
        while(player.hasShipsToBeConstructed()) {
            System.out.println(shipsLeft(player));
            String coord1;
            String coord2;
            do{
                 do {
                     do {
                         coord1 = "";
                         coord2 = "";
                         while(!Checks.checkCoord(coord1)) {
                             coord1 = Inputs.askCoord("première");
                         }
                         while (!Checks.checkCoord(coord2)) {
                             coord2 = Inputs.askCoord("seconde");
                         }
                     } while (Checks.checkDiago(coord1, coord2));
                     //Coord1 and 2 are both corects and aren't making a diagonal.
                     System.out.println("Vous tentez de placer un bateau de taille " + Coord.length(coord1,coord2));
                 }while(!Checks.checkCanBuildLength(player,Coord.length(coord1,coord2)));
                 //A ship of this length can be placed
             }while(!Checks.checkCanBePlaced(player,coord1,coord2));
            //Ship can be placed

            player.placeShip(coord1, coord2);
            System.out.println("Bateau placé");
            System.out.println(player.printShipGrid());
        }
    }

    /**
     * Shows a list of remaining ships to place
     */
    private static String shipsLeft(Player player){
        int[] list = player.getShipsToBeConstructed();
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

}


