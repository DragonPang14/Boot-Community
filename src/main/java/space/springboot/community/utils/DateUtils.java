package space.springboot.community.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDate(){
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }
}
