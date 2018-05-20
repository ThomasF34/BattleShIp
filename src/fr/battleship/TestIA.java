package fr.battleship;

import falcone.thomas.GameEngine;
import falcone.thomas.Players.IPlayer;

import java.io.FileWriter;
import java.io.IOException;

public class TestIA {

    private static int NBGAME = 100;

    public static void main(String[] args){

        FileWriter f = null;
        try {
            f = initializeFile();
        } catch (IOException e){
            System.out.println(e.getMessage());;
        }

        launchAIGame(0, 1, f);
        launchAIGame(0, 2, f);
        launchAIGame(1, 2, f);

        if( f != null ){
            try {
                f.close();
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }

    }

    private static FileWriter initializeFile() throws IOException{
        FileWriter fileWriter = new FileWriter("ai_proff.csv");
        fileWriter.write("AI Name; score; AI Name2; score2\n");
        return fileWriter;
    }

    /**
     * @param lvlIA1 Level of first AI
     * @param lvlIA2 level of second AI
     * @param fileWriter fileWriter to write the result in the csv
     * @return String describing results
     */
    private static void launchAIGame(int lvlIA1, int lvlIA2, FileWriter fileWriter) {
        GameEngine ge = new GameEngine(lvlIA1,lvlIA2);
        for(int i = 0; i < NBGAME - 1; i++){
            ge.begin();
        }
        IPlayer[] results = ge.begin();
        System.out.println(results[0].getName() + " Niveau : " + lvlIA1 + " Score : " + results[0].getScore() + "\n" +
                results[1].getName() + " Niveau : " + lvlIA2 + " Score : " + results[1].getScore());

        StringBuilder sb = new StringBuilder();
        sb.append(getTextLevel(lvlIA1));
        sb.append(";");
        sb.append(results[0].getScore());
        sb.append(";");
        sb.append(getTextLevel(lvlIA2));
        sb.append(";");
        sb.append(results[1].getScore());
        sb.append("\n");
        if(fileWriter != null){
            try{
                fileWriter.write(sb.toString());
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private static String getTextLevel(int lvl){
        switch (lvl) {
            case 0:
                return "Beginner";
            case 1:
                return "Medium";
            default:
                return "Hard";
        }
    }


}
