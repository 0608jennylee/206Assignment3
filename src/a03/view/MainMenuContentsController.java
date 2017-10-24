package a03.view;

import javafx.fxml.FXML;


import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
/**
 * The main menu controller, the first scene of the mainapp 
 * that has buttons to show all the options
 * @author jenny
 *
 */
public class MainMenuContentsController extends Controller implements Initializable {

	@FXML private JFXButton _playButton;
	@FXML private JFXButton _statisticsButton;
	@FXML private JFXButton _exitButton;
	@FXML private JFXButton _trophyButton;
	@FXML private Button button;
	@FXML private ImageView _imageView;
	/**
	 * when the play button is clicked notifies the stage 
	 * to switch scenes to chooselevels
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handlePlay(ActionEvent event) {
		_mainApp.chooseLevel();
	}

	/**
	 * when the exit button is clicked notifies the stage 
	 * to switch scenes to close the apllication
	 * @param event
	 */
	// Event Listener on Button.onAction
	@FXML
	public void handleExit(ActionEvent event) {
		_mainApp.confirmExit(false);
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
	@FXML
	public void handleCharts(ActionEvent event) {
		_mainApp.Charts();
	}
	@FXML
	public void handleActivityLog(ActionEvent event) {
		_mainApp.Statistics();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image play = new Image(getClass().getClassLoader().getResource("Icons/media-play-6x.png").toString());//
		_playButton.setGraphic(new ImageView(play));
		Image chart = new Image(getClass().getClassLoader().getResource("Icons/bar-chart-6x.png").toString());//
		_statisticsButton.setGraphic(new ImageView(chart));
		Image power = new Image(getClass().getClassLoader().getResource("Icons/power-standby-6x.png").toString());//
		_exitButton.setGraphic(new ImageView(power));
		Image trophy = new Image(getClass().getClassLoader().getResource("Icons/Trophy.png").toString());//
		_trophyButton.setGraphic(new ImageView(trophy));
		Image background = new Image(getClass().getClassLoader().getResource("fern.jpg").toString());//
		_imageView.setImage(background);
		_imageView.setOpacity(0.4);
	}
}
