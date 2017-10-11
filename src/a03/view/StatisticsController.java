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
		_easyTopScore.setText(GameStats.getGameStats().getEasyHighestScore().toString());
		_easyHoursPlayed.setText(GameStats.getGameStats().getTimePlayed().toString());
		_EasyLowestScore.setText(GameStats.getGameStats().getEasyLowestScore().toString());;
		_easyAverageScore.setText("" + GameStats.getGameStats().getAverageEasyScore());
		_hardHoursPlayed.setText(GameStats.getGameStats().getTimePlayed().toString());
		_hardTopScore.setText(GameStats.getGameStats().getHardHighestScore().toString());
		_hardLowestScore.setText(GameStats.getGameStats().getHardLowestScore().toString());
		_hardAverageScore.setText("" + GameStats.getGameStats().getAverageHardScore());
	}
}
