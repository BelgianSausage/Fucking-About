package FuckingAround.informatics;

import FuckingAround.Startup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class informaticsController {

    @FXML
    private BarChart<?, ?> todayBarChart;

    @FXML
    private TextField minutesFuckedAround;

    @FXML
    private TextField minutesNotFuckedAround;

    @FXML
    private RadioButton yesRadio;

    @FXML
    private ToggleGroup productivity;

    @FXML
    private RadioButton noRadio;

    @FXML
    private TextField difference;

    @FXML
    private Button backButton1;

    @FXML
    private BarChart<?, ?> weeklyBarChart;

    @FXML
    private TextField mostTimeFuckingAround;

    @FXML
    private TextField leastTimeFuckingAround;

    @FXML
    private TextField fuckingAroundRange;

    @FXML
    private TextField fuckingAroundMean;

    @FXML
    private TextField productiveDays;

    @FXML
    private Button backButton2;

    @FXML
    private Button backButton11;

    @FXML
    private LineChart<?, ?> xyChartOverTime;

    @FXML
    private TextField mostTime;

    @FXML
    private TextField leastTime;

    @FXML
    void goToHomepage(ActionEvent event) {
    	Startup.getFuckingAround().goToScene("homepage/Homepage.fxml");
    }

    @FXML
    void notProductive(ActionEvent event) {

    }

    @FXML
    void productive(ActionEvent event) {

    }

}
