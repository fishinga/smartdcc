package com.attiot.railAnaly.common;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2018/12/25.
 */
public class Convert {

    public static String changeDateToString(Date date, String format) {
        if(null == date ) return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }


    public static String getUUId() {
        String id = UUID.randomUUID().toString();
        return id.replaceAll("-","");
    }




    public static String getStringValue(Object obj) {
        if(null != obj) return ""+obj;
        return null;
    }
    public static Integer getIntegerValue(Object obj) {
        if(null != obj && (obj+"").length()>0) return Integer.valueOf(""+obj);
        return null;
    }

    public static Float getFloatValue(Object obj) {
        if(null != obj) return Float.valueOf(""+obj);
        return null;
    }


}
