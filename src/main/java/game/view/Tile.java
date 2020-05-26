package game.view;

import game.model.Game;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.tinylog.Logger;

public class Tile extends StackPane {
    private int row;

    private int column;

    private Game game;

    Tile(int row, int column, Game game, Launcher main) {
        this.row = row;
        this.column = column;
        this.game = game;

        Rectangle border = new Rectangle(30, 30);
        if (game.getTableCell(row, column) == 1){
            border.setFill(Color.WHITE);
        } else if (game.getTableCell(row, column) == 2){
            border.setFill(Color.DARKBLUE);
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

    void updateCells() {
        Rectangle border = new Rectangle(30, 30);
        if (game.getTableCell(row, column) == 1){
            border.setFill(Color.WHITE);
        } else if (game.getTableCell(row, column) == 2){
            border.setFill(Color.DARKBLUE);
        } else if (game.getTableCell(row, column) == 0){
            border.setFill(Color.GREENYELLOW);
        } else {
            border.setFill(Color.RED);
        }
        getChildren().addAll(border);
    }
}