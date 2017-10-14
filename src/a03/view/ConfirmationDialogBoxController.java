package a03.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ConfirmationDialogBoxController extends Controller implements Initializable{
	@FXML private ImageView _imageView;
	@FXML private ImageView _logo;
	@FXML private boolean _leaveClicked=true;
	@FXML private Button _save;
	private Stage _dialogStage;
	private boolean _saveClicked=false;
	private boolean _stayClicked=false;

	// Event Listener on Button.onAction
	@FXML
	public void handleLeave(ActionEvent event) {
		_leaveClicked=true;
		_saveClicked=true;		
		_stayClicked=false;
		_dialogStage.close();
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleSave(ActionEvent event) {
		_saveClicked=true;		
		_stayClicked=false;
		_leaveClicked=false;
		_dialogStage.close();
	}
	@FXML
	public void handleStay(ActionEvent event) {
		_stayClicked=true;
		_saveClicked=false;
		_leaveClicked=false;
		_dialogStage.close();
	}
	public boolean LeaveClicked() {
		return _leaveClicked;
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
	public boolean SaveClicked() {
		return _saveClicked;
	}
	public boolean StayClicked() {
		return _stayClicked;
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
