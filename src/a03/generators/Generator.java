package a03.generators;

import java.util.List;

import a03.Difficulty;

public abstract class Generator {
	protected Difficulty _level;

	public Generator(Difficulty level) {
		_level = level;
	}
	
	public abstract List<String> getNumbers();
}
