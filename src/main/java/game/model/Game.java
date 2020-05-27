package game.model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.util.Pair;
import org.tinylog.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Egy játékállapotot reprezentáló osztály.
 */
public class Game {
    /**
     * A tábla hosszúsága.
     */
    public static final int TABLE_SIZE_X = 6;

    /**
     * A tábla szélessége.
     */
    public static final int TABLE_SIZE_Y = 8;

    /**
     * Megmondja, hogy éppen melyik játékos lép következőként.
     */
    public boolean isFirstPlayer = true;

    /**
     * Megmondja, hogy éppen aktuálisan vettünk-e már le mezőt a lépésünk után.
     */
    public boolean wasACellDisabled = true;

    /**
     * A játéktábla aktuális felállását tároló tömb.
     */
    private int[][] table = new int[TABLE_SIZE_X][TABLE_SIZE_Y];

    /**
     * A köröket számolja.
     */
    public int stepCounter = 0;

    /**
     * Paraméter nélküli konstruktor. Üres játékot hoz létre.
     */
    public Game() {
    }

    /**
     * Játék létrehozása előre megadott játéktábla felállással.
     *
     * @param table a kezdeti játéktábla
     */
    public Game(int[][] table) {
        this.table = table;
    }

    /**
     * Egy kezdeti játéktábla inicializálása.
     */
    public void initTable() {
        for (int i = 0; i < TABLE_SIZE_X; ++i) {
            for (int j = 0; j < TABLE_SIZE_Y; ++j) {
                table[i][j] = 0;
            }
        }

        table[2][0] = 1;
        table[3][TABLE_SIZE_Y - 1] = 2;
        Logger.info("Új játéktábla létrehozva.");
    }

    /**
     * A json fileból tölti be a játéktáblát.
     * @throws FileNotFoundException ha nem talál fájlt
     */
    public void loadTable() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(System.getProperty("user.home") + File.separator + "saves.json"));
        Integer[][] data = gson.fromJson(reader, Integer[][].class);

        for (int i = 0; i < TABLE_SIZE_X; ++i) {
            for (int j = 0; j < TABLE_SIZE_Y; ++j) {
                table[i][j] = data[i][j];
            }
        }
    }

    /**
     * Az aktuális játéktábla adott celláján lévő szám lekérdezése.
     *
     * @param row a tábla egy sor indexe
     * @param column a tábla egy oszlop indexe
     * @return az érték, ami az adott cellán van
     */
    public int getTableCell(int row, int column){
        return table[row][column];
    }

    /**
     * A jelenlegi játéktábla lekérdezése.
     * @return játéktábla
     */
    public int[][] getTable(){
        return table;
    };

    /**
     * Bemenetként kap egy játékost, és visszaad egy párt, ami a játékos helye a táblán.
     *
     * @param player kapot játékos, 1 vagy 2
     * @return Pair visszaadott pár, x és y koordináta
     */
    public Pair getPlayersPosition(int player){
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
     * Egy irányt kap bemenetként, ami 1-8 közötti lehet. Ebből visszaad egy párt, ami egy x és y, -1, 0 vagy 1,
     * ami jelzi, hogy az adott tengelyen mennyit kell halandni.
     *
     * @param direction a 8 irány közül egy, számmal jelölve
     * @return Pair visszaadott pár, x és y koordináta
     */
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

    /**
     * Egy irányt kap bemenetként, ami 1-8 közötti lehet. Eldönti, hogy a játékos, aki éppen lép,
     * léphet-e a kívánt irányba, és ha igen, akkor lépteti a játékost.
     *
     * @param direction a 8 irány közül egy, számmal jelölve
     */
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
            throw new IllegalArgumentException();
        } else if (currentPosY + neededPosY < 0 || currentPosY + neededPosY > TABLE_SIZE_Y - 1){
            throw new IllegalArgumentException();
        } else if (table[currentPosX+neededPosX][currentPosY+neededPosY] == 1 || table[currentPosX+neededPosX][currentPosY+neededPosY] == 2){
            throw new IllegalArgumentException();
        } else if (table[currentPosX+neededPosX][currentPosY+neededPosY] == -1){
            throw new IllegalArgumentException();
        } else if (wasACellDisabled){
            if (isFirstPlayer){
                table[currentPosX][currentPosY] = 0;
                table[currentPosX + neededPosX][currentPosY + neededPosY] = 1;
                isFirstPlayer = false;
                wasACellDisabled = false;
                Logger.info("Az első játékos lépett a(z) {} irányba.", direction);
            } else {
                table[currentPosX][currentPosY] = 0;
                table[currentPosX + neededPosX][currentPosY + neededPosY] = 2;
                isFirstPlayer = true;
                wasACellDisabled = false;
                Logger.info("A második játékos lépett a(z) {} irányba.", direction);
            }
        } else throw new IllegalCallerException();
    }

    /**
     * Megnézi, hogy az adott játékállapot végállás-e.
     *
     * @return igaz, ha az adott játékállapot végállás, egyébként hamis.
     */
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
                        Logger.debug("Az aktuális játékállás még nem végállapot.");
                        return false;
                    }
                }
            }
        }

        Logger.debug("Az aktuális játékállás végállapot.");
        return true;
    }

    /**
     * Egy cella eltüntetéséhez használandó függvény.
     *
     * @param x a cella sorindexe
     * @param y a cella oszlopindexe
     */
    public void setCellDisabled(int x, int y){
        if (!wasACellDisabled){
            if (table[x][y] == 0){
                table[x][y] = -1;
                wasACellDisabled = true;
                stepCounter++;
                Logger.info("A pálya {} sorának {} oszlopa eltüntetve.", x + 1, y + 1);
            } else if (table[x][y] == -1){
                throw new IllegalCallerException();
            }
        } else throw new IllegalArgumentException();
    }

    /**
     * Visszaadja a köröket számláló változót.
     *
     * @return egy szám, a körök száma
     */
    public int getStepCounter(){
        return stepCounter;
    }

    /**
     * Megmondja, hogy mi a következő elvárt lépés a játékban.
     *
     * @return 1-et ad vissza, ha az első játékos következik, 2-őt, ha a második,
     * nullát, ha a következő lépés egy mező eltávolítása
     */
    public int getIsFirstPlayer(){
        if (wasACellDisabled) {
            if (isFirstPlayer)
                return 1;
            else return 2;
        }

        return 0;
    }
}
