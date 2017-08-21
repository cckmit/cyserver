package com.cy.core.share.service;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.EditorUtils;
import com.cy.common.utils.StringUtils;
import com.cy.core.share.dao.FileMapper;
import com.cy.core.share.dao.ShareMapper;
import com.cy.core.share.entity.File;
import com.cy.core.share.entity.Share;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2016/12/22.
 */
@Service("shareService")
public class ShareServiceImpl implements ShareService {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private FileMapper fileMapper;

    public void save(Share share){
        if (StringUtils.isNotBlank(share.getIntroduce())) {
            share.setIntroduce(EditorUtils.edictorContent(share.getIntroduce()));
        }
        if (StringUtils.isNotBlank(share.getId())) {
            share.preUpdate();
            shareMapper.update(share);

        } else {
            share.preInsert();
            shareMapper.insert(share);
        }
        if (StringUtils.isNotBlank(share.getPictureUrls())) {
            //删除该分享关联的图片
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("fileGroup",share.getId());
            map.put("bussType","10");
            fileMapper.delete(map);
            //保存改分享所关联的图片
            String[] urls = share.getPictureUrls().split(",");
            for (String url : urls) {
                File file = new File();
                file.setFileGroup(share.getId());
                file.setBussType("10");
                file.setPicUrl(url);
                file.preInsert();
                fileMapper.insert(file);
            }
        }

    }

    public Share selectById(String id) {
        Share share = shareMapper.selectById(id);
        if (StringUtils.isNotBlank(share.getIntroduce())) {
            share.setIntroduce(EditorUtils.changeSrcFromRelativeToAbsolute(share.getIntroduce()));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fileGroup", share.getId());
        map.put("bussType","10");
        List<File> fileList = fileMapper.selectList(map);
        share.setFileList(fileList);
        return share;
    }

    public List<Share> findList(Share share) {
        List<Share> shareList = shareMapper.selectList(share);
        if (shareList != null && shareList.size() > 0) {
            for (Share share1 : shareList) {
                //关联分享图片
                if (share1 != null && StringUtils.isNotBlank(share1.getId())) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("fileGroup", share1.getId());
                    map.put("bussType","10");
                    List<File> fileList = fileMapper.selectList(map);
                    share1.setFileList(fileList);
                }
                if (share1 != null && StringUtils.isNotBlank(share1.getIntroduce())) {
                    //将富文本中相对路径转换为绝对路径
                    share1.setIntroduce(EditorUtils.changeSrcFromRelativeToAbsolute(share1.getIntroduce()));
                }
            }
        }
        return shareList;
    }

    /**
     * 方法updateAndroidDownloads 的功能描述：更新android下載量
     * @createAuthor niu
     * @createDate 2017-04-05 13:44:37
     * @param
     * @return void
     * @throw
     *
     */
    public void updateAndroidDownloads(){

        List<Share> shares = shareMapper.selectList(new Share());
        if (shares!=null && !shares.isEmpty()){
            Share share = shares.get(0);
            if (StringUtils.isNotBlank(share.getId())){
                String androidDownloads = share.getAndroidDownloads();

                if (StringUtils.isBlank(androidDownloads)){
                   androidDownloads = "0";
                }
                share.setAndroidDownloads(String.valueOf(Long.parseLong(androidDownloads)+1));
                shareMapper.update(share);
            }
        }
    }

    /***********************************************************************
     * 【分享】相关API（以下区域）
     * <p>
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     ***********************************************************************/
    public void findShareSetting(Message message,String content){
        try {

            List<Share> shareList = findList(new Share());
            Share share = new Share();
            if (shareList != null && shareList.size()>0 ){
                share =shareList.get(0);
            }
            //过滤文件数据
            if (share !=null && share.getFileList()!=null && share.getFileList().size()>0){
                List<File> fileList = new ArrayList<>();
                for (File file :share.getFileList()){
                    File f = new File();
                    if(file != null && StringUtils.isNotBlank(file.getPicUrl())){
                        f.setPicUrl(file.getPicUrl());
                        fileList.add(f);
                    }
                }
                share.setFileList(fileList);
            }
            message.init(true,"查询成功",share);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
