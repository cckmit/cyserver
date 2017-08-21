package com.cy.common.utils.wechat.entity;

import java.util.Arrays;

/**
 * 复杂按钮（父按钮）
 * 
 * @author 孙海峰
 * @date 2013-08-08
 */
public class ComplexButton extends Button {
        private Button[] sub_button;

        public Button[] getSub_button() {
                return sub_button;
        }

        public void setSub_button(Button[] sub_button) {
                this.sub_button = sub_button;
        }

        @Override
        public String toString() {
                return "ComplexButton{" +
                        "sub_button=" + Arrays.toString(sub_button) +
                        '}';
        }
}