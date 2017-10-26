package a03.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import a03.MainApp;
import a03.enumerations.Difficulty;
import a03.enumerations.Level;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomizeController extends Controller implements Initializable{
	@FXML private ImageView _imageView;
	@FXML private Button _mainMenu;
	@FXML private Button _back;

	@FXML private CheckBox _numbersCB;
	@FXML private CheckBox _equationsCB;
	@FXML private TextField _minTB;
	@FXML private TextField _maxTB;
	@FXML private Slider _slider;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image quit = new Image(getClass().getClassLoader().getResource("Icons/quit.png").toString());//
		_mainMenu.setGraphic(new ImageView(quit));
		Image background = new Image(getClass().getClassLoader().getResource("fern.jpg").toString());//
		_imageView.setImage(background);
		_imageView.setOpacity(0.34);
	}
	@FXML private void handleMainMenu() {
		_mainApp.mainMenuContents();
	}
	@FXML private void handleBack() {
		_mainApp.chooseLevel();
	}
	@FXML private void handleOK() {
		Level level;
		int minimum = 1;
		int maximum;
		int questions;
		if (_numbersCB.isSelected() && _equationsCB.isSelected()) {
			level = Level.CUSTOM;
		}else if(_numbersCB.isSelected()) {
			level = Level.NUMBERS;
		}else if(_equationsCB.isSelected()){
			level = Level.EQUATIONS;
		}else {
			error("No level type selected, please choose either numbers, equations or both.");
			return;
		}
		
		if (_minTB.getText()==null||_minTB.getText().length()==0) {
			error("Minimum must have a value");
			return;
			
		}
		else if (Pattern.matches("[a-zA-Z]+", _minTB.getText()) == true) {
			error("Minimum can only be integer chracters e.g 1, 10, 99.");
			return;
			
		}else {
				minimum=Integer.parseInt(_minTB.getText());
				if (minimum<=0) {
					error("Minimum value cannot be below 0.");
					return;
					
				}else if (minimum>99) {
					error("Minimum cannot exceed the maximum allowed value of 99.");
					return;
					
				}
		}
		if (_maxTB.getText()==null||_maxTB.getText().length()==0) {
			error("Maximum must have a value");
			return;
			
		}
		else if (Pattern.matches("[a-zA-Z]+", _maxTB.getText()) == true) {
			error("Maximum can only be integer characters. e.g 1, 10, 99.");
			return;
			
		}
		else {
			maximum=Integer.parseInt(_maxTB.getText());
			if (maximum<=0) {
				error("Minimum value cannot be below 0.");
				return;
				
			}else if (maximum>99) {
				error("Maximum value cannot exceed 99.");
				return;
				
			}
			if (maximum<minimum) {
				error("Invalid range, maximum cannot be less than minimum.");
				return;
				
			}
		}
		if(Math.round((float)_slider.getValue()) == 0) {
			error("Cannot have zero questions");
			return;
		}else {
			questions = Math.round((float)_slider.getValue());
		}
		Difficulty difficulty = Difficulty.CUSTOM;
		difficulty.setMax(maximum);
		difficulty.setMin(minimum);
		if(new File("Saves/" + level.toString() + difficulty.toString() + ".dat").exists()) {
			_mainApp.LoadLevel(level, difficulty);
		}else {
			_mainApp.Start(level,difficulty, questions);
		}
		
	}
	
	private void error(String text) {
		_mainApp.customiseError(text);
	}

}
