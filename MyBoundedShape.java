/*
 * Description: MyBoundedShape class is extended to MyShape class, but this class is also the parent class for 
 *              MyRectangle and MyOval and MyRoundRectangle. 
 * 
 * Name: Alpha Mai
 * 
 * Date: May 25, 2015
 */

import java.awt.Color;

abstract class MyBoundedShape extends MyShape
{
    private double width;
    private double height;
    private boolean filled;
    
    // Default Constructor
    public MyBoundedShape()
    {
        super();
        filled = false;
    }
    
    // Constructor with input values
    public MyBoundedShape(double x1, double y1, double x2, double y2, Color color1, Color color2, boolean filled, boolean gradient, boolean dashed, float strokeWidth, float dashLength)
    {
        super (x1, y1, x2, y2, color1, color2, gradient, dashed, strokeWidth, dashLength);
        this.filled = filled;
    }
    
    //return the width
    public double getWidth()
    {
        width = Math.abs(super.getX2()-super.getX1());
        return width;
    }
    
    //return the height
    public double getHeight()
    {
        height = Math.abs(super.getY2()-super.getY1());
        return height;
    }
    
    //calculate and return the value of the upper X 
    public double getUpperLeftX()
    {
        if (super.getX1() <= super.getX2())
        {
            return super.getX1();
        }
        return super.getX2();
    }
    
    //calculate and return the value of the upper Y
    public double getUpperLeftY()
    {
        if (super.getY1() <= super.getY2())
        {
            return super.getY1();
        }
        return super.getY2();
    }
   
    //return the value of filled
    public boolean getFilled()
    {
        return filled;
    }
    
    //set the value of filled
    public void setFilled(boolean filled)
    {
        this.filled = filled;
    }
}