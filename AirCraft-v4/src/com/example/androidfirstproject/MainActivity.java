package com.example.androidfirstproject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

//界面
public class MainActivity extends Activity {
	// Activity运行呢的线程称之为主线程，又叫UI线程
	// UI线程规则：不能用其他线程直接更新UI

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 调用应用上下文环境(this)
		// MyView mv = new MyView(getApplicationContext());
		MySurfaceView sfv = new MySurfaceView(getApplicationContext());
		// 参数为Android视图对象View
		setContentView(sfv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
