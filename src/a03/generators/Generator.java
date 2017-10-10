package a03.generators;

import java.util.List;

import a03.Level;

public abstract class Generator {
	protected Level _level;

	public Generator(Level level) {
		_level = level;
	}
	
	public abstract List<String> getNumbers();
}
