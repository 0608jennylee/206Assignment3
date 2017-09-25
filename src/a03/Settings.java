package a03;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Settings {
	static Settings s = null;
	public Map<String,Boolean> settings = new HashMap<String,Boolean>();
	
	private Settings() {
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader("Settings.ini"));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] words = line.split("=");
				settings.put(words[0], Settings.toBoolean(words[1]));
			}
		} catch (FileNotFoundException e) {
			//write and create settings.ini file with default values
			settings.put("HARDLEVEL", false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Settings getSettings() {
		if(s == null) {
			s = new Settings();
			return s;
		}else {
			return s;
		}
	}
	
	private static boolean toBoolean(String bool) {
		if(bool.equalsIgnoreCase("false")) {
			return false;
		}else {
			return true;
		}
	}
}
