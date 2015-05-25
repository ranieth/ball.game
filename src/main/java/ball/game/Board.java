package ball.game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import javax.xml.parsers.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * Class handling the game board and ball.
 * 
 * Creates, handles, provides the board of the game and the ball for it.
 * 
 * @author ranieth
 *
 */
public class Board {

	/**
	 * Count of rows on the board.
	 */
	int row;
	/**
	 * Count of columns on the board.
	 */
	int column;
	/**
	 * Starting positions row.
	 */
	int start_row;
	/**
	 * Starting positions column.
	 */
	int start_column;
	/**
	 * Represents the actual board with integer values.
	 */
	ArrayList<ArrayList<Integer>> Field = new ArrayList<ArrayList<Integer>>();
	
	private static Logger logger = LoggerFactory.getLogger(Board.class);
	
	/**
	 * Constructs a simple all zero board sized to the given row and column parameters.
	 * 
	 * @param row how many row long will the board be
	 * @param column how many column long will the board be
	 */
	public Board(int row, int column){
		
		this.row = row;
		this.column = column;
		
		createBoard();
		
	}
	
	/**
	 * Constructs the board from a provided xml file.
	 * 
	 * @param filename the xml file which contains the parameters of a board
	 */
	public Board(String filename){

		logger.info("Reading game from file {}",filename);
		
		ClassLoader classLoader = getClass().getClassLoader();
		
		InputStream file = classLoader.getResourceAsStream(filename);
		
		readFieldSax(file);
		
	}
	
	/**
	 * Utilizes the memory for the boards field.
	 */
	void createBoard(){

		logger.info("Creating game board");
		for(int i = 0;i < row;i++){
			
			Field.add(new ArrayList<Integer>(Collections.nCopies(column,0)));
			
		}
		
	}
	
	/**
	 * Fills up the board with zeros.
	 */
	void fillBoard(){

		logger.info("Filling game field with zeros");
		for(int i = 0;i < row;i++){
			
			for(int j = 0;j < column;j++){
			
				Field.get(i).set(j,0);
			
			}
			
		}
		
	}
	
	/**
	 * Creates a wall on the board at the provided coordinates.
	 * 
	 * @param row the row coordinate of the wall
	 * @param column the column coordinate of the wall
	 */
	public void setWall(int row, int column){

		logger.info("Setting up wall on the board {},{}",row,column);
		Field.get(row).set(column, 1);
		
	}
	
	/**
	 * Checks if there is a wall on the given coordinates.
	 * 
	 * Checks if there is a wall or end of game field on the given coordinates.
	 * 
	 * @param row the row coordinate on the board
	 * @param column the column coordinate on the board
	 * @return <code>true</code> if there is a wall or end of game field on the provided coordinates, <code>false</code> otherwise
	 */
	boolean isWall(int row, int column){
		
		logger.info("Checking field has wall / table end on provided coordinates {},{}",row, column);
		return 	
				(
					(row == this.row)
					||
					(row < 0)
				)
				||
				(
					(column == this.column)
					||
					(column < 0)
				)
				||
				(
					getCell(row,column) == 1
				);
		
	}
	
	/**
	 * Creates the goal on the board at the provided coordinates.
	 * 
	 * @param row the row coordinate of the goal
	 * @param column the column coordinate of the goal
	 */
	public void setGoal(int row, int column){

		logger.info("Setting up board goal cell {},{}",row,column);
		Field.get(row).set(column, -1);
		
	}
	

	/**
	 * Checks if there is the goal on the given coordinates.
	 * 
	 * @param row the row coordinate on the board
	 * @param column the column coordinate on the board
	 * @return <code>true</code> if there is the goal on the provided coordinates, <code>false</code> otherwise
	 */
	boolean isGoal(int row, int column){

		logger.info("Checking field has goal cell on provided coordinates {},{}",row,column);
		return Field.get(row).get(column) == -1;
		
	}
	
	/**
	 * Initializes the starting position on the board.
	 * 
	 * @param row the row coordinate of the starting position
	 * @param column the column coordinate of the starting position
	 */
	public void setStart(int row, int column){

		logger.info("Initing starting position {},{}",row,column);
		this.start_row = row;
		this.start_column = column;
		
	}
	
	/**
	 * Returns the cell value of the provided coordinates.
	 * 
	 * @param row the row coordinate on the board
	 * @param column the column coordinate on the board
	 * @return the value of the wanted cell
	 */
	public int getCell(int row, int column){

		return Field.get(row).get(column);

	}

	/**
	 * Setting up the value of the cell with the provided coordinates.
	 * 
	 * @param row the row coordinate on the board
	 * @param column the column coordinate on the board
	 * @param value the value for the cell
	 * 
	 * @throws BallGameException if the provided coordinates are not valid
	 */
	public void setCell(int row, int column,int value){

		logger.info("Setting up cell value at provided coordinates {},{}",row,column);
		if(	value <= 1
			&&
			value >= -1
		){

			Field.get(row).set(column,value);
				
		}else{
			
			logger.error("Bad cell coordinates");
			throw new BallGameException("Bad coordinates for cell");
			
		}
		
	}

