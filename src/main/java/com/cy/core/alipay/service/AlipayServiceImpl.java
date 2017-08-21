package com.cy.core.alipay.service;

import org.springframework.stereotype.Service;

/**
 * <p>Title: AlipayServiceImpl</p>
 * <p>Description: </p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-10-10 12:11
 */
@Service("alipayService")
public class AlipayServiceImpl implements AlipayService {

    /**
     *	支付回调业务处理（同步回调）
     * @param out_trade_no	订单号
     * @param trade_no			交易号
     * @return
     */
    public String alipayResult(String out_trade_no ,String trade_no) {
//		String backUrl = "redirect:"+ Global.getAdminPath()+"/user/account/form?flag=my&message=";
//		Recharge recharge = rechargeService.getByOrderNumber(out_trade_no) ;
//		recharge.setTradeNo(trade_no);
//		PairUtil<String,String> pairUtil = rechargeService.recharge(recharge) ;
//		backUrl += URLEncoder.encode(pairUtil.getTwo()) ;
//		return backUrl ;

        System.out.println("-------> 支付成功!!!!!!!!!!!! ");
        return "" ;
    }
}
