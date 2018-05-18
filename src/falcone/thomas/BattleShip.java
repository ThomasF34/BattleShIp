package falcone.thomas;

public class BattleShip {

    public static void main(String[] args) {
        while(true) {
            int mode = Inputs.askMode();
            switch (0) {
                case 0: //Human vs Human
                    String name1 = Inputs.askName(1);
                    //String name2 = Inputs.askName(2);
                    GameEngine HvH = new GameEngine(name1, name1);
                    play(HvH);
                    break;
                case 1: // Human vs IA0
                    String name = Inputs.askName(1);
                    int lvl = Inputs.askLevel(2);
                    GameEngine HvIA = new GameEngine(name, lvl);
                    play(HvIA);
                    break;
                /*case 2: // IA0 vs IA0
                    int lvlFirstAI = Inputs.askLevel(1);
                    int lvlSecondAI = Inputs.askLevel(2);
                    GameEngine IAvIA = new GameEngine(lvlFirstAI, lvlSecondAI);
                    play(IAvIA);
                    break;*/
                default: //Error
                    System.exit(0);
                    break;
            }
        }
    }

    private static void play(GameEngine ge){
        boolean playAgain;
        do{
            ge.begin();
            System.out.println(ge.printScore());
            playAgain = Inputs.askContinue();
        }while(playAgain);
    }
    // TODO: 11/05/2018 Benchmark auto
    // TODO: 16/05/2018 Verfi interfaces

}
