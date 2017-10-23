package a03.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import a03.enumerations.Difficulty;
import a03.enumerations.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomizeController extends Controller implements Initializable{
	@FXML private ImageView _imageView;
	@FXML private Button _mainMenu;

	@FXML private CheckBox _numbersCB;
	@FXML private CheckBox _equationsCB;
	@FXML private TextField _minTB;
	@FXML private TextField _maxTB;
	@FXML private Slider _slider;
	
	@FXML private void handleMainMenu() {
		_mainApp.mainMenuContents();
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
			//TODO make it show that they must select a level type;
			//prompt();
			System.out.println("Level returned");
			return;
		}
		
		if (_minTB.getText()==null||_minTB.getText().length()==0) {
			//TODO show the user they must input something;
			//prompt();
			System.out.println("error no input");
			return;
			
		}
		else if (Pattern.matches("[a-zA-Z]+", _minTB.getText()) == true) {
			//TODO prompt user they must input a number;
			//prompt();
			System.out.println("error not numbers");
			return;
			
		}else {
				minimum=Integer.parseInt(_minTB.getText());
				if (minimum<=0) {
					System.out.println("minimum too low");
					//TODO prompt user less than 0;
					//prompt();
					return;
					
				}else if (minimum>99) {
					System.out.println("minimum too high");
					//TODO prompt user too high;
					//prompt();
					return;
					
				}
		}
		if (_maxTB.getText()==null||_maxTB.getText().length()==0) {
			System.out.println("error no input");
			//TODO show the user they must input something;
			//prompt();
			return;
			
		}
		else if (Pattern.matches("[a-zA-Z]+", _maxTB.getText()) == true) {
			System.out.println("error not numbers");
			//TODO prompt user they must input a number;
			//prompt();
			return;
			
		}
		else {
			maximum=Integer.parseInt(_maxTB.getText());
			if (maximum<=0) {
				System.out.println("minimum too low");
				//TODO prompt user less than 0;
				//prompt();
				return;
				
			}else if (maximum>99) {
				System.out.println("minimum too high");
				//TODO prompt user too high;
				//prompt();
				return;
				
			}
			if (maximum<minimum) {
				System.out.println("Invalid range");
				//TODO prompt user invalid range;
				//prompt();
				return;
				
			}
		}
		if(Math.round((float)_slider.getValue()) == 0) {
			
			//TODO prompt user cannot have 0 questions;
			//prompt();
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
			System.out.println("questions " + questions);
		}
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File file4 = new File(System.getProperty("user.dir")+"/Icons/png/quit.png");
		Image image4 = new Image(file4.toURI().toString());
		_mainMenu.setGraphic(new ImageView(image4));
		File file5 = new File(System.getProperty("user.dir")+"/fern.jpg");
		Image image5 = new Image(file5.toURI().toString());
		_imageView.setImage(image5);
		_imageView.setOpacity(0.3);
	}

}
