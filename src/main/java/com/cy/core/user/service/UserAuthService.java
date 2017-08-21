package com.cy.core.user.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.user.entity.UserAuth;
import com.cy.core.user.entity.UserRole;

/**
 * @author Administrator
 *
 */
public interface UserAuthService
{	
void insert (UserAuth userAuth);

void delete (UserAuth userAuth);

void deletebytoken (String token);

long CheckToken (String token);

void token_refresh(Message message, String content);
void token_refresh(Message message, String content,String token);
String GetAccount(String token);
}
