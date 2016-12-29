/*
 * Description: The TabbedPanel class will handle all the tabs that will be used in the paint program
 * 
 * Name: Alpha Mai
 * 
 * Date: May 25, 2015
 */

//imports
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Color;
import java.util.ArrayList;

public class TabbedPanel extends JTabbedPane
{
    //variables
    private int numTab = 1;
    private DrawPanel newPanel;
    private ArrayList<DrawPanel> drawPanels = new ArrayList<DrawPanel>();
    private JPanel title;
    private String tabName;
    private Settings properties = new Settings();
    
    //contructor
    public TabbedPanel(JLabel statusLabel)
    {
        //creat new DrawPanel
        newPanel = new DrawPanel(statusLabel);
        //change the settings
        setDefaults(newPanel);
        //add a new tab to the panel
        addNewTab("Untitled"+numTab, newPanel);
    }
    
    //method to set the defaults settings from the configuration file to the DrawPanel 
    public void setDefaults(DrawPanel panel)
    {
        panel.setCurrentShapeType(DrawPanel.Type.valueOf(properties.getSetting("shapeType")));
        panel.setCurrentShapeColor1(new Color(Integer.parseInt(properties.getSetting("color1r")),
                                                Integer.parseInt(properties.getSetting("color1g")),
                                                Integer.parseInt(properties.getSetting("color1b"))));
        panel.setCurrentShapeColor2(new Color(Integer.parseInt(properties.getSetting("color2r")),
                                                Integer.parseInt(properties.getSetting("color2g")),
                                                Integer.parseInt(properties.getSetting("color2b"))));
        panel.setCurrentShapeFilled(Boolean.parseBoolean(properties.getSetting("fill")));
        panel.setCurrentShapeGradient(Boolean.parseBoolean(properties.getSetting("gradient")));
        panel.setCurrentShapeDashed(Boolean.parseBoolean(properties.getSetting("dashedLine")));
        panel.setCurrentShapeStrokeWidth(Float.parseFloat(properties.getSetting("strokeWidth")));
        panel.setCurrentShapeDashLength(Float.parseFloat(properties.getSetting("dashLength")));
    }
    public void addNewTab(String name, DrawPanel panel)
    {
        numTab += 1;
        //create variables that points to the panel and name for later usage
        newPanel = panel;
        tabName = name;
        
        setDefaults(newPanel);
        //add a new tab
        addTab(name, panel);
        //while also adding the DrawPanel to a ArrayList for easy access to the DrawPanel
        drawPanels.add(panel);

        //create components for the title of the tab
        title = new JPanel();
        JButton closeButton = new JButton("X");
        closeButton.setPreferredSize(new Dimension(42,20));
        closeButton.setContentAreaFilled(false);

        title.setLayout(new GridBagLayout());
        
        //create an action handler when the user clicks on the close button
        closeButton.addActionListener(new CloseButtonHandler(tabName));
        
        GridBagConstraints c = new GridBagConstraints();
        
        //add the widgets into the panel
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        title.add(new JLabel(name), c);
        
        c.gridx = 1;
        c.weightx = 0;
        title.add(closeButton, c);
        
        //set the title of the tab using the JPanel I created
        setTabComponentAt(indexOfTab(name), title);
        
        
    }
    //check if the tab exist or not
    public boolean exist(String name)
    {
        if (indexOfTab(name) == -1)
        {
            return false;
        }
        return true;
    }
    private class CloseButtonHandler implements ActionListener 
    {
        private String tabName;
        public CloseButtonHandler(String tabName)
        {
            this.tabName = tabName;
        }
        public int getIndex()
        {
            return indexOfTab(tabName);
        }
        
        // handle button event
        public void actionPerformed( ActionEvent event )
        {
            int index = getIndex();
            if (index >= 0)
            {
                remove(index);
                drawPanels.remove(index);
            }
        } // end method actionPerformed
    } // end private inner class CloseButtonHandler
    
    //get tab number
    public int getTabNumber()
    {
        return numTab;
    }
    //check if there is anymore tabs
    public boolean noTabs()
    {
        if (getTabCount() == 0)
        {
            return true;
        }
        return false;
    }
    //returns the currently selected DrawPanel
    public DrawPanel getCurrentPanel()
    {
        return drawPanels.get(getSelectedIndex());
    }
    
}