package a03.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import a03.MainApp;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class StatisticsController implements Initializable{
	@FXML private Label _easyHoursPlayed;
	@FXML private Label _easyTopScore;
	@FXML private Label _EasyLowestScore;
	@FXML private Label _easyAverageScore;
	@FXML private Label _hardHoursPlayed;
	@FXML private Label _hardTopScore;
	@FXML private Label _hardLowestScore;
	@FXML private Label _hardAverageScore;
	private MainApp _mainApp;

	// Event Listener on Button.onAction
	@FXML
	public void handleMainMenu(ActionEvent event) {
		_mainApp.mainMenuContents();
	}
	
	public void setMainApp(MainApp mainApp){
		_mainApp = mainApp;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setScores();
	}
	public void setScores(){
//		_easyHoursPlayed;
//		_easyTopScore;
//		_EasyLowestScore;
//		_easyAverageScore;
//		_hardHoursPlayed;
//		_hardTopScore;
//		_hardLowestScore;
//		_hardAverageScore;
	}
}
