package a03.enumerations;

import a03.enumerations.Level;

public enum ChartTypes {
	EASYNUMBERS("Easy Numbers' ", Level.NUMBERS, Difficulty.EASY), HARDNUMBERS("Hard Numbers' ", Level.NUMBERS, Difficulty.HARD), CUSTOMNUMBERS("Custom Numbers' ", Level.NUMBERS, Difficulty.CUSTOM),
	EASYEQUATIONS("Easy Equations' ", Level.EQUATIONS, Difficulty.EASY), HARDEQUATIONS("Hard Equations' ", Level.EQUATIONS, Difficulty.HARD),CUSTOMEQUATIONS("Custom Equations' ", Level.EQUATIONS, Difficulty.CUSTOM), CUSTOMCUSTOM("Customs' ", Level.CUSTOM, Difficulty.CUSTOM);
	private Level _level;
	private Difficulty _difficulty;
	private String _rep;
	private ChartTypes(String rep, Level level, Difficulty difficulty) {
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
