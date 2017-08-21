package com.cy.common.utils;

import com.cy.system.Global;
import com.cy.util.DateUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 富文本工具类
 * @author  liuzhen
 */
public class EditorUtils {

    /**
     * 富文本内容修饰
     * @param content
     * @return
     */
    public static String edictorContent(String content) {
        if(StringUtils.isNotBlank(content)) {
            content = content.replaceAll("/static/umeditor/jsp/./../../..","") ;
            content = changeSrcFromAbsoluteToRelative(content) ;
//            System.out.println("--------> " + content.indexOf("&lt;style type=&quot;text/css&quot;&gt;")  + " | " + content.indexOf("<style type='text/css'>"));
            if(content.indexOf("&lt;style type=&quot;text/css&quot;&gt;") < 0 && content.indexOf("<style type='text/css'>") < 0 && content.indexOf("<style type=\"text/css\">") < 0 ) {
                String contentHeader = "<style type='text/css'>\n" +
                        "\t.contentEditor{\n" +
                        "\t\tmargin:5px;\n" +
                        "\t\twidth :97%;\n" +
                        "\t}\n" +
                        "\t.contentEditor img{\n" +
                        "\t\tmax-width: 100%;\n" +
                        "\t\theight:auto;\n" +
                        "\t}\n" +
                        "\t.contentEditor p{\n" +
                        "\t\tmargin-right: 0pt; margin-left: 0pt; text-indent: 0pt; line-height: 150%;\n" +
                        "\t}\n" +
                        "</style>\n" +
                        "<div class='contentEditor'>" ;
                /*contentHeader += "<link rel=\"stylesheet\" href=\""+Global.getConfig("image_url")+"/static/umeditor/third-party/mathquill/mathquill.css\">" ;
                contentHeader += "<script src=\""+Global.getConfig("image_url")+"/static/umeditor/third-party/jquery.min.js\"></script>" +
                        "<script src=\""+Global.getConfig("image_url")+"/static/umeditor/third-party/mathquill/mathquill.min.js\"></script>" ;*/
                String contentBottom = "</div>" ;
                content = contentHeader + content + contentBottom ;
            }
            content = StringEscapeUtils.unescapeHtml4(content) ;
        }
        return content ;
    }

    /**
     * 将内容中的路径从相对路径转换成绝对路径
     * @param content
     * @return
     */
    public static String changeSrcFromRelativeToAbsolute(String content) {
        if(StringUtils.isNotBlank(content)) {
            content = content.replaceAll("src=&quot;/" , "src=&quot;"+ Global.URL_DOMAIN);
            content = content.replaceAll("src=\"/" , "src=\""+ Global.URL_DOMAIN);
            content = content.replaceAll("url\\(\"/" , "url\\(\""+ Global.URL_DOMAIN);
            content = content.replaceAll("url\\(/" , "url\\("+ Global.URL_DOMAIN);
        }
        return content ;
    }
    /**
     * 将内容中的路径从绝对路径转换成相对路径
     * @param content
     * @return
     */
    public static String changeSrcFromAbsoluteToRelative(String content) {
        if(StringUtils.isNotBlank(content)) {
            if(StringUtils.isNotBlank(Global.URL_DOMAIN)) {
                content = content.replaceAll("src=&quot;" + Global.URL_DOMAIN, "src=&quot;/");
                content = content.replaceAll("src=\"" + Global.URL_DOMAIN, "src=\"/");
            }
//            if(StringUtils.isNotBlank(Global.getConfig("image_url_old"))) {
//                content = content.replaceAll("src=&quot;" + Global.getConfig("image_url_old"), "src=&quot;/" + Global.getConfig("project_name"));
//                content = content.replaceAll("src=\"" + Global.getConfig("image_url_old"), "src=\"/" + Global.getConfig("project_name"));
//            }
        }
        return content ;
    }

    public static void main(String[] args) throws Exception {
        Global.URL_DOMAIN = "http://localhost/cyfile/" ;
        String content = "<style type=\"text/css\">\n" +
                "\t.contentEditor{\n" +
                "\t\tmargin:5px;\n" +
                "\t\twidth :97%;\n" +
                "\t}\n" +
                "\t.contentEditor img{\n" +
                "\t\tmax-width: 100%;\n" +
                "\t\theight:auto;\n" +
                "\t}\n" +
                "\t.contentEditor p{\n" +
                "\t\tmargin-right: 0pt; margin-left: 0pt; text-indent: 0pt; line-height: 150%;\n" +
                "\t}</style>" +
                "<div class=\"contentEditor\">" +
                "<p>左边给定的是纸盒的外表面，下列哪一项能由它折叠而成：</p>" +
                "<p>" +
                "<img src=\"http://localhost/cyfile/userfiles/upload/20160220/93321455952148420.png\" " +
                "_src=\"http://localhost/cyfile/userfiles/upload/20160220/93321455952148420.png\"/>" +
                "</p>" +
                "</div>";

        content = changeSrcFromAbsoluteToRelative(content) ;
        System.out.println("--------> " + content) ;

        content = changeSrcFromRelativeToAbsolute(content) ;
        System.out.println("--------> content : " + content) ;
//
//        content = html2Text(content) ;
//        System.out.println("--------> content text : \n" + content);

    }

