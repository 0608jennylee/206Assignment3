package a03.view;

import java.io.File;

import java.io.IOException;
import java.util.List;

import a03.MainApp;
import a03.generators.EasyGen;
import a03.generators.Processor;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LessThanTenController {
	
	@FXML private ImageView _imageView;
	@FXML private Button _record;
	@FXML private Button _next;
	private MainApp _mainApp;
	private List<Integer> _numbers;
	private boolean _correct=true;
	private boolean _failed=false;
	private int _currentQuestion = 0;
	private boolean _secondtry = false;
	
	public LessThanTenController() {
		EasyGen eg = new EasyGen();
		_numbers = eg.getNumbers();
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleBack(ActionEvent event) {
		_mainApp.mainMenuContents();
	}
	
	// Event Listener on Button[#_record].onAction
	@FXML
	public void handleRecord(ActionEvent event) {
		Task<Void> record = new Task<Void>() { 
			@Override
			protected Void call() throws Exception {
				String cmd = "arecord -d 2 -r 22050 -c 1 -i -t wav -f s16_LE " + _numbers.get(_currentQuestion).toString() + ".wav;echo record passed; HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  -w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  user/dictionaryD user/tiedList "+ _numbers.get(_currentQuestion).toString() + ".wav; echo processing passed;";
				System.out.println(cmd);
				ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
				try {
					Process p = pb.start();
					String line = "test";
					int exitStatus = p.waitFor();
					Processor processor = new Processor();
					if(processor.processAnswer(_numbers.get(_currentQuestion))) {
						System.out.println("works");
					}else {
						System.out.println("failed");
					}
				} catch (IOException IOe) {
					IOe.printStackTrace();
				}
				return null;
			}
			
			
		};
		Thread recordThread = new Thread(record);
		recordThread.start();
		check();
	}
	
	public void check(){
		if (_failed){
			File file = new File(System.getProperty("user.dir")+"/failed/" + _numbers.get(_currentQuestion) + ".jpg");
			Image image = new Image(file.toURI().toString());
			_imageView.setImage(image);
		}else if(_correct){
			File file = new File(System.getProperty("user.dir")+"/Correct/" + _numbers.get(_currentQuestion) + ".jpg");
			Image image = new Image(file.toURI().toString());
			_imageView.setImage(image);
		}else{
			File file = new File(System.getProperty("user.dir")+"/Incorrect/" + _numbers.get(_currentQuestion) + ".jpg");
			Image image = new Image(file.toURI().toString());
			_imageView.setImage(image);
			System.out.println("you said this");
			if (_secondtry){
				_secondtry = false;
				
			}else{
				_secondtry =true;
				_next.setVisible(false);
			}
		}
	}

	public void setMainApp(MainApp mainApp) {
		_mainApp = mainApp;
	}
	
	public void setQuestion(){
		File file = new File(System.getProperty("user.dir")+"/Video/" + _numbers.get(_currentQuestion) + ".jpg");
		System.out.println(file);
		Image image = new Image(file.toURI().toString());
		_imageView.setImage(image);
	}
}
