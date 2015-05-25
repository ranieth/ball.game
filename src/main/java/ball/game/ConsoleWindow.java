package ball.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ball.game.Board.Ball;

/**
 * Class for playing the game in a terminal window.
 * 
 * @author ranieth
 *
 */
public class ConsoleWindow extends Window{

	/**
	 * The board of the game.
	 */
	Board board;
	/**
	 * The ball of the game.
	 */
	Board.Ball ball;
	/**
	 * Variable to scan player inputs from standard input.
	 */
	BufferedReader bufferRead;
	/**
	 * Variable to store player input command.
	 */
	String read;
	/**
	 * Constant to clear the screen.
	 */
	final String ANSI_CLS;
	/**
	 * Contant to reset cursor after screen clear.
	 */
	final String ANSI_HOME;
	
	/**
	 * Constructs the game and utilities to play it in terminal window.
	 * 
	 * @param filename the input xml file contains board parameters
	 */
	public ConsoleWindow(String filename){

		board = new Board(filename);
		ball = board.new Ball();
		
		bufferRead = new BufferedReader(new InputStreamReader(System.in));
		
		ANSI_CLS = "\u001b[2J";
		ANSI_HOME = "\u001b[H";
		
	}
	
	/**
	 * Overrided method from Window class to handle game playment.
	 * 
	 * Overrided method to handle the game field, receive player inputs.
	 */
	@Override
	public void Play() {

        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
		
		ball.printField();
		
		boolean goal = false;
		try {
			
			do{
				
			read = bufferRead.readLine();
			
			switch(read){
			
				case "up": if(ball.moveUp()) goal = true;
					break;

				case "down": if(ball.moveDown()) goal = true;
					break;

				case "left": if(ball.moveLeft()) goal = true;
					break;

				case "right": if(ball.moveRight()) goal = true;
					break;
			
			}

	        System.out.print(ANSI_CLS + ANSI_HOME);
	        System.out.flush();
			
			ball.printField();
			
			}while(goal != true);
			
			System.out.println("You win!");
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

}
