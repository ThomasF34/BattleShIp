package falcone.thomas;

public class Battleship {

    public static void main(String[] args) {
        while(true) {
            int mode = Inputs.askMode();
            switch (mode) {
                case 0: //Human vs Human
                    String name1 = Inputs.askName(1);
                    String name2 = Inputs.askName(2);
                    GameEngine HvH = new GameEngine(name1, name2);
                    play(HvH);
                    break;
                case 1: // Human vs AIBeginner
                    String name = Inputs.askName(1);
                    int lvl = Inputs.askLevel(1);
                    GameEngine HvIA = new GameEngine(name, lvl);
                    play(HvIA);
                    break;
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
    // TODO: 16/05/2018 Verfi interfaces

}
