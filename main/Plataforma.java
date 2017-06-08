package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Plataforma extends Rectangle {

	private static final int ANCHO_PLATAFORMA = 100;
	private static final int ALTO_PLATAFORMA = 10;
	private static final Color COLOR_PLATAFORMA = Color.BLUE;
	private static final int ARCO_PLATAFORMA = 20;
	private static int barraSpeed = 1;
	private int limiteXPantalla;

	public Plataforma(double posicionX, double poscionY, int limiteXPantalla) {
		super();
		setWidth(ANCHO_PLATAFORMA);
		setHeight(ALTO_PLATAFORMA);
		setFill(COLOR_PLATAFORMA);
		setArcWidth(ARCO_PLATAFORMA);
		setArcHeight(ARCO_PLATAFORMA);
		setX(posicionX);
		setY(poscionY);
		this.limiteXPantalla = limiteXPantalla;
	}

	public static int getArcoPlataforma() {
		return ARCO_PLATAFORMA;
	}

	public static int getAnchoPlataforma() {
		return ANCHO_PLATAFORMA;
	}

	public static int getAltoPlataforma() {
		return ALTO_PLATAFORMA;
	}

	public static Color getColorPlataforma() {
		return COLOR_PLATAFORMA;
	}

	public static void setBarraSpeed(int barraSpeed) {
		Plataforma.barraSpeed = barraSpeed;
	}

	public void mover() {
		if (getBoundsInParent().getMinX() == 0 || getBoundsInParent().getMaxX() == limiteXPantalla) {
			setBarraSpeed(0);
		}
	}

	public void movimientoIzquierda() {
		if (getBoundsInParent().getMaxX() == limiteXPantalla) {
			setBarraSpeed(1);
		}
	}

	public void movimientoDerecha() {
		if (getBoundsInParent().getMinX() == 0) {
			setBarraSpeed(1);
		}
	}

	public void direccionDerecha() {
		setTranslateX(getTranslateX() + barraSpeed);
	}

	public void direccionIzquierda() {
		setTranslateX(getTranslateX() - barraSpeed);
	}
}
