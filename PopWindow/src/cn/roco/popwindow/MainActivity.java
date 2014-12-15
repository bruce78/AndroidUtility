package cn.roco.popwindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	private PopupWindow popupWindow;
	private View parent;
	/**�˵�������ʱ��Ĳ˵���ͼ��*/
	private int[] images = { R.drawable.i1, R.drawable.i2, R.drawable.i3,
			R.drawable.i4, R.drawable.i5, R.drawable.i6, R.drawable.i7,
			R.drawable.i8 };
	/**�˵�������ʱ��Ĳ˵�������*/
	private String[] names = { "����", "�ļ�����", "���ع���", "ȫ��", "��ַ", "��ǩ", "������ǩ",
			"����ҳ��" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		/**PopupWindow�Ľ���*/
		View contentView = getLayoutInflater()
				.inflate(R.layout.popwindow, null);
		/**���񲼾ֽ���*/
		GridView gridView = (GridView) contentView.findViewById(R.id.gridView);
		/**�������񲼾ֵ�������*/
		gridView.setAdapter(getAdapter());
		/**�������񲼾ֵĲ˵�����ʱ���Listener*/
		gridView.setOnItemClickListener(new ItemClickListener());
		/**��ʼ��PopupWindow*/
		popupWindow = new PopupWindow(contentView,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);// ȡ�ý���
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		/**����PopupWindow�������˳�ʱ��Ķ���Ч��*/
		popupWindow.setAnimationStyle(R.style.animation);
		
		parent = this.findViewById(R.id.main);
	}
	
	private final class ItemClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (popupWindow.isShowing()) {
				popupWindow.dismiss();//�ر�
			}
		}
	}
	
	/**�������񲼾ֵ�������*/
	private ListAdapter getAdapter() {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < images.length; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("image", images[i]);
			item.put("name", names[i]);
			data.add(item);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,
				R.layout.grid_item, new String[] { "image", "name" },
				new int[] { R.id.imageView, R.id.textView });
		return simpleAdapter;
	}

	public void openPopWindow(View v) {
		/**����PopupWindow�������λ��*/
		popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	}
}