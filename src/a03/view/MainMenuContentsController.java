package a03.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
	@FXML private JFXButton _playButton;
	@FXML private JFXButton _statisticsButton;
	@FXML private JFXButton _infoButton;
	@FXML private JFXButton _exitButton;
	@FXML private JFXButton _trophyButton;
	@FXML private Button button;
	@FXML private ImageView _imageView;
//	@FXML private AnchorPane _anchor;
	private Stage _stage;
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
		_playButton.setGraphic(new ImageView(image));
		//Image Play = new Image(getClass().getResourceAsStream(System.getProperty("user.dir")+"/Icons/playmini.png"));
		System.out.print(System.getProperty("user.dir")+"/Icons/playmini.png");
		_mainApp.exit(null);
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
	public void handleActivityLog(ActionEvent event) {
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

		//	        _playButton.setStyle(
		//	                "-fx-background-radius: 10em; " +
		//	                "-fx-min-width: 30px; " +
		//	                "-fx-min-height: 30px; " +
		//	                "-fx-max-width: 30px; " +
		//	                "-fx-max-height: 30px;"
		//	        );
		//	        _statisticsButton.setStyle(
		//	                "-fx-background-radius: 10em; " +
		//	                "-fx-min-width: 30px; " +
		//	                "-fx-min-height: 30px; " +
		//	                "-fx-max-width: 30px; " +
		//	                "-fx-max-height: 30px;"
		//	        );
		//	        _infoButton.setStyle(
		//	                "-fx-background-radius: 10em; " +
		//	                "-fx-min-width: 30px; " +
		//	                "-fx-min-height: 30px; " +
		//	                "-fx-max-width: 30px; " +
		//	                "-fx-max-height: 30px;"
		//	        );
		//	        _exitButton.setStyle(
		//	                "-fx-background-radius: 10em; " +
		//	                "-fx-min-width: 30px; " +
		//	                "-fx-min-height: 30px; " +
		//	                "-fx-max-width: 30px; " +
		//	                "-fx-max-height: 30px;"
		//	        );
		//	        _trophyButton.setStyle(
		//	                "-fx-background-radius: 10em; " +
		//	                "-fx-min-width: 30px; " +
		//	                "-fx-min-height: 30px; " +
		//	                "-fx-max-width: 30px; " +
		//	                "-fx-max-height: 30px;"
		//	        );
//		_anchor.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		File file = new File(System.getProperty("user.dir")+"/Icons/png/media-play-4x.png");
		Image image = new Image(file.toURI().toString());
		_playButton.setGraphic(new ImageView(image));
		File file1 = new File(System.getProperty("user.dir")+"/Icons/png/graph-4x.png");
		//button.setGraphic(new ImageView(image));
		Image image1 = new Image(file1.toURI().toString());
		_statisticsButton.setGraphic(new ImageView(image1));
		File file2 = new File(System.getProperty("user.dir")+"/Icons/png/info-4x.png");
		Image image2 = new Image(file2.toURI().toString());
		_infoButton.setGraphic(new ImageView(image2));
		File file3 = new File(System.getProperty("user.dir")+"/Icons/png/power-standby-4x.png");
		Image image3 = new Image(file3.toURI().toString());
		_exitButton.setGraphic(new ImageView(image3));
		File file4 = new File(System.getProperty("user.dir")+"/Icons/icons8-Trophy-48.png");
		Image image4 = new Image(file4.toURI().toString());
		_trophyButton.setGraphic(new ImageView(image4));
		File file5 = new File(System.getProperty("user.dir")+"/fern.jpg");
		Image image5 = new Image(file5.toURI().toString());
		_imageView.setImage(image5);
		_imageView.setOpacity(0.4);
//				_imageView.setPreserveRatio(true); 
//				_imageView.fitWidthProperty().bind(_stage.widthProperty()); 
//				_imageView.fitHeightProperty().bind(_stage.heightProperty());
	}

	public void setStage(Stage stage) {
		_stage=stage;
		_imageView.setPreserveRatio(false); 
		_imageView.fitWidthProperty().bind(_stage.widthProperty()); 
		_imageView.fitHeightProperty().bind(_stage.heightProperty());
	}

}
