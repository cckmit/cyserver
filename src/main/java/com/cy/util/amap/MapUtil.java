package com.cy.util.amap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by hp on 2017/5/25.
 */
public class MapUtil {

    //个人百度地图key（数据请求量大需升级企业版）
    private static String key="141672994f4f2ce9cd6d63f4d1da0c18";

    /**
     * 获取关键字输入后的提示
     * @param tips（请求参数实体）
     * @return
     * @throws Exception
     */
    private static String getTips(Tips tips) throws Exception{
        String result = urlInterface("http://restapi.amap.com/v3/assistant/inputtips?key="+key+tips.getParams());
        return result;
    }

    /**
     * 步行规划方案
     * @param origin（起始点经纬度）
     * @param destination（目的地经纬度）
     * @return
     * @throws Exception
     */
    private static String getWalkPlans(String origin ,String destination) throws Exception{
        String result = urlInterface("http://restapi.amap.com/v3/direction/walking?key="+key+"&origin="+origin+"&destination="+destination);
        return result;
    }

    /**
     * 公交规划方案
     * @param busParams（请求参数实体）
     * @return
     * @throws Exception
     */
    private static String getTransitPlans(BusParams busParams) throws Exception{
        String result = urlInterface("http://restapi.amap.com/v3/direction/transit/integrated?key="+key+busParams.getParams());
        return result;
    }

    /**
     * 驾车方案规划
     * @param origin（起始点经纬度）
     * @param destination（目的地经纬度）
     * @return
     * @throws Exception
     */
    private static String getPlans(String origin ,String destination) throws Exception{
        String result = urlInterface("http://restapi.amap.com/v3/direction/driving?key="+key+"&origin="+origin+"&destination="+destination);
        return result;
    }

    /**
     * 距离测量
     *
     * @param origin（起始点经纬度）
     * @param destination（目的地经纬度）
     * @param type（0：直线距离，1：驾车距离，2：公交距离，3：步行距离）
     * @return
     * @throws Exception
     */
    private static String getDistance(String origin ,String destination,String type) throws Exception{
        String result = urlInterface("http://restapi.amap.com/v3/distance?key="+key+"&origin="+origin+"&destination="+destination+"&type="+type);
        return result;
    }

    /**
     * 根据地址获取经纬度
     * @param address（输入地址）
     * @return
     * @throws Exception
     */
    private static String getGeo(String address ) throws Exception{
        String result = urlInterface("http://restapi.amap.com/v3/geocode/geo?key="+key+"&address="+address);
        return result;
    }

    /**
     * 根据经纬度获取地址信息
     * @param location（输入经纬度）
     * @return
     * @throws Exception
     */
    private static String getRegeo(String location ) throws Exception{
        String result = urlInterface("http://restapi.amap.com/v3/geocode/regeo?key="+key+"&location="+location);
        return result;
    }

    /**
     * 谷歌经纬度转化百度经纬度
     * @param location（经纬度）
     * @return
     * @throws Exception
     */
    private static String turnGeo(String location ) throws Exception{
        String result = urlInterface("http://restapi.amap.com/v3/assistant/coordinate/convert?key="+key+"&locations="+location+"&coordsys=baidu");
        return result;
    }
    /**
     * url接口json接收
     *
     * @param pathUrl
     * @throws Exception
     */
    private static String urlInterface(String pathUrl)
            throws Exception{
        StringBuilder json = null;

        try {
            json = new StringBuilder();
            URL url = new URL(pathUrl);
            URLConnection connection = url.openConnection();
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    url.openStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                json.append(line + " ");
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static void main(String[] args) throws Exception {
        Tips tips =new Tips();
        tips.setKeywords("肯德基");
        tips.setCity("北京");
        tips.setType("050301");
        tips.setDatatype("all");
        String result = turnGeo("116.355588,39.81001");
        System.out.println(result);
    }

}
