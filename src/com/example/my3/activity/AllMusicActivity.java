package com.example.my3.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.my3.R;
import com.example.my3.fragment.ArtistFragment;
import com.example.my3.fragment.DownLoadFragment;
import com.example.my3.fragment.FileFragment;
import com.example.my3.fragment.SpecialFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

public class AllMusicActivity extends FragmentActivity{
	private List<Fragment> fragments;
	private ViewPager viewpager;
	private List<String> title;
	private MyFragmentViewPageAdapter adapter;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置无标题
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.activity_musicall);
	    
	    initView();
	}
	void initView(){
		viewpager = (ViewPager)findViewById(R.id.viewpager);
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
	
	public class MyFragmentViewPageAdapter extends FragmentPagerAdapter{

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
}
