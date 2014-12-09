package com.example.listviewgoutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewdemoActivity extends Activity {
	String[] famousNames = { "张三丰", "猪八戒", "罗志祥" };
	String[] title = { "武学宗师", "下凡仙人", "亚洲舞王" };
	int[] imageIds ={R.drawable.zhangsanfeng, R.drawable.zhubajie, R.drawable.luozhixiang};
	List<HashMap<String, Object>> data;
	List<String> dataList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_viewdemo);
		ListView lv = (ListView) findViewById(R.id.list_view_demo_);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "第" + position + "个数据",
						Toast.LENGTH_SHORT).show();
			}
		});
		

		initData();

		Intent intent = getIntent();
		switch (intent.getFlags()) {
		case 0:
			BAdapter adapter0 = new BAdapter();
			lv.setAdapter(adapter0);
			break;
		case 1:
			ArrayAdapter adapter1 = new ArrayAdapter(this, R.layout.row_view_arrayadapter, R.id.tv_array, dataList);
			lv.setAdapter(adapter1);
			break;
		case 2:
			//SimpleAdapter不进行重写，自动使用ConvertView形式进行复用
			SimpleAdapter adapter2 = new SimpleAdapter(this, data,
					R.layout.row_view, 
					new String[] { "name", "title" ,"image"},
					new int[] { R.id.tv_1, R.id.tv_0 , R.id.item_image});
			lv.setAdapter(adapter2);
			break;
		case 3:
			BAdapterViewHolder adapter3 = new BAdapterViewHolder();
			lv.setAdapter(adapter3);
			break;
		case 4:
			BAdapterSparseArray adapter4 = new BAdapterSparseArray();
			lv.setAdapter(adapter4);
			break;

		}
	}
	class BAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return famousNames.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			//convertView用作缓存使用过的View，不再重新创建
			if(convertView==null){
				convertView = ListViewdemoActivity.this.getLayoutInflater().inflate(R.layout.row_view, null);
				Log.i("new", position+"new出来");
			}
			
			Log.i("old", position+"old复用");
			((TextView) convertView.findViewById(R.id.tv_0))
					.setText(famousNames[position]);
			((ImageView) convertView.findViewById(R.id.item_image))
					.setVisibility(View.GONE);
			((TextView) convertView.findViewById(R.id.tv_1)).setVisibility(View.GONE);
			return convertView;
		}

	}
	
	class BAdapterViewHolder extends BaseAdapter{
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = ListViewdemoActivity.this.getLayoutInflater().inflate(R.layout.row_view, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.tv_0);
				holder.title = (TextView) convertView.findViewById(R.id.tv_1);
				holder.image = (ImageView) convertView.findViewById(R.id.item_image);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText((CharSequence) data.get(position).get("name"));
			holder.title.setText((CharSequence) data.get(position).get("title"));
			holder.image.setImageResource((Integer) data.get(position).get("image"));
			
			return convertView;
		}
		
	}
	
	class BAdapterSparseArray extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}

		
	}
	
	private static class ViewHolder{
		private TextView name;
		private TextView title;
		private ImageView image;
	}
	
	private void initData() {
		data = new ArrayList();
		dataList = new ArrayList();
		for (int i = 0; i < title.length; i++) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("name", famousNames[i]);
			hm.put("title", title[i]);
			hm.put("image", imageIds[i]);
			data.add(hm);
			dataList.add(famousNames[i]);
		}
	}
}
