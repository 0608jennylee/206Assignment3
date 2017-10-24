package a03.view;

import javafx.fxml.FXML;


import a03.GameStats;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class StatisticsController extends Controller{
	@FXML private Label _easyHoursPlayed;
	@FXML private Label _easyTopScore;
	@FXML private Label _EasyLowestScore;
	@FXML private Label _easyAverageScore;
	@FXML private Label _hardHoursPlayed;
	@FXML private Label _hardTopScore;
	@FXML private Label _hardLowestScore;
	@FXML private Label _hardAverageScore;

	// Event Listener on Button.onAction
	@FXML
	public void handleMainMenu(ActionEvent event) {
		_mainApp.mainMenuContents();
	}

//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		setScores();
//	}
	
	public void setScores(){
		_easyTopScore.setText("" + GameStats.getGameStats().getEasyHighestScore());
		_easyHoursPlayed.setText("" + GameStats.getGameStats().getTimePlayed());
		_EasyLowestScore.setText("" + GameStats.getGameStats().getEasyLowestScore());;
		_easyAverageScore.setText("" + GameStats.getGameStats().getAverageEasyScore());
		_hardHoursPlayed.setText("" + GameStats.getGameStats().getTimePlayed());
		_hardTopScore.setText("" + GameStats.getGameStats().getHardHighestScore());
		_hardLowestScore.setText("" + GameStats.getGameStats().getHardLowestScore());
		_hardAverageScore.setText("" + GameStats.getGameStats().getAverageHardScore());
	}
}
