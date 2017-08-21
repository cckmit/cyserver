package com.cy.core.dataMining.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.core.dataMining.entity.DataMining;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jiangling on 8/4/16.
 */
@Namespace("/dataMining")
@Action(value = "dataMiningAction")
public class DataMiningAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(DataMiningAction.class);

    private DataMining dataMining;

}
