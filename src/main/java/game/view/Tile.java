package game.view;

import game.model.Game;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.tinylog.Logger;

/**
 * A felhasználói felület játékbeli celláit megvalósító osztály.
 */
public class Tile extends StackPane {
    /**
     * A reprezentált cella sor indexe.
     */
    private int row;

    /**
     * A reprezentált cella oszlop indexe.
     */
    private int column;

    /**
     * A játék egy példánya.
     */
    private Game game;

    /**
     * Cella létrehozására szolgáló konstruktor.
     *
     * @param row a reprezentált játékcella sora
     * @param column a reprezentált játékcella oszlopa
     * @param game a játék példánya
     * @param main a felhasználói felület példánya
     */
    Tile(int row, int column, Game game, Launcher main) {
        this.row = row;
        this.column = column;
        this.game = game;

        Rectangle border = new Rectangle(30, 30);
        if (game.getTableCell(row, column) == 1){
            border.setFill(Color.WHITE);
        } else if (game.getTableCell(row, column) == 2){
            border.setFill(Color.BLACK);
        } else if (game.getTableCell(row, column) == 0){
            border.setFill(Color.GREENYELLOW);
        } else {
            border.setFill(Color.RED);
        }
        border.setStroke(Color.BLACK);

        setAlignment(Pos.CENTER);
        getChildren().addAll(border);

        setOnMouseClicked(event -> {
            try {
                game.setCellDisabled(row, column);
                main.updateGame();
            }
            catch (IllegalCallerException e){
                Logger.warn("Ez a mező már piros!");
            }
            catch (Exception IllegalArgumentException){
                Logger.warn("Előbb lépnie kell!");
            }
        });
    }

    /**
     * Frissíti a játékcella tartalmát.
     */
    void updateCells() {
        Rectangle border = new Rectangle(30, 30);
        if (game.getTableCell(row, column) == 1){
            border.setFill(Color.WHITE);
        } else if (game.getTableCell(row, column) == 2){
            border.setFill(Color.BLACK);
        } else if (game.getTableCell(row, column) == 0){
            border.setFill(Color.GREENYELLOW);
        } else {
            border.setFill(Color.RED);
        }
        getChildren().addAll(border);
    }
}