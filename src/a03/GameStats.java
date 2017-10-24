package a03;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import a03.enumerations.Difficulty;
import a03.enumerations.Level;

public class GameStats implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7414992782325115224L;
	private int _hardNumberHighestScore, _easyNumberHighestScore, _hardEquationHighestScore, _easyEquationHighestScore, _hardEquationLowestScore, _easyEquationLowestScore, _hardNumberLowestScore, _easyNumberLowestScore, _numberOfEasyNumberPlays,
	_numberOfHardNumberPlays, _numberOfHardEquationPlays, _numberOfEasyEquationPlays, _easyNumberCumulativeScore, _hardNumberCumulativeScore, _easyEquationCumulativeScore, _hardEquationCumulativeScore;
	private final transient int APP_START_TIME = (int) (System.currentTimeMillis() / (1000 * 60));
	private static transient GameStats _gamestats = null;
	private final static transient String SAVE = "GameStat.json";
	private boolean _isFirstEasyNumber = true;
	private boolean _isFirstHardNumber = true;
	private boolean _isFirstEasyEquation = true;
	private boolean _isFirstHardEquation = true;
	private transient Gson g = new Gson();
	
	/**
	 * Game stats class is a singleton, hence private constructor.
	 */
	private GameStats() {
	}
	
	public int getTimePlayed() {
		return (int) (System.currentTimeMillis() / (1000 * 60)- APP_START_TIME);
	}
	
	public int getHardNumberHighestScore() {
		return _hardNumberHighestScore;
	}
	
	
	public int getHardNumberLowestScore() {
		return _hardNumberLowestScore;
	}
	
	public int getHardEquationHighestScore() {
		return _hardEquationHighestScore;
	}
	
	public int getHardEquationLowestScore() {
		return _hardEquationLowestScore;
	}
	
	public int getEasyNumberHighestScore() {
		return _easyNumberHighestScore;
	}
	
	public int getEasyNumberLowestScore() {
		return _easyNumberLowestScore;
	}
	
	public int getEasyEquationHighestScore() {
		return _easyEquationHighestScore;
	}
	
	public int getEasyEquationLowestScore() {
		return _easyEquationLowestScore;
	}
	
	public int getNumberOfEasyNumberPlays() {
		return _numberOfEasyNumberPlays;
	}
	
	public int getNumberOfHardNumberPlays() {
		return _numberOfHardNumberPlays;
	}
	
	public int getNumberOfEasyEquationPlays() {
		return _numberOfEasyEquationPlays;
	}
	
	public int getNumberOfHardEquationPlays() {
		return _numberOfHardEquationPlays;
	}
	
