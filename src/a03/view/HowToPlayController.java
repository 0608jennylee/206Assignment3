package a03.view;

import a03.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class HowToPlayController {
	private final int _numberOfImages=5;
	private int _count=1;
	private Button _next;
	private Button _previous;
	private MainApp _mainApp;
	@FXML private ImageView _imageView;
	
	@FXML
	private void handleBack() {
		_mainApp.mainMenuContents();
	}
	
	
	public void setMainApp(MainApp mainApp) {
		_mainApp = mainApp;
	}

	public void handlePrevious(){
		if (_count<=_numberOfImages){
			_next.setDisable(false);
		}
		//DoStuff
		_count--;
		if (_count==1){
			_previous.setDisable(true);
		}
	}
	
	public void handleNext(){
		if (_count>=1){
			_previous.setDisable(false);
		}
		//DoStuff
		_count++;
		if (_count==_numberOfImages){
			_next.setDisable(true);
		}
	}

	public void setFirstImage() {
		
	}
}
