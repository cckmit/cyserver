package com.cy.core.smsAccount.dao;

import com.cy.core.smsAccount.entity.AppUser;
import com.cy.core.smsAccount.entity.SmsAccount;

import java.util.Map;

/**
 * Created by cha0res on 11/17/16.
 */
public interface SmsAccountMapper
{
    SmsAccount getByAlumniId( String alumniId );

    void saveAccount( SmsAccount smsAccount );

    void updateAccount( SmsAccount smsAccount );

    void clearAccount( String alumniId);

    AppUser getAppUser( String id );

    void saveAppUser( AppUser appUser );

    void updateAppUser ( AppUser appUser );

    void clearAppUser( String alumniId );

}
