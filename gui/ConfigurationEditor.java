package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import turmach.model.Configuration;

public class ConfigurationEditor extends JPanel {
	private static final long serialVersionUID = 8140725010501175828L;
	private ConfigurationToolBar toolBar ;
	private ConfigurationView view ;
	
	ConfigurationEditor( Configuration configuration ) {
		this.view = new ConfigurationView( configuration ) ;
		this.toolBar = new ConfigurationToolBar(configuration) ;
		this.setLayout( new BorderLayout() );
		this.add( BorderLayout.NORTH, toolBar) ;
		this.add( BorderLayout.CENTER, view ) ;
	}

	
	public void refresh() {
		view.refresh() ;
		toolBar.refresh() ;
	}

}
