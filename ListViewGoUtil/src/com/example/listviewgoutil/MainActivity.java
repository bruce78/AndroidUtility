package com.example.listviewgoutil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button [] button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = new Button[5];
		button[0] = (Button) findViewById(R.id.simple_l);
		button[1] = (Button) findViewById(R.id.simple_title);
		button[2] = (Button) findViewById(R.id.simple_pic);
		button[3] = (Button) findViewById(R.id.simple_viewholder);
		button[4] = (Button) findViewById(R.id.simple_sparseArr);
		for (int i = 0; i < button.length; i++) {
			button[i].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, ListViewdemoActivity.class);
					intent.setFlags(Integer.parseInt(v.getTag().toString()));
					startActivity(intent);
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
