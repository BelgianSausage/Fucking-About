package FuckingAround.homepage;

import FuckingAround.Startup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomepageController {

    @FXML
    private Button helpButton; //Button pressed for help on the general program

    @FXML
    private Button editActivityButton; //Button that runs goToSetData()

    @FXML
    private Button personalInformaticsButton; //Button that runs goToInformatics()

    @FXML
    private Button goalsManagerButton; //Button that runs goToGoalsManager

    @FXML
    void goToGoalsManager(ActionEvent event) {
    	Startup.getFuckingAround().goToScene("goalsManager/goalsManager.fxml");
    }

    @FXML
    void goToInformatics(ActionEvent event) {
    	Startup.getFuckingAround().goToScene("informatics/informatics.fxml");
    }

    @FXML
    void goToSetData(ActionEvent event) {
    	Startup.getFuckingAround().goToScene("setData/setData.fxml");
    }

    @FXML
    void goToMainHelpPage(ActionEvent event) {
    	Stage popup = new Stage();
    	popup.initModality(Modality.APPLICATION_MODAL);
    	popup.setTitle("Fucking Around Help");
    	Label label = new Label();
    	label.setText("Welcome to Fucking Around! This program aims to help you \n manage your time and keep track of how much you \n procrastinate day to day. Procrastination is important to stay \n energised, however too much can lead to a lack of productivity, \n which is to be avoided!");
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
