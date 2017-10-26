package a03.view;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import a03.GameStats;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ConfirmationDialogBoxController extends Controller implements Initializable{
	@FXML private ImageView _imageView;
	@FXML private ImageView _logo;
	@FXML private boolean _leaveClicked=false;
	@FXML private Button _save;
	private Stage _dialogStage;
	private GameController _gameController;
	private boolean _mainMenu = false;

	// Event Listener on Button.onAction. Saves game stats and clear recordings.
	@FXML
	public void handleLeave(ActionEvent event) {
		GameStats.getGameStats().save();
		deleteRecordings();
		//check to see if exit application or return to main menu.
		if(_mainMenu) {
			_dialogStage.close();
			_mainApp.mainMenuContents();
		}else {
			_dialogStage.close();
			Platform.exit();
		}
	}
	
	// Event Listener on Button.onAction. Save everything, gameStats and the current game values.
	@FXML
	public void handleSave(ActionEvent event) {
		_gameController.save();
		deleteRecordings();
		
		//check for if returning to main menu or not.
		if(_mainMenu) {
			_dialogStage.close();
			_mainApp.mainMenuContents();
		}else {
			GameStats.getGameStats().save();
			_dialogStage.close();
			Platform.exit();
		}
	}

	@FXML
	public void handleStay(ActionEvent event) {
		_dialogStage.close();
	}
	
	public void setDialogStage(Stage dialogStage) {
		_dialogStage=dialogStage;
	}
	
	public void setVisibleSaveButton() {
		_save.setVisible(true);
	}
	
	public void setInvisibleSaveButton() {
		_save.setVisible(false);
	}
	
	public void setLessThanTenController(GameController LTTC) {
		_gameController = LTTC;
	}
	
	public void setMainMenu(boolean mainMenu) {
		_mainMenu = mainMenu;
	}
	
	private void deleteRecordings() {
		boolean found = false;
		File[] files = new File("Saves").listFiles();
		if(files != null) {
			for(File f : new File("Recordings").listFiles()) {
				for(File save : files) {
					if(save.getName().equals(f.getName())) {
						found = true;
					}
				}
				if(!found) {
					f.delete();
				}
				found = false;
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image quit = new Image(getClass().getClassLoader().getResource("Icons/quit.png").toString());//
		Image background = new Image(getClass().getClassLoader().getResource("fern.jpg").toString());//
		_imageView.setFitWidth(500);
		_imageView.setImage(background);
		_logo.setImage(quit);
		_imageView.setOpacity(0.1);

	}
}
