package uk.ac.le.sppg.superdarn.colour;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import uk.ac.le.sppg.general.display.DecimalField;




/**
 * The Legend class extends JPanel to create a JPanel which shows a
 * colour scale. The JPanel also has a popup menu to allow the limits
 * of the scale to be changed and to change the number of colours
 * in the colour scale.
 * <p>
 * The Legend sets its preferred size to allow enough room to display 
 * the minimum and maximum limits of the scale in the current font.
 *  
 * 
 * @author Nigel Wade
 */

    
public class Legend extends JPanel implements ActionListener {

    private static final long serialVersionUID = 0x5253505047000001L;
    
    // the actual popup menu
    JPopupMenu popup;
    
    static final int SCALE_WIDTH = 15;  // width in pixels of the colour scale
    
    ColourScale colourScale;
    
    double min;
    double max;
    boolean linear;			// the scale can be linear or velocity 
    Dimension size = null;

    Limits notifier = null;
    
    FontMetrics fm;
    Dimension labelSpace;
    String maxStr;
    String minStr;
    int nColours;
    
    JFrame limitFrame;
    DecimalField minField;
    DecimalField maxField;
    JCheckBox minFixed;
    
    static final String menuLimitText = "Set limits...";
    
    Graphics g = null;
    
    /**
     * Creates a <code>Legend</code> with range from <code>min</code> to 
     * <code>max</code> and displaying
     * <code>nColours</code> distinct colours.
     * <p>
     * The <code>colourScale</code> displayed by the <code>Legend</code>
     * can be either a {@link colour.LinearScale}
     * or a {@link colour.VelocityScale}. The parameter <code>linear</code>
     * determines which. It should be set to <code>true</code> for a 
     * <code>LinearScale</code> and <code>false</code> for <code>VelocityScale</code>.
     * <p>
     * A callback object can be specified as the <code>callback</code> parameter.
     * This object will be notified via its <code>limitsChanged</code> method if
     * the user changes the limits of the legend using the popup
     * menu or the scale/limits are changed via the <code>setLinear</code>
     * or <code>setVelocity</code> methods. This object must implement the {@link colour.Legend.Limits}
     * interface.
     * If the <code>callback</code> parameter is <code>null</code> no callback
     * will be made if the limits are changed.
     * 
     * 
     * @param linear
     * specifies the type of <code>ColourScale</code>. 
     * <code>true</code> for linear or <code>false</code> for velocity.
     * @param min
     * the minimum value of the <code>Legend</code>.
     * @param max
     * the maximum value of the <code>Legend</code>.
     * @param nColours
     * the number of colours to use in the <code>colourScale</code>.
     * @param callback
     * a callback object to be notified when the limits are changed.
     * 
     */
    public Legend( boolean linear, double min, double max, int nColours, Limits callback ) {
    	
    	this.linear = linear;
    	this.min = min;
    	this.max = max;
    	createColourScale( nColours );
   	
    	this.notifier = callback;
        //notifier.limitsChanged( min, max );
        
        this.setBorder( BorderFactory.createEmptyBorder( 0, 10, 10, 10 ));
    	    	   	
    	popup = new JPopupMenu();
        
        JMenu submenu1 = new JMenu( "Colours" );

    	
    	ButtonGroup b = new ButtonGroup();
    	
    	JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("8");
    	rbMenuItem.addActionListener( this );
    	if ( nColours == 8 ) 
    	    rbMenuItem.setSelected( true );
    	b.add( rbMenuItem );
    	submenu1.add( rbMenuItem );
    
    	rbMenuItem = new JRadioButtonMenuItem("16");
    	rbMenuItem.addActionListener( this );
    	if ( nColours == 16 ) 
    	    rbMenuItem.setSelected( true );
    	b.add( rbMenuItem );
    	submenu1.add( rbMenuItem );
    	
    	rbMenuItem = new JRadioButtonMenuItem("32");
    	rbMenuItem.addActionListener( this );
    	if ( nColours == 32 ) 
    	    rbMenuItem.setSelected( true );
    	b.add( rbMenuItem );
    	submenu1.add( rbMenuItem );
    
    	rbMenuItem = new JRadioButtonMenuItem("64");
    	rbMenuItem.addActionListener( this );
    	if ( nColours == 64 ) 
    	    rbMenuItem.setSelected( true );
    	b.add( rbMenuItem );
    	submenu1.add( rbMenuItem );
    
    	rbMenuItem = new JRadioButtonMenuItem("128");
    	rbMenuItem.addActionListener( this );
    	if ( nColours == 128 ) 
    	    rbMenuItem.setSelected( true );
    	b.add( rbMenuItem );
    	submenu1.add( rbMenuItem );
    
    	rbMenuItem = new JRadioButtonMenuItem("256");
    	rbMenuItem.addActionListener( this );
    	if ( nColours == 256 ) 
    	    rbMenuItem.setSelected( true );
    	b.add( rbMenuItem );
    	submenu1.add( rbMenuItem );
    
     	popup.add( submenu1 );
        popup.add( new JSeparator() );
        
        JMenuItem limit = new JMenuItem( menuLimitText );
        limit.addActionListener( this );
        popup.add( limit );
        
    	//Add listener to components that can bring up popup menus.
        MouseListener popupListener = new PopupListener();
        this.addMouseListener(popupListener);
        
        createLimitDialog();

    }
    
