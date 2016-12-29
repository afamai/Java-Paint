/*
 * Description: A custom class that inherits the JMenuBar class, with its own menu items and event handler.
 * 
 * Name: Alpha Mai
 * 
 * Date: May 25, 2015
 */

//imports
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileFilter;

//Jframe class
public class MenuBar extends JMenuBar
{ 
    //variables
    private JMenu fileMenu;
    private JMenuItem newFile;
    private JMenuItem openFile;
    private JMenuItem saveFile;
    private JButton aboutMenu;
    private JButton preferences;
    private JButton closeButton;
    private JLabel statusLabel;
    private TabbedPanel panel;
    private JFileChooser fileChooser = new JFileChooser();
    private File file;
    private int operation;
    private boolean close = false;
    private TestFrame frame;
    
    // constructor
    public MenuBar(TabbedPanel tabPanel, JLabel label, TestFrame frame)
    {
        //allows access to the other classes/components
        this.frame = frame;
        panel = tabPanel;
        statusLabel = label;
        
        
        fileMenu = new JMenu( "File" ); //create the file menu
        
        //create Buttons that will be on the MenuBar
        aboutMenu = new JButton("About");
        aboutMenu.setContentAreaFilled(false); //remove the background color so it has the same as the MenuBar
        aboutMenu.setBorderPainted(false); //remove the border of the Button
        
        preferences = new JButton("Pref");
        preferences.setContentAreaFilled(false);
        preferences.setBorderPainted(false);
        
        closeButton = new JButton("Exit");
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        
        //create menu item/choice to place within fileMenu
        newFile = new JMenuItem( "New" );
        openFile = new JMenuItem( "Open" ); 
        saveFile = new JMenuItem( "Save");
        
        //set file filters. using FileNameExtensionFilter because the FileFilter does not have getExtention() 
        //method, which will be used later
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("JPEG Files (*.jpg;*.jpeg;*.jpe;*.jfif)", "jpeg", "jpg", "jpe", "jfif");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("GIF Files (*.gif)", "gif");
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("PNG Files (*.png)", "png");
        FileNameExtensionFilter filter4 = new FileNameExtensionFilter("TIFF Files (*.tif;*.tif)", "tif", "tiff");
        
        //add the filters into the file chooser
        fileChooser.addChoosableFileFilter(filter1);
        fileChooser.addChoosableFileFilter(filter2);
        fileChooser.addChoosableFileFilter(filter3);
        fileChooser.addChoosableFileFilter(filter4);
        
        //create the event handler
        FileHandler handler = new FileHandler();
        //add the handler into the components
        newFile.addActionListener(handler);
        saveFile.addActionListener(handler);
        openFile.addActionListener(handler);
        aboutMenu.addActionListener(handler);
        preferences.addActionListener(handler);
        closeButton.addActionListener(handler);
            
        //add openMenu items to fileMenu 
        fileMenu.add( newFile );  
        fileMenu.add( openFile );
        fileMenu.add( saveFile );
        
        //add fileMenu and buttons to the main menu bar
        add( fileMenu ); 
        add( preferences );
        add( aboutMenu );
        add( closeButton );
    }
    private class FileHandler implements ActionListener 
    {
        // handle button event
        public void actionPerformed( ActionEvent event )
        {
            if (event.getSource() == newFile)
            {
                panel.addNewTab("Untitled"+panel.getTabNumber(), new DrawPanel(statusLabel));
            }
            if (event.getSource() == saveFile)
            {
                //opens the fileChooser save dialog, with the variable keeping track of which button was pressed
                operation = fileChooser.showSaveDialog(null);
                //if okay/save button is selected
                if(operation == JFileChooser.APPROVE_OPTION)
                {
                    file = fileChooser.getSelectedFile();
                    
                }
                //make sure if the user hits close/cancel that the saving does not go on. file will be null if user hits cancel/close
                if (file != null)
                {
                    //get the filter
                    FileNameExtensionFilter filter = (FileNameExtensionFilter)fileChooser.getFileFilter();
                    //save the drawing by inputing the path name
                    panel.getCurrentPanel().saveDrawing(file.getAbsolutePath()+"."+filter.getExtensions()[0]);
                }
                //set file back to null
                file = null;
                
            }   
            if (event.getSource() == openFile)
            {
                //show the Open dialog
                operation = fileChooser.showOpenDialog(null);
                if(operation == JFileChooser.APPROVE_OPTION)
                {
                    file = fileChooser.getSelectedFile();
                    
                }
                if (file != null)
                {
                    //check if the file is already open in the tabbedPanel (FileName with the file format gone)
                    //if the file doesnt exist in the tabbedPanel then open it
                    if (!panel.exist(file.getName().substring(0, file.getName().indexOf('.'))))
                    {
                        panel.addNewTab(file.getName().substring(0, file.getName().indexOf('.')), new DrawPanel(new JLabel(), file.getAbsolutePath()));
                    }
                    //else replace it
                    else
                    {
                        panel.setComponentAt(panel.indexOfTab(file.getName().substring(0, file.getName().indexOf('.'))),new DrawPanel(new JLabel(), file.getAbsolutePath()));
                    }
                }
                //reset file back to null
                file = null;
            }
            //open about menu if user clicks "About"
            if (event.getSource() == aboutMenu)
            {
                String text = "Creator: Alpha Mai\nLast Modified Date: May 24, 2015\nDescription: This program allows the user, which is you, to draw and create\n"+
                    "             anything you like with the tools my program provides you with!";
                JOptionPane.showMessageDialog( null, text, "About", JOptionPane.INFORMATION_MESSAGE );
            }
            //opens Preference window when user clicks "Pref"
            if (event.getSource() == preferences)
            {
                PreferenceWindow pref = new PreferenceWindow();
                
                pref.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
                pref.setVisible( true );
            }
            //close the program when "Exit" is clicked
            if (event.getSource() == closeButton)
            {
                frame.close();
            }
        } // end method actionPerformed
    } // end private inner class FileHandler
}