package a03.generators;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import a03.Number;

public class Processor {
	public boolean processAnswer(int num) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("recout.mlf"));
			//skip the first line we dont care
			br.readLine();
			br.readLine();
			br.readLine();
			String line;
			if(num <= 10) {
				line = br.readLine();
				for(Number i : Number.values()) {
					if(num == i.getNumber() && i.getMaoriName().equals(line)) {
						return true;
					} 
				}
				return false;
			}else {
				String[] maoriName = new String[3];
				for(Number i : Number.values()) {
					if(num / 10 == i.getNumber()) {
						maoriName[0] = i.getMaoriName();
					}
				}
				maoriName[1] = "maa";
				for(Number i: Number.values()) {
					if(num % 10 == i.getNumber()) {
						maoriName[2] = i.getMaoriName();
					}
				}
				for(int i = 0; i < 3; i++) {
					line = br.readLine();
					if(!maoriName[i].equals(line)) {
						return false;
					}
				}
				return true;

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
}
