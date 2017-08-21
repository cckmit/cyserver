package com.cy.core.donation.utils;

import com.cy.common.utils.LoadFont;
import com.cy.common.utils.NumberToCN;
import com.cy.common.utils.StringUtils;
import com.cy.core.dict.entity.Dict;
import com.cy.core.dict.utils.DictUtils;
import com.cy.core.donation.entity.Donation;
import com.cy.system.Global;
import com.cy.util.IdGen;
import com.google.common.collect.Maps;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by cha0res on 2/23/17.
 */
public class Certificate {
    // 窗友的证书
    private static void createPic(String backFilePath, String logoFilePath, String signetFilePath, String targetFileName,
                                  String userName, String projectName, String schoolName,String foundation,String goodsName,String time, Double money) {
        ImageIcon backImageIcon = new ImageIcon(backFilePath);      // 获取背景图片

        Image backImage = backImageIcon.getImage();                 // Image可以获得图片的属性信息


        int width = backImage.getWidth(null);
        int height = backImage.getHeight(null);
        // 为画出与源图片的相同大小的图片（可以自己定义）
        BufferedImage bImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 构建2D画笔
        Graphics2D g = bImage.createGraphics();
        // 设置2D画笔的画出的文字颜色
        g.setColor(new Color(204, 159, 76));
        // 设置2D画笔的画出的文字背景色
        g.setBackground(Color.white);
        // 画出背景图片
        g.drawImage(backImage, 0, 0, null);



        /* --------对要显示的文字进行处理-------------- */
        String dear = "尊敬的";
        AttributedString atsDear = new AttributedString(dear);
        AttributedString atsName = new AttributedString(userName);

        if(StringUtils.isBlank(foundation)){
            foundation = schoolName + "基金会";
        }

        AttributedString atsFoundation = new AttributedString(foundation);
        AttributedString atsTime = new AttributedString(time);

        Font nameFont = new Font("华文黑体", Font.BOLD, 50);
        Font dearFont = new Font("宋体", Font.PLAIN, 50);
        Font contentFont = new Font("宋体", Font.PLAIN, 45);
        Font foundationFont = new Font("宋体", Font.PLAIN, 40);
        Font timeFont = new Font("宋体", Font.PLAIN, 30);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        atsName.addAttribute(TextAttribute.FONT, nameFont, 0, userName.length());
        // 姓名 ----位置
        g.drawString(atsName.getIterator(), 280, 630);

        g.setColor(new Color(49, 49, 49));
        atsDear.addAttribute(TextAttribute.FONT, dearFont, 0, dear.length());


        // 尊敬的 ----位置
        g.drawString(atsDear.getIterator(), 120, 630);

        String content = "　　感谢您关心和支持"+schoolName + projectName+"，捐赠";
        content += (StringUtils.isNotBlank(goodsName)?goodsName:(money+"元资金。"));
        System.out.println("內容---------->>>>>>>"+content + "\n長度----------------->>>>>>>" + content.length() );
        for(int i = 0; content.length() > i;i+=18){
            String showContent;
            if(18 > (content.length() - i)){
                showContent = (content.substring(i, content.length()));
            }else{
                showContent = (content.substring(i, i+18));
            }

            System.out.println("------------------>>>>>>>>>>>>>>>"+showContent);

            AttributedString atsContent = new AttributedString(showContent);
            atsContent.addAttribute(TextAttribute.FONT, contentFont, 0, showContent.length());

            g.drawString(atsContent.getIterator(), 120, 780 + 70*i/18);

            if(18 > (content.length() - i)){
                showContent = "　　特颁此证，以示感谢！";
                AttributedString atsTip = new AttributedString(showContent);
                atsTip.addAttribute(TextAttribute.FONT, contentFont, 0, showContent.length());
                g.drawString(atsTip.getIterator(), 120, 780 + 70*i/18 + 150);
            }

        }

        // 基金会名称
        atsFoundation.addAttribute(TextAttribute.FONT, foundationFont, 0, foundation.length());
        g.drawString(atsFoundation.getIterator(), width-120-(foundation.length()*44), height-240);


        // 时间
        g.setFont(timeFont);
        atsTime.addAttribute(TextAttribute.FONT, timeFont, 0, time.length());
        g.drawString(atsTime.getIterator(), width-20-(time.length()*34), height-190);

        if(StringUtils.isNotBlank(signetFilePath)){
            File signet = new File(signetFilePath);
            if(signet.exists()){
                ImageIcon signetImageIcon = new ImageIcon(signetFilePath);  // 获取印章图片
                signetImageIcon.setImage(signetImageIcon.getImage().getScaledInstance(225,225, Image.SCALE_DEFAULT));
                Image signetImage = signetImageIcon.getImage();
                // 绘制印章
                g.drawImage(signetImage, 620, 1080, null);
            }else{
                System.out.println("印章图片失效");
            }
        }else{
            System.out.println("印章图片未上传");
        }
        if(StringUtils.isNotBlank(logoFilePath)){
            File logo = new File(logoFilePath);
            if(logo.exists()){
                // 绘制logo
                ImageIcon logoImageIcon = new ImageIcon(logoFilePath);      // 获取logo图片
                logoImageIcon.setImage(logoImageIcon.getImage().getScaledInstance(99,99,Image.SCALE_DEFAULT));
                Image logoImage = logoImageIcon.getImage();
                g.drawImage(logoImage, 470, 210, null);
            }else{
                System.out.println("logo图片失效");
            }
        }else{
            System.out.println("logo图片未上传");
        }

        /* --------对要显示的文字进行处理--------------*/


        g.dispose();// 画笔结束

        createFileAndPath(targetFileName, bImage);
    }

