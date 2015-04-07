package com.arjinmc.facedemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

/**
 * @usage 表情工具类
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 * @create 2014年12月24日
 */
public class FaceUtil {
	/**删除按钮*/
	public static final String DELE_KEY = "[DELETE_ICON]";
	public static final String DELE_IMAGE_NAME="face_delete";
	
	/**
	 * @usage 获取表情图片的路径
	 * @author eminem
	 * @email eminem@hicsg.com
	 * @website arjinmc.com
	 * @create 2014年12月26日
	 * @param imageName
	 * @return
	 */
	public static String getFacePath(String imageName){
		return "face"+File.separator+imageName+".png";
	}

	/**
	 * @usage 获取表情表
	 * @author eminem
	 * @email eminem@hicsg.com
	 * @website arjinmc.com
	 * @create 2014年12月24日
	 * @param context
	 * @return
	 */
	public static Map<String,String> getFaceMap(Context context){
		Map<String,String> faces = new HashMap<String, String>();
		try {  
            InputStream in = context.getResources()
            		.getAssets().open("face_default_string");  
            BufferedReader br = new BufferedReader(new InputStreamReader(in,  
                    "UTF-8"));  
            String str = null;  
            while ((str = br.readLine()) != null) {  
                String[] temp = str.split("===");
                faces.put(temp[0], temp[1]);
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {
        	return faces;
        }
	};
	
	/**
	 * @usage 获取响应表情的图片名称
	 * @author eminem
	 * @email eminem@hicsg.com
	 * @website arjinmc.com
	 * @create 2014年12月25日
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getFaceImageName(Context context,String key){
		Map<String,String> faces = getFaceMap(context);
		return faces.get(key);
	}
	
	/**
	 * @usage 获取所有表情
	 * @author eminem
	 * @email eminem@hicsg.com
	 * @website arjinmc.com
	 * @create 2014年12月25日
	 * @param context
	 * @return
	 */
	public static List<FaceBean> getFaceList(Context context){
		List<FaceBean> faces = new ArrayList<FaceBean>();
		try {  
            InputStream in = context.getResources()
            		.getAssets().open("face_default_string");  
            BufferedReader br = new BufferedReader(new InputStreamReader(in,  
                    "UTF-8"));  
            String str = null;  
            while ((str = br.readLine()) != null) {  
                String[] temp = str.split("===");
                FaceBean tempFace = new FaceBean();
                tempFace.setKey(temp[0]);
                tempFace.setImageName(temp[1]);
                faces.add(tempFace);
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {
        	return faces;
        }
	}
	
	/**
	 * @usage 匹配表情和文字
	 * @author eminem
	 * @email eminem@hicsg.com
	 * @website arjinmc.com
	 * @create 2014年12月26日
	 * @param context
	 * @param str
	 * @return
	 */
	 public static SpannableString getExpressionString(Context context,String str){
	    	SpannableString spannableString = new SpannableString(str);
	    	String machersString = "\\[[\\s\\S]{1,3}\\]";
	        Pattern sinaPatten = Pattern.compile(machersString,Pattern.CASE_INSENSITIVE);
	        try {
	            dealExpression(context,spannableString, sinaPatten,0);
	        } catch (Exception e) {
	            Log.e("dealExpression", e.getMessage());
	            return new SpannableString("");
	        }
	        return spannableString;
	  }
	  
	/**
	 * @usage 把有表情的部分找出来并显示
	 * @author eminem
	 * @email eminem@hicsg.com
	 * @website arjinmc.com
	 * @create 2014年12月26日
	 * @param context
	 * @param spannableString
	 * @param patten
	 * @param start
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	 public static void dealExpression(Context context,SpannableString spannableString, Pattern patten,int start) throws SecurityException, IllegalArgumentException, IllegalAccessException {
	    	Matcher matcher = patten.matcher(spannableString);
			while (matcher.find()) {
	            String key = matcher.group();
	            if (matcher.start() < start) {
	                continue;
	            }
	            String imageName = FaceUtil.getFaceImageName(context, key);
	            Bitmap bitmap = null;
				try {
					bitmap = BitmapFactory.decodeStream(
							context.getAssets().open(getFacePath(imageName)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/**
			 * 因为每一个款式手机的TextView在选软的时候存在一些差异，所以需要做一下适配.
			 * 下面的是这对渲染出来的图片大小再做一次缩放，再根据具体的需求再调整
			 */
			Drawable drawable = new BitmapDrawable(null, bitmap);
			int drawableWidth= drawable.getIntrinsicWidth();
	        float drawableLevel = drawableWidth/10;
	        if(drawableLevel<=1){
	        	drawableLevel=4;
	        }else if(drawableLevel<=2){
	        	drawableLevel=3;
	        }else if(drawableLevel<=3){
	        	drawableLevel=1;
	        }else if(drawableLevel<=4){
	        	drawableLevel=0.8f;
	        }else if(drawableLevel<=5){
	        	drawableLevel=0.6f;
	        }else if(drawableLevel<=6){
	        	drawableLevel=0.4f;
	        }
	        drawable.setBounds(0, 0, (int)(drawableWidth*drawableLevel)
        			, (int)(drawableWidth*drawableLevel));
			
	        //记得这个地方要我们经过调整过后的drawable对象
	        ImageSpan imageSpan = new ImageSpan(drawable);				
	        int end = matcher.start() + key.length();					
	        spannableString.setSpan(imageSpan, matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	        if (end < spannableString.length()) {
	            dealExpression(context,spannableString,  patten, end);
	        }
	        break;
	    }
	}
}
