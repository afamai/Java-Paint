/*
 * Description: The ToolBar consist of all the components for changing the tools and settings for painting
 * 
 * Name: Alpha Mai
 * 
 * Date: May 25, 2015
 */

//imports
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JToolBar;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class ToolBar extends JToolBar
{
    //variables
    private String[] shapes = {"Line", "Rectangle", "Oval", "Round Rectangle"};
    private DrawPanel drawPanel;
    private JButton undoButton;
    private JButton redoButton;
    private JButton clearButton;
    private JButton color1;
    private JButton color2;
    private JComboBox<String> shapeSelect;
    private JCheckBox shapeFilled;
    private JCheckBox gradient;
    private JCheckBox dashLine;
    private JTextField strokeWidth;
    private JTextField dashLength;
    private JColorChooser cc = new JColorChooser();
    private Settings properties = new Settings();
    private Color firstColor;
    private Color secondColor;
    private DrawPanel.Type shapeType;
    private boolean filled;
    private boolean gradiented;
    private boolean dashedLine;
    private float thickness;
    private float length;
    
    ToolBar(DrawPanel currentDrawPanel)
    {
        //set the toolBar's settings to the one in the configuration file, when this is instantiated
        firstColor = new Color(Integer.parseInt(properties.getSetting("color1r")),
                           Integer.parseInt(properties.getSetting("color1g")),
                           Integer.parseInt(properties.getSetting("color1b")));
        secondColor = new Color(Integer.parseInt(properties.getSetting("color2r")),
                           Integer.parseInt(properties.getSetting("color2g")),
                           Integer.parseInt(properties.getSetting("color2b")));
        shapeType = DrawPanel.Type.valueOf(properties.getSetting("shapeType"));
        filled = Boolean.parseBoolean(properties.getSetting("fill"));
        gradiented = Boolean.parseBoolean(properties.getSetting("gradient"));
        dashedLine = Boolean.parseBoolean(properties.getSetting("dashedLine"));
        thickness = Float.parseFloat(properties.getSetting("strokeWidth"));
        length = Float.parseFloat(properties.getSetting("dashLength"));
        
        //create components
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");
        clearButton = new JButton("Clear");
        shapeSelect = new JComboBox<String>(shapes);
        shapeFilled = new JCheckBox("filled", filled);
        gradient = new JCheckBox("gradient", gradiented);
        dashLine = new JCheckBox("dashed lines", dashedLine);
        
        color1 = new JButton();
        color1.setPreferredSize(new Dimension(20,20));
        color1.setBackground(firstColor);
        color1.setContentAreaFilled(false);
        color1.setOpaque(true);
        
        color2 = new JButton();
        color2.setPreferredSize(new Dimension(20,20));
        color2.setBackground(secondColor);
        color2.setContentAreaFilled(false);
        color2.setOpaque(true);
        strokeWidth = new JTextField(String.valueOf(thickness), 5);
        dashLength = new JTextField(String.valueOf(length), 5);
        
        shapeSelect.setSelectedIndex(Integer.parseInt(properties.getSetting("shapeIndex")));
        
        drawPanel = currentDrawPanel;
        
        setLayout(new FlowLayout());
        
        add(undoButton);
        add(redoButton);
        add(clearButton);
        add(shapeSelect);
        add(new JLabel("Stroke Width:"));
        add(strokeWidth);
        add(new JLabel("Dash Length"));
        add(dashLength);
        add(color1);
        add(color2);
        add(shapeFilled);
        add(gradient);
        add(dashLine);
        
        ButtonHandler handler = new ButtonHandler();
        CheckBoxHandler boxHandler = new CheckBoxHandler();
        undoButton.addActionListener( handler );
        redoButton.addActionListener( handler );
        clearButton.addActionListener( handler );
        color1.addActionListener( handler );
        color2.addActionListener( handler );
        strokeWidth.addActionListener( handler );
        dashLength.addActionListener( handler );
        shapeFilled.addItemListener(boxHandler);
        gradient.addItemListener(boxHandler);
        dashLine.addItemListener(boxHandler);
        
        shapeSelect.addItemListener(
            new ItemListener() // anonymous inner class
            {
                //handle the shape selections
                public void itemStateChanged( ItemEvent event )
                {
                    switch(shapeSelect.getSelectedIndex())
                    {
                        case 0:
                            drawPanel.setCurrentShapeType(DrawPanel.Type.LINE);
                            shapeType = DrawPanel.Type.LINE;
                            break;
                        case 1:
                            drawPanel.setCurrentShapeType(DrawPanel.Type.RECTANGLE);
                            shapeType = DrawPanel.Type.RECTANGLE;
                            break;
                        case 2:
                            drawPanel.setCurrentShapeType(DrawPanel.Type.OVAL);
                            shapeType = DrawPanel.Type.OVAL;
                            break;
                        case 3:
                            drawPanel.setCurrentShapeType(DrawPanel.Type.ROUNDRECT);
                            shapeType = DrawPanel.Type.ROUNDRECT;
                            break;
                    }
                } 
            } 
        ); 
        
    }
    private class CheckBoxHandler implements ItemListener
    {
        // handle the filled check box
        public void itemStateChanged( ItemEvent event )
        {
            if (event.getSource() == shapeFilled)
            {
                if ( event.getStateChange() == ItemEvent.SELECTED )
                {
                    drawPanel.setCurrentShapeFilled(true);
                    filled = true;
                }
                else
                {
                    drawPanel.setCurrentShapeFilled(false);
                    filled = false;
                }
            }
            if (event.getSource() == gradient)
            {
                if ( event.getStateChange() == ItemEvent.SELECTED )
                {
                    drawPanel.setCurrentShapeGradient(true);
                    gradiented = true;
                }
                else
                {
                    drawPanel.setCurrentShapeGradient(false);
                    gradiented = false;
                }
            }
            if (event.getSource() == dashLine)
            {
                if ( event.getStateChange() == ItemEvent.SELECTED )
                {
                    drawPanel.setCurrentShapeDashed(true);
                    dashedLine = true;
                }
                else
                {
                    drawPanel.setCurrentShapeDashed(false);
                    dashedLine = false;
                }
            }
        } //end method itemStateChanged
    }//end ComboBoxHandler
    private class ButtonHandler implements ActionListener 
    {
        // handle button event
        public void actionPerformed( ActionEvent event )
        {
            if (event.getSource() == undoButton)
            {
                drawPanel.clearLastShape();
            }
            if (event.getSource() == redoButton)
            {
                drawPanel.redoLastShape();
            }
            if (event.getSource() == clearButton)
            {
                drawPanel.clearDrawing();
            }
            if (event.getSource() == color1)
            {
                firstColor = cc.showDialog(null, "Colors", drawPanel.getCurrentShapeColor1());
                
                if (firstColor == null)
                {
                    firstColor = drawPanel.getCurrentShapeColor1();
                }
                color1.setBackground(firstColor);
                drawPanel.setCurrentShapeColor1(firstColor);
            }
            if (event.getSource() == color2)
            {
                secondColor = cc.showDialog(null, "Colors", drawPanel.getCurrentShapeColor2());
                if (secondColor == null)
                {
                    secondColor = drawPanel.getCurrentShapeColor2();
                }
                color2.setBackground(secondColor);
                drawPanel.setCurrentShapeColor2(secondColor);
            }
            if (event.getSource() == strokeWidth)
            {
                try{
                    thickness = Float.parseFloat(strokeWidth.getText());
                    if (thickness < 1.0f)
                    {
                        thickness = 1.0f;
                        strokeWidth.setText(String.valueOf(thickness));
                    }
                    drawPanel.setCurrentShapeStrokeWidth(thickness);
                }
                catch(NumberFormatException e)
                {
                    strokeWidth.setText("");
                }
            }
            if (event.getSource() == dashLength)
            {
                try{
                   length= Float.parseFloat(dashLength.getText());
                   if(length < 1.0f)
                   {
                       length = 1.0f;
                       dashLength.setText(String.valueOf(length));
                   }
                drawPanel.setCurrentShapeDashLength(length);
                }
                catch(NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"Invalid input. Try again.");
                    strokeWidth.setText("");
                }
            }
            
        } // end method actionPerformed
    } // end private inner class ButtonHandler
    
    /*=============================================
     Mutator Methods
     ============================================*/
    public void setCurrentPanel(DrawPanel currentPanel)
    {
        drawPanel = currentPanel;
    }
    
    /*=============================================
     Accessor Methods
     ============================================*/
    public Color getColor1()
    {
        return firstColor;
    }
    public Color getColor2()
    {
        return secondColor;
    }
    public DrawPanel.Type getShapeType()
    {
        return shapeType;
    }
    public boolean getFilled()
    {
        return filled;
    }
    public boolean getGradient()
    {
        return gradiented;
    }
    public boolean getDashedLine()
    {
        return dashedLine;
    }
    public float getStrokeWidth()
    {
        return thickness;
    }
    public float getDashLength()
    {
        return length;
    }
}