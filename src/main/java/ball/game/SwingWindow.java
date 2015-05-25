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
	 * The board of the game.
	 */
	Board board;
	/**
	 * The ball of the game.
	 */
	Board.Ball ball;

	/**
	 * Constructs the game and utilities to play it in swing window.
	 * 
	 * @param filename the input xml file contains board parameters
	 */
	public SwingWindow(String filename){

		board = new Board(filename);
		ball = board.new Ball();
		
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
					CreateWindow frame = new CreateWindow(board,ball);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
