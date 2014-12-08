package com.job.listviewdemo;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	
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
					intent.setFlags(Integer.parseInt(v.getTag().toString()));
					intent.setClass(MainActivity.this, ListViewActivity.class);
					startActivity(intent);
				}
			});
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
