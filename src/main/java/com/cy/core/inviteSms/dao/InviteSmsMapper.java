package com.cy.core.inviteSms.dao;

import com.cy.core.inviteSms.entity.InviteSms;

import java.util.List;
import java.util.Map;

public interface InviteSmsMapper {
    InviteSms selectByTerAndTee(Map<String, String> map);
    void saveInviteEvent(InviteSms inviteSms);
    String isClassAdmin(String inviterId);
}
