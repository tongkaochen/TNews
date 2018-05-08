package com.tifone.tnews.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tongkao.chen on 2018/5/8.
 */

public class TimeUtils {
    public static String getCurrentTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
    public static String getTimeStampAgo(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String result = format.format(timeStamp * 1000L);
        Date date = null;
        try {
            date = format.parse(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeAgo(date);
    }
    private static String timeAgo(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
        if (date != null) {
            long agoTimeMillis = new Date(System.currentTimeMillis()).getTime() - date.getTime();
            long agoTimeMin = agoTimeMillis / 1000 / 60;
            if (agoTimeMin < 1) {
                return "刚刚";
            } else if (agoTimeMin <=60) {
                return agoTimeMin + "分钟前";
            } else if (agoTimeMin <= 60 * 24) {
                return (agoTimeMin / 60) + "小时前";
            } else if (agoTimeMin <= 60 * 24 * 2) {
                return (agoTimeMin / 60 / 24) + "天前";
            } else {
                return format.format(date);
            }
        }
        return format.format(new Date(0));
    }
}
