package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bola extends Circle {

	private static final int RADIO_BOLA = 10;
	private static final Color COLOR_BOLA = Color.RED;
	private static int ballSpeedX = 1;
	private static int ballSpeedY = -1;

	private Circle bola;

	public Bola() {
		super();
		setRadius(RADIO_BOLA);
		;
		setFill(COLOR_BOLA);
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

}
