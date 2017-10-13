package a03.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import a03.MainApp;
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


	private MainApp _mainApp;

	@FXML private void handleMainMenu() {
		_mainApp.mainMenuContents();
	}
	@FXML private void handleOK() {
		int minimum=1;
		if (_numbersCB.isArmed()) {
			System.out.println("numbers ticked");
		}
		if (_equationsCB.isArmed()) {
			System.out.println("equations ticked");
		}
		if (_minTB.getText()==null||_minTB.getText().length()==0) {
			System.out.println("error no input");
		}
		if (Pattern.matches("[a-zA-Z]+", _minTB.getText()) == true) {
			System.out.println("error not numbers");
		}
		try {
			minimum=Integer.parseInt(_minTB.getText());
			if (minimum<=0) {
				System.out.println("minimum too low");
			}else if (minimum>99) {
				System.out.println("minimum too high");
			}
		}catch(NumberFormatException e) {
			System.out.println("invalid choice of numbers");
		}
		if (_maxTB.getText()==null||_maxTB.getText().length()==0) {
			System.out.println("error no input");
		}
		if (Pattern.matches("[a-zA-Z]+", _maxTB.getText()) == true) {
			System.out.println("error not numbers");
		}
		try {
			int maximum=Integer.parseInt(_maxTB.getText());
			if (maximum<=0) {
				System.out.println("minimum too low");
			}else if (maximum>99) {
				System.out.println("minimum too high");
			}
			if (maximum<minimum) {
				System.out.println("Invalid range");
			}
		}catch(NumberFormatException e) {
			System.out.println("invalid choice of numbers");
		}

		
			System.out.println(_slider.getValue()+" questions");

	}
	public void setMainApp(MainApp mainApp) {
		_mainApp=mainApp;
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
