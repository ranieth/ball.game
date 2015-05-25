package ball.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;

public class CreateWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;
	BallGameTableModel tableModel;
	JButton buttonUp;
	JButton buttonDown;
	JButton buttonLeft;
	JButton buttonRight;
	
	boolean win = false;
	
	public CreateWindow(Board board, Board.Ball ball) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 255);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tableModel = new BallGameTableModel(board.getField(),ball.getRow(),ball.getColumn());
		
		table = new JTable(tableModel);
		
		resizeColumns(board.getColumns(),15);
		
		setTextAlign(board.getColumns());
		
		setWallsBackground();
		
		initButtons(ball);
		
		contentPane.add(table, BorderLayout.CENTER);
		
	}

	void resizeColumns(int count,int size){
		
		for(int i = 0;i < count;i++){
			
			table.getColumnModel().getColumn(i).setPreferredWidth(size);
			
		}
		
	}
	
	void setTextAlign(int count){
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		for(int i = 0;i < count;i++){
			
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			
		}
		
	}
	
	void setWallsBackground(){

		for(int j = 0;j < table.getColumnCount();j++){
			
			table.getColumnModel().getColumn(j).setCellRenderer(new WallCellRenderer());
				
		}
			
	}
	
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
	
	void setupButtonUp(Board.Ball ball){
		
		buttonUp.addActionListener(e -> {
			
			tableModel.setValueAt(" ", ball.getRow(), ball.getColumn());
			
			ball.moveUp();

			tableModel.setValueAt("B", ball.getRow(), ball.getColumn());
			
			this.repaint();
			
		});
		
	}

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
	
	void setupButtonRight(Board.Ball ball){
		
		buttonRight.addActionListener(e -> {
			
			tableModel.setValueAt(" ", ball.getRow(), ball.getColumn());
			
			ball.moveRight();

			tableModel.setValueAt("B", ball.getRow(), ball.getColumn());
			
			this.repaint();
			
		});
		
	}
	
	void setupButtonLeft(Board.Ball ball){

		
		buttonLeft.addActionListener(e -> {
			
			tableModel.setValueAt(" ", ball.getRow(), ball.getColumn());
			
			ball.moveLeft();

			tableModel.setValueAt("B", ball.getRow(), ball.getColumn());
			
			this.repaint();
			
		});
		
	}
	
	public class WallCellRenderer extends DefaultTableCellRenderer {
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			//Cells are by default rendered as a JLabel.
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
						
										
			if(table.getValueAt(row, column).equals("W")){			
							
				label.setBackground(Color.BLACK);
				label.setForeground(Color.BLACK);
				
			}else{
				
				label.setBackground(Color.WHITE);
				
			}
			
			//Return the JLabel which renders the cell.
			return label;
			  
		}

	}

}
