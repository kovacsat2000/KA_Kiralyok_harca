package game.view;

import game.model.Game;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane {
    private int row;

    private int column;

    private Text text;

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

        //text = new Text();
        //text.setText(String.valueOf(game.getTableCell(row,column)));
        //text.setFont(Font.font(15));

        setAlignment(Pos.CENTER);
        getChildren().addAll(border);

        setOnMouseClicked(event -> {
            game.setCellDisabled(row, column);
            main.updateGame();
        });
    }

    void updateCells() {
        //text.setText(String.valueOf(game.getTableCell(row, column)));
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