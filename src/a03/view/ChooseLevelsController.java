package a03.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import a03.Level;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import a03.MainApp;
import javafx.event.ActionEvent;
/**
 * Displays the level available to the user to play
 * @author edwar jenny
 *
 */
public class ChooseLevelsController implements Initializable{
	@FXML private Button _hard;
	private MainApp _mainApp;
	@FXML private ImageView _imageView;
	@FXML private Button _easy;
	@FXML private Button _back;


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
		_hard.setDisable(true);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		_anchor.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		File file4 = new File(System.getProperty("user.dir")+"/Icons/png/quit.png");
		Image image4 = new Image(file4.toURI().toString());
		_back.setGraphic(new ImageView(image4));
		File file5 = new File(System.getProperty("user.dir")+"/fern.jpg");
		Image image5 = new Image(file5.toURI().toString());
		_imageView.setImage(image5);
		_imageView.setOpacity(0.3);
		
	}

}
