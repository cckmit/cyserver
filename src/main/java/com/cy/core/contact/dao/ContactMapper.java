package com.cy.core.contact.dao;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.contact.entity.Contact;

public interface ContactMapper {
	
	List<Contact> query(Map<String, Object> map);
	
	List<Contact> querylist(Map<String, Object> map);
	
    long count(Map<String, Object> map);

    Contact getById(long id);
    
    Contact getByleaveId(long id);
    
    int reply(Contact contact);

    void delete(List<Long> list);
}
