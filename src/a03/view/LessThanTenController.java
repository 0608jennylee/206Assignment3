package a03.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
/**
 * this controller, controls the scene for displaying the level
 * @author edwar jenny
 *
 */
public class LessThanTenController extends Controller implements Initializable, Saveable{
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
		if(!new File(RECORDINGSFOLDER).exists()) {
			new File(RECORDINGSFOLDER).mkdirs();
		}
		_playback.setDisable(true);
		_submit.setDisable(true);
		if (_secondTry&&_tryAgainPressed||_failed){ //if it fails or is the second try for the user
			setQuestion();
			//if its failed will change the tryAgainPressed to false
			if(!_failed) {
				_tryAgainPressed=false;
			}
			recordButton();
			_failed=false;
		}else{//recording
			Task<Void> record = new Task<Void>() { 
				@Override
				protected Void call() throws Exception {
					_progressBar.setVisible(true);
					_record.setDisable(true);
					String cmd = "arecord -d 2 -r 22050 -c 1 -i -t wav -f s16_LE " + RECORDINGSFOLDER + Processor.toInt(_numbers.get(_currentQuestion)) + ".wav;echo record passed; HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  -w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  user/dictionaryD user/tiedList " + RECORDINGSFOLDER + Processor.toInt(_numbers.get(_currentQuestion)) + ".wav; echo processing passed;";
					ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
					try {
						Process p = pb.start();
						p.waitFor();
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
		_record.setDisable(true);
		_playback.setDisable(true);
		_submit.setDisable(true);
		String filepath = RECORDINGSFOLDER + Processor.toInt(_numbers.get(_currentQuestion)) + ".wav";
		Media sound = new Media(new File(filepath).toURI().toString());
		mp = new MediaPlayer(sound);
		mp.setOnEndOfMedia(this::enable);
		mp.play();
	}

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
		_submit.setDisable(true);
		_record.setDisable(true);
		_playback.setDisable(true);
		if (_failed){//HTK failed
			System.out.println("failed");
			//			_sorry.setVisible(true);
			//			_weMuckedUp.setVisible(true);
			//			_tryAgain.setVisible(true);
			tryAgain();
			_record.setDisable(false);
			//_text.setVisible(true);
		}else if(_correct){//user gets correct answer
			//File file = new File(System.getProperty("user.dir")+"/Correct/" + _numbers.get(_currentQuestion) + ".jpg");
			//			File file = new File(System.getProperty("user.dir")+"/Correct/" + _numbers.get(_currentQuestion) + ".jpg");
			setProgress(Correctness.CORRECT);
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
			//			File file = new File(System.getProperty("user.dir")+"/Incorrect/" + _numbers.get(_currentQuestion) + ".jpg");
			//			Image file = new Image(getClass().getClassLoader().getResource("Incorrect/" + _numbers.get(_currentQuestion) + ".jpg").toString());
			//			setImage(file);
			animate();
			if (_secondTry){//user gets the answer incorrect the second time
				setProgress(Correctness.INCORRECT);
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
		if (_currentQuestion==_totalQuestions){//the user has answered the all questions for this level
			displayFinalScore();
		}
	}
		
		void animate() {
			_question.setStyle("-fx-border-color: red; -fx-border-width: 5; -fx-border-radius: 5");
	}

	/**
	 * at the end of each game, displays the score, and gives the user 
	 * the option to go back to main menu play again or go to next level 
	 * if they are on easy and have passed i
	 */
	private void displayFinalScore() {
		boolean flag = new File("Logs/" + _level.toString() + _difficulty.toString() + "History.dat").exists();
		GameStats.getGameStats().update(_difficulty,_level, _correctAnswers);
		Gson g = new Gson();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM HH:mm");
		LocalDateTime localdatetime = LocalDateTime.now();
		System.out.println(_totalQuestions);
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
		if (_difficulty==Difficulty.HARD||(_difficulty==Difficulty.EASY&&_correctAnswers>=8)) {
			_mainApp.Start(_level, Difficulty.HARD, 10);
		}else {
			_mainApp.Start(_level, _difficulty, 10);
		}
	}

	/**
	 * sets the level of the current scene
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
	}

	/**
	 * sets the question of the current scene
	 */
	public void setQuestion(){
		if(!_secondTry) {
			_playback.setDisable(true);
			_submit.setDisable(true);
		}else {
			_record.setDisable(false);
			_submit.setDisable(false);
			_playback.setDisable(false);
		}
		//		_sorry.setVisible(false);
		//		_weMuckedUp.setVisible(false);
		//		_tryAgain.setVisible(false);
		_theirAnswer.setText("");
		_theCorrectAnswer.setText("");
		_nextQuestion.setVisible(false);
		_question.setFont(new Font("Ubuntu",100));
		_question.setText(_numbers.get(_currentQuestion));
		//File file = new File(System.getProperty("user.dir")+"/Video/" + _numbers.get(_currentQuestion) + ".jpg");
		//		Image file = new Image(getClass().getClassLoader().getResource("Video/" + _numbers.get(_currentQuestion) + ".jpg").toString());//
		int display = _currentQuestion+1;
		_title.setText(_display +"Question: "+display);
		//		setImage(file);
		_record.setDisable(false);
	}

	@FXML
	public void handleSubmit() {
		try {
			Processor processor = new Processor();
			System.out.print(Processor.toInt(_numbers.get(_currentQuestion)));
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

	private void delete() {
		System.out.println("deleted");
		File recordings = new File(RECORDINGSFOLDER + Processor.toInt(_numbers.get(_currentQuestion)) + ".wav");
		recordings.delete();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File file4 = new File(System.getProperty("user.dir")+"/Icons/png/quit.png");
		Image image4 = new Image(file4.toURI().toString());
		_back.setGraphic(new ImageView(image4));
		File file1 = new File(System.getProperty("user.dir")+"/Icons/png/bullhorn-6x.png");
		Image image1 = new Image(file1.toURI().toString());
		_playback.setGraphic(new ImageView(image1));
		recordButton();
		File file3 = new File(System.getProperty("user.dir")+"/Icons/png/arrow-circle-right-8x.png");
		Image image3 = new Image(file3.toURI().toString());
		_nextQuestion.setGraphic(new ImageView(image3));
		File file5 = new File(System.getProperty("user.dir")+"/fern.jpg");
		Image image5 = new Image(file5.toURI().toString());
		_imageView.setImage(image5);
		_imageView.setOpacity(0.1);
		Image file = new Image(getClass().getClassLoader().getResource("Progress/1.png").toString());//
		_Q1.setImage(file);
		Image file2 = new Image(getClass().getClassLoader().getResource("Progress/2.png").toString());//
		_Q2.setImage(file2);
		Image file6 = new Image(getClass().getClassLoader().getResource("Progress/3.png").toString());//
		_Q3.setImage(file6);
		Image file7 = new Image(getClass().getClassLoader().getResource("Progress/4.png").toString());//
		_Q4.setImage(file7);
		Image file8 = new Image(getClass().getClassLoader().getResource("Progress/5.png").toString());//
		_Q5.setImage(file8);
		Image file9 = new Image(getClass().getClassLoader().getResource("Progress/6.png").toString());//
		_Q6.setImage(file9);
		Image file10 = new Image(getClass().getClassLoader().getResource("Progress/7.png").toString());//
		_Q7.setImage(file10);
		Image file11 = new Image(getClass().getClassLoader().getResource("Progress/8.png").toString());//
		_Q8.setImage(file11);
		Image file12 = new Image(getClass().getClassLoader().getResource("Progress/9.png").toString());//
		_Q9.setImage(file12);
		Image file13 = new Image(getClass().getClassLoader().getResource("Progress/10.png").toString());//
		_Q10.setImage(file13);
		Image q = new Image(getClass().getClassLoader().getResource("Progress/q.png").toString());//
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
		_progressBar.setVisible(false);
	}
	private void setProgress(Correctness correctness) {
		Image correct = new Image(getClass().getClassLoader().getResource("Progress/correct.png").toString());//
		Image incorrect = new Image(getClass().getClassLoader().getResource("Progress/incorrect.png").toString());//

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
	public void tryAgain() {
		File file3 = new File(System.getProperty("user.dir")+"/Icons/png/reload-6x.png");
		Image image3 = new Image(file3.toURI().toString());
		_record.setGraphic(new ImageView(image3));

	}
	public void recordButton() {
		File file3 = new File(System.getProperty("user.dir")+"/Icons/png/microphone-6x.png");
		Image image3 = new Image(file3.toURI().toString());
		_record.setGraphic(new ImageView(image3));

	}

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

	public void load(Level level, Difficulty difficulty) {
		
		try {
			Gson g = new Gson();
			JsonReader reader = new JsonReader(new FileReader(SAVEFOLDER + "/" + level.toString() + difficulty.toString() + ".dat"));
			LessThanTenController controller = g.fromJson(reader, LessThanTenController.class);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	public boolean getCorrect() {
		return _correct;
	}

	//	public void setCorrect(boolean bool) {
	//		_correct = bool;
	//	}

	public boolean getFailed() {
		return _failed;
	}

	//	public void setFailed(boolean bool) {
	//		_failed = bool;
	//	}

	public boolean getSecondTry() {
		return _secondTry;
	}

	//	public void setSecondTry(boolean bool) {
	//		_secondTry = bool;
	//	}

	public boolean getTryAgainPressed() {
		return _tryAgainPressed;
	}

	//	public void setTryAgainPressed(boolean bool) {
	//		_tryAgainPressed = bool;
	//	}

	public int getCurrentQuestion() {
		return _currentQuestion;
	}

	//	public void setCurrentQuestion(int question) {
	//		_currentQuestion = question;
	//	}

	public int getCorrectAnswers() {
		return _correctAnswers;
	}

	//	public void setCorrectAnswers(int answers) {
	//		_correctAnswers = answers;
	//	}

	public List<String> getNumbers(){
		return _numbers;
	}

	//	public void setNumbers(List<String> numbers) {
	//		_numbers = numbers;
	//	}

	public String getDisplay() {
		return _display;
	}

	//	public void setDisplay(String string) {
	//		_display = string;
	//	}

	public Level getLevel() {
		return _level;
	}

	//	public void setLevel(Level level) {
	//		_level = level;
	//	}

	public Difficulty getDifficulty() {
		return _difficulty;
	}
	
//	public void setDifficulty(Difficulty difficulty) {
//		_difficulty = difficulty;
//	}
	
	private int getTotalQuestions() {
		return _totalQuestions;
	}
}
