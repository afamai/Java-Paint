/*
 * Description: The DrawPanel class contains methods for creating and drawing shapes onto the JPanel
 * 
 * Name: Alpha Mai
 * 
 * Date: May 25, 2015
 */

//imports
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import java.io.File;

public class DrawPanel extends JPanel
{
    //constants
    public static enum Type { LINE, RECTANGLE, OVAL, ROUNDRECT };
    // Instance Variables
    private Random randomNumbers = new Random();  
    private DynamicStack<MyShape> shapeObjects = new DynamicStack<MyShape>();
    private DynamicStack<MyShape> clearedObjects = new DynamicStack<MyShape>();
    private Type currentShapeType;
    private boolean currentShapeFilled;
    private boolean currentShapeGradient;
    private boolean currentShapeDashed;
    private Color currentShapeColor1;
    private Color currentShapeColor2;
    private float currentShapeStrokeWidth;
    private float currentShapeDashedLength;
    private MyShape currentShapeObject;
    private JLabel statusLabel;
    private MyShape shape;
    private boolean undo = false;
    private BufferedImage openImage;
    
    private MyLine a = new MyLine();
    
    // constructor for creating a blank drawing panel
    public DrawPanel(JLabel label)
    {
        setDefaults(label);
    } // end DrawPanel contructor
    
    // constructor with the desire image file to open with.
    public DrawPanel(JLabel label, String fileName)
    {
        setDefaults(label);
        try
        {
            openImage = ImageIO.read(new File(fileName));
        }
        catch(Exception e)
        {
        }
        repaint();
    }
    
    //method for setting default settings for the drawing panel.
    public void setDefaults(JLabel label)
    {
        //set default settings
        setBackground( Color.WHITE );
        setLayout(new BorderLayout());
        
        statusLabel = label;
        
        MouseHandler handler = new MouseHandler(); 
        addMouseListener( handler ); 
        addMouseMotionListener( handler ); 
    }
    public void clearLastShape()
    {
        //wil| not undo hf thepe is no shape in the stack
        if (!shapeObjects.isEmpty())
        {
            clearedObjects.push(shapeObjects.pop());
            undo = true; 
        }
        repaint();
    }
    public void redoLastShape()
    {
        if (!clearedObjects.isEmpty())
        {
            shapeObjects.push(clearedObjects.pop());
        }
        repaint();
    }
    public void clearDrawing()
    {
        shapeObjects.makeEmpty(); 
        clearedObjects.makeEmpty();
        repaint(); 
    }
    /*======================================================
    Mutator Methods
    ======================================================*/
    public void setCurrentShapeType(Type type)
    {
        currentShapeType = type;
    }
    public void setCurrentShapeColor1(Color color)
    {
        currentShapeColor1 = color;
    }
    public void setCurrentShapeColor2(Color color)
    {
        currentShapeColor2 = color;
    }
    public void setCurrentShapeGradient(boolean gradient)
    {
        currentShapeGradient = gradient;
    }
    public void setCurrentShapeDashed(boolean dashed)
    {
        currentShapeDashed = dashed;
    }
    public void setCurrentShapeDashLength(float length)
    {
        currentShapeDashedLength = length;
    }
    public void setCurrentShapeStrokeWidth(float width)
    {
        currentShapeStrokeWidth = width;
    }
    public void setCurrentShapeFilled(boolean filled)
    {
        currentShapeFilled = filled;
    }
    /*=====================================================
    Accessor Methods
    =====================================================*/
    public Color getCurrentShapeColor1()
    {
        return currentShapeColor1;
    }
    public Color getCurrentShapeColor2()
    {
        return currentShapeColor2;
    }
    
