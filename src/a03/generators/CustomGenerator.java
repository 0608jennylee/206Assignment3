package a03.generators;

import java.util.ArrayList;
import java.util.List;

import a03.enumerations.Difficulty;

public class CustomGenerator extends Generator{

	public CustomGenerator(Difficulty level, int questions) {
		super(level, questions);
	}

	@Override
	public List<String> getNumbers() {
		EquationsGenerator eg = new EquationsGenerator(_level, _questions);
		NumbersGenerator ng = new NumbersGenerator(_level, _questions);
		List<String> _numbers = new ArrayList<String>();
		List<String> egNum = eg.getNumbers();
		List<String> ngNum = ng.getNumbers();
		for(int i = 0; i < _questions; i++) {
			if(Math.random() < 0.5) {
				_numbers.add(egNum.get(i));
			}else {
				_numbers.add(ngNum.get(i));
			}
		}
		return _numbers;
	}

}
