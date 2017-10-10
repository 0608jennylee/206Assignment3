package a03.view;

import javafx.fxml.FXML;
import a03.MainApp;
import a03.Level;
import javafx.event.ActionEvent;
/**
 * Controller for the start scene
 * @author jenny
 *
 */
public class StartController {
	//fields that are used to switch scenes on the stage
	private MainApp _mainApp;
	private Level _level;

	/**
	 * when the start button is clicked notifies the stage 
	 * to switch scenes to the level that will be played
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handleStart(ActionEvent event) {
//		if (_level==Level.EASYNUMBERS){//starts the game with easy level for the scene
			_mainApp.Level(_level);
//		}else if (_level==Level.HARDNUMBERS){//starts the game with hard level for the scene
//			_mainApp.Level(_level);
//		}
	}

	/**
	 * when the mainmenu button is clicked notifies the stage 
	 * to switch scenes back to the main menu
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handleBack(ActionEvent event) {
		_mainApp.chooseLevels();
	}
	
	/**
	 * sets the level that the user has chosen so that it can 
	 * be passed onto the next scene
	 * @param level the current level of the game
	 */
	public void setLevel(Level level) {
		_level = level;
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
