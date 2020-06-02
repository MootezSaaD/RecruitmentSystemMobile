package tn.medtech.recruitmentsystemapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateParser {
    public DateParser() {
    }

    ;

    public static String parseDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        int year;
        int month;
        int day;
        try {
            cal.setTime(sdf.parse(date));
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1;
            day = cal.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            return "inf"; // hhhh for the meme
        }

        return year + "/" + month + "/" + day;
    }
}
