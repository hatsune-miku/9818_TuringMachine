package gui;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class BirdsEyeViewStrategy implements ViewStrategy {
	
	
	// Invariant: cachedBoundary == null
	//         or     worldLeft-worldRight >= 10.0
	//            and worldTop-worldBottom >= 10.0
	//            and viewWidth >= 1.0
	//            and viewHeight >= 1.0
	//            and v2wStretch = max( (worldRight - worldLeft) / viewWidth,
	//                                  (worldTop - worldBottom) / viewHeight)
	//            and w2vStretch = min( viewWidth / (worldRight - worldLeft),
	//                                  viewHeigth / (worldTop - worldBottom) )
	
	private double viewWidth ;
	private double viewHeight ;
	private double v2wStretch ;
	private double w2vStretch ;
	private Rectangle2D cachedBounds = null ;
	
	private void updateTransform( Rectangle2D bounds, double width, double height ) {
		if( bounds != cachedBounds || width != viewWidth || height != viewHeight ) {
			cachedBounds = bounds ;
			if( width < 1) viewWidth = 1 ;
			else viewWidth = width ;
			if( height < 1 ) viewHeight = 1 ;
			else viewHeight = height ;
			double hStretch = bounds.getWidth() / viewWidth ;
			double vStretch = bounds.getHeight() / viewHeight ;
			v2wStretch = Math.max(hStretch, vStretch) ;
			w2vStretch = 1.0 / v2wStretch ;
			//System.out.println("viewWidth " + viewWidth ) ;
			//System.out.println("viewHeight " + viewHeight ) ;
			//System.out.println("v2wStretch " + v2wStretch ) ;
			//System.out.println("w2vStretch " + w2vStretch ) ;
		}
	}
    
    public Point2D worldToView( double width, double height,
    						    Rectangle2D bounds,
    		                    double xw, double yw )
    {
    	updateTransform( bounds, width, height ) ;
        double x1 = xw - bounds.getX() ;
        double y1 = yw - bounds.getY() ;
        double xv = x1 * w2vStretch ;
        double yv = y1 * w2vStretch ;
        return new Point2D.Double( xv, yv ) ; }

    
    public Point2D viewToWorld(
    		double width, double height,
    		Rectangle2D bounds,
            double xv, double yv )
    {
    	updateTransform( bounds, width, height ) ;
        double x1 = xv * v2wStretch ;
    	double y1 = yv * v2wStretch ;
    	double xw = x1 + bounds.getX() ;
    	double yw = bounds.getY() + y1 ;
    	//System.out.println( "View to world ("+xv+", "+yv+") -> ("+x1+", "+y1+") -> ("+xw+", "+yw+")" ) ;
        return new Point2D.Double( xw, yw ) ; }
}