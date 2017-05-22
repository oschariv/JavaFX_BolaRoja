import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import javafx.animation.Timeline;

import javafx.animation.KeyFrame;
import javafx.util.Duration;

import java.util.Random;

import javafx.scene.shape.Rectangle;

/**
 * Write a description of class bola2 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class bola2 extends Application {
	private static int ballSpeedX = 1;
	private static int ballSpeedY = 1;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage escenario) {

		Pane root = new Pane();
		Scene escena = new Scene(root, 500, 500);
		escenario.setScene(escena);

		// Bola.
		Circle circulo = new Circle();
		circulo.setRadius(20);
		circulo.setFill(Color.RED);

		// Barra
		Rectangle barra = new Rectangle();
		barra.setX(200);
		barra.setY(450);
		barra.setWidth(100);
		barra.setHeight(10);
		barra.setArcWidth(20);
		barra.setArcHeight(20);
		barra.setFill(Color.BLUE);

		int yRandom = new Random().nextInt(480);
		int xRandom = new Random().nextInt(480);
		
		//Centro de la bola
		circulo.setCenterX(circulo.getRadius() + xRandom);
		circulo.setCenterY(circulo.getRadius() + yRandom);

		root.getChildren().add(circulo);
		root.getChildren().add(barra);

		final Timeline timeline = new Timeline();
		timeline.setAutoReverse(true);
		final KeyFrame kf = new KeyFrame(Duration.millis(1.2), event -> {
			// Desplazamiento de la bola.
			if (circulo.getBoundsInParent().getMinX() <= 0
					|| circulo.getBoundsInParent().getMaxX() >= escena.getWidth()) {
				ballSpeedX *= -1;
			}
			circulo.setTranslateX(circulo.getTranslateX() + ballSpeedX);

			if (circulo.getBoundsInParent().getMinY() <= 0
					|| circulo.getBoundsInParent().getMaxY() >= escena.getHeight()) {
				ballSpeedY *= -1;
			}
			circulo.setTranslateY(circulo.getTranslateY() + ballSpeedY);
			
			barra.setTranslateX(barra.getTranslateX() + 0.5);
		});

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(kf);

		timeline.play();

		escenario.show();
	}
}
