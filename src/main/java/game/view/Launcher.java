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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Launcher extends Application {
    //private static Logger logger = LoggerFactory.getLogger(Launcher.class);

    private Stage window;

    private Game game;

    private Label stepLabel;

    private Rectangle2D primScreenBounds;

    private void showGamePanel() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.styleProperty().setValue("-fx-background-color: #20B2AA");
        Scene scene = new Scene(gridPane,400, 200);
        scene.setCursor(Cursor.CLOSED_HAND);

        Label entryLabel = new Label("A játék belépési pontja");
        Label gameLabel = new Label("Királyok harca\nKészítő: Kovács Attila\nVerzió: 1.0");

        Button startButton = new Button("Start");

        startButton.styleProperty().setValue("-fx-background-color: #B8860B; -fx-background-radius: 6, 5");

        gridPane.add(entryLabel, 0, 0);
        gridPane.add(gameLabel, 0, 2);
        gridPane.add(startButton, 1, 4);

        window.setScene(scene);

        //logger.debug("A felhasználói felület tartalma megváltozott!");
    }

    @Override
    public void start(Stage stage) {
        window = stage;
        primScreenBounds = Screen.getPrimary().getVisualBounds();

        showGamePanel();
        window.setTitle("Tábla kirakó játék");
        window.show();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
        //logger.info("A program elindult! Képernyő méretek: {} x {}", primScreenBounds.getWidth(), primScreenBounds.getHeight());

    }

    public static void main(String[] args) {
        launch(args);
    }
}
