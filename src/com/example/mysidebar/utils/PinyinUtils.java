package com.example.mysidebar.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音工具类
 * 
 * @author owen
 */
public class PinyinUtils {

	/**
	 * 传入中文，获得该中文的汉语拼音
	 * 
	 * @param chineseStr 需要转换的中文字符串
	 * @return 拼音字符串，且都是大写。或者是长度为0的字符串
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String getPinyinOfHanyu(String chineseStr) throws BadHanyuPinyinOutputFormatCombination {
		StringBuilder zhongWenPinYin = new StringBuilder();   
        char[] inputArray = chineseStr.toCharArray();   
  
        for (int i = 0; i < inputArray.length; i++) {   
            String[] pinYin = PinyinHelper.toHanyuPinyinStringArray(inputArray[i], getDefaultOutputFormat());   

            if (pinYin != null)  // 有转换出拼音    
            	zhongWenPinYin.append(pinYin[0]);   
            else {  // 没有转换出拼音，说明是非中文字符，比如英文、特殊符号等  
                zhongWenPinYin.append(inputArray[i]);
            }
        }
        
        return zhongWenPinYin.toString();
	}

	private static HanyuPinyinOutputFormat getDefaultOutputFormat() {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		
		// 大写
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		// 无声调，比如“中文”的拼音为“zhong wen”
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		// “绿”的拼音为 lu
		format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
		
		return format;
	}
	
}
