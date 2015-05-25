package ball.game;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import ball.game.Board.Ball;

public class SwingWindow extends Window {

	Board board;
	Board.Ball ball;
	
	public SwingWindow(String filename){

		board = new Board(filename);
		ball = board.new Ball();
		
	}
	
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
