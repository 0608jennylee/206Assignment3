package a03.generators;

import java.util.List;

import a03.enumerations.Difficulty;

public abstract class Generator {
	protected Difficulty _difficulty;
	protected int _questions;

	public Generator(Difficulty level, int questions) {
		_difficulty = level;
		_questions = questions;
	}
	
	public abstract List<String> getNumbers();
}