    // 燕山大学的证书
    private static void createPicforYS(String backFilePath, String signetFilePath, String targetFileName,
                                  String userName, String time,String goodsName, Double money){
        ImageIcon backImageIcon = new ImageIcon(backFilePath);      // 获取背景图片

        Image backImage = backImageIcon.getImage();                 // Image可以获得图片的属性信息


        int width = backImage.getWidth(null);
        int height = backImage.getHeight(null);
        // 为画出与源图片的相同大小的图片（可以自己定义）
        BufferedImage bImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 构建2D画笔
        Graphics2D g = bImage.createGraphics();
        // 设置2D画笔的画出的文字颜色
        g.setColor(new Color(49, 49, 49));
        // 设置2D画笔的画出的文字背景色
        g.setBackground(Color.white);
        // 画出背景图片
        g.drawImage(backImage, 0, 0, null);

        AttributedString atsName = new AttributedString(userName);

        Font contentFont = new Font("华文黑体", Font.BOLD, 22);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(contentFont);

        atsName.addAttribute(TextAttribute.FONT, contentFont, 0, userName.length());
        g.drawString(atsName.getIterator(), 160, 500);

        String content = "　　感谢您对燕山大学教育事业的关心与支持，为学校捐赠了"+ (StringUtils.isNotBlank(goodsName)?goodsName:(NumberToCN.number2CNMontrayUnit(BigDecimal.valueOf(money)))+"人民币。");
        for(int i = 0; content.length() > i;i+=18){
            String showContent;
            if(18 > (content.length() - i)){
                showContent = (content.substring(i, content.length()));
            }else{
                showContent = (content.substring(i, i+18));
            }

            AttributedString atsContent = new AttributedString(showContent);
            atsContent.addAttribute(TextAttribute.FONT, contentFont, 0, showContent.length());

            g.drawString(atsContent.getIterator(), 160, 545 + 43*i/18);

            if(18 > (content.length() - i)){
                showContent = "　　特颁此证，谨致谢忱。";
                AttributedString atsTip = new AttributedString(showContent);
                atsTip.addAttribute(TextAttribute.FONT, contentFont, 0, showContent.length());
                g.drawString(atsTip.getIterator(), 160, 545 + 43*i/18 + 45);
            }

        }

        Font footFont = new Font("宋体", Font.BOLD, 17);
        String orgName = "河北省燕山大学教育基金会";

        AttributedString atsOrgName = new AttributedString(orgName);
        atsOrgName.addAttribute(TextAttribute.FONT, footFont, 0, orgName.length());
        g.drawString(atsOrgName.getIterator(), 370, 789);

        Font enFont = new Font("宋体", Font.BOLD, 13);
        String orgNameEn = "Yanshan University Education Foundation";
        AttributedString atsOrgNameEn = new AttributedString(orgNameEn);
        atsOrgNameEn.addAttribute(TextAttribute.FONT, enFont, 0, orgNameEn.length());
        g.drawString(atsOrgNameEn.getIterator(), 347, 814);

        Font timeFont = new Font("宋体", Font.BOLD, 14);
        AttributedString atsTime = new AttributedString(time);
        atsTime.addAttribute(TextAttribute.FONT, timeFont, 0, time.length());
        g.drawString(atsTime.getIterator(), 418, 842);

        if(StringUtils.isNotBlank(signetFilePath)){
            File signet = new File(signetFilePath);
            if(signet.exists()){
                ImageIcon signetImageIcon = new ImageIcon(signetFilePath);  // 获取印章图片
                signetImageIcon.setImage(signetImageIcon.getImage().getScaledInstance(125,125, Image.SCALE_DEFAULT));
                Image signetImage = signetImageIcon.getImage();
                // 绘制印章
                g.drawImage(signetImage, 413, 738, null);
            }else{
                System.out.println("印章图片失效");
            }
        }else{
            System.out.println("印章图片未上传");
        }

        g.dispose();// 画笔结束

        createFileAndPath(targetFileName, bImage);
    }


