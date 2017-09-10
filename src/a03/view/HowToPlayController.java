package a03.view;

import a03.MainApp;
import javafx.fxml.FXML;

public class HowToPlayController {

	private MainApp _mainApp;
	
	@FXML
	private void handleBack() {
		_mainApp.mainMenuContents();
	}
	
	public void setMainApp(MainApp mainApp) {
		_mainApp = mainApp;
	}
}
