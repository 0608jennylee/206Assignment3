package a03.generators;

import java.util.ArrayList;
import java.util.List;

import a03.Level;

public class HardNumbersGenerator extends Generator{

	public HardNumbersGenerator(Level level) {
		super(level);
	}

	@Override
	public List<String> getNumbers() {
		List<String> _numbers = new ArrayList<String>();
		for(int i = 0; i < 10; i++) {
			Integer randNum = new Integer((int)(Math.random() * _level.getRange() + 1));
			_numbers.add(randNum.toString());
		}
		return _numbers;
	}

}
