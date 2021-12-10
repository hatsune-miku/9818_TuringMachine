package gui;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public interface ViewStrategy {
	
	Point2D worldToView( double viewWidth, double viewHeight,
            			 Rectangle2D bounds,
            			 double xw, double yw ) ;
	
	Point2D viewToWorld(
			double viewWidth, double viewHeight,
			Rectangle2D bounds,
            double xv, double yv ) ;
}