    /**
     * 富文本内容转换成纯文本内容
     * @param inputHtml
     * @return
     * @throws Exception
     */
    public static String html2Text(String inputHtml) {
        if(StringUtils.isNotBlank(inputHtml)) {
            inputHtml = inputHtml.replaceAll("(<style)[\\s\\S]*(</style>)","");
            inputHtml = inputHtml.replaceAll("\\n","");
            inputHtml = inputHtml.replaceAll("\\t","");
            inputHtml = inputHtml.replaceAll("&nbsp;","");
        }
        String htmlStr = inputHtml; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串
    }




    public static void download(String urlString, String savePath, String filename) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 设置请求超时为5s
        con.setConnectTimeout(5*1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 输出的文件流
        File sf = new File(savePath);
        if(!sf.exists()){
            sf.mkdirs();

        }
        String path = sf.getPath()+"/"+filename;
        OutputStream os = new FileOutputStream(path);

        // 1K的数据缓冲
        byte[] bs = new byte[1024*5];
        // 读取到的数据长度
        int len;

        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }

        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

    private static List<String> getImageUrlList(String content){
        List<String> imageUrlList = new ArrayList<String>();
        String imgSuffix = "gif,jpg,jpeg,png,bmp";

        if (org.apache.commons.lang3.StringUtils.isNotBlank(content)) {
            String[] contentArr = content.split("http");
            // 必须包含http且不可能是第一个字符，所以数组长度一定大于1，且数组第一个肯定不是
            if (contentArr.length > 1) {
                for (int i = 1; i < contentArr.length; i++) {
                    // 包含图片地址的【前缀】字符串
                    String preChar = contentArr[i - 1].substring(contentArr[i - 1].length() - 1);

                    // 单双引号的转义符
                    if (";".equals(preChar)) {
                        preChar = contentArr[i - 1].substring(contentArr[i - 1].length() - 6);
                    }

                    // 包含图片地址的【后缀】字符串
                    String sufChar = preChar;

                    // 样式background-image:url()中可能会由小括号包裹
                    if ("(".equals(preChar)) {
                        sufChar = ")";
                    }

                    // 如果包裹图片地址的字符对儿存在，获取该图片地址
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(sufChar)) {
                        int index = contentArr[i].indexOf(sufChar);
                        if (index > 0) {
                            String oldImgUrl = "http" + contentArr[i].substring(0, index);

                            // 图片后缀（包含点号）
                            String suffStr = oldImgUrl.substring(oldImgUrl.lastIndexOf(".") + 1);

                            if (imgSuffix.contains(suffStr)) {
                                imageUrlList.add(oldImgUrl);
                            }
                        }
                    }
                }
            }
        }

        return imageUrlList;
    }

    /**
     * 方法 的功能描述：将图片下载到本地
     * @createAuthor niu
     * @createDate 2017-06-15 14:45:05
     * @param imgUrl 完整路径的图片地址
     * @return  返回本地图片的绝对路径
     * @throw
     *
    */
    public static String downloadImage(String imgUrl) throws Exception {
        // 文件保存目录路径
        String savePath = Global.DISK_PATH + "image/" + DateUtils.getDate("yyyyMMdd") + "/";
        // 数据库中保存的路径（相对路径）
        String saveURL = "/image/" + DateUtils.getDate("yyyyMMdd") + "/";

        String imgName = DateUtils.getDate("yyyyMMddHHmmss") + "_"+ new Random().nextInt(1000) + ".jpg";

        EditorUtils.download(imgUrl,savePath,imgName);

        return saveURL+imgName;
    }

    public static String filterContentForNetImg(String content) {

        // 文件保存目录路径
        String savePath = Global.DISK_PATH + "image/" + DateUtils.getDate("yyyyMMdd") + "/";
        // 数据库中保存的路径（相对路径）
        String saveURL = "/image/" + DateUtils.getDate("yyyyMMdd") + "/";

        List<String> imageUrlList = getImageUrlList(content);

        if (imageUrlList != null && imageUrlList.size() > 0) {
            for(String oldImgUrl : imageUrlList){
                // 验证图片地址是否有效

                // 原图片名称
                String oldImageName = oldImgUrl.substring(oldImgUrl.lastIndexOf("/") + 1);
//                System.out.println("---------oldImageName=" + oldImageName);

                // 图片后缀（包含点号）
                String suffStr = oldImageName.substring(oldImageName.lastIndexOf("."));

                // 新图片名称
                String newImagename = DateUtils.getDate("yyyyMMddHHmmss") + "_"+ new Random().nextInt(1000) + suffStr;
//                System.out.println("---------newImagename=" + newImagename);

                try {
                    download(oldImgUrl, savePath, newImagename);

                    content = content.replaceAll(oldImgUrl, saveURL + newImagename);

//                    System.out.println("------content=" + content);
                }
                catch (Exception e) {
//                    e.printStackTrace();
//                    System.out.println(e.getMessage() + "\n" + e.getStackTrace());
                }
            }
        }

        return content;
    }
}
