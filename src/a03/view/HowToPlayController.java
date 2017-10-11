package a03.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Displays a set of images to the user to show 
 * them how the application works
 * @author jenny
 *
 */
public class HowToPlayController extends Controller{
	private final int _numberOfImages=5;
	private int _count=1;
	private String _imageDirectory = "HowToPlayImages/";
	@FXML private Button _next;
	@FXML private Button _previous;
	@FXML private ImageView _imageView;
	
	/**
	 * when the back button is clicked notifies the stage 
	 * to switch scenes to the main menu
	 * @param event
	 */
	@FXML
	private void handleBack() {
		_mainApp.mainMenuContents();
	}
	
	/**
	 * when the start button is clicked, changes 
	 * the image to the one previous one in line
	 * @param event
	 */
	@FXML
	public void handlePrevious(){
//		if (_count<=_numberOfImages){
//			_next.setDisable(false);
//		}
//		_count--;
//		setImage();
//		if (_count==1){
//			_previous.setDisable(true);
//		}
	}
	
	/**
	 * when the start button is clicked, changes 
	 * the image to the one next in line
	 * @param event
	 */
	@FXML
	public void handleNext(){
//		if (_count>=1){
//			_previous.setDisable(false);
//		}
//		_count++;
//		setImage();
//		if (_count==_numberOfImages){
//			_next.setDisable(true);
//		}
	}

	/**
	 * Sets the first image of the scene and shows the image, 
	 * disables the previous button ast this is the first image 
	 * and there is no previous image
	 */
	public void setFirstImage() {
//		setImage();
//		_previous.setDisable(true);
	}

}