//	public void updateCumulative(String key, Integer score) {
//		_numVals.put(key, _numVals.get(key) + score);
//	}
	
	public int getAverageEasyNumberScore() {
		if(_numberOfEasyNumberPlays == 0) {
			return 0;
		}else {
			return _easyNumberCumulativeScore / _numberOfEasyNumberPlays;
		}

	}
	
	public int getAverageHardNumberScore() {
		if(_numberOfHardNumberPlays == 0) {
			return 0;
		}else {
			return _hardNumberCumulativeScore / _numberOfHardNumberPlays;
		}
	}
	
	public int getAverageEasyEquationScore() {
		if(_numberOfEasyEquationPlays == 0) {
			return 0;
		}else {
			return _easyEquationCumulativeScore / _numberOfEasyEquationPlays;
		}
	}
	
	public int getAverageHardEquationScore() {
		if(_numberOfHardEquationPlays == 0) {
			return 0;
		}else {
			return _hardEquationCumulativeScore / _numberOfHardEquationPlays;
		}
	}
	
	public int getEasyNumberCumulativeScore() {
		return _easyNumberCumulativeScore;
	}
	
	public int getHardNumberCumulativeScore() {
		return _hardNumberCumulativeScore;
	}
	
	public int getEasyEquationCumulativeScore() {
		return _easyEquationCumulativeScore;
	}
	
	public int getHardEquationCumulativeScore() {
		return _hardEquationCumulativeScore;
	}

	public void update(Difficulty difficulty, Level level, int correctAnswers) {
		if(difficulty == Difficulty.EASY) {
			if(level == Level.NUMBERS) {
				_numberOfEasyNumberPlays++;
				_easyNumberCumulativeScore += correctAnswers;
				if(correctAnswers > _easyNumberHighestScore) {
					_easyNumberHighestScore = correctAnswers;
				}
				if(correctAnswers < _easyNumberLowestScore || _isFirstEasyNumber) {
					if(_isFirstEasyNumber) {
						_isFirstEasyNumber = false;
					}
					_easyNumberLowestScore = correctAnswers;
				}
			}else {
				_numberOfEasyEquationPlays++;
				_easyEquationCumulativeScore += correctAnswers;
				if(correctAnswers > _easyEquationHighestScore) {
					_easyEquationHighestScore = correctAnswers;
				}
				if(correctAnswers < _easyEquationLowestScore || _isFirstEasyEquation) {
					if(_isFirstEasyEquation) {
						_isFirstEasyEquation = false;
					}
					_easyEquationLowestScore = correctAnswers;
				}
			}
		}else {
			if(level == Level.NUMBERS) {
				_numberOfHardNumberPlays++;
				_hardNumberCumulativeScore += correctAnswers;
				if(correctAnswers > _hardNumberHighestScore) {
					_hardNumberHighestScore = correctAnswers;
				}
				if(correctAnswers < _hardNumberLowestScore || _isFirstHardNumber) {
					if(_isFirstHardNumber) {
						_isFirstHardNumber = false;
					}
					_hardNumberLowestScore = correctAnswers;
				}
			}else {
				_numberOfHardEquationPlays++;
				_hardEquationCumulativeScore += correctAnswers;
				if(correctAnswers > _hardEquationHighestScore) {
					_hardEquationHighestScore = correctAnswers;
				}
				if(correctAnswers < _hardEquationLowestScore || _isFirstHardEquation) {
					if(_isFirstHardEquation) {
						_isFirstHardEquation = false;
					}
					_hardEquationLowestScore = correctAnswers;
				}
			}
		}
	}
	
	public static GameStats getGameStats() {
		if(_gamestats == null) {
			_gamestats = new GameStats();
			if(new File(SAVE).exists()) {
				_gamestats.load();
			}
			return _gamestats;
		}else {
			return _gamestats;
		}
	}
	
	private void load() {
		try {
			JsonReader reader = new JsonReader(new FileReader(SAVE));
			GameStats gameStats = g.fromJson(reader, GameStats.class);
			_hardNumberHighestScore = gameStats.getHardNumberHighestScore();
			_easyNumberHighestScore = gameStats.getEasyNumberHighestScore();
			_hardEquationHighestScore = gameStats.getHardEquationHighestScore();
			_easyEquationHighestScore = gameStats.getEasyEquationHighestScore();
			_hardNumberLowestScore = gameStats.getHardNumberLowestScore();
			_easyNumberLowestScore = gameStats.getEasyNumberLowestScore();
			_hardEquationLowestScore = gameStats.getHardEquationLowestScore();
			_easyEquationLowestScore = gameStats.getEasyEquationLowestScore();
			_numberOfEasyNumberPlays = gameStats.getNumberOfEasyNumberPlays();
			_numberOfHardNumberPlays = gameStats.getNumberOfHardNumberPlays();
			_numberOfEasyEquationPlays = gameStats.getNumberOfEasyEquationPlays();
			_numberOfHardEquationPlays = gameStats.getNumberOfHardEquationPlays();
			_easyNumberCumulativeScore = gameStats.getEasyNumberCumulativeScore();
			_hardNumberCumulativeScore = gameStats.getHardNumberCumulativeScore();
			_easyEquationCumulativeScore = gameStats.getEasyEquationCumulativeScore();
			_hardEquationCumulativeScore = gameStats.getHardEquationCumulativeScore();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save() {
		String j = g.toJson(this);
		try (FileWriter filewriter = new FileWriter(SAVE)){
			filewriter.write(j.toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
