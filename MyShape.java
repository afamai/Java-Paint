/*
 * Description: MyShape class contains most of the methods and vairables for any basic shapes. Meanig that this class
 *              can be used as a base class for any type of shapes.
 * 
 * Name: Alpha Mai
 * 
 * Date: May 25, 2014
 */

import java.awt.Color;
import java.awt.Graphics;
abstract class MyShape
{
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private Color color1;
    private Color color2;
    private float strokeWidth;
    private float dashLength;
    private boolean gradient;
    private boolean dashed;
    
    //default constructor
    public MyShape()
    {
        color1 = new Color(0,0,0);
        color2 = new Color(0,0,0);
        strokeWidth = 1.0f;
        dashLength = 1.0f;
        gradient = false;
        dashed = false;
        x1 = 0;
        y1 = 0;  
        x2 = 0;
        y2 = 0;
    }
    
    //constructor with input values
    public MyShape(double x1, double y1, double x2, double y2, Color color1, Color color2, boolean gradient, boolean dashed, float strokeWidth, float dashLength)
    {
        this.color1 = color1;
        this.color2 = color2;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2; 
        this.strokeWidth = strokeWidth;
        this.dashLength = dashLength;
        this.gradient = gradient;
        this.dashed = dashed;
    }
    public void setX1(double x1)
    {
        this.x1 = x1;
    }
    public void setY1(double y1)
    {
        this.y1 = y1;
    }
    public void setX2(double x2)
    {
        this.x2 = x2;
    }
    public void setY2(double y2)
    {
        this.y2 = y2;
    }
    public void setColor1(Color color)
    {
        this.color1 = color;
    }
    public void setColor2(Color color)
    {
        this.color2 = color;
    }
    public void setGradient(boolean gradient)
    {
        this.gradient = gradient;
    }
    public void setDashed(boolean dashed)
    {
        this.dashed = dashed;
    }
    public void setStrokeWidth(float strokeWidth)
    {
        this.strokeWidth = strokeWidth;
    }
    public void setDashLength(float dashLength)
    {
        this.dashLength = dashLength;
    }
    public double getX1()
    {
        return x1;
    }
    public double getY1()
    {
        return y1;
    }
    public double getX2()
    {
        return x2;
    }
    public double getY2()
    {
        return y2;
    }
    public Color getColor1()
    {
        return color1;
    }
    public Color getColor2()
    {
        return color2;
    }
    public boolean getGradient()
    {
        return gradient;
    }
    public boolean getDashed()
    {
        return dashed;
    }
    public float getStrokeWidth()
    {
        return strokeWidth;
    }
    public float getDashLength()
    {
        return dashLength;
    }
    public abstract void draw(Graphics g);
}