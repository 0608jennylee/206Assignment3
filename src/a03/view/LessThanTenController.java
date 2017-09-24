package a03.view;

import java.io.File;


import java.io.IOException;
import java.util.List;

import a03.MainApp;
import a03.Level;
import a03.generators.Generator;
import a03.generators.Processor;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class LessThanTenController {

	@FXML private ImageView _imageView;
	@FXML private Button _record;
	@FXML private Button _nextQuestion;
	@FXML private ImageView _score;
	private MainApp _mainApp;
	private List<Integer> _numbers;
	private boolean _correct=true;
	private boolean _failed=false;
	private int _currentQuestion = 0;
	private boolean _secondTry = false;
	private boolean _tryAgainPressed=true;
//	private int _incorrectAnswers;
	private int _correctAnswers;
	private AnchorPane _root;
	private Generator _generator;

	public LessThanTenController() {
		_generator = new Generator();
//		_numbers = eg.getNumbers();
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleBack(ActionEvent event) {
		_mainApp.mainMenuContents();
	}

	// Event Listener on Button[#_record].onAction
	@FXML
	public void handleRecord(ActionEvent event) {
		if (_secondTry&&_tryAgainPressed){
			setQuestion();
			_tryAgainPressed=false;
			_record.setText("Record");
		}else{
//			Task<Void> record = new Task<Void>() { 
//				@Override
//				protected Void call() throws Exception {
//					String cmd = "arecord -d 2 -r 22050 -c 1 -i -t wav -f s16_LE " + _numbers.get(_currentQuestion).toString() + ".wav;echo record passed; HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  -w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  user/dictionaryD user/tiedList "+ _numbers.get(_currentQuestion).toString() + ".wav; echo processing passed;";
//					System.out.println(cmd);
//					ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
//					try {
//						Process p = pb.start();
//						String line = "test";
//						int exitStatus = p.waitFor();
//						Processor processor = new Processor();
//						if(processor.processAnswer(_numbers.get(_currentQuestion))) {
//							_correct=true;
//						}else {
//							_correct=false;
//						}
//					} catch (IOException IOe) {
//						IOe.printStackTrace();
//					}
//					return null;
//				}
//			};
//			Thread recordThread = new Thread(record);
//			recordThread.start();
			check();
		}
	}

	public void check(){
		if (_failed){
			File file = new File(System.getProperty("user.dir")+"/failed/" + _numbers.get(_currentQuestion) + ".jpg");
			setImage(file);
		}else if(_correct){
			File file = new File(System.getProperty("user.dir")+"/Correct/" + _numbers.get(_currentQuestion) + ".jpg");
			setImage(file);
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
				_secondTry = false;
				_currentQuestion++;
				_record.setDisable(true);
				_record.setText("Record");
				_nextQuestion.setVisible(true);
				_tryAgainPressed=false;
			}else{
				_secondTry = true;
				_record.setText("Try Again");
				_tryAgainPressed=true;
			}
		}
		if (_currentQuestion==10){
			displayFinalScore();
		}
	}

	private void displayFinalScore() {
		_record.setVisible(false);
		_nextQuestion.setVisible(false);
		File file = new File(System.getProperty("user.dir")+"/Result/" + _correctAnswers + ".jpg");
		System.out.println(file);
		setImage(file);
	}

	public void setMainApp(MainApp mainApp) {
		_mainApp = mainApp;
	}

	public void setQuestion(){
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
		_generator.setLevel(level);
		_numbers = _generator.getNumbers();
	}
}
