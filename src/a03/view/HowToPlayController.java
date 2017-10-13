package a03.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Displays a set of images to the user to show 
 * them how the application works
 * @author jenny
 *
 */
public class HowToPlayController extends Controller implements Initializable{
	private final int _numberOfImages=5;
	private int _count=1;
	private String _imageDirectory = "HowToPlayImages/";
	@FXML private Button _next;
	@FXML private Button _previous;
	@FXML private Button _back;
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File file4 = new File(System.getProperty("user.dir")+"/Icons/png/quit.png");
		Image image4 = new Image(file4.toURI().toString());
		_back.setGraphic(new ImageView(image4));
		File file1 = new File(System.getProperty("user.dir")+"/Icons/png/arrow-circle-left-8x.png");
		Image image1 = new Image(file1.toURI().toString());
		_previous.setGraphic(new ImageView(image1));
		File file3 = new File(System.getProperty("user.dir")+"/Icons/png/arrow-circle-right-8x.png");
		Image image3 = new Image(file3.toURI().toString());
		_next.setGraphic(new ImageView(image3));
		File file5 = new File(System.getProperty("user.dir")+"/fern.jpg");
		Image image5 = new Image(file5.toURI().toString());
		_imageView.setImage(image5);
		_imageView.setOpacity(0.2);
		
	}

}
