package a03.view;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jfoenix.controls.JFXButton;

import a03.LogData;
import a03.MainApp;
import a03.enumerations.ChartTypes;
import javafx.event.ActionEvent;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChartsController extends Controller implements Initializable{
	@FXML private JFXButton _back;
	@FXML private BarChart<String, Number> _barChart;
	@FXML private JFXButton _previous;
	@FXML private JFXButton _next;
	@FXML private ImageView _imageView;
	@FXML private NumberAxis _yAxis;
	
	private MainApp _mainApp;
	private ChartTypes _chartType = ChartTypes.EASYNUMBERS;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		_yAxis.setAutoRanging(false);
		_yAxis.setLowerBound(0);
		_yAxis.setUpperBound(100);
		_yAxis.setTickUnit(10);
		_barChart.getXAxis().setAnimated(false);
		_barChart.getYAxis().setAnimated(false);
		_barChart.setAnimated(false);
		Image right = new Image(getClass().getClassLoader().getResource("Icons/right.png").toString());//
		_next.setGraphic(new ImageView(right));
		Image left = new Image(getClass().getClassLoader().getResource("Icons/left.png").toString());//
		_previous.setGraphic(new ImageView(left));
		Image quit = new Image(getClass().getClassLoader().getResource("Icons/quit.png").toString());//
		_back.setGraphic(new ImageView(quit));
		Image background = new Image(getClass().getClassLoader().getResource("fern.jpg").toString());//
		_imageView.setImage(background);
		_imageView.setOpacity(0.1);
	
	}

	// Event Listener on JFXButton[#_back].onAction
	@FXML
	public void handleBack(ActionEvent event) {
		_mainApp.mainMenuContents();
	}
	
	// Event Listener on JFXButton[#_left].onAction
	@FXML
	public void handleLeft(ActionEvent event) {
		_barChart.getData().clear();

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
		_barChart.getData().clear();

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
		//Creating and initialising the Bar chart
		Gson g = new Gson();
		_barChart.setTitle(_chartType.toString() + "Recent High Scores");
		XYChart.Series<String, Number> series2 = new XYChart.Series<>();
		_barChart.setLegendVisible(false);
		series2.setName("Score(%)");

		//If saved data exists fetch and load it into the chart for the respective levels.
		if(new File("Logs/" + _chartType.getFileSuffix()).exists()) {
			BufferedReader br = new BufferedReader(new FileReader("Logs/" + _chartType.getFileSuffix()));
			String line = null;
			int totalLines = 0;
			int linesRead = 0;
			//first pass to calculate total number of lines in a file
			while((line = br.readLine()) != null) {
				totalLines++;
			}
			line = null;
			br = new BufferedReader(new FileReader("Logs/" + _chartType.getFileSuffix()));
			while((line = br.readLine()) != null) {
				linesRead++;
				if(linesRead >= totalLines - 9) {
				LogData logData = g.fromJson(line, LogData.class);
				series2.getData().add(new XYChart.Data<>(logData.toString(),logData.toRatio()));
				}
				}
			_barChart.getData().addAll(series2);
		}
		
		//Setting the color of the bars to orange.
		_barChart.lookupAll(".default-color0.chart-bar")
		.forEach(n -> n.setStyle("-fx-bar-fill: orange;"));
		}catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
