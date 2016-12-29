/*
 * Description: The Preference Window is a JFrame which contains all the methods and components for setting the 
 *              preferences for the paint program
 * 
 * Name: Alpha Mai
 * 
 * Date: May 25, 2015
 */

//imports 
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JColorChooser;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;

public class PreferenceWindow extends JFrame
{
    //variables
    private String[] shapes = {"Line", "Rectangle", "Oval", "Round Rectangle"};
    private JButton color1;
    private JButton color2;
    private JComboBox<String> shapeSelect;
    private JCheckBox shapeFilled;
    private JCheckBox gradient;
    private JCheckBox dashLine;
    private JTextField strokeWidth;
    private JTextField dashLength;
    private Settings properties = new Settings();
    private Color color;
    private JColorChooser cc = new JColorChooser();
    
    public PreferenceWindow()
    {
        setLayout(new GridBagLayout());
        
        //set the components with its defaults settings from the configuration file
        shapeSelect = new JComboBox<String>(shapes);
        shapeFilled = new JCheckBox("filled", Boolean.parseBoolean(properties.getSetting("fill")));
        gradient = new JCheckBox("gradient", Boolean.parseBoolean(properties.getSetting("gradient")));
        dashLine = new JCheckBox("dashed lines", Boolean.parseBoolean(properties.getSetting("dashedLine")));
        strokeWidth = new JTextField(properties.getSetting("strokeWidth"), 5);
        dashLength = new JTextField(properties.getSetting("dashLength"), 5);
        color1 = new JButton();
        //customizing buttons
        color1.setPreferredSize(new Dimension(20,20));
        color1.setContentAreaFilled(false);
        color1.setOpaque(true);
        color1.setBackground(new Color(Integer.parseInt(properties.getSetting("color1r")),
                                       Integer.parseInt(properties.getSetting("color1g")),
                                       Integer.parseInt(properties.getSetting("color1b")))); 
        color2 = new JButton();
        color2.setPreferredSize(new Dimension(20,20));
        color2.setContentAreaFilled(false);
        color2.setOpaque(true);
        color2.setBackground(new Color(Integer.parseInt(properties.getSetting("color2r")),
                                       Integer.parseInt(properties.getSetting("color2g")),
                                       Integer.parseInt(properties.getSetting("color2b"))));
        shapeSelect.setSelectedIndex(Integer.parseInt(properties.getSetting("shapeIndex")));
        
        //create GridBagConstraints
        GridBagConstraints c = new GridBagConstraints();
        
        //add components into JFrame with specific layout
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        add(shapeSelect,c);
        
        c.gridy += 1;
        add(new JLabel("Stroke Width:"),c);
        
        c.gridx = 1;
        add(strokeWidth,c);
        
        c.gridx = 0;
        c.gridy += 1;
        add(new JLabel("Dash Length"),c);
        
        c.gridx = 1;
        add(dashLength,c);
        
        c.gridx = 0;
        c.gridy += 1;
        add(new JLabel("Color #1:"),c);
        
        c.gridx = 1;
        add(color1,c);
        
        c.gridx = 0;
        c.gridy += 1;
        add(new JLabel("Color #2:"),c);
        
        c.gridx = 1;
        add(color2,c);
        
        c.gridx = 0;
        c.gridy += 1;
        add(shapeFilled,c);
        
        c.gridx = 0;
        c.gridy += 1;
        add(gradient,c);
        
        c.gridx = 0;
        c.gridy += 1;
        add(dashLine,c);
        
        pack();//pack eveything in
        
        //create event handlers for the components
        ButtonHandler handler = new ButtonHandler();
        CheckBoxHandler boxHandler = new CheckBoxHandler();
        //add the handlers to the components
        color1.addActionListener( handler );
        color2.addActionListener( handler );
        strokeWidth.addActionListener( handler );
        dashLength.addActionListener( handler );
        shapeFilled.addItemListener(boxHandler);
        gradient.addItemListener(boxHandler);
        dashLine.addItemListener(boxHandler);
        
        //creating a event handler for the shapeselect comboBox
        shapeSelect.addItemListener(
            new ItemListener() // anonymous inner class
            {
                //handle the shape selections
                public void itemStateChanged( ItemEvent event )
                {
                    switch(shapeSelect.getSelectedIndex())
                    {
                        case 0:
                            properties.setSetting("shapeType", "LINE");
                            properties.setSetting("shapeIndex", "0");
                            break;
                        case 1:
                            properties.setSetting("shapeType", "RECTANGLE");
                            properties.setSetting("shapeIndex", "1");
                            break;
                        case 2:
                            properties.setSetting("shapeType", "OVAL");
                            properties.setSetting("shapeIndex", "2");
                            break;
                        case 3:
                            properties.setSetting("shapeType", "ROUNDRECT");
                            properties.setSetting("shapeIndex", "3");
                            break;
                    }
                } 
            } 
        );
    }
    //handler classes that will change the configuration file depending on the user choice
    private class CheckBoxHandler implements ItemListener
    {
        // handle the filled check box
        public void itemStateChanged( ItemEvent event )
        {
            if (event.getSource() == shapeFilled)
            {
                if ( event.getStateChange() == ItemEvent.SELECTED )
                {
                    properties.setSetting("fill","true");
                }
                else
                {
                    properties.setSetting("fill","false");
                }
            }
            if (event.getSource() == gradient)
            {
                if ( event.getStateChange() == ItemEvent.SELECTED )
                {
                    properties.setSetting("gradient","true");
                }
                else
                {
                    properties.setSetting("gradient","false");
                }
            }
            if (event.getSource() == dashLine)
            {
                if ( event.getStateChange() == ItemEvent.SELECTED )
                {
                    properties.setSetting("dashedLine","true");
                }
                else
                {
                    properties.setSetting("dashedLine","false");
                }
            }
        }//end method itsmStateChanged
    }//end ComboBoxHandler
    private class ButtonHandler implements ActionListener 
    {
        // handle button event
        public void actionPerformed( ActionEvent event )
        {
            if (event.getSource() == color1)
            {
                Color defaultColor = new Color(Integer.parseInt(properties.getSetting("color1r")),
                                               Integer.parseInt(properties.getSetting("color1g")),
                                               Integer.parseInt(properties.getSetting("color1b")));
                //get the user color by calling the JColorChooser show dialog
                color = cc.showDialog(null, "Colors", defaultColor);
                //if no color was chosen
                if (color == null)
                {
                    //set color to defaultColor
                    color = defaultColor;
                }
                //change Buttons's color
                color1.setBackground(color);
                //save the settings
                properties.setSetting("color1r", String.valueOf(color.getRed()));
                properties.setSetting("color1g", String.valueOf(color.getGreen()));
                properties.setSetting("color1b", String.valueOf(color.getBlue()));
                
            }
            if (event.getSource() == color2)
            {
                Color defaultColor = new Color(Integer.parseInt(properties.getSetting("color2r")),
                                               Integer.parseInt(properties.getSetting("color2g")),
                                               Integer.parseInt(properties.getSetting("color2b")));
                
                color = cc.showDialog(null, "Colors", defaultColor);
                
                if (color == null)
                {
                    color = defaultColor;
                }
                color2.setBackground(color);
                properties.setSetting("color2r", String.valueOf(color.getRed()));
                properties.setSetting("color2g", String.valueOf(color.getGreen()));
                properties.setSetting("color2b", String.valueOf(color.getBlue()));
            }
            if (event.getSource() == strokeWidth)
            {
                try
                {
                    if (Float.parseFloat(strokeWidth.getText()) < 1.0f)
                    {
                        strokeWidth.setText("1.0");
                    }
                    properties.setSetting("strokeWidth",strokeWidth.getText());
                }
                catch(NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"Invalid input. Try again.");
                    strokeWidth.setText("");
                }
            }
            if (event.getSource() == dashLength)
            {
                try
                {
                    if (Float.parseFloat(dashLength.getText()) < 1.0f)
                    {
                        dashLength.setText("1.0");
                    }
                    properties.setSetting("dashLength",dashLength.getText());
                }
                catch(NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"Invalid input. Try again.");
                    strokeWidth.setText("");
                }
            }
            
        } // end method actionPerformed
    } // end private inner class ButtonHandler
}