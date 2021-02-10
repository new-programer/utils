package site.ericgao.dateutil;

import org.apache.commons.lang3.StringUtils;
import site.ericgao.commonutil.ProString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Project advanced
 * @Description
 * 日期工具类，提供常用的日期方法，包括日期格式转换，日期处理，获取日期等
 * @Author EricGao
 * @Date 2021/2/8 13:21
 * @Version 1.0
 **/
public class ProDateUtil {
    /** 格式为yyyy-MM-dd */
    public final static String DATE_FORMAT = "yyyy-MM-dd";
    /** 格式为HH:mm:ss */
    public final static String TIME_FORMAT = "HH:mm:ss";
    /** 格式为yyyy-MM-dd HH:mm:ss */
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 格式为yyyy-MM-dd HH:mm:ss */
    public final static String DATE_MILLI_SECOND_FORMAT = "HH:mm:ss.SSS";
    /** 格式为yyyyMMdd */
    public final static String DATE_SIMPLE_FORMAT = "yyyyMMdd";
    /** 格式为yyMMdd */
    public final static String DATE_2YEAR_FORMAT = "yyMMdd";
    /** 格式为yyyyMM */
    public final static String DATE_SIMPLE_MONTH_FORMAT = "yyyyMM";
    /** 格式为yyyy-MM */
    public final static String DATE_MONTH_FORMAT = "yyyy-MM";
    /** 格式为 HHmmss */
    public final static String TIME_SIMPLE_FORMAT = "HHmmss";
    /** 格式为yyyyMMddHHmmss */
    public final static String DATE_TIME_SIMPLE_FORMAT = "yyyyMMddHHmmss";
    /** 格式为yyyy年MM月dd日 */
    public final static String DATE_CHN_FORMAT = "yyyy年MM月dd日";
    /** 格式为HH时mm分ss秒 */
    public final static String TIME_CHN_FORMAT = "HH时mm分ss秒";
    /** 格式为yyyy年MM月dd日 HH时mm分ss秒 */
    public final static String DATE_TIME_CHN_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";
    /** 起始的时间后缀 00:00:00*/
    public final static String DATE_BEGIN = " 00:00:00";
    /** 起始的时间后缀 000000*/
    public final static String DATE_BEGIN_SHOT = "000000";
    /** 截止的时间后缀 23:59:59*/
    public final static String DATE_END = " 23:59:59";
    /** 截止的时间后缀 235959*/
    public final static String DATE_END_SHOT = "235959";

    /**
     * 获取日期字符串
     * @param date 时间
     * @return yyyy-MM-dd
     */
    public static String getDateStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取日期加时间的字符串
     * @param date 时间
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前日期
     * @return yyyy-MM-dd
     */
    public static String getCurrentDate() {
        return getCurrentDate(DATE_FORMAT);
    }

    /**
     * 获取当前日期
     * @param dateFormatStr 格式
     * @return New Date 日期
     */
    public static String getCurrentDate(String dateFormatStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatStr);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前日期时间
     * @return HH:mm:ss
     */
    public static String getCurrentTime() {
        return getCurrentDate(TIME_FORMAT);
    }

    /**
     * 获取当前的日期与时间
     * @return  yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTime() {
        return getCurrentDate(DATE_TIME_FORMAT);
    }

    /**
     * 获取当前时间是周几
     * @return String;New Date 周几
     */
    public static String getCurrentWeek() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取给定时间字符串是周几
     * @param dateStr 时间
     * @return  String 周几
     * @throws ParseException
     */
    public static String getWeek(String dateStr) throws ParseException {
        return getWeek(dateStr, DATE_FORMAT);

    }

    /**
     * 获取给定时间字符串是周几
     *
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return String 周几
     * @throws ParseException
     */
    public static String getWeek(String dateStr, String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
        return simpleDateFormat.format(date);
    }

    /**
     * 获得一个日期所在的周的星期几的日期
     *
     * @param dateStr 时间
     * @param num 星期几
     * @return String 日期
     * @throws ParseException
     */
    public static String getWeekDay(String dateStr, int num) throws ParseException {
        return getWeekDay(dateStr, num, DATE_FORMAT);
    }

    /**
     * 获得一个日期所在的周的星期几的日期
     *
     * @param dateStr 时间
     * @param num 星期几
     * @param dateFormatStr 格式
     * @return String 日期
     * @throws ParseException
     */
    public static String getWeekDay(String dateStr, int num,
                                    String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        return getWeekDay(date, num, dateFormatStr);
    }

