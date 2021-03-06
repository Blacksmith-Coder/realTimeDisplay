package uk.ac.le.sppg.coords.proj;


import java.awt.geom.Point2D;

import uk.ac.le.sppg.coords.CoordConstants;
import uk.ac.le.sppg.coords.Geographic;

/**
 * Provides a Stereographic projection.
 *
 * Equations for the projection were obtained from Mathworld at
 * Wolfram Research (http://mathworld.wolfram.com/topics/MapProjections.html)
 * 
 * @author Nigel Wade
 */
public class Stereographic extends Projection implements CoordConstants {

    
    /**
     * Creates a <code>Projection</code> which will project 
     * <code>Geographic</code> locations onto a plane surface.
     * <p>
     * The <code>Geographic</code> location of the centre of 
     * the projection is set by <code>centre</code> and a scale
     * factor for zooming the projection is set by <code>scale</code>.
     * @param scale
     * the scale factor for the projection.
     * 1.0 is no scaling. Larger values zoom in, smaller values zoom out.
     * @param centre
     * the <code>Geographic</code> location of the centre
     */
    public Stereographic( double scale, Geographic centre ) {
        setCentre( centre );
        this.scale = scale;
        type = ProjectionType.Stereographic;
    }

    public void setScale( double scale ) { 
        this.scale = scale; 
    }
    
    public Point2D geoToPoint( Geographic geo ) {
        double p = geo.latRadians;
        double p1 = centre.latRadians;
        double l = geo.lonRadians;
        double l0 = centre.lonRadians;
        
        double z = Math.sin(p1)*Math.sin(p) + Math.cos(p1)*Math.cos(p)*Math.cos(l - l0);
        double k = 2 * scale / (1 + z);

        //double x = (1.0 + k * Math.cos( geo.latRadians ) *
        //       Math.sin( geo.lonRadians - centre.lonRadians )) / 2.0;
        double x = k * Math.cos(p) * Math.sin(l - l0);

        //double y = (1.0 - k * ( Math.cos( centre.latRadians ) * Math.sin( geo.latRadians ) -
        //      Math.sin( centre.latRadians ) * Math.cos( geo.latRadians ) *
        //       Math.cos( geo.lonRadians - centre.lonRadians ) )) / 2.0;

        double y = k * (Math.cos(p1)*Math.sin(p) - Math.sin(p1)*Math.cos(p)*Math.cos(l-l0));
        //x = x / 2 + 0.5;
        //y = -y / 2 + 0.5;
        
        return new Point2D.Double( x, y );
    }
    
    public Geographic pointToGeo( Point2D point ) {
        double x = point.getX();
        double y = point.getY();
        double p1 = centre.latRadians;
        double l0 = centre.lonRadians;

        //x = 2*(x - 0.5);
        //y = -2*(y - 0.5);
        
        double rho = Math.sqrt( x*x + y*y);
        double c = 2.0 * Math.atan2(rho, (2 * scale));
        
        double lat = Math.asin( Math.cos(c)*Math.sin(p1)+y*Math.sin(c)*Math.cos(p1)/rho);
        double lon = l0 + Math.atan2(x*Math.sin(c), (rho*Math.cos(p1)*Math.cos(c)-y*Math.sin(p1)*Math.sin(c)));
        
        return new Geographic( Math.toDegrees(lat), Math.toDegrees(lon), 0.0 );
    }

    public void setCentre( Geographic centre ) {
        this.centre = centre;
    }

    public Projection copy() {
        Stereographic result = new Stereographic( this.scale, this.centre );
        return result;
    }

}
