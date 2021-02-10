package site.ericgao.dateutil;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 阴历
 * @create Jkf
 * @createDate 2016年3月21日 下午1:57:08
 * @update EricGao   
 * @updateDate 2016年3月21日13:57:47
 */
public class ProLunar {
    private Calendar solar;
    private int lunarYear;
    private int lunarMonth;
    private int lunarDay;
    private boolean isLeap;
    private boolean isLeapYear;
    private int solarYear;
    private int solarMonth;
    private int solarDay;
    private int cyclicalYear = 0;
    private int cyclicalMonth = 0;
    private int cyclicalDay = 0;
    private int maxDayInMonth = 29;
    private final static Pattern SFREG = Pattern.compile("^(\\d{2})(\\d{2})([\\s\\*])(.+)$");
    private final static Pattern WFREG = Pattern.compile("^(\\d{2})(\\d)(\\d)([\\s\\*])(.+)$");
    private final static int[] LUNARINFO = { 0x4bd8, 0x4ae0, 0xa570, 0x54d5, 0xd260, 0xd950,
                                            0x5554, 0x56af, 0x9ad0, 0x55d2, 0x4ae0, 0xa5b6, 0xa4d0,
                                            0xd250, 0xd295, 0xb54f, 0xd6a0, 0xada2, 0x95b0, 0x4977,
                                            0x497f, 0xa4b0, 0xb4b5, 0x6a50, 0x6d40, 0xab54, 0x2b6f,
                                            0x9570, 0x52f2, 0x4970, 0x6566, 0xd4a0, 0xea50, 0x6a95,
                                            0x5adf, 0x2b60, 0x86e3, 0x92ef, 0xc8d7, 0xc95f, 0xd4a0,
                                            0xd8a6, 0xb55f, 0x56a0, 0xa5b4, 0x25df, 0x92d0, 0xd2b2,
                                            0xa950, 0xb557, 0x6ca0, 0xb550, 0x5355, 0x4daf, 0xa5b0,
                                            0x4573, 0x52bf, 0xa9a8, 0xe950, 0x6aa0, 0xaea6, 0xab50,
                                            0x4b60, 0xaae4, 0xa570, 0x5260, 0xf263, 0xd950, 0x5b57,
                                            0x56a0, 0x96d0, 0x4dd5, 0x4ad0, 0xa4d0, 0xd4d4, 0xd250,
                                            0xd558, 0xb540, 0xb6a0, 0x95a6, 0x95bf, 0x49b0, 0xa974,
                                            0xa4b0, 0xb27a, 0x6a50, 0x6d40, 0xaf46, 0xab60, 0x9570,
                                            0x4af5, 0x4970, 0x64b0, 0x74a3, 0xea50, 0x6b58, 0x5ac0,
                                            0xab60, 0x96d5, 0x92e0, 0xc960, 0xd954, 0xd4a0, 0xda50,
                                            0x7552, 0x56a0, 0xabb7, 0x25d0, 0x92d0, 0xcab5, 0xa950,
                                            0xb4a0, 0xbaa4, 0xad50, 0x55d9, 0x4ba0, 0xa5b0, 0x5176,
                                            0x52bf, 0xa930, 0x7954, 0x6aa0, 0xad50, 0x5b52, 0x4b60,
                                            0xa6e6, 0xa4e0, 0xd260, 0xea65, 0xd530, 0x5aa0, 0x76a3,
                                            0x96d0, 0x4afb, 0x4ad0, 0xa4d0, 0xd0b6, 0xd25f, 0xd520,
                                            0xdd45, 0xb5a0, 0x56d0, 0x55b2, 0x49b0, 0xa577, 0xa4b0,
                                            0xaa50, 0xb255, 0x6d2f, 0xada0, 0x4b63, 0x937f, 0x49f8,
                                            0x4970, 0x64b0, 0x68a6, 0xea5f, 0x6b20, 0xa6c4, 0xaaef,
                                            0x92e0, 0xd2e3, 0xc960, 0xd557, 0xd4a0, 0xda50, 0x5d55,
                                            0x56a0, 0xa6d0, 0x55d4, 0x52d0, 0xa9b8, 0xa950, 0xb4a0,
                                            0xb6a6, 0xad50, 0x55a0, 0xaba4, 0xa5b0, 0x52b0, 0xb273,
                                            0x6930, 0x7337, 0x6aa0, 0xad50, 0x4b55, 0x4b6f, 0xa570,
                                            0x54e4, 0xd260, 0xe968, 0xd520, 0xdaa0, 0x6aa6, 0x56df,
                                            0x4ae0, 0xa9d4, 0xa4d0, 0xd150, 0xf252, 0xd520 };
    private final static int[] SOLARTERMINFO = { 0, 21208, 42467, 63836, 85337, 107014, 128867,
                                                150921, 173149, 195551, 218072, 240693, 263343,
                                                285989, 308563, 331033, 353350, 375494, 397447,
                                                419210, 440795, 462224, 483532, 504758 };
    /** 阴历时间*/
    public final static String[] TIANAN = { "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸" };
    /** 代表生肖的阴历时间*/ 
    public final static String[] DEQI = { "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌",
                                         "亥" };
    /** 十二生肖*/
    public final static String[] ANIMALS = { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗",
                                            "猪" };
    /** 二十四节气*/
    public final static String[] SOLARTERM = { "小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨",
                                              "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露",
                                              "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至" };
    /** 个位*/
    public final static String[] LUNARSTRING1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
    /** 十位*/
    public final static String[] LUNARSTRING2 = { "初", "十", "廿", "卅", "正", "腊", "冬", "闰" };
    /** 国历节日 *表示放假日*/
    private final static String[] SFTV = { "0101*元旦", "0214 情人节", "0308 妇女节", "0312 植树节",
                                          "0315 消费者权益日", "0401 愚人节", "0501*劳动节", "0504 青年节",
                                          "0509 郝维节", "0512 护士节", "0601 儿童节", "0701 建党节 香港回归纪念",
                                          "0801 建军节", "0808 父亲节", "0816 燕衔泥节", "0909 毛泽东逝世纪念",
                                          "0910 教师节", "0928 孔子诞辰", "1001*国庆节", "1006 老人节",
                                          "1024 联合国日", "1111 光棍节", "1112 孙中山诞辰纪念", "1220 澳门回归纪念",
                                          "1225 圣诞节", "1226 毛泽东诞辰纪念" };
    /** 农历节日 *表示放假日*/
    private final static String[] LFTV = { "0101*春节、弥勒佛诞", "0106 定光佛诞", "0115 元宵节", "0208 释迦牟尼佛出家",
                                          "0215 释迦牟尼佛涅槃", "0209 海空上师诞", "0219 观世音菩萨诞",
                                          "0221 普贤菩萨诞", "0316 准提菩萨诞", "0404 文殊菩萨诞", "0408 释迦牟尼佛诞",
                                          "0415 佛吉祥日——释迦牟尼佛诞生、成道、涅槃三期同一庆(即南传佛教国家的卫塞节)", "0505 端午节",
                                          "0513 伽蓝菩萨诞", "0603 护法韦驮尊天菩萨诞",
                                          "0619 观世音菩萨成道——此日放生、念佛，功德殊胜", "0707 七夕情人节",
                                          "0713 大势至菩萨诞", "0715 中元节", "0724 龙树菩萨诞", "0730 地藏菩萨诞",
                                          "0815 中秋节", "0822 燃灯佛诞", "0909 重阳节", "0919 观世音菩萨出家纪念日",
                                          "0930 药师琉璃光如来诞", "1005 达摩祖师诞", "1107 阿弥陀佛诞",
                                          "1208 释迦如来成道日，腊八节", "1224 小年", "1229 华严菩萨诞", "0100*除夕" };
    /** 某月的第几个星期几*/
    private static String[] WFTV = { "0520 母亲节", "0716 合作节", "0730 被奴役国家周" };

