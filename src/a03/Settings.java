package a03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings implements Saveable{
	static Settings s = null;
	public Map<String,Boolean> settings = new HashMap<String,Boolean>();
	
	public void save() {
		List<String> lines = new ArrayList<String>();
		for(Map.Entry<String, Boolean> e : settings.entrySet()) {
			lines.add(e.getKey() + "=" + e.getValue().toString());
		}
		try {
			Files.write(new File("Settings.ini").toPath(), lines);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
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
			//TODO write and create settings.ini file with default values
			settings.put("HARDLEVEL", false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void enableHard() {
		settings.put("HARDLEVEL", true);
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
