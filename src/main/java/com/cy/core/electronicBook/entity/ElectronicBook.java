package com.cy.core.electronicBook.entity;

import com.cy.base.entity.DataEntity;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 电子刊物</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-3-14
 */
public class ElectronicBook extends DataEntity<ElectronicBook> {


    private String name;
    private String author;
    private String version;
    private String upload;
    private String logo;
    private String logoUrl;         //logo存储地址
    private String uploadUrl;
    private String uploadOrganization;
    private String alumniName;
    private String introduction;

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUploadOrganization() {
        return uploadOrganization;
    }

    public void setUploadOrganization(String uploadOrganization) {
        this.uploadOrganization = uploadOrganization;
    }

    public String getAlumniName() {
        return alumniName;
    }

    public void setAlumniName(String alumniName) {
        this.alumniName = alumniName;
    }

    public String getUploadUrl() {
        if(StringUtils.isBlank(uploadUrl) && StringUtils.isNotBlank(upload) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(upload.indexOf(Global.URL_DOMAIN) == 0) {
                if (!upload.trim().startsWith("/")){
                    upload="/"+upload;
                }
                uploadUrl = upload.substring(Global.URL_DOMAIN.length()) ;
            }else{
                uploadUrl=upload;
            }
        }
        return uploadUrl;
    }

    public String getLogoUrl() {
        if(StringUtils.isBlank(logoUrl) && StringUtils.isNotBlank(logo) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(logo.indexOf(Global.URL_DOMAIN) == 0) {
                if (!logo.trim().startsWith("/")){
                    logo="/"+logo;
                }
                logoUrl = logo.substring(Global.URL_DOMAIN.length()) ;
            }else{
                logoUrl=logo;
            }
        }
        return logoUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getLogo() {
        if(StringUtils.isBlank(logo) && StringUtils.isNotBlank(logoUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(logoUrl.indexOf("http") < 0) {
                if (!logoUrl.trim().startsWith("/")){
                    logoUrl="/"+logoUrl;
                }
                logo =  logoUrl ;
            }else{
                logo=logoUrl;
            }
        }
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpload() {
        if(StringUtils.isBlank(upload) && StringUtils.isNotBlank(uploadUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(uploadUrl.indexOf("http") < 0) {
                if (!uploadUrl.trim().startsWith("/")){
                    uploadUrl="/"+uploadUrl;
                }
                upload = uploadUrl ;
            }else{
                upload=uploadUrl;
            }
        }
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
