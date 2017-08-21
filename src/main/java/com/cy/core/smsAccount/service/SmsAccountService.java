package com.cy.core.smsAccount.service;

import com.cy.base.entity.Message;
import com.cy.core.smsAccount.entity.SmsAccount;


/**
 * Created by cha0res on 11/17/16.
 */
public interface SmsAccountService {

    SmsAccount getByAlumniId(String alumniId );

    Message saveAccount(SmsAccount smsAccount );

    Message updateAccount( SmsAccount smsAccount );

    Message findSmsAccount( SmsAccount smsAccount );

    String sendSms( String alumniId, String mobile, String content );

}
