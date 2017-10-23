package a03.view;

import javafx.fxml.FXML;


import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import a03.enumerations.Difficulty;
import a03.enumerations.Level;
import javafx.event.ActionEvent;
/**
 * Displays the level available to the user to play

 * @author edwar jenny
 *
 */

public class ChooseDifficultyController extends Controller implements Initializable{

	@FXML private Button _hard;
	@FXML private ImageView _imageView;
	@FXML private Button _easy;
	@FXML private Button _back;
	private Level _level;


	/**
	 * when the easy button is clicked notifies the stage 
	 * to switch scenes to level with easy as the parameter for level chosen
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handleEasySelection(ActionEvent event) {
		if((new File("Saves/" + _level.toString() + Difficulty.EASY + ".dat").exists())) {
			_mainApp.LoadLevel(_level,Difficulty.EASY);
		}else {
			_mainApp.Start(_level,Difficulty.EASY, 10);
		}
	}

	/**
	 * when the hard button is clicked notifies the stage 
	 * to switch scenes to level with hard as the parameter for level chosen
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handleHardSelection(ActionEvent event) {
		//TODO
		if(new File("Saves/" + _level.toString() + Difficulty.HARD + ".dat").exists()) {
			_mainApp.LoadLevel(_level,Difficulty.HARD);
		}else {
			_mainApp.Start(_level,Difficulty.HARD, 10);
		}
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
		_hard.setDisable(true);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//		_anchor.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		File file4 = new File(System.getProperty("user.dir")+"/Icons/png/quit.png");
		Image image4 = new Image(file4.toURI().toString());
		_back.setGraphic(new ImageView(image4));
		File file5 = new File(System.getProperty("user.dir")+"/fern.jpg");
		Image image5 = new Image(file5.toURI().toString());
		_imageView.setImage(image5);
		_imageView.setOpacity(0.36);

	}

	public void setLevel(Level level) {
		_level=level;

	}

}
