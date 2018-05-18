package falcone.thomas;

public class Main {

    public static void main(String[] args) {
        while(true) {
            switch (Inputs.askMode()) {
                case 0: //Human vs Human
                    String name1 = Inputs.askName(1);
                    String name2 = Inputs.askName(2);
                    GameEngine HvH = new GameEngine(name1, name2);
                    break;
                case 1: // Human vs IA0
                    String name = Inputs.askName(1);
                    int lvl = Inputs.askLevel(1);
                    GameEngine HvIA = new GameEngine(name, lvl);
                    break;
                case 2: // IA0 vs IA0
                    int lvlFirstAI = Inputs.askLevel(1);
                    int lvlSecondAI = Inputs.askLevel(2);
                    GameEngine IAvIA = new GameEngine(lvlFirstAI, lvlSecondAI);
                    break;
                case 3: // Benchmark Manuel
                    int lvlIA1 = Inputs.askLevel(1);
                    int lvlIA2 = Inputs.askLevel(2);
                    int nbGame = Inputs.askNbGame();
                    //System.out.println(launchAIGame(lvlIA1, lvlIA2, nbGame));
                    break;
                case 4: //Benchmark Automatique
                   /* System.out.println(launchAIGame(0, 1, 100));
                    System.out.println(launchAIGame(0, 2, 100));
                    System.out.println(launchAIGame(1, 2, 100));*/
                    break;
                default: //Error
                    System.exit(0);
                    break;
            }
        }
    }



    // TODO: 11/05/2018 Benchmark auto
    // TODO: 16/05/2018 Verfi interfaces

}
