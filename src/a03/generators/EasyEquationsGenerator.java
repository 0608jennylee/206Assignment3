package a03.generators;

import java.util.ArrayList;
import java.util.List;

import a03.Difficulty;
import a03.enumerations.Level;

public class EasyEquationsGenerator extends Generator{

	public EasyEquationsGenerator(Difficulty level) {
		super(level);
	}

	@Override
	public List<String> getNumbers() {
		List<String> _numbers = new ArrayList<String>();
		return _numbers;
	}

}
