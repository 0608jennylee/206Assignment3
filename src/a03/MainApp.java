package a03;
import java.io.IOException;
import a03.Settings;
import a03.view.ChooseLevelsController;
import a03.view.HowToPlayController;
import a03.view.LessThanTenController;
import a03.view.MainMenuContentsController;
import a03.view.StatisticsController;
//import a03.view.StartController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import a03.Level;

public class MainApp extends Application {
	//	public enum Level {hard(""),easy};
	private Stage _primaryStage;

	/**
	 * Constructor
	 */
	public MainApp() {
	}

	@Override
	public void start(Stage primaryStage) {
		_primaryStage = primaryStage;
		_primaryStage.setTitle("Tatai");
		_primaryStage.initStyle(StageStyle.UNDECORATED);
		GameStats.getGameStats().updateDiscrete(Stats.APPSTARTTIME.toString(), new Integer((int) (System.currentTimeMillis() / (1000 * 60))));
		mainMenuContents();
	}

	/**
	 * Shows the main menu inside the root layout.
	 */
	public void mainMenuContents() {
		try {
			//Load main menu 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MainMenuContents.fxml"));
			AnchorPane mainMenu = (AnchorPane) loader.load();
			//main menu into the centre of root layout.
			Scene scene = new Scene(mainMenu);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			MainMenuContentsController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Level(Level level) {
		try {
			//Load main menu 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/LessThanTen.fxml"));
			AnchorPane lessThanTen = (AnchorPane) loader.load();
			//main menu into the centre of root layout.
			Scene scene = new Scene(lessThanTen);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			LessThanTenController controller = loader.getController();
			controller.setMainApp(this);
			controller.setLevel(level);
			controller.setQuestion();
//			controller.setRoot(lessThanTen);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void howToPlay() {
		try {
			//Load main menu 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/HowToPlay.fxml"));
			AnchorPane howToPlay = (AnchorPane) loader.load();
			//main menu into the centre of root layout.
			Scene scene = new Scene(howToPlay);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			HowToPlayController controller = loader.getController();
			controller.setMainApp(this);
			controller.setFirstImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//	public void Start(Level level) {
	//		try {
	//			//Load main menu 
	//			FXMLLoader loader = new FXMLLoader();
	//			loader.setLocation(MainApp.class.getResource("view/Start.fxml"));
	//			AnchorPane start = (AnchorPane) loader.load();
	//			//main menu into the centre of root layout.
	//			Scene scene = new Scene(start);
	//			_primaryStage.setScene(scene);
	//			_primaryStage.show();
	//			// Give the controller access to the main app.
	//			StartController controller = loader.getController();
	//			controller.setMainApp(this);
	//			controller.setLevel(level);
	//
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//	}

	public void chooseLevels() {
		try {
			//Load main menu 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ChooseLevels.fxml"));
			AnchorPane chooseLevels = (AnchorPane) loader.load();
			//main menu into the centre of root layout.
			Scene scene = new Scene(chooseLevels);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			ChooseLevelsController controller = loader.getController();
			if(!Settings.getSettings().settings.get("HARDLEVEL")) {
				controller.disableHard();
			}
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Statistics() {
		try {
			//Load main menu 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Statistics.fxml"));
			BorderPane statistics = (BorderPane) loader.load();
			//main menu into the centre of root layout.
			Scene scene = new Scene(statistics);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			StatisticsController controller = loader.getController();
			controller.setMainApp(this);
			controller.setScores();
			System.out.println("Statistics run");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			
		}
	}

	public void exit() {
		Platform.exit();
	}

	/**
	 * @return
	 */
	public Stage getPrimaryStage() {
		return _primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}