package ball.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;

/**
 * Class for create and handle swing window and its events.
 * 
 * @author ranieth
 *
 */
public class CreateWindow extends JFrame {

	/**
	 * The pane for the interfaces contents.
	 */
	private JPanel contentPane;
	/**
	 * Table to print out the game field.
	 */
	private JTable table;
	/**
	 * Table model to handle the changes of the field on the interface.
	 */
	BallGameTableModel tableModel;
	/**
	 * Button to move up the ball on the field.
	 */
	JButton buttonUp;
	/**
	 * Button to move down the ball on the field.
	 */
	JButton buttonDown;
	/**
	 * Button to move left the ball on the field.
	 */
	JButton buttonLeft;
	/**
	 * Button to move right the ball on the field.
	 */
	JButton buttonRight;

	/**
	 * Boolean variable to determine win after a button press and interface repaint.
	 */
	boolean win = false;
	
	/**
	 * The board of the game.
	 */
	Board board;
	/**
	 * The ball of the game.
	 */
	Board.Ball ball;

	/**
	 * Constructs the window for the game.
	 * 
	 * Constructs the window pane, and create the content of the window.
	 * 
	 * @param filename the name of the xml file to import
	 */
	public CreateWindow(String filename) {
		
		board = new Board(filename);
		ball = board.new Ball();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 270);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JButton buttonImport = new JButton("Import xml");
		
		buttonImport.addActionListener(new ActionListener(){
			
			@Override
            public void actionPerformed(ActionEvent arg0) {
				
                JFileChooser openFile = new JFileChooser();
                int returnValue = openFile.showOpenDialog(null);

    			if(returnValue == JFileChooser.APPROVE_OPTION){
    				
    				File file = openFile.getSelectedFile();
    				
    				board = new Board(file);
    				ball = board.new Ball();
    				
    				contentPane.removeAll();
    				
    				initInterface();
    				
    				contentPane.revalidate();
    				
    				contentPane.repaint();
    				
    			}
                
            }
			
		});
		
