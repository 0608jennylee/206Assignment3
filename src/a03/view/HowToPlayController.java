package a03.view;

import java.io.File;

import a03.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HowToPlayController {
	private final int _numberOfImages=4;
	private int _count=1;
	private String _imageDirectory = System.getProperty("user.dir")+"/HowToPlayImages/";
	@FXML private Button _next;
	@FXML private Button _previous;
	@FXML private MainApp _mainApp;
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
		_count--;
		setImage();
		if (_count==1){
			_previous.setDisable(true);
		}
	}
	
	public void handleNext(){
		if (_count>=1){
			_previous.setDisable(false);
		}
		_count++;
		setImage();
		if (_count==_numberOfImages){
			_next.setDisable(true);
		}
	}

	public void setFirstImage() {
		setImage();
		_previous.setDisable(true);
	}
	
	private void setImage(){
		File file = new File(_imageDirectory+_count+".jpg");
		Image image = new Image(file.toURI().toString());
		_imageView.setImage(image);
	}
}
