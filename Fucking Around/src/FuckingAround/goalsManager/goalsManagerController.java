package FuckingAround.goalsManager;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import FuckingAround.Startup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

public class goalsManagerController {

	String [][] goals = new String [50][5];
	int goalCount = 0;
	ObservableList <String> goalsObvList = FXCollections.observableArrayList();
	protected static int goalSelected = 0;

	@FXML
	private Button deleteButton;

	@FXML
	private ListView <String> goalList;

	@FXML
	private Label goalDescription;

	@FXML
	private CheckBox weeklyTickBox;

	@FXML
	private CheckBox dailyTickBox;

	@FXML
	private TextField rewardField;

	@FXML
	private ProgressIndicator percentageCompletionDisk;

	@FXML
	private Button editGoalButton;

	@FXML
	private Button newGoalButton;

	@FXML
	private Button backButton;
	
	@FXML
	private Button alphabeticalSortButton;
	
	@FXML
	private Button completionSortButton;
	
	@FXML
	private Button minutesSpentButton;
	
	@FXML
	private TextField timeField;

	@FXML
	void deleteGoal(){
		goalSelected = goalList.getSelectionModel().getSelectedIndex();
		if(goalSelected != -1){
			for(int i = goalSelected; i < goalCount - 1; i++){
				for(int j = 0; j < 5; j++){
					goals[i][j] = goals[i + 1][j];
				}
			}
			goals[goalCount - 1][0] = null;
			goals[goalCount - 1][1] = null;
			goals[goalCount - 1][2] = null;
			goals[goalCount - 1][3] = null;
			goals[goalCount - 1][4] = null;
			for(int i = 0; i < goalCount; i++){
				goalsObvList.remove(0);
			}
			writeGoals();
			readGoals();
			for(int i = 0; i < goalCount; i++){
				goalsObvList.add(goals[i][0]);
			}
		}
	}

	@FXML
	void goToEditGoal(ActionEvent event) {
		Startup.getFuckingAround().goToScene("goalsManager/editGoals.fxml");
	}

	@FXML
	void goToHomepage(ActionEvent event) {
		Startup.getFuckingAround().goToScene("homepage/Homepage.fxml");
	}

	@FXML
	void goToNewGoal(ActionEvent event) {
		Startup.getFuckingAround().goToScene("goalsManager/addGoals.fxml");
	}

	@FXML
	void initialize(){
		readGoals();
		for(int i = 0; i < goalCount; i++){
			goalsObvList.add(goals[i][0]);
		}
		goalSelected = 0;
		updateGoalInformation(0);
	}

	@FXML
	void displaySelectedGoal(){
		goalSelected = goalList.getSelectionModel().getSelectedIndex();
		updateGoalInformation(goalSelected);
	}

	void updateGoalInformation(int index) {
		if(goalCount > 0 && index <= goalCount-1 && index != -1){
			goalList.setItems(goalsObvList);
			goalDescription.setText(goals[index][1]);
			rewardField.setText(goals[index][2]);
			if(goals[index][3].equals(0+"")){
				dailyTickBox.setSelected(true);
				weeklyTickBox.setSelected(false);
			}
			else{
				dailyTickBox.setSelected(false);
				weeklyTickBox.setSelected(true);
			}
			percentageCompletionDisk.setProgress(Double.parseDouble(goals[index][4]));
		}
	}

	@FXML
	void alphabeticalSort() {
		for(int i = 0; i < goalCount; i++) {
			for(int j = 0; j < goalCount; j++) {
                if (goals[j][0].toUpperCase().compareTo(goals[i][0].toUpperCase())>0){
                    swapper(i,j);
                }
            }
        }
		showSort();
	}
	
	@FXML
	void completionSort() {
		for (int i = 0; i < goalCount; i++){
            for (int j = 0; j < goalCount; j++){
                if (Double.parseDouble(goals[i][4]) > Double.parseDouble(goals[j][4])){
                    swapper(i,j);
                }
            }
        }
		showSort();
	}
	
	@FXML
	void minutesSpentSort() {
		for (int i = 0; i < goalCount; i++){
            for (int j = 0; j < goalCount; j++){
                if (1==1){  //THIS MUST BE SWAPPED WITH MINUTES SPENT CONDITION
                    swapper(i,j);
                }
            }
        }
		showSort();
	}
	
	void showSort() {
		goalsObvList.clear();
		for(int i = 0; i < goalCount; i++){
			goalsObvList.add(goals[i][0]);
		}
		goalSelected = 0;
		updateGoalInformation(goalSelected);
	}
	
	void swapper(int i, int j) {
		String temp0;
		String temp1;
		String temp2;
		String temp3;
		String temp4;
		temp0 = goals[i][0];
		temp1 = goals[i][1];
		temp2 = goals[i][2];
		temp3 = goals[i][3];
		temp4 = goals[i][4];
        goals[i][0] = goals[j][0];
        goals[i][1] = goals[j][1];
        goals[i][2] = goals[j][2];
        goals[i][3] = goals[j][3];
        goals[i][4] = goals[j][4];
        goals[j][0] = temp0;
        goals[j][1] = temp1;
        goals[j][2] = temp2;
        goals[j][3] = temp3;
        goals[j][4] = temp4;
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
			for(int i = 0; i < goalCount - 1; i++){
				for(int j = 0; j < 5; j++){
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
