package a03;

import a03.enumerations.Difficulty;
import a03.enumerations.Level;

public class LogData {
	private String _correctAnswers;
	private String _totalAnswers;
	private String _level;
	private String _difficulty;
	private String _date;
	
	public LogData(String score, String _totalAnswers, Level level, Difficulty difficulty, String date) {
		_correctAnswers = score;
		_level = level.toString();
		_difficulty = difficulty.toString();
		_date = date;
	}
	
	public String toString() {
		return _date;
	}
}
