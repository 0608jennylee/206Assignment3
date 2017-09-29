package a03.view;

import javafx.fxml.FXML;
import a03.Level;
import javafx.scene.control.Button;
import a03.MainApp;
import javafx.event.ActionEvent;
/**
 * Displays the level available to the user to play
 * @author edwar jenny
 *
 */
public class ChooseLevelsController {
	@FXML Button Hard;
	private MainApp _mainApp;

	/**
	 * when the easy button is clicked notifies the stage 
	 * to switch scenes to level with easy as the parameter for level chosen
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handleEasySelection(ActionEvent event) {
		_mainApp.Start(Level.EASY);
	}

	/**
	 * when the hard button is clicked notifies the stage 
	 * to switch scenes to level with hard as the parameter for level chosen
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handleHardSelection(ActionEvent event) {
		_mainApp.Start(Level.HARD);
	}

	/**
	 * when the mainmenu button is clicked notifies the stage 
	 * to switch scenes back to the main menu
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handleBack(ActionEvent event) {
		_mainApp.mainMenuContents();
	}
	
	/**
	 * disables the hard button
	 */
	public void disableHard() {
		Hard.setDisable(true);
	}
	
	/**
	 * sets the mainApp for the controller in order for the 
	 * controller know where to notify the events on the 
	 * start stage
	 * @param mainApp the mainApp that the scene is staged on
	 */
	public void setMainApp(MainApp mainApp){
		_mainApp = mainApp;
	}

}