    /*public static void main(String[] args) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ProLunar l = new ProLunar(System.currentTimeMillis());
        System.out.println("节气:" + l.getTermString());
        System.out.println("干支历:" + l.getCyclicalDateString());
        // System.out.println("星期" + l.getDayOfWeek());
        System.out.println("农历" + l.getLunarDateString());
        Calendar t = Calendar.getInstance();
        t.set(1985, 10, 17);
        // System.out.print(t.getTime().getMonth() + "月");
        // System.out.println(getbeforesolarTerm(t.get(Calendar.YEAR),
        // t.getTime()));
        // System.out.println(getaftersolarTerm(t.get(Calendar.YEAR),
        // t.getTime()));
        Date[] jieqi = jieqilist(2013);
        for (int i = 0; i < SOLARTERM.length; i++) {
            System.out.println(SOLARTERM[i] + "=" + df.format(jieqi[i]));
            // System.out.print(jieqi[i].getMonth() + "月");
            // System.out.println(jieqi[i].getDate());
        }
        // for(int i=0;i<solarTerm.length;i++){
        // System.out.println(solarTermInfo[i+1]-solarTermInfo[i]);
        // }
        Date d = ProDateUtil.getDateObj("2012-01-01");
        for (int i = 0; i < 600; i++) {
            String temp = ChineseTwentyFourDay(ProDateUtil.getDateObj(ProDateUtil
                .getIntervalDateStr(i, d, "yyyy-MM-dd")));
            if (temp != null && !temp.equals("")) {
                System.out.println("==" + temp);
            }

        }

        // Datejieqilist(2013);
    }*/

