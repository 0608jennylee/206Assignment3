package a03.view;

import javafx.fxml.FXML;

import a03.MainApp;
import a03.Level;
import javafx.event.ActionEvent;

public class StartController {
	private MainApp _mainApp;
	private Level _level;
	// Event Listener on Button.onAction
	@FXML
	public void handleStart(ActionEvent event) {
		if (_level==Level.EASY){
			_mainApp.Level(_level);
			
		}else if (_level==Level.HARD){
			_mainApp.Level(_level);
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleBack(ActionEvent event) {
		_mainApp.chooseLevels();
	}

	public void setMainApp(MainApp mainApp){
		_mainApp = mainApp;
	}

	public void setLevel(Level level) {
		_level = level;
	}
}
