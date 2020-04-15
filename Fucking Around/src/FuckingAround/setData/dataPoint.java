package FuckingAround.setData;

import java.io.*;

public class dataPoint {

    int manualDataCount;

    public void addDataPoint(String activity, String date, String start, String end){
        try {
            FileWriter fw = new FileWriter("RawData.txt", true);
            fw.append("\n"+ activity + " " + date + " " + start + " " + end + ",");
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDataPoint(){
        sortData();
    }

    public void sortData(){
        int rawDataLength = getRawDataLength();
        String[][] sortData = new String[manualDataCount-1][4];
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
            br.close();
            fr.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return manualDataCount;
    }
}
