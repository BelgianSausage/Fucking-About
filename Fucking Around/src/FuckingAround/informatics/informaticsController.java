package FuckingAround.informatics;

import FuckingAround.Startup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.TextField;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class informaticsController {

    String [][] activities = new String [50][4];
    int activityCount = 0;

    @FXML
    private BarChart<String, Number> todayBarChart;

    @FXML
    private TextField minutesFuckedAround;

    @FXML
    private TextField minutesNotFuckedAround;

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
    void initialize() throws ParseException {
        getData();
        today();
        week();
        allTime();
    }

    @FXML
    void today() {
        int count = 0;
        boolean manualSleep = false;
        XYChart.Series series = new XYChart.Series();
        todayBarChart.setLegendVisible(false);
        for (int i = 0; i < activityCount; i++) {
            if (activities[i][1].equals(getDate(0))) {
                int diff = getDiff(activities[i][2]);
                series.getData().add(new XYChart.Data(activities[i][0], diff));
                count += diff;
            }
        }
        if (count > 1440) {
            minutesFuckedAround.setText(Integer.toString(0));
            minutesNotFuckedAround.setText(Integer.toString(1440));
            series.getData().add(new XYChart.Data("Fucking around", 0));
        } else {
            minutesFuckedAround.setText(Integer.toString(1440 - count));
            minutesNotFuckedAround.setText(Integer.toString(count));
            series.getData().add(new XYChart.Data("Fucking around", 1440 - count));
        }

        todayBarChart.getData().addAll(series);
    }

    @FXML
    void week() {
        LocalDate localDate = LocalDate.of(Integer.parseInt(getDate(0).substring(0,4)), Integer.parseInt(getDate(0).substring(5,7)), Integer.parseInt(getDate(0).substring(8,10)));
        DayOfWeek dayOfWeek = DayOfWeek.from(localDate);
        XYChart.Series series = new XYChart.Series();
        weeklyBarChart.setLegendVisible(false);
        int[] dayCount = new int[dayOfWeek.getValue()];
        for (int j = dayOfWeek.getValue(); j > 0; j--) {
            for (int i = 0; i < activityCount; i++) {
                if (activities[i][1].equals(getDate(dayOfWeek.getValue()-j))) {
                    int diff = getDiff(activities[i][2]);
                    if (dayCount[j-1] + diff > 1440) {
                        dayCount[j-1] = 1440;
                    } else {
                        dayCount[j-1] += diff;
                    }
                }
            }
        }

        int min = dayCount[0];
        for (int i = 1; i < dayCount.length; i++) {
            if (dayCount[i] < min) {
                min = dayCount[i];
            }
        }

        int max = dayCount[0];
        for (int i = 1; i < dayCount.length; i++) {
            if (dayCount[i] > max) {
                max = dayCount[i];
            }
        }

        int sum = 0;
        for (int i = 0; i < dayCount.length; i++) {
            sum += 1440 - dayCount[i];
        }

        series.getData().add(new XYChart.Data("Monday", 1440 - dayCount[0]));

        if (dayOfWeek.getValue() > 1) {
            series.getData().add(new XYChart.Data("Tuesday", 1440 - dayCount[1]));
        }

        if (dayOfWeek.getValue() > 2) {
            series.getData().add(new XYChart.Data("Wednesday", 1440 - dayCount[2]));
        }

        if (dayOfWeek.getValue() > 3) {
            series.getData().add(new XYChart.Data("Thursday", 1440 - dayCount[3]));
        }

        if (dayOfWeek.getValue() > 4) {
            series.getData().add(new XYChart.Data("Friday", 1440 - dayCount[4]));
        }

        if (dayOfWeek.getValue() > 5) {
            series.getData().add(new XYChart.Data("Saturday", 1440 - dayCount[5]));
        }

        if (dayOfWeek.getValue() > 6) {
            series.getData().add(new XYChart.Data("Sunday", 1440 - dayCount[6]));
        }

        if (max > 1440) {
            leastTimeFuckingAround.setText(Integer.toString(0));
        } else {
            leastTimeFuckingAround.setText(Integer.toString(1440 - max));
        }

        mostTimeFuckingAround.setText(Integer.toString(1440 - min));
        fuckingAroundRange.setText(Integer.toString(Integer.parseInt(mostTimeFuckingAround.getText()) - Integer.parseInt(leastTimeFuckingAround.getText())));
        fuckingAroundMean.setText(Integer.toString(sum / dayCount.length));
        weeklyBarChart.getData().addAll(series);

        try {
            FileReader fr = new FileReader("Productive.txt");
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            String lastAccessed = br.readLine();
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            Date todayDate = new Date();
            Date date = format.parse(lastAccessed);
            long diffInMillies = Math.abs(todayDate.getTime() - date.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            FileWriter fw = new FileWriter("Productive.txt");
            if (dayOfWeek.getValue()<diff) {
                fw.append("0,0,0,0,0,0,0");
            } else {
                fw.append(str);
            }
            fw.append("\n" + getDate(0));
            fw.close();
            String[] spltStr = str.split(",");
            int productive = 0;
            for (int i=0; i<spltStr.length; i++) {
                if (spltStr[i].equals("1")) {
                    productive += 1;
                }
            }
            br.close();
            fr.close();
            productiveDays.setText(Integer.toString(productive));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void allTime() throws ParseException {
        //DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        //Date todayDate = new Date();
        //Date oldestDate = format.parse(activities[0][1]);
        //long diffInMillies = Math.abs(todayDate.getTime() - oldestDate.getTime());
        //long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        int diff = 30;
        XYChart.Series series = new XYChart.Series();
        xyChartOverTime.setLegendVisible(false);
        int[] dayCount = new int[(int) diff+1];
        for (int j = (int) diff+1; j > 0; j--) {
            for (int i = 0; i < activityCount; i++) {
                if (activities[i][1].equals(getDate(j-1))) {
                    int difference = getDiff(activities[i][2]);
                    if (dayCount[j-1] + difference > 1440) {
                        dayCount[j-1] = 1440;
                    } else {
                        dayCount[j-1] += difference;
                    }
                }
            }
        }

        int min = dayCount[0];
        for(int i = 1; i < dayCount.length;i++) {
            if(dayCount[i] < min) {
                min = dayCount[i];
            }
        }

        int max = dayCount[0];
        for(int i = 1; i < dayCount.length;i++) {
            if(dayCount[i] > max) {
                max = dayCount[i];
            }
        }

        for (int i= (int) diff; i>-1; i--) {
            series.getData().add(new XYChart.Data(Integer.toString(i), 1440 - dayCount[i]));
        }

        if (max > 1440) {
            leastTime.setText(Integer.toString(0));
        } else {
            leastTime.setText(Integer.toString(1440-max));
        }

        mostTime.setText(Integer.toString(1440-min));
        leastTime.setText(Integer.toString(1440-max));
        xyChartOverTime.getData().addAll(series);
    }

    void getData() {
        try {
            FileReader fr = new FileReader("sortedData.txt");
            BufferedReader br = new BufferedReader(fr);
            activityCount = 0;
            for(int i = 0; i < 50; i++){
                String str = br.readLine();
                if(str != null){
                    activityCount++;
                }
                else{
                    break;
                }
                str.substring(0, str.length() - 1);
                String [] splitArray = str.split(" ");
                for(int j = 0; j < 3; j++){
                    activities[i][j] = splitArray[j];
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

    int getDiff(String time2) {
        //int h1 = Integer.parseInt(time1.substring(0,2));
        //int m1 = Integer.parseInt(time1.substring(3,5));
        //int h2 = Integer.parseInt(time2.substring(0,2));
        //int m2 = Integer.parseInt(time2.substring(3,5));
        return Integer.parseInt(time2);
        /*if (h2 > h1) {
            return (h2 - h1) * 60 + m2 - m1;
        } else if (h1 > h2) {
            return (24 + h2 - h1) * 60 + m2 - m1;
        } else {
            if (m2 > m1) {
                return (m2 - m1);
            } else if (m1 > m2) {
                return (24 * 60 + m2 - m1);
            } else {
                return 0;
            }
        }*/
    }

    String getDate(int days) {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date date = new Date(System.currentTimeMillis() - (days * DAY_IN_MS));
        return format.format(date);
    }


    @FXML
    void productive() {
        button("1");
    }

    @FXML
    void notProductive() {
        button("0");
    }

    @FXML
    void button(String buttonValue) {
        LocalDate localDate = LocalDate.of(Integer.parseInt(getDate(0).substring(0,4)), Integer.parseInt(getDate(0).substring(5,7)), Integer.parseInt(getDate(0).substring(8,10)));
        DayOfWeek dayOfWeek = DayOfWeek.from(localDate);
        try {
            FileReader fr = new FileReader("Productive.txt");
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            String[] spltStr = str.split(",");
            spltStr[dayOfWeek.getValue()-1] = buttonValue;
            FileWriter fw = new FileWriter("Productive.txt");
            fw.append(spltStr[0] + "," + spltStr[1] + "," + spltStr[2] + "," + spltStr[3] + "," + spltStr[4] + "," + spltStr[5] + "," + spltStr[6]);
            fw.append("\n" + getDate(0));
            fw.close();
            int productive = 0;
            for (int i=0; i<spltStr.length; i++) {
                if (spltStr[i].equals("1")) {
                    productive += 1;
                }
            }
            br.close();
            fr.close();
            productiveDays.setText(Integer.toString(productive));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
