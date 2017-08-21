package com.cy.util.msgPushUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * @desc Json处理java对象到json格式转换的日期处理类
 * 
 */
public class JsonDateValueProcessor implements JsonValueProcessor {
	private String datePattern = "yyyy-MM-dd HH:mm:ss";// 日期格式

	public JsonDateValueProcessor() {
		super();
	}

	// 构造函数
	public JsonDateValueProcessor(String format) {
		super();
		this.datePattern = format;
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		return process(value);
	}

	private Object process(Object value) {
		try {
			if (value instanceof Date) {
				SimpleDateFormat sdf = new SimpleDateFormat(datePattern,
						Locale.UK);
				return sdf.format((Date) value);
			}
			return value == null ? "" : value.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePaterns) {
		this.datePattern = datePaterns;
	}

//	public static void main(String[] args) {
//
//		//ArrayList list = new ArrayList();
//		Users user = new Users();
//		Date date = new Date();
//		user.setDate(date);
//		//list.add(user);
//		System.out.println(user.toString());
//		JsonConfig jsonConfig = new JsonConfig();
//
//		// 设置javabean中日期转换时的格式
//		jsonConfig.registerJsonValueProcessor(Date.class,
//				new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
//		// 获取json数组
//		JSONObject jsonArray = JSONObject.fromObject(user, jsonConfig);
//
//		System.out.println(jsonArray.toString());
//	}
//
//	//must be a POJO
//	public static class Users {
//		private Date date;
//
//		public Date getDate() {
//			return date;
//		}
//
//		public void setDate(Date date) {
//			this.date = date;
//		}
//	}
}