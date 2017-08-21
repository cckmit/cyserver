package com.cy.smscloud.entity;

/**
 * 请求响应实体
 * Created by Kentun on 2015-08-28.
 */
public class RequestEntity {
    private Header header;//头部
    private Body body;//主体内容
    private String code;//请求码
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public RequestEntity(Header header, Body body,String code) {
        this.header = header;
        this.body = body;
        this.code = code;
    }

    public RequestEntity() {
    }

	@Override
	public String toString() {
		return "RequestEntity [header=" + header + ", body=" + body + ", code="
				+ code + "]";
	}
}
