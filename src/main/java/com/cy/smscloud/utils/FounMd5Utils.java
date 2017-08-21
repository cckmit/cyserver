package com.cy.smscloud.utils;

import com.cy.common.utils.JsonUtils;
import com.cy.common.utils.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by PengLv on 2017/4/14.
 */
public class FounMd5Utils {

    public static String founMd5(String inStr) {
        String outStr = null;
        if (inStr == null) {
            outStr = null;
        } else if ("".equals(inStr)) {
            outStr = "";
        } else {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(inStr.getBytes());
                byte b[] = md.digest();
                StringBuffer buf = new StringBuffer();
                for (int i = 0; i < b.length; i++) {
                    int c = b[i] >>> 4 & 0xf;
                    buf.append(Integer.toHexString(c));
                    c = b[i] & 0xf;
                    buf.append(Integer.toHexString(c));
                }
                outStr = buf.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return outStr;
    }

    public static String getFounMd5(Map map) throws Exception {
        String md5 = null;
        if (map ==null || map.isEmpty()){
            return null;
        }
        String json = JsonUtils.obj2json(map);
        md5 = founMd5(json);
        System.out.println("cysys--------第一次"+md5);
        System.out.println("cysys--------第一次"+json);
        String replace = md5.replace("a", "d");
        replace = replace.replace("5", "9");
        String createDate = (String) map.get("createDate");
        if (StringUtils.isBlank(createDate)){
            return "创建时间为空";
        }
        replace =replace+"chuangyou"+createDate;
        md5 = founMd5(replace);

        return md5;
    }
public static void  main (String[] a){
        String s ="{\"command\":\"618\",\"content\":{\"donorName\":\"窗友科技\",\"payMoney\":15.0,\"donorType\":\"0\",\"name\":\"赵六\",\"jsonMd5\":\"10b16483eede445e0927d06a5281f1\",\"createDate\":\"2017-04-19 11:19:13\",\"donorNum\":\"12345678901\",\"founProject\":\"f770c2f9532f432ea71b106bac4eb161\"}";

    try {
        String md5 =JsonUtils.obj2json(s);
        md5=founMd5(md5);
        String replace = md5.replace("a", "d");
        replace = replace.replace("5", "9");
        String createDate = "2017-04-19 11:19:13";
        replace =replace+"chuangyou"+createDate;
        md5 = founMd5(replace);
        System.out.print(md5);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
