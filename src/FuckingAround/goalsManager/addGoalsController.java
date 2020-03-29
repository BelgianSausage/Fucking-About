package FuckingAround.goalsManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import FuckingAround.Startup;
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

public class addGoalsController{

	String [][] goals = new String [50][5];
	int goalTimescope = 2;
	int goalCount = 0;

	@FXML
	private TextField goalNameField;

	@FXML
	private TextField goalDescriptionField;

	@FXML
	private CheckBox weeklyCheckbox;

	@FXML
	private CheckBox dailyCheckbox;

	@FXML
	private Button backButton;

	@FXML
	private Button addGoalButton;

	@FXML
	private TextField goalRewardField;

	@FXML
	void addGoal(ActionEvent event) {
		boolean completeEdit = checkForInformation();
		if(completeEdit){
			readGoals();
			goals[goalCount][0] = goalNameField.getText();
			goals[goalCount][1] = goalDescriptionField.getText();
			goals[goalCount][2] = goalRewardField.getText();
			goals[goalCount][3] = "" + goalTimescope;
			goals[goalCount][4] = "0"; //Progress defualt to 0 until changed
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
	void dailyGoal(){
		dailyCheckbox.setSelected(true);
		weeklyCheckbox.setSelected(false);
		goalTimescope = 0;
	}

	@FXML
	void weeklyGoal(){
		dailyCheckbox.setSelected(false);
		weeklyCheckbox.setSelected(true);
		goalTimescope = 1;
	}

	boolean checkForInformation(){
		if(!goalNameField.getText().equals("") && !goalDescriptionField.getText().equals("") && !goalRewardField.getText().equals("") && goalTimescope != 2){
			return true;
		}
		else{
			return false;
		}
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
				for(int j = 0; j < 5; j++){
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
			for(int i = 0; i < goalCount + 1; i++){
				for(int j = 0; j < 5; j++){
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

	void infoPopup(){
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setTitle("Not Enough Information");
		Label label = new Label();
		label.setText("Not enough information was supplied to add the new goal. \n Please make sure all 4 values are supplied before pressing the \n Add Goal button.");
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
