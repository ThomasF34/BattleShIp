package falcone.thomas;

import falcone.thomas.Players.IPlayer;

import java.util.Scanner;

public abstract class Inputs{

    private static Scanner sc = new Scanner(System.in);

    public static int askLevel(int i) {
        int lvl;
        do{
            System.out.println("Quel est le niveau de l'IA n°"+ i +" ? (0 Beginner | 1 Medium | 2 Hard");
            lvl = Integer.parseInt(sc.nextLine());//sc.nextInt() only read the int and following sc.nextLine()
            //will return an empty lines. With ParseInt(nextLine) we avoid this problem.
        }while(lvl < 0 || lvl > 2);
        return lvl;
    }

    public static String askName(int i) {
        System.out.println("Quel est le nom du joueur n°"+ i +" ?");
        return sc.nextLine();
    }

    public static int askMode(){
        System.out.println("Quel mode ? \n0 Human vs Human\n1 IA vs Human \nAutre nombre pour quitter");
        return Integer.parseInt(sc.nextLine()); //sc.nextInt() only read the int and following sc.nextLine()
        //will return an empty lines. With ParseInt(nextLine) we avoid this problem.
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
            res = Integer.parseInt(sc.nextLine());//sc.nextInt() only read the int and following sc.nextLine()
            //will return an empty lines. With ParseInt(nextLine) we avoid this problem.
        } while (res != 0 && res != 1);
        return res == 1;
    }
}
