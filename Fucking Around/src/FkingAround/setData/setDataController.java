package FkingAround.setData;

import FkingAround.Startup;
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

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class setDataController {

	ObservableList <String> hours = FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
	ObservableList <String> minutes = FXCollections.observableArrayList("00","10","20","30","40","50");
	ObservableList <String> goalNames = FXCollections.observableArrayList();
	ObservableList <String> day = FXCollections.observableArrayList("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31");
	ObservableList <String> month = FXCollections.observableArrayList("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Nov","Oct","Dec");
	ObservableList <String> year = FXCollections.observableArrayList("2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030");
	
	private Timeline timeline;
	private DoubleProperty timeSeconds = new SimpleDoubleProperty(), splitTimeSeconds = new SimpleDoubleProperty();
	private Duration time = Duration.ZERO, splitTime = Duration.ZERO;
	boolean first = true;
	String hour = "00";
	String minute = "00";
	dataPoint Dp;
	String start;
	String end;
	DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	Date date = new Date();
	int goalCount;
	String [][] goals = new String [50][6];

	{ Dp = new dataPoint(); }

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
	private ComboBox<String> goalDropdown;
	
	@FXML
	private ComboBox<String> goalDropdownManual;
	
	@FXML
	private ComboBox<String> dayDropdown;
	
	@FXML
	private ComboBox<String> monthDropdown;

	@FXML
	private ComboBox<String> yearDropdown;
	
	@FXML
	void initialize(){
		//timerLabel.textProperty().bind(timeSeconds.asString());
		hourTimerDropdown.setItems(hours);
		hourStartManualDropdown.setItems(hours);
		hourEndManualDropdown.setItems(hours);
		minuteDropdown.setItems(minutes);
		minuteStartManualDropdown.setItems(minutes);
		minuteEndManualDropdown.setItems(minutes);
		readGoals();
		insertGoalNames();
		goalDropdown.setItems(goalNames);
		goalDropdownManual.setItems(goalNames);
		dayDropdown.setItems(day);
		monthDropdown.setItems(month);
		yearDropdown.setItems(year);
	}

	@FXML
	void goToHomepage(ActionEvent event) {
		Startup.getFuckingAround().goToScene("homepage/Homepage.fxml");
	}

	@FXML
	void resetActivity(ActionEvent event) {
		hourStartManualDropdown.setValue("Hour");
		hourEndManualDropdown.setValue("Hour");
		minuteStartManualDropdown.setValue("Min");
		minuteEndManualDropdown.setValue("Min");
		goalDropdown.setValue("Goal");
		dayDropdown.setValue("Day");
		monthDropdown.setValue("Month");
		yearDropdown.setValue("Year");
		nameFieldManual.clear();
	}

	@FXML
	void setActivity(ActionEvent event) throws ParseException {
		boolean completeEdit = checkForInformationManual();
		if(completeEdit) {
			start = hourStartManualDropdown.getValue() + ":" + minuteStartManualDropdown.getValue();
			end = hourEndManualDropdown.getValue() + ":" + minuteEndManualDropdown.getValue();
			String Mon = monthDropdown.getValue();
			String month = monthNumberer(Mon);
			String customDate = yearDropdown.getValue() + "/" + month + "/" + dayDropdown.getValue();
			if (!goalDropdownManual.getValue().equals("No goal")) {
				float duration = getDuration(hourStartManualDropdown.getValue(), minuteStartManualDropdown.getValue(), hourEndManualDropdown.getValue(), minuteEndManualDropdown.getValue());
				updateGoalCompletion(goalDropdownManual.getValue(), duration);
			}
			Dp.manualDurationCalc(nameFieldManual.getText(), customDate, start, end);
		}
	}

	public float getDuration(String hour1, String minute1, String hour2, String minute2) {
		int startTime = Integer.parseInt(hour1) * 60 + Integer.parseInt(minute1);
		int endTime = Integer.parseInt(hour2) * 60 + Integer.parseInt(minute2);
		float duration;
		if (startTime <= endTime) {
			duration = endTime - startTime;
		} else {
			duration = 24 * 60 + endTime - startTime;
		}

		return duration;
	}
	public void updateGoalCompletion(String goalName, float duration) {
		System.out.println(duration);
		for (int i=0; i<goalCount; i++) {
			if (goals[i][0] != null && goals[i][0].equals(goalName)) {
				System.out.println(goalName + " " + goals[i][0] + " " + duration + " " + goals[i][3]);
				if (goals[i][3].equals("0")) {
					if (Float.parseFloat(goals[i][4]) + duration/Float.parseFloat(goals[i][5]) < 1) {
						goals[i][4] = Float.toString(Float.parseFloat(goals[i][4]) + duration/Float.parseFloat(goals[i][5]));
					} else {
						goals[i][4] = "1";
					}
				} else {
					if (Float.parseFloat(goals[i][4]) + 1/Float.parseFloat(goals[i][5]) < 1) {
						goals[i][4] = Float.toString(Float.parseFloat(goals[i][4]) + 1/Float.parseFloat(goals[i][5]));
						System.out.println(goals[i][4]);
					} else {
						goals[i][4] = "1";
					}
				}
			}

		}
		writeGoals();
	}

	public String monthNumberer(String month) {
		if(month == "Jan"){
			return "01";
		}
		else if(month == "Feb"){
			return "02";
		}
		else if(month == "Mar"){
			return "03";
		}
		else if(month == "Apr"){
			return "04";
		}
		else if(month == "May"){
			return "05";
		}
		if(month == "Jun"){
			return "06";
		}
		if(month == "Jul"){
			return "07";
		}
		else if(month == "Aug"){
			return "08";
		}
		else if(month == "Sep"){
			return "09";
		}
		else if(month == "Oct"){
			return "10";
		}
		else if(month == "Nov"){
			return "11";
		}
		else{
			return "12";
		}
	}

	boolean checkForInformationManual(){
		if(!nameFieldManual.getText().equals("") && !hourStartManualDropdown.getSelectionModel().isEmpty() && !minuteStartManualDropdown.getSelectionModel().isEmpty() && !hourEndManualDropdown.getSelectionModel().isEmpty() && !minuteEndManualDropdown.getSelectionModel().isEmpty()  && !dayDropdown.getSelectionModel().isEmpty() && !monthDropdown.getSelectionModel().isEmpty() && !yearDropdown.getSelectionModel().isEmpty() && !goalDropdownManual.getSelectionModel().isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}

	@FXML
	void startTimer(ActionEvent event) {
		boolean canStart = checkForInformation();
		if(canStart){
			if(startButton.getText().equals("Start")){
				start = hourTimerDropdown.getValue() + ":" + minuteDropdown.getValue();
				startButton.setText("Stop");
				nameField.setEditable(false);
				hourTimerDropdown.setDisable(true);
				minuteDropdown.setDisable(true);
				timer(true);
			}
			else{
				if ((Integer.parseInt(start.substring(0,2))+Integer.parseInt(hour))*60 + (Integer.parseInt(start.substring(3,5))+Integer.parseInt(minute))> 1440) {
					String activity = nameField.getText();
					Dp.addDataPoint(activity, format.format(date), start, (23 - Integer.parseInt(start.substring(0, 2))) * 60 + (60 - Integer.parseInt(start.substring(3, 5))));
					DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
					Date newDate = date;
					Calendar cal = Calendar.getInstance();
					cal.setTime(newDate);
					cal.add(Calendar.DATE, 1);
					newDate = cal.getTime();
					Dp.addDataPoint(activity, format.format(newDate), "00:00",
							Integer.parseInt(hour) *60 + Integer.parseInt(minute) - ((23 - Integer.parseInt(start.substring(0, 2))) * 60 + (60 - Integer.parseInt(start.substring(3, 5)))));
				} else {
					Dp.addDataPoint(nameField.getText(), format.format(date), start, Integer.parseInt(hour) *60 + Integer.parseInt(minute));
				}
				if (!goalDropdown.getValue().equals("No goal")) {
					float duration = Float.parseFloat(hour) * 60 + Float.parseFloat(minute);
					updateGoalCompletion(goalDropdownManual.getValue(), duration);
				}
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
	
	void readGoals() {
		try{
			FileReader fr = new FileReader("Goals.txt");
			BufferedReader br = new BufferedReader(fr);
			goalCount = 0;
			for(int i = 0; i < 50; i++){
				String str = br.readLine();
				if(str != null){
					goalCount++;
				}
				else{
					break;
				}
				String [] splitArray = str.split(",");
				for(int j = 0; j < 6; j++){
					goals[i][j] = splitArray[j];
				}
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void writeGoals(){
		try {
			FileWriter fw = new FileWriter("Goals.txt");
			for(int i = 0; i < goalCount; i++){
				for(int j = 0; j < 6; j++){
					if(j == 2) {
						try {
							Integer.parseInt(goals[i][j]);
							fw.write(goals[i][j] + ",");
						} catch (NumberFormatException e) {
							fw.write("Error " + e + ",");
						}
					}
					else{
						fw.write(goals[i][j] + ",");
					}
				}
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void insertGoalNames() {
		goalNames.add("No goal");
		for(int i = 0; i < goalCount; i++){
			goalNames.add(goals[i][0]);
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
				}
			}
			else{
				minute = minute.charAt(0) + "" + ((minute.charAt(1) + 1) - 48) + "";
			}
		}
	}

	boolean checkForInformation(){
		if(!hourTimerDropdown.getSelectionModel().isEmpty() && !minuteDropdown.getSelectionModel().isEmpty() && !nameField.getText().equals("") && !goalDropdown.getSelectionModel().isEmpty()){
			
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
		label.setText("Not enough information was supplied to start the activity timer. \n Please make sure all values are supplied before pressing the \n Start button.");
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
