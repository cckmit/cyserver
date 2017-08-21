package com.cy.common.utils;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by cha0res on 4/28/17.
 */
public class LoadFont {

    // 正式部署用
    private static final String ROOT_DIR =  System.getProperty("user.dir") + "/webapps/ROOT";
    // 本地测试用
    // private static final String ROOT_DIR =  System.getProperty("user.dir") + "/src/main/webapp";
    private static final String LISHU_DIR = ROOT_DIR+"/fonts/FZLB.ttf";
    private static final String BIAOSONT_DIR = ROOT_DIR+"/fonts/FZBS.ttf";

    private static Font loadFont(String fontFileName, float fontSize)  //第一个参数是外部字体名，第二个是字体大小
    {
        try
        {
            File file = new File(fontFileName);
            FileInputStream aixing = new FileInputStream(file);
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);
            Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
            aixing.close();
            return dynamicFontPt;
        }
        catch(Exception e)//异常处理
        {
            e.printStackTrace();
            return new Font("宋体", Font.PLAIN, 14);
        }
    }

    /**
     * 方正隶书
     * @param size
     * @return
     */
    public static Font LiShu(int size){
        return LoadFont.loadFont(LISHU_DIR, size);
    }

    /**
     * 方正标宋
     * @param size
     * @return
     */
    public static Font BiaoSong(int size){
        return LoadFont.loadFont(BIAOSONT_DIR, size);
    }
}
