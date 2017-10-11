package a03.generators;

import java.util.ArrayList;
import java.util.List;

import a03.enumerations.Level;

public class HardEquationsGenerator extends Generator{

	public HardEquationsGenerator(Level level) {
		super(level);
	}

	@Override
	public List<String> getNumbers() {
		List<String> _numbers = new ArrayList<String>();
		for(int i = 0; i < 10; i++) {
			if(Math.random() > 0.5) {
				Integer a = new Integer((int)(Math.random() * (_level.getRange() - 1)) + 1);
				Integer b = new Integer((int)(Math.random() * (_level.getRange() - a)) + 1);
				_numbers.add(a.toString() + " + " + b.toString());
			}else {
				Integer a = new Integer((int)(Math.random() * (_level.getRange() - 1)) + 1);
				Integer b = new Integer((int)(Math.random() * (_level.getRange() - a)) + 1);
				_numbers.add(a.toString() + " + " + b.toString());
			}
		}
		return _numbers;
	}

}
