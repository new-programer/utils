package site.ericgao.commonutil;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * @create ll
 * @createDate 2016年4月14日 上午9:17:51
 * @update 
 * @updateDate
 */
public class ReflectUtils {

    /**
     * 通过字段名从对象或对象的父类中得到字段的值
     * @param object 对象实例
     * @param fieldName 字段名
     * @return 字段对应的值
     * @throws Exception
     * @Author : ll. create at 2016年4月14日 上午9:18:19
     */
    public static Object getValue(Object object, String fieldName) throws Exception {
        if (object == null) {
            return null;
        }
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(object);
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  
            }
        }

        return null;
    }

    /**
     * 通过字段名从对象或对象的父类中得到字段的值（调用字典的get方法）
     * @param object 对象实例
     * @param fieldName 字段名
     * @return 字段对应的值
     * @throws Exception
     * @Author : ll. create at 2016年4月14日 上午9:18:19
     */
    public static Object getValueOfGet(Object object, String fieldName) throws Exception {
        if (object == null) {
            return null;
        }
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);

                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                //获得get方法  
                Method getMethod = pd.getReadMethod();
                //执行get方法返回一个Object
                return getMethod.invoke(object);
               
       			 
            } catch(InvocationTargetException e){	 
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  
            }
        }

        return null;
    }

    /**
     * 通过字段名向该对象或者父类的属性赋值
     * @param object 对象实例
     * @param fieldName 字段名
     * @return 字段对应的值
     * @throws Exception
     * @Author : yjk. create at 2017年11月14日 上午9:18:19
     */
    public static boolean setValueOfGetIncludeObjectFeild(Object object, String fieldName, Object o)
                                                                                         throws Exception {
    	boolean flag=true;
        if (object != null&&!StringUtils.isBlank(fieldName)) {
	        Field field = null;
	        Class<?> clazz = object.getClass();
	        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
	            try {
	                field = clazz.getDeclaredField(fieldName);
	                if(field!=null){
	                    field.setAccessible(true);
	                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
	                    //获得get方法  
	                    Method setMethod = pd.getWriteMethod();
	                    //执行get方法返回一个Object
	                    setMethod.invoke(object,o);
	                    flag = true;
	                    break;
	                }
	            } catch(InvocationTargetException e){
	            	
	            } catch (Exception e) {
	                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
	                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  
	            }
	        }
        }
        return flag;
    }

    /**
     * 通过字段名从对象或对象的父类中得到字段的值（调用字典的get方法，可以取出复杂的对象的值）
     * @param object 对象实例
     * @param fieldName 字段名
     * @return 字段对应的值
     * @throws Exception
     * @Author : ll. create at 2016年4月14日 上午9:18:19
     */
    public static Object getValueOfGetIncludeObjectFeild(Object object, String fieldName) throws Exception {
                                                                                        

        if (object == null) {
            return null;
        }
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                if (fieldName.contains(".")) {
                    // 如：operatorUser.name、operatorUser.org.name，递归调用
                    String[] splitFiledName = fieldName.split("\\.");
                    return getValueOfGetIncludeObjectFeild(
                        getValueOfGetIncludeObjectFeild(object, splitFiledName[0]),
                        splitFiledName[1]);
                }
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);

                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                //获得get方法  
                Method getMethod = pd.getReadMethod();
                //执行get方法返回一个Object
                return getMethod.invoke(object);
            } catch(InvocationTargetException e){	
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  
            }
        }

        return null;
    }

    /**
     * 根据传入对象重新创建一个对象
     * @param object
     * @return
     * @throws Exception
     * @Author : jk. create at 2016年5月31日 下午3:10:42
     */
    public static Object createSameObject(Object object, boolean createChild) throws Exception {
        Class<?> clazz = object.getClass();
        Object obj = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.getType().getName().equals("java.util.List")) {
                    if (createChild) {
                        Method method = getReadMethod(f.getName(), clazz);
                        if (method != null)
                            f.set(obj, method.invoke(object));
                    }
                } else {
                    Method method = getReadMethod(f.getName(), clazz);
                    if (method != null)
                        f.set(obj, method.invoke(object));
                }
            }
        }
        return obj;
    }

    private static Method getReadMethod(String name, Class<?> clazz) {
        String methodName = "get" + ProString.upperFirst(name);
        try {
            return clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException | SecurityException e) {
            return null;
        }
    }

}
