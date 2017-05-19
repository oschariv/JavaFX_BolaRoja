import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import javafx.animation.Timeline;

import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.animation.Animation;

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
        Button boton = new Button("Parar");
        
        Circle circulo = new Circle();
        circulo.setCenterX(250.0f);
        circulo.setCenterY(250.0f);
        circulo.setRadius(50.0f);
        circulo.setFill(Color.RED);

        Pane root = new Pane();
        root.getChildren().add(circulo);
        root.getChildren().add(boton);
        Scene escena = new Scene(root,500, 500);
        escenario.setScene(escena);

        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(false);
        final KeyFrame kf = new KeyFrame(Duration.millis(10), event ->{
                    circulo.setTranslateY(circulo.getTranslateY() + 1);
                    circulo.setTranslateX(circulo.getTranslateX() + 1);
                });
        timeline.setCycleCount(1000);
        timeline.getKeyFrames().add(kf);
        
        boton.setOnAction(event -> {
                if(timeline.getStatus().equals(Animation.Status.RUNNING)){
                    timeline.stop();
                }
                else{
                    timeline.play();
                }
            });
        
        timeline.play();

        escenario.show();
    }
}
















