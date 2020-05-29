package game.view;

import game.model.Game;
import game.model.GameH;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.tinylog.Logger;

/**
 * A felhasználói felület játékbeli celláit megvalósító osztály.
 */
public class TileH extends StackPane {
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
    private GameH gameH;

    TileH(int row, int column, GameH gameH, Launcher main) {
        this.row = row;
        this.column = column;
        this.gameH = gameH;

        Rectangle border = new Rectangle(30, 30);
        if (gameH.getTableCell(row, column) == 1){
            border.setFill(Color.WHITE);
        } else if (gameH.getTableCell(row, column) == 2){
            border.setFill(Color.BLACK);
        } else if (gameH.getTableCell(row, column) == 0){
            border.setFill(Color.GREENYELLOW);
        } else {
            border.setFill(Color.RED);
        }
        border.setStroke(Color.BLACK);

        setAlignment(Pos.CENTER);
        getChildren().addAll(border);

        setOnMouseClicked(event -> {
            try {
                gameH.setCellDisabled(row, column);
                main.updateGameH();
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
        if (gameH.getTableCell(row, column) == 1){
            border.setFill(Color.WHITE);
        } else if (gameH.getTableCell(row, column) == 2){
            border.setFill(Color.BLACK);
        } else if (gameH.getTableCell(row, column) == 0){
            border.setFill(Color.GREENYELLOW);
        } else {
            border.setFill(Color.RED);
        }
        getChildren().addAll(border);
    }
}