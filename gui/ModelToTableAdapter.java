package gui;

import javax.swing.table.AbstractTableModel;

import turmach.model.TuringMachine;
import turmach.model.Configuration;
import static turmach.model.Configuration.Direction ;
import util.Assert;

public class ModelToTableAdapter extends AbstractTableModel
	implements TuringMachine.TMObserver {
	private static final long serialVersionUID = -2368888535103534009L;
	
	private TuringMachine model ;

	public ModelToTableAdapter(TuringMachine model) {
		this.model = model ;
		model.addObserver( this );
	}
	
	public TuringMachine getModel() {
		return model ;
	}

	@Override
	public int getRowCount() {
		return model.getRuleCount() ;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch( columnIndex ) {
			case 0: return "State" ;
			case 1: return "Symbol" ;
			case 2: return "New symbol" ;
			case 3: return "Move" ;
			case 4: return "New state" ;
			default : throw new IllegalArgumentException() ;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch( columnIndex ) {
			case 0: return String.class ;
			case 1: return Character.class ;
			case 2: return Character.class ;
			case 3: return Configuration.Direction.class ;
			case 4: return String.class ;
			default : throw new IllegalArgumentException() ;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Assert.check( 0 <= columnIndex && columnIndex < 5 ) ;
		Assert.check( 0 <= rowIndex && rowIndex < model.getRuleCount() ) ;
		switch( columnIndex ) {
			case 0: return model.getRuleState( rowIndex ) ;
			case 1: return model.getRuleSymbol( rowIndex ) ;
			case 2: return model.getRuleNewSymbol( rowIndex ) ;
			case 3: return model.getRuleDirection( rowIndex ) ;
			case 4: return model.getRuleNewState( rowIndex ) ;
			default : throw new IllegalArgumentException() ;
		}
	}

	@Override
	public void setValueAt(Object newValue, int rowIndex, int columnIndex) {
		Assert.check( 0 <= columnIndex && columnIndex < 5 ) ;
		Assert.check( 0 <= rowIndex && rowIndex < model.getRuleCount() ) ;
		
		String state = model.getRuleState( rowIndex ) ;  
		char symbol = model.getRuleSymbol( rowIndex ) ;
		Direction direction = model.getRuleDirection( rowIndex ) ;
		char newSymbol = model.getRuleNewSymbol( rowIndex ) ;
		String newState = model.getRuleNewState( rowIndex ) ;
		
		switch( columnIndex ) {
			case 0: state = (String) newValue ;
				break ;
			case 1: symbol = (char) newValue ;
				break ;
			case 2: newSymbol = (char) newValue ;
				break ;
			case 3: direction = (Direction) newValue ;
				break ;
			case 4: newState = (String) newValue ;
				break ;
			default : throw new IllegalArgumentException() ;
		}
		model.changeRule(rowIndex, state, symbol, newSymbol, direction, newState);
		//fireTableCellUpdated(rowIndex, columnIndex);
	}

	@Override
	public void notifyChangeToTM(TuringMachine tm) {
		fireTableDataChanged();
	}

}
