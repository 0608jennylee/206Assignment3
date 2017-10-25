package a03.generators;

import java.util.ArrayList;
import java.util.List;

import a03.enumerations.Difficulty;
import a03.enumerations.Level;


// The generator which is a aggregation of number and equation generator used to generate combined questions under the custom setting.
public class CustomGenerator extends Generator{
	
	private Level _level;

	public CustomGenerator(Difficulty difficulty, int questions, Level level) {
		super(difficulty, questions);
		_level = level;
	}

	@Override
	public List<String> getNumbers() {
		if(_level == Level.CUSTOM) {
		EquationsGenerator eg = new EquationsGenerator(_difficulty, _questions);
		NumbersGenerator ng = new NumbersGenerator(_difficulty, _questions);
		List<String> _numbers = new ArrayList<String>();
		List<String> egNum = eg.getNumbers();
		List<String> ngNum = ng.getNumbers();
		System.out.println("getnumbers" + _questions);
		for(int i = 0; i < _questions; i++) {
			//50/50 chance of number or equation theoretically.
			if(Math.random() < 0.5) {
				_numbers.add(egNum.get(i));
			}else {
				_numbers.add(ngNum.get(i));
			}
		}
		return _numbers;
		}else if(_level == Level.NUMBERS) {
			return new NumbersGenerator(_difficulty, _questions).getNumbers();
		}else {
			return new EquationsGenerator(_difficulty, _questions).getNumbers();
		}
	}

}
