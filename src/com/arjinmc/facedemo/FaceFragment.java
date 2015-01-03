package com.arjinmc.facedemo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.arjinmc.utils.ViewHolder;
import com.arjinmc.widget.MyGridView;

/**
 * @usage 表情显示fragment
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 * @create 2014年12月26日
 */
public class FaceFragment extends Fragment {

	private MyGridView gridview;
	private List<FaceBean> faces = new ArrayList<FaceBean>();
	private GridViewAdapter adapter;
	private Handler handler;
	
	public FaceFragment(List<FaceBean> faces,Handler handler) {
		this.faces = faces;
		this.handler = handler;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_face, null);
		gridview = (MyGridView) view.findViewById(R.id.gridview);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		adapter = new GridViewAdapter();
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Message msg = new Message();
				msg.obj = faces.get(position).getKey();
				handler.sendMessage(msg);
			}
		});
		super.onViewCreated(view, savedInstanceState);
	}
	
	
	private class GridViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return faces.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return faces.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_face, null);
			}
			
			ImageView ivFace = ViewHolder.get(convertView, R.id.item_face);
			InputStream is = null;
			try {
				is = getActivity().getAssets().open("face"
						+File.separator+faces.get(position).getImageName()+".png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(is!=null){
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				ivFace.setImageBitmap(bitmap);
			}
			
			return convertView;
		}
		
	}
}
