package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import turmach.model.Configuration; 

public class ConfigurationView extends JPanel
{
	
	private static final long serialVersionUID = 237882347038933995L;
	private Configuration model ;
	private ViewStrategy worldToViewMap;
	private Rectangle2D.Double  worldBounds
	    = new Rectangle2D.Double( -5.5, -5.0, +18.0, 10.0) ;
	
	ConfigurationView( Configuration model ) {
		this( model, new BirdsEyeViewStrategy() ) ;
	}
	
	ConfigurationView( Configuration model, ViewStrategy worldToViewMap ) {
		this.model = model ;
		this.worldToViewMap = worldToViewMap ;
		setPreferredSize(new Dimension(300, 200));
		
	}
	
	private double displayedPosition = 0.0 ;
	private double timeInterval = 0.03 ;
	private double velocity = 0.1 ;
	private int goalPosition = 0 ;
	
	ActionListener animiationListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(    Math.abs(displayedPosition-goalPosition)
				<= Math.abs( velocity ) ) {
				displayedPosition = goalPosition ;
				timer.stop() ;
			} else {
				displayedPosition += velocity ;
				//System.out.println( "diplayedPosition = " + displayedPosition) ;
			}
			repaint() ;
		}
		
	} ;
	
	javax.swing.Timer timer
    = new javax.swing.Timer((int)(timeInterval*1000), animiationListener) ;
	
	private void animate() {
		timer.start();
	}
	
	void refresh() {
		goalPosition = model.getCurrentPosition() ;
		if( displayedPosition != goalPosition ) {
			//System.out.println( "diplayedPosition != goalPosition" ) ;
			//System.out.println( "diplayedPosition = " + displayedPosition) ;
			//System.out.println( "goalPosition = " + goalPosition) ;
			velocity = (goalPosition - displayedPosition) / (0.5 / timeInterval) ;
			//System.out.println( "velocity = " + velocity) ;
			animate() ;
		}
		this.repaint() ;
	}
	
    @Override protected void paintComponent( Graphics g ) {
    	super.paintComponent(g) ;
    	//System.out.println( "Drawing the configuration" ) ;
    	int width = getWidth(), height = getHeight() ;
    	
    	// Change the bounds based on the position
    	double marginWidth = 4.5 ;
    	if( displayedPosition - marginWidth < worldBounds.getMinX()  ) {
    		double newX = displayedPosition - marginWidth ;
    		worldBounds = new Rectangle2D.Double( newX,
			    				worldBounds.getY(), 
			    				worldBounds.getWidth(),
			    				worldBounds.getHeight() ) ;
    	} else if( displayedPosition + marginWidth > worldBounds.getMaxX() ) {
    		double newX = displayedPosition + marginWidth - worldBounds.getWidth() ;
    		worldBounds = new Rectangle2D.Double( newX,
			    				worldBounds.getY(),
			    				worldBounds.getWidth(),
			    				worldBounds.getHeight() ) ;
    	}

    	//System.out.println( "worldBounds is " + worldBounds ) ;
    	
    	Point2D wTL = viewToWorld( width, height, worldBounds, 0.0, 0.0 ) ;
    	Point2D wTR = viewToWorld( width, height, worldBounds, width, 0.0 ) ;
    	
    	Point2D vWorldOrigin = worldToView( width, height, worldBounds, 0.0, 0.0 ) ;
    	//System.out.println( "vWorldOrigin is " + vWorldOrigin ) ;
    	Point2D vWorldOneOne = worldToView( width, height, worldBounds, 1.0, 1.0 ) ;
    	// Two horizontal lines for the tape
    	int vTopLineY = (int) vWorldOrigin.getY() ;
    	int vBottomLineY = (int) vWorldOneOne.getY() ;
    	int vBaseLine = vBottomLineY + (vTopLineY - vBottomLineY) / 4 ;
    	int vCellHalfWidth = (int) (vWorldOneOne.getX() - vWorldOrigin.getX()) / 2;
    	
    	g.drawLine( 0, vTopLineY, width, vTopLineY ) ;
    	g.drawLine( 0, vBottomLineY, width, vBottomLineY ) ;
    	
    	Font f = new Font(Font.MONOSPACED, Font.BOLD, 20 ) ;
    	g.setFont( f );
    	int vHalfCharWidth = g.getFontMetrics().getMaxAdvance() / 2;
    	// Vertical Lines and symbols.
    	int leftMostSquare = (int) wTL.getX() ;
    	int rightMostSquare = (int) wTR.getX() ;
    	for( int i = leftMostSquare ; i <= rightMostSquare ; ++i ) {
    		int vx = (int) worldToView( width, height, worldBounds, i, 0.0 ).getX() ;
    		g.drawLine( vx, vBottomLineY, vx, vTopLineY ) ;
    		String symbol = Character.toString( model.getTapeCellSymbol( i ) ) ;
    		g.drawString(symbol, vx+vCellHalfWidth-vHalfCharWidth, vBaseLine ) ;
    	}
    	
    	// Draw the current state
    	Point2D vStatePosition = worldToView( width, height, worldBounds, displayedPosition, 3.0 ) ;
    	String state = model.getCurrentState() ;
    	int vHalfStateWidth = g.getFontMetrics().stringWidth(state) / 2 ;
    	int vStateHeight = g.getFontMetrics().getAscent() ;
    	int vMiddleX = (int)vStatePosition.getX()+vCellHalfWidth ;
    	g.drawString( state,
    			vMiddleX-vHalfStateWidth,
    			(int)vStatePosition.getY()+vStateHeight );
    	// Draw an arrow
    	g.drawLine(vMiddleX, (int)vStatePosition.getY(),
    			vMiddleX, (int)vWorldOneOne.getY() ) ;
    	g.drawLine( vMiddleX, (int)vWorldOneOne.getY(),
    			vMiddleX-vHalfStateWidth, (int)vWorldOneOne.getY()+vHalfStateWidth);
    	g.drawLine( vMiddleX, (int)vWorldOneOne.getY(),
    			vMiddleX+vHalfStateWidth, (int)vWorldOneOne.getY()+vHalfStateWidth);
    }
    
    private Point2D worldToView( double width, double height, Rectangle2D bounds, double x, double y ) {
        return worldToViewMap.worldToView( width, height, bounds, x, y )  ; }
    
    private Point2D viewToWorld( double width, double height, Rectangle2D bounds, double x, double y ) {
        return worldToViewMap.viewToWorld( width, height, bounds, x, y )  ; }
}
