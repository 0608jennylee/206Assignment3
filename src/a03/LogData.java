package a03;

import a03.enumerations.Difficulty;
import a03.enumerations.Level;

//This class represents each complete level that is written to a LogData array in a json file such that a persistent history is saved.
//An instance of this class is created each time a level has been completed in the LessThanTenController displayFinalScore().

public class LogData {
	
	private int _correctAnswers;
	private int _totalAnswers;
	@SuppressWarnings("unused")
	private String _level;
	@SuppressWarnings("unused")
	private String _difficulty;
	private String _date;
	
	public LogData(int score, int totalAnswers, Level level, Difficulty difficulty, String date) {
		_correctAnswers = score;
		_level = level.toString();
		_difficulty = difficulty.toString();
		_date = date;
		_totalAnswers = totalAnswers;
	}
	
	public String toString() {
		return _date;
	}
	
	public double toRatio() {
		return ((double)_correctAnswers / (double)_totalAnswers * 100);
	}
}
