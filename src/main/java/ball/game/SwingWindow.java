	
package ball.game;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import ball.game.Board.Ball;

/**
 * Class for playing the game in a swing window.
 * 
 * @author ranieth
 *
 */
public class SwingWindow extends Window {

	/**
	 * Contains the opened files name
	 */
	String filename;

	/**
	 * Constructs the game and utilities to play it in swing window.
	 * 
	 * @param filename the input xml file contains board parameters
	 */
	public SwingWindow(String filename){

		this.filename = filename;
		
	}

	/**
	 * Overrided method from Window class to handle game playment.
	 * 
	 * Overrided method to run the swing window.
	 */
	@Override
	public void Play() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateWindow frame = new CreateWindow(filename);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
