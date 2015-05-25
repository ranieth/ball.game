package ball.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class of the ball in the maze game what can be played in terminal or as a window application.
 * 
 * @author ranieth
 *
 */
public class BallGame{

	/**
	 * Initalizes the logger object to create logs from the game.
	 */
	private static Logger logger = LoggerFactory.getLogger(BallGame.class);

	/**
	 * Main method of the game.
	 * 
	 * @param args given arguments to determine interface type and starting board file
	 */
	public static void main(String[] args) {

		if(args.length < 2){
			
			System.out.println("Missing play mode! (console / window)");
			logger.error("Missing play mode argument");
			System.exit(0);
			
		}
		
		Window game = null;
		
		switch(args[0]){
		
			case "console": game = new ConsoleWindow(args[1]);
							logger.info("User picked console interface");
				break;
			case "window": 	game = new SwingWindow(args[1]);
							logger.info("User picked swing window interface");
				break;
		
		}
		
		logger.info("Starting game");
		game.Play();
		logger.info("Ending game");
		
	}

}
