package a03.view;

import java.io.File;


import java.io.IOException;
import java.util.List;

import a03.MainApp;
import a03.Settings;
import a03.GameStats;
import a03.HTKError;
import a03.Level;
import a03.generators.Generator;
import a03.generators.Processor;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
/**
 * this controller, controls the scene for displaying the level
 * @author edwar jenny
 *
 */
public class LessThanTenController {

	@FXML private ImageView _imageView;
	@FXML private ImageView _score;
	@FXML private Button _record;
	@FXML private Button _nextQuestion;
	@FXML private Button _mainMenuTop;
	@FXML private Button _mainMenuBottom;
	@FXML private Button _nextLevel;
	@FXML private Label _theCorrectAnswer;
	@FXML private Label _theirAnswer;
	//@FXML private Label _text;
	@FXML private Label _text1;
	@FXML private Label _text2;
	@FXML private Label _title;
	@FXML private Label _sorry;
	@FXML private Label _weMuckedUp;
	@FXML private Label _tryAgain;

	private boolean _correct=true;
	private boolean _failed=false;
	private boolean _secondTry = false;
	private boolean _tryAgainPressed=true;
	private int _currentQuestion = 0;
	private int _correctAnswers = 0;

	private MainApp _mainApp;
	private List<Integer> _numbers;
	private Level _level;
	private String _display;
	private MediaPlayer mp;
	private Generator _generator;

	/**
	 * constructor for the controller and generates a new generator instance 
	 * for the current instance of lessThanTenController
	 */
	public LessThanTenController() {
		_generator = new Generator();
	}


	/**
	 * when the record button is clicked, the recording starts in a background 
	 * thread, and changes correct based on whether they are wrong or correct
	 * @param event
	 */
	// Event Listener on Button[#_record].onAction
	@FXML
	public void handleRecord(ActionEvent event) {
		if (_secondTry&&_tryAgainPressed||_failed){ //if it fails or is the second try for the user
			setQuestion();
			//if its failed will change the tryAgainPressed to false
			if(!_failed) {
				_tryAgainPressed=false;
			}
			_record.setText("Record");
			_failed=false;
		}else{//recording
			Task<Void> record = new Task<Void>() { 
				@Override
				protected Void call() throws Exception {
					_record.setDisable(true);
					String cmd = "arecord -d 2 -r 22050 -c 1 -i -t wav -f s16_LE " + _numbers.get(_currentQuestion).toString() + ".wav;echo record passed; HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  -w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  user/dictionaryD user/tiedList "+ _numbers.get(_currentQuestion).toString() + ".wav; echo processing passed;";
					ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
					try {
						Process p = pb.start();
						p.waitFor();
						Processor processor = new Processor();
						if(processor.processAnswer(_numbers.get(_currentQuestion))) {
							_correct=true;
						}else {
							_correct=false;
						}
					} catch (IOException IOe) {
						IOe.printStackTrace();
					} catch (HTKError HTKe) {
						_failed = true;
					}
					return null;
				}
			};
			record.setOnSucceeded(this::playBack);
			Thread recordThread = new Thread(record);
			recordThread.start();
		}
	}

	/**
	 * playsback the recording to the user
	 * @param e
	 */
	public void playBack(WorkerStateEvent e) {
		String filepath = _numbers.get(_currentQuestion) + ".wav";
		Media sound = new Media(new File(filepath).toURI().toString());
		mp = new MediaPlayer(sound);
		mp.setOnEndOfMedia(this::check);
		mp.setOnError(this::check);
		mp.play();
	}
	
	/**
	 * checks whether the HTK failed to recognize anything, the user got the 
	 * correct or incorrect answer, then displays the corresponding result to 
	 * the user
	 */

