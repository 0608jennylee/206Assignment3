package a03.view;

import a03.MainApp;

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
}
