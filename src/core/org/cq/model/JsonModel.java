package org.cq.model;

public class JsonModel {

	// 0：成功 1：信息 2：警告 3：错误 默认为成功
	public static int SUCCESS = 0;
	public static int ERROR = 1;

	/**
	 * 返回json字符串的封装
	 * 
	 * @param msg
	 *            返回成功提示(默认提示，操作成功)
	 * @return
	 */
	public static String success(String... msg) {
		return write(SUCCESS, msg.length == 0 ? "操作成功" : msg[0], null, null);
	}

	/**
	 * 返回json字符串的封装
	 * 
	 * @param msg
	 *            返回成功提示(默认提示，操作成功)
	 * @param id
	 *            实体的ID
	 * @return
	 */
	public static String success(Long id, String... msg) {
		return write(SUCCESS, msg.length == 0 ? "操作成功" : msg[0], id, null);
	}

	/**
	 * 返回json字符串的封装
	 * 
	 * @param msg
	 *            返回错误提示(默认提示，系统错误)
	 * @return
	 */
	public static String error(String... msg) {
		String[] msgs = msg;
		return write(ERROR, msgs.length == 0 ? "系统错误" : msg[0], null, null);
	}

	/**
	 * 基础返回
	 * 
	 * @param type
	 *            类型（成功，失败）
	 * @param msg
	 *            提示信息
	 * @param id
	 *            实体ID
	 * @param msgId
	 *            其他存储
	 * @return
	 */
	public static String write(int type, String msg, Long id, String msgId) {
		return "{\"type\":" + type + ",\"msg\":\"" + msg + "\",\"id\":" + id
				+ ",\"msgId\":" + msgId + "}";
	}
}