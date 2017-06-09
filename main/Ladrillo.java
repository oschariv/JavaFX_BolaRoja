package main;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ladrillo extends Rectangle {

	private static final int ANCHO_LADRILLO = 60;
	private static final int ALTO_LADRILLO = 8;
	private static final Color COLOR_FONDO_LADRILLO = Color.GREEN;
	private static final Color COLOR_BORDE_LADRILLO = Color.BLACK;

	public Ladrillo(int anchoEscena, int altoEscena) {
		super();
		setWidth(ANCHO_LADRILLO);
		setHeight(ALTO_LADRILLO);
		setFill(COLOR_FONDO_LADRILLO);
		setStroke(COLOR_BORDE_LADRILLO);
		setVisible(false);
		Random aleatorio = new Random();
		int posXLadrillo = aleatorio.nextInt(anchoEscena - ANCHO_LADRILLO);
		int posYLadrillo = aleatorio.nextInt((altoEscena / 2) + ALTO_LADRILLO);
		setX(posXLadrillo);
		setY(posYLadrillo);
	}

	public static int getAnchoLadrillo() {
		return ANCHO_LADRILLO;
	}

	public static int getAltoLadrillo() {
		return ALTO_LADRILLO;
	}

	public static Color getColorFondoLadrillo() {
		return COLOR_FONDO_LADRILLO;
	}

	public static Color getColorBordeLadrillo() {
		return COLOR_BORDE_LADRILLO;
	}

	public void setItVisible(boolean b) {
		this.setVisible(b);

	}

}
