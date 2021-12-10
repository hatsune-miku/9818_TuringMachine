package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import turmach.model.TuringMachine;

public class TuringMachineToolBar extends JToolBar {
	
	private static final long serialVersionUID = 3271294535087672493L;

	TuringMachineToolBar(TuringMachine model, JTable table ) {
		JButton button ;
		ActionListener listener ;
		
		button = new JButton( "Delete Rule" ) ;
		listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int ruleNum = table.getSelectedRow() ;
				if( ruleNum >= 0 && ruleNum < model.getRuleCount() ) {
					model.removeRule(ruleNum);
				}
			}
		} ;
		button.addActionListener( listener ) ;
		this.add( button ) ;

		
		button = new JButton( "Add Rule" ) ;
		listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int ruleNum = table.getSelectedRow() ;
				if( ruleNum < 0 || ruleNum > model.getRuleCount() ) {
					ruleNum = model.getRuleCount() ; }
				char blank = model.getBlankSymbol() ;
				model.addRule(ruleNum,
						"state",
						blank,
						blank,
						TuringMachine.Direction.RIGHT,
						"new state" );
			}
		} ;
		button.addActionListener( listener ) ;
		this.add( button ) ;
		
		JLabel label = new JLabel( "Change symbol set to:" ) ;
		this.add( label ) ;
		
		JTextField textField = new JTextField(10) ;
		listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				char[]  text = textField.getText().toCharArray() ;
				char blank = model.getBlankSymbol() ;
				List<Character> symbols = model.getSymbolList() ;
				for( Character ch : symbols ) {
					if( ch != blank ) {
						model.removeSymbol( ch );
					}
				}
				for( int i = 0 ; i < text.length ; ++ i ) {
					char ch = text[i] ;
					if( ch > ' ' ) {
						//System.out.println( "Adding symbol " + ch) ;
						model.addSymbol(ch);
					}
				}
			}} ;
		textField.addActionListener(listener);
		this.add( textField ) ;
	}
}
