package a03.generators;

import java.util.ArrayList;
import java.util.List;

import a03.enumerations.Difficulty;

public class NumbersGenerator extends Generator{

	//By default set number of questions to 10 is the generator is not attempting to generate custom question list.
	public NumbersGenerator(Difficulty level) {
		super(level, 10);
	}
	
	public NumbersGenerator(Difficulty level, int questions) {
		super(level, questions);
	}

	@Override
	public List<String> getNumbers() {
		List<String> _numbers = new ArrayList<String>();
		for(int i = 0; i < _questions; i++) {
			Integer randNum = new Integer((int)(Math.random() * (_difficulty.getMax() - _difficulty.getMin()) + _difficulty.getMin()));
			_numbers.add(randNum.toString());
		}
		return _numbers;
	}

}