    /*    *//**
              * 获得某天前个节气日期差
              * 
              * @return 日期数
              */
    /*
     * public static long getbeforesolarTerm(int year, Date date) { List<Date>
     * jieqi =Alljieqi(year); int[] jieqibeforeafter = getnearsolarTerm(year,
     * date); return MyDate.DateDays(date, jieqi.get(jieqibeforeafter[0]));
     * 
     * 
     * }
     *//**
       * 获得某天后个节气日期差
       * 
       * @return 日期数
       */
    /*
     * public static long getaftersolarTerm(int year, Date date) { List<Date>
     * jieqi =Alljieqi(year); int[] jieqibeforeafter = getnearsolarTerm(year,
     * date); return MyDate.DateDays(date, jieqi.get(jieqibeforeafter[1]));
     * 
     * }
     */

    public static List<Date> Alljieqi(int year) {

        List<Date> jieqi = new ArrayList<Date>();
        Date[] temp;
        temp = jieqilist(year - 1);
        jieqi.addAll(Arrays.asList(temp));
        temp = jieqilist(year);
        jieqi.addAll(Arrays.asList(temp));
        temp = jieqilist(year + 1);
        jieqi.addAll(Arrays.asList(temp));
        return jieqi;
    }

    /**
     * 获得某天前后两个节气序号
     * @param year 年
     * @param date 时间
     * @return 节气序号
     * @Author : EricGao. create at 2016年3月21日 下午1:47:37
     */
    public static int[] getnearsolarTerm(int year, Date date) {
        List<Date> jieqi = Alljieqi(year);

        int[] returnValue = new int[2];
        for (int i = 0; i < jieqi.size(); i++) {
            if (date.getTime() > jieqi.get(i).getTime()) {
                continue;
            }
            if (i % 2 == 0) {// 只管气
                returnValue[0] = i - 2;
                returnValue[1] = i;
            } else {
                returnValue[0] = i - 1;
                returnValue[1] = i + 1;

            }
            break;
        }

        return returnValue;
    }

    /**
     * 获得某年中所有节气Date
     * @param year 年
     * @return 节气Date[]
     * @Author : EricGao. create at 2016年3月21日 下午1:48:16
     */
    public static Date[] jieqilist(int year) {
        Date[] returnvalue = new Date[SOLARTERM.length];

        for (int i = 0; i < SOLARTERM.length; i++) {

            Date t = getSolarTermCalendar(year, i);
            returnvalue[i] = t;

        }
        return returnvalue;
    }
    
