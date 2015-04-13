package org.cq.util;

import com.jfinal.log.Logger;

public class LogUtil {
	public static void info(String msg) {
		Logger.getLogger("").info(msg);
	}
}
