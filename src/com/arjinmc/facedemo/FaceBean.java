package com.arjinmc.facedemo;

/**
 * @usage 自定义表情对象
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 * @create 2014年12月26日
 */
public class FaceBean {

	/**表情的图片名称*/
	private String imageName;
	/**表情的字符串信息*/
	private String key;
	
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
