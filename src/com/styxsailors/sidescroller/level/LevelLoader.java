package com.styxsailors.sidescroller.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LevelLoader {
	
	public LevelLoader(){
		
	}
	
	public ArrayList<String> loadLevel(String level){
		String str="";
		ArrayList<String> levelLoaded = new ArrayList<String>();
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("levels/"+level+".lvl");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    if (is!=null) {                         
	        try {
				while ((str = reader.readLine()) != null) { 
				   levelLoaded.add(str);
				}
				is.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}               
	    }       
	    return levelLoaded;
	}

}
