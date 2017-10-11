package a03.view;

import java.io.File;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import a03.MainApp;
import a03.Saveable;
import a03.Settings;
import a03.enumerations.Level;
import a03.errors.HTKError;
import a03.GameStats;
import a03.generators.Generator;
import a03.generators.GeneratorFactory;
import a03.generators.Processor;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.text.Font;
/**
 * this controller, controls the scene for displaying the level
 * @author edwar jenny
 *
 */
public class LessThanTenController implements Initializable, Saveable{

//	@FXML private ImageView _imageView;
	@FXML private ImageView _score;
	@FXML private Button _record;
	@FXML private Button _submit;
	@FXML private Button _playback;

	@FXML private Button _nextQuestion;
//	@FXML private Button _mainMenuTop;
//	@FXML private Button _mainMenuBottom;
	@FXML private Button _nextLevel;
	@FXML private Label _theCorrectAnswer;
	@FXML private Label _theirAnswer;
	@FXML private Label _question;
	//@FXML private Label _text;
	@FXML private Label _text1;
	@FXML private Label _text2;
	@FXML private Label _title;
	@FXML private ImageView _imageView;
//	@FXML private Label _sorry;
//	@FXML private Label _weMuckedUp;
//	@FXML private Label _tryAgain;
	@FXML private BorderPane _root;
	
	private boolean _correct=true;
	private boolean _failed=false;
	private boolean _secondTry = false;
	private boolean _tryAgainPressed=true;
	private int _currentQuestion = 0;
	private int _correctAnswers = 0;

	private MainApp _mainApp;
	private List<String> _numbers;
	private Level _level;
	private String _display;
	private MediaPlayer mp;
	private Generator _generator;
	@FXML private Button _back;

	/**
	 * when the record button is clicked, the recording starts in a background 
	 * thread, and changes correct based on whether they are wrong or correct
	 * @param event
	 */
	// Event Listener on Button[#_record].onAction
	@FXML
	public void handleRecord(ActionEvent event) {
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
					_record.setDisable(true);
					String cmd = "arecord -d 2 -r 22050 -c 1 -i -t wav -f s16_LE " + Processor.toInt(_numbers.get(_currentQuestion)) + ".wav;echo record passed; HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  -w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  user/dictionaryD user/tiedList "+ Processor.toInt(_numbers.get(_currentQuestion)) + ".wav; echo processing passed;";
					ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
					try {
						Process p = pb.start();
						p.waitFor();
						_playback.setDisable(false);
						_submit.setDisable(false);
						_record.setDisable(false);
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
		String filepath = Processor.toInt(_numbers.get(_currentQuestion)) + ".wav";
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
			_theCorrectAnswer.setText((Processor.toMaori(Processor.toInt(_numbers.get(_currentQuestion)))));
			_theirAnswer.setText((Processor.getUserAnswer()));
			_currentQuestion++;
			if(_currentQuestion != 10) {
				_nextQuestion.setVisible(true);
			}
			_secondTry = false;
			recordButton();
			_correctAnswers++;

		}else{//user gets incorrect answer
//			File file = new File(System.getProperty("user.dir")+"/Incorrect/" + _numbers.get(_currentQuestion) + ".jpg");
//			Image file = new Image(getClass().getClassLoader().getResource("Incorrect/" + _numbers.get(_currentQuestion) + ".jpg").toString());
//			setImage(file);
			if (_secondTry){//user gets the answer incorrect the second time
				_theCorrectAnswer.setText((Processor.toMaori(Processor.toInt(_numbers.get(_currentQuestion)))));
				_theirAnswer.setText(Processor.getUserAnswer());
				_secondTry = false;
				_currentQuestion++;
				_record.setDisable(true);
				recordButton();
				if(_currentQuestion != 10) {
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
		if (_currentQuestion==10){//the user has answered the all questions for this level
			displayFinalScore();
		}
	}

	/**
	 * at the end of each game, displays the score, and gives the user 
	 * the option to go back to main menu play again or go to next level 
	 * if they are on easy and have passed i
	 */
	private void displayFinalScore() {
		_title.setVisible(false);
		if(_correctAnswers >= 8&&_level==Level.EASYNUMBERS) {
			Settings.getSettings().enableHard();
//			_mainMenuTop.setVisible(true);
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
//		_imageView.setTranslateY(150);
//		File file = new File(System.getProperty("user.dir")+"/Result/" + _correctAnswers + ".jpg");
//		Image file = new Image(getClass().getClassLoader().getResource("Result/" + _correctAnswers + ".jpg").toString());
//		setImage(file);
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
		if (_level==Level.HARDNUMBERS||(_level==Level.EASYNUMBERS&&_correctAnswers>=8)) {
			_mainApp.Start(Level.HARDNUMBERS);
		}else {
			_mainApp.Start(_level);
		}
	}
	
	/**
	 * sets the level of the current scene
	 * @param level
	 */
	public void setLevel(Level level){
		if (level==Level.HARDNUMBERS){
			_display = "HARD ";
		}else{
			_display = "EASY ";
		}
		_level = level;
		GeneratorFactory gf = new GeneratorFactory();
		_generator = gf.getGenerator(_level);
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
		_question.setFont(new Font("Ubuntu",50));
		_question.setText(_numbers.get(_currentQuestion));
		//File file = new File(System.getProperty("user.dir")+"/Video/" + _numbers.get(_currentQuestion) + ".jpg");
//		Image file = new Image(getClass().getClassLoader().getResource("Video/" + _numbers.get(_currentQuestion) + ".jpg").toString());//
		int display = _currentQuestion+1;
		_title.setText(_display +"Question: "+display);
		//		setImage(file);
		_record.setDisable(false);
	}
	
	/**
	 * sets the mainApp for the controller in order for the 
	 * controller know where to notify the events on the 
	 * start stage
	 * @param mainApp the mainApp that the scene is staged on
	 */public void setMainApp(MainApp mainApp) {
		_mainApp = mainApp;
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File file4 = new File(System.getProperty("user.dir")+"/Icons/png/quit.png");
		Image image4 = new Image(file4.toURI().toString());
		_back.setGraphic(new ImageView(image4));
		File file = new File(System.getProperty("user.dir")+"/Icons/png/data-transfer-upload-6x.png");
		Image image = new Image(file.toURI().toString());
		_submit.setGraphic(new ImageView(image));
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
		List<String> lines = new ArrayList<String>();
		lines.add("CORRECT=" + Boolean.toString(_correct));
		lines.add("FAILED=" + Boolean.toString(_failed));
		lines.add("SECONDTRY=" + Boolean.toString(_secondTry));
		lines.add("TRYAGAINPRESSED=" + Boolean.toString(_tryAgainPressed));
		lines.add("CURRENTQUESTION=" + Integer.toString(_currentQuestion));
		lines.add("CORRECTANSWERS=" + Integer.toString(_correctAnswers));
		lines.add("LEVEL=" + _level.name());
		lines.add("DISPLAY=" + _display);
		lines.addAll(_numbers);
		
		try {
			Files.write(new File(_level.toString() + ".dat").toPath(), lines);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void load() {
		
	}
}
