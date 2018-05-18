package falcone.thomas;

import falcone.thomas.Players.IPlayer;

import java.util.Scanner;

public class Inputs{

    private static Scanner sc = new Scanner(System.in);

    public static int askNbGame() {
        System.out.println("Combien de match voulez-vous lancer pour ce benchmark ?");
        return sc.nextInt();
    }

    public static int askLevel(int i) {
        int lvl;
        do{
            System.out.println("Quel est le niveau de l'IABeginner n°"+ i +" ?");
            lvl = sc.nextInt();
        }while(lvl < 0 || lvl > 2);
        return lvl;
    }

    public static String askName(int i) { // FIXME: 18/05/2018 Impression des deux lignes à la fois
        System.out.println("Quel est le nom du joueur n°"+ i +" ?");
        System.out.println("Il m'a pas répondu");
        String line = sc.nextLine();
        System.out.println("Il m'a répondu");
        return line;
    }

    public static int askMode(){
        System.out.println("Quel mode ? \n0 Human vs Human\n1 IABeginner vs Human \nAutre nombre pour quitter");
        return sc.nextInt();
    }

    public static String askCoord(String s) {
        System.out.println("Entrez la " + s + " coordonée");
        return sc.nextLine();
    }

    public static String askShot(IPlayer player){
        System.out.println(player.getName() + ", où voulez vous tirer ?");
        return sc.nextLine();
    }

    public static boolean askContinue() {
        int res;
        do {
            System.out.println("Voulez-vous refaire une partie ? (0 | 1)");
            res = sc.nextInt();
        } while (res != 0 && res != 1);
        return res == 1;
    }
}
