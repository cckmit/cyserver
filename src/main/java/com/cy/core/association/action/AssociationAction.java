package com.cy.core.association.action;

import com.alibaba.fastjson.JSONObject;
import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.association.dao.AssociationMapper;
import com.cy.core.association.entity.Association;
import com.cy.core.association.service.AssociationService;
import com.cy.core.user.entity.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cha0res on 12/13/16.
 */
@Namespace("/association")
@Action(value = "associationAction")
public class AssociationAction extends AdminBaseAction {
    private static final Logger logger = Logger.getLogger(AssociationAction.class);

    @Autowired
    private AssociationService associationService;

    private Association association;
    private String associationId;
    private String top;

    //得到列表并且分页
    public void dataGraidAssociation() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", rows);
        map.put("page", page);
        User user = getUser();
        if(user != null && user.getDeptId()>1){
            map.put("alumniId", String.valueOf(user.getDeptId()));
        }
        if(association != null){
            if(StringUtils.isNotBlank(association.getName())){
                map.put("name", association.getName());
            }
            if(StringUtils.isNotBlank(association.getTypeId())){
                map.put("typeId", association.getTypeId());
            }
            if(StringUtils.isNotBlank(association.getTop())){
                map.put("top", association.getTop());
            }
        }
        DataGrid<Association> data = associationService.dataGrid(map);
        super.writeJson(data);
    }

    //删除
    public void deleteAssociation(){
        Message message=new Message();
        try {
            associationService.deleteAssociation(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);

    }

    //新增组织
    public void saveAssociation(){
        Message message=new Message();
        try {
            User user = getUser();
            association.setAlumniId(String.valueOf(user.getDeptId()));
            int code = associationService.saveAssociation(association);
            switch (code){
                case 0:
                    message.init(true, "新增成功", null);
                    break;
                case 1:
                    message.init(false, "账号已存在", null);
                    break;
                default:
                    message.init(false, "未知错误", null);
            }

        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("新增失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    //修改组织
    public void updateAssociation(){
        Message message=new Message();
        try {
            User user = getUser();
            association.setAlumniId(String.valueOf(user.getDeptId()));
            associationService.updateAssociation(association);
            message.setMsg("修改成功");
            message.setSuccess(true);

        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("修改失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    //查看详情
    public void getAssociationById(){
        Association association=associationService.getAssociationById(associationId);
        super.writeJson(association);
    }

    public void doNotNeedSecurity_getAssociationType(){
        super.writeJson(associationService.getAssociationType());
    }


    public void doNotNeedSecurity_getAssociationList(){

        super.writeJson(associationService.findList());
    }

    /**
     * 设置幻灯片
     */
    public void setTopAssociation(){
        Message message=new Message();
        try{
            if(StringUtils.isNotBlank(top)){
                if(StringUtils.isNotBlank(ids)){
                    String[] array = ids.split(",");
                    for(int i = 0; i < array.length; i++){
                        Association association=associationService.getAssociationById(array[i]);
                        if(association != null && StringUtils.isNotBlank(association.getId())){
                            association.setTop(top);
                            associationService.updateAssociation(association);
                        }
                    }
                    message.init(true,"设置成功", null);
                }else if(StringUtils.isNotBlank(associationId)){
                    Association association=associationService.getAssociationById(associationId);
                    if(association != null && StringUtils.isNotBlank(association.getId())){
                        association.setTop(top);
                        associationService.updateAssociation(association);
                        message.init(true,"设置成功", null);
                    }else{
                        message.init(false,"社团不存在",null);
                    }
                }else{
                    message.init(false,"请选择要设置的社团",null);
                }
            }else{
                message.init(false,"请提供设置类型",null);
            }

        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("设置失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    //获取社团详情
    public void doNotNeedSecurity_getAssociationInfo(){
        User user = getUser();
        if(user != null && com.cy.common.utils.StringUtils.isNotBlank(user.getAssociationId())){
            Association association = associationService.getAssociationById(user.getAssociationId());
            if( association != null ){
                super.writeJson(association);
            }else{
                super.writeJson(null);
            }
        }else {
            super.writeJson(null);
        }
    }

    //更新社团信息
    public void doNotNeedSecurity_updateAssociationInfo(){
        Message message=new Message();
        try {
            if(StringUtils.isNotBlank(association.getId())){
                Association associationTmp = associationService.getAssociationById(association.getId());
                if(associationTmp != null){
                    associationTmp.preUpdate();
                    if(StringUtils.isNotBlank(association.getIntroduction())){
                        associationTmp.setIntroduction(association.getIntroduction());
                        associationTmp.setLogo(association.getLogo());
                        associationTmp.setLogoUrl("");
                        associationTmp.setPoster(association.getPoster());
                        associationTmp.setPosterUrl("");
                        associationService.updateAssociation(associationTmp);
                        message.setMsg("保存成功");
                        message.setSuccess(true);
                    }else if(StringUtils.isNotBlank(association.getConcatName())&&StringUtils.isNotBlank(association.getTelephone())){
                        associationTmp.setConcatName(association.getConcatName());
                        associationTmp.setTelephone(association.getTelephone());
                        associationTmp.setEmail(association.getEmail());
                        associationService.updateAssociation(associationTmp);
                        message.setMsg("联系人信息保存成功");
                        message.setSuccess(true);
                    }else {
                        message.setMsg("请填写必要信息");
                        message.setSuccess(false);
                    }
                }else {
                    message.setMsg("社团不存在");
                    message.setSuccess(false);
                }
            }else {
                message.setMsg("未获取社团ID");
                message.setSuccess(false);
            }
        }catch (Exception e) {
            logger.error(e, e);
            message.setMsg("修改失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    //生成二维码

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public void doNotNeedSessionAndSecurity_getErWeiMa(){
        String qrUrl = "http://www.cymobi.com" ;
        getResponse().setDateHeader("Expires", 0);
        getResponse().setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        getResponse().addHeader("Cache-Control", "post-check=0, pre-check=0");
        getResponse().setHeader("Pragma", "no-cache");
        getResponse().setContentType("image/gif");
        Message message = new Message();

        if (StringUtils.isBlank(associationId)){

            qrUrl += "?err=社团编号为空" ;
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("c", "409");
            jsonObject.put("id", associationId);
            String jsonStr = jsonObject.toJSONString();
            qrUrl += "?qr=" + jsonStr;
        }

        System.out.println("--------------> qrUrl : " + qrUrl);
        try{

            int width = 300;
            int height = 300;
            //二维码的图片格式
            String format = "gif";
            Hashtable hints = new Hashtable();
            //内容所使用编码
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrUrl,
                    BarcodeFormat.QR_CODE, width, height, hints);
            //生成二维码
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
                }
            }
            ServletOutputStream out = getResponse().getOutputStream();
            ImageIO.write(image,format, out);
            out.flush();
            out.close();
        }catch (Exception e){
//            message.setMsg("该活动编号不存在");
//            message.setSuccess(false);
            logger.error(e, e);
        }
      //  super.writeJson(message);

    }

    //海报页面

    public void doNotNeedSessionAndSecurity_getPoster() {

        User user = getUser();
        String id = new String();
        if(StringUtils.isNotBlank(user.getAssociationId())){
            id = user.getAssociationId();
        }else {
            id = association.getId();
        }

        association = associationService.getAssociationById(id);
        String result = new String();
        if (association.getIntroduction() != null) {

            String tmpOneStr = "";

            StringBuffer bufConStr = new StringBuffer(association.getIntroduction());

            int findImg = bufConStr.indexOf("<img");

            while (findImg != -1) {

                tmpOneStr = bufConStr.substring(findImg, bufConStr.indexOf(">", findImg) + 1);

                bufConStr.delete(findImg, bufConStr.indexOf(">", findImg) + 1);

                tmpOneStr = tmpOneStr.replaceAll("(?i)style[\\s]*=[\\s]*[\'\"][\\S]*[\\s]*[\'\"]", "");

                tmpOneStr = tmpOneStr.replaceAll("(?i)width[\\s]*=[\\s]*[\'\"][\\S]*[\\s]*[\'\"]", "");

                tmpOneStr = tmpOneStr.replaceAll("(?i)height[\\s]*=[\\s]*[\'\"][\\S]*[\\s]*[\'\"]", "");

                bufConStr.insert(findImg, tmpOneStr);

                findImg = bufConStr.indexOf("<img", findImg + 1);

            }

            result = bufConStr.toString();
        }
         super.writeJson(result);
    }


    public String getAssociationId() {
        return associationId;
    }

    public void setAssociationId(String associationId) {
        this.associationId = associationId;
    }

    public Association getAssociation() {return association;}

    public void setAssociation(Association association) {this.association = association;}

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }


}
