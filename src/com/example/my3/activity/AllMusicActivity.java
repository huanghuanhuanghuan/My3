package com.example.my3.activity;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.my3.R;
import com.example.my3.db.Mp3InfoDao;
import com.example.my3.fragment.ArtistFragment;
import com.example.my3.fragment.DownLoadFragment;
import com.example.my3.fragment.FileFragment;
import com.example.my3.fragment.SpecialFragment;
import com.example.my3.util.MediaScanner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;

public class AllMusicActivity extends FragmentActivity {
	private List<Fragment> fragments;
	private ViewPager viewpager;
	private List<String> title;
	private MyFragmentViewPageAdapter adapter;
	private SharedPreferences share;
	private SharedPreferences.Editor edit;
	public static ProgressDialog pd1;
	private Context context = this;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_musicall);
		init();
		//initView();
	}

	void init() {
		share = getSharedPreferences("count", MODE_WORLD_WRITEABLE);
		edit = share.edit();
		int count = share.getInt("num", 0);
		if (count == 0) {
			// 搜索歌曲，创建进度对话框
			//show();
			Log.i("AllMusicActivity", "正在执行异步任务");
			UpdataSQL updata = new UpdataSQL();
			updata.execute(new Long(1));
		}
		else{
			initView();
		}
		count++;
		edit.putInt("num", count);
		edit.commit();
	}

	public void ProgressDialogshow() {
		pd1 = new ProgressDialog(this);
		pd1.setTitle("任务执行中.....");
		pd1.setMessage("正在扫描歌曲中，请耐心等待....");
		pd1.setCancelable(true);
		pd1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd1.show();
	}
	public void ProgressDialogDismiss(){
		pd1.dismiss();
	}

	void initView() {
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		fragments = new ArrayList<Fragment>();
		title = new ArrayList<String>();
		title.add("文件夹");
		title.add("歌手");
		title.add("专辑");
		title.add("下载");
		fragments.add(new FileFragment());
		fragments.add(new ArtistFragment());
		fragments.add(new SpecialFragment());
		fragments.add(new DownLoadFragment());
		adapter = new MyFragmentViewPageAdapter(getSupportFragmentManager());
		viewpager.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	public class MyFragmentViewPageAdapter extends FragmentPagerAdapter {

		public MyFragmentViewPageAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return title.get(position);
		}

	}
	
	class UpdataSQL extends AsyncTask<Long, Integer, String>{

		@Override
		protected String doInBackground(Long... params) {
			MediaScanner scanner = new MediaScanner(context);
			String[] filePath = {"/sdcard/","/sdcard0/RM/res/song/"};
			//String type = "audio/mp3";
			String type = ".mp3";
			scanner.ScannerFiles(filePath, type);
			Mp3InfoDao dao = new Mp3InfoDao(context);
			dao.insertall(context);
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			initView();
			ProgressDialogDismiss();
			//Log.i("AllMusicActivity", "pd1.dismiss()");
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ProgressDialogshow();
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
	}
}
