package com.cy.util.easemob.api;

import com.cy.util.easemob.comm.wrapper.BodyWrapper;
import com.cy.util.easemob.comm.wrapper.HeaderWrapper;
import com.cy.util.easemob.comm.wrapper.QueryWrapper;
import com.cy.util.easemob.comm.wrapper.ResponseWrapper;

import java.io.File;

public interface RestAPIInvoker {
	ResponseWrapper sendRequest(String method, String url, HeaderWrapper header, BodyWrapper body, QueryWrapper query);
	ResponseWrapper sendRequest(String method, String url, HeaderWrapper header, QueryWrapper query);
	ResponseWrapper uploadFile(String url, HeaderWrapper header, File file);
    ResponseWrapper downloadFile(String url, HeaderWrapper header, QueryWrapper query);
}
