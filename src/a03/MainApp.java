package a03;
import java.io.IOException;
import a03.Settings;
import a03.enumerations.GameState;
import a03.enumerations.Level;
import a03.enumerations.Stats;
import a03.view.ChooseLevelsController;
import a03.view.HowToPlayController;
import a03.view.LessThanTenController;
import a03.view.MainMenuContentsController;
import a03.view.StartController;
import a03.view.StatisticsController;
//import a03.view.StartController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class MainApp extends Application {
	//	public enum Level {hard(""),easy};
	private Stage _primaryStage;
	private GameState _gameState;

	/**
	 * Constructor
	 */
	public MainApp() {
	}

	/**
	 * sets the stage of the app
	 */
	@Override
	public void start(Stage primaryStage) {
		//sets the primary stage as the stage the app is running on
		_primaryStage = primaryStage;
		_gameState = GameState.MENU;
		_primaryStage.setOnCloseRequest(this::exit);
		//sets the title
		_primaryStage.setTitle("Tatai");
		_primaryStage.setMinHeight(450);
		_primaryStage.setMinWidth(700);
//		_primaryStage.setResizable(false);
//		_primaryStage.initStyle(StageStyle.UNDECORATED);
		GameStats.getGameStats().updateDiscrete(Stats.APPSTARTTIME.toString(), new Integer((int) (System.currentTimeMillis() / (1000 * 60))));
		mainMenuContents();
	}

	/**
	 * Shows the main menu scene on the stage
	 */
	public void mainMenuContents() {
		try {
			_gameState = GameState.MENU;
			//Load main menu 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MainMenuContents.fxml"));
			AnchorPane mainMenu = (AnchorPane) loader.load();
			//load main menu scene on primary stage
			Scene scene = new Scene(mainMenu);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			MainMenuContentsController controller = loader.getController();
			controller.setMainApp(this);
			controller.setStage(_primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * shows the level scene on the stage, this is the scene where the game happens
	 * @param level the level chosen by the user
	 */
	public void Level(Level level) {
		try {
			_gameState = GameState.INGAME;
			//Load level 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/LessThanTen.fxml"));
			AnchorPane lessThanTen = (AnchorPane) loader.load();
			//load level scene on primary stage
			Scene scene = new Scene(lessThanTen);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			LessThanTenController controller = loader.getController();
			controller.setMainApp(this);
			controller.setLevel(level);
			controller.setQuestion();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * shows the how to play scene on the stage, displays a set of images that 
	 * demonstrate how the game is played
	 */
	public void howToPlay() {
		try {
			_gameState = GameState.MENU;
			//Load how to play 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/HowToPlay.fxml"));
			AnchorPane howToPlay = (AnchorPane) loader.load();
			//mload how to play on primary stage
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

	/**
	 * shows the start scene on the stage
	 */
	public void Start(Level level) {
		try {
			_gameState = GameState.MENU;
			//Load start
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Start.fxml"));
			AnchorPane start = (AnchorPane) loader.load();
			//load start scene on primary stage
			Scene scene = new Scene(start);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			StartController controller = loader.getController();
			controller.setMainApp(this);
			controller.setLevel(level);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * shpws the choose level scene on the stage
	 */
	public void chooseLevels() {
		try {
			_gameState = GameState.MENU;
			//Load choose level
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ChooseLevels.fxml"));
			AnchorPane chooseLevels = (AnchorPane) loader.load();
			//load choose level scene on primary stage
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

	/**
	 * shows the statistics scene on the stage
	 */
	public void Statistics() {
		try {
			_gameState = GameState.MENU;
			//Load statistics 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Statistics.fxml"));
			BorderPane statistics = (BorderPane) loader.load();
			//load statistics scene on the primary stage
			Scene scene = new Scene(statistics);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			StatisticsController controller = loader.getController();
			controller.setMainApp(this);
			controller.setScores();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {

		}
	}
	

	/**
	 * closes the app and saves the settings
	 */
	public void exit(WindowEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you would like to exit the game?", new ButtonType("Stay"), new ButtonType("Quit"));
		if(_gameState == GameState.INGAME){
			alert.getButtonTypes().add(new ButtonType("Save and Quit"));
			alert.showAndWait();
		}else {
			alert.getDialogPane().setMinWidth(Region.USE_COMPUTED_SIZE);
			alert.showAndWait();
		}
		Settings.getSettings().save();
		Platform.exit();
	}

	/**
	 * @return
	 */
	public Stage getPrimaryStage() {
		return _primaryStage;
	}

	/**
	 * main to run the app
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}