	public void check(){
		_record.setDisable(false);
		if (_failed){//HTK failed
			_sorry.setVisible(true);
			_weMuckedUp.setVisible(true);
			_tryAgain.setVisible(true);
			_record.setText("Try Again");
//			File file = new File(System.getProperty("user.dir")+"/Fail/1.jpg");
			Image file = new Image(getClass().getClassLoader().getResource("Fail/1.jpg").toString());
			//_text.setVisible(true);
			setImage(file);
		}else if(_correct){//user gets correct answer
			//File file = new File(System.getProperty("user.dir")+"/Correct/" + _numbers.get(_currentQuestion) + ".jpg");
//			File file = new File(System.getProperty("user.dir")+"/Correct/" + _numbers.get(_currentQuestion) + ".jpg");
			Image file = new Image(getClass().getClassLoader().getResource("Correct/" + _numbers.get(_currentQuestion) + ".jpg").toString());
			setImage(file);
			_theCorrectAnswer.setText((Processor.toMaori(_numbers.get(_currentQuestion))));
			_theirAnswer.setText((Processor.getUserAnswer()));
			_currentQuestion++;
			_record.setDisable(true);
			if(_currentQuestion != 10) {
				_nextQuestion.setVisible(true);
			}
			_secondTry = false;
			_record.setText("Record");
			_correctAnswers++;

		}else{//user gets incorrect answer
//			File file = new File(System.getProperty("user.dir")+"/Incorrect/" + _numbers.get(_currentQuestion) + ".jpg");
			Image file = new Image(getClass().getClassLoader().getResource("Incorrect/" + _numbers.get(_currentQuestion) + ".jpg").toString());
			setImage(file);
			if (_secondTry){//user gets the answer incorrect the second time
				_theCorrectAnswer.setText((Processor.toMaori(_numbers.get(_currentQuestion))));
				_theirAnswer.setText(Processor.getUserAnswer());
				_secondTry = false;
				_currentQuestion++;
				_record.setDisable(true);
				_record.setText("Record");
				if(_currentQuestion != 10) {
					_nextQuestion.setVisible(true);
				}
				_tryAgainPressed=false;
			}else{//user gets the answer incorrect the first time
				_secondTry = true;
				_theirAnswer.setText(Processor.getUserAnswer());
				_record.setText("Try Again");
				_record.setDisable(false);
				_tryAgainPressed=true;
			}
		}
		if (_currentQuestion==10){//the user has answered the all questions for this level
			displayFinalScore();
		}
	}

	/**
	 * at the end of each game, displays the score, and gives the user 
	 * the option to go back to main menu play again or go to next level 
	 * if they are on easy and have passed it
	 */
	private void displayFinalScore() {
		_title.setVisible(false);
		if(_correctAnswers >= 8&&_level==Level.EASY) {
			Settings.getSettings().enableHard();
			_mainMenuTop.setVisible(true);
			_nextLevel.setVisible(true);
		}else {
			_nextLevel.setText("Play Again");
			_nextLevel.setVisible(true);
		}
		GameStats.getGameStats().update(_level, _correctAnswers);
		_theirAnswer.setText("");
		_theCorrectAnswer.setText("");
		_text1.setText("");
		_text2.setText("");
		_record.setVisible(false);
		_nextQuestion.setVisible(false);
		_imageView.setTranslateY(150);
//		File file = new File(System.getProperty("user.dir")+"/Result/" + _correctAnswers + ".jpg");
		Image file = new Image(getClass().getClassLoader().getResource("Result/" + _correctAnswers + ".jpg").toString());
		setImage(file);
	}

	/**
	 * when the next question button is clicked notifies the stage 
	 * to switch scenes to the level that will be played
	 * @param event
	 */
	@FXML 
	public void handleNextQuestion(){
		setQuestion();
	}

	/**
	 * when the mainmenu button is clicked notifies the stage 
	 * to switch scenes back to the main menu
	 * @param event
	 */
	@FXML
	public void handleMainMenu(){
		_mainApp.mainMenuContents();
	}

	/**
	 * when the next level button is clicked at the end of the level 
	 * notifies the stage to switch scenes to start to create a new instance 
	 * of the level
	 * @param event
	 */
	@FXML
	public void handleNextLevel(){
		if (_level==Level.HARD||(_level==Level.EASY&&_correctAnswers>=8)) {
			_mainApp.Start(Level.HARD);
		}else {
			_mainApp.Start(_level);
		}
	}
	
	/**
	 * sets the level of the current scene
	 * @param level
	 */
	public void setLevel(Level level){
		if (level==Level.HARD){
			_display = "HARD ";
		}else{
			_display = "EASY ";
		}
		_level = level;
		_generator.setLevel(level);
		_numbers = _generator.getNumbers();
		_mainMenuBottom.setVisible(false);
		_mainMenuTop.setVisible(true);
	}
	
	/**
	 * sets the question of the current scene
	 */
	public void setQuestion(){
		_sorry.setVisible(false);
		_weMuckedUp.setVisible(false);
		_tryAgain.setVisible(false);
		_theirAnswer.setText("");
		_theCorrectAnswer.setText("");
		_nextQuestion.setVisible(false);
		//File file = new File(System.getProperty("user.dir")+"/Video/" + _numbers.get(_currentQuestion) + ".jpg");
		Image file = new Image(getClass().getClassLoader().getResource("Video/" + _numbers.get(_currentQuestion) + ".jpg").toString());//
		setImage(file);
		_record.setDisable(false);
	}

	/**
	 * sets the image to be diaplyed
	 * @param file the name of the directory of the image file
	 */
	private void setImage(Image file){
		int display = _currentQuestion+1;
		_title.setText(_display +"Question: "+display);
		_imageView.setImage(file);
	}
	
	/**
	 * sets the mainApp for the controller in order for the 
	 * controller know where to notify the events on the 
	 * start stage
	 * @param mainApp the mainApp that the scene is staged on
	 */public void setMainApp(MainApp mainApp) {
		_mainApp = mainApp;
	}
}
