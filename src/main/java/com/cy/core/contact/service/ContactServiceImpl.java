package com.cy.core.contact.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.contact.dao.ContactMapper;
import com.cy.core.contact.entity.Contact;

@Service("contactService")
public class ContactServiceImpl implements ContactService {
	@Autowired
    private ContactMapper contactMapper;

    public DataGrid<Contact> dataGrid(Map<String, Object> map) {
        DataGrid<Contact> dataGrid = new DataGrid<Contact>();
        long total = contactMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<Contact> list = contactMapper.query(map);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public Contact getById(long id) {
        return contactMapper.getById(id);
    }


    public void reply(Contact contact) {
        if (contact == null)
            throw new IllegalArgumentException("contact cannot be null!");

        contactMapper.reply(contact);
    }


    public void delete(String ids) {
    	String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
		contactMapper.delete(list);
    }

}
