package com.arjinmc.facedemo;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.TextView;

import com.arjinmc.facedemo.R;

public class MainActivity extends FragmentActivity {
	
	private EditText edittext;
	private TextView textview;
	private RadioGroup layoutDots;
	private Button send;
	private ViewPager viewpager;
	private List<FaceFragment> fragments;
	
	private Handler faceHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			String key = (String)msg.obj;
			if(key.equals(FaceUtil.DELE_KEY)){
				//删除内容
				deleOneString();
			}else{
				int edittextCursor = edittext.getSelectionStart();
				Editable editable = edittext.getText();  
				editable.insert(edittextCursor, key);  
				int newCursor = edittextCursor+key.length();
				edittext.setText(editable);
				edittext.setSelection(newCursor);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	private void init(){
		edittext  = (EditText) findViewById(R .id.edittext);
		textview = (TextView) findViewById(R.id.textview);
		send = (Button) findViewById(R.id.send);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		layoutDots = (RadioGroup) findViewById(R.id.layout_dots);
		
		fragments = new ArrayList<FaceFragment>();
		List<FaceBean> allFaces = FaceUtil.getFaceList(MainActivity.this);
		for(int i=0;i<3;i++){
			List<FaceBean> tempList = new ArrayList<FaceBean>();
			tempList = allFaces.subList(20*i, 20*(i+1));
			List<FaceBean> pageFaces = new ArrayList<FaceBean>();
			pageFaces.addAll(tempList);
			FaceBean lastBean = new FaceBean();
			lastBean.setKey(FaceUtil.DELE_KEY);
			lastBean.setImageName(FaceUtil.DELE_IMAGE_NAME);
			pageFaces.add(lastBean);
			if (tempList!= null && tempList.size()>1 ) {
				FaceFragment faceFragment = new FaceFragment(pageFaces,faceHandler);
				fragments.add(faceFragment);
			}
			layoutDots.addView(createRadioButton());
		}
		
		ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		viewpager.setAdapter(pagerAdapter);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				RadioButton radioButton = (RadioButton) layoutDots.getChildAt(position);
				radioButton.setChecked(true);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				textview.setText(edittext.getText().toString());
				edittext.setText("");
			}
		});
		
		//默认选中第一页
		RadioButton radioButton = (RadioButton) layoutDots.getChildAt(0);
		radioButton.setChecked(true);
		
	}
	
	/**
	 * @usage 创建新的点
	 * @author eminem
	 * @email eminem@hicsg.com
	 * @website arjinmc.com
	 * @create 2014年12月26日
	 */
	private RadioButton createRadioButton(){
		RadioButton button = new RadioButton(MainActivity.this);
		RadioGroup.LayoutParams params = new LayoutParams(20, 20);
		params.rightMargin = 5;
		button.setLayoutParams(params);
		button.setButtonDrawable(new BitmapDrawable());
		button.setBackgroundResource(R.drawable.selector_radiobuton);
		return button;
	}
	
	private class ViewPagerAdapter extends FragmentPagerAdapter{

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.size();
		}
		
	}
	
	/**
	 * @usage 删除editetext上的字符串或者表情
	 * @author eminem
	 * @email eminem@hicsg.com
	 * @website arjinmc.com
	 * @create 2014年12月26日
	 */
	private void deleOneString(){
		int edittextCursor = edittext.getSelectionStart();
		Editable editable = edittext.getText();  
		String tempString = edittext.getText().toString().substring(0, edittextCursor);
		int end = tempString.lastIndexOf("]");
		if(end==edittextCursor-1){
			int start = tempString.lastIndexOf("[");
			if(start!=-1){
				editable.delete(start, edittextCursor); 
			}else if(edittextCursor!=0){
				editable.delete(edittextCursor-1, edittextCursor); 
			}
		}else if(edittextCursor!=0){
			editable.delete(edittextCursor-1, edittextCursor); 
		}
	}

}
