package a03.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import a03.enumerations.Difficulty;
import a03.enumerations.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ScoreSceneController extends Controller implements Initializable{
	@FXML private ImageView _imageView;
	@FXML private ImageView _scoreImageView;
	@FXML private Button _nextLevel;
	@FXML private Button _mainMenu;
	@FXML private Button _playAgain;
	private int _score;
	private Difficulty _difficulty;
	private Level _level;
	@FXML public void handleNextLevel() {
		_mainApp.Game(Difficulty.HARD, _level);
	}
	@FXML public void handleMainMenu() {
		_mainApp.mainMenuContents();
	}
	@FXML public void handlePlayAgain() {
		_mainApp.Game(_difficulty, _level);
	}
	public void setScore(int score) {
		_score=score;
		Image q = new Image(getClass().getClassLoader().getResource("Result/"+_score+".jpg").toString());//
		_scoreImageView.setImage(q);
		if (_difficulty==Difficulty.HARD||_score<8) {
			_nextLevel.setVisible(false);
		}
	}
	public void setDifficulty(Difficulty difficulty, Level level) {
		_difficulty=difficulty;
		_level=level;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File file5 = new File(System.getProperty("user.dir")+"/fern.jpg");
		Image image5 = new Image(file5.toURI().toString());
		_imageView.setImage(image5);
		_imageView.setOpacity(0.2);
		File file4 = new File(System.getProperty("user.dir")+"/Icons/png/quit.png");
		Image image4 = new Image(file4.toURI().toString());
		_mainMenu.setGraphic(new ImageView(image4));
	}
			
}
