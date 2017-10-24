package a03.view;

import java.net.URL;
import java.util.ResourceBundle;

import a03.GameStats;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Displays a set of images to the user to show 
 * them how the application works
 * @author jenny
 *
 */
public class ScoreboardController extends Controller implements Initializable{
	private int _count=1;
	@FXML private Button _next;
	@FXML private Button _previous;
	@FXML private Button _mainMenu;
	@FXML private ImageView _imageView;

	@FXML private Label _hoursPlayed;
	@FXML private Label _topScore;
	@FXML private Label _lowestScore;
	@FXML private Label _averageScore;
	@FXML private Label _title;
	
	/**
	 * when the back button is clicked notifies the stage 
	 * to switch scenes to the main menu
	 * @param event
	 */
	@FXML
	private void handleMainMenu() {
		_mainApp.mainMenuContents();
	}
	
	/**
	 * when the start button is clicked, changes 
	 * the image to the one previous one in line
	 * @param event
	 */
	@FXML
	public void handlePrevious(){
		if (_count<=4){
			_next.setDisable(false);
		}
		_count--;
		setScoreBoard();
		if (_count==1){
			_previous.setDisable(true);
		}
	}
	


	/**
	 * when the start button is clicked, changes 
	 * the image to the one next in line
	 * @param event
	 */
	@FXML
	public void handleNext(){
		if (_count>=1){
			_previous.setDisable(false);
		}
		_count++;
		setScoreBoard();
		if (_count==4){
			_next.setDisable(true);
		}
	}

	/**
	 * Sets the first image of the scene and shows the image, 
	 * disables the previous button ast this is the first image 
	 * and there is no previous image
	 */
	public void setScoreBoard() {
		if (_count==1) {
			_title.setText("Numbers: Easy Scoreboard");
			_topScore.setText("" + GameStats.getGameStats().getEasyNumberHighestScore());
			_hoursPlayed.setText("" + GameStats.getGameStats().getTimePlayed());
			_lowestScore.setText("" + GameStats.getGameStats().getEasyNumberLowestScore());;
			_averageScore.setText("" + GameStats.getGameStats().getAverageEasyNumberScore());
			
		}else if (_count==2) {
			_title.setText("Numbers: Hard Scoreboard");

			_topScore.setText("" + GameStats.getGameStats().getHardNumberHighestScore());
			_hoursPlayed.setText("" + GameStats.getGameStats().getTimePlayed());
			_lowestScore.setText("" + GameStats.getGameStats().getHardNumberLowestScore());;
			_averageScore.setText("" + GameStats.getGameStats().getAverageHardNumberScore());
			
		}else if (_count==3) {
			_title.setText("Equations: Easy Scoreboard");

			_topScore.setText("" + GameStats.getGameStats().getEasyEquationHighestScore());
			_hoursPlayed.setText("" + GameStats.getGameStats().getTimePlayed());
			_lowestScore.setText("" + GameStats.getGameStats().getEasyEquationLowestScore());;
			_averageScore.setText("" + GameStats.getGameStats().getAverageEasyEquationScore());
			
		}else {
			_title.setText("Equations: Hard Scoreboard");

			_topScore.setText("" + GameStats.getGameStats().getHardEquationHighestScore());
			_hoursPlayed.setText("" + GameStats.getGameStats().getTimePlayed());
			_lowestScore.setText("" + GameStats.getGameStats().getHardEquationLowestScore());;
			_averageScore.setText("" + GameStats.getGameStats().getAverageHardEquationScore());
			
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image right = new Image(getClass().getClassLoader().getResource("Icons/right.png").toString());//
		_next.setGraphic(new ImageView(right));
		Image left = new Image(getClass().getClassLoader().getResource("Icons/left.png").toString());//
		_previous.setGraphic(new ImageView(left));
		Image quit = new Image(getClass().getClassLoader().getResource("Icons/quit.png").toString());//
		_mainMenu.setGraphic(new ImageView(quit));
		Image background = new Image(getClass().getClassLoader().getResource("fern.jpg").toString());//
		_imageView.setImage(background);
		_imageView.setOpacity(0.2);
		
		_title.setText("Numbers: Easy Scoreboard");
		_topScore.setText("" + GameStats.getGameStats().getEasyNumberHighestScore());
		_hoursPlayed.setText("" + GameStats.getGameStats().getTimePlayed());
		_lowestScore.setText("" + GameStats.getGameStats().getEasyNumberLowestScore());;
		_averageScore.setText("" + GameStats.getGameStats().getAverageEasyNumberScore());
		_previous.setDisable(true);
	}

}