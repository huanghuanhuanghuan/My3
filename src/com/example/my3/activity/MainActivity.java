package com.example.my3.activity;

import java.util.List;

import com.example.my3.R;
import com.example.my3.db.Mp3InfoDao;
import com.example.my3.dialog.MyProgressDialog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private TextView allmusic;
	private TextView recent;
	private TextView like;
	private TextView network;
	private TextView player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();//初始化界面
	}

	void initView() {
		allmusic = (TextView) findViewById(R.id.allmusic);
		recent = (TextView) findViewById(R.id.recent);
		like = (TextView) findViewById(R.id.like);
		network = (TextView) findViewById(R.id.internet);
		player = (TextView) findViewById(R.id.player);

		allmusic.setOnClickListener(this);
		recent.setOnClickListener(this);
		like.setOnClickListener(this);
		network.setOnClickListener(this);
		player.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.allmusic:
			Intent intent1 = new Intent(this, AllMusicActivity.class);
			this.startActivity(intent1);
			break;
		case R.id.recent:
			Intent intent2 = new Intent(this, RecentActivity.class);
			this.startActivity(intent2);
			break;
		case R.id.like:
			Intent intent3 = new Intent(this, LikeActivity.class);
			this.startActivity(intent3);
			break;
		case R.id.internet:
			Intent intent4 = new Intent(this, InternetActivity.class);
			this.startActivity(intent4);
			break;
		case R.id.player:
			Intent intent5 = new Intent(this, PlayerActivity.class);
			this.startActivity(intent5);
			break;
		}
	}
}
