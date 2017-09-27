package a03.generators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import a03.Level;

public class Generator {
	private Level _level;

	public void setLevel(Level level){
		_level = level;
	}

	public List<Integer> getNumbers(){
		List<Integer> _numbers = new ArrayList<Integer>();
		if (_level==Level.EASY){
			for(int i = 1; i < 10; i++) {
				_numbers.add(new Integer(i));
			}
			Integer randNum = new Integer((int)(Math.random() * 9 + 1));
			_numbers.add(randNum);
			Collections.shuffle(_numbers);
			return _numbers;
		}else{
			for(int i = 0; i < 10; i++) {
				Integer randNum = new Integer((int)(Math.random() * _level.getRange() + 1));
				_numbers.add(randNum);
			}
			//Collections.shuffle(_numbers);
			return _numbers;
		}
	}
}
