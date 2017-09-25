package a03.generators;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import a03.HTKError;
import a03.Number;

public class Processor {
	
	public static String toMaori(int num) {
		String[] maoriName = new String[4];
		if(num <= 10) {
			for(Number i : Number.values()){
				if(num == i.getNumber()){
					return i.getMaoriName();
				}
			}
		}else {
			maoriName[1] = "tekau";
			for(Number i : Number.values()) {
				if(num / 10 == i.getNumber()) {
					maoriName[0] = i.getMaoriName();
				}
			}
			maoriName[2] = "maa";
			for(Number i: Number.values()) {
				if(num % 10 == i.getNumber()) {
					maoriName[3] = i.getMaoriName();
				}
			}
			return maoriName[0] + " " + maoriName[1] + " " + maoriName[2] + " " + maoriName[3];
		}
		return null;
	}
	
	public static String getUserAnswer() throws HTKError {
		try {
			List<String> lines = new ArrayList<String>();
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader("recout.mlf"));
			String answer = null;
			while((answer = br.readLine()) != null) {
				lines.add(answer);
			}
			if(lines.size() != 1){
				answer = lines.get(3);
				for(int i = 4; i < lines.size() - 2; i++) {
					answer = answer + " " + lines.get(i);
				}
			}else {
				throw new HTKError();
			}
			return answer;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean processAnswer(int num) throws HTKError {
		String line = getUserAnswer();
		String[] words = line.split(" ");
		if(num <= 10) {
			for(Number i : Number.values()) {
				if(num == i.getNumber() && i.getMaoriName().equals(line) && words.length == 1) {
					return true;
				} 
			}
			return false;
		}else {
			String[] maoriName;
			String temp = toMaori(num);
			maoriName = temp.split(" ");
			if(words.length != 3) {
				return false;
			}else {
				for(int i = 0; i < 3; i++) {
					if(!maoriName[i].equals(line)) {
						return false;
					}
				}
				return true;
			}

		}
		}
}
