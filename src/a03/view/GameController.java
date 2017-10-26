package a03.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import a03.Saveable;
import a03.enumerations.Difficulty;
import a03.enumerations.Level;
import a03.errors.HTKError;
import a03.GameStats;
import a03.LogData;
import a03.generators.Generator;
import a03.generators.GeneratorFactory;
import a03.generators.Processor;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
/**
 * this controller, controls the scene for displaying the level
 * @author edwar jenny
 *
 */
public class GameController extends Controller implements Initializable, Saveable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7763569242616710859L;

	private enum Correctness{CORRECT,INCORRECT}
	@FXML private transient ProgressBar _progressBar;

	@FXML private transient Button _record;
	@FXML private transient Button _submit;
	@FXML private transient Button _playback;
	@FXML private transient Button _nextQuestion;
	@FXML private transient Button _back;
	@FXML private transient Label _theCorrectAnswer;
	@FXML private transient Label _theirAnswer;
	@FXML private transient Label _question;
	@FXML private transient Label _text1;
	@FXML private transient Label _text2;
	@FXML private transient Label _title;
	@FXML private transient ImageView _score;
	@FXML private transient ImageView _imageView;
	@FXML private transient ImageView _Q1, _A1;
	@FXML private transient ImageView _Q2, _A2;
	@FXML private transient ImageView _Q3, _A3;
	@FXML private transient ImageView _Q4, _A4;
	@FXML private transient ImageView _Q5, _A5;
	@FXML private transient ImageView _Q6, _A6;
	@FXML private transient ImageView _Q7;
	@FXML private transient ImageView _A7;
	@FXML private transient ImageView _Q8;
	@FXML private transient ImageView _A8;
	@FXML private transient ImageView _Q9;
	@FXML private transient ImageView _A9;
	@FXML private transient ImageView _Q10;
	@FXML private transient ImageView _A10;
	@FXML private transient BorderPane _root;

	
	private boolean _correct=true;
	private boolean _failed=false;
	private boolean _secondTry = false;
	private boolean _tryAgainPressed=true;
	private int _currentQuestion = 0;
	private int _correctAnswers = 0;

	private List<String> _numbers;
	private Level _level;
	private String _display;
	private transient MediaPlayer mp;
	private transient Generator _generator;

	private Difficulty _difficulty;
	private final transient String SAVEFOLDER = "Saves";
	private String RECORDINGSFOLDER = "";
	private int _totalQuestions = 10;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image right = new Image(getClass().getClassLoader().getResource("Icons/right.png").toString());//
		_nextQuestion.setGraphic(new ImageView(right));
		recordButton();
		Image listen = new Image(getClass().getClassLoader().getResource("Icons/bullhorn-6x.png").toString());//
		_playback.setGraphic(new ImageView(listen));
		Image quit = new Image(getClass().getClassLoader().getResource("Icons/quit.png").toString());//
		_back.setGraphic(new ImageView(quit));
		Image background = new Image(getClass().getClassLoader().getResource("fern.jpg").toString());//
		_imageView.setImage(background);
		_imageView.setOpacity(0.3);
		_progressBar.setVisible(false);
	}

	private void initProgressBar() {
		Image q = new Image(getClass().getClassLoader().getResource("Progress/q.1.png").toString());//
		_A1.setImage(q);
		_A2.setImage(q);
		_A3.setImage(q);
		_A4.setImage(q);
		_A5.setImage(q);
		_A6.setImage(q);
		_A7.setImage(q);
		_A8.setImage(q);
		_A9.setImage(q);
		_A10.setImage(q);
	}

	@Override
	public void setMainAppHook() {
		RECORDINGSFOLDER = "Recordings/" + _level.toString() + _difficulty.toString() + "/";
	}
	
	/**
	 * when the record button is clicked, the recording starts in a background 
	 * thread, and changes correct based on whether they are wrong or correct
	 * @param event
	 */
	// Event Listener on Button[#_record].onAction
	@FXML
	public void handleRecord(ActionEvent event) {
		//!!!! this methods multithreading violates concurrency rules. The bcakground thread makes changes to the gui.
		
		//For the purpose of not cluttering the directory the jar is in check if the specified recording saving directory exists, if not then create the hierarchy.
		if(!new File(RECORDINGSFOLDER).exists()) {
			new File(RECORDINGSFOLDER).mkdirs();
		}
		
		//Disable the submit and playback button to not create concurrency problems by both trying to write and read the audio file. Also helps prevent user error.
		_playback.setDisable(true);
		_submit.setDisable(true);
		
		if (_secondTry&&_tryAgainPressed||_failed){ //if it fails or is the second try for the user. Enters this if statement IF the button is in try again state rather than record.
			
			setQuestion();
			
			//if its failed will change the tryAgainPressed to false
			if(!_failed) {
				_tryAgainPressed=false;
			}
			
			//set to record button.
			recordButton();
			_failed=false;
			
		}else{//recording
			
			//multithreading recording on background as to not freeze gui
			Task<Void> record = new Task<Void>() { 
				@Override
				protected Void call() throws Exception {
					
					_progressBar.setVisible(true);
					//disable the record button to not allow the user to trigger numerous recording threads.
					_record.setDisable(true);
					
					//Using system call to arecord to record audio.
					String cmd = "arecord -d 2 -r 22050 -c 1 -i -t wav -f s16_LE " + RECORDINGSFOLDER + Processor.toInt(_numbers.get(_currentQuestion)) + ".wav;echo record passed; HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  -w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  user/dictionaryD user/tiedList " + RECORDINGSFOLDER + Processor.toInt(_numbers.get(_currentQuestion)) + ".wav; echo processing passed;";
					ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
					try {
						Process p = pb.start();
						p.waitFor();
						
						//after the recording has finished reenable the buttons and hide the progress bar.
						_playback.setDisable(false);
						_submit.setDisable(false);
						_record.setDisable(false);
						_progressBar.setVisible(false);
					} catch (IOException IOe) {
						IOe.printStackTrace();
					}
					return null;
				}
			};
			Thread recordThread = new Thread(record);
			recordThread.start();
		}
	}

	/**
	 * playsback the recording to the user
	 * @param e
	 */
	public void handlePlayback(ActionEvent e) {
		
		//disables the buttons to prevent concurrency problems writing and reading simultaneously, also prevent user error via button locking.
		//buttons are reenabled in the enable().
		_record.setDisable(true);
		_playback.setDisable(true);
		_submit.setDisable(true);
		String filepath = RECORDINGSFOLDER + Processor.toInt(_numbers.get(_currentQuestion)) + ".wav";
		Media sound = new Media(new File(filepath).toURI().toString());
		mp = new MediaPlayer(sound);
		mp.setOnEndOfMedia(this::enable);
		mp.play();
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
	public void handleBack(){
		_mainApp.confirmExit(true);
	}

	//Event handler for when the submit button is pressed.
	@FXML
	public void handleSubmit() {
		try {
			Processor processor = new Processor();
			if(processor.processAnswer(Processor.toInt(_numbers.get(_currentQuestion)))) {
				_correct=true;
			}else {
				_correct=false;
			}
		}catch(HTKError e) {
			_failed = true;
		}finally {
			check();
		}
	}

	/**
	 * at the end of each game, displays the score, and gives the user 
	 * the option to go back to main menu play again or go to next level 
	 * if they are on easy and have passed it
	 */
	private void displayFinalScore() {
		boolean flag = new File("Logs/" + _level.toString() + _difficulty.toString() + "History.dat").exists();
		GameStats.getGameStats().update(_difficulty,_level, _correctAnswers);
		Gson g = new Gson();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM HH:mm");
		LocalDateTime localdatetime = LocalDateTime.now();
		String j = g.toJson(new LogData(_correctAnswers, _totalQuestions, _level, _difficulty, dtf.format(localdatetime)));
		if(!new File("Logs").exists()) {
			new File("Logs").mkdir();
		}
	
		try (FileWriter filewriter = new FileWriter("Logs/" + _level.toString() + _difficulty.toString() + "History.dat", true)){
			if(flag) {
				filewriter.append(System.lineSeparator());
			}
			filewriter.append(j.toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		GameStats.getGameStats().update(_difficulty,_level, _correctAnswers);
		_mainApp.Score(_correctAnswers,_totalQuestions, _difficulty, _level);
	}

	/**
	 * a helper method that sets the buttons into a correct state after playback.
	 */
	private void enable() {
		_record.setDisable(false);
		_playback.setDisable(false);
		_submit.setDisable(false);
	}

	/**
	 * checks whether the HTK failed to recognize anything, the user got the 
	 * correct or incorrect answer, then displays the corresponding result to 
	 * the user
	 */

	public void check(){
		
		//Button lock to prevent multithread accessing the same resource.
		_submit.setDisable(true);
		_record.setDisable(true);
		_playback.setDisable(true);
		
		if (_failed){
			
			//makes the questions border go yellow to indicate a non user fault.
			
			colorFailed();
			tryAgain();
			_record.setDisable(false);
			_playback.setDisable(true);
			_submit.setDisable(true);
		}else if(_correct){ //user gets correct answer
			
			//makes the label border green to indicate the answer was correct.
			colorCorrect();
			
			if(_difficulty != Difficulty.CUSTOM) {
				setProgress(Correctness.CORRECT);
			}
			
			delete();
			
			_theCorrectAnswer.setText((Processor.toMaori(Processor.toInt(_numbers.get(_currentQuestion)))));
			_theirAnswer.setText((Processor.getUserAnswer()));
			
			_currentQuestion++;
			
			if(_currentQuestion != _totalQuestions) {
				_nextQuestion.setVisible(true);
			}
			
			_secondTry = false;
			recordButton();
			_correctAnswers++;

		}else{//user gets incorrect answer
			
			colorWrong();
			
			if (_secondTry){//user gets the answer incorrect the second time
				
				if(_difficulty != Difficulty.CUSTOM) {
					setProgress(Correctness.INCORRECT);
				}
				
				delete();
				_theCorrectAnswer.setText((Processor.toMaori(Processor.toInt(_numbers.get(_currentQuestion)))));
				_theirAnswer.setText(Processor.getUserAnswer());
				_secondTry = false;
				_currentQuestion++;
				_record.setDisable(true);
				recordButton();
				
				if(_currentQuestion != _totalQuestions) {
					_nextQuestion.setVisible(true);
				}
				
				_tryAgainPressed=false;
				
			}else{//user gets the answer incorrect the first time
				
				_secondTry = true;
				_theirAnswer.setText(Processor.getUserAnswer());
				tryAgain();
				_record.setDisable(false);
				_tryAgainPressed=true;
				
			}
		}
		
		//the user has answered the all questions for this level
		if (_currentQuestion==_totalQuestions){
			displayFinalScore();
		}
		
	}
	
	//The method sets the color of the labels border depending on the outcome of the question.
	private	void colorCorrect() {
		_question.setStyle("-fx-border-color: green; -fx-border-width: 5; -fx-border-radius: 5;");
	}

	private void colorWrong() {
		_question.setStyle("-fx-border-color: red; -fx-border-width: 5; -fx-border-radius: 5;");
	}
	
	private void defaultColor() {
		_question.setStyle("-fx-border-color: transparent;");
	}

	private void colorFailed(){
		_question.setStyle("-fx-border-color: yellow; -fx-border-width: 5; -fx-border-radius: 5;");
	}
	
	/**
	 * this method is called to delete the recording that was made this is to prevent recording persistence messing up as you could replay previous records
	 * as they shared the same name. This is also for a space saving effect and to not clutter the file hierarchy.
	 */
	private void delete() {
		File recordings = new File(RECORDINGSFOLDER + Processor.toInt(_numbers.get(_currentQuestion)) + ".wav");
		recordings.delete();
	}
	
	/**
	 * Sets the image of the _record button to a try again icon. This button doubles as a recording button.
	 */
	public void tryAgain() {
		Image quit = new Image(getClass().getClassLoader().getResource("Icons/reload-6x.png").toString());//
		_record.setGraphic(new ImageView(quit));

	}
	
	/**
	 * Changes the image of the _record button to a microphone. This button doubles as a retry icon.
	 */
	public void recordButton() {
		Image quit = new Image(getClass().getClassLoader().getResource("Icons/microphone-6x.png").toString());//
		_record.setGraphic(new ImageView(quit));

	}

	/**
	 * write the current values of our non-transient fields to a json file with the specified naming convention SAVEFOLDER/levelDifficulty.dat
	 * this method should be exclusively called form the ConfirmationDialogBoxController.
	 */
	@Override
	public void save() {
		Gson g = new Gson();
		String j = g.toJson(this);
		if(!new File(SAVEFOLDER).exists()) {
			new File(SAVEFOLDER).mkdir();
		}
		try (FileWriter filewriter = new FileWriter(SAVEFOLDER + "/" + _level.toString() + _difficulty.toString() + ".dat")){
			filewriter.write(j.toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Set the values of this controllers fields to that of a previous save state.
	 * @param level
	 * @param difficulty
	 */
	public void load(Level level, Difficulty difficulty) {
		try {
			Gson g = new Gson();
			JsonReader reader = new JsonReader(new FileReader(SAVEFOLDER + "/" + level.toString() + difficulty.toString() + ".dat"));
			GameController controller = g.fromJson(reader, GameController.class);
			_correct = controller.getCorrect();
			_failed = controller.getFailed();
			_secondTry = controller.getSecondTry();
			_tryAgainPressed = controller.getTryAgainPressed();
			_currentQuestion = controller.getCurrentQuestion();
			_correctAnswers  = controller.getCorrectAnswers();
			_numbers = controller.getNumbers();
			_level = controller.getLevel();
			_difficulty = controller.getDifficulty();
			_display = controller.getDisplay();
			_totalQuestions = controller.getTotalQuestions();
			new File(SAVEFOLDER + "/" + level.toString() + difficulty.toString() + ".dat").delete();
		} catch (FileNotFoundException e) {
			//this exception should never occur because the method is only called after a save file has been verified to exist.
			e.printStackTrace();
		}

	}
	
//Private getters for loading from json. private as access is from within this class and to not expose implementation details.

	/**
	 * Based on the current question this method should be called when the user has got it correct or after two failed attempts.
	 * this method updates the progress bar to display that status of each question for default 10 question levels only(Excludes customised levels).
	 * @param correctness
	 */
	private void setProgress(Correctness correctness) {
		Image correct = new Image(getClass().getClassLoader().getResource("Progress/c.png").toString());//
		Image incorrect = new Image(getClass().getClassLoader().getResource("Progress/i.png").toString());//
	
		if (_currentQuestion==0) {
			if (correctness==Correctness.CORRECT) {
				_A1.setImage(correct);
			}else {
				_A1.setImage(incorrect);
			}
		}else if (_currentQuestion==1) {
			if (correctness==Correctness.CORRECT) {
				_A2.setImage(correct);
			}else {
				_A2.setImage(incorrect);
			}
		}else if (_currentQuestion==2) {
			if (correctness==Correctness.CORRECT) {
				_A3.setImage(correct);
			}else {
				_A3.setImage(incorrect);
			}
		}else if (_currentQuestion==3) {
			if (correctness==Correctness.CORRECT) {
				_A4.setImage(correct);
			}else {
				_A4.setImage(incorrect);
			}
		}else if (_currentQuestion==4) {
			if (correctness==Correctness.CORRECT) {
				_A5.setImage(correct);
			}else {
				_A5.setImage(incorrect);
			}
		}else if (_currentQuestion==5) {
			if (correctness==Correctness.CORRECT) {
				_A6.setImage(correct);
			}else {
				_A6.setImage(incorrect);
			}
		}else if (_currentQuestion==6) {
			if (correctness==Correctness.CORRECT) {
				_A7.setImage(correct);
			}else {
				_A7.setImage(incorrect);
			}
		}else if (_currentQuestion==7) {
			if (correctness==Correctness.CORRECT) {
				_A8.setImage(correct);
			}else {
				_A8.setImage(incorrect);
			}
		}else if (_currentQuestion==8) {
			if (correctness==Correctness.CORRECT) {
				_A9.setImage(correct);
			}else {
				_A9.setImage(incorrect);
			}
		}else if (_currentQuestion==9) {
			if (correctness==Correctness.CORRECT) {
				_A10.setImage(correct);
			}else {
				_A10.setImage(incorrect);
			}
		}
	}

	/**
	 * sets the level of the current scene. 
	 * NOTE: FOR GAMECONTROLLER THIS METHOD MUST BE CALLED BEFORE CALLING THE SET QUESTION METHOD
	 * @param level
	 */
	public void setDifficulty(Difficulty difficulty, Level level, int questions){
		_level=level;
		_totalQuestions = questions;
		if (difficulty==Difficulty.HARD){
			_display = "Hard ";
		}else if(difficulty == Difficulty.EASY){
			_display = "Easy ";
		}else {
			_display = "Custom ";
		}
	
		_difficulty = difficulty;
		GeneratorFactory gf = new GeneratorFactory();
		_generator = gf.getGenerator(_difficulty, _level, questions);
		_numbers = _generator.getNumbers();
		if(_difficulty != Difficulty.CUSTOM) {
		initProgressBar();
		}else {
			_A1.getParent().setVisible(false);
		}
	}

	/**
	 * sets the question of the current scene
	 */
	public void setQuestion(){
		defaultColor();
		if(!_secondTry) {
			_playback.setDisable(true);
			_submit.setDisable(true);
		}else {
			_record.setDisable(false);
			_submit.setDisable(true);
			_playback.setDisable(true);
		}
		_theirAnswer.setText("");
		_theCorrectAnswer.setText("");
		_nextQuestion.setVisible(false);
		_question.setFont(new Font("Ubuntu",100));
		_question.setText(_numbers.get(_currentQuestion));
		int display = _currentQuestion+1;
		_title.setText(_display +"Question: "+display + " of " + _totalQuestions);
		_record.setDisable(false);
	}

	private boolean getCorrect() {
		return _correct;
	}

	private boolean getFailed() {
		return _failed;
	}

	private boolean getSecondTry() {
		return _secondTry;
	}

	private boolean getTryAgainPressed() {
		return _tryAgainPressed;
	}

	private int getCurrentQuestion() {
		return _currentQuestion;
	}

	private int getCorrectAnswers() {
		return _correctAnswers;
	}

	private List<String> getNumbers(){
		return _numbers;
	}

	private String getDisplay() {
		return _display;
	}

	private Level getLevel() {
		return _level;
	}

	private Difficulty getDifficulty() {
		return _difficulty;
	}
	
	private int getTotalQuestions() {
		return _totalQuestions;
	}
}
