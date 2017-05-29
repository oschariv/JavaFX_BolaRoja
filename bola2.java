import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import javafx.animation.Timeline;
import javafx.animation.Animation;

import javafx.animation.KeyFrame;
import javafx.util.Duration;

import java.util.Random;

import javafx.scene.shape.Rectangle;

import javafx.scene.input.KeyEvent;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Write a description of class bola2 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class bola2 extends Application {
	private static int ballSpeedX = 1;
	private static int ballSpeedY = 1;

	private static int barraSpeed = 1;

	private static boolean goLeft;
	private static boolean goRight;
	
	private int tiempoEnSegundos = 0;
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage escenario) {
		// Escena, panel y escenario.
		Pane root = new Pane();
		Scene escena = new Scene(root, 500, 500);
		escenario.setScene(escena);
		escenario.setTitle("ARKANOID");
		
		//Inicializacion de la linea de tiempo
		final Timeline timeline = new Timeline();

		// Bola.
		Circle circulo = new Circle();
		circulo.setRadius(10);
		circulo.setFill(Color.RED);

		// Barra
		Rectangle barra = new Rectangle();
		barra.setX(escena.getWidth() / 2);
		barra.setY(escena.getHeight() - 25);
		barra.setWidth(100);
		barra.setHeight(10);
		barra.setArcWidth(20);
		barra.setArcHeight(20);
		barra.setFill(Color.BLUE);
		
		
		
		// Label para cronometro
		Label tiempoPasado = new Label("0");
		root.getChildren().add(tiempoPasado);
		
		// Posicion x e y random
		int yRandom = new Random().nextInt(480);
		int xRandom = new Random().nextInt(480);

		// Centro de la bola
		circulo.setCenterX(circulo.getRadius() + xRandom);
		circulo.setCenterY(circulo.getRadius() + yRandom);

		// Adhesiones al panel
		root.getChildren().add(circulo);
		root.getChildren().add(barra);

		// TECLADO:
		escena.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case LEFT:
				// Cambia la velocidad de la barra al pulsar la tecla.
				if (barra.getBoundsInParent().getMaxX() == escena.getWidth()){
					barraSpeed = 1;
				}
				goLeft = true;
				goRight = false;
				break;
			case RIGHT:
				// Cambia la velocidad de la barra al pulsar la tecla.
				if (barra.getBoundsInParent().getMinX() == 0){
					barraSpeed = 1;
				}
				goLeft = false;
				goRight = true;
				break;
			case P:
				if (timeline.getStatus().equals(Animation.Status.RUNNING)) {
					timeline.stop();
					escenario.setTitle("ARKANOID (PAUSE)");
				} else {
					timeline.play();
					escenario.setTitle("ARKANOID");
				}
			}
		});

		timeline.setAutoReverse(true);
		final KeyFrame kf = new KeyFrame(Duration.millis(1.7), event -> {
			// Desplazamiento de la bola.
				// Desplazamiento en X
			if (circulo.getBoundsInParent().getMinX() <= 0
					|| circulo.getBoundsInParent().getMaxX() >= escena.getWidth()) {
				ballSpeedX *= -1;
			}
			circulo.setTranslateX(circulo.getTranslateX() + ballSpeedX);
			
				// Desplazamiento en Y
			if (circulo.getBoundsInParent().getMinY() <= 0) {
				ballSpeedY *= -1;
			}
			circulo.setTranslateY(circulo.getTranslateY() + ballSpeedY);

			// Desplazamiento Barra
			if (goLeft) {
				barra.setTranslateX(barra.getTranslateX() - barraSpeed);
			} else {
				barra.setTranslateX(barra.getTranslateX() + barraSpeed);
			}

			// REBOTE BARRA
			if (barra.getBoundsInParent().getMinX() <= 0 || barra.getBoundsInParent().getMaxX() >= escena.getWidth()) {
				barraSpeed = 0;
			}

			if (circulo.getBoundsInParent().intersects(barra.getBoundsInParent())) {
				ballSpeedY *= -1;
			}

			if (circulo.getBoundsInParent().getMinY() > escena.getHeight()) {
				Label GOMessage = new Label("GAME OVER!");
				GOMessage.setFont(Font.font("Courier New", FontWeight.BOLD, 62));
				root.getChildren().add(GOMessage);
				GOMessage.layoutXProperty().bind(root.widthProperty().subtract(GOMessage.widthProperty()).divide(2));
				GOMessage.layoutYProperty().bind(root.heightProperty().subtract(GOMessage.heightProperty()).divide(2));
				escenario.setTitle("ARKANOID (GAME OVER)");
				timeline.stop();
			}
			// Actualizamos la etiqueta del tiempo
			int minutos = tiempoEnSegundos/60;
			int segundos = tiempoEnSegundos%60;
			tiempoPasado.setText(String.valueOf(minutos) + " : " + String.valueOf(segundos));

		});

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(kf);

		timeline.play();

		escenario.show();
		
		
		TimerTask cronometro = new TimerTask(){
			@Override
			public void run(){
				tiempoEnSegundos++;
			}
		};
		Timer timer = new Timer();
		timer.schedule(cronometro, 0, 1000);
	}
}
