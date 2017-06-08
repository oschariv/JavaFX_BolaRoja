package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Plataforma extends Rectangle {

	private static final int ANCHO_PLATAFORMA = 100;
	private static final int ALTO_PLATAFORMA = 10;
	private static final Color COLOR_PLATAFORMA = Color.BLUE;
	private static final int ARCO_PLATAFORMA = 20;

	private static int posicionX;
	private static int posicionY;

	private static int barraSpeed = 1;

	private Rectangle plataforma;

	public Plataforma() {
		super();
		setWidth(ANCHO_PLATAFORMA);
		setHeight(ALTO_PLATAFORMA);
		setFill(COLOR_PLATAFORMA);
		setArcWidth(ARCO_PLATAFORMA);
		setArcHeight(ARCO_PLATAFORMA);
	}

	public Rectangle getPlataforma() {
		return plataforma;
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

	public static int getPosicionX() {
		return posicionX;
	}

	public static void setPosicionX(int posicionX) {
		Plataforma.posicionX = posicionX;
	}

	public static int getPosicionY() {
		return posicionY;
	}

	public static void setPosicionY(int posicionY) {
		Plataforma.posicionY = posicionY;
	}

	public void setPlataforma(Plataforma plataforma) {
		this.plataforma = plataforma;
	}

	public static int getBarraSpeed() {
		return barraSpeed;
	}

	public static void setBarraSpeed(int barraSpeed) {
		Plataforma.barraSpeed = barraSpeed;
	}
}
