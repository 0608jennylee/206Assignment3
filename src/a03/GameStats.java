package a03;

import java.util.HashMap;
import java.util.Map;

public class GameStats {
	private static GameStats _gamestats;
	private Map<String,Integer> _numVals = new HashMap<String,Integer>();
	
	private GameStats() {
	}
	
	public Integer getTimePlayed() {
		return _numVals.get(Stats.APPSTARTTIME.toString()) - (int)(System.currentTimeMillis()/(1000*60));
	}
	
	public Integer getHardHighestScore() {
		return _numVals.get(Stats.HARDHIGHESTSCORE.toString());
	}
	
	public Integer getHardLowestScore() {
		return _numVals.get(Stats.HARDLOWESTSCORE.toString());
	}
	
	public Integer getEasyHighestScore() {
		return _numVals.get(Stats.EASYHIGHESTSCORE.toString());
	}
	
	public Integer getEasyLowestScore() {
		return _numVals.get(Stats.EASYLOWESTSCORE.toString());
	}
	
	public Integer getNumberOfEasyPlays() {
		return _numVals.get(Stats.NUMBEROFEASYPLAYS.toString());
	}
	
	public Integer getNumberOfHardPlays() {
		return _numVals.get(Stats.NUMBEROFHARDPLAYS.toString());
	}
	
	public void updateCumulative(String key, Integer score) {
		_numVals.put(key, _numVals.get(key) + score);
	}
	
	public int getAverageEasyScore() {
		return _numVals.get(Stats.EASYCUMULATIVESCORE.toString()) / _numVals.get(Stats.NUMBEROFEASYPLAYS.toString());
	}
	
	public int getAverageHardScore() {
		return _numVals.get(Stats.HARDCUMULATIVESCORE.toString()) / _numVals.get(Stats.NUMBEROFHARDPLAYS.toString());
	}

	public void update(Level level, int correctAnswers) {
		if(level == Level.EASY) {
			_numVals.put(Stats.NUMBEROFEASYPLAYS.toString(), _numVals.get(Stats.NUMBEROFEASYPLAYS.toString()) + new Integer(1));
			_numVals.put(Stats.EASYCUMULATIVESCORE.toString(), _numVals.get(Stats.EASYCUMULATIVESCORE.toString()) + new Integer(correctAnswers));
			if(correctAnswers > _numVals.get(Stats.EASYHIGHESTSCORE.toString())) {
				_numVals.put(Stats.EASYHIGHESTSCORE.toString(), new Integer(correctAnswers));
			}
			if(correctAnswers < _numVals.get(Stats.EASYLOWESTSCORE.toString())) {
				_numVals.put(Stats.EASYLOWESTSCORE.toString(), new Integer(correctAnswers));
			}
		}else {
			_numVals.put(Stats.NUMBEROFHARDPLAYS.toString(), _numVals.get(Stats.NUMBEROFHARDPLAYS.toString()) + new Integer(1));
			_numVals.put(Stats.HARDCUMULATIVESCORE.toString(), _numVals.get(Stats.HARDCUMULATIVESCORE.toString()) + new Integer(correctAnswers));
			if(correctAnswers > _numVals.get(Stats.HARDHIGHESTSCORE.toString())) {
				_numVals.put(Stats.HARDHIGHESTSCORE.toString(), new Integer(correctAnswers));
			}
			if(correctAnswers < _numVals.get(Stats.HARDLOWESTSCORE.toString())) {
				_numVals.put(Stats.HARDLOWESTSCORE.toString(), new Integer(correctAnswers));
			}
		}
	}
	
	public void updateDiscrete(String key, Integer value) {
		_numVals.put(key, value);
	}
	
	public static GameStats getGameStats() {
		if(_gamestats == null) {
			_gamestats = new GameStats();
			return _gamestats;
		}else {
			return _gamestats;
		}
	}
}
