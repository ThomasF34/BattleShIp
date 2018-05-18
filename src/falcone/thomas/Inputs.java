package falcone.thomas;

import falcone.thomas.Players.IPlayer;

import java.util.Scanner;

public class Inputs {

    private static Scanner sc = new Scanner(System.in);

    static int askNbGame() {
        System.out.println("Combien de match voulez-vous lancer pour ce benchmark ?");
        return sc.nextInt();
    }

    static int askLevel(int i) {
        int lvl;
        do{
            System.out.println("Quel est le niveau de l'IA n°"+ i +" ?");
            lvl = sc.nextInt();
        }while(lvl < 0 || lvl > 2);
        return lvl;
    }

    static String askName(int i) {
        System.out.println("Quel est le nom du joueur n°"+i+" ?");
        String line = sc.nextLine();
        return line;
    }

    static int askMode(){
        System.out.println("Quel mode ? \n0 Human vs Human\n1 IA vs Human \n2 IA vs IA\n3 Benchmark Manuel\n4 Benchmark Automatique\nAutre nombre pour quitter");
        return sc.nextInt();
    }

    static String askCoord(String s) {
        System.out.println("Entrez la " + s + "coordonée");
        return sc.nextLine();
    }

    public static String askShot(IPlayer player){
        System.out.println(player.getName() + ", où voulez vous tirer ?");
        return sc.nextLine();
    }
}
