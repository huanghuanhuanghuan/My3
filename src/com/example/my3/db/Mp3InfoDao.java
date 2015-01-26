package com.example.my3.db;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

import com.example.my3.demo.Mp3Info;
import com.example.my3.util.MediaInformation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class Mp3InfoDao {
	private MyDataBaseOpenHelper hepler;

	public Mp3InfoDao(Context context) {
		super();
		hepler = new MyDataBaseOpenHelper(context, "Mp3", null, 1);
	}

	/*
	 * 添加手机中的所有数据
	 */
	public void insertall(Context context) {
		SQLiteDatabase db = hepler.getWritableDatabase();
		if (db.isOpen()) {
			MediaInformation m = new MediaInformation();
			List<Mp3Info> mp3Infos = m.getMp3Infos(context);
			Toast.makeText(context,"mp3Infos.size" + mp3Infos.size(),10000).show();
			for (int i = 0; i < mp3Infos.size(); i++) {
				Mp3Info mp3Info = mp3Infos.get(i);
				insert(context, mp3Info);
			}
		}
		db.close();
	}

	/*
	 * 增加单行数据
	 */
	public void insert(Context context, Mp3Info mp3Info) {
		SQLiteDatabase db = hepler.getWritableDatabase();
		if (db.isOpen()) {
			String title = mp3Info.getTitle();
			String artist = mp3Info.getArtist();
			String album = mp3Info.getAlbum();
			String displayname = mp3Info.getDisplayName();
			long albumId = mp3Info.getAlbumId();
			long duration = mp3Info.getDuration();
			long size = mp3Info.getSize();
			String url = mp3Info.getUrl();
			db.execSQL(
					"insert into mp3all(title,artist,album,displayname,albumId,duration,size,url) values(?,?,?,?,?,?,?,?);",
					new Object[] { title, artist, album, displayname, albumId,
							duration, size, url });
		}
		db.close();
	}

	public List<String> quray(String str) {
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = hepler.getReadableDatabase();
		if (db.isOpen()) {
			if (str == "file") {
				Cursor c = db.rawQuery("select * from mp3all", null);
				for (c.moveToFirst(); !c.isLast(); c.moveToNext()) {
					String str1 = c.getString(8);
					String str2 = str1.substring(0, str1.lastIndexOf('/'));
					if (!list.contains(str2)) {
						list.add(str2);
					}
				}
			} else if (str == "artist") {
				Cursor c = db.rawQuery("select * from mp3all", null);
				for (c.moveToFirst(); !c.isLast(); c.moveToNext()) {
					String str1 = c.getString(2);
					list.add(str1);
				}
			} else {
				Cursor c = db.rawQuery("select * from mp3all", null);
				for (c.moveToFirst(); !c.isLast(); c.moveToNext()) {
					String str1 = c.getString(3);
					list.add(str1);
				}
			}
		}
		db.close();
		return list;
	}
}
