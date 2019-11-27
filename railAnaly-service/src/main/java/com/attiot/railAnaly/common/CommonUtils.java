package com.attiot.railAnaly.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by bfzhang on 2018/9/14.
 */
@Slf4j
public class CommonUtils {
    //提交频率,记录上次提交时间
    private static Map<String,String> timerMap = new HashMap();
    private static Map<String,String> refnumMap = new HashMap();
    private static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger logger = Logger.getLogger(CommonUtils.class);

    //取当前值班日期
    public static String getCurrentShiftdate() {
        //取今天的排班，排班时间在08:00-
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currendate="";
        Calendar current = Calendar.getInstance();
        if(timeFormat.format(Calendar.getInstance().getTime()).compareTo("0800")<0) {
            current.add(Calendar.DAY_OF_MONTH,-1);            }
        currendate = dateFormat.format(current.getTime());
        return currendate;
    }

    public static  long secDiff(String startTime,String endTime,String format) {
//        long result = 0l;
        //按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000*24*60*60;//一天的毫秒数
        long nh = 1000*60*60;//一小时的毫秒数
        long nm = 1000*60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数long diff;try {
        //获得两个时间的毫秒时间差异
        Long diff=0L;
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            log.error("系统内部异常", e);
        }
        long day = diff/nd;//计算差多少天
        long hour = diff%nd/nh;//计算差多少小时
        long min = diff%nd%nh/nm;//计算差多少分钟
        long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
        return sec+min*60+hour*60*60+day*24*60*60;
    }

    public static boolean isDanger(String useraction) {
        String lastruntime=timerMap.get(useraction);
        SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currenttime = datetimeFormat.format(Calendar.getInstance().getTime());
        if(null == lastruntime || lastruntime.length()<1) {
            timerMap.put(useraction,currenttime);
            return false;
        }
        //计算访问间隔,不超过10s
        long diff = secDiff(lastruntime,currenttime,"yyyy-MM-dd HH:mm:ss");
        if(diff<=10) {
            return true;
        }
        return false;
    }

    /***
     * 取当前refnum的状态:0:不存在，可继续提交操作；1:进行中,请等待; 2:已完成；
     * @param refnum
     * @return
     */
    public static Integer getCurrentRefnumState(String refnum) {
        String refValue = null != refnumMap.get(refnum)?refnumMap.get(refnum):null;
        if(null == refValue) {
            return 0;
        }
        return Integer.valueOf(refValue.substring(0,1));
    }
    public static void setRefnum(String refnum,int value) {
        SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String currenttime = datetimeFormat.format(Calendar.getInstance().getTime());
        refnumMap.put(refnum,value+currenttime);

        //删除掉refnumMap中超过12小时的refnum
        Iterator<Map.Entry<String, String>> it = refnumMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, String> entry= it.next();
            String entryValue = entry.getValue();
            long diff = secDiff(entryValue.substring(1),currenttime,"yyyyMMddHHmmss");
            if(diff>=43200) {//超过12小时
                it.remove();
            }
        }
//        logger.info("--===--"+refnumMap);
    }

//    public static void main(String args[]) {
//        String starttime = "2018-10-11 12:23:23";
//        String endtime = "2018-10-11 15:23:44";
//        long diff = secDiff(starttime,endtime,"yyyy-MM-dd HH:mm:ss");
//        System.out.println(diff>10);
//    }


}
