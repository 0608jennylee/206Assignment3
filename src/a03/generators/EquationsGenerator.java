package a03.generators;

import java.util.ArrayList;
import java.util.List;

import a03.enumerations.Difficulty;

public class EquationsGenerator extends Generator{

	public EquationsGenerator(Difficulty level) {
		super(level, 10);
	}
	
	public EquationsGenerator(Difficulty level, int questions) {
		super(level, questions);
	}

	@Override
	public List<String> getNumbers() {
		List<String> _numbers = new ArrayList<String>();
		for(int i = 0; i < _questions; i++) {
			if(Math.random() > 0.5) {
				Integer answer = new Integer((int)(Math.random() * (_difficulty.getMax() - _difficulty.getMin() + 1)) + _difficulty.getMin());
				Integer a = new Integer((int)(Math.random() * (answer - 1)) + 1);
				Integer b = new Integer(answer - a);
				_numbers.add(a.toString() + " + " + b.toString());
			}else {
				Integer a = new Integer((int)(Math.random() * (_difficulty.getMax() - _difficulty.getMin() + 1)) + _difficulty.getMin());
				Integer b = new Integer((int)(Math.random() * (a - _difficulty.getMin() + 1)));
				_numbers.add(a.toString() + " - " + b.toString());
			}
		}
		return _numbers;
	}

}
