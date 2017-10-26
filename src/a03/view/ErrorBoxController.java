package a03.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ErrorBoxController extends Controller{
	
	@FXML Label _text;
	@FXML ImageView _logo;
	@FXML ImageView _imageView;
	
	
	private Stage _errorStage;
	
	@FXML
	private void handleOK() {
		_errorStage.close();
	}

	public void setDialogStage(Stage errorStage) {
		_errorStage = errorStage;
	}
	
	public void setText(String text) {
		_text.setText(text);
		init();
	}
	
	private void init() {
		Image quit = new Image(getClass().getClassLoader().getResource("Icons/error.png").toString());//
		Image background = new Image(getClass().getClassLoader().getResource("fern.jpg").toString());//
		_imageView.setFitWidth(621);
		_imageView.setImage(background);
		_logo.setImage(quit);
		_imageView.setOpacity(0.1);

	}
}
