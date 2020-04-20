package FuckingAround.setData;

public class point implements Comparable<point> {

    private String activity;
    private int Year;
    private int Month;
    private int Day;
    private int hour;
    private int Minute;
    private int Duration;
    private boolean duplicate;

    public point(String activity, int Year, int Month, int Day, int hour, int Minute, int Duration){
        this.activity = activity;
        this.Year = Year;
        this.Month = Month;
        this.Day = Day;
        this.hour = hour;
        this.Minute = Minute;
        this.Duration = Duration;
        duplicate = false;
    }
    public String getActivity(){return activity;}
    public int getYear(){return Year;}
    public int getMonth(){return Month;}
    public int getDay(){return Day;}
    public int getHour(){return hour;}
    public int getMinute(){return Minute;}
    public int getDuration(){return Duration;}
    public boolean getDuplicate(){return duplicate;}

    void setDuplicate() {
        duplicate = true;
    }

    void setDuration(int dur) {
        Duration += dur;
    }

    @Override
    public int compareTo(point point) {
        if(getYear() > point.getYear()){
            return 1;
        }
        else if (getYear() == point.getYear()){
            if(getMonth() > point.getMonth()){
                return 1;
            }
            else if(getMonth() == point.getMonth()){
                if(getDay() > point.getDay()){
                    return 1;
                }
                else if(getDay() == point.getDay()){
                    if (getHour() > point.getHour()){
                        return 1;
                    }
                    else if(getHour() == point.getHour()){
                        if(getMinute() > point.getMinute()){
                            return 1;
                        }
                        else{
                            return -1;
                        }
                    }
                    else{
                        return -1;
                    }
                }
                else{
                    return -1;
                }
            }
            else{
                return -1;
            }
        }
        else{
            return -1;
        }
    }
}
