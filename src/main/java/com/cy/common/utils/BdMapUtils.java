package com.cy.common.utils;

import com.cy.util.PairUtil;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.sin;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 企业信息</p>
 *
 * @author 郭亚斌
 * @Company 博视创诚
 * @data 2016-5-19
 */
public class BdMapUtils {

    private  static double  pi = 3.14159265358979324 * 3000.0 / 180.0;

    //地球平均半径
    private static final double EARTH_RADIUS = 6378137;
    //把经纬度转为度（°）
    private static double rad(double d){
        return d * Math.PI / 180.0;
    }
    /**
     *
     * @param gcjPoint
     * GCJ-02(火星)转BD-09（百度地图）
     */
    public static PairUtil<Double,Double> gcjTranscateBd(PairUtil<Double,Double> gcjPoint)
    {
        //注意经度在前，纬度在后

        //定义百度坐标经度变量
        double bdlonPoint;
        //定义百度坐标纬度变量
        double bdlatPoint;
        //获取传入的坐标值，第一个参数是经度，第二个参数是纬度
        double x =gcjPoint.getOne(), y = gcjPoint.getTwo();
        //计算公式
        double z = sqrt(x * x + y * y) + 0.00002 * sin(y * pi);
        //计算公式
        double theta = atan2(y, x) + 0.000003 * cos(x * pi);
        //算得转换后的百度的经度坐标
        bdlonPoint = z * cos(theta) + 0.0065;
        //算得转换后的百度的纬度坐标
        bdlatPoint = z * sin(theta) + 0.006;
        //最后返回转换后的最终的经纬度坐标
        PairUtil<Double,Double> finallyCount = new PairUtil< Double, Double >(bdlonPoint,bdlatPoint);
        return  finallyCount;
    }
    /**
     * @param startPoint
     * @param endPoint
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     */
    public static double getDistance(PairUtil<Double,Double> startPoint,PairUtil<Double,Double> endPoint){
        //注意经度在前，纬度在后

        //起点的经度
        double  startRadlng = rad(startPoint.getOne());
        //起点的纬度
        double startRadLat = rad(startPoint.getTwo());
        //终点的经度
        double  endRadlng = rad(endPoint.getOne());
        //终点的纬度
        double endRadLat = rad(endPoint.getTwo());
        //起点的经度减去终点的经度
        double lngCount = startRadlng - endRadlng;
        //起点的纬度减去终点的纬度
        double latCount = startRadLat - endRadLat;
        //计算公式
        double distence = 2 * Math.asin(
                Math.sqrt(
                        Math.pow(Math.sin(latCount/2),2)
                                + Math.cos(startRadLat)*Math.cos(endRadLat)*Math.pow(Math.sin(lngCount/2),2)
                )
        );
        distence = distence * EARTH_RADIUS;
        distence = Math.round(distence * 10000) / 10000;
        return distence;
    }


}
