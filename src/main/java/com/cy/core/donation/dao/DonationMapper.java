package com.cy.core.donation.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.donation.entity.CountPeopleAndMoney;
import com.cy.core.donation.entity.Donation;

public interface DonationMapper {
	List<Donation> selectDonationList(Map<String, Object> map);

	long countDonation(Map<String, Object> map);
	
	List<Donation> selectDonationForCountList(Map<String, Object> map);

	long countDonationForCount(Map<String, Object> map);

	void save(Donation donation);

	void update(Donation donation);

	void delete(List<Long> list);
	
	Donation selectById(long id);
	
	void updateFromShouXin(Donation donation);
	
	List<Donation> selectRandom50();
	
	List<Donation> selectByNameAndPhone(Donation donation);
	
	long countDonationForMobile(Map<String, Object> map);
	
	long countDonationForMobileNew(Map<String, Object> map);
	
	List<Donation> selectDonationForCountMobile(Map<String, Object> map);
	
	List<Donation> selectDonationForCountMobileNew(Map<String, Object> map);
	
	Donation selectByOrderNo(String orderNo);

	/**
	 * 获取该校友最终班级信息
	 */
	Map<String, String> selectClassInfo(String accountNum);

	/**
	 * 更新捐赠支付类型
	 * @param donation
	 */
	void updatePayType(Donation donation);

	/**
	 * 查询人数和金额
	 * @param map
	 */
	CountPeopleAndMoney countDonationMoneyAndPeople(Map<String, Object> map);
	//删除收入（从基金会传过来的）
	void deleteByIncomeId(String incomeId);

}
