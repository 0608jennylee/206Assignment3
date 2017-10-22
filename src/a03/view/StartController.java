package a03.view;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import a03.enumerations.Difficulty;
import a03.enumerations.Level;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
/**
 * Controller for the start scene
 * @author jenny
 *
 */
public class StartController extends Controller implements Initializable{
	//fields that are used to switch scenes on the stage
	private Level _level;
	@FXML private Button _back;
	@FXML private ImageView _imageView;
	private Difficulty _difficulty;
	private int _questions;
	/**
	 * when the start button is clicked notifies the stage 
	 * to switch scenes to the level that will be played
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handleStart(ActionEvent event) {
			_mainApp.Game(_difficulty, _level, false, _questions);
	}

	/**
	 * when the mainmenu button is clicked notifies the stage 
	 * to switch scenes back to the main menu
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handleBack(ActionEvent event) {
		_mainApp.chooseDifficulty(_level);
	}
	
	/**
	 * sets the level that the user has chosen so that it can 
	 * be passed onto the next scene
	 * @param level the current level of the game
	 */
	public void setLevel(Level level) {
		_level = level;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File file4 = new File(System.getProperty("user.dir")+"/Icons/png/quit.png");
		Image image4 = new Image(file4.toURI().toString());
		_back.setGraphic(new ImageView(image4));
		File file5 = new File(System.getProperty("user.dir")+"/fern.jpg");
		Image image5 = new Image(file5.toURI().toString());
		_imageView.setImage(image5);
		_imageView.setOpacity(0.32);
		
	}

	public void setDifficulty(Difficulty difficulty) {
		_difficulty=difficulty;
		
	}
	
	public void setQuestions(int questions) {
		_questions = questions;
	}
}
