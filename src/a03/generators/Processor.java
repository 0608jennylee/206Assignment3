package a03.generators;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import a03.enumerations.Number;
import a03.errors.HTKError;

public class Processor {
	
	//Solves and returns the result of a string representation of either a) an addition b) a subtraction or c) just a number.
	public static int toInt(String formula) {
		if(formula.contains("+")) {
			return Integer.parseInt(formula.split(" ")[0]) + Integer.parseInt(formula.split(" ")[2]);
		}else if(formula.contains("-")) {
			return Integer.parseInt(formula.split(" ")[0]) - Integer.parseInt(formula.split(" ")[2]);
		}else {
			return Integer.parseInt(formula);
		}
	}
	
	//Convert a number to a string that is the Maori equivalent. Macrons included!
	public static String toMaori(int num) {
		String[] maoriName = new String[4];
		if(num <= 10) {
			for(Number i : Number.values()){
				if(num == i.getNumber()){
					return i.getMaoriName();
				}
			}
		}else if(num <= 19) {
			maoriName[0] = "tekau";
			maoriName[1] = "mā";
			for(Number i : Number.values()) {
				if(i.getNumber() == num % 10) {
					maoriName[2] = i.getMaoriName();
				}
			}
			return maoriName[0] + " " + maoriName[1] + " " + maoriName[2];
		}else if(num % 10 == 0) {
			for(Number i : Number.values()) {
				if(i.getNumber() == num / 10) {
					maoriName[0] = i.getMaoriName();
				}
			}
			maoriName[1] = "tekau";
			return maoriName[0] + " " + maoriName[1];
		}else {
			maoriName[1] = "tekau";
			for(Number i : Number.values()) {
				if(num / 10 == i.getNumber()) {
					maoriName[0] = i.getMaoriName();
				}
			}
			maoriName[2] = "mā";
			for(Number i: Number.values()) {
				if(num % 10 == i.getNumber()) {
					maoriName[3] = i.getMaoriName();
				}
			}
			return maoriName[0] + " " + maoriName[1] + " " + maoriName[2] + " " + maoriName[3];
		}
		return null;
	}
	
	//returns the string that represents what the user has said, read from the recout.mlf file.
	//throws HTK error if no sound is picked up.
	public static String getUserAnswer() throws HTKError {
		try {
			List<String> lines = new ArrayList<String>();
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader("recout.mlf"));
			String answer = null;
			
			//Parse HTK output to Maori with macrons.
			while((answer = br.readLine()) != null) {
				if(answer.equals("maa")) {
					answer = "mā";
				}else if(answer.equals("whaa")) {
					answer = "whā";
				}
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method given an integer matches weather the Maori representation defined by the toMaori method of this class
	 * matches to the user answer. Lenient matching has been applied to account for background noise
	 * if the correct words appear in the correct order then it is treated as correct.
	 * @param num
	 * @return correct(true)/incorrect(false)
	 * @throws HTKError
	 */
	public boolean processAnswer(int num) throws HTKError{
		String line = getUserAnswer();
		String[] words = line.split(" ");
		LinkedList<String> maoriName = new LinkedList<String>(Arrays.asList(toMaori(num).split(" ")));
		
		for(int i = 0; i < words.length; i++) {
			if(words[i].equals(maoriName.peek())) {
				maoriName.pop();
			}
		}
		
		//if the size is zero it means all words have been matched so the user spoke correctly.
		if(maoriName.size() == 0) {
			return true;
		}else {
			return false;
		}
		
	}
}