		menuBar.add(buttonImport);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		initInterface();
		
	}
	
	/**
	 * Initalizes the games interface.
	 * 
	 * Initalizes the field table with the board then creates the buttons.
	 */
	void initInterface(){
		
		tableModel = new BallGameTableModel(board.getField(),ball.getRow(),ball.getColumn());
		
		table = new JTable(tableModel);
		
		resizeColumns(board.getColumns(),15);
		
		setTextAlign(board.getColumns());
		
		setWallsBackground();
		
		initButtons(ball);

		contentPane.add(table, BorderLayout.CENTER);
		
	}
	
	/**
	 * Resizes all of the columns to a given size.
	 * 
	 * @param count the count of columns on the game board
	 * @param size the wanted size of the columns
	 */
	void resizeColumns(int count,int size){
		
		for(int i = 0;i < count;i++){
			
			table.getColumnModel().getColumn(i).setPreferredWidth(size);
			
		}
		
	}
	
	/**
	 * Sets the text align to center in the columns.
	 * 
	 * @param count the count of columns on the game board
	 */
	void setTextAlign(int count){
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		for(int i = 0;i < count;i++){
			
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			
		}
		
	}
	
	/**
	 * Sets black background to walls on game board.
	 */
	void setWallsBackground(){

		for(int j = 0;j < table.getColumnCount();j++){
			
			table.getColumnModel().getColumn(j).setCellRenderer(new WallCellRenderer());
				
		}
			
	}
	
	/**
	 * Initalizes the moving buttons.
	 * 
	 * Initalizes the buttons, adds them to the content pane and call the event handler initalizer
	 * functions.
	 * 
	 * @param ball the ball of the game board
	 */
	void initButtons(Board.Ball ball){
		
		buttonUp = new JButton("Up");
		contentPane.add(buttonUp, BorderLayout.NORTH);
		setupButtonUp(ball);

		buttonDown = new JButton("Down");
		contentPane.add(buttonDown, BorderLayout.SOUTH);
		setupButtonDown(ball);
		
		buttonRight = new JButton("Right");
		contentPane.add(buttonRight, BorderLayout.EAST);
		setupButtonRight(ball);
		
		buttonLeft = new JButton("Left");
		contentPane.add(buttonLeft, BorderLayout.WEST);
		setupButtonLeft(ball);
		
	}
	
	/**
	 * Initalizes the action listener to the button which moves up the ball on the board.
	 * 
	 * @param ball the ball of the game board
	 */
	void setupButtonUp(Board.Ball ball){
		
		buttonUp.addActionListener(e -> {
			
			tableModel.setValueAt(" ", ball.getRow(), ball.getColumn());
			
			if(ball.moveUp()){
				
				win = true;
				
			}

			tableModel.setValueAt("B", ball.getRow(), ball.getColumn());
			
			this.repaint();

			if(win){

				JOptionPane.showMessageDialog(null,"You win!");
				setVisible(false);
				dispose();
				
			}
			
		});
		
	}

	/**
	 * Initalizes the action listener to the button which moves down the ball on the board.
	 * 
	 * @param ball the ball of the game board
	 */
	void setupButtonDown(Board.Ball ball){
		
		buttonDown.addActionListener(e -> {
			
			tableModel.setValueAt(" ", ball.getRow(), ball.getColumn());
			
			if(ball.moveDown()){
				
				win = true;
				
			}

			tableModel.setValueAt("B", ball.getRow(), ball.getColumn());
			
			this.repaint();
			
			if(win){

				JOptionPane.showMessageDialog(null,"You win!");
				setVisible(false);
				dispose();
				
			}
			
		});
		
	}

	/**
	 * Initalizes the action listener to the button which moves right the ball on the board.
	 * 
	 * @param ball the ball of the game board
	 */
	void setupButtonRight(Board.Ball ball){
		
		buttonRight.addActionListener(e -> {
			
			tableModel.setValueAt(" ", ball.getRow(), ball.getColumn());
			
			if(ball.moveRight()){
				
				win = true;
				
			}

			tableModel.setValueAt("B", ball.getRow(), ball.getColumn());
			
			this.repaint();

			if(win){

				JOptionPane.showMessageDialog(null,"You win!");
				setVisible(false);
				dispose();
				
			}
			
		});
		
	}

	/**
	 * Initalizes the action listener to the button which moves left the ball on the board.
	 * 
	 * @param ball the ball of the game board
	 */
	void setupButtonLeft(Board.Ball ball){

		
		buttonLeft.addActionListener(e -> {
			
			tableModel.setValueAt(" ", ball.getRow(), ball.getColumn());
			
			if(ball.moveLeft()){
				
				win = true;
				
			}

			tableModel.setValueAt("B", ball.getRow(), ball.getColumn());
			
			this.repaint();

			if(win){

				JOptionPane.showMessageDialog(null,"You win!");
				setVisible(false);
				dispose();
				
			}
			
		});
		
	}
	
	/**
	 * Class to create a cell renderer to the table.
	 * 
	 * @author ranieth
	 *
	 */
	public class WallCellRenderer extends DefaultTableCellRenderer {
		
		/**
		 * Overrided method which creates the renderer to the table.
		 * 
		 * Overrided method which creates the renderer to the table. The renderer sets
		 * all the walls background to black in the table.
		 * 
		 * @param table the table object of the interface
		 * @param value the value of the actual cell in the table
		 * @param isSelected <code>true</code> if the actual cell is selected, <code>false</code> otherwise
		 * @param hasFocus <code>true</code> if the actual cell is focused, <code>false</code> otherwise
		 * @param row the row index of the actual cell
		 * @param column the column index of the actual cell
		 * 
		 * @return returns with the customized label for the cell
		 */
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
						
										
			if(table.getValueAt(row, column).equals("W")){			
							
				label.setBackground(Color.BLACK);
				label.setForeground(Color.BLACK);
				
			}else{
				
				label.setBackground(Color.WHITE);
				
			}
			
			return label;
			  
		}

	}

}
