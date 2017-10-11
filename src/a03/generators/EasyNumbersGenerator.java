package a03.generators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import a03.enumerations.Difficulty;

public class EasyNumbersGenerator extends Generator{

	public EasyNumbersGenerator(Difficulty level) {
		super(level);
	}

	@Override
	public List<String> getNumbers() {
		List<String> _numbers = new ArrayList<String>();
		for(int i = 1; i < 10; i++) {
			_numbers.add("" + i);
		}
		Integer randNum = new Integer((int)(Math.random() * _level.getRange() + 1));
		_numbers.add(randNum.toString());
		Collections.shuffle(_numbers);
		return _numbers;
	}

}
