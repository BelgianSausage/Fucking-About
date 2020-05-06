package FkingAround;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Startup extends Application{
	private static Startup fuckingAround;
	protected static Stage primaryStage;
	private AnchorPane mainLayout;
	protected FXMLLoader loader;

	public void start(Stage primaryStage){ //This method sets the primaryStage Stage
		Startup.primaryStage = primaryStage; //Sets stage
		primaryStage.setTitle("Fucking Around"); //Sets title to name of program
		goToScene("homepage/Homepage.fxml"); //Runs method goToHomepage
	}

	public void goToScene(String sceneLocation){ //Loads the home page
		try {
			loader = new FXMLLoader(); //Creates new FXMLLoader called loader used to load scenes
			loader.setLocation(Startup.class.getResource(sceneLocation)); //Locates the requested scene
			mainLayout = loader.load();
			Scene scene = new Scene(mainLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		fuckingAround = new Startup();
		launch(args);
	}

	public static Startup getFuckingAround() { //Accessor for fuckingAround
		return fuckingAround;
	}

	public static void setFuckingAround(Startup fuckingAround) { //Mutator for fuckingAround
		Startup.fuckingAround = fuckingAround;
	}

}
