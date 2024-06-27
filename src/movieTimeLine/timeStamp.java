package movieTimeLine;

import java.util.Objects;

public class timeStamp {
    // Accepts total time duration of the movie clip in seconds
    // Converts the total duration in the format hh:mm:ss

    int totalDurationSec;
    int hrs, min, sec;

    //constructor: method, always executed when u create instance of class
    //has the same name as the class

    public timeStamp(int totalDurationSec) {
        this.totalDurationSec = totalDurationSec;
        convertSeconds();
    }

    private void convertSeconds() {
        hrs = totalDurationSec/3600;
        sec = totalDurationSec%3600; //remaining seconds after converting to hrs
        min = sec/60;
        sec %= 60;
    }

    // toString is inbuilt method in all classes
    // whenever println is called, toString method of that object is accessed
    // We override the toString func accd to our req. ie to print in hh:mm:ss format

    @Override
    public String toString() {
        return String.format("%s:%s:%s",
                utilities.to2DigitInt(hrs), utilities.to2DigitInt(min), utilities.to2DigitInt(sec));
    }

    public static int getTime(int totalSec, String time) {
        timeStamp ins = new timeStamp(totalSec);

        if (Objects.equals(time, "hrs")) {
            return ins.hrs;
        }
        else if (Objects.equals(time, "min")) {
            return ins.min;
        }
        else {
            return ins.sec;
        }
    }
    public static void main (String[] args) {
        timeStamp timeStamp1 = new timeStamp(10005);
        System.out.println(timeStamp1);
    }
}