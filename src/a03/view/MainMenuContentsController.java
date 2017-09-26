package a03.view;

import javafx.fxml.FXML;
import a03.MainApp;
import javafx.event.ActionEvent;

public class MainMenuContentsController {

	private MainApp _mainApp;
	
	// Event Listener on Button.onAction
	@FXML
	public void handlePlay(ActionEvent event) {
		_mainApp.chooseLevels();
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void handleHowToPlay(ActionEvent event) {
		_mainApp.howToPlay();
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void handleExit(ActionEvent event) {
		_mainApp.exit();
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void handleStatistics(ActionEvent event) {
		_mainApp.Statistics();
	}
	
	/**
	 * Sets the mainApp for the MainContentsController
	 * @param mainApp the mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		_mainApp = mainApp;
	}
}
