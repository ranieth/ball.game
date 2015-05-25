package ball.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ball.game.Board.Ball;

public class ConsoleWindow extends Window{

	Board board;
	Board.Ball ball;
	BufferedReader bufferRead;
	String read;
	final String ANSI_CLS;
	final String ANSI_HOME;
	
	public ConsoleWindow(String filename){

		board = new Board(filename);
		ball = board.new Ball();
		
		bufferRead = new BufferedReader(new InputStreamReader(System.in));
		
		ANSI_CLS = "\u001b[2J";
		ANSI_HOME = "\u001b[H";
		
	}
	
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