	/**
	 * Reads the board parameters from the given file.
	 * 
	 * Uses Sax Parser to parse the provided xml file to read the board parameters from it.
	 * 
	 * @param file the inputstream of the file
	 */
	public void readFieldSax(InputStream file){
		
		logger.info("Reading game board from xml");
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		try {
			
			SAXParser parser = factory.newSAXParser();
			
			DefaultHandler handler = new DefaultHandler(){

				boolean nodeRow = false;
				boolean nodeColumn = false;
				
				boolean nodeSize = false;
				boolean nodeStart = false;
				boolean nodeGoal = false;
				boolean nodeWall = false;
				
				int tempRow;
				
				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
					
					if(qName.equalsIgnoreCase("size")){
						
						nodeSize = true;
						
					}

					if(qName.equalsIgnoreCase("start")){
						
						nodeStart = true;
						
					}

					if(qName.equalsIgnoreCase("goal")){

						createBoard();
						nodeGoal = true;
						
					}
					
					if(qName.equalsIgnoreCase("wall")){
						
						nodeWall = true;
						
					}
					
					if(	qName.equalsIgnoreCase("row")
						||
						qName.equalsIgnoreCase("rows")){
						
						nodeRow = true;
						
					}

					if(	qName.equalsIgnoreCase("column")
						||
						qName.equalsIgnoreCase("columns")){
						
						nodeColumn = true;
						
					}
					
				}
				
				@Override
				public void endElement(String uri, String localName, String qName) throws SAXException {

					if(qName.equalsIgnoreCase("size")){
						
						nodeSize = false;
						
					}

					if(qName.equalsIgnoreCase("start")){
						
						nodeStart = false;
						
					}

					if(qName.equalsIgnoreCase("goal")){
						
						nodeGoal = false;
						
					}
					
					if(qName.equalsIgnoreCase("wall")){
						
						nodeWall = false;
						
					}
					
					if(	qName.equalsIgnoreCase("row")
						||
						qName.equalsIgnoreCase("rows")){
						
						nodeRow = false;
						
					}

					if(	qName.equalsIgnoreCase("column")
						||
						qName.equalsIgnoreCase("columns")){
						
						nodeColumn = false;
						
					}
					
				}
				
				@Override
				public void characters(char ch[], int start, int length) throws SAXException {

					if(nodeSize){
						
						if(nodeRow){

							row = Integer.parseInt(String.copyValueOf(ch, start, length));
							
						}
						
						if(nodeColumn){
							
							column = Integer.parseInt(String.copyValueOf(ch, start, length));
							
						}
						
					}
					
					if(nodeStart){

						if(nodeRow){
							
							start_row = Integer.parseInt(String.copyValueOf(ch, start, length));
							
						}
						
						if(nodeColumn){
							
							start_column = Integer.parseInt(String.copyValueOf(ch, start, length));
							
						}
						
					}
					
					if(nodeGoal){
						
						if(nodeRow){
							
							tempRow = Integer.parseInt(String.copyValueOf(ch, start, length));
							
						}
						
						if(nodeColumn){
							
							setGoal(tempRow,Integer.parseInt(String.copyValueOf(ch, start, length)));
							
						}
						
					}

					if(nodeWall){
						
						if(nodeRow){

							tempRow = Integer.parseInt(String.copyValueOf(ch, start, length));
							
						}
						
						if(nodeColumn){

							setWall(tempRow,Integer.parseInt(String.copyValueOf(ch, start, length)));
							
						}
						
					}
					
					
				}
				
			};
			
			parser.parse(file, handler);
			
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
			
		} catch (SAXException e){
			
			e.printStackTrace();
			
		} catch (IOException e){
			
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * Returns the actual field of the board.
	 * 
	 * @return the actual field of the board. 
	 */
	public ArrayList<ArrayList<Integer>> getField(){
		
		return Field;
		
	}
	
	/**
	 * Returns the number of rows of the board.
	 * 
	 * @return the number of rows of the board.
	 */
	public int getRows(){
		
		return row;
		
	}
	
	/**
	 * Returns the number of columns of the board.
	 * 
	 * @return the number of columns of the board.
	 */
	public int getColumns(){
		
		return column;
		
	}
	
	/**
	 * Class represents the ball of the game.
	 * 
	 * Utilizes and handles the ball for the actual board.
	 */
	public class Ball{
		
		/**
		 * Row coordinate of the ball.
		 */
		int row;
		/**
		 * Column coordinate of the ball
		 */
		int column;
		
		/**
		 * Constructs the ball with the given coordinates.
		 * 
		 * Setting up the balls coordinates provided.
		 * 
		 * @param row the row coordinate of the ball
		 * @param column the column coordinate of the ball
		 * 
		 * @throws BallGameException if the coordinates are not valid
		 */
		public Ball(int row, int column){

			logger.info("Setting up ball on {},{}",row,column);
			if(	row < Board.this.row
				&&
				column < Board.this.column
			){
				
				this.row = row;
				this.column = column;
				
				startPosition(row, column);

			}else{

				logger.error("Invalid ball coordinates");
				throw new BallGameException("Invalid ball coordinates");
				
			}
				
		}
		
		/**
		 * Constructs the ball with the starting poisition coordinates of the board.
		 */
		public Ball(){

			logger.info("Setting up ball on {},{}",Board.this.start_row,Board.this.start_column);
			row = Board.this.start_row;
			column = Board.this.start_column;
			
		}
		
		/**
		 * Setting up the start position of the ball with the given coordinates.
		 * 
		 * @param row the row coordinate of the ball
		 * @param the column coordinate of the ball
		 */
		void startPosition(int row, int column){

			logger.info("Setting up start coordinates for ball {},{}",row,column);
			this.row = row;
			this.column = column;
			
		}
		
		/**
		 * Moves up the ball on the board.
		 * 
		 * Moves up the ball on the board until it is reach a wall, side of the board or the goal.
		 * 
		 * @return <code>true</code> if the ball reach the goal, <code>false</code> otherwise
		 */
		public boolean moveUp(){

			logger.info("Moving the ball up on the board from {},{}",row,column);
			int move_row = row;
			int move_column = column;

			while(!isWall(move_row - 1,move_column)){
				
				if(isGoal(move_row - 1,move_column)){
					
					row = move_row - 1;
					logger.info("Ball reached goal cell at {},{}",row, column);
					return true;
					
				}
				
				move_row--;
				
			}
			
			row = move_row;
			column = move_column;
			
			logger.info("Ball reached cell {},{}",row,column);
			
			return false;
			
		}

		/**
		 * Moves down the ball on the board.
		 * 
		 * Moves down the ball on the board until it is reach a wall, side of the board or the goal.
		 * 
		 * @return <code>true</code> if the ball reach the goal, <code>false</code> otherwise
		 */
		public boolean moveDown(){

			logger.info("Moving the ball down on the board from {},{}",row,column);
			int move_row = row;
			int move_column = column;

			while(!isWall(move_row + 1,move_column)){

				if(isGoal(move_row + 1,move_column)){
					
					row = move_row + 1;
					logger.info("Ball reached goal cell at {},{}",row, column);
					return true;
					
				}
				
				move_row++;
				
			}
			
			row = move_row;
			column = move_column;
			logger.info("Ball reached cell {},{}",row,column);
			
			return false;
			
		}

		/**
		 * Moves right the ball on the board.
		 * 
		 * Moves right the ball on the board until it is reach a wall, side of the board or the goal.
		 * 
		 * @return <code>true</code> if the ball reach the goal, <code>false</code> otherwise
		 */
		public boolean moveRight(){

			logger.info("Moving the ball right on the board");
			int move_row = row;
			int move_column = column;

			while(!isWall(move_row,move_column + 1)){

				if(isGoal(move_row,move_column + 1)){
					
					column = move_column + 1;
					logger.info("Ball reached goal cell at {},{}",row, column);
					return true;
					
				}
				
				move_column++;
				
			}

			row = move_row;
			column = move_column;
			logger.info("Ball reached cell {},{}",row,column);
			
			return false;
			
		}

		/**
		 * Moves left the ball on the board.
		 * 
		 * Moves left the ball on the board until it is reach a wall, side of the board or the goal.
		 * 
		 * @return <code>true</code> if the ball reach the goal, <code>false</code> otherwise
		 */
		public boolean moveLeft(){

			logger.info("Moving the ball left on the board");
			int move_row = row;
			int move_column = column;

			while(!isWall(move_row,move_column - 1)){

				if(isGoal(move_row,move_column - 1)){
					
					column = move_column - 1;
					logger.info("Ball reached goal cell at {},{}",row, column);
					return true;
					
				}
				
				move_column--;
					
			}

			row = move_row;
			column = move_column;
			logger.info("Ball reached cell {},{}",row,column);
			
			return false;
			
		}
		
		/**
		 * Returns with the current row coordinate of the ball.
		 * 
		 * @return the row coordinate of the ball
		 */
		public int getRow(){
			
			return row;
			
		}

		/**
		 * Returns with the current column coordinate of the ball.
		 * 
		 * @return the column coordinate of the ball
		 */
		public int getColumn(){
			
			return column;
			
		}

		/**
		 * Writes out the actual gaming field with the ball to the standard output
		 */
		public void printField(){
			
			logger.info("Printing out actual field");
			
			StringJoiner output;
			
			for(int i = 0;i < Board.this.row;i++){
				
				output = new StringJoiner(" ");
				
				for(int j = 0;j < Board.this.column;j++){
					
					if(	i == row
						&&
						j == column
						){
						
						output.add("B");
						
					}else{
						
						switch(Field.get(i).get(j)){
						
							case 1: output.add("W");
								break;
							case 0: output.add(" ");
								break;
							case -1: output.add("O");
								break;
						
						}
						
					}
					
				}
				
				System.out.println(output.toString());
				
			}
			
		}
		
	}
	
}
