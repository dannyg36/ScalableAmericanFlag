// Flag starter code

 

/*

 * Danny Gu

 * Aiden Xia

 */

 

import java.awt.Color;

import java.awt.Graphics;

import java.awt.Graphics2D;

 

import javax.swing.JApplet;

 

public class Flag extends JApplet {

 private final int STRIPES = 13;

 

 // SCALE FACTORS (A through L)

 //

 // Note: Constants in Java should always be ALL_CAPS, even

 // if we are using single letters to represent them

 //

 // NOTE 2: Do not delete or change the names of any of the

 // variables given here

 //

 // Set the constants to exactly what is specified in the documentation

 // REMEMBER: These are scale factors.  They are not numbers of pixels.

 // You will use these and the width and height of the Applet to figure

 // out how to draw the parts of the flag (stripes, stars, field).

 private final double A = 1.0;  // Hoist (width) of flag

 private final double B = 1.9;  // Fly (length) of flag

 private final double C = 7/13.0;  // Hoist of Union

 private final double D = 0.76;  // Fly of Union

 private final double E = 0.054;  // See flag specification

 private final double F = 0.054;  // See flag specification

 private final double G = 0.063;  // See flag specification

 private final double H = 0.063;  // See flag specification

 private final double K = 0.0616;  // Diameter of star

 private final double L = 1/13.0;  // Width of stripe

 

 // You will need to set values for these in paint()

 private double flag_width;   // width of flag in pixels

 private double flag_height;  // height of flag in pixels

 private double stripe_height; // height of an individual stripe in pixels

 

 // init() will automatically be called when an applet is run

 public void init() {

  // Choice of width = 1.9 * height to start off

  // 760 : 400 is ratio of FLY : HOIST

  setSize(760, 400);

  repaint();

 }

 

 // paint() will be called every time a resizing of an applet occurs

 public void paint(Graphics g) {

	    flag_width = getWidth();   // width of applet    

	    flag_height = getHeight();      // height of applet

	   

	    // Figure out whether it is the flag_height or flag_width that is

	    // the limiting factor on how to present the flag.

	    //

	    // Applets always repaint upon resizing

	   

	    if (flag_height * B > flag_width) {  // Height too tall for length of display

	      flag_height = flag_width/B;

	    } else {                   // Length too tall for height ofdisplay

	      flag_width = flag_height * B;

	    }

	   //calculate stripe height
	    stripe_height = flag_height / 13.0;
	   

	   //draw commands
	    drawBackground(g);
	    drawStripes(g);
	    drawField(g);
	    drawStars(g);
	  }

 

 private void drawBackground(Graphics g) {
	 g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth() , getHeight() );
		g.fillRect(0, 0, getWidth() , getHeight() );
 }

 

 public void drawStripes(Graphics g) {
	 g.setColor(Color.RED);
	 for(int i = 0; i < 7; i++) {
		 g.drawRect(0, (int) (2*i*stripe_height), (int) (flag_width), (int) (stripe_height));
		 g.fillRect(0, (int) (2*i*stripe_height), (int) (flag_width), (int) (stripe_height));
	 }
	 g.setColor(Color.WHITE);
	 for(int i = 0; i < 6; i++) {
		 g.drawRect(0, (int) (2*i*stripe_height + stripe_height), (int) (flag_width), (int) (stripe_height));
		 g.fillRect(0, (int) (2*i*stripe_height + stripe_height), (int) (flag_width), (int) (stripe_height));
	 }
 }

 

 public void drawField(Graphics g) {
	 g.setColor(Color.BLUE);
		g.drawRect(0, 0, (int) (flag_width * D/B), (int) (stripe_height * 7));
		g.fillRect(0, 0, (int) (flag_width * D/B), (int) (stripe_height * 7));
 }

 

 public void drawStars(Graphics g) {
	    Star star = new Star();
	    
	    
	    double horizontalSpace = (flag_width * D) / 11.5;  
	    double verticalSpace = (stripe_height * 7) / 10.0;  

	    for (int row = 0; row < 9; row++) {
	        for (int col = 0; col < 6; col++) {
	            if (row % 2 == 0) {
	                star.draw(g, (int) ((col + 0.5) * horizontalSpace), (int) ((row + 1) * verticalSpace), flag_height * K / 2.0);
	            } else {
	                if (col < 5) {
	                    star.draw(g, (int) ((col + 1) * horizontalSpace), (int) ((row + 1) * verticalSpace), flag_height * K / 2.0);
	                }
	            }
	        }
	    }
	}


public class Star {
		private Color color;
		private final int STAR_POINTS = 10;
		private int[] polygonX = new int[STAR_POINTS];
		private int[] polygonY = new int[STAR_POINTS];
		
		public Star() {
			color = Color.WHITE;
		}
		
		public void draw(Graphics g, int centerX, int centerY, double radius) {
			/*
			 * To produce a polygon that looks like a star, a little trig...
			 * 
			 * If you draw a regular 5-pointed star with center (0,0) on a graph, 
			 * the points will be at angles 18, 90, 162, 234, and 306 degrees.
			 * The y-coord of the first point will be the radius of the star times
			 * sin18.  The star can be thought of having 5 inner points at angles
			 * 54, 126, 198, 270, and 342 degrees.  The y-coord of the point at 54
			 * degrees is the same as the one at 18 degrees and is Rsin54 where R
			 * is the radius of inner points.  Since Rsin54 = rsin18, and since
			 * r, sin18, and sin54 are known, we have R = rsin18/sin54.  We can use
			 * this to do some nice computations for coordinates of points around
			 * the polygon; five on the outside and five on the inside.
			 */
			
			double innerRadius = radius*Math.sin(Math.toRadians(18)/Math.sin(Math.toRadians(54)));

			// Note that (i-18)/36 will be 0, 2, 4, 6 8
			for (int i = 18; i < 360; i += 72) {
				polygonX[(i-18)/36] = centerX + (int) (radius * Math.cos(Math.toRadians(i)));
				polygonY[(i-18)/36] = centerY - (int) (radius * Math.sin(Math.toRadians(i))); 
			}

			// Here (i-18)/36 will be 1, 3, 5, 7, 9
			for (int i = 54; i < 360; i += 72) {
				polygonX[(i-18)/36] = centerX + (int) (innerRadius * Math.cos(Math.toRadians(i)));
				polygonY[(i-18)/36] = centerY - (int) (innerRadius * Math.sin(Math.toRadians(i))); 
			}

			Color c = g.getColor();
			g.setColor(color);
			g.fillPolygon(polygonX, polygonY, STAR_POINTS);
			g.setColor(c);
		}

	}
}