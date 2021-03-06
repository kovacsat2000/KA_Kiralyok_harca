package game.view;

import game.model.Game;
import game.model.GameH;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import org.tinylog.Logger;


public class ArrowH extends StackPane {

    ArrowH(int direction, GameH gameH, Launcher main) {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(0.0, 0.0, 25.0, 0.0, 12.5, 35.0);
        triangle.setFill(Color.rgb(255,200,100));

        switch (direction) {
            case 1:
                triangle.getTransforms().add(new Rotate(180, 15, 15));
                break;
            case 2:
                triangle.getTransforms().add(new Rotate(225, 15, 15));
                break;
            case 3:
                triangle.getTransforms().add(new Rotate(270,  15, 15));
                break;
            case 4:
                triangle.getTransforms().add(new Rotate(315,  15, 15));
                break;
            case 5:
                triangle.getTransforms().add(new Rotate(0,  15, 15));
                break;
            case 6:
                triangle.getTransforms().add(new Rotate(45,  15, 15));
                break;
            case 7:
                triangle.getTransforms().add(new Rotate(90,  15, 15));
                break;
            case 8:
                triangle.getTransforms().add(new Rotate(135,  15, 15));
                break;
        }

        setAlignment(Pos.CENTER);
        getChildren().add(triangle);

        setOnMouseClicked(event -> {
            try {
                gameH.movePlayer(direction);
                main.updateGameH();
            } catch (IllegalCallerException e){
                Logger.warn("Vegyen le egy mezőt!");
            }
            catch (Exception IllegalArgumentException){
                Logger.warn("Ide nem léphet!");
            }
        });

    }

}
