package com.example.my3.fragment;

import java.util.List;

import com.example.my3.R;
import com.example.my3.activity.AllMusicActivity;
import com.example.my3.adapter.FileAdapter;
import com.example.my3.db.Mp3InfoDao;
import com.example.my3.util.MediaScanner;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FileFragment extends Fragment {
	private View view;
	private ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_file, null);
		initView();
		return view;
	}

	void initView() {
		lv = (ListView) view.findViewById(R.id.file_list);
		Mp3InfoDao dao = new Mp3InfoDao(getActivity());
		List<String> list = dao.quray("file");
		FileAdapter fileAdapter = new FileAdapter(list, getActivity());
		lv.setAdapter(fileAdapter);
		fileAdapter.notifyDataSetChanged();
	}
}
