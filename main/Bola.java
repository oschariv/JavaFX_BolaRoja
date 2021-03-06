package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Bola extends Circle {

	private static final int RADIO_BOLA = 10;
	private static final Color COLOR_BOLA = Color.RED;
	private static int ballSpeedX = 1;
	private static int ballSpeedY = -1;
	private int anchoPantalla;

	public Bola(int posicionX, int poscionY, int anchoPantalla) {
		super();
		setRadius(RADIO_BOLA);
		setFill(COLOR_BOLA);
		setCenterX(posicionX);
		setCenterY(poscionY);
		this.anchoPantalla = anchoPantalla;
	}

	public static int getBallSpeedX() {
		return ballSpeedX;
	}

	public static void setBallSpeedX() {
		ballSpeedX *= -1;
	}

	public static int getBallSpeedY() {
		return ballSpeedY;
	}

	public static void setBallSpeedY() {
		ballSpeedY *= -1;
	}

	public static int getRadioBola() {
		return RADIO_BOLA;
	}

	public static Color getColorBola() {
		return COLOR_BOLA;
	}

	public void mover() {
		// movimiento en X
		if (getBoundsInParent().getMinX() <= 0 || getBoundsInParent().getMaxX() >= anchoPantalla) {
			setBallSpeedX();
		}
		setTranslateX(getTranslateX() + getBallSpeedX());
		// movimiento en Y
		if (getBoundsInParent().getMinY() <= 0) {
			setBallSpeedY();
		}
		setTranslateY(getTranslateY() + getBallSpeedY());
	}

	public void reboteConBarra(Plataforma barra) {
		if (getBoundsInParent().intersects(barra.getBoundsInParent())) {
			setBallSpeedY();
		}
	}

	public boolean chocaConLadrillo(Ladrillo ladrillo) {
		boolean colision = false;
		Shape interseccion = Shape.intersect(this, ladrillo);
		if (interseccion.getBoundsInParent().getWidth() != -1) {
			colision = true;
			setBallSpeedY();

		}
		return colision;
	}
}