    /**
     * 获得一个日期所在的周的星期几的日期 中国星期一是第一天跟calendar有所不同，所以需要特殊处理
     *
     * @param date 时间
     * @param num  星期几
     * @param dateFormatStr 格式
     * @return  String 日期
     */
    public static String getWeekDay(Date date, int num, String dateFormatStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            // 把周日设置到最后一位
            dayOfWeek = 7;
        }
        // 计算偏移量，直接按差距天数进行获取
        int offsetDay = num - dayOfWeek;
        return getIntervalDateStr(offsetDay, date, dateFormatStr);
    }

    /**
     * 获取当前日期的周末日期
     * @return  String
     */
    public static String getCurrentWeekend() {
        return getWeekDay(new Date(), 7, DATE_FORMAT);
    }

    /**
     * 获取给定时间字符串对应的周末日期
     * @param dateStr 时间
     * @return  String
     * @throws ParseException
     */
    public static String getWeekend(String dateStr) throws ParseException {
        return getWeekend(dateStr, DATE_FORMAT);
    }

    /**
     * 获取给定时间字符串对应的周末日期
     *
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return String
     * @throws ParseException
     */
    public static String getWeekend(String dateStr, String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        return getWeekDay(date, 7, dateFormatStr);
    }

    /**
     * 获取当前日期的周一日期
     * @return  String
     */
    public static String getCurrentMonday() {
        return getWeekDay(new Date(), 1, DATE_FORMAT);
    }

    /**
     * 获取给定时间字符串对应的周一日期
     * @param dateStr 时间字符串
     * @return  String
     * @throws ParseException
     */
    public static String getMonday(String dateStr) throws ParseException {
        return getMonday(dateStr, DATE_FORMAT);
    }

    /**
     * 获取给定日期的上一个月的日期
     *
     * @param dateStr 时间
     * @return String
     * @throws ParseException
     */
    public static String getLastMonthDay(String dateStr) throws ParseException {
        Date date = getDateObj(dateStr, DATE_FORMAT);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取给定日期的指定月份之后的日期
     * @param dateStr
     * @param n 指定月份之后
     * @return
     * @throws ParseException
     */
    public static String getNextMonthDay(String dateStr, int n) throws ParseException {
        Date date = getDateObj(dateStr, DATE_FORMAT);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + n);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取给定日期的上一年的日期
     *
     * @param dateStr 时间
     * @return String
     * @throws ParseException
     */
    public static String getLastYearDay(String dateStr) throws ParseException {
        Date date = getDateObj(dateStr, DATE_FORMAT);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取给定时间字符串对应的周一日期
     *
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return String
     * @throws ParseException
     */
    public static String getMonday(String dateStr, String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        return getWeekDay(date, 1, dateFormatStr);
    }

    /**
     * 获取当前月份
     * @return int
     */
    public static int getCurrentMonth() {
        // Calendar月份是从0开始的，所以需要加1
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取给定时间字符串所属月份
     *
     * @param dateStr 时间
     * @return int
     * @throws ParseException
     */
    public static int getMonth(String dateStr) throws ParseException {
        return getMonth(dateStr, DATE_FORMAT);
    }

    /**
     * 获取给定时间字符串所属月份
     *
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return int
     * @throws ParseException
     */
    public static int getMonth(String dateStr, String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        return getMonth(date);
    }

    /**
     * 获取给定时间字符串所属月份
     * @param date 时间
     * @return  int
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Calendar月份是从0开始的，所以需要加1
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取给定时间字符串所属月份
     *
     * @param dateStr 时间
     * @return  yyyy-MM-dd
     * @throws ParseException
     */
    public static int getDay(String dateStr) throws ParseException {
        return getDay(dateStr, DATE_FORMAT);
    }

    /**
     * 获取给定时间字符串所属月份
     *
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return int
     * @throws ParseException
     */
    public static int getDay(String dateStr, String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        return getDay(date);
    }

    /**
     * 获取给定时间字符串所属月份
     * @param date 时间
     * @return  int
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取当前月的最后一天的日期
     * @return yyyy-MM-dd

     */
    public static String getCurrentMonthEnd() {
        return getMonthEnd(new Date(), DATE_FORMAT);
    }

    /**
     * 获取给定日期字符串所在月份的最后一天的日期
     * @param dateStr
     * @return yyyy-MM-dd
     * @throws ParseException
     *
     */
    public static String getMonthEnd(String dateStr) throws ParseException {
        return getMonthEnd(dateStr, DATE_FORMAT);
    }

    /**
     * 获取给定日期字符串所在月份的最后一天的日期
     *
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return String
     * @throws ParseException
     *
     */
    public static String getMonthEnd(String dateStr, String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        return getMonthEnd(date, dateFormatStr);
    }

    /**
     * 获取给定日期字符串所在月份的最后一天的日期
     * @param date 时间
     * @param dateFormatStr 格式
     * @return String
     */
    public static String getMonthEnd(Date date, String dateFormatStr) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormatStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DATE, 0);
        return df.format(calendar.getTime());
    }

    /**
     * 获取当前月的第一天的日期
     * @return yyyy-MM-dd
     * @Author : EricGao. create at 2016年3月21日 上午11:19:41
     */
    public static String getCurrentMonthBegin() {
        return getMonthBegin(new Date(), DATE_FORMAT);
    }

    /**
     * 获取给定日期字符串所在月份的第一天的日期
     * @param dateStr
     * @return  yyyy-MM-dd
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:19:41
     */
    public static String getMonthBegin(String dateStr) throws ParseException {
        return getMonthBegin(dateStr, DATE_FORMAT);
    }

    /**
     * 获取给定日期字符串所在月份的第一天的日期
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return  String
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:19:41
     */
    public static String getMonthBegin(String dateStr, String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        return getMonthBegin(date, dateFormatStr);
    }

    /**
     * 获取给定日期字符串所在月份的第一天的日期
     * @param date 时间
     * @return  String
     * @Author : EricGao. create at 2016年3月21日 上午11:19:41
     */
    public static String getMonthBegin(Date date, String dateFormatStr) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormatStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DATE, 1);
        return df.format(calendar.getTime());
    }

    /**
     * 获取当前年
     * @return int
     * @Author : EricGao. create at 2016年3月21日 上午11:19:41
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取给定时间字符串所属年
     * @param dateStr 时间
     * @return yyyy-MM-dd
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:19:41
     */
    public static int getYear(String dateStr) throws ParseException {
        return getYear(dateStr, DATE_FORMAT);
    }

    /**
     * 获取给定时间字符串所属年
     *
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return  int
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:19:41
     */
    public static int getYear(String dateStr, String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取季节 12,1,2 冬 3,4,5春 6,7,8夏 9,10,11秋
     * @return String
     * @Author : EricGao. create at 2016年3月21日 上午11:22:48
     */
    public static String getCurrentSeason() {
        return getSeason(getCurrentMonth());
    }

    /**
     * 获取给定时间字符串所属季节
     * @param dateStr 时间
     * @return yyyy-MM-dd
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:22:48
     */
    public static String getSeason(String dateStr) throws ParseException {
        return getSeason(dateStr, DATE_FORMAT);
    }

    /**
     * 获取给定时间字符串所属季节
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return String
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:22:48
     */
    public static String getSeason(String dateStr, String dateFormatStr) throws ParseException {
        return getSeason(getMonth(dateStr, dateFormatStr));
    }

    /**
     * 根据月份得到季节
     * @param month 月份
     * @return String
     * @Author : EricGao. create at 2016年3月21日 上午11:22:48
     */
    public static String getSeason(int month) {
        switch (month) {
            case 12:
            case 1:
            case 2:
                return "冬季";
            case 3:
            case 4:
            case 5:
                return "春季";
            case 6:
            case 7:
            case 8:
                return "夏季";
            case 9:
            case 10:
            case 11:
                return "秋季";
        }
        return "";
    }

    /**
     * 获取节气有问题？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？ 获取当前日期的对应的节气
     *
     * @return
     */
    /*
     * public static String getCurrentSolar(){ ProLunar l = new
     * ProLunar(System.currentTimeMillis()); return l.getTermString(); }
     *//**
     * 获取给定时间字符串所属节气
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    /*
     * public static String getSolar(String dateStr) throws ParseException{
     * return getSolar(dateStr,DATE_FORMAT); }
     *//**
     * 获取给定时间字符串所属节气
     *
     * @param dateStr
     * @param dateFormatStr
     * @return
     * @throws ParseException
     */

    /*
     * public static String getSolar(String dateStr,String dateFormatStr) throws
     * ParseException{ Date date = getDateObj(dateStr,dateFormatStr); ProLunar l
     * = new ProLunar(date); return l.getTermString(); }
     */
    /**
     * 获取农历日期
     * @return String
     */
    public static String getCurrentLunar() {
        ProLunar proLunar = new ProLunar(System.currentTimeMillis());
        return proLunar.getLunarDateString();
    }

    /**
     * 获取给定日期字符串所属农历日期
     * @param dateStr 时间
     * @return yyyy-MM-dd
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getLunar(String dateStr) throws ParseException {
        return getLunar(dateStr, DATE_FORMAT);
    }

    /**
     * 获取给定日期字符串所属农历日期
     *
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return String
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getLunar(String dateStr, String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        ProLunar proLunar = new ProLunar(date);
        return proLunar.getLunarDateString();
    }

    /**
     * 获取前一天的日期
     * @return yyyy-MM-dd
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getCurrentLastDate() {
        return getIntervalDateStr(-1, new Date(), DATE_FORMAT);
    }

    /**
     * 获取特定格式的前一天日期
     *
     * @param dateStr 时间
     * @return yyyy-MM-dd
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getLastDate(String dateStr) throws ParseException {
        return getIntervalDateStr(-1, dateStr, DATE_FORMAT);
    }

    /**
     * 获取特定格式的前一天日期
     *
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return String
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getLastDate(String dateStr, String dateFormatStr) throws ParseException {
        return getIntervalDateStr(-1, dateStr, dateFormatStr);
    }

    /**
     * 获取后一天的日期
     * @return yyyy-MM-dd
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getCurrentNextDate() {
        return getIntervalDateStr(1, new Date(), DATE_FORMAT);
    }

    /**
     * 获取特定格式的后一天的日期
     * @param dateStr 时间
     * @return yyyy-MM-dd
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getNextDate(String dateStr) throws ParseException {
        return getIntervalDateStr(1, dateStr, DATE_FORMAT);
    }

    /**
     * 获取后一天
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return String
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getNextDate(String dateStr, String dateFormatStr) throws ParseException {
        return getIntervalDateStr(1, dateStr, dateFormatStr);
    }


    /**
     * 获取输入日期的下 n 天 返回 8位 like 20050101
     *
     * @param day
     * @param n
     * @return
     */
    public static String getNextDay(String day, int n) {
        if (day == null || "".equals(day) || day.length() != 8) {
            throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的日期换算.");
        }
        try {
            String sYear = day.substring(0, 4);
            int year = Integer.parseInt(sYear);
            String sMonth = day.substring(4, 6);
            int month = Integer.parseInt(sMonth);
            String sDay = day.substring(6, 8);
            int dday = Integer.parseInt(sDay);
            Calendar cal = Calendar.getInstance();
            cal.set(year, month - 1, dday);
            cal.add(Calendar.DATE, n);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            return df.format(cal.getTime());

        } catch (Exception e) {
            throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);

        }
    }

    public static String getNextDateByYear(String date, int n) {
        if (date == null || "".equals(date) || date.length() != 8) {
            throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的日期换算.");
        }
        try {
            String sYear = date.substring(0, 4);
            int year = Integer.parseInt(sYear);
            String sMonth = date.substring(4, 6);
            int month = Integer.parseInt(sMonth);
            String sDay = date.substring(6, 8);
            int day = Integer.parseInt(sDay);
            Calendar cal = Calendar.getInstance();
            cal.set(year, month - 1, day);
            cal.add(Calendar.YEAR, n);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            return df.format(cal.getTime());
        } catch (Exception e) {
            throw new RuntimeException("根据年份变换进行日期计算是输入得参数不符合系统规格." + e);
        }
    }

    /**
     * 获取当前前后某天的日期
     * @param intervalNum
     * @return  yyyy-MM-dd
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getCurrentIntervalDate(int intervalNum) {
        return getIntervalDateStr(intervalNum, new Date(), DATE_FORMAT);
    }

    /**
     * 获取特定格式的前后某天的日期
     *
     * @param intervalNum 几天
     * @param dateStr 时间
     * @return  yyyy-MM-dd
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getIntervalDateStr(int intervalNum, String dateStr) throws ParseException {
        return getIntervalDateStr(intervalNum, dateStr, DATE_FORMAT);
    }

    /**
     * 获取特定格式的前后某天的日期
     *
     * @param intervalNum 前后几天
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return String
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getIntervalDateStr(int intervalNum, String dateStr,
                                            String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        return getIntervalDateStr(intervalNum, date, dateFormatStr);
    }

    /**
     * 获取前后某天的日期
     *
     * @param intervalNum 前后几天
     * @param date 时间
     * @param dateFormatStr 格式
     * @return String
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getIntervalDateStr(int intervalNum, Date date, String dateFormatStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatStr);
        return simpleDateFormat.format(getIntervalDate(intervalNum, date));
    }

    /**
     * 获取前后某天的日期
     *
     * @param intervalNum 前后几天
     * @param date 时间
     * @return Date
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static Date getIntervalDate(int intervalNum, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, intervalNum);
        return calendar.getTime();
    }

    /**
     * 获取当前日期的时间戳
     * @return yyyyMMddHHmmssmm
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getCurrentDateTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssmm");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前日期的时间戳
     * @param len 长度
     * @return String
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static String getCurrentDateTimeStamp(int len) {
        String stamp = getCurrentDateTimeStamp();
        if (len < 16) {
            return stamp.substring(0, len);
        } else {
            return stamp + ProString.getRandom(len - 16);
        }
    }

    /**
     * 获取本月天数
     * @return int
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static int getCurrentMonthDayCount() {
        return getMonthDayCount(new Date());
    }

    /**
     * 获取指定日期对应月份的天数
     *
     * @param dateStr 时间
     * @return  yyyy-MM-dd
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static int getMonthDayCount(String dateStr) throws ParseException {
        return getMonthDayCount(dateStr, DATE_FORMAT);
    }

    /**
     * 获取指定日期对应月份的天数
     *
     * @param dateStr 时间
     * @param dateFormatStr 格式
     * @return  int
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static int getMonthDayCount(String dateStr, String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        return getMonthDayCount(date);
    }

    /**
     * 获取指定日期对应月份的天数
     *
     * @param date 时间
     * @return int
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static int getMonthDayCount(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DATE, 0);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取两个日期字符串之间的日期list
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @param includeSelf  boolean
     * @return  List<String>;yyyy-MM-dd
     * @throws Exception
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static List<String> getDateStrList(String beginDateStr, String endDateStr,
                                              boolean includeSelf) throws Exception {
        return getDateStrList(beginDateStr, endDateStr, includeSelf, DATE_FORMAT);
    }

    /**
     * 获取两个日期字符串之间的日期list(包含传入的日期)
     *
     * @param beginDateStr 开始日期
     * @param endDateStr  结束日期
     * @param dateFormatStr 格式
     * @param includeSelf boolean
     * @return List<String>
     * @throws Exception
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static List<String> getDateStrList(String beginDateStr, String endDateStr,
                                              boolean includeSelf,
                                              String dateFormatStr) throws Exception {
        List<String> dateList = new ArrayList<String>();
        Date beginDate = getDateObj(beginDateStr, dateFormatStr);
        Date endDate = getDateObj(endDateStr, dateFormatStr);
        if (beginDate.before(endDate)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatStr);
            if (includeSelf) {
                dateList.add(simpleDateFormat.format(beginDate));
            }
            Calendar beginCal = Calendar.getInstance();
            beginCal.setTime(beginDate);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate);
            while (beginCal.before(endCal)) {
                beginCal.add(Calendar.DATE, 1);
                simpleDateFormat.format(beginDate.getTime());
                dateList.add(simpleDateFormat.format(beginCal.getTime()));
            }
            if (includeSelf) {
                dateList.add(simpleDateFormat.format(endDate));
            }
            return dateList;
        } else {
            throw new Exception("开始日期不能大于结束日期");
        }

    }

    /**
     * 获取两个日期之间的间隔天数
     *
     * @param beginDateStr 开始日期
     * @param endDateStr 结束日期
     * @return long 天数
     * @throws Exception
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static long get2DatesIntervalNum(String beginDateStr,
                                            String endDateStr) throws Exception {
        return get2DatesIntervalNum(beginDateStr, endDateStr, DATE_FORMAT);
    }

    /**
     * 获取两个日期之间的间隔天数
     *
     * @param beginDateStr 开始日期
     * @param endDateStr 结束日期
     * @param dateFormatStr 格式
     * @return long 天数
     * @throws Exception
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static long get2DatesIntervalNum(String beginDateStr, String endDateStr,
                                            String dateFormatStr) throws Exception {
        Date beginDate = getDateObj(beginDateStr, dateFormatStr);
        Date endDate = getDateObj(endDateStr, dateFormatStr);
        return get2DatesIntervalNum(beginDate, endDate);
        /*
         * if(beginDate.before(endDate)){ return (endDate.getTime() -
         * beginDate.getTime()) / (24 * 60 * 60 * 1000); }else{ return
         * -(beginDate.getTime()-endDate.getTime() ) / (24 * 60 * 60 * 1000);
         * //throw new Exception("开始日期不能大于结束日期"); }
         */
    }

    /**
     * 获取两个日期之间的间隔天数
     *
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return long 天数
     * @throws Exception
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static long get2DatesIntervalNum(Date beginDate, Date endDate) throws Exception {
        // if(beginDate.before(endDate)){
        try {
            beginDate = getDateObj(ProDateUtil.getDateStr(beginDate));
            endDate = getDateObj(ProDateUtil.getDateStr(endDate));
        } catch (ParseException e) {
            throw new Exception("日期格式转换错误");
            // return 0;
        }
        return (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // }else{
        // return -(beginDate.getTime()-endDate.getTime() ) / (24 * 60 * 60 *
        // 1000);
        // throw new Exception("开始日期不能大于结束日期");
        // }
    }

    /**
     * 获取给定日期字符串对应的日期对象，并把时间设置为00:00：00用于查询，
     *
     * @param dateStr 时间
     * @return yyyy-MM-dd HH:mm:ss
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:25:08
     */
    public static Date convertDateStr2Date4BeginQuery(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr + " 00:00:00");
        } catch (ParseException e) {
            throw new ParseException("日期格式错误: \"" + dateStr + "\"", 0);
        }
        return date;
    }

    /**
     * 获取给定日期字符串对应的日期对象，并把时间设置为23:59：59用于查询，
     * @param dateStr 时间
     * @return Date
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static Date convertDateStr2Date4EndQuery(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr + " 23:59:59");
        } catch (ParseException e) {
            throw new ParseException("日期格式错误: \"" + dateStr + "\"", 0);
        }
        return date;
    }

    /**
     * 比较两个日期字符串
     *
     * @param beginDateStr 开始日期
     * @param endDateStr 结束日期
     * @return 1:开始日期小于结束日期 -1：开始日期大于结束日期 0：开始日期等于结束日期
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static int compareDateStr(String beginDateStr, String endDateStr) throws ParseException {
        return compareDateStr(beginDateStr, endDateStr, DATE_TIME_SIMPLE_FORMAT);
    }

    /**
     * 比较两个日期字符串 yyyyMMdd
     *
     * @param beginDateStr 开始日期
     * @param endDateStr 结束日期
     * @return 1:开始日期小于结束日期 -1：开始日期大于结束日期 0：开始日期等于结束日期
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static int compareDateStrSimple(String beginDateStr,
                                           String endDateStr) throws ParseException {
        return compareDateStr(beginDateStr, endDateStr, DATE_SIMPLE_FORMAT);
    }

    /**
     * 比较两个日期字符串
     * @param beginDateStr 开始日期
     * @param endDateStr 结束日期
     * @param dateFormatStr 格式
     * @return 1:开始日期小于结束日期 -1：开始日期大于结束日期 0：开始日期等于结束日期
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static int compareDateStr(String beginDateStr, String endDateStr,
                                     String dateFormatStr) throws ParseException {
        Date beginDate = getDateObj(beginDateStr, dateFormatStr);
        Date endDate = getDateObj(endDateStr, dateFormatStr);
        return endDate.compareTo(beginDate);
    }

    /**
     * 比较两个时间字符串
     * @param beginTimeStr 开始日期
     * @param endTimeStr 结束日期
     * @return 1:开始时间小于结束时间 -1：开始时间大于结束时间 0：开始时间等于结束时间
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static int compareTimeStr(String beginTimeStr, String endTimeStr) throws ParseException {
        return compareTimeStr(beginTimeStr, endTimeStr, TIME_FORMAT);
    }

    /**
     * 比较两个时间字符串(跟比较日期使用相同的方法)
     *
     * @param beginTimeStr 开始日期
     * @param endTimeStr 结束日期
     * @param timeFormatStr 格式
     * @return 1:开始时间小于结束时间 -1：开始时间大于结束时间 0：开始时间等于结束时间
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static int compareTimeStr(String beginTimeStr, String endTimeStr,
                                     String timeFormatStr) throws ParseException {
        return compareDateStr(beginTimeStr, endTimeStr, timeFormatStr);
    }

    /**
     * 比较给定日期与当前日期
     *
     * @param dateStr 日期
     * @return 1:传入日期小于当前日期 -1：传入日期大于当前日期 0：传入日期等于当前日期
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static int compareCurrentDate(String dateStr) throws ParseException {
        return compareCurrentDate(dateStr, DATE_FORMAT);
    }

    /**
     * 比较给定日期与当前日期(需要格式化为天，否则加上时间后比较有问题)
     *
     * @param dateStr 日期
     * @param dateFormatStr 格式
     * @return int 0:当前日期和dateStr相等  小于0：dateStr小于当前日期 大于0：dateStr大于当前日期
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static int compareCurrentDate(String dateStr,
                                         String dateFormatStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatStr);
        Date beginDate = getDateObj(dateStr, dateFormatStr);
        Date endDate = getDateObj(simpleDateFormat.format(new Date()), dateFormatStr);
        return beginDate.compareTo(endDate);
    }

    /**
     * 将日期字符串转换成date对象
     *
     * @param dateStr 日期字符串
     * @return yyyy-MM-dd
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static Date getDateObj(String dateStr) throws ParseException {
        return getDateObj(dateStr, DATE_FORMAT);
    }

    /**
     * 将日期字符串转换成date对象
     *
     * @param dateStr 日期字符串
     * @param dateFormatStr 格式
     * @return Date
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static Date getDateObj(String dateStr, String dateFormatStr) throws ParseException {
        if (dateStr != null && dateFormatStr != null) {
            if (dateStr.length() >= dateFormatStr.length()) {
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormatStr);
                return sdf.parse(dateStr);
            } else {
                throw new ParseException("转换值【" + dateStr + "】长度不能小于转换格式【" + dateFormatStr + "】长度",
                        0);
            }
        } else {
            throw new ParseException("转换值和转换格式不能为null", 0);
        }

    }

    /**
     * 日期字符串格式转换为其他格式字符串
     *
     * @param dateStr 日期字符串(yyyy-MM-dd)
     * @param dateFormatStr 格式
     * @return
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static String convertDateStr2DateStr(String dateStr, String dateFormatStr) {
        return convertDateStr2DateStr(dateStr, DATE_FORMAT, dateFormatStr);
    }

    /**
     * 日期字符串格式转换为其他格式字符串
     *
     * @param dateStr 日期
     * @param fromDateFormatStr 初格式
     * @param toDateFormatStr 其他格式
     * @return  String
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static String convertDateStr2DateStr(String dateStr, String fromDateFormatStr,
                                                String toDateFormatStr) {
        Date date = null;
        try {
            date = getDateObj(dateStr, fromDateFormatStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("【字符串格式的日期转成Date对象失败】dateStr = " + dateStr
                    + " , fromDateFormatStr = " + fromDateFormatStr);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(toDateFormatStr);
        return sdf.format(date);
    }

    /**
     * 日期格式转换为给定格式
     *
     * @param timeStr 日期
     * @param timeFormatStr 格式
     * @return HH:mm:ss
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static String convertTimeStr2TimeStr(String timeStr,
                                                String timeFormatStr) throws ParseException {
        return convertTimeStr2TimeStr(timeStr, TIME_FORMAT, timeFormatStr);
    }

    /**
     * 日期格式转换为给定格式
     * @param timeStr 日期
     * @param fromTimeFormatStr 初格式
     * @param toTimeFormatStr 其他格式
     * @return
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static String convertTimeStr2TimeStr(String timeStr, String fromTimeFormatStr,
                                                String toTimeFormatStr) throws ParseException {
        return convertDateStr2DateStr(timeStr, fromTimeFormatStr, toTimeFormatStr);
    }

    /**
     * 判断两个日期段是否有交叉
     *
     * @param beginDateStr1 开始日期1
     * @param endDateStr1 结束日期1
     * @param beginDateStr2 开始日期2
     * @param endDateStr2 结束日期2
     * @return boolean;true:有交叉;false:无交叉
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static boolean isOverlap(String beginDateStr1, String endDateStr1, String beginDateStr2,
                                    String endDateStr2) throws ParseException {
        return isOverlap(beginDateStr1, endDateStr1, beginDateStr2, endDateStr2, DATE_FORMAT);
    }

    /**
     * 判断两个日期段是否有交叉
     *
     * @param beginDateStr1 开始日期1
     * @param endDateStr1 结束日期1
     * @param beginDateStr2 开始日期2
     * @param endDateStr1 结束日期2
     * @param dateFormatStr 格式
     * @return  boolean;true:有交叉;false:无交叉
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static boolean isOverlap(String beginDateStr1, String endDateStr1, String beginDateStr2,
                                    String endDateStr2,
                                    String dateFormatStr) throws ParseException {
        Date beginDate1 = getDateObj(beginDateStr1, dateFormatStr);
        Date endDate1 = getDateObj(endDateStr1, dateFormatStr);
        Date beginDate2 = getDateObj(beginDateStr2, dateFormatStr);
        Date endDate2 = getDateObj(endDateStr2, dateFormatStr);
        // 判断交叉
        int result1 = beginDate2.compareTo(beginDate1);
        int result2 = beginDate2.compareTo(endDate1);
        if ((result1 == 0 || result1 == 1) && (result2 == 0 || result2 == -1)) {
            return true;
        }
        int result3 = endDate2.compareTo(beginDate1);
        int result4 = endDate2.compareTo(endDate1);
        if ((result3 == 0 || result3 == 1) && (result4 == 0 || result4 == -1)) {
            return true;
        }
        // 判断包含
        if ((result1 == 0 || result1 == -1) && (result4 == 0 || result4 == 1)) {
            return true;
        }
        // 判断包含
        if ((result1 == 0 || result1 == 1) && (result4 == 0 || result4 == -1)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为闰年
     * @return boolean;true:是闰年;false:不是闰年
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static boolean isLeapYear() {
        return isLeapYear(new Date());
    }

    /**
     * 判断是否为闰年
     *
     * @param dateStr 时间
     * @return boolean;true:是闰年;false:不是闰年
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static boolean isLeapYear(String dateStr) throws ParseException {
        return isLeapYear(dateStr, DATE_FORMAT);
    }

    /**
     * 判断是否为闰年
     *
     * @param dateStr 日期
     * @param dateFormatStr 格式
     * @return  boolean;true:是闰年;false:不是闰年
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static boolean isLeapYear(String dateStr, String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        return isLeapYear(date);
    }

    /**
     * 判断是否为闰年 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
     * 3.能被4整除同时能被100整除则不是闰年
     *
     * @param date 日期
     * @return boolean;true:是闰年;false:不是闰年
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static boolean isLeapYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        if ((year % 400) == 0) {
            return true;
        } else if ((year % 4) == 0) {
            if ((year % 100) == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断指定日期是否在两个日期范围内
     *
     * @param dateStr 日期
     * @param beginDateStr 开始日期
     * @param endDateStr 结束日期
     * @return boolean;true:在范围内;false:不再范围内
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static boolean isInDateScope(String dateStr, String beginDateStr,
                                        String endDateStr) throws ParseException {
        return isInDateScope(dateStr, beginDateStr, endDateStr, DATE_FORMAT);
    }

    /**
     * 判断指定日期是否在两个日期范围内
     *
     * @param dateStr 日期
     * @param beginDateStr 开始日期
     * @param endDateStr 结束日期
     * @param dateFormatStr 格式
     * @return boolean;true:在范围内;false:不再范围内
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static boolean isInDateScope(String dateStr, String beginDateStr, String endDateStr,
                                        String dateFormatStr) throws ParseException {
        Date date = getDateObj(dateStr, dateFormatStr);
        Date beginDate = getDateObj(beginDateStr, dateFormatStr);
        Date endDate = getDateObj(endDateStr, dateFormatStr);
        int result1 = date.compareTo(beginDate);
        int result2 = date.compareTo(endDate);
        if ((result1 == 0 || result1 == 1) && (result2 == 0 || result2 == -1)) {
            return true;
        }
        return false;
    }

    /**
     * 判断指定时间是否在两个时间范围内
     *
     * @param timeStr 时间
     * @param beginTimeStr 开始时间
     * @param endTimeStr 结束时间
     * @return boolean;true:在范围内;false:不再范围内
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static boolean isInTimeScope(String timeStr, String beginTimeStr,
                                        String endTimeStr) throws ParseException {
        return isInTimeScope(timeStr, beginTimeStr, endTimeStr, TIME_FORMAT);
    }

    /**
     * 判断指定时间是否在两个时间范围内
     *
     * @param timeStr 时间
     * @param beginTimeStr 开始时间
     * @param endTimeStr 结束时间
     * @param timeFormateStr 时间格式
     * @return  boolean;true:在范围内;false:不再范围内
     * @throws ParseException
     * @Author : EricGao. create at 2016年3月21日 上午11:37:29
     */
    public static boolean isInTimeScope(String timeStr, String beginTimeStr, String endTimeStr,
                                        String timeFormateStr) throws ParseException {
        return isInDateScope(timeStr, beginTimeStr, endTimeStr, timeFormateStr);
    }

    /**
     * 将字符串日期转换成日期对象
     * @param dateStr 字符串日期
     * @param format 格式
     * @return {@link Date}
     * @throws ParseException
     * @Author : ll. create at 2016年5月13日 上午10:54:35
     */
    public static Date parse(String dateStr, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(dateStr);
    }

    /**
     * 日期（yyyyMMdd格式）切片，根据传入的起止日期的间隔满足的条件，对日期进行切片，不包含起始日期
     * @param dateBeginStr 其实日期
     * @param dateEndStr 结束日期
     * @return 时间切片集合
     * @Author : ll. create at 2016年5月17日 下午6:25:52
     */
    public static List<String> getDatePoint(String dateBeginStr, String dateEndStr) {
        if (StringUtils.isBlank(dateBeginStr)) {
            throw new IllegalArgumentException("dateBeginStr");
        }
        if (StringUtils.isBlank(dateEndStr)) {
            throw new IllegalArgumentException("dateEndStr");
        }

        List<String> results = new ArrayList<String>();
        if (dateBeginStr.equals(dateEndStr)) {
            results.add(dateEndStr);
            return results;
        }

        Date dateBegin = null;
        Date dateEnd = null;
        try {
            dateBegin = parse(dateBeginStr, DATE_SIMPLE_FORMAT);
            dateEnd = parse(dateEndStr, DATE_SIMPLE_FORMAT);
        } catch (ParseException e) {
            throw new IllegalArgumentException();
        }

        if (dateBegin.getTime() > dateEnd.getTime()) {
            throw new IllegalArgumentException("起始日期不能大于结束日期");
        }

        Date tmpDate = dateBegin;

        for (int i = 1; tmpDate.getTime() < dateEnd.getTime(); i++) {
            tmpDate = addDay(dateBegin, i);
            results.add(format(tmpDate, DATE_SIMPLE_FORMAT));
        }
        return results;
    }

    /**
     * 月份切片，根据传入年份，划分出1-12月
     * @param year 年份
     * @param connectSingal 连接，如"","-","/"
     * @return 月份集合，如2016-01
     */
    public static List<String> getMonthPoint(String year, String connectSingal) {
        List<String> monthList = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            String month = i < 10 ? "0" + i : "" + i;
            monthList.add(year + connectSingal + month);
        }
        return monthList;
    }

    /**
     * 使用用户格式格式化日期
     * @param date 日期
     * @param pattern 日期格式
     * @return 格式化之后的日期
     * @Author : ll. create at 2016年5月17日 下午6:41:32
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return returnValue;
    }

    /**
     * 在当前日期的基础上，添加天数
     * @param date 需要操作的日期
     * @param day 需要添加的天数
     * @return 操作后的日期
     * @Author : ll. create at 2016年5月17日 下午6:39:13
     */
    public static Date addDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 在当前日期的基础上，添加天数
     * @param dateStr 需要操作的日期
     * @param day 需要添加的天数
     * @param format 日期的格式
     * @return 操作后的日期
     * @throws ParseException
     * @Author : ll. create at 2016年5月17日 下午6:39:13
     */
    public static String addDay(String dateStr, int day, String format) throws ParseException {
        Date date = parse(dateStr, format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return format(cal.getTime(), format);
    }

    /**
     * 校验日期所在的月份是否过完
     * @param statementMonth 需要校验的日期
     * @param dateFormat 日期格式
     * @return true表示已经过完
     * @throws ParseException
     * @Author : ll. create at 2016年7月5日 上午11:02:16
     */
    public static boolean isMonthOver(String statementMonth,
                                      String dateFormat) throws ParseException {
        if (StringUtils.isBlank(statementMonth)) {
            throw new IllegalArgumentException("statementMonth");
        }

        // 获取需要校验的月份的最后一天
        String lastDayOfMonth = getMonthEnd(parse(statementMonth, dateFormat), DATE_FORMAT);

        String currentDay = getCurrentDate(DATE_FORMAT);

        int result = compareDateStr(currentDay, lastDayOfMonth, DATE_FORMAT);

        return result == -1;
    }

    /**
     * 验证日期不能大于当天
     * @param date 日期
     * @return true:小于或等于 false:大于
     * @Author : EricGao. create at 2016年7月13日 上午11:49:44
     */
    public static Boolean validataDate(String date) {
        String nowDate = ProDateUtil.getCurrentDate(ProDateUtil.DATE_SIMPLE_FORMAT);
        int conut = 0;
        try {
            conut = ProDateUtil.compareDateStrSimple(date, nowDate);
        } catch (ParseException e) {
            throw new RuntimeException("【比较错误】{}", e);
        }

        if (conut == -1) {
            return false;
        }
        return true;
    }
}
