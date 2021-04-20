package hw4;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * The ClockIcon class
 * @author Stephanie Cheng
 *
 */
public class ClockIcon implements Icon
{
	int width;
	int height;
	double centerX;
	double centerY;
	double radius;
	
	/**
	 * The constructor for the clock icon
	 * @param theWidth the width of the clock
	 */
	public ClockIcon(int theWidth) 
	{
		this.width = theWidth;
		this.height = theWidth;
	}

	/**
	 * Paints the icon
	 * @param c the component
	 * @param g the graphics
	 * @param x the x-coordinate of the clock
	 * @param y the y-coordinate of the clock
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) 
	{
		Graphics2D g2 = (Graphics2D) g;
		centerX = x + width/2;
		centerY = y + width/2;
		radius = width/2;
		
		Ellipse2D.Double clockBase = new Ellipse2D.Double(x, y, width, height);
		g2.setColor(Color.DARK_GRAY);
		g2.fill(clockBase);
		
		
		//hand length with respect to width
		final double H = 0.4;
		final double M = 0.6;
		final double S = 0.8;
		final double N = 0.9;
		
		
		
		g2.setColor(Color.WHITE);
		g2.drawString("12", (float) endX(N, 0) -6
				, (float) endY(N, 0)+6);
		g2.drawString("3", (float) endX(N, 15) -2*4, (float) endY(N,15));
		g2.drawString("6", (float) endX(N,30) -2*4, (float) endY(N,30) +2*4);
		g2.drawString("9", (float) endX(N,45) -1*4, (float) endY(N,45));
		
		GregorianCalendar time = new GregorianCalendar();
		int hours = time.get(Calendar.HOUR);
		
		hours = (hours % 12) * 5; //convert to ticks
		int minutes = time.get(Calendar.MINUTE);
		int seconds = time.get(Calendar.SECOND);
		
		hours = hours + (int) (5 * minutes / 60.0); //add the fraction of the minutes
		
		Line2D.Double hourHand = new Line2D.Double(centerX, centerY, endX(0.4,hours), endY(0.4,hours));
		Line2D.Double minuteHand = new Line2D.Double(centerX, centerY, endX(0.6,minutes), endY(0.6,minutes));
		Line2D.Double secondHand = new Line2D.Double(centerX, centerY, endX(0.8,seconds), endY(0.8,seconds));
		
		g2.setColor(Color.PINK);
		g2.draw(hourHand);
		g2.setColor(Color.RED);
		g2.draw(minuteHand);
		g2.setColor(Color.WHITE);
		g2.draw(secondHand);
		
	}
	
	/**
	 * Finds the end point x-coordinate for the clock hands
	 * @param ratio the scale of the hand to the clock radius
	 * @param ticks the amount of lines of the clock it is at
	 * @return x the x-coordinate
	 */
	public double endX(double ratio, int ticks)
	{
		double lengthHand = ratio * radius;
		double radians = Math.PI * ticks / 30;
		double x = centerX + lengthHand * Math.sin(radians);
		return x;
	}
	
	/**
	 * Finds the end point y-coordinate for the clock hands
	 * @param ratio ratio the scale of the hand to the clock radius
	 * @param ticks ticks the amount of lines of the clock it is at
	 * @return y the y-coordinate
	 */
	public double endY(double ratio, int ticks)
	{
		double lengthHand = ratio * radius;
		double radians = Math.PI * ticks / 30;
		double y = centerY - lengthHand * Math.cos(radians);
		return y;
	}

	/**
	 * Gets the width of the clock
	 */
	public int getIconWidth() 
	{
		return width;
	}

	/**
	 * Gets the height of the clock
	 */
	public int getIconHeight() 
	{
		return height;
	}

}