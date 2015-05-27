package com.example.mysidebar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mysidebar.adapter.ContactAdapter;
import com.example.mysidebar.model.Contact;
import com.example.mysidebar.utils.PinyinComparator;
import com.example.mysidebar.utils.PinyinUtils;
import com.example.mysidebar.widget.SideBar;

/**
 * 主界面
 * 
 * @author owen
 */
public class MainActivity extends Activity implements SideBar.onLetterTouchedChangeListener {

	private TextView textViewDialog = null;
	private SideBar sideBar = null;
	private ListView listView = null;
	
	private ContactAdapter contactAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
	}

	private void initView() {
		textViewDialog = (TextView) findViewById(R.id.textViewDialog);
		sideBar = (SideBar) findViewById(R.id.siderbar);
		sideBar.setTextViewDialog(textViewDialog);
		sideBar.setOnLetterTouchedChangeListener(this);
		
		contactAdapter = new ContactAdapter(MainActivity.this, generateContacts());
		listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(contactAdapter);
	}

	/**
	 * 生成联系人数据
	 */
	private List<Contact> generateContacts() {
		String[] contactArray = getResources().getStringArray(R.array.contacts);
		List<Contact> contacts = new ArrayList<Contact>(contactArray.length);
		
		for (int i = 0; i < contactArray.length; i++) {
			try {
				Contact contact = new Contact();
				
				contact.setName(contactArray[i]);
				
				String firstLetter = PinyinUtils.getPinyinOfHanyu(contactArray[i]).substring(0, 1).toUpperCase();
				if (firstLetter.matches("[A-Z]")) {
					contact.setFirstLetter(firstLetter);
				} else {
					contact.setFirstLetter("#");
				}
				
				contacts.add(contact);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		}
		
		// 对联系人列表按照 ABCDEFG...# 的顺序进行排序
		Collections.sort(contacts, new PinyinComparator());
		
		return contacts;
	}
	
	@Override
	public void onTouchedLetterChange(String letterTouched) {
		// 联系人列表随着导航栏的滑动而滑动到相应的位置
		int position = contactAdapter.getPositionForSection(letterTouched.charAt(0));
		if (position != -1) {
			listView.setSelection(position);
		}
	}

}
