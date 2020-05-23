package game.view;

import game.model.Game;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Launcher extends Application {
    private Stage window;

    private Game game;

    private Label stepLabel;

    private List<Tile> tiles = new ArrayList<>();

    private Rectangle2D primScreenBounds;

    private void changeScene(Scene scene) {
        window.setScene(scene);
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
    }

    private void showEntryPanel() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.styleProperty().setValue("-fx-background-color: red");
        Scene scene = new Scene(gridPane,400, 200);

        Label entryLabel = new Label("A játék indítása");
        Label gameLabel = new Label("Királyok harca\nKészítő: Kovács Attila Patrik\nVerzió: 1.0");

        Button startButton = new Button("Start");

        startButton.styleProperty().setValue("-fx-background-color: green; -fx-background-radius: 6, 5");

        gridPane.add(gameLabel, 0, 2);
        gridPane.add(entryLabel, 0, 4);
        gridPane.add(startButton, 1, 4);

        changeScene(scene);

        startButton.setOnMouseClicked((event -> {
            showGameMenu();
        }));

    }

    private void showGameMenu() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.styleProperty().setValue("-fx-background-color: red");
        Scene scene = new Scene(gridPane,800, 400);

        Button newGameButton = new Button("Új játék");
        newGameButton.styleProperty().setValue("-fx-background-color: green; -fx-background-radius: 6, 5");

        Button loadGameButton = new Button("Játék betöltése");
        loadGameButton.styleProperty().setValue("-fx-background-color: green; -fx-background-radius: 6, 5");

        GridPane.setHalignment(newGameButton, HPos.CENTER);
        GridPane.setHalignment(loadGameButton, HPos.CENTER);

        gridPane.add(newGameButton, 0, 1);
        gridPane.add(loadGameButton, 0, 2);

        changeScene(scene);

        newGameButton.setOnMouseClicked((event -> {
            showGame();
        }));

    }

    private void showGame() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.styleProperty().setValue("-fx-background-color: red");
        Scene scene = new Scene(gridPane, 600, 600);

        GridPane gridPane2 = new GridPane();
        gridPane2.setHgap(12);
        gridPane2.setVgap(12);
        gridPane2.setAlignment(Pos.CENTER);

        for (int i=0; i < Game.TABLE_SIZE_X; ++i) {
            for (int j = 0; j < Game.TABLE_SIZE_Y; ++j) {

                Tile tile = new Tile(i, j, game);
                tiles.add(tile);
                gridPane2.add(tile, j+1, i+1);

            }
        }

        GridPane gridPane3 = new GridPane();
        gridPane3.setHgap(5);
        gridPane3.setVgap(5);
        gridPane3.setAlignment(Pos.CENTER);

        Arrow arrow1 = new Arrow(1, game, this);
        Arrow arrow2 = new Arrow(2, game, this);
        Arrow arrow3 = new Arrow(3, game, this);
        Arrow arrow4 = new Arrow(4, game, this);
        Arrow arrow5 = new Arrow(5, game, this);
        Arrow arrow6 = new Arrow(6, game, this);
        Arrow arrow7 = new Arrow(7, game, this);
        Arrow arrow8 = new Arrow(8, game, this);

        gridPane3.add(arrow8, 0, 0, 1, 1);
        gridPane3.add(arrow1, 1, 0, 1, 1);
        gridPane3.add(arrow2, 2, 0, 1, 1);
        gridPane3.add(arrow7, 0, 1, 1, 1);
        gridPane3.add(arrow3, 2, 1, 1, 1);
        gridPane3.add(arrow6, 0, 2, 1, 1);
        gridPane3.add(arrow5, 1, 2, 1, 1);
        gridPane3.add(arrow4, 2, 2, 1, 1);

        Button exitButton = new Button("Kilépés");
        exitButton.styleProperty().setValue("-fx-background-color: blue; -fx-background-radius: 6, 5");
        exitButton.setOnMouseClicked((event -> showGameMenu()));

        gridPane.add(gridPane2, 1, 0, 1, 1);
        gridPane.add(gridPane3, 1, 2, 1, 1);
        gridPane.add(exitButton, 0, 3);

        changeScene(scene);
    }

    @Override
    public void start(Stage stage) {
        window = stage;
        primScreenBounds = Screen.getPrimary().getVisualBounds();

        showEntryPanel();
        window.setTitle("Tábla kirakó játék");
        window.show();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
