package a03.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jfoenix.controls.JFXButton;

import a03.LogData;
import a03.MainApp;
import a03.enumerations.ChartTypes;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChartsController extends Controller implements Initializable{
	@FXML
	private JFXButton _back;
	@FXML
	private BarChart<String, Number> _barChart;
	@FXML
	private JFXButton _previous;
	@FXML
	private JFXButton _next;
	private MainApp _mainApp;
	private ChartTypes _chartType = ChartTypes.EASYNUMBERS;
	@FXML private ImageView _imageView;
	@FXML private NumberAxis _yAxis;
	// Event Listener on JFXButton[#_back].onAction
	@FXML
	public void handleBack(ActionEvent event) {
		_mainApp.mainMenuContents();
	}
	// Event Listener on JFXButton[#_left].onAction
	@FXML
	public void handleLeft(ActionEvent event) {
		ChartTypes[] e = ChartTypes.values();
		for(int i = 0; i < e.length; i++) {
			if(e[i] == _chartType) {
				if(i == 0) {
					_chartType = e[e.length - 1];
					break;
				}else {
					_chartType = e[i - 1];
					break;
				}
			}
		}
		changeChart();
	}
	// Event Listener on JFXButton[#_right].onAction
	@FXML
	public void handleRight(ActionEvent event) {
		ChartTypes[] e = ChartTypes.values();
		for(int i = 0; i < e.length; i++) {
			if(e[i] == _chartType) {
				if(i == e.length - 1) {
					_chartType = e[0];
					break;
				}else {
					_chartType = e[i + 1];
					break;
				}
			}
		}
		changeChart();
	}
	public void setMainApp(MainApp mainApp) {
		_mainApp=mainApp;
		changeChart();
	}
	public void changeChart() {    
		try {
		//			      CategoryAxis xAxis = new CategoryAxis();  
		//			      xAxis.setCategories(FXCollections.<String>
		//			      observableArrayList(Arrays.asList("Speed", "User rating", "Milage", "Safety")));
		//			      xAxis.setLabel("category");
		//			       
		//			      NumberAxis yAxis = new NumberAxis();
		//			      yAxis.setLabel("score");
		//			     
		//Creating the Bar chart
		Gson g = new Gson();
		_barChart.setTitle(_chartType.toString() + "Recent High Scores");
		System.out.println("Changed");
		XYChart.Series<String, Number> series2 = new XYChart.Series<>();
		series2.setName("Score(%)");
		System.out.println("Logs/" + _chartType.getFileSuffix());
		if(new File("Logs/" + _chartType.getFileSuffix()).exists()) {
			System.out.println("passed if");
			BufferedReader br = new BufferedReader(new FileReader("Logs/" + _chartType.getFileSuffix()));
			String line = null;
			while((line = br.readLine()) != null) {
				System.out.println("Loaded");
				LogData logData = g.fromJson(line, LogData.class);
				series2.getData().add(new XYChart.Data<>(logData.toString(),logData.toRatio()));
			}
			_barChart.getData().add(series2);
		}else {
			_barChart.getData().clear();
		}

		
//		series2.getData().add(new XYChart.Data<>("a", 50.0));
//		series2.getData().add(new XYChart.Data<>("b", 80.0));
//		series2.getData().add(new XYChart.Data<>("c", 100.0));
//		series2.getData().add(new XYChart.Data<>("d", 20.0));
		//Setting the data to bar chart       
		_barChart.getXAxis().setAnimated(false);
		_barChart.getYAxis().setAnimated(true);
		_barChart.setAnimated(true);
		_barChart.lookupAll(".default-color0.chart-bar")
		.forEach(n -> n.setStyle("-fx-bar-fill: orange;"));
		}catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//		_anchor.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		_yAxis.setAutoRanging(false);
		_yAxis.setLowerBound(0);
		_yAxis.setUpperBound(100);
		_yAxis.setTickUnit(10);
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
		_imageView.setOpacity(0.1);

	}
}
