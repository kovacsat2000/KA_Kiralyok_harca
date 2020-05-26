package game.view;

import game.model.Game;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.tinylog.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Launcher extends Application {
    private Stage window;

    private Label stepLabel;

    private Label nextPlayerLabel;

    private Game game;

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
        gridPane.styleProperty().setValue("-fx-background-color: DimGray");
        Scene scene = new Scene(gridPane,400, 200);

        Label entryLabel = new Label("A játék indítása: ");
        entryLabel.setFont(new Font(15));
        Label gameLabel = new Label("Királyok harca\nKészítő: Kovács Attila Patrik\nVerzió: 1.0");
        gameLabel.setFont(new Font(12));

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
        gridPane.styleProperty().setValue("-fx-background-color: GoldenRod");
        Scene scene = new Scene(gridPane,800, 400);

        Button newGameButton = new Button("Új játék");
        newGameButton.styleProperty().setValue("-fx-background-color: green; -fx-background-radius: 6, 5");

        Button loadGameButton = new Button("Játék betöltése");
        loadGameButton.styleProperty().setValue("-fx-background-color: green; -fx-background-radius: 6, 5");

        Button quitGameButton = new Button("Játék bezárása");
        quitGameButton.styleProperty().setValue("-fx-background-color: red; -fx-background-radius: 6, 5");

        GridPane.setHalignment(newGameButton, HPos.CENTER);
        GridPane.setHalignment(loadGameButton, HPos.CENTER);

        gridPane.add(newGameButton, 0, 1);
        gridPane.add(loadGameButton, 0, 2);
        gridPane.add(quitGameButton, 0, 3);

        changeScene(scene);

        newGameButton.setOnMouseClicked((event -> {
            newGame();
        }));

        quitGameButton.setOnMouseClicked((event -> {
            window.close();
            Logger.debug("A felhasználó kilépett a játékból.");
        }));

        Logger.debug("A felhasználói felület tartalma megváltozott!");
    }

    private void showGame() {
        game = new Game();
        game.initTable();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.styleProperty().setValue("-fx-background-color: DarkSeaGreen");
        Scene scene = new Scene(gridPane, 600, 600);

        GridPane gridPane2 = new GridPane();
        gridPane2.setHgap(12);
        gridPane2.setVgap(12);
        gridPane2.setAlignment(Pos.CENTER);

        for (int i=0; i < Game.TABLE_SIZE_X; ++i) {
            for (int j = 0; j < Game.TABLE_SIZE_Y; ++j) {

                Tile tile = new Tile(i, j, game, this);
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

        gridPane3.add(arrow1, 1, 0, 1, 1);
        gridPane3.add(arrow2, 2, 0, 1, 1);
        gridPane3.add(arrow3, 2, 1, 1, 1);
        gridPane3.add(arrow4, 2, 2, 1, 1);
        gridPane3.add(arrow5, 1, 2, 1, 1);
        gridPane3.add(arrow6, 0, 2, 1, 1);
        gridPane3.add(arrow7, 0, 1, 1, 1);
        gridPane3.add(arrow8, 0, 0, 1, 1);
        gridPane3.setMargin(arrow4, new Insets(15));
        gridPane3.setMargin(arrow8, new Insets(15));

        Button exitButton = new Button("Kilépés");
        exitButton.styleProperty().setValue("-fx-background-color: red; -fx-background-radius: 6, 5");
        exitButton.setOnMouseClicked((event -> showGameMenu()));

        stepLabel = new Label("Aktuális körszám: " + game.getStepCounter());
        stepLabel.setFont(new Font(20));
        GridPane.setHalignment(stepLabel, HPos.CENTER);
        nextPlayerLabel = new Label("Következő játékos: " + "fehér");
        nextPlayerLabel.setFont(new Font(20));
        GridPane.setHalignment(nextPlayerLabel, HPos.CENTER);

        gridPane.add(gridPane2, 1, 0, 1, 1);
        gridPane.add(gridPane3, 1, 2, 1, 1);
        gridPane.add(exitButton, 0, 3);
        gridPane.add(stepLabel, 0,2);
        gridPane.add(nextPlayerLabel, 0,1);

        changeScene(scene);

        Logger.debug("A felhasználói felület tartalma megváltozott!");
    }

    private void newGame() {
        game = new Game();
        while (game.isThisEndOfGame())
            game.initTable();
        showGame();

        Logger.info("Új játék kezdődött!");
    }

    void updateGame() {
        for (Tile i : tiles) {
            i.updateCells();
        }
        if (game.getStepCounter()%2 == 0){
            stepLabel.setText("Aktuális körszám: " + game.getStepCounter()/2);
        }
        if (game.getIsFirstPlayer() == 0){
            nextPlayerLabel.setText("Válassz egy mezőt!");
        } else if (game.getIsFirstPlayer() == 1) {
            nextPlayerLabel.setText("Következő játékos: " + "fehér");
        } else {
            nextPlayerLabel.setText("Következő játékos: " + "kék");
        }
        if (game.isThisEndOfGame())
            endGame(game.getStepCounter());

        Logger.debug("Az aktuális játékállás frissült.");
    }

    private void endGame(int stepCount) {
        Logger.info("A játék sikeresen véget ért! Körök száma: {}.", stepCount);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.styleProperty().setValue("-fx-background-color: DarkMagenta");
        Scene scene = new Scene(gridPane, 400, 200);

        Button menuButton = new Button("Főmenü");
        menuButton.styleProperty().setValue("-fx-background-color: #B8860B; -fx-background-radius: 6, 5");
        menuButton.setOnMouseClicked((event -> showGameMenu()));

        Label winLabel = new Label();
        if (game.getIsFirstPlayer() == 1){
            winLabel.setText("A játék véget ért! A győztes a 2. játékos");
        } else {
            winLabel.setText("A játék véget ért! A győztes az 1. játékos");
        }
        GridPane.setHalignment(winLabel, HPos.CENTER);
        winLabel.setFont(new Font(15));

        Label stepLabel = new Label();
        stepLabel.setText("A játék során megtett körök száma: " + stepCount);

        gridPane.add(winLabel, 0, 0, 2, 1);
        gridPane.add(menuButton, 0, 1);

        changeScene(scene);

        Logger.debug("A felhasználói felület tartalma megváltozott!");
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
