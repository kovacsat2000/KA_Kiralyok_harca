package game.model;

import javafx.util.Pair;

import java.util.*;

/**
 * Egy játékállapotot reprezentáló osztály.
 */
public class Game {
    /**
     * A tábla kezdeti méretét megadó érték.
     */
    public static final int TABLE_SIZE_X = 6;
    public static final int TABLE_SIZE_Y = 8;

    /**
     * Megmondja, hogy melyik játékos lép következőnek.
     */
    private boolean isFirstPlayer = true;

    /**
     * A játéktábla aktuális felállását tároló tömb.
     */
    private int[][] table = new int[TABLE_SIZE_X][TABLE_SIZE_Y];

    /**
     * Az aktuális játékban a játékosok által megtett lépések száma.
     */
    private int stepCount1 = 0;
    private int stepCount2 = 0;

    /**
     * Paraméter nélküli konstruktor. Üres játékot hoz létre.
     */
    public Game() {
    }

    public int getTableCell(int row, int column){
        return table[row][column];
    }

    /**
     * Egy kezdeti játéktábla inicializálása.
     * A létrejövő tábla {@code TABLE_SIZE_X x TABLE_SIZE_Y} méretű lesz.
     */
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
        table[pos_2][TABLE_SIZE_Y] = 2;

        stepCount1 = 0;
        stepCount2 = 0;
    }

    /**
     * A játékos poziciójának, annak koordinátáinak a lekérdezése.
     * @param player
     * @return
     */
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

    /**
     * A számok szerint tárolt lépésirányokat
     * (1: fel, 2: fel-jobbra, 3: jobbra, 4: le-jobbra, 5: le, 6: le-balra, 7: balra, 8: fel-balra)
     * alakítja Pair objektummá, amelyben tárol két adatot, hogy az X és Y tengelyen merre
     * történik a mozgás (-1 hátrafelé, 0 sehová, 1 előrefelé).
     */
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
        } else {
            Pair pos = new Pair(-1, -1);
            return pos;
        }
    }

    /**
     * Megnézi, hogy az éppen soron következő játékos léphet-e az adott irányba anélkül,
     * hogy kilépne a játéktábláról.
     */
    private boolean canPlayerMoveBecauseOfWalls(int direction){
        int currentPosX;
        int currentPosY;

        if (isFirstPlayer) {
            currentPosX = (int) getPlayersPosition(1).getKey();
            currentPosY = (int) getPlayersPosition(1).getValue();
        } else {
            currentPosX = (int) getPlayersPosition(2).getKey();
            currentPosY = (int) getPlayersPosition(2).getValue();
        }

        currentPosX = currentPosX + (int) directionToPairs(direction).getKey();
        currentPosY = currentPosY + (int) directionToPairs(direction).getValue();

        if (currentPosX < TABLE_SIZE_X + 1 && currentPosY < TABLE_SIZE_Y + 1){
            return true;
        } else{
            return false;
        }

    }

    /**
     * Megnézi, hogy az éppen soron következő játékos léphet-e az adott irányba anélkül,
     * hogy hogy egy már nem létező mezőre lépne.
     */
    private boolean canPlayerMoveBecauseOfLostPlaces(int direction){
        int currentPosX;
        int currentPosY;

        if (isFirstPlayer) {
            currentPosX = (int) getPlayersPosition(1).getKey();
            currentPosY = (int) getPlayersPosition(1).getValue();
        } else {
            currentPosX = (int) getPlayersPosition(2).getKey();
            currentPosY = (int) getPlayersPosition(2).getValue();
        }

        currentPosX = currentPosX + (int) directionToPairs(direction).getKey();
        currentPosY = currentPosY + (int) directionToPairs(direction).getValue();

        if (table[currentPosX][currentPosY] == -1){
            return false;
        } else {
            return true;
        }

    }

    /**
     * Megvizsgálja, hogy vége-e a játéknak, azaz a soron következő játékos tud-e még hová lépni.
     */
    public boolean isThisEndOfGame(){
        for (int i = 1; i < 8; i++){
            if ((canPlayerMoveBecauseOfWalls(i)) && canPlayerMoveBecauseOfLostPlaces(i)){
                return true;
            }
        }

         return false;
    }

    /**
     * Egy adott irányba lépteti a soron következő játékost.
     */
    public void movePlayer(int direction){
        int currentPosX;
        int currentPosY;
        int neededPosX;
        int neededPosY;

        if (isFirstPlayer) {
            currentPosX = (int) getPlayersPosition(1).getKey();
            currentPosY = (int) getPlayersPosition(1).getValue();
            neededPosX = (int) directionToPairs(1).getKey();
            neededPosY = (int) directionToPairs(1).getValue();
        } else {
            currentPosX = (int) getPlayersPosition(2).getKey();
            currentPosY = (int) getPlayersPosition(2).getValue();
            neededPosX = (int) directionToPairs(2).getKey();
            neededPosY = (int) directionToPairs(2).getValue();
        }

        if (canPlayerMoveBecauseOfWalls(direction) && canPlayerMoveBecauseOfLostPlaces(direction)){
            table[currentPosX][currentPosY] = 0;
            if (isFirstPlayer) {
                table[currentPosX + neededPosX][currentPosY + neededPosY] = 1;
                stepCount1++;
                isFirstPlayer = false;
            } else {
                table[currentPosX + neededPosX][currentPosY + neededPosY] = 2;
                stepCount2++;
                isFirstPlayer = true;
            }
            if (isThisEndOfGame()) throw new IllegalArgumentException();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setCellDisabled(int x, int y){
        if (table[x][y] == 0)
            table[x][y] = -1;
    }

    public int getStepCount(){
        if (isFirstPlayer) {
            return stepCount1;
        } else {
            return stepCount2;
        }
    }

}
