package a03.generators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EasyGen {
	
	public List<Integer> getNumbers(){
		List<Integer> _numbers = new ArrayList<Integer>();
		for(int i = 1; i < 10; i++) {
			_numbers.add(new Integer(i));
		}
		Integer randNum = new Integer((int)(Math.random() * 10));
		_numbers.add(randNum);
		Collections.shuffle(_numbers);
		return _numbers;
	}
}
