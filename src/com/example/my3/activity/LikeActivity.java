package com.example.my3.activity;

import com.example.my3.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class LikeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// �����ޱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_like);
	}
}
