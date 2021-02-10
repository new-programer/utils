package site.ericgao.commonutil;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 集合相关的操作
 * @create jk
 * @createDate 2016年3月18日 下午12:12:16
 * @update EricGao
 * @updateDate 2016年3月21日15:20:39
 */
public class ProCollection {

    /**
     * 判断传入的集合是否为空或0个元素
     * @param c
     * @return  boolean; true 为空 ; false 不为空
     * @Author : jk. create at 2016年3月18日 下午1:10:38
     */
    public static boolean isEmpty(Collection<?> c) {
        return CollectionUtils.isEmpty(c);
    }

    /**
     * 判断一个Map对象是否为空
     * @param map {@link Map}
     * @return true：传入的Map对象为null或者为empty
     * @Author : EricGao. create at 2016年3月21日 下午2:45:36
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return CollectionUtils.isEmpty(Collections.singleton(map));
    }

    /**
     * 判断一个数组是否为空
     * @param arrays 数组
     * @return true ： 传入的数组为空
     * @Author : ll. create at 2016年4月23日 下午12:01:51
     */
    public static boolean isEmpty(Object[] arrays) {
        return ArrayUtils.isEmpty(arrays);
    }

    /**
     * 串接字符串
     * @param col 字符串集合
     * @param separate 串接符号
     * @return 串接之后的字符串
     * @Author : EricGao. create at 2016年7月5日 下午2:34:58
     */
    public static String join(Collection<String> col, String separate) {
        if (isEmpty(col)) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (String string : col) {
            stringBuffer.append(string).append(separate);
        }
        return stringBuffer.toString().substring(0, stringBuffer.length() - 1);
    }

    /**
     * 串接字符串
     * @param col 字符串集合
     * @param separate 串接符号
     * @return 串接之后的字符串
     */
    public static String list2String(Collection<Integer> col, String separate) {
        if (isEmpty(col)) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Integer integer : col) {
            stringBuffer.append(integer).append(separate);
        }
        return stringBuffer.toString().substring(0, stringBuffer.length() - 1);
    }

    /**
     * 串接字符串
     * @param arrays 字符串数组
     * @param separate 串接符号
     * @return 串接之后的字符串
     * @Author : EricGao. create at 2016年7月5日 下午2:34:58
     */
    public static String join(String[] arrays, String separate) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String string : arrays) {
            stringBuffer.append(string).append(separate);
        }
        return stringBuffer.toString().substring(0, stringBuffer.length() - 1);
    }

    /**
     * List转Map，通过反射获取给定字段的值，作为map的key。相同的key的数据会被覆盖
     * @param fieldName 需要用作key值的字段
     * @param list 需要转换的集合
     * @return 转换后的Map对象
     * @Author : EricGao. create at 2016年10月25日 上午10:38:52
     */
    public static Map<Object, Object> list2Map(String fieldName, List<?> list) {
        if (isEmpty(list)) {
            return null;
        }
        if (StringUtils.isBlank(fieldName)) {
            throw new IllegalArgumentException("fieldName不能为空");
        }
        Map<Object, Object> map = new HashMap<Object, Object>();
        Object fieldValue = null;
        for (Object item : list) {
            try {
                fieldValue = ReflectUtils.getValue(item, fieldName);
            } catch (Exception e) {
                throw new RuntimeException("通过反射获取字段值失败", e);
            }
            map.put(fieldValue, item);
        }
        return map;
    }

    /**
     * list转Map,通过反射获取给定字段的值，作为map的key,value为list,相同的key的数据会被添加进value
     * @param fieldName 需要用作key值的字段
     * @param list 需要转换的集合
     * @return
     */
    public static Map<Object, List<Object>> list2MapWithListValue(String fieldName, List<?> list){
//    	list判空
    	if (ProCollection.isEmpty(list)) {
            return null;
        }
//    	fieldName判空
        if (StringUtils.isBlank(fieldName)) {
            throw new IllegalArgumentException("fieldName不能为空");
        }
//      临时变量
        Map<Object, List<Object>> map = new HashMap<>();
        List<Object> tempList = new ArrayList<>();
        Object fieldValue = null;
//      遍历传入list
        for (Object item : list) {
            try {
//            	获取key
                fieldValue = ReflectUtils.getValue(item, fieldName);
//                包含则通过key获取list赋值临时list,再将value存入,最终一起存入value的list,不包含则新起一个key value存入map
                if (map.containsKey(fieldValue)) {
                	tempList = map.get(fieldValue);
                } else {
                	tempList = new ArrayList<>();
                }
                tempList.add(item);
                map.put(fieldValue, tempList);
            } catch (Exception e) {
                throw new RuntimeException("通过反射获取字段值失败", e);
            }
        }
        return map;
    }

    /**
     * 去除集合中的Null元素
     * @param col 需要去除null元素的集合
     * @Author : EricGao. create at 2016年10月25日 下午1:32:56
     */
    public static void removeNull(Collection<?> col) {
        Collection<?> nuCon = new Vector<Object>();
        nuCon.add(null);
        col.removeAll(nuCon);
    }

    /**
     * map的值转换成集合
     * @param map 需要转换的map
     * @return 值存放的集合
     * @Author : EricGao. create at 2017年1月15日 下午3:13:16
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List mapValue2ArrayList(Map map) {
        List list = new ArrayList();
        Set<Object> keySet = map.keySet();
        for (Object key : keySet) {
            list.add(map.get(key));
        }
        return list;
    }

    /**
     * 将字符串数组，拼接成如'1','2','3'格式的字符串
     * @param arrays 字符串数组
     * @return 拼接后，格式如'1','2','3'的字符串
     * @Author : EricGao. create at 2017年2月28日 下午2:16:42
     */
    public static String convert2Mult(String[] arrays) {
        if (isEmpty(arrays)) {
            return "";
        }
        return "'" + join(arrays, ",").replaceAll("'", "").replaceAll(",", "','") + "'";
    }

}
