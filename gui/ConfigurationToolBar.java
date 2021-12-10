package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import turmach.model.Configuration;

public class ConfigurationToolBar extends JToolBar {

	private static final long serialVersionUID = -143838180898711557L;
	private Configuration model;
	private List<Character> cachedSymbolList = null ;
	private JButton advanceButton;
	private JTextField currentStateField;

	ConfigurationToolBar( Configuration configuration ) {
		this.model = configuration ;
		refreshButtons( this ) ;
	}
	
    void refresh() {
    	List<Character> symbolList = model.getSymbolList() ;
    	if( !symbolList.equals( cachedSymbolList) ) {
    		cachedSymbolList = symbolList ;
    		refreshButtons( this ) ;
    	}
    	advanceButton.setEnabled(model.canAdvance());
    	this.currentStateField.setText( model.getCurrentState() ) ;
    	repaint() ;
    }
	

	
	private void refreshButtons( JToolBar toolBar ) {
		// Remove all buttons 
		while( toolBar.getComponentCount() > 0 )
			toolBar.remove( toolBar.getComponent(0) );
		
		JButton button ;
		
		// Go to the initial state
		button = new JButton( "Reset" ) ;
		button.addActionListener(a -> {
			reset();
			refresh();
		});
		toolBar.add( button ) ;
		
		// Move left to next blank
		button = new JButton( "<<" ) ;
		button.addActionListener(a -> {
			moveLeftMost();
			refresh();
		});
		toolBar.add( button ) ;
		
		// Move left
		button = new JButton( "<" ) ;
		button.addActionListener(a -> {
			moveLeft();
			refresh();
		});
		toolBar.add( button ) ;

		
		// For each symbol in the machine. Make one button
		java.util.List<Character> symbols = model.getSymbolList() ;
		for( Character symbol : symbols ) {
			button = new JButton( Character.toString( symbol ) )  ;
			button.addActionListener(a -> {
				changeTapeTo(symbol);
				refresh();
			});
			toolBar.add( button ) ;
		}

		// Move Right
		button = new JButton( ">" ) ;
		button.addActionListener(a -> {
			moveRight();
			refresh();
		});
		toolBar.add( button ) ;

		
		// Move right to next blank
		button = new JButton( ">>" ) ;
		button.addActionListener(a -> {
			moveRightMost();
			refresh();
		});
		toolBar.add( button ) ;
		
		JLabel label = new JLabel( "CurrentState: " ) ;
		toolBar.add( label ) ;
		
		this.currentStateField = new JTextField(10) ;
		this.currentStateField.addActionListener(a -> {
			model.setCurrentState(this.currentStateField.getText());
			refresh();
		});
		toolBar.add( this.currentStateField ) ;
		this.currentStateField.setText( model.getCurrentState() );
		
		this.advanceButton = new JButton( "Advance" ) ;
		this.advanceButton.addActionListener(a -> {
			model.advance();
			refresh();
		});
		toolBar.add( this.advanceButton ) ;
	}
	
	private void moveLeft() {
		int p = model.getCurrentPosition() ;
		model.setPosition(p-1) ;
	}

	private void moveLeftMost() {
		model.setPosition(model.getLeftEnd());
	}
	
	private void moveRight() {
		int p = model.getCurrentPosition() ;
		model.setPosition(p+1) ;
	}

	private void moveRightMost() {
		model.setPosition(model.getRightEnd());
	}
	
	private void changeTapeTo( char symbol ) {
		int p = model.getCurrentPosition() ;
		model.setTapeCell(p, symbol);
	}

	private void reset() {
		model.setCurrentState(model.getInitialState());
	}
}
