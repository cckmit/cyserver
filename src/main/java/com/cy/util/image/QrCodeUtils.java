package com.cy.util.image;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.base.action.AdminBaseAction;
import com.cy.system.Global;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.aspectj.util.FileUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 二维码生成工具类
 */
public class QrCodeUtils {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;


    /**
     * 生成二维码
     * @param
     * @return
     */



    public static String createQrCodeByValue(String value )throws Exception {
        //String decrypt = ConfigTools.decrypt(value);
        // 文件保存目录路径
        String savePath = Global.DISK_PATH;
        // 文件保存目录URL
        String saveUrl = Global.URL_DOMAIN;
        int width = 300;
        int height = 300;
        //二维码的图片格式
        String format = "gif";
        Hashtable hints = new Hashtable();
        //内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(value,
                BarcodeFormat.QR_CODE, width, height, hints);
        //生成二维码
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                   image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
            }
        }

        String dirName = "/image";
        // 创建文件夹
        savePath += dirName + "/";
        saveUrl += dirName + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        savePath += ymd + "/";
        saveUrl += ymd + "/";
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000);
        File uploadedFile = new File(savePath, newFileName + "." + format);
        String url =saveUrl + newFileName + "." + format;
        ImageIO.write(image, format,uploadedFile);
        return url ;
    }
}
