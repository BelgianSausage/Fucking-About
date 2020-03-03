package FuckingAround.setData;

import FuckingAround.Startup;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class setDataController {

	ObservableList <String> hours = FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
	ObservableList <String> minutes = FXCollections.observableArrayList("00","10","20","30","40","50");
	private Timeline timeline;
	private DoubleProperty timeSeconds = new SimpleDoubleProperty(), splitTimeSeconds = new SimpleDoubleProperty();
	private Duration time = Duration.ZERO, splitTime = Duration.ZERO;
	boolean first = true;
	String hour = "00";
	String minute = "00";

	@FXML
	private TextField nameField;

	@FXML
	private Label timerLabel;

	@FXML
	private Button startButton;

	@FXML
	private ComboBox<String> hourTimerDropdown;

	@FXML
	private ComboBox<String> minuteDropdown;

	@FXML
	private TextField nameFieldManual;

	@FXML
	private Button resetButton;

	@FXML
	private Button setActivityButton;

	@FXML
	private Button backButton;

	@FXML
	private ComboBox<String> hourStartManualDropdown;

	@FXML
	private ComboBox<String> minuteStartManualDropdown;

	@FXML
	private ComboBox<String> hourEndManualDropdown;

	@FXML
	private ComboBox<String> minuteEndManualDropdown;

	@FXML
	void initialize(){
		//timerLabel.textProperty().bind(timeSeconds.asString());
		hourTimerDropdown.setItems(hours);
		hourStartManualDropdown.setItems(hours);
		hourEndManualDropdown.setItems(hours);
		minuteDropdown.setItems(minutes);
		minuteStartManualDropdown.setItems(minutes);
		minuteEndManualDropdown.setItems(minutes);
	}

	@FXML
	void goToHomepage(ActionEvent event) {
		Startup.getFuckingAround().goToScene("homepage/Homepage.fxml");
	}

	@FXML
	void resetActivity(ActionEvent event) {
		hourStartManualDropdown.setValue("Hour");
		hourEndManualDropdown.setValue("Hour");
		minuteStartManualDropdown.setValue("Minute");
		minuteEndManualDropdown.setValue("Minute");
		nameFieldManual.clear();
	}

	@FXML
	void setActivity(ActionEvent event) {

	}

	@FXML
	void startTimer(ActionEvent event) {
		boolean canStart = checkForInformation();
		if(canStart){
			if(startButton.getText().equals("Start")){
				startButton.setText("Stop");
				nameField.setEditable(false);
				hourTimerDropdown.setDisable(true);
				minuteDropdown.setDisable(true);
				timer(true);
			}
			else{
				startButton.setText("Start");
				nameField.setEditable(true);
				hourTimerDropdown.setDisable(false);
				minuteDropdown.setDisable(false);
				timer(false);
			}
		}
		else{
			infoPopup();
		}
	}

	void timer(boolean start){
		if(!first){
			hour = "00";
			minute = "00";
			timeline.play();
		}
		if(start){
			time = Duration.ZERO;
			if (timeline != null) {
				splitTime = Duration.ZERO;
				splitTimeSeconds.set(splitTime.toSeconds());
			} else {
				timeline = new Timeline(
						new KeyFrame(Duration.millis(100),
								new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent t) {
								Duration duration = ((KeyFrame)t.getSource()).getTime();
								time = time.add(duration);
								splitTime = splitTime.add(duration);
								timeSeconds.set(time.toSeconds());
								decideTime();
								timerLabel.setText(hour + ":" + minute + ":" + timeSeconds.getValue().toString());
								splitTimeSeconds.set(splitTime.toSeconds());
							}
						})
						);
				timeline.setCycleCount(Timeline.INDEFINITE);
				timeline.play();
			}
		}
		else{
			timeline.pause();
			first = false;
		}

	}

	void decideTime(){
		if(timeSeconds.getValue() == 60){
			timeSeconds.set(0);
			time = Duration.ZERO;
			if(minute.charAt(1) == '9'){
				if(minute.charAt(0) == '5'){
					minute = "00";
					if(hour.charAt(1) == '9'){
						hour = ((hour.charAt(0) + 1) - 48) + "0";
					}
					else{
						hour = hour.charAt(0) + "" + ((hour.charAt(1) + 1) - 48) + "";
					}
				}
				else{
					minute = ((minute.charAt(0) + 1) - 48) + "0";
					System.out.println(minute);
				}
			}
			else{
				minute = minute.charAt(0) + "" + ((minute.charAt(1) + 1) - 48) + "";
			}
		}
	}

	boolean checkForInformation(){
		if(!hourTimerDropdown.getSelectionModel().isEmpty() && !minuteDropdown.getSelectionModel().isEmpty() && !nameField.getText().equals("")){
			return true;
		}
		else{
			return false;
		}
	}

	void infoPopup(){
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setTitle("Not Enough Information");
		Label label = new Label();
		label.setText("Not enough information was supplied to start the activity timer. \n Please make sure all 3 values are supplied before pressing the \n Start button.");
		Button closeButton = new Button("Understood!");
		closeButton.setOnAction(z -> popup.close());
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout, 350, 200);
		popup.setScene(scene);
		popup.showAndWait();
	}
}