    void createLimitDialog() {
        
        limitFrame = new JFrame( "Set limits" );
        
        JPanel p = new JPanel();
        GridBagLayout gb = new GridBagLayout();
        p.setLayout( gb );
        GridBagConstraints c = new GridBagConstraints();
        
        minField = new DecimalField( min, 10 );
        minField.addActionListener( this );
        minField.setEnabled(false);
        maxField = new DecimalField( max, 10 );
        maxField.addActionListener( this );
        minFixed = new JCheckBox("Set min.", false);
        minFixed.addActionListener(this);
 
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        
        JLabel ul = new JLabel( "max: " );
        gb.setConstraints( ul, c );
        p.add( ul );
        
        c.gridy = 1;
        JLabel ul2 = new JLabel( "min: " );
        gb.setConstraints( ul2, c );
        p.add( ul2 );
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridx = 1;
        c.gridy = 0;
        
        gb.setConstraints( maxField, c );
        p.add( maxField );
        
        c.gridy = 1;
        gb.setConstraints( minField, c );
        p.add( minField );
        
        c.weightx = 0.0;
        c.gridx = 2;
        gb.setConstraints( minFixed, c );
        p.add( minFixed );
        
        
        limitFrame.setContentPane( p );
        limitFrame.pack();
        
    }
    
    
    /**
     * sets the <code>colourScale</code> to a <code>LinearScale</code>
     * and new values for this objects limits.
     * <p>
     * If a callback object has been specified then its <code>limitsChanged</code>
     * method will be called with the new limits.
     * 
     * @param min
     * minimum value of the scale
     * @param max
     * maximum value of the scale
     */
    public void setLinear( double min, double max ) { 
    	linear = true; 
    	this.min = min;
    	this.max = max;
    	maxStr = String.valueOf( max );
    	minStr = String.valueOf( min );
    	createColourScale( nColours); 
        requiredWidth();
        
        minFixed.setSelected(false);
        
        if ( notifier != null )
            notifier.limitsChanged( min, max );
        
        repaint();
    }
    
    /**
     * sets the <code>colourScale</code> to a <code>VelocityScale</code>
     * and new values for this objects limits.
     * <p>
     * If a callback object has been specified then its <code>limitsChanged</code>
     * method will be called with the new limits.
     * 
     * @param min
     * minimum value of the scale
     * @param max
     * maximum value of the scale
     */
    public void setVelocity( double min, double max ) { 
    	linear = false; 
    	this.min = min;
    	this.max = max;
    	maxStr = String.valueOf( max );
    	minStr = String.valueOf( min );
    	createColourScale( nColours );
        requiredWidth();
        
        if ( notifier != null )
            notifier.limitsChanged( min, max );
        
        repaint();
    }
    
    public void setLimits(double min, double max) {
        if ( linear )
            setLinear(min, max);
        else
            setVelocity(min, max);
    }
    
    /**
     * The callback notification feature of the <code>Legend</code> is done
     * via this interface. When the limits of the <code>Legend</code> are
     * changed either by the user with the popup menu or programmatically
     * via the <code>setLinear</code> or <code>setVelocity</code> methods
     * the callback object will be notified by its <code>limitsChanged</code>
     * method
     * 
     * @author Nigel Wade
     */
    public interface Limits {
    	/**
         * Any callback object specified by the constructor or the 
         * <code>setCallback</code> method will be notified of a change
         * of limits by this method.
         * 
         * @param min
         * the new minimum value
         * @param max
         * the new maximum value
         */
        public void limitsChanged( double min, double max );
    }

    /**
     * gets the <code>colourScale</code> being used by this object.
     * @return
     * the displayed <code>colourScale</code>.
     */
    public ColourScale getColourScale() { return colourScale; }
    /**
     * gets the minimum value of the scale
     * @return
     * the minimum value of the scale
     */
    public double getMin() { return min; }
    /**
     * gets the maximum value of the scale
     * @return
     * the maximum value of the scale
     */
    public double getMax() { return max; }
    
    /**
     * calculates the space necessary to display the <code>colourScale</code>
     * and the labels for the minimum and maximum values in the current
     * font.
     * 
     * @return
     * the preferred size of the component. 
     * @see java.awt.Component#getPreferredSize()
     */
    public Dimension getPreferredSize() {
        
        Insets insets = getInsets();
        Dimension size = getSize();
        
       
        Dimension result = new Dimension( insets.left+insets.right, size.height );
        
        if ( labelSpace == null ) {
            requiredWidth();
            
            if ( labelSpace == null ) {
                return result;
            }
        }

        // calculate the size of the drawing area.
        int currentHeight = getHeight() - insets.top - insets.bottom;
        
        if ( g == null ) {
            g = this.getGraphics();
        }
        
        if ( fm == null || !g.getFont().equals( fm.getFont() ) ) {
            fm = g.getFontMetrics();
            labelSpace = this.spaceForLabel();
            if ( labelSpace == null ) {
                return result;
            }
        }
    
        result.width = insets.left + insets.right + SCALE_WIDTH + labelSpace.width;
        result.height = insets.top + insets.bottom + currentHeight;
        
        
        return result;        
    }
    
