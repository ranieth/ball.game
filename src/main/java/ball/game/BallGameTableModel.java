package ball.game;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class BallGameTableModel implements TableModel {

	ArrayList<ArrayList<String>> Board = new ArrayList<ArrayList<String>>();
	
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

	
	@Override
	public int getRowCount() {

		return Board.size();
		
	}

	@Override
	public int getColumnCount() {
		
		return Board.get(0).size();
		
	}

	@Override
	public String getColumnName(int columnIndex) {

		return String.valueOf(columnIndex);
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		return String.class;
		
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
		
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		return Board.get(rowIndex).get(columnIndex);
		
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		Board.get(rowIndex).set(columnIndex, String.valueOf(aValue));
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

}
