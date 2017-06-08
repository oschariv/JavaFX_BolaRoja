package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Desarrollo del Arkanoid Clasico mediante Java FX.
 * 
 * @author Oscar Charro Rivera
 * @version 1.0
 */
public class main extends Application {

	private static boolean goLeft;
	private static boolean goRight;

	private static final int NUMERO_LADRILLOS = 50;

	private static final int ANCHO_PANTALLA = 600;
	private static final int ALTO_PANTALLA = 500;
	private static final int ALTO_BARRA_SUPERIOR = 40;

	private int tiempoEnSegundos = 0;

	private ArrayList<Ladrillo> ladrillos;

	private int puntos = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage escenario) {
		// Escena, panel y escenario.
		Pane root = new Pane();
		Scene escena = new Scene(root, ANCHO_PANTALLA, ALTO_PANTALLA);
		escenario.setScene(escena);
		escenario.setTitle("ARKANOID");

		Random aleatorio = new Random();

		// Inicializacion de la linea de tiempo
		final Timeline timeline = new Timeline();

		// Bola.
		Bola bola = new Bola();
		bola.setCenterX(ANCHO_PANTALLA / 2 - bola.getRadioBola());
		bola.setCenterY(ALTO_PANTALLA - (bola.getRadioBola() * 4));

		// Barra
		Plataforma barra = new Plataforma();
		barra.setX(escena.getWidth() / 2);
		barra.setY(escena.getHeight() - 25);

		// Label para cronometro
		Label tiempoPasado = new Label("0");
		root.getChildren().add(tiempoPasado);

		// Label para puntuacion
		Label puntuacion = new Label("0");
		root.getChildren().add(puntuacion);

		// Centro de la bola

		// Ladrillos
		ladrillos = new ArrayList<>();
		int numeroLadrillosAnidados = 0;
		while (numeroLadrillosAnidados < NUMERO_LADRILLOS) {

			boolean encontradoLadrilloValido = false;
			while (!encontradoLadrilloValido) {

				// Poscion ladrillo aleatoria
				int posXLadrillo = aleatorio.nextInt(ANCHO_PANTALLA - Ladrillo.getAnchoLadrillo());
				int posYLadrillo = aleatorio.nextInt((ALTO_PANTALLA / 2) + Ladrillo.getAltoLadrillo());

				Ladrillo posibleLadrillo = new Ladrillo();
				posibleLadrillo.setVisible(false);
				posibleLadrillo.setX(posXLadrillo);
				posibleLadrillo.setY(posYLadrillo);

				int ladrilloActual = 0;
				boolean solapamientoDetectado = false;
				while (ladrilloActual < ladrillos.size() && !solapamientoDetectado) {
					Shape interseccion = Shape.intersect(posibleLadrillo, ladrillos.get(ladrilloActual));
					if (interseccion.getBoundsInParent().getWidth() != -1) {
						solapamientoDetectado = true;
					}
					ladrilloActual++;
				}

				// Si hemos encontrado un ladrillo
				if (!solapamientoDetectado) {
					encontradoLadrilloValido = true;
					posibleLadrillo.setVisible(true);
					ladrillos.add(posibleLadrillo);
					root.getChildren().add(posibleLadrillo);
				}
			}
			numeroLadrillosAnidados++;
		}

		// Adhesiones al panel
		root.getChildren().add(bola);
		root.getChildren().add(barra);

		// TECLADO:
		escena.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case LEFT:
				// Cambia la velocidad de la barra al pulsar la tecla.
				if (barra.getBoundsInParent().getMaxX() == escena.getWidth()) {
					barra.setBarraSpeed(1);
				}
				goLeft = true;
				goRight = false;
				break;
			case RIGHT:
				// Cambia la velocidad de la barra al pulsar la tecla.
				if (barra.getBoundsInParent().getMinX() == 0) {
					barra.setBarraSpeed(1);
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
			if (bola.getBoundsInParent().getMinX() <= 0 || bola.getBoundsInParent().getMaxX() >= escena.getWidth()) {
				bola.setBallSpeedX();
			}
			bola.setTranslateX(bola.getTranslateX() + bola.getBallSpeedX());

			// Desplazamiento en Y
			if (bola.getBoundsInParent().getMinY() <= 0) {
				bola.setBallSpeedY();
			}
			bola.setTranslateY(bola.getTranslateY() + bola.getBallSpeedY());

			// Desplazamiento Barra
			if (goLeft) {
				barra.setTranslateX(barra.getTranslateX() - barra.getBarraSpeed());
			} else {
				barra.setTranslateX(barra.getTranslateX() + barra.getBarraSpeed());
			}

			// REBOTE BARRA
			if (barra.getBoundsInParent().getMinX() <= 0 || barra.getBoundsInParent().getMaxX() >= escena.getWidth()) {
				barra.setBarraSpeed(0);
			}

			if (bola.getBoundsInParent().intersects(barra.getBoundsInParent())) {
				bola.setBallSpeedY();
			}

			if (bola.getBoundsInParent().getMinY() > escena.getHeight()) {
				Label GOMessage = new Label("GAME OVER!");
				GOMessage.setFont(Font.font("Courier New", FontWeight.BOLD, 62));
				root.getChildren().add(GOMessage);
				GOMessage.layoutXProperty().bind(root.widthProperty().subtract(GOMessage.widthProperty()).divide(2));
				GOMessage.layoutYProperty().bind(root.heightProperty().subtract(GOMessage.heightProperty()).divide(2));
				escenario.setTitle("ARKANOID (GAME OVER)");
				timeline.stop();
			}
			// Actualizamos la etiqueta del tiempo
			int minutos = tiempoEnSegundos / 60;
			int segundos = tiempoEnSegundos % 60;

			tiempoPasado.setText(String.valueOf(minutos) + " : " + String.valueOf(segundos));

			// Desctruccion ladrillos
			for (int i = 0; i < ladrillos.size(); i++) {
				Ladrillo ladrilloAComprobar = ladrillos.get(i);
				Shape interseccion = Shape.intersect(bola, ladrilloAComprobar);
				if (interseccion.getBoundsInParent().getWidth() != -1) {
					root.getChildren().remove(ladrilloAComprobar);
					ladrillos.remove(ladrilloAComprobar);
					bola.setBallSpeedY();
					puntos++;
					i--;
				}
			}
			puntuacion.setText(String.valueOf(puntos));
			puntuacion.setLayoutX(ANCHO_PANTALLA - 20);

		});

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(kf);

		timeline.play();

		escenario.show();

		TimerTask cronometro = new TimerTask() {
			@Override
			public void run() {
				tiempoEnSegundos++;
			}
		};
		Timer timer = new Timer();
		timer.schedule(cronometro, 0, 1000);
	}
}
