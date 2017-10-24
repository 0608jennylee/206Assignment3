package a03.view;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import a03.enumerations.Level;
import a03.MainApp;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChooseLevelController extends Controller implements Initializable{
	@FXML
	private ImageView _imageView;
	@FXML
	private JFXButton _back;
	// Event Listener on JFXButton[#_back].onAction
	@FXML
	public void handleMainMenu(ActionEvent event) {
		_mainApp.mainMenuContents();
	}
	// Event Listener on JFXButton[#_back].onAction
	@FXML
	public void handleBack(ActionEvent event) {
		_mainApp.mainMenuContents();
	}
	// Event Listener on JFXButton[#_easy].onAction
	@FXML
	public void handleNumbersSelection(ActionEvent event) {
		_mainApp.chooseDifficulty(Level.NUMBERS);
	}
	// Event Listener on JFXButton[#_hard].onAction

	@FXML
	public void handleEquationsSelection(ActionEvent event) {
		_mainApp.chooseDifficulty(Level.EQUATIONS);
	}
	// Event Listener on JFXButton[#_hard].onAction
	@FXML
	public void handleCuztomizeSelection(ActionEvent event) {
		_mainApp.customize();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image quit = new Image(getClass().getClassLoader().getResource("Icons/quit.png").toString());//
		_back.setGraphic(new ImageView(quit));
		Image background = new Image(getClass().getClassLoader().getResource("fern.jpg").toString());//
		_imageView.setImage(background);
		_imageView.setOpacity(0.38);

	}
}
