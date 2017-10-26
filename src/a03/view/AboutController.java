package a03.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AboutController extends Controller implements Initializable{

	@FXML private ImageView _imageView;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image background = new Image(getClass().getClassLoader().getResource("fern.jpg").toString());//
		_imageView.setImage(background);
		_imageView.setOpacity(0.4);
	}
	
	@FXML
	private void handleBack() {
		_mainApp.mainMenuContents();
	}
}
