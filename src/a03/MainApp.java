package a03;
import java.io.File;
import java.io.IOException;
import a03.Settings;
import a03.enumerations.Difficulty;
import a03.enumerations.GameState;
import a03.enumerations.Level;
import a03.enumerations.Stats;
import a03.view.Controller;
import a03.view.CustomizeController;
import a03.view.ChartsController;
import a03.view.ChooseDifficultyController;
import a03.view.ChooseLevelController;
import a03.view.ConfirmationDialogBoxController;
import a03.view.HowToPlayController;
import a03.view.LessThanTenController;
import a03.view.LoadLevelController;
import a03.view.MainMenuContentsController;
import a03.view.ScoreBoardController;
import a03.view.ScoreboardController;
import a03.view.StartController;
import a03.view.StatisticsController;
//import a03.view.StartController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.StageStyle;

public class MainApp extends Application {
	//	public enum Level {hard(""),easy};
	private Stage _primaryStage;
	private GameState _gameState;
	private LessThanTenController LTTController;

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
			LTTController = null;
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * shows the level scene on the stage, this is the scene where the game happens
	 * @param level the level chosen by the user
	 */
	public void Game(Difficulty difficulty, Level level) {
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
			LTTController = loader.getController();
			LTTController.setDifficulty(difficulty, level);
			LTTController.setMainApp(this);
			LTTController.setQuestion();
			//			
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
	 * @param _level 
	 */
	public void Start(Level level, Difficulty difficulty) {
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
			controller.setDifficulty(difficulty);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * shpws the choose level scene on the stage
	 * @param numbers 
	 */
	public void chooseLevel() {
		try {
			//Load choose level
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ChooseLevel.fxml"));
			AnchorPane chooseLevels = (AnchorPane) loader.load();
			//load choose level scene on primary stage
			Scene scene = new Scene(chooseLevels);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			ChooseLevelController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * shpws the choose level scene on the stage
	 * @param numbers 
	 */
	public void chooseDifficulty(Level level) {
		try {
			_gameState = GameState.MENU;
			//Load choose level
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ChooseDifficulty.fxml"));
			AnchorPane chooseLevels = (AnchorPane) loader.load();
			//load choose level scene on primary stage
			Scene scene = new Scene(chooseLevels);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			ChooseDifficultyController controller = loader.getController();
			if(!Settings.getSettings().settings.get("HARDLEVEL")) {
				controller.disableHard();
			}
			controller.setMainApp(this);
			controller.setLevel(level);

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
			loader.setLocation(MainApp.class.getResource("view/Scoreboard.fxml"));
			AnchorPane statistics = (AnchorPane) loader.load();
			//load statistics scene on the primary stage
			Scene scene = new Scene(statistics);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			ScoreboardController controller = loader.getController();
			controller.setMainApp(this);
			//controller.setScores();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void Charts() {
		try {
			//Load statistics 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Charts.fxml"));
			AnchorPane charts = (AnchorPane) loader.load();
			//load statistics scene on the primary stage
			Scene scene = new Scene(charts);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			ChartsController controller = loader.getController();
			controller.setMainApp(this);
			//controller.setScores();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * closes the app and saves the settings
	 */
	public void exit(WindowEvent e) {
		try{// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ConfirmationDialogBox.fxml"));
			AnchorPane page= (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(_primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.

			ConfirmationDialogBoxController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			//controller.setMainApp(this);
			if(_gameState == GameState.INGAME){
				controller.setVisibleSaveButton();
			}else {
				controller.setInvisibleSaveButton();
			}
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			if(controller.LeaveClicked()) {
				Settings.getSettings().save();
				deleteRecordings();
				Platform.exit();
			}else if(controller.SaveClicked()){
				if(LTTController != null) {
					LTTController.save();
				}
				deleteRecordings();
				Settings.getSettings().save();
				Platform.exit();
			}else {
				if(e != null) {
					e.consume();
				}
			}
		}catch (IOException e1) {
			e1.printStackTrace();
		}
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

	public void LoadLevel(Level level, Difficulty difficulty) {
		try {
			//Load statistics 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/LoadLevel.fxml"));
			AnchorPane charts = (AnchorPane) loader.load();
			//load statistics scene on the primary stage
			Scene scene = new Scene(charts);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			LoadLevelController controller = loader.getController();
			controller.setMainApp(this);
			controller.setLevel(level);
			controller.setDifficulty(difficulty);
			//controller.setScores();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void loadGame(Level level, Difficulty difficulty) {
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
			LTTController = loader.getController();
			LTTController.load(level, difficulty);
			LTTController.setMainApp(this);
			LTTController.setQuestion();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setGameState(GameState gamestate) {
		_gameState = gamestate;
	}

	public void customize() {
		try {
			//Load statistics 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Customize.fxml"));
			AnchorPane charts = (AnchorPane) loader.load();
			//load statistics scene on the primary stage
			Scene scene = new Scene(charts);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			CustomizeController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}