package com.cy.core.contact.service;

import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.contact.entity.Contact;

public interface ContactService {
	
	DataGrid<Contact> dataGrid(Map<String, Object> map);

	Contact getById(long id);

    void reply(Contact contact);

    void delete(String id);

}
