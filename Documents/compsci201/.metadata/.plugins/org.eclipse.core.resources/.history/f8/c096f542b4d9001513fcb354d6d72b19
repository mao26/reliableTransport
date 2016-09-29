package Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LanguagesDriver {
	
	Properties myProperties;
    private BufferedReader myFileReader;
    private final String langDirectory = "resources.languages";
    private final String langExtension = ".properties";
    
    public void load(String language) throws IOException {
    	language = format(language);
        String filename = langDirectory + "/" + langExtension;
        myFileReader = null;
        try{
        	myFileReader = new BufferedReader(new FileReader(filename));
        }
        catch(IOException e){
        	throw new FileNotFoundException(String.format("File not found: %s", filename));
        }
        myProperties = new Properties();
        myProperties.load(myFileReader);
    }
    
    public String getString(String key) {
        return myProperties.getProperty(key);
    }
    
    public String format(String language){
    	return Character.toString(language.charAt(0)).toUpperCase() + language.substring(1);
    }
}