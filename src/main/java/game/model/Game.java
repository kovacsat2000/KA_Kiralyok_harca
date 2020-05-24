package game.model;

import javafx.util.Pair;

import java.util.*;

public class Game {
    public static final int TABLE_SIZE_X = 6;
    public static final int TABLE_SIZE_Y = 8;

    public boolean isFirstPlayer = true;

    public boolean wasACellDisabled = true;

    private int[][] table = new int[TABLE_SIZE_X][TABLE_SIZE_Y];

    public int stepCounter = 0;

    public Game() {
    }

    public void initTable() {
        for (int i = 0; i < TABLE_SIZE_X; ++i) {
            for (int j = 0; j < TABLE_SIZE_Y; ++j) {
                table[i][j] = 0;
            }
        }

        Random rand = new Random();
        int pos_1 = rand.nextInt(5);
        int pos_2 = rand.nextInt(5);

        table[pos_1][0] = 1;
        table[pos_2][TABLE_SIZE_Y - 1] = 2;
    }

    public int getTableCell(int row, int column){
        return table[row][column];
    }

    private Pair getPlayersPosition(int player){
        int posX = 0;
        int posY = 0;

        for (int i = 0; i < TABLE_SIZE_X; ++i) {
            for (int j = 0; j < TABLE_SIZE_Y; ++j) {
                if (table[i][j] == player) {
                    posX = i;
                    posY = j;
                }
            }
        }

        Pair pos = new Pair(posX, posY);

        return pos;
    }

    private Pair directionToPairs(int direction){
        if (direction == 1){
            Pair pos = new Pair(-1, 0);
            return pos;
        } else if (direction == 2){
            Pair pos = new Pair(-1, 1);
            return pos;
        } else if (direction == 3){
            Pair pos = new Pair(0, 1);
            return pos;
        } else if (direction == 4){
            Pair pos = new Pair(1, 1);
            return pos;
        } else if (direction == 5){
            Pair pos = new Pair(1, 0);
            return pos;
        } else if (direction == 6){
            Pair pos = new Pair(1, -1);
            return pos;
        } else if (direction == 7){
            Pair pos = new Pair(0, -1);
            return pos;
        } else if (direction == 8) {
            Pair pos = new Pair(-1, -1);
            return pos;
        } else {
            Pair pos = new Pair(0,0);
            return  pos;
        }
    }

    public void movePlayer(int direction){
        int currentPosX = 0;
        int currentPosY = 0;
        int neededPosX = 0;
        int neededPosY = 0;

        neededPosX = (int) directionToPairs(direction).getKey();
        neededPosY = (int) directionToPairs(direction).getValue();

        if (isFirstPlayer) {
            currentPosX = (int) getPlayersPosition(1).getKey();
            currentPosY = (int) getPlayersPosition(1).getValue();
        } else {
            currentPosX = (int) getPlayersPosition(2).getKey();
            currentPosY = (int) getPlayersPosition(2).getValue();
        }

        if (currentPosX + neededPosX < 0 || currentPosX + neededPosX > TABLE_SIZE_X - 1){
            throw new IllegalCallerException();
        } else if (currentPosY + neededPosY < 0 || currentPosY + neededPosY > TABLE_SIZE_Y - 1){
            throw new IllegalCallerException();
        } else if (table[currentPosX+neededPosX][currentPosY+neededPosY] == -1){
            throw new IllegalCallerException();
        } else if (isFirstPlayer) {
            table[currentPosX][currentPosY] = 0;
            table[currentPosX + neededPosX][currentPosY + neededPosY] = 1;
            isFirstPlayer = false;
            wasACellDisabled = false;
        } else {
            table[currentPosX][currentPosY] = 0;
            table[currentPosX + neededPosX][currentPosY + neededPosY] = 2;
            isFirstPlayer = true;
            stepCounter++;
            wasACellDisabled = false;
        }

    }

    public boolean isThisEndOfGame(){
        int currentPosX = 0;
        int currentPosY = 0;
        int neededPosX = 0;
        int neededPosY = 0;

        if (isFirstPlayer) {
            currentPosX = (int) getPlayersPosition(1).getKey();
            currentPosY = (int) getPlayersPosition(1).getValue();
        } else {
            currentPosX = (int) getPlayersPosition(2).getKey();
            currentPosY = (int) getPlayersPosition(2).getValue();
        }

        for (int i = 1; i <= 8; ++i){
            neededPosX = (int) directionToPairs(i).getKey();
            neededPosY = (int) directionToPairs(i).getValue();

            if (!(currentPosX + neededPosX < 0 || currentPosX + neededPosX > TABLE_SIZE_X - 1)){
                if (!(currentPosY + neededPosY < 0 || currentPosY + neededPosY > TABLE_SIZE_Y - 1)){
                    if (!(table[currentPosX+neededPosX][currentPosY+neededPosY] == -1)){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void setCellDisabled(int x, int y){
        if (!wasACellDisabled){
            if (table[x][y] == 0)
                table[x][y] = -1;
            wasACellDisabled = true;
        }
    }

    public int getStepCounter(){
        return stepCounter;
    }

    public int getIsFirstPlayer(){
        if (isFirstPlayer)
            return 1;
        else return 2;
    }
}
