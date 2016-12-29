/*
 * Description: MyLine class contain two constructors and a specific draw method for a line.
 * 
 * Name: Alpha Mai
 * 
 * Date: May 25, 2015
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.GradientPaint;
import java.awt.BasicStroke;
public class MyLine extends MyShape 
{
    // Default Constructor
    public MyLine()
    {
        super();
    }
    // constructor with input values
    public MyLine( double x1, double y1, double x2, double y2, Color color1, Color color2, boolean gradient, boolean dashed, float strokeWidth, float dashLength )
    {
        super (x1, y1, x2, y2, color1, color2, gradient, dashed, strokeWidth, dashLength);
    } // end MyLine constructor
     
    // Actually draws the line
    public void draw( Graphics g )
    {
        Graphics2D g2 = (Graphics2D)g;
        if(super.getDashed())
        {
            float[] dash = {super.getDashLength()};
            g2.setStroke(new BasicStroke(super.getStrokeWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,10.0f, dash , 0.0f));
        }
        else
        {
            float[] dash = {1.0f};
            g2.setStroke(new BasicStroke(super.getStrokeWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,10.0f, dash , 0.0f));
        }
        
        if (super.getGradient())
        {
            g2.setPaint(new GradientPaint((float)super.getX1(), (float)super.getY1(), super.getColor1(), (float)super.getX2(), (float)super.getY2(), super.getColor2()));
        }
        else
        {
            g2.setColor( super.getColor1() );
        }
        
        g2.draw(new Line2D.Double(super.getX1(), super.getY1(), super.getX2(), super.getY2()));
    } // end method draw
} // end class MyLine