    /**
     * String转化为int
     * @param str 字符串
     * @return int
     * @Author : EricGao. create at 2016年3月21日 下午1:48:54
     */
    private static int toInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return -1;
        }
    }
    
    private synchronized void findFestival() {
        int sM = this.getSolarMonth();
        int sD = this.getSolarDay();
        int lM = this.getLunarMonth();
        int lD = this.getLunarDay();
        int sy = this.getSolarYear();
        Matcher m;
        for (int i = 0; i < ProLunar.SFTV.length; i++) {
            m = ProLunar.SFREG.matcher(ProLunar.SFTV[i]);
            if (m.find()) {
                if (sM == ProLunar.toInt(m.group(1)) && sD == ProLunar.toInt(m.group(2))) {
                    this.isSFestival = true;
                    this.sFestivalName = m.group(4);
                    if ("*".equals(m.group(3))) {
                        this.isHoliday = true;
                    }
                    break;
                }
            }
        }
        for (int i = 0; i < ProLunar.LFTV.length; i++) {
            m = ProLunar.SFREG.matcher(ProLunar.LFTV[i]);
            if (m.find()) {
                if (lM == ProLunar.toInt(m.group(1)) && lD == ProLunar.toInt(m.group(2))) {
                    this.isLFestival = true;
                    this.lFestivalName = m.group(4);
                    if ("*".equals(m.group(3))) {
                        this.isHoliday = true;
                    }
                    break;
                }
            }
        }

        // 月周节日
        int w, d;
        for (int i = 0; i < ProLunar.WFTV.length; i++) {
            m = ProLunar.WFREG.matcher(ProLunar.WFTV[i]);
            if (m.find()) {
                if (this.getSolarMonth() == ProLunar.toInt(m.group(1))) {
                    w = ProLunar.toInt(m.group(2));
                    d = ProLunar.toInt(m.group(3));
                    if (this.solar.get(Calendar.WEEK_OF_MONTH) == w
                        && this.solar.get(Calendar.DAY_OF_WEEK) == d) {
                        this.isSFestival = true;
                        this.sFestivalName += "|" + m.group(5);
                        if ("*".equals(m.group(4))) {
                            this.isHoliday = true;
                        }
                    }
                }
            }
        }
        if (sy > 1874 && sy < 1909) {
            this.description = "光绪" + (((sy - 1874) == 1) ? "元" : "" + (sy - 1874));
        }
        if (sy > 1908 && sy < 1912) {
            this.description = "宣统" + (((sy - 1908) == 1) ? "元" : String.valueOf(sy - 1908));
        }
        if (sy > 1911 && sy < 1950) {
            this.description = "民国" + (((sy - 1911) == 1) ? "元" : String.valueOf(sy - 1911));
        }
        if (sy > 1949) {
            this.description = "共和国" + (((sy - 1949) == 1) ? "元" : String.valueOf(sy - 1949));
        }
        this.description += "年";
        this.sFestivalName = this.sFestivalName.replaceFirst("^\\|", "");
        this.isFinded = true;
    }

    private boolean isFinded = false;
    private boolean isSFestival = false;
    private boolean isLFestival = false;
    private String sFestivalName = "";
    private String lFestivalName = "";
    private String description = "";
    private boolean isHoliday = false;

   
    /**
     * 返回农历年闰月月份
     * @param lunarYear 指定农历年份(数字)
     * @return 该农历年闰月的月份(数字,没闰返回0)
     * @Author : EricGao. create at 2016年3月21日 下午1:50:10
     */
    private static int getLunarLeapMonth(int lunarYear) {
        // 数据表中,每个农历年用16bit来表示,
        // 前12bit分别表示12个月份的大小月,最后4bit表示闰月
        // 若4bit全为1或全为0,表示没闰, 否则4bit的值为闰月月份
        int leapMonth = ProLunar.LUNARINFO[lunarYear - 1900] & 0xf;
        leapMonth = (leapMonth == 0xf ? 0 : leapMonth);
        return leapMonth;
    }

    
    /**
     * 返回农历年闰月的天数
     * @param lunarYear 指定农历年份(数字)
     * @return 该农历年闰月的天数(数字)
     * @Author : EricGao. create at 2016年3月21日 下午1:50:26
     */
    private static int getLunarLeapDays(int lunarYear) {
        // 下一年最后4bit为1111,返回30(大月)
        // 下一年最后4bit不为1111,返回29(小月)
        // 若该年没有闰月,返回0
        return ProLunar.getLunarLeapMonth(lunarYear) > 0 ? ((ProLunar.LUNARINFO[lunarYear - 1899] & 0xf) == 0xf ? 30
            : 29)
            : 0;
    }

    /**
     * 返回农历年的总天数
     * @param lunarYear 指定农历年份(数字)
     * @return 该农历年的总天数(数字)
     * @Author : EricGao. create at 2016年3月21日 下午1:50:45
     */
    private static int getLunarYearDays(int lunarYear) {
        // 按小月计算,农历年最少有12 * 29 = 348天
        int daysInLunarYear = 348;
        // 数据表中,每个农历年用16bit来表示,
        // 前12bit分别表示12个月份的大小月,最后4bit表示闰月
        // 每个大月累加一天
        for (int i = 0x8000; i > 0x8; i >>= 1) {
            daysInLunarYear += ((ProLunar.LUNARINFO[lunarYear - 1900] & i) != 0) ? 1 : 0;
        }
        // 加上闰月天数
        daysInLunarYear += ProLunar.getLunarLeapDays(lunarYear);

        return daysInLunarYear;
    }

    /**
     * 返回农历年正常月份的总天数
     * @param lunarYear 指定农历年份(数字)
     * @param lunarMonth  指定农历月份(数字)
     * @return 该农历年闰月的月份(数字,没闰返回0)
     * @Author : EricGao. create at 2016年3月21日 下午1:50:45
     */
    private static int getLunarMonthDays(int lunarYear, int lunarMonth) {
        // 数据表中,每个农历年用16bit来表示,
        // 前12bit分别表示12个月份的大小月,最后4bit表示闰月
        int daysInLunarMonth = ((ProLunar.LUNARINFO[lunarYear - 1900] & (0x10000 >> lunarMonth)) != 0) ? 30
            : 29;
        return daysInLunarMonth;
    }

    /**
     * 取 Date 对象中用全球标准时间 (UTC) 表示的日期
     * @param date  指定日期
     * @return UTC 全球标准时间 (UTC) 表示的日期
     * @Author : EricGao. create at 2016年3月21日 下午1:51:45
     */
    public static synchronized int getUTCDay(Date date) {
        ProLunar.makeUTCCalendar();
        synchronized (utcCal) {
            utcCal.clear();
            utcCal.setTimeInMillis(date.getTime());
            return utcCal.get(Calendar.DAY_OF_MONTH);
        }
    }

    private static GregorianCalendar utcCal = null;

    private static synchronized void makeUTCCalendar() {
        if (ProLunar.utcCal == null) {
            ProLunar.utcCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        }
    }

    /**
     * 返回全球标准时间 (UTC) (或 GMT) 的 1970 年 1 月 1 日到所指定日期之间所间隔的毫秒数。
     * @param y 指定年份
     * @param m 指定月份
     * @param d 指定日期
     * @param h 指定小时
     * @param min 指定分钟
     * @param sec 指定秒数
     * @return 全球标准时间 (UTC) (或 GMT) 的 1970 年 1 月 1 日到所指定日期之间所间隔的毫秒数
     * @Author : EricGao. create at 2016年3月21日 下午1:52:45
     */
    public static synchronized long UTC(int y, int m, int d, int h, int min, int sec) {
        ProLunar.makeUTCCalendar();
        synchronized (utcCal) {
            utcCal.clear();
            utcCal.set(y, m, d, h, min, sec);
            return utcCal.getTimeInMillis();
        }
    }

    /**
     * 返回公历年节气的日期
     * @param solarYear 指定公历年份(数字)
     * @param index 指定节气序号(数字,0从小寒算起)
     * @return 日期(数字,所在月份的第几天)
     * @Author : EricGao. create at 2016年3月21日 下午1:53:45
     */
    private static int getSolarTermDay(int solarYear, int index) {

        return ProLunar.getUTCDay(getSolarTermCalendar(solarYear, index));
    }

    /**
     * 返回公历年节气的日期
     * @param solarYear 指定公历年份(数字)
     * @param index 指定节气序号(数字,0从小寒算起)
     * @return 日期(数字,所在月份的第几天)
     * @Author : EricGao. create at 2016年3月21日 下午1:53:50
     */
    public static Date getSolarTermCalendar(int solarYear, int index) {
        // 0, 21208, 42467, 63836, 85337, 107014, 128867, 150921,
        // 173149, 195551, 218072, 240693, 263343, 285989, 308563, 331033,
        // 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758
        Calendar cl = Calendar.getInstance();
        cl.set(1901, 0, 6);// 1900-01-31是农历1900年正月初一
        // Calendar cl1 = Calendar.getInstance();
        // cl1.set(solarYear, 0, 31);
        long l = cl.getTimeInMillis() + SOLARTERMINFO[index] * 60000L;
        // long l = (long) 31556925974.7 * (solarYear - 1900)
        // + solarTermInfo[index] * 60000L;
        // l = l + ProLunar.UTC(1900, 0, 6, 2, 5, 0);
        return new Date(l);
    }

    public static String ChineseTwentyFourDay(Date date1) {

        String[] SolarTerm = new String[] { "小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏",
                                           "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分",
                                           "寒露", "霜降", "立冬", "小雪", "大雪", "冬至" };
        int[] sTermInfo = new int[] { 0, 21208, 42467, 63836, 85337, 107014, 128867, 150921,
                                     173149, 195551, 218072, 240693, 263343, 285989, 308563,
                                     331033, 353350, 375494, 397447, 419210, 440795, 462224,
                                     483532, 504758 };
        // Date baseDateAndTime = new Date(1900, 1, 6, 2, 5, 0); //#1/6/1900
        // 2:05:00 AM#
        Calendar cal = Calendar.getInstance();
        cal.set(1900, 0, 6, 2, 5, 0);// 月份从0开始
        final Date baseDateAndTime = cal.getTime();
        double num;
        int y;
        String tempStr = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        // 获取年份
        y = Integer.parseInt(df.format(date1));

        for (int i = 1; i <= 24; i++) {
            num = 525948.76 * (y - 1900) + sTermInfo[i - 1];
            Calendar calendarMin = Calendar.getInstance();
            calendarMin.setTime(baseDateAndTime);
            // 添加分钟
            calendarMin.add(Calendar.MINUTE, (int) Math.ceil(num) + 5);
            // 对传递的天数进行处理
            Calendar calendarDate1 = Calendar.getInstance();
            calendarDate1.setTime(date1);
            /*
             * System.out.println(y+ "    "+ num); System.out.println(
             * "  date1  "+ formatData(date1, "yyyy-MM-dd HH:mm:ss"));
             * System.out.println("添加分钟："+formatData(calendarMin.getTime(),
             * "yyyy-MM-dd HH:mm:ss"));
             */

            // 对时间进行格式化字符串比较
            if (df1.format(calendarMin.getTime()).trim()
                .equals(df1.format(calendarDate1.getTime()).trim())) {
                tempStr = SolarTerm[i - 1];
                System.out.print(df1.format(calendarMin.getTime()));
                break;
            }
        }
        return tempStr;
    }

   
    /**
     * 通过 Date 对象构建农历信息
     * @param date 指定日期对象
     */
    public ProLunar(Date date) {
        if (date == null) {
            date = new Date();
        }
        this.init(date.getTime());
    }

    /**
     * 通过 TimeInMillis 构建农历信息
     * @param TimeInMillis
     */
    public ProLunar(long TimeInMillis) {
        this.init(TimeInMillis);
    }

    private void init(long TimeInMillis) {
        this.solar = Calendar.getInstance();
        this.solar.setTimeInMillis(TimeInMillis);
        Calendar baseDate = new GregorianCalendar(1900, 0, 31);
        long offset = (TimeInMillis - baseDate.getTimeInMillis()) / 86400000;
        // 按农历年递减每年的农历天数，确定农历年份
        this.lunarYear = 1900;
        int daysInLunarYear = ProLunar.getLunarYearDays(this.lunarYear);
        while (this.lunarYear < 2100 && offset >= daysInLunarYear) {
            offset -= daysInLunarYear;
            daysInLunarYear = ProLunar.getLunarYearDays(++this.lunarYear);
        }
        // 农历年数字

        // 按农历月递减每月的农历天数，确定农历月份
        int lunarMonth = 1;
        // 所在农历年闰哪个月,若没有返回0
        int leapMonth = ProLunar.getLunarLeapMonth(this.lunarYear);
        // 是否闰年
        this.isLeapYear = leapMonth > 0;
        // 闰月是否递减
        boolean leapDec = false;
        boolean isLeap = false;
        int daysInLunarMonth = 0;
        while (lunarMonth < 13 && offset > 0) {
            if (isLeap && leapDec) { // 如果是闰年,并且是闰月
                // 所在农历年闰月的天数
                daysInLunarMonth = ProLunar.getLunarLeapDays(this.lunarYear);
                leapDec = false;
            } else {
                // 所在农历年指定月的天数
                daysInLunarMonth = ProLunar.getLunarMonthDays(this.lunarYear, lunarMonth);
            }
            if (offset < daysInLunarMonth) {
                break;
            }
            offset -= daysInLunarMonth;

            if (leapMonth == lunarMonth && isLeap == false) {
                // 下个月是闰月
                leapDec = true;
                isLeap = true;
            } else {
                // 月份递增
                lunarMonth++;
            }
        }
        this.maxDayInMonth = daysInLunarMonth;
        // 农历月数字
        this.lunarMonth = lunarMonth;
        // 是否闰月
        this.isLeap = (lunarMonth == leapMonth && isLeap);
        // 农历日数字
        this.lunarDay = (int) offset + 1;
        // 取得干支历
        this.getCyclicalData();
    }

    /**
     * 取干支历 不是历年，历月干支，而是中国的从立春节气开始的节月，是中国的太阳十二宫，阳历的。
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    private void getCyclicalData() {
        this.solarYear = this.solar.get(Calendar.YEAR);
        this.solarMonth = this.solar.get(Calendar.MONTH);
        this.solarDay = this.solar.get(Calendar.DAY_OF_MONTH);
        // 干支历
        int cyclicalYear = 0;
        int cyclicalMonth = 0;
        int cyclicalDay = 0;

        // 干支年 1900年立春後为庚子年(60进制36)
        int term2 = ProLunar.getSolarTermDay(solarYear, 2); // 立春日期
        // 依节气调整二月分的年柱, 以立春为界
        if (solarMonth < 1 || (solarMonth == 1 && solarDay < term2)) {
            cyclicalYear = (solarYear - 1900 + 36 - 1) % 60;
        } else {
            cyclicalYear = (solarYear - 1900 + 36) % 60;
        }

        // 干支月 1900年1月小寒以前为 丙子月(60进制12)
        int firstNode = ProLunar.getSolarTermDay(solarYear, solarMonth * 2); // 传回当月「节」为几日开始
        // 依节气月柱, 以「节」为界
        if (solarDay < firstNode) {
            cyclicalMonth = ((solarYear - 1900) * 12 + solarMonth + 12) % 60;
        } else {
            cyclicalMonth = ((solarYear - 1900) * 12 + solarMonth + 13) % 60;
        }

        // 当月一日与 1900/1/1 相差天数
        // 1900/1/1与 1970/1/1 相差25567日, 1900/1/1 日柱为甲戌日(60进制10)
        cyclicalDay = (int) (ProLunar.UTC(solarYear, solarMonth, solarDay, 0, 0, 0) / 86400000 + 25567 + 10) % 60;
        this.cyclicalYear = cyclicalYear;
        this.cyclicalMonth = cyclicalMonth;
        this.cyclicalDay = cyclicalDay;
    }

    /**
     * 取农历年生肖
     * @return 农历年生肖(例:龙)
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public String getAnimalString() {
        return ProLunar.ANIMALS[(this.lunarYear - 4) % 12];
    }

    /**
     * 返回公历日期的节气字符串
     * @return 二十四节气字符串,若不是节气日,返回空串(例:冬至)
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public String getTermString() {
        // 二十四节气
        String termString = "";
        if (ProLunar.getSolarTermDay(solarYear, solarMonth * 2) == solarDay) {
            termString = ProLunar.SOLARTERM[solarMonth * 2];
        } else if (ProLunar.getSolarTermDay(solarYear, solarMonth * 2 + 1) == solarDay) {
            termString = ProLunar.SOLARTERM[solarMonth * 2 + 1];
        }
        return termString;
    }

    /**
     * 取得干支历字符串
     * @return 干支历字符串(例:甲子年甲子月甲子日)
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public String getCyclicalDateString() {
        return this.getCyclicaYear() + "年" + this.getCyclicaMonth() + "月" + this.getCyclicaDay()
               + "日";
    }

    /**
     * 年份天干
     * @return 年份天干
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public int getTiananY() {
        return ProLunar.getTianan(this.cyclicalYear);
    }

    /**
     * 月份天干
     * @return 月份天干
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public int getTiananM() {
        return ProLunar.getTianan(this.cyclicalMonth);
    }

    /**
     * 日期天干
     * @return 日期天干
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public int getTiananD() {
        return ProLunar.getTianan(this.cyclicalDay);
    }

    /**
     * 年份地支
     * @return 年分地支
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public int getDeqiY() {
        return ProLunar.getDeqi(this.cyclicalYear);
    }

    /**
     * 月份地支
     * @return 月份地支
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public int getDeqiM() {
        return ProLunar.getDeqi(this.cyclicalMonth);
    }

    /**
     * 日期地支
     * @return 日期地支
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public int getDeqiD() {
        return ProLunar.getDeqi(this.cyclicalDay);
    }

    /**
     * 取得干支年字符串
     * @return 干支年字符串
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public String getCyclicaYear() {
        return ProLunar.getCyclicalString(this.cyclicalYear);
    }

    /**
     * 取得干支月字符串
     * @return 干支月字符串
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public String getCyclicaMonth() {
        return ProLunar.getCyclicalString(this.cyclicalMonth);
    }

    /**
     * 取得干支日字符串
     * @return 干支日字符串
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public String getCyclicaDay() {
        return ProLunar.getCyclicalString(this.cyclicalDay);
    }

    /**
     * 返回农历日期字符串
     * @return 农历日期字符串
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public String getLunarDayString() {
        return ProLunar.getLunarDayString(this.lunarDay);
    }

    /**
     * 返回农历日期字符串
     * @return 农历日期字符串
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public String getLunarMonthString() {
        return (this.isLeap() ? "闰" : "") + ProLunar.getLunarMonthString(this.lunarMonth);
    }

    /**
     * 返回农历日期字符串
     * @return 农历日期字符串
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public String getLunarYearString() {
        return ProLunar.getLunarYearString(this.lunarYear);
    }

    /**
     * 返回农历表示字符串
     * @return 农历字符串(例:甲子年正月初三)
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public String getLunarDateString() {
        return this.getLunarYearString() + "年" + this.getLunarMonthString() + "月"
               + this.getLunarDayString() + "日";
    }

    /**
     * 农历年是否是闰月
     * @return 农历年是否是闰月
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public boolean isLeap() {
        return isLeap;
    }

    /**
     * 农历年是否是闰年
     * @return 农历年是否是闰年
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public boolean isLeapYear() {
        return isLeapYear;
    }

    /**
     * 当前农历月是否是大月
     * @return 当前农历月是大月
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public boolean isBigMonth() {
        return this.getMaxDayInMonth() > 29;
    }

    /**
     * 当前农历月有多少天
     * @return 当前农历月有多少天
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public int getMaxDayInMonth() {
        return this.maxDayInMonth;
    }

    /**
     * 农历日期
     * @return 农历日期
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public int getLunarDay() {
        return lunarDay;
    }

    /**
     * 农历月份
     * @return 农历月份
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public int getLunarMonth() {
        return lunarMonth;
    }

    /**
     * 农历年份
     * @return 农历年份
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    public int getLunarYear() {
        return lunarYear;
    }

    /**
     * 公历日期
     * 
     * @return 公历日期
     */
    public int getSolarDay() {
        return solarDay;
    }

    /**
     * 公历月份
     * 
     * @return 公历月份 (不是从0算起)
     */
    public int getSolarMonth() {
        return solarMonth + 1;
    }

    /**
     * 公历年份
     * 
     * @return 公历年份
     */
    public int getSolarYear() {
        return solarYear;
    }

    /**
     * 星期几
     * 
     * @return 星期几(星期日为:1, 星期六为:7)
     */
    /*
     * public int getDayOfWeek() { return this.solar.get(Calendar.DAY_OF_WEEK);
     * }
     */

    /**
     * 黑色星期五
     * 
     * @return 是否黑色星期五
     */
    /*
     * public boolean isBlackFriday() { return (this.getSolarDay() == 13 &&
     * this.solar.get(Calendar.DAY_OF_WEEK) == 6); }
     */
    /**
     * 是否是今日
     * 
     * @return 是否是今日
     */
    /*
     * public boolean isToday() { Calendar clr = Calendar.getInstance(); return
     * clr.get(Calendar.YEAR) == this.solarYear && clr.get(Calendar.MONTH) ==
     * this.solarMonth && clr.get(Calendar.DAY_OF_MONTH) == this.solarDay; }
     */
    /**
     * 取得公历节日名称
     * 
     * @return 公历节日名称,如果不是节日返回空串
     */
    public String getSFestivalName() {
        return this.sFestivalName;
    }

    /**
     * 取得农历节日名称
     * @return 农历节日名称,如果不是节日返回空串
     */
    public String getLFestivalName() {
        return this.lFestivalName;
    }

    /**
     * 是否是农历节日
     * 
     * @return 是否是农历节日
     */
    public boolean isLFestival() {
        if (!this.isFinded) {
            this.findFestival();
        }
        return this.isLFestival;
    }

    /**
     * 是否是公历节日
     * 
     * @return 是否是公历节日
     */
    public boolean isSFestival() {
        if (!this.isFinded) {
            this.findFestival();
        }
        return this.isSFestival;
    }

    /**
     * 是否是节日
     * 
     * @return 是否是节日
     */
    public boolean isFestival() {
        return this.isSFestival() || this.isLFestival();
    }

    /**
     * 是否是放假日
     * 
     * @return 是否是放假日
     */
    public boolean isHoliday() {
        if (!this.isFinded) {
            this.findFestival();
        }
        return this.isHoliday;
    }

    /**
     * 其它日期说明
     * 
     * @return 日期说明(如:民国2年)
     */
    public String getDescription() {
        if (!this.isFinded) {
            this.findFestival();
        }
        return this.description;
    }

    /**
     * 干支字符串
     * @param cyclicalNumber 指定干支位置(数字,0为甲子)
     * @return 干支字符串
     * @Author : EricGao. create at 2016年3月21日 下午1:53:35
     */
    private static String getCyclicalString(int cyclicalNumber) {
        return ProLunar.TIANAN[ProLunar.getTianan(cyclicalNumber)]
               + ProLunar.DEQI[ProLunar.getDeqi(cyclicalNumber)];
    }

    /**
     * 获得地支
     * @param cyclicalNumber
     * @return 地支 (数字)
     * @Author : EricGao. create at 2016年3月21日 下午1:54:35
     */
    private static int getDeqi(int cyclicalNumber) {
        return cyclicalNumber % 12;
    }

    /**
     * 获得天干
     * @param cyclicalNumber
     * @return 天干 (数字)
     * @Author : EricGao. create at 2016年3月21日 下午1:54:35
     */
    private static int getTianan(int cyclicalNumber) {
        return cyclicalNumber % 10;
    }

    /**
     * 返回指定数字的农历年份表示字符串
     * @param lunarYear 农历年份(数字,0为甲子)
     * @return 农历年份字符串
     * @Author : EricGao. create at 2016年3月21日 下午1:54:35
     */
    private static String getLunarYearString(int lunarYear) {
        return ProLunar.getCyclicalString(lunarYear - 1900 + 36);
    }

    /**
     * 返回指定数字的农历月份表示字符串
     * @param lunarMonth 农历月份(数字)
     * @return 农历月份字符串 (例:正)
     * @Author : EricGao. create at 2016年3月21日 下午1:54:35
     */
    private static String getLunarMonthString(int lunarMonth) {
        String lunarMonthString = "";
        if (lunarMonth == 1) {
            lunarMonthString = ProLunar.LUNARSTRING2[4];
        } else {
            if (lunarMonth > 9) {
                lunarMonthString += ProLunar.LUNARSTRING2[1];
            }
            if (lunarMonth % 10 > 0) {
                lunarMonthString += ProLunar.LUNARSTRING1[lunarMonth % 10];
            }
        }
        return lunarMonthString;
    }

    /**
     * 返回指定数字的农历日表示字符串
     * @param lunarDay 农历日(数字)
     * @return 农历日字符串 (例: 廿一)
     * @Author : EricGao. create at 2016年3月21日 下午1:54:35
     */
    private static String getLunarDayString(int lunarDay) {
        if (lunarDay < 1 || lunarDay > 30) {
            return "";
        }
        int i1 = lunarDay / 10;
        int i2 = lunarDay % 10;
        String c1 = ProLunar.LUNARSTRING2[i1];
        String c2 = ProLunar.LUNARSTRING1[i2];
        if (lunarDay < 11) {
            c1 = ProLunar.LUNARSTRING2[0];
        }
        if (i2 == 0) {
            c2 = ProLunar.LUNARSTRING2[1];
        }
        return c1 + c2;
    }
}
