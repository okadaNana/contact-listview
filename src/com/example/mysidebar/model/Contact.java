package com.example.mysidebar.model;


/**
 * 实体类：一个联系人
 * 
 * @author owen
 */
public class Contact {

	/**
	 * 联系人姓名
	 */
	private String name = null;

	/**
	 * 姓名首字母
	 */
	private String firstLetter = null;

	public Contact() {
	}

	public Contact(String name, String firstLetter) {
		this.name = name;
		this.firstLetter = firstLetter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

}