    private double requiredWidth() {
        labelSpace = spaceForLabel();
        
        if ( labelSpace == null )
            return -1.0;
        else
            return SCALE_WIDTH + labelSpace.width;
    }
    
    
    private Dimension spaceForLabel() {
    	
   	// calculate the necessary size to display the text for
    	// the max/min values.

        if ( g == null ) {
            g = getGraphics();
            
            if ( g == null ) {
                return null;
            }            
        }
            
    	
    	fm = g.getFontMetrics();
    	
    	maxStr = String.valueOf( max );
    	minStr = String.valueOf( min );
    	
    	Rectangle2D sizeMax = fm.getStringBounds( maxStr, g );
    	Rectangle2D sizeMin = fm.getStringBounds( minStr, g );
    	
    	int maxWidth = (int)Math.max( sizeMax.getWidth(), sizeMin.getWidth() );
    	int maxHeight = (int)Math.max( sizeMax.getHeight(), sizeMin.getHeight() );
    	
    	return new Dimension( maxWidth, maxHeight );
    	
    }

    private void createColourScale( int nColours ) {
    	 	
    	if ( linear )
    	    this.colourScale = new LinearScale( nColours );
    	else
    	    this.colourScale = new VelocityScaleTwo( nColours );
    	
    	this.nColours = nColours;
        
        repaint();
    }
    
    public boolean isLinear() {
        return linear;
    }
    
    /**
     * sets the callback object which will be notified of changes in
     * the limits of the scale.
     * The callback object must implement the {@link Legend.Limits Limits}
     *  interface
     * @param callback
     * an object to be notified when the limits are changed
     */
    public void setCallback( Limits callback ) {
    	this.notifier = callback;
    }
    
    // listener for right click to post the popup menu.
    class PopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }
    
        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
            	int mouseX = e.getX();
            	int mouseY = e.getY();
                popup.show(e.getComponent(),
                       mouseX, mouseY );
            }
        }
    }
    
    /**
     * Displays the popup menu which allows the user to set the number
     * of colours used by the <code>colourScale</code> or to set new
     * limts for this object.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed( ActionEvent e ) {     	
        if ( e.getSource() == maxField || e.getSource() == minField) {
            
           // event from the DecimalField to change the legend limit
            double newMax = maxField.getValue();
            double newMin = minFixed.isSelected() ? minField.getValue() : min;
            
            if ( (newMin != min || newMax != max) && notifier != null ) {
                min = newMin;
                max = newMax;
                if ( ! linear && ! minFixed.isSelected() ) {
                    min = -max;
                }
                notifier.limitsChanged( min, max );
                
                maxStr = String.valueOf( max );
                minStr = String.valueOf( min );
                
                requiredWidth();

            }
            
            limitFrame.setVisible( false );
            
            repaint();
        }
        
        else if ( e.getSource() == minFixed ) {
            minField.setEnabled(minFixed.isSelected());
        }
        
        else {
            String ac = e.getActionCommand();
            
            if ( ac.equals( menuLimitText ) ) 
                limitFrame.setVisible( true );
            else {
            	try {
            	    int n = Integer.parseInt( ac );
            	    
            	    createColourScale( n );
            	}
            	catch ( NumberFormatException ex ) {
            	    System.err.println( "bad string in colour number selection menu" );
            	}
            	
            	repaint();
            }
        }
    }

    /**
     * Draws the component colour scale and the minimum and maximum limits.
     *
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public synchronized void paintComponent( Graphics g ) {

        Graphics2D g2 = (Graphics2D) g;
        
        super.paintComponent( g );
        
	    // calculate the size of the drawing area.
        Insets insets = getInsets();
        int currentHeight = getHeight() - insets.top - insets.bottom;
    	
        super.paintComponent( g );      //clears the background
    
    	// draw a vertical colour scale
    	int scaleWidth = SCALE_WIDTH;
    	int scaleHeight = currentHeight - labelSpace.height;
    	
    	float y = insets.top + labelSpace.height;
    	// y = 0;
    	float yStep = ((float)scaleHeight-labelSpace.height) / (Byte.MAX_VALUE-Byte.MIN_VALUE+1);
    	
    	g2.setColor( Color.BLACK );
    	g2.drawString( maxStr, scaleWidth+insets.left, y );
    	
    	for ( int b=Byte.MAX_VALUE; b >= Byte.MIN_VALUE; b-- ) {
    	    g2.setColor( colourScale.colour( (byte)b ) );
    	    g2.fill( new Rectangle2D.Double( insets.left, y,
                                             scaleWidth, yStep ));
            y += yStep;
    	}
    	
    	g2.setColor( Color.BLACK );
    	g2.drawString( minStr, scaleWidth+insets.left, y+labelSpace.height );
    	
	
    }

}
