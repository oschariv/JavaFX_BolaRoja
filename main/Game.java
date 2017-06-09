package main;

import java.util.ArrayList;
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
public class Game extends Application {

	private static boolean goLeft;
	@SuppressWarnings("unused")
	private static boolean goRight;

	private static final int NUMERO_LADRILLOS = 50;

	private static final int ANCHO_PANTALLA = 600;
	private static final int ALTO_PANTALLA = 500;

	private int tiempoEnSegundos = 0;

	private ArrayList<Ladrillo> ladrillos;

	private int puntos = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void start(Stage escenario) {
		// Escena, panel y escenario.
		Pane root = new Pane();
		Scene escena = new Scene(root, ANCHO_PANTALLA, ALTO_PANTALLA);
		escenario.setScene(escena);
		escenario.setTitle("ARKANOID");

		// Inicializacion de la linea de tiempo
		final Timeline timeline = new Timeline();

		// Bola.
		Bola bola = new Bola(ANCHO_PANTALLA / 2 - Bola.getRadioBola(), ALTO_PANTALLA - (Bola.getRadioBola() * 4),
				ANCHO_PANTALLA);

		// Barra
		Plataforma barra = new Plataforma(escena.getWidth() / 2, escena.getHeight() - 30, ANCHO_PANTALLA);

		// Label para cronometro
		Label tiempoPasado = new Label("0");
		root.getChildren().add(tiempoPasado);

		// Label para puntuacion
		Label puntuacion = new Label("0");
		root.getChildren().add(puntuacion);

		// Ladrillos
		ladrillos = new ArrayList<>();
		int numeroLadrillosAnidados = 0;
		while (numeroLadrillosAnidados < NUMERO_LADRILLOS) {

			boolean encontradoLadrilloValido = false;
			while (!encontradoLadrilloValido) {
				Ladrillo posibleLadrillo = new Ladrillo(ANCHO_PANTALLA, ALTO_PANTALLA);

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
					posibleLadrillo.setItVisible(true);
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
				barra.movimientoIzquierda();
				goLeft = true;
				goRight = false;
				break;
			case RIGHT:
				// Cambia la velocidad de la barra al pulsar la tecla.
				barra.movimientoDerecha();
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
			bola.movimientoEnX();
			// Desplazamiento en Y
			bola.movimientoEnY();

			// Desplazamiento Barra
			if (goLeft) {
				barra.direccionIzquierda();
			} else {
				barra.direccionDerecha();
			}

			// NO REBOTE BARRA
			barra.mover();

			// REBOTE BOLA BARRA
			bola.reboteConBarra(barra);

			// SALIDA BOLA POR DEBAJO DE LA PANTALLA.
			if (bola.getBoundsInParent().getMinY() > ALTO_PANTALLA) {
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
			tiempoPasado.setLayoutX(15);
			tiempoPasado.setLayoutY(ALTO_PANTALLA - 20);

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
			puntuacion.setLayoutY(ALTO_PANTALLA - 20);

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
