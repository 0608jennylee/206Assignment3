package a03.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import a03.MainApp;
import a03.MainApp.Level;
import javafx.event.ActionEvent;

public class ChooseLevelsController {
	
	@FXML Button Hard;
	
	public void disableHard() {
		Hard.setDisable(true);
	}

	private MainApp _mainApp;
	// Event Listener on Button.onAction
	@FXML
	public void handleEasySelection(ActionEvent event) {
		_mainApp.Start(Level.easy);
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleHardSelection(ActionEvent event) {
		_mainApp.Start(Level.hard);
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleBack(ActionEvent event) {
		_mainApp.mainMenuContents();
	}
	
	public void setMainApp(MainApp mainApp){
		_mainApp = mainApp;
	}
	
}
