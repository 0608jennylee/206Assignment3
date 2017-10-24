package a03.view;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import a03.enumerations.Difficulty;
import a03.enumerations.Level;
import a03.MainApp;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoadLevelController implements Initializable{
	@FXML
	private ImageView _imageView;
	@FXML
	private JFXButton _back;
	@FXML
	private JFXButton _mainMenu;
	@FXML
	private JFXButton _load;
	@FXML
	private JFXButton _new;
	private MainApp _mainApp;
	private Difficulty _difficulty;
	private Level _level;

	// Event Listener on JFXButton[#_back].onAction
	@FXML
	public void handleMainMenu(ActionEvent event) {
		_mainApp.mainMenuContents();
	}
	// Event Listener on JFXButton[#_back].onAction
	@FXML
	public void handleBack(ActionEvent event) {
		_mainApp.chooseDifficulty(_level);
	}
	// Event Listener on JFXButton[#_easy].onAction
	@FXML
	public void handleLoad(ActionEvent event) {
		_mainApp.Game(_difficulty, _level, true);
	}
	// Event Listener on JFXButton[#_hard].onAction
	@FXML
	public void handleNew(ActionEvent event) {
		new File("Saves/" + _level.toString() + _difficulty.toString() + ".dat").delete();
		_mainApp.Start(_level, _difficulty, 10);
	}
	public void setMainApp(MainApp mainApp) {
		_mainApp=mainApp;
	}
	public void setDifficulty(Difficulty difficulty) {
		_difficulty=difficulty;
	}
	public void setLevel(Level level) {
		_level=level;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image quit = new Image(getClass().getClassLoader().getResource("Icons/quit.png").toString());//
		_mainMenu.setGraphic(new ImageView(quit));
		Image background = new Image(getClass().getClassLoader().getResource("fern.jpg").toString());//
		_imageView.setImage(background);
		_imageView.setOpacity(0.34);
	}
}
