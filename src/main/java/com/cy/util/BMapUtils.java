package com.cy.util;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Title: BMapUtils</p>
 * <p>Description: 百度地图工具类</p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-09-21 16:21
 */
public class BMapUtils {
    private final static String bMapKey = "3pkcvoxSY9cIcbv4wGL0mW3G" ;

    /**
     * 百度地图地址解析
     * @param address
     * @return
     */
    public static Map<String,Double> getLngAndLat(String address){
        Map<String,Double> map=new HashMap<String, Double>();
        String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak="+bMapKey;
        String json = loadJSON(url);
        JSONObject obj = JSONObject.fromObject(json);
        if(obj.get("status").toString().equals("0")){
            double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
            double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
            map.put("lng", lng);
            map.put("lat", lat);
            //System.out.println("经度："+lng+"---纬度："+lat);
        }else{
            //System.out.println("未找到相匹配的经纬度！");
        }
        return map;
    }
    /**
     * 百度地图反向地址解析
     * @param lng   经度
     * @param lat   纬度
     * @return
     */
    public static Map<String, String> getAddress(String lng, String lat) throws IOException {
        String url = "http://api.map.baidu.com/geocoder?" + bMapKey + "=您的密钥" +
                "&callback=renderReverse&location=" + lat
                + "," + lng + "&output=json";
        String json = loadJSON(url);
        System.out.println(json);
        Map<String,String> map = new HashMap<String,String>() ;

        JSONObject obj = JSONObject.fromObject(json);
        if(obj.get("status").toString().equals("OK")){
            String province     =obj.getJSONObject("result").getJSONObject("addressComponent").getString("province");
            String city         =obj.getJSONObject("result").getJSONObject("addressComponent").getString("city");
            String district     =obj.getJSONObject("result").getJSONObject("addressComponent").getString("district");
            String street       =obj.getJSONObject("result").getJSONObject("addressComponent").getString("street");
            String street_number=obj.getJSONObject("result").getJSONObject("addressComponent").getString("street_number");
            String address      =obj.getJSONObject("result").getString("formatted_address");

            map.put("province",province);
            map.put("city",city);
            map.put("district",district);
            map.put("street",street);
            map.put("street_number",street_number);
            map.put("address",address);
        }else{
            //System.out.println("未找到相匹配的经纬度！");
        }
        return map;

    }

    /**
     * 百度地图API 请求处理
     * @param url
     * @return
     */
    public static String loadJSON (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static void main(String[] args) throws IOException {

        Map<String,Double> map=BMapUtils.getLngAndLat("乌鲁木齐市");
        System.out.println("经度："+map.get("lng")+"---纬度："+map.get("lat"));

        Map<String,String> json = BMapUtils.getAddress(map.get("lng")+"",map.get("lat")+"");
        System.out.println("address :" + json.get("address"));
        String schoolCity = json.get("city") ;
        schoolCity = schoolCity.lastIndexOf("市") == schoolCity.length() - 1?schoolCity.substring(0,schoolCity.length()-1):schoolCity ;
        System.out.println("schoolCity :" + schoolCity);
    }

}