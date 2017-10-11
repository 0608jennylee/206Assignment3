package a03.view;

import javafx.fxml.FXML;

import java.util.Arrays;

import com.jfoenix.controls.JFXButton;

import a03.MainApp;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ChartsController {
	@FXML
	private JFXButton _back;
	@FXML
	private BarChart<String, Number> _barChart;
	@FXML
	private JFXButton _left;
	@FXML
	private JFXButton _right;
	private MainApp _mainApp;
	private int _currentChart;

	// Event Listener on JFXButton[#_back].onAction
	@FXML
	public void handleBack(ActionEvent event) {
		_mainApp.mainMenuContents();
	}
	// Event Listener on JFXButton[#_left].onAction
	@FXML
	public void handleLeft(ActionEvent event) {
		_currentChart++;
		changeChart();
	}
	// Event Listener on JFXButton[#_right].onAction
	@FXML
	public void handleRight(ActionEvent event) {
		_currentChart--;
		changeChart();
	}
	public void setMainApp(MainApp mainApp) {
		_mainApp=mainApp;
		changeChart();
	}
	public void changeChart() {             
		//			      CategoryAxis xAxis = new CategoryAxis();  
		//			      xAxis.setCategories(FXCollections.<String>
		//			      observableArrayList(Arrays.asList("Speed", "User rating", "Milage", "Safety")));
		//			      xAxis.setLabel("category");
		//			       
		//			      NumberAxis yAxis = new NumberAxis();
		//			      yAxis.setLabel("score");
		//			     
		//Creating the Bar chart
		// _barChart = new BarChart<>(xAxis, yAxis); 
		_barChart.setTitle("Recent High scores");

		XYChart.Series<String, Number> series2 = new XYChart.Series<>();
		series2.setName("scores");
		series2.getData().add(new XYChart.Data<>("Speed", 5.0));
		series2.getData().add(new XYChart.Data<>("User rating", 6.0));
		series2.getData().add(new XYChart.Data<>("Milage", 10.0));
		series2.getData().add(new XYChart.Data<>("Safety", 4.0));

		//Setting the data to bar chart       
		_barChart.getData().addAll(series2);
		_barChart.lookupAll(".default-color0.chart-bar")
		.forEach(n -> n.setStyle("-fx-bar-fill: orange;"));
	}
}
