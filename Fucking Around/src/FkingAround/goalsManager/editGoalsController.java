package FkingAround.goalsManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import FkingAround.Startup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class editGoalsController extends goalsManagerController{

	int goalTimescope = 2; //Whether the goal is to be daily or weekly
	int goalCount = 0;
	String [][] goals = new String [50][6];

	@FXML
	private TextField goalNameField;

	@FXML
	private TextField goalDescriptionField;

	@FXML
	private CheckBox weeklyCheckbox;

	@FXML
	private CheckBox dailyCheckbox;

	@FXML
	private TextField goalRewardField;

	@FXML
	private Button backButton;

	@FXML
	private Button editGoalButton;

	@FXML
	private TextField goalMinutesField;

	@FXML
	private TextField goalDaysField;

	@FXML
	void dailyGoal(ActionEvent event) {
		dailyCheckbox.setSelected(true);
		weeklyCheckbox.setSelected(false);
		goalDaysField.setEditable(false);
		goalMinutesField.setEditable(true);
		goalTimescope = 0;
	}

	@FXML
	void editGoal(ActionEvent event) {
		boolean completeEdit = checkForInformation();
		if(completeEdit){
			int change = goalsManagerController.goalSelected;
			readGoals();
			goals[change][0] = goalNameField.getText();
			goals[change][1] = goalDescriptionField.getText();
			goals[change][2] = goalRewardField.getText();
			goals[change][3] = "" + goalTimescope;
			if (goalTimescope == 0) {
				goals[change][5] = goalMinutesField.getText();
			} else if (goalTimescope == 1) {
				goals[change][5] = goalDaysField.getText();
			}
			//Add progress if it must be done manually
			writeGoals();
			Startup.getFuckingAround().goToScene("goalsManager/goalsManager.fxml");
		}
		else{
			infoPopup();
		}
	}

	@FXML
	void goToHomepage(ActionEvent event) {
		Startup.getFuckingAround().goToScene("goalsManager/goalsManager.fxml");
	}

	@FXML
	void weeklyGoal(ActionEvent event) {
		dailyCheckbox.setSelected(false);
		weeklyCheckbox.setSelected(true);
		goalDaysField.setEditable(true);
		goalMinutesField.setEditable(false);
		goalTimescope = 1;
	}

	@FXML
	void initialize(){
		readGoals();
		goalNameField.setText(goals[goalSelected][0]);
		goalDescriptionField.setText(goals[goalSelected][1]);
		goalRewardField.setText(goals[goalSelected][2]);
		if(goals[goalSelected][3].equals("0")) {
			weeklyCheckbox.setSelected(true);
			dailyCheckbox.setSelected(false);
			goalTimescope = 1;
		}
		else {
			dailyCheckbox.setSelected(true);
			weeklyCheckbox.setSelected(false);
			goalTimescope = 0;
		}
		//Progression? 
	}

	boolean checkForInformation(){
		if(!goalNameField.getText().equals("") && !goalDescriptionField.getText().equals("") && !goalRewardField.getText().equals("") && goalTimescope != 2){
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
		label.setText("Not enough information was supplied to edit the chosen goal. \n Please make sure all 4 values are supplied before pressing the \n Edit Goal button.");
		Button closeButton = new Button("Understood!");
		closeButton.setOnAction(z -> popup.close());
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout, 350, 200);
		popup.setScene(scene);
		popup.showAndWait();
	}

	void readGoals(){
		try {
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
					fw.write(goals[i][j] + ",");
				}
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
