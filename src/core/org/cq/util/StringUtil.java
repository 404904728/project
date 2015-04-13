package org.cq.util;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author hejian
 */
public class StringUtil {

    /**
     * 判断字符串是否为null或者空白字符或者空字符串
     */
    public static boolean isEmpty(Object str) {
        return str == null || str.toString().matches("\\s*");
    }

    /**
     * 验证是否手机号码
     *
     * @param str 需要验证的字符串
     * @return
     */
    public static boolean isMobilePhone(String str) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * json字符串转 map对象
     * @param str 
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonstrToMap(String str) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(str, Map.class);
        } catch (IOException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 对象转json字符串
     * @param o 
     * @return
     */
    public static String objTojsonstr(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(o);
			System.out.println(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonString;
	}
    
    public static Class getVoClass(Object vo) {
        final Class clazz = vo.getClass();
        return clazz.getName().indexOf("$") > 0 ? clazz.getSuperclass() : clazz;
    }
    

    /**
     * 文件长度友好显示
     */
    public static String fomatFileSize(long size) {
        if (size < 1024) {
            return size + "B";
        } else if (size < 1024 * 1024) {
            return size / 1024 + "K";
        } else {
            return size / 1024 / 1024 + "M";
        }
    }

}
