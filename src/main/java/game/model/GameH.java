package game.model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.util.Pair;
import org.tinylog.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class GameH {
    public static final int TABLE_SIZE_X = 6;

    public static final int TABLE_SIZE_Y = 8;

    private int[][] table = new int[TABLE_SIZE_X][TABLE_SIZE_Y];

    public boolean wasACellDisabled = true;

    public int stepCounter = 0;

    public GameH() {
    }

    public void initTable() {
        for (int i = 0; i < TABLE_SIZE_X; ++i) {
            for (int j = 0; j < TABLE_SIZE_Y; ++j) {
                table[i][j] = 0;
            }
        }

        table[2][0] = 1;
        table[3][TABLE_SIZE_Y - 1] = 2;
    }

    public int getTableCell(int row, int column){
        return table[row][column];
    }

    public int[][] getTable(){
        return table;
    };

    public Pair getPlayersPosition(){
        int posX = 0;
        int posY = 0;

        for (int i = 0; i < TABLE_SIZE_X; ++i) {
            for (int j = 0; j < TABLE_SIZE_Y; ++j) {
                if (table[i][j] == 1) {
                    posX = i;
                    posY = j;
                }
            }
        }

        Pair pos = new Pair(posX, posY);

        return pos;
    }

    public Pair getEnemysPosition(){
        int posX = 0;
        int posY = 0;

        for (int i = 0; i < TABLE_SIZE_X; ++i) {
            for (int j = 0; j < TABLE_SIZE_Y; ++j) {
                if (table[i][j] == 2) {
                    posX = i;
                    posY = j;
                }
            }
        }

        Pair pos = new Pair(posX, posY);

        return pos;
    }

    public Pair directionToPairs(int direction){
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

        currentPosX = (int) getPlayersPosition().getKey();
        currentPosY = (int) getPlayersPosition().getValue();

        if (currentPosX + neededPosX < 0 || currentPosX + neededPosX > TABLE_SIZE_X - 1){
            throw new IllegalArgumentException();
        } else if (currentPosY + neededPosY < 0 || currentPosY + neededPosY > TABLE_SIZE_Y - 1){
            throw new IllegalArgumentException();
        } else if (table[currentPosX+neededPosX][currentPosY+neededPosY] == 1 || table[currentPosX+neededPosX][currentPosY+neededPosY] == 2){
            throw new IllegalArgumentException();
        } else if (table[currentPosX+neededPosX][currentPosY+neededPosY] == -1){
            throw new IllegalArgumentException();
        } else if (wasACellDisabled){
            table[currentPosX][currentPosY] = 0;
            table[currentPosX + neededPosX][currentPosY + neededPosY] = 1;
            wasACellDisabled = false;
        }
        else throw new IllegalCallerException();
    }

    public boolean isThisEndOfGame(){
        int currentPosX = 0;
        int currentPosY = 0;
        int neededPosX = 0;
        int neededPosY = 0;

        currentPosX = (int) getPlayersPosition().getKey();
        currentPosY = (int) getPlayersPosition().getValue();

        for (int i = 1; i <= 8; ++i){
            neededPosX = (int) directionToPairs(i).getKey();
            neededPosY = (int) directionToPairs(i).getValue();

            if (!(currentPosX + neededPosX < 0 || currentPosX + neededPosX > TABLE_SIZE_X - 1)){
                if (!(currentPosY + neededPosY < 0 || currentPosY + neededPosY > TABLE_SIZE_Y - 1)){
                    if (!(table[currentPosX+neededPosX][currentPosY+neededPosY] == -1)){
                        currentPosX = (int) getEnemysPosition().getKey();
                        currentPosY = (int) getEnemysPosition().getValue();

                        for (int j = 1; j <= 8; ++j){
                            neededPosX = (int) directionToPairs(j).getKey();
                            neededPosY = (int) directionToPairs(j).getValue();

                            if (!(currentPosX + neededPosX < 0 || currentPosX + neededPosX > TABLE_SIZE_X - 1)){
                                if (!(currentPosY + neededPosY < 0 || currentPosY + neededPosY > TABLE_SIZE_Y - 1)){
                                    if (!(table[currentPosX+neededPosX][currentPosY+neededPosY] == -1)){
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public void setCellDisabled(int x, int y){
        if (!wasACellDisabled){
            if (table[x][y] == 0){
                table[x][y] = -1;
                wasACellDisabled = true;
                stepCounter++;
            } else if (table[x][y] == -1){
                throw new IllegalCallerException();
            }
        } else throw new IllegalArgumentException();
    }

    public int getRandomDirection(){
        int direction;

        Random rand = new Random();

        return direction = rand.nextInt(8) + 1;
    }

    public boolean isDirectionGood(int direction){
        int currentPosX = (int) getEnemysPosition().getKey();
        int currentPosY = (int) getEnemysPosition().getValue();

        int neededPosX = (int) directionToPairs(direction).getKey();
        int neededPosY = (int) directionToPairs(direction).getValue();

        if (currentPosX + neededPosX < 0 || currentPosX + neededPosX > TABLE_SIZE_X - 1){
            return false;
        } else if (currentPosY + neededPosY < 0 || currentPosY + neededPosY > TABLE_SIZE_Y - 1){
            return false;
        } else if (table[currentPosX+neededPosX][currentPosY+neededPosY] == 1 || table[currentPosX+neededPosX][currentPosY+neededPosY] == 2){
            return false;
        } else if (table[currentPosX+neededPosX][currentPosY+neededPosY] == -1){
            return false;
        } else return true;
    }

    public void movingEnemy(){
        if (!isThisEndOfGame()){
            int direction = getRandomDirection();

            while (!isDirectionGood(direction)){
                direction = getRandomDirection();
            }

            int currentPosX = (int) getEnemysPosition().getKey();
            int currentPosY = (int) getEnemysPosition().getValue();

            int neededPosX = (int) directionToPairs(direction).getKey();
            int neededPosY = (int) directionToPairs(direction).getValue();

            table[currentPosX][currentPosY] = 0;
            table[currentPosX + neededPosX][currentPosY + neededPosY] = 2;

            enemyCellDisable();
        }
    }

    public int getRandomCellX(){
        int pos;

        Random rand = new Random();

        return pos = rand.nextInt(TABLE_SIZE_X);
    }

    public int getRandomCellY(){
        int pos;

        Random rand = new Random();

        return pos = rand.nextInt(TABLE_SIZE_Y);
    }

    public boolean isPosGood(int x, int y){
        if (table[x][y] == 0){
            return true;
        } else return false;
    }

    public void enemyCellDisable(){
        int posX = getRandomCellX();
        int posY = getRandomCellY();

        while (!isPosGood(posX, posY)){
            posX = getRandomCellX();
            posY = getRandomCellY();
        }

        table[posX][posY] = -1;
    }
}
