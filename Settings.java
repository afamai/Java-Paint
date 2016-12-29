/*
 * Description: The Settings class allows the control and access ot the configuration file that will be used to handle 
 *              default settings for the paint program.
 * 
 * Name: Alpha Mai
 * 
 * Date: May 25, 2015
 */

//imports
import java.util.Properties;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class Settings
{
    //variables
    private File configFile;
    private String value;
    private Properties props;
    private FileReader reader;
    private FileWriter writer;
    
    public Settings()
    {
        //create new properties
        props = new Properties();
        /* Properties is a subclass of HashTable, meaning it works the same way as a hashTable with 
         * keys refering to values
         */
        //open the configuration file
        try{
            configFile = new File("config.properties");
            if(configFile.createNewFile())
            {
                setSetting("shapeType", "LINE");
                setSetting("color1r", "0");
                setSetting("color1g", "0");
                setSetting("color1b", "0");
                setSetting("color2r", "255");
                setSetting("color2g", "255");
                setSetting("color2b", "255");
                setSetting("dashLength", "1");
                setSetting("strokeWidth", "1");
                setSetting("shapeIndex", "0");
                setSetting("dashedLines", "false");
                setSetting("gradient", "false");
                setSetting("fill", "false");
            }
        }
        catch(NullPointerException e){}
        catch(IOException e){}
    }
    public String getSetting(String key)
    {
        try
        {
            reader = new FileReader(configFile);//create a FileReader object, which is used for reading the settings
            props.load(reader); //load the reader onto the properties
            value = props.getProperty(key);//the getProperty(String key) method will return the value of the key
            reader.close();//close the file reader
        }
        catch(Exception e)
        {
        }
        return value; //return value
    }
    public void setSetting(String key, String value)
    {
        props.setProperty(key, value);//set the key with new values, but does not permanently store the settings
        try
        {
            writer = new FileWriter(configFile);
            props.store(writer, "settings");//need to use the store(FilWriter writer, Strign comment) to make 
                                            //permanent changes to the file
            writer.close();
        }
        catch(Exception e)
        {
        }
        
    }
}