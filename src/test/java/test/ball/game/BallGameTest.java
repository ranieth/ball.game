package test.ball.game;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

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

		board.setCell(1, 0, 1);
		
		assertEquals(1,board.getCell(1, 0));
		
	}

	@Test(expected=BallGameException.class)
	public void testSetCellFailValue(){

		board.setCell(1, 1, 3);
		board.setCell(1, 1, -2);
		
	}
	
	@Test
	public void testGetCellPass(){

		int cell = board.getCell(4, 5);
		
	}

	@Test
	public void testBall(){

		ball = board.new Ball(1,3);
		
	}
	
	@Test
	public void testMoveUp(){

		ball = board.new Ball(9,0);
		
		ball.moveUp();
		
		assertEquals(0,ball.getRow());
		
	}
	
	@Test
	public void testMoveUpGoal(){

		ball = board.new Ball(9,0);
		
		board.setGoal(5, 0);
		
		ball.moveUp();
		
		assertEquals(5,ball.getRow());
		
	}

	@Test
	public void testMoveDown(){

		ball = board.new Ball(0,0);
		
		ball.moveDown();
		
		assertEquals(9,ball.getRow());
		
	}

	@Test
	public void testMoveDownGoal(){

		ball = board.new Ball(0,0);
		
		board.setGoal(5, 0);
		
		ball.moveDown();
		
		assertEquals(5,ball.getRow());
		
	}
	
	@Test
	public void testMoveRight(){

		ball = board.new Ball(0,0);
		
		ball.moveRight();
		
		assertEquals(9,ball.getColumn());
		
	}

	@Test
	public void testMoveRightGoal(){

		ball = board.new Ball(0,0);
		
		board.setGoal(0, 5);
		
		ball.moveRight();
		
		assertEquals(5,ball.getColumn());
		
	}
	
	@Test
	public void testMoveLeft(){

		ball = board.new Ball(0,9);
		
		ball.moveLeft();
		
		assertEquals(0,ball.getColumn());
		
	}

	@Test
	public void testMoveLeftGoal(){

		ball = board.new Ball(0,9);
		
		board.setGoal(0, 5);
		
		ball.moveLeft();
		
		assertEquals(5,ball.getColumn());
		
	}
	
	@Test
	public void testFileInput(){
		
		board = new Board("ball.game.001.xml");
		
	}
	
	@Test
	public void testSetWall(){
		
		board.setWall(0, 1);
		
		assertEquals(1,board.getCell(0, 1));
		
	}
	
	@Test
	public void testSetGoal(){
		
		board.setGoal(0, 1);
		
		assertEquals(-1,board.getCell(0,1));
		
	}

	@Test
	public void testGetField(){
		
		ArrayList<ArrayList<Integer>> field = board.getField();
		
		assertEquals(0,field.get(0).get(0).intValue());
		
	}
	
	@Test
	public void testGetRows(){
		
		assertEquals(10,board.getRows());
		
	}
	
	@Test
	public void testGetColumns(){
		
		assertEquals(10,board.getColumns());
		
	}
	
	@Test
	public void testConstructBall(){
		
		board.setStart(0, 1);
		Board.Ball ball = board.new Ball();
		
		assertTrue(ball.getRow() == 0 && ball.getColumn() == 1);
		
	}
	
	@Test
	public void testPrintField(){
		
		board.setWall(4, 3);
		board.setGoal(5, 5);
		board.setStart(6, 4);
		
		Board.Ball ball = board.new Ball();
		
		ball.printField();
		
	}
	
}