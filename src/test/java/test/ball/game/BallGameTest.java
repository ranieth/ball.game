package test.ball.game;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ball.game.*;

public class BallGameTest {

	private Board board;
	private Board.Ball ball;
	
	@Before
	public void setUp(){
		
		board = new Board(10,10);
		
	}
	
	@Test
	public void testSetCellPass(){

		board = new Board(10,10);
		board.setCell(1, 0, 1);
		
		assertEquals(1,board.getCell(1, 0));
		
	}

	@Test(expected=BallGameException.class)
	public void testSetCellFailValue(){

		board = new Board(10,10);
		board.setCell(1, 1, 3);
		board.setCell(1, 1, -2);
		
	}
	
	@Test
	public void testGetCellPass(){

		board = new Board(10,10);
		int cell = board.getCell(4, 5);
		
	}

	@Test
	public void testBall(){

		board = new Board(10,10);
		ball = board.new Ball(1,3);
		
	}
	
	@Test
	public void testMoveUp(){

		board = new Board(10,10);
		ball = board.new Ball(9,0);
		
		ball.moveUp();
		
		assertEquals(0,ball.getRow());
		
	}

	@Test
	public void testMoveDown(){

		board = new Board(10,10);
		ball = board.new Ball(0,0);
		
		ball.moveDown();
		
		assertEquals(9,ball.getRow());
		
	}

	@Test
	public void testMoveRight(){

		board = new Board(10,10);
		ball = board.new Ball(0,0);
		
		ball.moveRight();
		
		assertEquals(9,ball.getColumn());
		
	}

	@Test
	public void testMoveLeft(){

		board = new Board(10,10);
		ball = board.new Ball(0,9);
		
		ball.moveLeft();
		
		assertEquals(0,ball.getColumn());
		
	}
	
}