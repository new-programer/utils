package site.ericgao.commonutil;

import org.apache.commons.lang3.StringUtils;
import site.ericgao.dateutil.ProDateUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProString {

    private static final String PARAM_ALL = "$\\{[a-zA-Z0-9]+\\}";

    private static final String PARAM = "[a-zA-Z0-9]+";

    /**
     * 分割字符串
     * @return 返回分割结果的字符串
     * @throws NullPointerException 传入的字符串为空，则抛出异常
     */
    public static String[] getSpiltStrArray(String str,String rule){
    	if(str == null){
    		throw new NullPointerException("传入的字符串不能为空！");
    	}
    	return str.split(rule.toString());
    }
    
    /**
     * 判断是否为null，如果是就用""代替;
     * @param str 字符串
     * @return str 转化为"";
     * @Author : pengjunhao. create at 2016年3月21日 下午2:31:08
     */
    public static String getString(String str) {
        return str == null ? "" : str;
    }

    /**
     * 判断是否为null，如果是就用""代替toString;
     * @param intValue 数值
     * @return String
     * @Author : pengjunhao. create at 2016年3月21日 下午2:31:39
     */
    public static String getString(Integer intValue) {
        return intValue == null ? "" : intValue.toString();
    }

    /**
     * 判断是否为null，如果是就用""代替toString;
     * @param floatValue 
     * @return String
     * @Author : pengjunhao. create at 2016年3月21日 下午2:32:18
     */
    public static String getString(Float floatValue) {
        return floatValue == null ? "" : floatValue.toString();
    }

    /**
     * 判断是否为null，如果是就用""代替toString;
     * @param bigDecimalValue
     * @return String
     * @Author : pengjunhao. create at 2016年3月21日 下午2:33:34
     */
    public static String getString(BigDecimal bigDecimalValue) {
        return bigDecimalValue == null ? "" : bigDecimalValue.toString();
    }

    /**
     * 判断是否为null，如果是就用""代替toString;
     * @param str
     * @return String
     * @Author : pengjunhao. create at 2016年3月21日 下午2:33:38
     */
    public static BigDecimal getBigDecimal(String str) {
        return str == null ? null : new BigDecimal(str);
    }

    /**
     * 判断是否为null，如果是就用""代替toString;
     * @param date
     * @return String
     * @Author : pengjunhao. create at 2016年3月21日 下午2:33:42
     */
    public static String getString(Date date) {
        return date == null ? "" : ProDateUtil.getDateStr(date);
    }

    /**
     * 进行null或者空过来
     * @param str 字符串
     * @param defaultValue 默认值
     * @return String
     * @Author : pengjunhao. create at 2016年3月21日 下午2:17:03
     */
    public static String getString(String str, String defaultValue) {
        return str == null || str.equals("") ? defaultValue : str;
    }

    /**
     * 进行null过滤
     * @param intValue 数字
     * @param defaultValue 默认值
     * @return Integer
     * @Author : pengjunhao. create at 2016年3月21日 下午2:18:32
     */
    public static Integer getInteger(Integer intValue, Integer defaultValue) {
        return intValue == null ? defaultValue : intValue;
    }

    public static String getIntegerStr(Integer intValue) {
        return intValue == null ? "" : intValue.toString();
    }

    /**
     * 进行null过滤
     * @param charValue char
     * @param defaultValue 默认值
     * @return Character
     * @Author : pengjunhao. create at 2016年3月21日 下午2:19:05
     */
    public static Character getCharacter(Character charValue, Character defaultValue) {
        return charValue == null ? defaultValue : charValue;
    }

    public static String getCharacterStr(Character charValue) {
        return charValue == null ? "" : charValue.toString();
    }

    /**
     * 检查字符串中是否非法字符,为防止sql注入，需要过滤',(,)等
     * @param str 字符串
     * @return boolean; true:不是非法字符;false:是非法字符
     * @Author : pengjunhao. create at 2016年3月21日 下午2:19:26
     */
    public static boolean securityCheck(String str) {
        if (str.indexOf("'") > -1 || str.indexOf("`") > -1)
            return false;
        return true;
    }

    /**
     * 把userInfo转换为 USER_INFO
     * @param str 字符串
     * @return String 大写字符串
     * @Author : pengjunhao. create at 2016年3月21日 下午2:20:29
     */
    public static String upperStr(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            // System.out.println(a);
            if (a >= 65 && a <= 90) {
                stringBuffer.append("_");
            }
            stringBuffer.append(String.valueOf(a));
        }
        return stringBuffer.toString().toUpperCase();
    }

    /**
     * 把USER_INFO转化为userInfo
     * @param str
     * @return
     * @Author : pengjunhao. create at 2016年5月11日 下午4:08:57
     */
    public static String lowerStr(String str) {
        str = str.toLowerCase();
        if (str.contains("_")) {
            int a = str.indexOf("_");
            str = str.substring(0, a) + str.substring(a + 1, a + 2).toUpperCase()
                  + str.substring(a + 2, str.length());
            str = str.replace("_", "");
        }
        return str;
    }

    /**
     *  把userInfo转换为 UserInfo
     * @param str 字符串
     * @return String 开头大写
     * @Author : pengjunhao. create at 2016年3月21日 下午2:20:51
     */
    public static String upperFirstStr(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }

    /**
     *  把UserInfo转换为 userInfo
     * @param str 字符串
     * @return String 开头小写
     * @Author : pengjunhao. create at 2016年3月21日 下午2:20:51
     */
    public static String lowerFirst(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
    }

    /**
     * 把数组转换为字符串，使用joinStr进行连接
     * @param objects 数组
     * @param joinStr 添加值
     * @return String
     * @Author : pengjunhao. create at 2016年3月21日 下午2:21:14
     */
    public static String join(Object[] objects, String joinStr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Object str : objects) {
            if (stringBuffer.length() > 0) {
                stringBuffer.append(joinStr);
            }
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }

    /**
     * 把数组转换为字符串，使用joinStr进行连接
     * @param strs 数组Integer[]
     * @param joinStr 添加值
     * @return String
     * @Author : pengjunhao. create at 2016年3月21日 下午2:21:46
     */
    public static String join(Integer[] strs, String joinStr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Integer str : strs) {
            if (stringBuffer.length() > 0) {
                stringBuffer.append(joinStr);
            }
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }

    /**
     * 获取固定长度随机数
     * @param len 固定长度
     * @return String 随机数
     * @Author : pengjunhao. create at 2016年3月21日 下午2:22:37
     */
    public static String getRandom(int len) {
        Random random = new Random();
        // int temp= random.nextInt(len);
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuffer.append(random.nextInt(10));
        }
        return stringBuffer.toString();
    }

    /**
     * 获取日期自定义格式
     * @param format 格式
     * @return  String
     * @Author : pengjunhao. create at 2016年3月21日 下午2:23:01
     */
    public static String getDateFormatStr(String format) {
        Date toDaty = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(toDaty);
    }

    /**
     * 字符串填充 123，填充0到6位 得到 000123
     * @param str 需要填充的字符串
     * @param fix 填充内容
     * @param len 填充之后的长度
     * @return String 填充之后内容
     * @Author : pengjunhao. create at 2016年3月21日 下午2:24:08
     */
    public static String fixStr(String str, Character fix, int len) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = str.length(); i < len; i++) {
            stringBuilder.append(fix);
        }
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    /**
     * 处理特殊字符
     * @param tempValue 需要处理的字符串
     * @return String 处理之后字符串
     * @Author : pengjunhao. create at 2016年3月21日 下午2:24:44
     */
    public static String dealSepcialChars(String tempValue) {
        if (tempValue == null) {
            return tempValue;
        }
        tempValue = tempValue.replaceAll("'", "");
        tempValue = tempValue.replaceAll("`", "");
        tempValue = tempValue.replaceAll("\\(", "");
        tempValue = tempValue.replaceAll("\\)", "");
        tempValue = tempValue.replaceAll("%", "");
        return tempValue;
    }

    /**
     * 转换文件大小
     * @param fileS 文件
     * @return String
     * @Author : pengjunhao. create at 2016年3月21日 下午2:24:44
     */
    public static String formetFileSize(long fileS) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = decimalFormat.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = decimalFormat.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = decimalFormat.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = decimalFormat.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 判断一个字符串是否为空
     * <pre>
     * ProString.isEmpty(null)      = true
     * ProString.isEmpty("")        = true
     * ProString.isEmpty(" ")       = false
     * ProString.isEmpty("bob")     = false
     * ProString.isEmpty("  bob  ") = false
     * <pre>
     * @param str 要进行判断的字符串
     * @return true:字符串为空或null;false:字符串不为空或null;
     * @Author : pengjunhao. create at 2016年3月21日 下午2:25:41
     */
    public static boolean isEmpty(final String str) {
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断一个字符串不为空
     * <pre>
     * ProString.isNotEmpty(null)      = false
     * ProString.isNotEmpty("")        = false
     * ProString.isNotEmpty(" ")       = true
     * ProString.isNotEmpty("bob")     = true
     * ProString.isNotEmpty("  bob  ") = true
     * <pre>
     * @param str 要进行判断的字符串
     * @return true:字符串不为空或不为null
     * @Author : jk. create at 2016年3月18日 下午12:10:12
     */
    public static boolean isNotEmpty(final String str) {
        return StringUtils.isNotEmpty(str);
    }

    /**
     * 判断一个字符串是否为空
     * <pre>
     * ProString.isBlank(null)      = true
     * ProString.isBlank("")        = true
     * ProString.isBlank(" ")       = true
     * ProString.isBlank("bob")     = false
     * ProString.isBlank("  bob  ") = false
     * <pre>
     * @param str 要进行判断的字符串
     * @return true:字符串为空、null、"  "
     * @Author : ll. create at 2016年3月18日 下午1:45:06
     */
    public static boolean isBlank(final String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 判断一个字符串是否为空，如果str为"null"，也返回true
     * <pre>
     * ProString.isBlank(null)      = true
     * ProString.isBlank("null")    = true
     * ProString.isBlank("")        = true
     * ProString.isBlank(" ")       = true
     * ProString.isBlank("bob")     = false
     * ProString.isBlank("  bob  ") = false
     * <pre>
     * @param str 要进行判断的字符串
     * @return true:字符串为空、null、"  "
     * @Author : ll. create at 2016年3月18日 下午1:45:06
     */
    public static boolean isBlankOrNullString(final String str) {
        return StringUtils.isBlank(str) || str.equals("null");
    }

    /**
     * 判断一个字符串是否为空
     * <pre>
     * ProString.isNotBlank(null)      = false
     * ProString.isNotBlank("")        = false
     * ProString.isNotBlank(" ")       = false
     * ProString.isNotBlank("bob")     = true
     * ProString.isNotBlank("  bob  ") = true
     * <pre>
     * @param str 要进行判断的字符串
     * @return true:字符串不为空、不为null、不为" "
     * @Author : ll. create at 2016年3月18日 下午1:45:06
     */
    public static boolean isNotBlank(final String str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 判断一个字符串是否为空，且不能为null字符串
     * <pre>
     * ProString.isNotBlankOrNullStr(null)      = false
     * ProString.isNotBlankOrNullStr("null")    = false
     * ProString.isNotBlankOrNullStr("")        = false
     * ProString.isNotBlankOrNullStr(" ")       = false
     * ProString.isNotBlankOrNullStr("bob")     = true
     * ProString.isNotBlankOrNullStr("  bob  ") = true
     * <pre>
     * @param str 要进行判断的字符串
     * @return true:字符串不为空、不为null、不为" "
     * @Author : ll. create at 2016年3月18日 下午1:45:06
     */
    public static boolean isNotBlankOrNullStr(final String str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 将list集合转换为字符串以，分割
     * @param list
     * @return
     * @Author : zangyuandi. create at 2016年4月23日 上午9:53:58
     */
    public static String list2String(List<String> list) {
        if (!ProCollection.isEmpty(list)) {
            StringBuffer stringBuffer = new StringBuffer();
            for (String string : list) {
                stringBuffer.append(string + ",");
            }
            String result = stringBuffer.toString();
            result = result.substring(0, result.lastIndexOf(","));
            return result;
        }
        return null;
    }

    /**
     * 返回首字母大写的字符串
     * @param str
     * @return
     * @Author : jk. create at 2016年5月31日 下午4:43:13
     */
    public static String upperFirst(String str) {
        if (str != null) {
            if (str.length() > 0) {
                return str.substring(0, 1).toUpperCase() + str.substring(1);
            }
        }
        return str;
    }

    /**
     * 模板替换
     * @param tempStr 模板字符串
     * @param item 替换值的对象
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static String replaceParam(String tempStr, Object item) throws IllegalArgumentException,
                                                                   IllegalAccessException {
        Pattern p1 = Pattern.compile(PARAM_ALL);
        Pattern p2 = Pattern.compile(PARAM);
        Matcher m = p1.matcher(tempStr);
        Matcher m2;
        Set<String> set = new HashSet<String>();
        while (m.find()) {
            m2 = p2.matcher(m.group());
            if (m2.find()) {
                //获取属性名称
                set.add(m2.group());
            }
        }

        //根据属性名称找到属性的值  然后进行替换
        Field[] fs;
        Class<?> c = item.getClass();
        while (c.getName().indexOf("com.centuryeast.model") >= 0) {
            fs = c.getDeclaredFields();
            for (Field field : fs) {
                //如果存在可以替换的值
                if (set.contains(field.getName())) {
                    field.setAccessible(true); //设置些属性是可以访问的
                    tempStr = tempStr.replaceAll("#\\{" + field.getName() + "\\}",
                        field.get(item).toString());
                }
            }
            c = c.getSuperclass();
        }
        return tempStr;
    }
}
