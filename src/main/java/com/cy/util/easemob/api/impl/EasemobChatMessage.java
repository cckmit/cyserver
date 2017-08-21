package com.cy.util.easemob.api.impl;

import com.cy.util.easemob.api.ChatMessageAPI;
import com.cy.util.easemob.api.EasemobRestAPI;
import com.cy.util.easemob.comm.constant.HTTPMethod;
import com.cy.util.easemob.comm.helper.HeaderHelper;
import com.cy.util.easemob.comm.wrapper.HeaderWrapper;
import com.cy.util.easemob.comm.wrapper.QueryWrapper;

public class EasemobChatMessage extends EasemobRestAPI implements ChatMessageAPI {

    private static final String ROOT_URI = "chatmessages";

    public Object exportChatMessages(Long limit, String cursor, String query) {
        String url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        QueryWrapper queryWrapper = QueryWrapper.newInstance().addLimit(limit).addCursor(cursor).addQueryLang(query);

        return getInvoker().sendRequest(HTTPMethod.METHOD_DELETE, url, header, null, queryWrapper);
    }

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }
}
