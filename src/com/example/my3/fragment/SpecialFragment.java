package com.example.my3.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.my3.R;
import com.example.my3.adapter.SpecialAdater;
import com.example.my3.db.Mp3InfoDao;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SpecialFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LayoutInflater inflate = LayoutInflater.from(getActivity());
		View view = inflate.inflate(R.layout.fragment_special, null);

		ListView lv = (ListView) view.findViewById(R.id.special_list);
		Mp3InfoDao dao = new Mp3InfoDao(getActivity());
		List<HashMap<String, String>> list_special = new ArrayList<HashMap<String, String>>();
		try {
			list_special = dao.quraySpecialFromSQL();
		} catch (Exception e) {
			List<String> list1 = dao.quray("special");
			list_special = dao.quraySpecial(list1);
		}
		SpecialAdater adapter = new SpecialAdater(list_special, getActivity());
		lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		return view;
	}

}
