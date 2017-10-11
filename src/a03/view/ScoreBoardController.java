package a03.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import a03.GameStats;
import a03.MainApp;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ScoreBoardController extends Controller implements Initializable{
	@FXML private Label _easyHoursPlayed;
	@FXML private Label _easyTopScore;
	@FXML private Label _EasyLowestScore;
	@FXML private Label _easyAverageScore;
	@FXML private Label _hardHoursPlayed;
	@FXML private Label _hardTopScore;
	@FXML private Label _hardLowestScore;
	@FXML private Label _hardAverageScore;
	@FXML private Button _back;
	@FXML private ImageView _imageView;

	// Event Listener on Button.onAction
	@FXML
	public void handleBack(ActionEvent event) {
		_mainApp.mainMenuContents();
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
		_imageView.setOpacity(0.05);

	}
	
	public void setScores(){
		_easyTopScore.setText(GameStats.getGameStats().getEasyHighestScore().toString());
		_easyHoursPlayed.setText(GameStats.getGameStats().getTimePlayed().toString());
		_EasyLowestScore.setText(GameStats.getGameStats().getEasyLowestScore().toString());;
		_easyAverageScore.setText("" + GameStats.getGameStats().getAverageEasyScore());
		_hardHoursPlayed.setText(GameStats.getGameStats().getTimePlayed().toString());
		_hardTopScore.setText(GameStats.getGameStats().getHardHighestScore().toString());
		_hardLowestScore.setText(GameStats.getGameStats().getHardLowestScore().toString());
		_hardAverageScore.setText("" + GameStats.getGameStats().getAverageHardScore());
	}
}
