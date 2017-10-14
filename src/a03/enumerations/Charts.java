package a03.enumerations;

import a03.enumerations.Level;

public enum Charts {
	EASYNUMBERS("Easy Numbers' ", Level.EQUATIONS, Difficulty.EASY), HARDNUMBERS("Hard Numbers' ", Level.NUMBERS, Difficulty.HARD), EASYEQUATIONS("Easy Equations' ", Level.NUMBERS, Difficulty.EASY), HARDEQUATIONS("Hard Equations' ", Level.EQUATIONS, Difficulty.HARD), CUSTOM("Customs' ", Level.CUSTOM, Difficulty.CUSTOM);
	private Level _level;
	private Difficulty _difficulty;
	private String _rep;
	private Charts(String rep, Level level, Difficulty difficulty) {
		_rep = rep;
		_level = level;
		_difficulty = difficulty;
	}
	
	public String toString() {
		return _rep;
	}
	
	public String getFileSuffix() {
		return _level.toString() + _difficulty.toString() + "History.dat";
	}
}
