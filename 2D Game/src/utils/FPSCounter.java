package utils;

import org.lwjgl.Sys;

public class FPSCounter {
	private static long lastFrame;
	private static int fps;
	private static long lastFPS;

	private static int newFPS;

	public static void setup() {
		getDelta();
		lastFPS = getTime();
	}

	public static int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	private static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public static void update() {
		if (getTime() - lastFPS > 1000) {
			newFPS = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public static int getFPS() {
		return newFPS;
	}
}
