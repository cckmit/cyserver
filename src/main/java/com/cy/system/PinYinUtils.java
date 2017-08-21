package com.cy.system;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字转拼音工具类
 * @author dengqiao
 *
 */
public class PinYinUtils {
	public static String getPinYin(String str){
		char[] ch=str.toCharArray();
		String en="";
		for(int i=0;i<ch.length;i++){
			HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
			hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
			hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
			hanyuPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
			try {
				if(isChinese(ch[i])){
					String[] s=PinyinHelper.toHanyuPinyinStringArray(ch[i], hanyuPinyinOutputFormat);
					en+=s[0].substring(0, 1);
				}else{
					en+=String.valueOf(ch[i]).toLowerCase();
				}
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		}
		return en;
		
	}
	
	  private static boolean isChinese(char c) {
	        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
	        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
	            return true;
	        }
	        return false;
	    }
	  
	  public static String getQuanPin(String str){
			char[] ch=str.toCharArray();
			String en="";
			for(int i=0;i<ch.length;i++){
				HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
				hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
				hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
				hanyuPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
				try {
					if(isChinese(ch[i])){
						String[] s=PinyinHelper.toHanyuPinyinStringArray(ch[i], hanyuPinyinOutputFormat);
						if(s!=null){
							en+=s[0];
						}
					}else{
						en+=String.valueOf(ch[i]).toLowerCase();
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			}
			return en;
			
		}
	  
	
	public static void main(String[] args) {
		System.out.println(getQuanPin("邓乔(@)"));
	}
}
