import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import javafx.animation.Timeline;

import javafx.animation.KeyFrame;
import javafx.util.Duration;
/**
 * Write a description of class Panel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bola extends Application
{
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage escenario){
        Circle circulo = new Circle();
        circulo.setCenterX(250.0f);
        circulo.setCenterY(250.0f);
        circulo.setRadius(50.0f);
        circulo.setFill(Color.RED);

        Pane root = new Pane();
        root.getChildren().add(circulo);
        Scene escena = new Scene(root,500, 500);
        escenario.setScene(escena);

        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(false);
        final KeyFrame kf = new KeyFrame(Duration.millis(10), event ->{
                    circulo.setTranslateY(circulo.getTranslateY() + 1);
                    circulo.setTranslateX(circulo.getTranslateX() + 1);
                });
        timeline.setCycleCount(500);
        timeline.getKeyFrames().add(kf);

        timeline.play();

        escenario.show();
    }
}
