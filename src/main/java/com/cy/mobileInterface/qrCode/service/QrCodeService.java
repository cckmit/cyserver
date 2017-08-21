package com.cy.mobileInterface.qrCode.service;

import com.cy.base.entity.Message;

public interface QrCodeService {
	void getUserInfoByAccountNum(Message message, String content);

	void getUserQRCodeAddress(Message message, String content, String address);
}
