package game;
import java.io.IOException;

import utils.Loader;

public class Application {
	public static void main(String[] args) {

		try {

			Game game = new Game();
			game.setup();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
