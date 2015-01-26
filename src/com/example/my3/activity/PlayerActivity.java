package com.example.my3.activity;

import com.example.my3.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class PlayerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_player);
	}
}
