package com.example.androidfirstproject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

//����
public class MainActivity extends Activity {
	// Activity�����ص��̳߳�֮Ϊ���̣߳��ֽ�UI�߳�
	// UI�̹߳��򣺲����������߳�ֱ�Ӹ���UI

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ����Ӧ�������Ļ���(this)
		// MyView mv = new MyView(getApplicationContext());
		MySurfaceView sfv = new MySurfaceView(getApplicationContext());
		// ����ΪAndroid��ͼ����View
		setContentView(sfv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
