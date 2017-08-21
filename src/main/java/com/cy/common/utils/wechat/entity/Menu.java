package com.cy.common.utils.wechat.entity;

import java.util.Arrays;

/**
 * 菜单
 * 
 * @author 捷微团队
 * @date 2013-08-08
 */
public class Menu {
	private Button[] button;

	public Button[] getButton() {
		return button;
	}

	public void setButton(Button[] button) {
		this.button = button;
	}

	@Override
	public String toString() {
		return "Menu{" +
				"button=" + Arrays.toString(button) +
				'}';
	}
}