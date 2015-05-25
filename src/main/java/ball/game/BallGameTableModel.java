package ball.game;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Class to create a table model for the games interface table.
 * 
 * @author ranieth
 *
 */
public class BallGameTableModel implements TableModel {

	/**
	 * Board of the game contains the representing string values of the elements of the field.
	 */
	ArrayList<ArrayList<String>> Board = new ArrayList<ArrayList<String>>();
	
	/**
	 * Constructs the table model for handling the table interface.
	 * 
	 * @param Field the actual field of the game
	 * @param start_row the starting row coordinate of the game
	 * @param start_column the starting column coordinate of the game
	 */
	public BallGameTableModel(ArrayList<ArrayList<Integer>> Field, int start_row, int start_column){
		
		for(int i = 0;i < Field.size();i++){
			
			Board.add(new ArrayList<String>(Collections.nCopies(Field.get(i).size()," ")));
			
		}
		
		for(int i = 0;i < Field.size();i++){
			
			for(int j = 0;j < Field.get(i).size();j++){
				
				switch(Field.get(i).get(j)){
					
						case 0: Board.get(i).set(j, " ");
							break;
						case -1: Board.get(i).set(j, "O");
							break;
						case 1: Board.get(i).set(j, "W");
							break;
				}
				
			}
			
		}

		Board.get(start_row).set(start_column, "B");
		
	}

	/**
	 * Gives back the count of rows in the table.
	 * 
	 * @return the count of rows in the table
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {

		return Board.size();
		
	}

	/**
	 * Gives back the count of columns in the table.
	 * 
	 * @return the count of columns in the table
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		
		return Board.get(0).size();
		
	}

	/**
	 * Gives back the name of the wanted column.
	 * 
	 * @param columnIndex the index of the wanted column
	 * @return the name of the wanted column
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {

		return String.valueOf(columnIndex);
		
	}

	/**
	 * Returns with the class of the wanted column
	 * 
	 * @param columnIndex the index of the wanted column
	 * @return the class of the wanted column
	 * @see javax.swing.table.TableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {

		return String.class;
		
	}

	/**
	 * Returns with <code>true</code> if the wanted cell is editable, <code>false</code> otherwise.
	 * 
	 * @param rowIndex the index of the wanted cells row
	 * @param columnIndex the index of the wanted cells column
	 * @return <code>true</code> if the wanted cell is editable, <code>false</code> otherwise
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
		
	}

	/**
	 * Return with the wanted cells value in the table.
	 * 
	 * @param rowIndex the index of the wanted cells row
	 * @param columnIndex the index of the wanted cells column
	 * return the value of the wanted cell
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		return Board.get(rowIndex).get(columnIndex);
		
	}

	/**
	 * Sets the value of a cell in the table.
	 * 
	 * @param aValue the new value of the cell
	 * @param rowIndex the index of the row of the cell
	 * @param columnIndex the index of the column of the cell
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		Board.get(rowIndex).set(columnIndex, String.valueOf(aValue));
		
	}

	/**
	 * Adds listener to the table model.
	 * 
	 * Not used!
	 */
	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Removes listener from the table model.
	 * 
	 * Not used!
	 */
	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

}
