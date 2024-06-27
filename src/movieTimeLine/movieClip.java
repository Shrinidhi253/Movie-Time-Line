package movieTimeLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class movieClip {
    // Contains all the details of 1 movie clip - title, time stamp in hh:mm:ss, etc
    String title;
    int totalDurationSec; //Duration in seconds of 1 m.c
    int offset; // start time in seconds of the clip

    // if there are 2 clips (00:00 - 13:00) and (00: 14:00), the second clip
    // should be from (13:00 - 27:00) in the full movie - this is offset

    int hrs, min, sec;
    timeStamp startTimeStamp, endTimeStamp; // final time stamps of the m.c
    timeStampSpecifier specifier;

    public movieClip(String title, int hrs, int min, int sec, timeStampSpecifier specifier) {
        this.title = title;
        this.hrs = hrs;
        this.min = min;
        this.sec = sec;
        this.specifier = specifier;
        this.totalDurationSec = hrs * 3600 + min * 60 + sec;
    }

    // override the previous constructor by having a default specifier
    public movieClip(String title, int hrs, int min, int sec) {
        this(title, hrs, min, sec, timeStampSpecifier.DURATION);
    }

    public movieClip setTimeStampSpecifier(timeStampSpecifier specifier) {
        this.specifier = specifier;
        return this;
    }

    public movieClip setTotalDurationSec(int totalDurationSec) {
        this.totalDurationSec = totalDurationSec;
        this.hrs = timeStamp.getTime(totalDurationSec, "hrs");
        this.min = timeStamp.getTime(totalDurationSec, "min");
        this.sec = timeStamp.getTime(totalDurationSec, "sec");
        return this;
    }

    public movieClip setOffset(int offset) {
        this.offset = offset;
        startTimeStamp = new timeStamp(offset);
        endTimeStamp = new timeStamp((this.offset + this.totalDurationSec));
        return this;
    }

    public int getOffset() {
        return this.offset;
    }

    public timeStamp getDurationTimeStamp() {
        return new timeStamp(totalDurationSec);
    }

    public int getTotalDurationSec() {
        return totalDurationSec;
    }

    public timeStamp getStartTimeStamp() {
        if (startTimeStamp == null) {
            startTimeStamp = new timeStamp(offset);
        }
        return startTimeStamp;
    }

    public timeStamp getEndTimeStamp() {
        if (endTimeStamp == null) {
            endTimeStamp = new timeStamp((this.offset + this.totalDurationSec));
        }
        return endTimeStamp;
    }

    @Override
    public String toString() {
        String str;
        switch (specifier) {
            case START_TIME -> str = "%s %s".formatted(getStartTimeStamp(), title);
            case END_TIME -> str = "%s %s".formatted(getEndTimeStamp(), title);
            default -> str = "%s %s".formatted(getDurationTimeStamp(), title);
        }
        return str;
    }

    public static movieClip parseString(String str) {
        // str is string information abt the m.c -> can be "    hh:mm:ss          title"
        // the white spaces needs to be removed to get "hh:mm:ss" and "title" seperately

        str = str.strip();

        // get ind of 1st white space b/w timestamp and title
        int index = str.indexOf(' ');

        // timestamp = str[0:index], title = str[index:end]->can have leading white spaces
        String timeStampStr = str.substring(0, index);
        String title = (str.substring(index)).strip();

        //Convert the string timestamp "hh:mm:ss" into an array ["hh", "mm", "ss"]
        //Convert the string "hh", "mm", "ss" in the arr to int hh, mm, ss
        String[] timeStampArr = timeStampStr.split(":");
        int hrs = Integer.parseInt(timeStampArr[0]);
        int min = Integer.parseInt(timeStampArr[1]);
        int sec = Integer.parseInt(timeStampArr[2]);

        //use the parsed title and times to create a movie clip
        return new movieClip(title, hrs, min, sec);
    }
}