    // 江苏海事的证书
    private static void createPicforJSHS(String backFilePath, String signetFilePath, String targetFileName,
                                       String userName, String time,String goodsName, Double money){
        ImageIcon backImageIcon = new ImageIcon(backFilePath);      // 获取背景图片

        Image backImage = backImageIcon.getImage();                 // Image可以获得图片的属性信息


        int width = backImage.getWidth(null);
        int height = backImage.getHeight(null);
        // 为画出与源图片的相同大小的图片（可以自己定义）
        BufferedImage bImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 构建2D画笔
        Graphics2D g = bImage.createGraphics();
        // 设置2D画笔的画出的文字颜色
        g.setColor(new Color(49, 49, 49));
        // 设置2D画笔的画出的文字背景色
        g.setBackground(Color.white);
        // 画出背景图片
        g.drawImage(backImage, 0, 0, null);
        Font smallFont = LoadFont.LiShu(16);
        Font middleFont = LoadFont.LiShu(27);
        Font bigFont = LoadFont.LiShu(34);


        AttributedString atsName = new AttributedString(userName);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(bigFont);

        atsName.addAttribute(TextAttribute.FONT, bigFont, 0, userName.length());
        g.drawString(atsName.getIterator(), 87, 430);


        String contentLine1 = "　　感谢您对江苏海事职业技术学院教育事";
        AttributedString atsContent1 = new AttributedString(contentLine1);
        atsContent1.addAttribute(TextAttribute.FONT, middleFont, 0, contentLine1.length());
        g.drawString(atsContent1.getIterator(), 87, 482);

        String contentLine2 = "业的关心与支持，为学校捐赠了";
        AttributedString atsContent2 = new AttributedString(contentLine2);
        atsContent2.addAttribute(TextAttribute.FONT, middleFont, 0, contentLine2.length());
        g.drawString(atsContent2.getIterator(), 87, 526);

        String donate = (StringUtils.isNotBlank(goodsName)?goodsName:(NumberToCN.number2CNMontrayUnit(BigDecimal.valueOf(money))));
        String thanks = "　　特颁此证，谨致谢忱。";
        AttributedString atsThanks = new AttributedString(thanks);
        atsThanks.addAttribute(TextAttribute.FONT, middleFont, 0, thanks.length());

        if(donate.length() > 4){
            String startPart = donate.substring(0, 4);
            AttributedString atsStartPart = new AttributedString(startPart);
            atsStartPart.addAttribute(TextAttribute.FONT, bigFont, 0, startPart.length());
            g.drawString(atsStartPart.getIterator(), 465, 527);

            String restPart = donate.substring(4);
            int lineNum = 14;
            System.out.println(restPart.length());
            for(int i = 0; i <= restPart.length()/lineNum; i++){
                String linePart;
                if(restPart.length() - (lineNum*(i+1)) >= 0){
                    linePart = restPart.substring(i*lineNum, (i+1)*lineNum);
                }else{
                    linePart = restPart.substring(i*lineNum, restPart.length());
                }
                if(StringUtils.isNotBlank(linePart)){
                    AttributedString atsLinePart = new AttributedString(linePart);
                    atsLinePart.addAttribute(TextAttribute.FONT, bigFont, 0, linePart.length());
                    g.drawString(atsLinePart.getIterator(), 87, 527+44*(i+1));
                    if(StringUtils.isBlank(goodsName)){
                        if(14-linePart.length() == 1){
                            AttributedString atsRMB1 = new AttributedString("人民");
                            atsRMB1.addAttribute(TextAttribute.FONT, middleFont, 0, 2);
                            g.drawString(atsRMB1.getIterator(), (90+linePart.length()*34), 526+44*(i+1));

                            AttributedString atsRMB2 = new AttributedString("币。");
                            atsRMB2.addAttribute(TextAttribute.FONT, middleFont, 0, 2);
                            g.drawString(atsRMB2.getIterator(), 87, 526+44*(i+2));

                            g.drawString(atsThanks.getIterator(), 87, 520+44*(i+3));
                        }else if(14-linePart.length() > 1){
                            AttributedString atsRMB = new AttributedString("人民币。");
                            atsRMB.addAttribute(TextAttribute.FONT, middleFont, 0, 4);
                            g.drawString(atsRMB.getIterator(), (90+linePart.length()*34), 526+44*(i+1));

                            g.drawString(atsThanks.getIterator(), 87, 520+44*(i+2));
                        }
                    }else{
                        if(14-linePart.length() > 0){
                            g.drawString(atsThanks.getIterator(), 87, 520+44*(i+2));
                        }
                    }
                }else{
                    if(StringUtils.isBlank(goodsName)){
                        AttributedString atsRMB = new AttributedString("人民币。");
                        atsRMB.addAttribute(TextAttribute.FONT, middleFont, 0, 4);
                        g.drawString(atsRMB.getIterator(), 87, 526+44*(i+1));
                        g.drawString(atsThanks.getIterator(), 87, 520+44*(i+2));
                    }else{
                        g.drawString(atsThanks.getIterator(), 87, 520+44*(i+1));
                    }
                }
            }
        }else{
            AttributedString atsDonate = new AttributedString(donate);
            atsDonate.addAttribute(TextAttribute.FONT, bigFont, 0, donate.length());
            g.drawString(atsDonate.getIterator(), 465, 527);
            if(StringUtils.isBlank(goodsName)){
                if(donate.length() == 4){
                    AttributedString atsRMB = new AttributedString("人民币。");
                    atsRMB.addAttribute(TextAttribute.FONT, middleFont, 0, 4);
                    g.drawString(atsRMB.getIterator(), 87, 526+44);
                    g.drawString(atsThanks.getIterator(), 87, 520+44*2);
                }else if(donate.length() == 3){
                    AttributedString atsRMB1 = new AttributedString("人");
                    atsRMB1.addAttribute(TextAttribute.FONT, middleFont, 0, 1);
                    g.drawString(atsRMB1.getIterator(), 87+484, 526);

                    AttributedString atsRMB2 = new AttributedString("民币。");
                    atsRMB2.addAttribute(TextAttribute.FONT, middleFont, 0, 3);
                    g.drawString(atsRMB2.getIterator(), 87, 526+44);

                    g.drawString(atsThanks.getIterator(), 87, 520+44*2);
                }else if(donate.length() == 2){
                    AttributedString atsRMB1 = new AttributedString("人民");
                    atsRMB1.addAttribute(TextAttribute.FONT, middleFont, 0, 2);
                    g.drawString(atsRMB1.getIterator(), 87+448, 526);

                    AttributedString atsRMB2 = new AttributedString("币。");
                    atsRMB2.addAttribute(TextAttribute.FONT, middleFont, 0, 2);
                    g.drawString(atsRMB2.getIterator(), 87, 526+44);

                    g.drawString(atsThanks.getIterator(), 87, 520+44*2);
                }else{
                    AttributedString atsRMB = new AttributedString("人民币。");
                    atsRMB.addAttribute(TextAttribute.FONT, middleFont, 0, 4);
                    g.drawString(atsRMB.getIterator(), 87+378+34*donate.length(), 526);
                    g.drawString(atsThanks.getIterator(), 87, 520+44);
                }
            }else{
                g.drawString(atsThanks.getIterator(), 87, 526+44);
            }
        }

        AttributedString atsTime = new AttributedString(time);
        atsTime.addAttribute(TextAttribute.FONT, smallFont, 0, time.length());
        g.drawString(atsTime.getIterator(), 400, 734);

        if(StringUtils.isNotBlank(signetFilePath)){
            File signet = new File(signetFilePath);
            if(signet.exists()){
                ImageIcon signetImageIcon = new ImageIcon(signetFilePath);  // 获取印章图片
                signetImageIcon.setImage(signetImageIcon.getImage().getScaledInstance(125,125, Image.SCALE_DEFAULT));
                Image signetImage = signetImageIcon.getImage();
                // 绘制印章
                g.drawImage(signetImage, 394, 650, null);
            }else{
                System.out.println("印章图片失效");
            }
        }else{
            System.out.println("印章图片未上传");
        }

        g.dispose();// 画笔结束


        // 输出文件
        createFileAndPath(targetFileName, bImage);
    }



