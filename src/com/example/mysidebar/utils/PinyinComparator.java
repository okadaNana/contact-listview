package com.example.mysidebar.utils;

import java.util.Comparator;

import com.example.mysidebar.model.Contact;

/**
 * 拼音排序接口：用来对ListView里面的数据根据ABCDEFG...来排序  
 * 
 * @author owen
 */
public class PinyinComparator implements Comparator<Contact> {

	@Override
	public int compare(Contact cta1, Contact cta2) {
		
        if (cta1.getFirstLetter().equals("#")) {
            return 1;  
        } else if (cta2.getFirstLetter().equals("#")) {  
            return -1;  
        } else {  
            return cta1.getFirstLetter().compareTo(cta2.getFirstLetter());  
        }
	}
	
}
