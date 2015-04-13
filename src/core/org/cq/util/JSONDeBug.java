package org.cq.util;

import org.codehaus.jackson.map.ObjectMapper;


/**
 * 返回json数据时的调试。 主要是ajax请求报500错误
 * 
 * @author 何建
 * 
 */
public class JSONDeBug {

	public static String vaildJson(Object o) {
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
}