    // 生成文件保存
    public static void createFileAndPath(String targetFileName, BufferedImage bImage){
        try {
//            Global.DISK_PATH = "D:\\Bscc\\workspace\\CYServer2.0\\src\\main\\webapp\\cyfile\\";
            // 判断Global路径是否存在
            if(StringUtils.isBlank(Global.DISK_PATH)){
                System.out.println("路径不存在");
                return;
            }
            File globalPath =new File(Global.DISK_PATH);
            if(!globalPath.exists() && !globalPath.isDirectory()){
                System.out.println("目录不存在");
                return;
            }


            // 判断image目录是否存在
            File imgPath = new File(Global.DISK_PATH + "/image/");
            if(!imgPath.exists()  && !imgPath.isDirectory())
                imgPath.mkdir();


            // 判断foundationCertificate目录是否存在
            File cerPath = new File(Global.DISK_PATH + "/image/foundationCertificate/");
            if(!cerPath.exists() && !cerPath.isDirectory())
                cerPath.mkdir();

            String targetFilePath = Global.DISK_PATH + "/image/foundationCertificate/" + targetFileName;

            // 输出 文件 到指定的路径
            FileOutputStream out = new FileOutputStream(targetFilePath);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bImage);
            param.setQuality(1, true);
            encoder.encode(bImage, param);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String createCertificate( Donation donation ){
        String targetFileName = IdGen.uuid()+".jpg";

        String goodsName = "";
        if("20".equals(donation.getDonationType())){
            if(StringUtils.isNotBlank(donation.getItemName())){
                goodsName = donation.getItemName();
            }else if(StringUtils.isNotBlank(donation.getItemType())){
                Map<String,String> stringMap = Maps.newHashMap();
                stringMap.put("dictTypeValue","29");
                java.util.List<Dict> dictList = DictUtils.findDictList(stringMap);

                if(dictList != null && dictList.size() > 0){
                    for(Dict d:dictList){
                        if(d.getDictValue().equals(donation.getItemType())){
                            goodsName = d.getDictName();
                            break;
                        }
                    }
                }
            }
            if(StringUtils.isNotBlank(donation.getItemNum())){
                goodsName += donation.getItemNum();
            }
            if(StringUtils.isNotBlank(donation.getItemType())){
                Map<String,String> stringMap = Maps.newHashMap();
                stringMap.put("dictTypeValue","31");
                List<Dict> dicts = DictUtils.findDictList(stringMap);
                if(dicts != null && dicts.size() > 0){
                    for(Dict d: dicts){
                        if(d.getDictValue().equals(donation.getItemType())){
                            goodsName += d.getDictName();
                            break;
                        }
                    }
                }
            }
        }

        String userName;
        if(StringUtils.isNotBlank(donation.getDonorName())){
            userName = donation.getDonorName();
        }else if(StringUtils.isNotBlank(donation.getX_name())){
            userName = donation.getX_name();
        }else{
            return "";
        }

        String sex = "";
        if("10".equals(donation.getDonorType())){
            sex = "先生/女士";
            if("1".equals(donation.getX_sex()) || "女".equals(donation.getX_sex())){
                sex = "女士";
            }else if("0".equals(donation.getX_sex()) || "男".equals(donation.getX_sex())){
                sex = "先生";
            }
        }
        userName += " "+sex+":";


        String payTime = "";
        if (donation.getPayTime()!=null){
            payTime= new SimpleDateFormat("yyyy年MM月dd日").format(donation.getPayTime());
        }else if(donation.getDonationTime()!=null){
            payTime = new SimpleDateFormat("yyyy年MM月dd日").format(donation.getDonationTime());
        }

        if("000440".equals(Global.deptNo)){
            // 燕山大学证书
            String backFilePath = "/home/cysys/tomcat/webapps/ROOT/donation_certificate/certificate_ys_MIN.jpg";

            Certificate.createPicforYS(backFilePath, Global.DISK_PATH + Global.FOUNDATION_SIGNET,targetFileName,userName, payTime, goodsName, donation.getMoney());
        }else if("000230".equals(Global.deptNo)){
            // 江苏海事证书
            String backFilePath = "/home/cysys/tomcat/webapps/ROOT/donation_certificate/certificate_jshs.jpg";
            Certificate.createPicforJSHS(backFilePath, Global.DISK_PATH, targetFileName, userName, payTime, goodsName, donation.getMoney());
        }else{
            // 窗友证书生成
            String backFilePath = "/home/cysys/tomcat/webapps/ROOT/foundation/img/certificate_back.png";
            Certificate.createPic(backFilePath, Global.DISK_PATH + Global.schoolLogo,Global.DISK_PATH + Global.FOUNDATION_SIGNET,
                    targetFileName,donation.getX_name(),donation.getProject().getProjectName(), Global.schoolSign ,Global.FOUNDATION_NAME, goodsName, payTime, donation.getMoney());
        }

        return "/image/foundationCertificate/"+targetFileName;
    }

 /*   public static void main(String[] args) {
        // 窗友证书生成
        String backFilePath = "D:\\Bscc\\workspace\\CYServer2.0\\src\\main\\webapp\\foundation\\img\\certificate_back.png";
        Certificate.createPic(backFilePath, Global.DISK_PATH + Global.schoolLogo,Global.DISK_PATH + Global.FOUNDATION_SIGNET,
                "apbc.jpg","仼","哈哈哈哈", Global.schoolSign ,"基友大学基金会", "",new SimpleDateFormat("yyyy年MM月dd日").format(new Date()), 2.13);

    }*/

}
