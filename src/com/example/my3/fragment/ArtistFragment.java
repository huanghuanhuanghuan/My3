package com.example.my3.fragment;

import java.util.HashMap;
import java.util.List;

import com.example.my3.R;
import com.example.my3.adapter.ArtistAdapter;
import com.example.my3.adapter.FileAdapter;
import com.example.my3.db.Mp3InfoDao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ArtistFragment extends Fragment {
	private View view ;
	private ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_artist, null);
		initView();
		return view;
	}

	void initView() {
		lv = (ListView) view.findViewById(R.id.artist_list);
		Mp3InfoDao dao = new Mp3InfoDao(getActivity());
		List<String> list = dao.quray("artist");
		List<HashMap<String, String>> list1 = dao.qurayArtistSongNum(list);
		ArtistAdapter artistAdapter = new ArtistAdapter(getActivity(),list1);
		lv.setAdapter(artistAdapter);
		artistAdapter.notifyDataSetChanged();
	}
}
