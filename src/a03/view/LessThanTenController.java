package a03.view;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import a03.MainApp;
import a03.generators.EasyGen;
import a03.generators.Processor;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;

public class LessThanTenController {
	
	@FXML private ImageView _imageView;
	@FXML private Button _record;
	private MainApp _mainApp;
	private List<Integer> _numbers;
	private boolean _correct=false;
	private boolean _failed=false;
	
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
				String cmd = "arecord -d 2 -r 22050 -c 1 -i -t wav -f s16_LE " + _numbers.get(0).toString() + ".wav;echo record passed; HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  -w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  user/dictionaryD user/tiedList "+ _numbers.get(0).toString() + ".wav; echo processing passed;";
				System.out.println(cmd);
				ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
				try {
					Process p = pb.start();
					String line = "test";
					int exitStatus = p.waitFor();
					Processor processor = new Processor();
					if(processor.processAnswer(_numbers.get(0))) {
						System.out.println("works");
					}else {
						System.out.println("failed");
					}
				} catch (IOException IOe) {
					// TODO Auto-generated catch block
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
			File file = new File(System.getProperty("user.dir")+"/failed/" + _numbers.get(0) + ".jpg");
			System.out.println(file);
			Image image = new Image(file.toURI().toString());
			_imageView.setImage(image);
		}else if(_correct){
			File file = new File(System.getProperty("user.dir")+"/Correct/" + _numbers.get(0) + ".jpg");
			System.out.println(file);
			Image image = new Image(file.toURI().toString());
			_imageView.setImage(image);
		}else{
			File file = new File(System.getProperty("user.dir")+"/Incorrect/" + _numbers.get(0) + ".jpg");
			System.out.println(file);
			Image image = new Image(file.toURI().toString());
			_imageView.setImage(image);
			System.out.println("you said this");
		}
	}

	public void setMainApp(MainApp mainApp) {
		_mainApp = mainApp;
	}
	
	public void setQuestion(){
		File file = new File(System.getProperty("user.dir")+"/Video/" + _numbers.get(0) + ".jpg");
		System.out.println(file);
		Image image = new Image(file.toURI().toString());
		_imageView.setImage(image);
	}
}
