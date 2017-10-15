package a03.generators;

import java.util.ArrayList;
import java.util.List;

import a03.enumerations.Difficulty;
import a03.enumerations.Level;

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
				Integer a = new Integer((int)(Math.random() * (_level.getMax() - 1)) + _level.getMin());
				Integer b = new Integer((int)(Math.random() * (_level.getMax() - a)) + _level.getMin());
				_numbers.add(a.toString() + " + " + b.toString());
			}else {
				Integer a = new Integer((int)(Math.random() * (_level.getMax() - 1)) + _level.getMin());
				Integer b = new Integer((int)(Math.random() * (_level.getMax() - a)) + _level.getMin());
				_numbers.add(a.toString() + " + " + b.toString());
			}
		}
		return _numbers;
	}

}
