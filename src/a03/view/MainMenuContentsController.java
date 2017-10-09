package a03.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import a03.MainApp;
import javafx.event.ActionEvent;
/**
 * The main menu controller, the first scene of the mainapp 
 * that has buttons to show all the options
 * @author jenny
 *
 */
public class MainMenuContentsController implements Initializable {
	private MainApp _mainApp;
	@FXML private JFXButton _random;
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
		File file = new File(System.getProperty("user.dir")+"/Icons/playlarge.png");
		Image image = new Image(file.toURI().toString());
		_random.setGraphic(new ImageView(image));
		//Image Play = new Image(getClass().getResourceAsStream(System.getProperty("user.dir")+"/Icons/playmini.png"));
		System.out.print(System.getProperty("user.dir")+"/Icons/playmini.png");
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	        _random.setStyle(
	                "-fx-background-radius: 10em; " +
	                "-fx-min-width: 30px; " +
	                "-fx-min-height: 30px; " +
	                "-fx-max-width: 30px; " +
	                "-fx-max-height: 30px;"
	        );
		File file = new File(System.getProperty("user.dir")+"/Icons/playlarge.png");
		Image image = new Image(file.toURI().toString());
		_random.setGraphic(new ImageView(image));
	}

}
