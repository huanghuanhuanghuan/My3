package com.example.my3.adapter;

import java.util.HashMap;
import java.util.List;

import com.example.my3.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FileAdapter extends BaseAdapter{

	private List<HashMap<String,String>> list;
	private Context context;
	
	public FileAdapter(List<HashMap<String,String>> list,Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.item_file, null);
		
		TextView textView = (TextView)view.findViewById(R.id.item_file_textView);
		TextView textView_small = (TextView)view.findViewById(R.id.item_file_textView2_small);
		Button bn = (Button)view.findViewById(R.id.item_file_Button);
		final String str = list.get(position).get("file");
		Log.i("FileAdapter", "str---"+str);
		Log.i("FileAdapter", "str2---"+list.get(position).get("filepath"));
		textView.setText(str);
		textView_small.setText(list.get(position).get("filePath"));
		bn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle(str);
				builder.setItems(new String[] {"���ӵ��б�","ɾ��"}, null);
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
		return view;
	}

}
