package com.attiot.railAnaly.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SSS on 2018/5/11.
 */
public class DateUtil {

    public static String parseTime(Date curDate){
        String dateStr = "";
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(null!=curDate){
            dateStr = time.format(curDate);
        }
        return dateStr;
    }

    public static String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        return sdf.format(date);
    }
}
