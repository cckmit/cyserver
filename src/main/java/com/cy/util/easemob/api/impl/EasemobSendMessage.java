package com.cy.util.easemob.api.impl;

import com.cy.util.easemob.api.EasemobRestAPI;
import com.cy.util.easemob.api.SendMessageAPI;
import com.cy.util.easemob.comm.wrapper.BodyWrapper;
import com.cy.util.easemob.comm.constant.HTTPMethod;
import com.cy.util.easemob.comm.helper.HeaderHelper;
import com.cy.util.easemob.comm.wrapper.HeaderWrapper;

public class EasemobSendMessage extends EasemobRestAPI implements SendMessageAPI {
    private static final String ROOT_URI = "/messages";

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }

    public Object sendMessage(Object payload) {
        String  url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        BodyWrapper body = (BodyWrapper) payload;

        return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
    }
}
