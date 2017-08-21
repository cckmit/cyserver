package com.cy.core.donation.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.alipay.entity.AlipayResult;
import com.cy.core.donation.entity.Donation;
import com.cy.core.donation.entity.MyDonation;
import com.cy.core.donation.entity.NewDonate;
import com.cy.util.PairUtil;

public interface DonationService {
	void save(Donation donation);

	int saveFromMobile(Donation donation);

	void update(Donation donation);

	void delete(String ids);

	Donation selectById(long id);

	DataGrid<Donation> dataGrid(Map<String, Object> map);

	DataGrid<Donation> dataGridForCount(Map<String, Object> map);

	void updateFromShouXin(Donation donation);

	List<Donation> selectRandom50();

	List<Donation> selectByNameAndPhone(Donation donation);

	MyDonation listAll(Map<String, Object> map);

	NewDonate listNew(Map<String, Object> map);

	Donation selectByOrderNo(String orderNo);

	/**
	 * 查询捐赠项目列表
	 * @param message
	 * @param content
	 */
	void findDonationList(Message message, String content) ;

	/**
	 * 查询最新捐赠项目列表
	 * @param message
	 * @param content
	 */
	void findDonationNewList(Message message, String content) ;

	/**
	 * 查询我的捐赠详情接口
	 * @param message
	 * @param content
	 */
	void findMyDonation(Message message, String content);

	/**
	 * 捐赠订单下单接口
	 * @param message
	 * @param content
	 */
	void saveDonation(Message message, String content);

	void updatePayStatus(Message message, String content);

	/**
	 * 取消订单接口
	 * @param message
	 * @param content
	 */
	void cancleDonate(Message message, String content);
	/**
	 * 确认发货接口
	 * @param message
	 * @param content
	 */
	void updateDonationStatus(Message message, String content);

	/**
	 * 订单支付回调接口
	 * @param alipayResult
	 */
	PairUtil<Integer,String> donationPayFeedBack(AlipayResult alipayResult) ;

	Donation wechatDonationPayBack(String orderNo);

	void  updatePayType(String orderNo,String payType,String payMethod);

	//生成捐赠证书
	int createCertificate(long donationId);
	void saveFounIncome(Message message,String content);
	void updateFounIncome(Message message,String content);
	void deleteFounIncome(Message message,String content);


}
