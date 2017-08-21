package com.cy.core.resumeBase.entity;

import com.cy.base.entity.DataEntity;

/**
 * Created by cha0res on 6/6/17.
 */
public class ResumeCertificate extends DataEntity<ResumeCertificate> {
    private String resumeBaseId;        // 基础简历编号
    private String certificateName;     // 证书名称
    private String time;                // 获取证书时间

    public String getResumeBaseId() {
        return resumeBaseId;
    }

    public void setResumeBaseId(String resumeBaseId) {
        this.resumeBaseId = resumeBaseId;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
