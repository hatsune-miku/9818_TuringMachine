package gui;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.* ;

import turmach.model.TuringMachine;
import turmach.model.TuringMachineImpl;
import turmach.model.Configuration ;
import turmach.model.ConfigurationImpl;

public class TuringMachineGUI extends JFrame {

	private static final long serialVersionUID = -481727961913562341L;

	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch(Exception e) {
        	e.printStackTrace();
        }

		TuringMachineGUI frame = new TuringMachineGUI();


        //Centre the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }

	private TuringMachineEditor topPanel ;
	private ConfigurationEditor bottomPanel;
	private Configuration model;
	
    //Construct the frame
    public TuringMachineGUI() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {

        	this.setTitle("Turing Machine Emulator");
        	JMenu fileMenu = new JMenu("File");
            JMenuItem exitMenuItem = new JMenuItem("Exit");
            exitMenuItem.addActionListener(new ActionListener()  {
                public void actionPerformed(ActionEvent e) {
                	System.exit(0);
                }
            });
            JMenu helpMenu = new JMenu("Help");
            JMenuItem aboutMenuItem = new JMenuItem("About");
            aboutMenuItem.addActionListener(new ActionListener()  {
                public void actionPerformed(ActionEvent e) {
                	JOptionPane.showMessageDialog(TuringMachineGUI.this,
                			"Turing Machine Emulator\n"
                			+"(c) Theodore Norvell\n"
                			+"Licence is granted to modify and redistribute\n"
                			+"this program in creative ways for fun and education.");
                }
            });
            JMenuBar menuBar = new JMenuBar();
            fileMenu.add(exitMenuItem);
            helpMenu.add(aboutMenuItem);
            menuBar.add(fileMenu);
            menuBar.add(helpMenu);
            this.setJMenuBar(menuBar);
            
            TuringMachine tm = new TuringMachineImpl() ;
            this.model = new ConfigurationImpl(tm) ;
            
            this.topPanel = new TuringMachineEditor( model ) ;
            
            this.bottomPanel = new ConfigurationEditor( model ) ;
            
            Configuration.ConfigurationObserver observer = 
            		new Configuration.ConfigurationObserver() {

						@Override
						public void notifyChangeToConfiguration(Configuration config) {
							//System.out.println("Change noted.") ;
							refresh() ;
						}
            } ;
            
            model.addConfigurationObserver(observer);
            
            JSplitPane splitPane = new JSplitPane(
            		JSplitPane.VERTICAL_SPLIT,
            		topPanel,
            		bottomPanel) ;
            
            this.getContentPane().add( splitPane ) ;
        	
            setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
            this.setSize( 300, 400 );
            this.pack() ;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    void refresh() {
    	topPanel.refresh() ;
    	bottomPanel.refresh() ;
    }
}
