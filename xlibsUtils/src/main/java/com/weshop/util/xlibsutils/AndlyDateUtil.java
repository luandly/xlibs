package com.weshop.util.xlibsutils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.GregorianCalendar;

/**
 * 日期操作工具类.
 */
public class AndlyDateUtil {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param str
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static Date str2Date(String str) {
        return str2Date(str, null);
    }

    /**
     * @param str
     * @param format
     * @return 根据传入的时间格式，格式化字符串
     */
    public static Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
            date = sdf.parse(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }

    public static Calendar str2Calendar(String str) {
        return str2Calendar(str, null);

    }

    public static Calendar str2Calendar(String str, String format) {

        Date date = str2Date(str, format);
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;

    }

    public static String getTime(Date date) {
        String todySDF = "HH:mm";
        String yesterDaySDF = "昨天 HH:mm";
        String yester2DaySDF = "前天 HH:mm";
        String otherSDF = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sfd = null;
        String time = "";
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(now);
        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        if (dateCalendar.after(targetCalendar)) {
            sfd = new SimpleDateFormat(todySDF, Locale.CHINA);
            time = sfd.format(date);
            return time;
        } else {
            targetCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(targetCalendar)) {
                sfd = new SimpleDateFormat(yesterDaySDF, Locale.CHINA);
                time = sfd.format(date);
                return time;
            } else {
                targetCalendar.add(Calendar.DATE, -2);
                if (dateCalendar.after(targetCalendar)) {
                    sfd = new SimpleDateFormat(yester2DaySDF, Locale.CHINA);
                    time = sfd.format(date);
                    return time;
                }
            }
        }
        sfd = new SimpleDateFormat(otherSDF, Locale.CHINA);
        time = sfd.format(date);
        return time;
    }

    public static String date2Str(Calendar c) {// yyyy-MM-dd HH:mm:ss
        return date2Str(c, null);
    }

    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }

    public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
        return date2Str(d, null);
    }

    public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        String s = sdf.format(d);
        return s;
    }

    /**
     *
     * @return 2012-1-6 21:23:30
     */
    public static String getCurDateStr() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
                + c.get(Calendar.DAY_OF_MONTH) + " "
                + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)
                + ":" + c.get(Calendar.SECOND);
    }

    /**
     * @param format
     * @return 获得当前日期的字符串格式
     */
    public static String getCurDateStr(String format) {
        Calendar c = Calendar.getInstance();
        return date2Str(c, format);
    }

    /**
     *
     * @param time
     * @return 格式到秒
     */
    public static String getMillon(long time) {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA).format(time);
    }

    /**
     *
     * @param time
     * @return 格式到天
     */
    public static String getDay(long time) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(time);
    }

    /**
     * @param time
     * @return 格式到毫秒
     */
    public static String getSMillon(long time) {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.CHINA).format(time);

    }

    /**
     * 当前时间
     *
     * @return Timestamp
     */
    public static Timestamp cruntTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * @return String ex:2006-07-07 获取当前时间的字符串
     */
    public static String getCurrentDate() {
        Timestamp d = cruntTime();
        return d.toString().substring(0, 10);
    }

    /**
     * @return String ex:2006-07-07 22:10:10 获取当前时间的字符串
     */
    public static String getCurrentDateTimeStr() {
        Timestamp d = cruntTime();
        return d.toString().substring(0, 19);
    }

    public static String getWeekDay() {
        Calendar date = Calendar.getInstance();
        date.setTime(cruntTime());
        return new SimpleDateFormat("EEEE",Locale.US).format(date.getTime());
    }

    /**
     * 将毫秒转成时间
     */
    public static String getTimeMillisToDateStr(long l) {
        Timestamp d = new Timestamp(l);
        return d.toString().substring(0, 19);
    }

    /**
     * @param t
     *            Timestamp
     * @return String ex:2006-07-07 获取指定时间的字符串,只到日期
     */
    public static String getStrDate(Timestamp t) {
        return t.toString().substring(0, 10);
    }

    /**
     * @param t
     *            Timestamp
     * @return String ex:2006-07-07 22:10:10 获取指定时间的字符串
     */
    public static String getStrDateTime(Timestamp t) {
        return t.toString().substring(0, 19);
    }

    /**
     * @param days
     * @return String 获得当前日期的前段日期
     */
    public static String getStrIntervalDate(String days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -Integer.parseInt(days));
        String strBeforeDays = sdf.format(cal.getTime());
        return strBeforeDays;
    }

    /**
     *
     * @param date
     *            java.util.Date
     * @param day
     *            int
     * @return java.util.Date 获得某日前后的某一天 主要是进行日期计算 date2Str(getDayCounts(new Date(),-1)) 昨天
     */
    public static Date getDayCounts(Date date, int day) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(GregorianCalendar.DATE, day);
        return c.getTime();
    }

    /**
     * @param week
     *            int
     * @return String[] 获得距当前周的前后某一周的日期
     */
    public static String[] getDaysOfWeek(int week) {
        String[] days = new String[7];
        Date monday = getMondayOfWeek(week); // 获得距本周前或后的某周周一
        Timestamp t = new Timestamp(monday.getTime());
        days[0] = getStrDate(t);
        for (int i = 1; i < 7; i++) {
            t = new Timestamp(getDayCounts(monday, i).getTime());
            days[i] = getStrDate(t);
        }
        return days;
    }

    /**
     * @param week
     *            int
     * @return java.util.Date 获得距当前时间所在某星期的周一的日期 例： 0-本周周一日期 -1-上周周一日期 1-下周周一日期
     */
    public static Date getMondayOfWeek(int week) {
        int mondayPlus = getMondayPlus(); // 相距周一的天数差
        GregorianCalendar current = new GregorianCalendar();
        current.add(GregorianCalendar.DATE, mondayPlus + 7 * week);
        return current.getTime();
    }

    /**
     * @return int 获得当前系统日期与本周一相差的天数
     */
    private static int getMondayPlus() {
        Calendar calendar = Calendar.getInstance();
        // 获得今天是一周的第几天，正常顺序是星期日是第一天，星期一是第二天......
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 星期日是第一天
        return (dayOfWeek == 1) ? -6 : 2 - dayOfWeek;
    }

    /**
     *
     * @return 返回当前日期所在星期，2对应星期一
     */
    public static int getMonOfWeek() {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        return cal1.get(Calendar.DAY_OF_WEEK);
    }

    /**
     *
     * @param strDate
     * @param day
     * @return 获取指定日期之后的日期字符串 如 2007-04-15 后一天 就是 2007-04-16
     * getNextDay(getCurrentDate(),55) 当天后相差多少天 55可以是正负数
     */
    public static String getNextDay(String strDate, int day) {
        if (strDate != null && !strDate.equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            Calendar cal1 = Calendar.getInstance();
//			String[] string = strDate.trim().split("-");
//			int one = Integer.parseInt(string[0]) - 1900;
//			int two = Integer.parseInt(string[1]) - 1;
//			int three = Integer.parseInt(string[2]);
//			cal1.setTime(new Date(one, two, three));

            try {
                cal1.setTime(formatter.parse(strDate));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            cal1.add(Calendar.DAY_OF_MONTH, day);

            return formatter.format(cal1.getTime());
        } else {
            return null;
        }
    }

    //

    /**
     * @param strDate
     * @param year
     * @return 获取指定日期之后的日期字符串 如 2007-02-28 后一年 就是 2008-02-29 （含闰年）
     */
    public static String getNextYearDate(String strDate, int year) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        Calendar cal1 = Calendar.getInstance();

//		String[] string = strDate.trim().split("-");
//		int one = Integer.parseInt(string[0]) - 1900;
//		int two = Integer.parseInt(string[1]) - 1;
//		int three = Integer.parseInt(string[2]);
        try {
            cal1.setTime(formatter.parse(strDate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cal1.add(Calendar.YEAR, year);

        return formatter.format(cal1.getTime());
    }

    public static void main(String[] args) {
        System.out.println(getCurDateStr());
        System.out.println(getCurrentDate());
        System.out.println(getMondayPlus());
        System.out.println(getWeekDay());
        System.out.println(getMonOfWeek());
        System.out.println(getNextYearDate("2020-01-06",3));
        System.out.println(getNextDay("2020-01-06",53));
        System.out.println(getNextDay("2020-01-06",54));
        System.out.println(getNextDay("2020-01-06",52));
        System.out.println(getNextDay("2020-01-06",55));
        System.out.println(getNextDay(getCurrentDate(),55));
        System.out.println(getNextDay(getCurrentDate(),-2));
        System.out.println(date2Str(getDayCounts(new Date(),55)));
        System.out.println(date2Str(getDayCounts(new Date(),-1)));
    }
}

