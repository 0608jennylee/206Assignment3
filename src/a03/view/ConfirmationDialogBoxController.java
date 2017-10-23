package a03.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import a03.Settings;
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
	private boolean _saveClicked=false;
	private boolean _stayClicked=false;
	private LessThanTenController _LTTC;
	private boolean _mainMenu = false;

	// Event Listener on Button.onAction
	@FXML
	public void handleLeave(ActionEvent event) {
		Settings.getSettings().save();
		deleteRecordings();
		if(_mainMenu) {
			_dialogStage.close();
			_mainApp.mainMenuContents();
		}else {
			_dialogStage.close();
			Platform.exit();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleSave(ActionEvent event) {
		System.out.print(Thread.currentThread());
		_LTTC.save();
		deleteRecordings();
		Settings.getSettings().save();
		if(_mainMenu) {
			_dialogStage.close();
			_mainApp.mainMenuContents();
		}else {
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
	public void setLessThanTenController(LessThanTenController LTTC) {
		_LTTC = LTTC;
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
		File file4 = new File(System.getProperty("user.dir")+"/Icons/png/quit.png");
		Image image4 = new Image(file4.toURI().toString());
		_logo.setImage(image4);
		File file5 = new File(System.getProperty("user.dir")+"/fern.jpg");
		Image image5 = new Image(file5.toURI().toString());
		_imageView.setFitWidth(500);
		_imageView.setImage(image5);
		_imageView.setOpacity(0.1);
		
	}
}