    // for each shape in the shape array it will call out each shapes draw() method
    public void paintComponent( Graphics g )
    {
        
        super.paintComponent( g );
        
        //if loading a image, will draw the image loaded onto the panel
        g.drawImage(openImage, 0, 0, null);
        
        for (int counter = shapeObjects.size()-1; counter >= 0; counter--)
        {
            shape = shapeObjects.elementAt(counter);
            shape.draw(g);
        } 
        if (currentShapeObject != null)
        {
            currentShapeObject.draw(g);
        }
        
    }
    
    private class MouseHandler extends MouseAdapter
    { 
        private boolean buttonClicked = false;
        // handle event when mouse pressed
        public void mousePressed( MouseEvent event )
        {
            //make sure the user only clicks on the left button
            if (event.getButton() == MouseEvent.BUTTON1)
            {
                buttonClicked = true;
            }
            else 
            {
                buttonClicked = false;
            }
            if (buttonClicked)
            {
                if (currentShapeType == Type.LINE)
                {
                    currentShapeObject = new MyLine((double)event.getX(), (double)event.getY(), (double)event.getX(), (double)event.getY(),currentShapeColor1, currentShapeColor2, 
                                                    currentShapeGradient, currentShapeDashed, currentShapeStrokeWidth, currentShapeDashedLength);
                }
                else if (currentShapeType == Type.RECTANGLE)
                {
                    currentShapeObject = new MyRectangle((double)event.getX(), (double)event.getY(), (double)event.getX(), (double)event.getY(),currentShapeColor1, 
                                                         currentShapeColor2,currentShapeFilled, currentShapeGradient, currentShapeDashed, currentShapeStrokeWidth, currentShapeDashedLength);
                }
                else if(currentShapeType ==  Type.OVAL)
                {
                    currentShapeObject = new MyOval((double)event.getX(), (double)event.getY(), (double)event.getX(), (double)event.getY(),currentShapeColor1, 
                                                         currentShapeColor2,currentShapeFilled, currentShapeGradient, currentShapeDashed, currentShapeStrokeWidth, currentShapeDashedLength);
                }
                else if(currentShapeType ==  Type.ROUNDRECT)
                {
                    currentShapeObject = new MyRoundRectangle((double)event.getX(), (double)event.getY(), (double)event.getX(), (double)event.getY(),currentShapeColor1, 
                                                         currentShapeColor2,currentShapeFilled, currentShapeGradient, currentShapeDashed, currentShapeStrokeWidth, currentShapeDashedLength);
                }
                statusLabel.setText( String.format( "Presced at [%d, %d]", event.getX(), event.getY() ) );
            }
        } // end method MousePressed
        
        
        // handle event when user drag  the mouse with button pressed
        public void mouseDragged( MouseEvent event )
        {
            if (buttonClicked)
            {
                currentShapeObject.setX2((double)event.getX());
                currentShapeObject.setY2((double)event.getY());
                repaint();
                statusLabel.setText( String.format( "Dragged at [%d, %d]", event.getX(), event.getY() ) );
            }
        } //end method MouseDragged
        
        // handle event when mouse button is released after dragging
        public void mouseReleased( MouseEvent event)
        {
            if (buttonClicked)
            {
                if (undo)
                {
                    clearedObjects.makeEmpty();
                    undo = false;
                } 
                shapeObjects.push(currentShapeObject);
                currentShapeObject = null; 
                repaint(); 
                statusLabel.setText(String.format( "Released at [%d, %d]", event.getX(), event.getY() ) ); 
            }
        } // end medhod mouseReleased
        
        public void mouseMoved( MouseEvent event )
        {
            statusLabel.setText( String.format( "Moved at [%d, %d]", event.getX(), event.getY() ) );
        } // end method mouseMoved
        
    } // end inner class MouseHandler
    
    //Saving the current image into a memeory storage
    public void saveDrawing(String fileName)
    {
        try 
        {
            BufferedImage image = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
            paint(image.createGraphics());
            ImageIO.write(image, "jpg", new File(fileName));
        }
        catch(Exception e)
        {
        }
    }
} // end class DrawPanel