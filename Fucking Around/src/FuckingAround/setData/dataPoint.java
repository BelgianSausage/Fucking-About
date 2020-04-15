package FuckingAround.setData;

import java.io.*;

public class dataPoint {
    int manualDataCount;

    public void manualDurationCalc(String activity, String date, String start, String end){
        String[] begin = start.split(":");
        String[] End = end.split(":");
        int[] time = new int[2];
        for(int timeCalc = 0; timeCalc < 2; timeCalc++){
            if(timeCalc == 0) {
                time[timeCalc] = Integer.parseInt(End[timeCalc]) - Integer.parseInt(begin[timeCalc]);
            }
            else{
                time[timeCalc] = Integer.parseInt(End[timeCalc]) - Integer.parseInt(begin[timeCalc]);
            }
        }
        if(time[0] < 0){
            time[0] = time[0] * -1;
        }
        if(time[1] < 0){
            time[1] = time[1] * -1;
        }
        String hour;
        String minute;
        if(String.valueOf(time[0]).length() == 1){
            hour = "0" + String.valueOf(time[0]);
        }
        else{
            hour = String.valueOf(time[0]);
        }
        if(String.valueOf(time[1]).length() == 1){
            minute = "0" + String.valueOf(time[1]);
        }
        else{
            minute = String.valueOf(time[1]);
        }
        addDataPoint(activity, date, start, hour + ":" + minute);
    }

    public void addDataPoint(String activity, String date, String start, String end){
        try {
            FileWriter fw = new FileWriter("RawData.txt", true);
            String convActivity = new String(convertActivity(activity));
            fw.append("\n"+ convActivity + " " + date + " " + start + " " + end + ",");
            fw.close();
            getRawDataLength();
            setArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char[] convertActivity(String activity){
        char[] toConvert = activity.toCharArray();
        char[] converted = new char[toConvert.length];
        for(int lenOfChar = 0; lenOfChar < toConvert.length; lenOfChar++){
            if(toConvert[lenOfChar] != ' '){
                converted[lenOfChar] = toConvert[lenOfChar];
            }
            else {
                converted[lenOfChar] = '-';
            }
        }
        return converted;
    }

    public int getRawDataLength(){
        try {
            FileReader fr = new FileReader("RawData.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            manualDataCount = 0;
            while (true){
                line = br.readLine();
                if(line != null) {
                    manualDataCount++;
                }
                else{
                    break;
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return manualDataCount;
    }

    public void setArray(){
        point[] data = new point[manualDataCount];
        try {
            FileReader readToSort = new FileReader("RawData.txt");
            BufferedReader bReadToSort = new BufferedReader(readToSort);
            int count = 0;
            while (true){
                String historyRead = bReadToSort.readLine();
                if(historyRead != null && count != 0) {
                    String[] initialSplit = historyRead.split(" ");
                    String[] dateSplit = initialSplit[1].split("/");
                    String[] timeSplit = initialSplit[2].split(":");
                    point current = new point(initialSplit[0], Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]), Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]), initialSplit[3]);
                    data[count-1] = current;
                    count++;
                }
                else if(count == 0){
                    count++;
                }
                else{
                    break;
                }
            }
            readToSort.close();
            bReadToSort.close();
            sort(data);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sort(point[] data) {
        for(int z = 0; z < manualDataCount; z++) {
            for (int i = 0; i < manualDataCount; i++) {
                if (i < (manualDataCount-1) && data[i].compareTo(data[i + 1]) > 0) {
                    point holder = data[i + 1];
                    data[i + 1] = data[i];
                    data[i] = holder;
                }
            }
        }
        write(data);
    }

    public void write(point[] data){
        try {
            FileWriter fw = new FileWriter("sortedData.txt");
            for(int j = 0; j < manualDataCount; j++) {
                fw.write("\n" + data[j].getActivity() + " " + data[j].getYear() + "/" + data[j].getMonth() + "/" + data[j].getDay() + " " + data[j].getHour() + ":" + data[j].getMinute() + " " +  data[j].getDuration());
            }
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
