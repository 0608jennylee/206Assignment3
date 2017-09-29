package a03.view;

import javafx.fxml.FXML;
import a03.MainApp;
import javafx.event.ActionEvent;
/**
 * The main menu controller, the first scene of the mainapp 
 * that has buttons to show all the options
 * @author jenny
 *
 */
public class MainMenuContentsController {
	private MainApp _mainApp;
	
	/**
	 * when the play button is clicked notifies the stage 
	 * to switch scenes to chooselevels
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handlePlay(ActionEvent event) {
		_mainApp.chooseLevels();
	}
	
	/**
	 * when the how to play button is clicked notifies the stage 
	 * to switch scenes to the how to play
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handleHowToPlay(ActionEvent event) {
		_mainApp.howToPlay();
	}
	
	/**
	 * when the exit button is clicked notifies the stage 
	 * to switch scenes to close the apllication
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handleExit(ActionEvent event) {
		_mainApp.exit();
	}
	
	/**
	 * when the statistics button is clicked notifies the stage 
	 * to switch scenes to statistics to display the statistics
	 * @param event
	 */
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
