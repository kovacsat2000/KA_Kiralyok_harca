package game.view;

import game.model.Game;

import game.model.GameH;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import com.google.gson.*;
import org.tinylog.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Királyok harca játék felhasználói felületét megvalósító osztály.
 */
public class Launcher extends Application {
    /**
     * A felhasználói felület ablakának azonosítója.
     */
    private Stage window;

    /**
     * A lépések számát megjelenítő szöveg azonosítója.
     */
    private Label stepLabel;

    /**
     * A soron következő játékost megjelenítő szöveg azonosítója.
     */
    private Label nextPlayerLabel;

    /**
     * A játék egy példánya.
     */
    private Game game;

    private GameH gameH;

    /**
     * A megjelenített játéktábla cellái.
     */
    private List<Tile> tiles = new ArrayList<>();

    private List<TileH> tilesH = new ArrayList<>();

    /**
     * A JSON műveletek támogató objektum.
     */
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * A felhasználói felület ablakának frissítése.
     *
     * @param scene aktuális tartalom
     */
    private void changeScene(Scene scene) {
        window.setScene(scene);
    }

    /**
     * A kezdeti panel megjelenítése.
     */
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

    /**
     * Kiírja .json fileba a pillanatnyi játékállást.
     */
    private void saveGameState(){
        try (FileWriter writer = new FileWriter(System.getProperty("user.home") + File.separator + "saves.json")) {
            gson.toJson(game.getTable(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A menü panel megjelenítése.
     */
    private void showGameMenu() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.styleProperty().setValue("-fx-background-color: GoldenRod");
        Scene scene = new Scene(gridPane,800, 400);

        Button newGameButton = new Button("1 v. 1");
        newGameButton.styleProperty().setValue("-fx-background-color: green; -fx-background-radius: 6, 5");

        Button newGameHButton = new Button("Játék gép ellen");
        newGameHButton.styleProperty().setValue("-fx-background-color: green; -fx-background-radius: 6, 5");

        Button loadGameButton = new Button("Játék betöltése");
        loadGameButton.styleProperty().setValue("-fx-background-color: green; -fx-background-radius: 6, 5");
        loadGameButton.setOnMouseClicked((event -> {
            try {
                loadGame();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }));

        Button quitGameButton = new Button("Játék bezárása");
        quitGameButton.styleProperty().setValue("-fx-background-color: red; -fx-background-radius: 6, 5");

        GridPane.setHalignment(newGameButton, HPos.CENTER);
        GridPane.setHalignment(loadGameButton, HPos.CENTER);

        gridPane.add(newGameHButton, 0, 1);
        gridPane.add(newGameButton, 0, 2);
        gridPane.add(loadGameButton, 0, 3);
        gridPane.add(quitGameButton, 0, 4);

        changeScene(scene);

        newGameButton.setOnMouseClicked((event -> {
            newGame();
        }));

        newGameHButton.setOnMouseClicked((event -> {
            newGameH();
        }));

        quitGameButton.setOnMouseClicked((event -> {
            window.close();
            Logger.debug("A felhasználó kilépett a játékból.");
        }));

        Logger.debug("A felhasználói felület tartalma megváltozott!");
    }

    /**
     * Az aktuális játék megjelenítése új ablakban.
     */
    private void showGame() {
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
        exitButton.setOnMouseClicked((event -> {
            saveGameState();
            showGameMenu();
        }));

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

    private void showGameH() {
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

        for (int i=0; i < GameH.TABLE_SIZE_X; ++i) {
            for (int j = 0; j < GameH.TABLE_SIZE_Y; ++j) {

                TileH tileH = new TileH(i, j, gameH, this);
                tilesH.add(tileH);
                gridPane2.add(tileH, j+1, i+1);

            }
        }

        GridPane gridPane3 = new GridPane();
        gridPane3.setHgap(5);
        gridPane3.setVgap(5);
        gridPane3.setAlignment(Pos.CENTER);

        ArrowH arrow1 = new ArrowH(1, gameH, this);
        ArrowH arrow2 = new ArrowH(2, gameH, this);
        ArrowH arrow3 = new ArrowH(3, gameH, this);
        ArrowH arrow4 = new ArrowH(4, gameH, this);
        ArrowH arrow5 = new ArrowH(5, gameH, this);
        ArrowH arrow6 = new ArrowH(6, gameH, this);
        ArrowH arrow7 = new ArrowH(7, gameH, this);
        ArrowH arrow8 = new ArrowH(8, gameH, this);

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
        exitButton.setOnMouseClicked((event -> {
            showGameMenu();
        }));

        gridPane.add(gridPane2, 1, 0, 1, 1);
        gridPane.add(gridPane3, 1, 2, 1, 1);
        gridPane.add(exitButton, 0, 3);

        changeScene(scene);

        Logger.debug("A felhasználói felület tartalma megváltozott!");
    }

    /**
     * Betölt egy elmentett játékot.
     */
    private void loadGame() throws FileNotFoundException {
        game = new Game();
        game.loadTable();
        showGame();

        Logger.info("Játék betöltésre került!");
    }

    /**
     * Új játék kezdése.
     */
    private void newGame() {
        game = new Game();
        game.initTable();
        showGame();

        Logger.info("Új játék kezdődött!");
    }

    private void newGameH() {
        gameH = new GameH();
        gameH.initTable();
        showGameH();

        Logger.info("Új játék kezdődött!");
    }

    /**
     * Aktuális játékfelület frissítése.
     */
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
            nextPlayerLabel.setText("Következő játékos: " + "fekete");
        }
        if (game.isThisEndOfGame())
            endGame(game.getStepCounter());

        Logger.debug("Az aktuális játékállás frissült.");
    }

    void updateGameH() {
        if (gameH.isThisEndOfGame()){
            endGameH();
        } else {
            if (gameH.wasACellDisabled){
                gameH.movingEnemy();
            }

            for (TileH i : tilesH) {
                i.updateCells();
            }

            Logger.debug("Az aktuális játékállás frissült.");
        }
    }

    /**
     * A játék végét jelző panel. Akkor hívódik meg, ha valaki veszített.
     *
     * @param stepCount
     */
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

    private void endGameH() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.styleProperty().setValue("-fx-background-color: DarkMagenta");
        Scene scene = new Scene(gridPane, 400, 200);

        Button menuButton = new Button("Főmenü");
        menuButton.styleProperty().setValue("-fx-background-color: #B8860B; -fx-background-radius: 6, 5");
        menuButton.setOnMouseClicked((event -> showGameMenu()));

        gridPane.add(menuButton, 0, 1);

        changeScene(scene);

        Logger.debug("A felhasználói felület tartalma megváltozott!");
    }

    /**
     * A felhasználói felület megjelenítése.
     *
     * @param stage a felhasználói felület ablak azonosítója
     */
    @Override
    public void start(Stage stage) {
        window = stage;

        showEntryPanel();
        window.setTitle("Királyok harca");
        window.show();
    }

    /**
     * Az alkalmazás belépési pontja. A játék indításakor hívódik meg.
     *
     * @param args bemenő paraméterek
     */
    public static void main(String[] args) {
        launch(args);
    }
}
