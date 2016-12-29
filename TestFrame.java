/*
 * Description: The TestFrame class is what holds everthing together.
 * 
 * Name: Alpha Mai
 * 
 * Date: May 25, 2015
 */

//imports 
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class TestFrame extends JFrame
{ 
   //variables
   private TabbedPanel panel;
   private MenuBar menu;
   private ToolBar myToolBar;
   private JLabel statusLabel = new JLabel("Mouse Position Label");
   
   public TestFrame()
   {
       //create the components
       panel = new TabbedPanel(statusLabel);
       menu = new MenuBar(panel, statusLabel, this);
       myToolBar = new ToolBar(panel.getCurrentPanel());
       
       //create action handler
       panel.addChangeListener(
            new ChangeListener() // anonymous inner class
            {
                //handle the shape selections
                public void stateChanged( ChangeEvent event )
                {
                    //if there is no more tabs exit program
                    if (panel.noTabs())
                    {
                        close();
                    }
                    
                    panel.getCurrentPanel().setCurrentShapeType(myToolBar.getShapeType());
                    panel.getCurrentPanel().setCurrentShapeColor1(myToolBar.getColor1());
                    panel.getCurrentPanel().setCurrentShapeColor2(myToolBar.getColor2());
                    panel.getCurrentPanel().setCurrentShapeFilled(myToolBar.getFilled());
                    panel.getCurrentPanel().setCurrentShapeGradient(myToolBar.getGradient());
                    panel.getCurrentPanel().setCurrentShapeDashed(myToolBar.getDashedLine());
                    panel.getCurrentPanel().setCurrentShapeStrokeWidth(myToolBar.getStrokeWidth());
                    panel.getCurrentPanel().setCurrentShapeDashLength(myToolBar.getDashLength());
                    
                    myToolBar.setCurrentPanel(panel.getCurrentPanel());
                } 
       } 
       ); 
       //add the ManuBar and components into the Frame
       setJMenuBar(menu);
       add(myToolBar, BorderLayout.NORTH);
       add(panel, BorderLayout.CENTER);
       add(statusLabel, BorderLayout.SOUTH);
   }
   public void close()
   {
       System.exit(0);
   }
   public static void main( String args[] )
   {
     //instantiate JTestFrame
     TestFrame application = new TestFrame(); 
     application.setTitle("Java Paint");
     application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
     application.setSize( 900, 500 );
     application.setVisible( true );
   }
}