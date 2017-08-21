package com.cy.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;
import java.util.regex.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.dept.action.DeptAction;
import com.cy.system.Global;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class WebUtil {

	public static String MDHM = "MM-dd HH:mm";
	public static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static String YMDHMSS = "yyyy-MM-dd HH:mm:ss:SSS";
	public static String YMD = "yyyy-MM-dd";
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	private static final Logger logger = Logger.getLogger(WebUtil.class);

	public static String trim(Object obj) {

		if (String.class.isInstance(obj)) {
			String str = String.valueOf(obj);

			if (str != null) {
				return str.trim();
			} else {
				return "";
			}
		} else {
			Date date = (Date) obj;

			if (date == null) {
				return "";
			} else {
				return formatDateByPattern(date, YMDHMS);
			}
		}

	}
	
	public static boolean isNull(String str){
		return str==null?true:str.trim().length()==0;
	}
	
	
	public static boolean isEmpty(String str) {
		return trim(str).isEmpty();
		//return str==null?true:str.isEmpty();
	}

	public static <E> boolean isEmpty(List<E> list) {
		if (list == null) {
			return true;
		} else if (list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static Date formatDateObjectByPattern(String date, String pattern) {
		try {
			if (isEmpty(date)) {
				return new Date();
			}

			SimpleDateFormat sdf = new SimpleDateFormat(pattern);

			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return new Date();
	}

	public static String formatDateByPattern(String date, String pattern) {
		try {
			if (isEmpty(date)) {
				return "";
			}

			SimpleDateFormat sdf = new SimpleDateFormat(pattern);

			return sdf.format(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static String formatDateByPattern(Date date, String pattern) {
		if (date == null) {
			return "";
		} else {
			return new SimpleDateFormat(pattern).format(date);
		}
	}

	/**
	 * 獲取目標時區的時間
	 * @param targetTimeZone
	 * @return
	 */
	private static Date getDateForTargetTimeZone(TimeZone targetTimeZone) {

		//當前時區
		TimeZone sourceTimeZone = Calendar.getInstance().getTimeZone();

		//換算成目標時區時間
		Long targetTime = Calendar.getInstance().getTime().getTime() - sourceTimeZone.getRawOffset() + targetTimeZone.getRawOffset();

		return new Date(targetTime);
	}

	/**  
	 * 计算输入的日期与当前时间已失去的时长
	 * @param date 输入的一个已过去的时间
	 * @return 描述已失去的时长的字符串
	 */
	public static String pastTime(Date date) {

		long day = 0;
		long hour = 0;
		long min = 0;

		String pastTime = "";

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS);

			Date d1 = sdf.parse(sdf.format(TimeZoneUtils.getDate()));
			Date d2 = sdf.parse(sdf.format(date));

			Calendar cal = Calendar.getInstance();
			cal.setTime(d1);
			long time1 = cal.getTimeInMillis();//System.out.println("time1=" + time1);         
			cal.setTime(d2);
			long time2 = cal.getTimeInMillis();//System.out.println("time2=" + time2);         

			long diff = 0;

			/*
			
			if(time1<time2) 
			{  
			    diff = time2 - time1;  
			} 
			else 
			{  
			    diff = time1 - time2;  
			}
			 */

			diff = time1 - time2;

			//System.out.println("diff=" + diff);
			day = (diff) / (1000 * 3600 * 24);
			//System.out.println("day=" + day);
			if (day == 0) {
				hour = (diff / (60 * 60 * 1000) - day * 24);
				//System.out.println("hour=" + hour);
				if (hour == 0) {
					min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
					//System.out.println("min=" + min);

					if (min <= 0) {
						pastTime = "刚刚";
					} else {
						pastTime = min + "分钟前";
					}

				} else if (hour < 24) {
					pastTime = hour + "小时前";
				}
			}
			if (day == 1) {
				pastTime = "昨天";
			} else if (day > 1) {
				pastTime = day - 1 + "天前";
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pastTime;
	}

	/**  
	 * 计算两个日期之间相差的天数  
	 * @param smdate 较小的时间 
	 * @param bdate  较大的时间 
	 * @return 相差天数 
	 */
	public static int daysBetween(Date smdate, Date bdate) {

		long between_days = 0;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date d1 = sdf.parse(sdf.format(smdate));
			Date d2 = sdf.parse(sdf.format(bdate));

			Calendar cal = Calendar.getInstance();
			cal.setTime(d1);
			long time1 = cal.getTimeInMillis();
			cal.setTime(d2);
			long time2 = cal.getTimeInMillis();

			between_days = (time2 - time1) / (1000 * 3600 * 24);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Integer.parseInt(String.valueOf(between_days));
	}

	/** 
	 *字符串的日期格式的计算 
	 */
	public static int daysBetween(String smdate, String bdate) {
		long between_days = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			between_days = (time2 - time1) / (1000 * 3600 * 24);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static String doubleTrans(double d) {
		if (Math.round(d) - d == 0) {
			return String.valueOf((long) d);
		}
		return String.valueOf(d);
	}

	public static String getIpAddr(HttpServletRequest request) {
		//logger.info("**************************************************");

		//logger.info(new SimpleDateFormat("yyyy-MM-dd H:m:s.S").format(new java.util.Date()));

		String ip = request.getHeader("X-Forwarded-For");

		//logger.info("X-Forwarded-For=" + ip);

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			//logger.info("Proxy-Client-IP=" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			//logger.info("WL-Proxy-Client-IP=" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			//logger.info("HTTP_CLIENT_IP=" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			//logger.info("HTTP_X_FORWARDED_FOR=" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			//logger.info("RemoteAddr=" + ip);
		}

		//logger.info("**************************************************");
		return ip;
	}

	/** --将字符串转为int-- * */
	public static int toInt(String str) {
		int flag = 0;
		try {
			flag = Integer.valueOf(str);
		} catch (NumberFormatException ex) {

		}
		return flag;
	}

	/** --将字符串转为long-- * */
	public static long toLong(String str) {
		long flag = 0;
		try {
			flag = Long.valueOf(str);
		} catch (NumberFormatException ex) {

		}
		return flag;
	}
	
	/** --将字符串转为double-- * */
	public static double toDouble(String str) {
		double flag = 0.0;
		try {
			flag = Double.valueOf(str);
		} catch (NumberFormatException ex) {

		}
		return flag;
	}

	/** --获得客户端浏览器-- **/
	public static String getNavigatorInfo(HttpServletRequest request) {
		try {
			String info = request.getHeader("user-agent");
			if (info.indexOf("MSIE") != -1) {
				return info.substring(info.indexOf("MSIE"), info
						.indexOf("MSIE") + 8);
			} else if (info.indexOf("Firefox") != -1) {
				return info.substring(info.indexOf("Firefox"), info.length());
			} else if (info.indexOf("Chrome") != -1) {
				return info.substring(info.indexOf("Chrome"), info.length());
			} else if (info.indexOf("Opera") != -1) {
				return info.substring(info.indexOf("Opera"), info
						.indexOf("Opera") + 11);
			} else if (info.indexOf("Lunascape") != -1) {
				return info.substring(info.indexOf("Lunascape"), info.length());
			} else if (info.indexOf("AppleWebKit") != -1) {
				return info.substring(info.indexOf("AppleWebKit"), info
						.length());
			} else {
				return "其他";
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取客户端操作系统信息，目前只匹配Win 7、WinXP、Win2003、Win2000、MAC、WinNT、Linux、Mac68k、Win9x
	 * @param userAgent request.getHeader("user-agent")的返回值
	 * @return
	 */
	public static String getClientOS(String userAgent) {
		String cos = "unknow os";

		Pattern p = Pattern.compile(".*(Windows NT 6\\.1).*");
		Matcher m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Win 7";
			return cos;
		}

		p = Pattern.compile(".*(Windows NT 5\\.1|Windows XP).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "WinXP";
			return cos;
		}

		p = Pattern.compile(".*(Windows NT 5\\.2).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Win2003";
			return cos;
		}

		p = Pattern.compile(".*(Win2000|Windows 2000|Windows NT 5\\.0).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Win2000";
			return cos;
		}

		p = Pattern.compile(".*(Mac|apple|MacOS8).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "MAC";
			return cos;
		}

		p = Pattern.compile(".*(WinNT|Windows NT).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "WinNT";
			return cos;
		}

		p = Pattern.compile(".*Linux.*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Linux";
			return cos;
		}

		p = Pattern.compile(".*(68k|68000).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Mac68k";
			return cos;
		}

		p = Pattern
				.compile(".*(9x 4.90|Win9(5|8)|Windows 9(5|8)|95/NT|Win32|32bit).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Win9x";
			return cos;
		}

		return cos;
	}

	/**
	 * 用MD5加密

	 * @return String resultString 加密后的字符串
	 */
	public static String MD5Encode(String originString) {
		String resultString = null;
		try {
			resultString = new String(originString);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {

		}
		return resultString;
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String encodeUTF8(String content) {
		String str = "";
		try {
			str = URLEncoder.encode(content, "utf-8");
		} catch (Exception e) {
			str = e.toString();
		}
		return str;
	}

	public static String decodeUTF8(String content) {
		String str = "";
		try {
			str = URLDecoder.decode(content, "utf-8");
		} catch (Exception e) {
			str = e.toString();
		}

		return str;
	}

	/**--按比例生成缩微图 
	 * 小图:imgSize="100*80"   
	 * 大图:imgSize="320*200"--**/
	public static void getThumb(String imgSize, boolean proportion,
			String inputPath, String outPath) {
		//初始高宽100
		int initWidth = 100;
		int initHeight = 100;
		
		int imgWidth = WebUtil.toInt(imgSize.substring(0, imgSize.indexOf("*")));
		int imgHeight = WebUtil.toInt(imgSize.substring(imgSize.indexOf("*") + 1, imgSize.length()));
		if (imgWidth == 0) {
			imgWidth = initWidth;
		}
		if (imgHeight == 0) {
			imgHeight = initHeight;
		}
		int newWidth = 0;
		int newHeight = 0;

		try {
			//获得源文件
			logger.error(inputPath);
			File file = new File(inputPath);
			if (!file.exists()) {
				return;
			}
			Image img = ImageIO.read(file);
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				System.out.println(" can't read,retry!" + "<BR>");
				return;
			}
			if (proportion) {
				// 为等比缩放计算输出的图片宽度及高度   
				double rate1 = ((double) img.getWidth(null))
						/ (double) imgWidth;
				double rate2 = ((double) img.getHeight(null))
						/ (double) imgHeight;
				// 根据缩放比率大的进行缩放控制   
				double rate = rate1 > rate2 ? rate2 : rate1;
				newWidth = (int) (((double) img.getWidth(null)) / rate);
				newHeight = (int) (((double) img.getHeight(null)) / rate);
			} else {
				newWidth = imgWidth;
				newHeight = imgHeight;
			}
			BufferedImage tag = new BufferedImage((int) newWidth,
					(int) newHeight, BufferedImage.TYPE_INT_RGB);

			/* 
			 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
			 * 优先级比速度高 生成的图片质量比较好 但速度慢 
			 */
			tag.getGraphics().drawImage(
					img.getScaledInstance(newWidth, newHeight,
							Image.SCALE_SMOOTH), 0, 0, null);
			FileOutputStream out = new FileOutputStream(outPath);
			// JPEGImageEncoder可适用于其他图片类型的转换   
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/** --主键生成策略,通过UUID,随机数也可以通过这么生成-- **/
	public static String getPrimaryKey() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
		UUID uuid = UUID.randomUUID();
		return dateformat.format(new Date())
				+ uuid.toString().replaceAll("-", "").substring(0, 18);
	}

	public static boolean isNumeric(String str) {

		if (!WebUtil.isEmpty(str)) {
			Pattern pattern = Pattern.compile("[0-9]*");
			return pattern.matcher(str.trim()).matches();
		} else {
			return false;
		}

	}

	public static String getPictureByType(String picture, String pictureType) {
		StringBuilder pictureStringBuilder = new StringBuilder();

		pictureStringBuilder.append(picture.substring(0, picture.length() - 4));
		pictureStringBuilder.append("_");
		pictureStringBuilder.append(pictureType);
		pictureStringBuilder.append(picture.substring(picture.length() - 4));

		return pictureStringBuilder.toString();
	}

	public static String getIcon(int iconsId) {
		String[] icons = new String[20];
		icons[0] = "picture_0.png";
		icons[1] = "picture_1.png";
		icons[2] = "picture_2.png";
		icons[3] = "picture_3.png";
		icons[4] = "picture_4.png";
		icons[5] = "picture_5.png";
		icons[6] = "picture_6.png";
		icons[7] = "picture_7.png";
		icons[8] = "picture_8.png";
		icons[9] = "picture_9.png";
		icons[10] = "picture_10.png";
		icons[11] = "picture_11.png";
		icons[12] = "picture_12.png";
		icons[13] = "picture_13.png";
		icons[14] = "picture_14.png";
		icons[15] = "picture_15.png";
		icons[16] = "picture_16.png";
		icons[17] = "picture_17.png";
		icons[18] = "picture_18.png";
		icons[19] = "picture_19.png";

		return "../img/icons/" + icons[iconsId];
	}

	/**
	 * 读取某个文件夹下的所有文件
	 */
	public static boolean readfile(String filepath)
			throws FileNotFoundException, IOException {
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
				//System.out.println("文件");
				System.out.println("path=" + file.getAbsolutePath());
				String path = file.getAbsolutePath();
				String fileExt = path.substring(path.lastIndexOf("."));
				String temp = path.substring(0, path.lastIndexOf("."));
				WebUtil.getThumb("100*80", true, path, temp + "_MIN" + fileExt);
				//高清图
				WebUtil.getThumb("320*200", true, path, temp + "_MAX"+ fileExt);
			} else if (file.isDirectory()) {
				//System.out.println("文件夹");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						System.out.println("path=" + readfile.getAbsolutePath());
						String path = readfile.getAbsolutePath();
						String fileExt = path.substring(path.lastIndexOf("."));
						String temp = path.substring(0, path.lastIndexOf("."));
						WebUtil.getThumb("100*80", true, path, temp + "_MIN"+ fileExt);
						//高清图
						WebUtil.getThumb("320*200", true, path, temp + "_MAX"+ fileExt);
					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i]);
					}
				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return true;
	}

	/**--读取属性文件--**/
	public static String getPropertyParams(String key, String fileName) {
		Properties keyWordProperties = null;
		try {
			keyWordProperties = PropertiesLoaderUtils
					.loadAllProperties(fileName);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return keyWordProperties.getProperty(key);
	}

	/**--64base加密--**/
	public static String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	/**--64base解密--**/
	public static String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/** 
	 * 生成以中心点为中心的四方形经纬度 
	 *  
	 * @param lat 纬度
	 * @param lon 精度
	 * @param raidus 半径（以米为单位） 
	 * @return 
	 */
	public static double[] getAround(double mu_longitud, double mu_latitude,int raidus) {

		Double latitude = mu_latitude;
		Double longitude = mu_longitud;

		Double degree = (24901 * 1609) / 360.0;
		double raidusMile = raidus;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		Double minLat = latitude - radiusLat;
		Double maxLat = latitude + radiusLat;

		Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		Double minLng = longitude - radiusLng;
		Double maxLng = longitude + radiusLng;
		return new double[] { minLng,maxLng,minLat, maxLat};
	}

	/**  
	 * 计算中心经纬度与目标经纬度的距离（米）  
	 *   
	 * @param centerLon  
	 *            中心精度  
	 * @param centerLan  
	 *            中心纬度  
	 * @param targetLon  
	 *            需要计算的精度  
	 * @param targetLan  
	 *            需要计算的纬度  
	 * @return 米  
	 */
	/*public static double distance(double centerLon, double centerLat,double targetLon, double targetLat) {

		double jl_jd = 102834.74258026089786013677476285;// 每经度单位米;    
		double jl_wd = 111712.69150641055729984301412873;// 每纬度单位米;    
		double b = Math.abs((centerLat - targetLat) * jl_jd);
		double a = Math.abs((centerLon - targetLon) * jl_wd);
		return Math.sqrt((a * a + b * b));
	}*/

	public static double distance( double lng1, double lat1, double lng2, double lat2)
	{
		double radLat1 = lat1 * Math.PI / 180.0;
		double radLat2 = lat2 * Math.PI / 180.0;
		double a = radLat1 - radLat2;
		double b = (lng1-lng2)* Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
				Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * 6378137.0;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	/**--获得图片的宽高即 多少*多少--*
	 * @throws IOException */
	public static String getImgeWidthAndHeight(File file) throws IOException{
		Image src = javax.imageio.ImageIO.read(file); //构造Image对象
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		return width+"x"+height;
	}
	
	/**--判断是否为手机号--**/
	public static boolean isMobileNO(String mobiles) {
		if (mobiles == null) {
			return false;
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	/**--将数组转为String用逗号分割--**/
	public static String arrayToString(List<String> list){
		String str = "";
		for(int i=0;i<list.size();i++){
			if(i<list.size()-1){
				str+=list.get(i)+",";
			}else{
				str+=list.get(i);
			}
		}
		return str;
	}

	/*方法二：推荐，速度最快
	  * 判断是否为整数 
	  * @param str 传入的字符串 
	  * @return 是整数返回true,否则返回false 
	*/
	 public static boolean isInteger(String str) {  
	        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	        return pattern.matcher(str).matches();  
	  }
	 
	/**
	 * 检查文件路径
	 * @param url 文件相对路径
	 * @return
	 */
	public static boolean checkUrl(String url){
		boolean isExists = false ;
		String fileUrl = Global.DISK_PATH + url ;
		System.out.println("-----------> 文件存放路径："+fileUrl);
		File file = new File(fileUrl) ;
		if(file.exists()) {
			isExists = true ;
		}
		return isExists;
	}

	public static void main(String[] args) {
	}

}
