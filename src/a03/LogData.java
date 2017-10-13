package a03;

import a03.enumerations.Difficulty;
import a03.enumerations.Level;

public class LogData {
	private String _score;
	private String _level;
	private String _difficulty;
	
	public LogData(String score, Level level, Difficulty difficulty) {
		_score = score;
		_level = level.toString();
		_difficulty = difficulty.toString();
	}
}
