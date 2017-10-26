package a03;

import java.io.IOException;
import a03.enumerations.Difficulty;
import a03.enumerations.GameState;
import a03.enumerations.Level;
import a03.view.CustomizeController;
import a03.view.ErrorBoxController;
import a03.view.ChartsController;
import a03.view.ChooseDifficultyController;
import a03.view.ChooseLevelController;
import a03.view.ConfirmationDialogBoxController;
import a03.view.GameController;
import a03.view.LoadLevelController;
import a03.view.MainMenuContentsController;
import a03.view.ScoreSceneController;
import a03.view.ScoreboardController;
import a03.view.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {
	
	private Stage _primaryStage;
	private GameState _gameState;
	private GameController _gameController;

	/**
	 * main to run the app
	 * @param args
	 */
	public static void main(String[] args) {
		//launch calls start().
		launch(args);
	}

	/**
	 * sets the stage of the app and initialises some properties to specific values such as non resizeable and onCloseRequest.
	 */
	@Override
	public void start(Stage primaryStage) {
		_primaryStage = primaryStage;
		_gameState = GameState.MENU;
		_primaryStage.setOnCloseRequest(this::exit);
		_primaryStage.setTitle("Tatai");
		_primaryStage.setMinHeight(450);
		_primaryStage.setMinWidth(700);
		_primaryStage.setResizable(false);
		GameStats.getGameStats();
		mainMenuContents();
	}
	
//----------------------------------- Scene Switching Facilitators ------------------------------

	/**
	 * Shows the main menu scene on the stage
	 */
	public void mainMenuContents() {
		try {
			
			_gameController = null;
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
	 * This method loads the LessThanTej.fxml file which represents the game scene.
	 * @param level - the level chosen by the user
	 * @param difficulty - the difficulty chosen by the user
	 * @param load - the boolean specifying weather the load option was chosen.
	 * 
	 * The game method is overridden to implement the functionality of loading and setting the default amount of questions to 10.
	 */
	public void Game(Difficulty difficulty, Level level, boolean load) {
		Game(difficulty, level, load, 10);
	}
	
	public void Game(Difficulty difficulty, Level level, boolean load, int questions) {
		try {
			_gameState = GameState.INGAME;
			//Load level 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Game.fxml"));
			AnchorPane lessThanTen = (AnchorPane) loader.load();
			
			//load level scene on primary stage
			Scene scene = new Scene(lessThanTen);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			
			// Give the controller access to the main app.
			_gameController = loader.getController();
			
			//check weather the controller values are required to be loaded in or not.
			if(load) {
				_gameController.load(level, difficulty);
			}else {
				_gameController.setDifficulty(difficulty, level, questions);
			}
			
			//give the controller access to MainApp object to facilitate scene transfers.
			_gameController.setMainApp(this);
			_gameController.setQuestion();
			//			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the scene where the final score is displayed.
	 * 
	 * @param score
	 * @param questions
	 * @param difficulty
	 * @param level
	 */
	public void Score(int score, int questions, Difficulty difficulty, Level level) {
		try {
			_gameState = GameState.INGAME;
			//Load level 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ScoreScene.fxml"));
			AnchorPane lessThanTen = (AnchorPane) loader.load();
			//load level scene on primary stage
			Scene scene = new Scene(lessThanTen);
			_primaryStage.setScene(scene);
			_primaryStage.show();
			// Give the controller access to the main app.
			ScoreSceneController controller = loader.getController();
			controller.setDifficulty(difficulty, level);
			controller.setMainApp(this);
			controller.setScore(score, questions);
			//			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * shows the start scene on the stage
	 * @param _level 
	 */
	public void Start(Level level, Difficulty difficulty, int questions) {
		try {
			_gameState = GameState.MENU;
			//Load start
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Start.fxml"));
			AnchorPane start = (AnchorPane) loader.load();
			//load start scene on primary stage
			Scene scene = new Scene(start);
			_primaryStage.setScene(scene);
			
			// Give the controller access to the main app.
			StartController controller = loader.getController();
			controller.setMainApp(this);
			controller.setLevel(level);
			controller.setDifficulty(difficulty);
			controller.setQuestions(questions);
			_primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * shows the choose level scene on the stage
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
	 * shows the choose difficulty scene on the stage
	 * @param level
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
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Switches the scene to the Charts.fxml scene.
	 */
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
	 * Loads the scene where users can customise their level to play.
	 */
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

	/**
	 * If there is a load file this method is called. It asks the user weather they want to load their save file or play a new game.
	 * @param level
	 * @param difficulty
	 */
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
	
	/**
	 * Loads the dialogue box that prompts the user to confirm any of their exit attempts.
	 * @param mainMenu
	 */
	public void confirmExit(boolean mainMenu) {
		try{// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ConfirmationDialogBox.fxml"));
			AnchorPane page= (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("");
			dialogStage.setResizable(false);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(_primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.

			ConfirmationDialogBoxController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(this);
			controller.setMainMenu(mainMenu);
			if(_gameState == GameState.INGAME){
				controller.setVisibleSaveButton();
				controller.setLessThanTenController(_gameController);
			}else {
				controller.setInvisibleSaveButton();
			}
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void customiseError(String text) {
		try{// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ErrorBox.fxml"));
			AnchorPane page= (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage errorStage = new Stage();
			errorStage.setTitle("");
			errorStage.setResizable(false);
			errorStage.initModality(Modality.WINDOW_MODAL);
			errorStage.initOwner(_primaryStage);
			Scene scene = new Scene(page);
			errorStage.setScene(scene);
			ErrorBoxController controller = loader.getController();
			controller.setDialogStage(errorStage);
			controller.setText(text);
			controller.setMainApp(this);
			// Show the dialog and wait until the user closes it
			errorStage.showAndWait();

		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

//--------------------------------------- End of Scene Switching Facilitators -----------------
//--------------------------------------- Getter and Setters ----------------------------------	
	
	public Stage getPrimaryStage() {
		return _primaryStage;
	}

	public void setGameState(GameState gamestate) {
		_gameState = gamestate;
	}

//--------------------------------------- End of Getters and Setters -----------------------	
	
	/**
	 * closes the app and saves all persistent files.
	 */
	public void exit(WindowEvent e) {
		if(e != null) {
			e.consume();
		}
		confirmExit(false);
	}

}