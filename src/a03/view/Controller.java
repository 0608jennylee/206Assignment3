package a03.view;

import com.jfoenix.controls.JFXButton;

import a03.MainApp;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.scene.Node;

public abstract class Controller {
	protected transient MainApp _mainApp;
	
	/**
	 * sets the mainApp for the controller in order for the 
	 * controller know where to notify the events on the 
	 * start stage
	 * @param mainApp the mainApp that the scene is staged on
	 */
	public void setMainApp(MainApp app) {
		_mainApp = app;
		setMainAppHook();
	}
	
	public void setMainAppHook() {};
	
	@FXML
	private void handleHoverEnter(MouseEvent e) {
		Node b = (Node)e.getSource();
		FadeTransition ft = new FadeTransition(new Duration(500),b);
		ft.setFromValue(b.getOpacity());
		ft.setToValue(b.getOpacity() + 0.25);
		ft.play();
	}
	
	@FXML
	private void handleHoverExit(MouseEvent e) {
		JFXButton b = (JFXButton)e.getSource();
		FadeTransition ft = new FadeTransition(new Duration(500),b);
		ft.setFromValue(b.getOpacity());
		ft.setToValue(0.75);
		ft.play();
	}
}
