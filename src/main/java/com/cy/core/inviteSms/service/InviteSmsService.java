package com.cy.core.inviteSms.service;


import com.cy.base.entity.Message;

public interface InviteSmsService {
    void sendInviteSms(Message message, String content);
}
