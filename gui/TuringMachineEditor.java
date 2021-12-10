package gui;

import java.awt.BorderLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import turmach.model.Configuration;
import turmach.model.TuringMachine;

public class TuringMachineEditor extends JPanel {
	private static final long serialVersionUID = 1284570905537624819L;
	
	JTable table ;
	TuringMachine model ;
	
	public TuringMachineEditor(TuringMachine model) {
		this.model = model ;
		this.table = makeTable(model) ;
		this.setLayout( new BorderLayout() );
		JScrollPane scrollPane = new JScrollPane( table ) ;
		this.add( BorderLayout.CENTER, scrollPane ) ;
		JToolBar toolBar = new TuringMachineToolBar( model, table ) ;
		this.add( BorderLayout.NORTH, toolBar ) ;
	}
	
	void refresh() {
		this.repaint() ;
	}
	


	private static JTable makeTable( TuringMachine model ) {
        
        ModelToTableAdapter adapter = new ModelToTableAdapter(model) ;

    	JTable table = new JTable(adapter) ;
        table.setColumnSelectionAllowed(false);
        setUpDirectionColumn(
        		table,
        		table.getColumnModel().getColumn(3)) ;
        setUpSymbolColumn(
        		table,
        		table.getColumnModel().getColumn(1),
        		model ) ;
        setUpSymbolColumn(
        		table,
        		table.getColumnModel().getColumn(2),
        		model ) ;
        return table ;
	}
    
    private static void setUpDirectionColumn(JTable table,
    		TableColumn directionColumn) {
    	//Set up the editor for the direction cells.
    	JComboBox<Configuration.Direction> comboBox = new JComboBox<>();
    	comboBox.addItem(TuringMachine.Direction.LEFT);
    	comboBox.addItem(TuringMachine.Direction.RIGHT);
    	directionColumn.setCellEditor(new DefaultCellEditor(comboBox));

    	//Set up tool tips for the direction cells.
    	DefaultTableCellRenderer renderer =
    			new DefaultTableCellRenderer();
    	renderer.setToolTipText("Click for combo box");
    	directionColumn.setCellRenderer(renderer);
    }
    
    private static void setUpSymbolColumn(JTable table,
    		TableColumn symbolColumn,
    		TuringMachine model) {
    	//Set up the editor for the sport cells.
    	
    	SymbolComboBoxModel comboBoxModel = new SymbolComboBoxModel(model) ;
    	JComboBox<Character> comboBox = new JComboBox<>(comboBoxModel);
    	symbolColumn.setCellEditor(new DefaultCellEditor(comboBox));

    	//Set up tool tips for the sport cells.
    	DefaultTableCellRenderer renderer =
    			new DefaultTableCellRenderer();
    	renderer.setToolTipText("Click for combo box");
    	symbolColumn.setCellRenderer(renderer);
    }
}