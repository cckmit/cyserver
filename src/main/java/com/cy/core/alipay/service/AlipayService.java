package com.cy.core.alipay.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.dept.entity.Dept;
import com.cy.util.PairUtil;

import java.util.List;
import java.util.Map;


public interface AlipayService {
	/**
	 *	支付回调业务处理（同步回调）
	 * @param out_trade_no	订单号
	 * @param trade_no			交易号
	 * @return
	 */
	public String alipayResult(String out_trade_no ,String trade_no) ;

}
