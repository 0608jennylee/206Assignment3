package a03.view;

import java.io.File;


import java.io.IOException;
import java.util.List;

import a03.MainApp;
import a03.Settings;
import a03.Stats;
import a03.GameStats;
import a03.HTKError;
import a03.Level;
import a03.generators.Generator;
import a03.generators.Processor;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LessThanTenController {

	@FXML private ImageView _imageView;
	@FXML private Button _record;
	@FXML private Button _nextQuestion;
	@FXML private Button _mainMenuTop;
	@FXML private Button _mainMenuBottom;
	@FXML private ImageView _score;
	@FXML private Label _theCorrectAnswer;
	@FXML private Label _theirAnswer;
	@FXML private Label _text1;
	@FXML private Label _text2;
	private MainApp _mainApp;
	private List<Integer> _numbers;
	private boolean _correct=true;
	private boolean _failed=false;
	private int _currentQuestion = 0;
	private boolean _secondTry = false;
	private boolean _tryAgainPressed=true;
	
//	private int _incorrectAnswers;
	private int _correctAnswers;
	private Generator _generator;
	private Level _level;
	@FXML private Button _nextLevel;
	public LessThanTenController() {
		_generator = new Generator();
//		_numbers = eg.getNumbers();
	}

//	// Event Listener on Button.onAction
//	@FXML
//	public void handleBack(ActionEvent event) {
//		_mainApp.mainMenuContents();
//	}

	// Event Listener on Button[#_record].onAction
	@FXML
	public void handleRecord(ActionEvent event) {
		if (_secondTry&&_tryAgainPressed){
			setQuestion();
			_tryAgainPressed=false;
			_record.setText("Record");
		}else{
			Task<Void> record = new Task<Void>() { 
				@Override
				protected Void call() throws Exception {
					_record.setDisable(true);
					String cmd = "arecord -d 2 -r 22050 -c 1 -i -t wav -f s16_LE " + _numbers.get(_currentQuestion).toString() + ".wav;echo record passed; HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  -w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  user/dictionaryD user/tiedList "+ _numbers.get(_currentQuestion).toString() + ".wav; echo processing passed;";
					System.out.println(cmd);
					ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
					try {
						Process p = pb.start();
						p.waitFor();
						_record.setDisable(false);
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

			record.setOnFailed(this::failed);
			record.setOnSucceeded(this::playBack);
			Thread recordThread = new Thread(record);
			recordThread.start();
			
		}
	}
	
	private void failed(WorkerStateEvent e) {
		System.out.println("failed");
	}
	
	public void playBack(WorkerStateEvent e) {
		String filepath = _numbers.get(_currentQuestion) + ".wav";
		Media sound = new Media(new File(filepath).toURI().toString());
		MediaPlayer mp = new MediaPlayer(sound);
		mp.setOnEndOfMedia(this::check);
		mp.play();
	}

	public void check(){
		if (_failed){
			//TODO still need to implement what happens when fail.
			System.out.println("failed");
			File file = new File(System.getProperty("user.dir")+"/failed/" + _numbers.get(_currentQuestion) + ".jpg");
			setImage(file);
		}else if(_correct){
			System.out.println("Broken");
			File file = new File(System.getProperty("user.dir")+"/Correct/" + _numbers.get(_currentQuestion) + ".jpg");
			setImage(file);
			_theCorrectAnswer.setText((Processor.toMaori(_numbers.get(_currentQuestion))));
			_theirAnswer.setText((Processor.getUserAnswer()));
			_currentQuestion++;
			_record.setDisable(true);
			_nextQuestion.setVisible(true);
			_secondTry = false;
			_record.setText("Record");
			_correctAnswers++;
			
		}else{
			File file = new File(System.getProperty("user.dir")+"/Incorrect/" + _numbers.get(_currentQuestion) + ".jpg");
			setImage(file);
			System.out.println("you said this");
			if (_secondTry){
				_theCorrectAnswer.setText((Processor.toMaori(_numbers.get(_currentQuestion))));
				_theirAnswer.setText(Processor.getUserAnswer());
				_secondTry = false;
				_currentQuestion++;
				_record.setDisable(true);
				_record.setText("Record");
				_nextQuestion.setVisible(true);
				_tryAgainPressed=false;
			}else{
				_secondTry = true;
				_theirAnswer.setText(Processor.getUserAnswer());
				_record.setText("Try Again");
				_record.setDisable(false);
				_tryAgainPressed=true;
			}
		}
		if (_currentQuestion==10){
			displayFinalScore();
		}
	}

	private void displayFinalScore() {
		if(_correctAnswers >= 8) {
			Settings.getSettings().enableHard();
		}
		GameStats.getGameStats().update(_level, _correctAnswers);
		if (_correctAnswers>=8){
			_mainMenuTop.setVisible(false);
			_mainMenuBottom.setVisible(true);
			_nextLevel.setVisible(true);
		}
		_theirAnswer.setText("");
		_theCorrectAnswer.setText("");
		_text1.setText("");
		_text2.setText("");
		_record.setVisible(false);
		_nextQuestion.setVisible(false);
		_imageView.setTranslateY(150);
		File file = new File(System.getProperty("user.dir")+"/Result/" + _correctAnswers + ".jpg");
		System.out.println(file);
		setImage(file);
	}

	public void setMainApp(MainApp mainApp) {
		_mainApp = mainApp;
	}

	public void setQuestion(){

		_theirAnswer.setText("");
		_theCorrectAnswer.setText("");

		_nextQuestion.setVisible(false);
		File file = new File(System.getProperty("user.dir")+"/Video/" + _numbers.get(_currentQuestion) + ".jpg");
		setImage(file);
		_record.setDisable(false);
	}

	private void setImage(File file){
		Image image = new Image(file.toURI().toString());
		_imageView.setImage(image);
	}
	
	@FXML 
	public void handleNextQuestion(){
		setQuestion();
	}
	
	@FXML
	public void handleMainMenu(){
		_mainApp.mainMenuContents();
	}
//	public void setRoot(AnchorPane lessThanTen){
//		_root = lessThanTen;
//	}
//	public void score(){
//		Line line = new Line();
//		//Setting the Properties of the Line 
//		line.setStartX(150.0f); 
//		line.setStartY(0.0f);         
//		line.setEndX(450.0f); 
//		line.setEndY(140.0f);
//		_root.getChildren().add(line);
//	}
	public void setLevel(Level level){
		_level = level;
		_generator.setLevel(level);
		_numbers = _generator.getNumbers();
		_mainMenuBottom.setVisible(false);
		_mainMenuTop.setVisible(true);
	}
	public void handleNextLevel(){
		_mainApp.Level(Level.HARD);
	}
}
