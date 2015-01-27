package com.example.my3.db;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;
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
			// Toast.makeText(context,"mp3Infos.size" +
			// mp3Infos.size(),10000).show();
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

	/*
	 * 查询歌曲的文件列表，artist列表，special列表
	 */
	public List<String> quray(String str) {
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = hepler.getReadableDatabase();
		if (db.isOpen()) {
			if (str == "file") {
				
				String sql = "create table mp3_file(_id integer primary key, file varchar(20),  path varchar(60));";
				db.execSQL(sql);
				Cursor c = db.rawQuery("select * from mp3all", null);
				for (c.moveToFirst(); !c.isLast(); c.moveToNext()) {
					String str1 = c.getString(8);
					String str2 = str1.substring(0, str1.lastIndexOf('/'));
					if (!list.contains(str2)) {
						list.add(str2);
						String str3 = str2.substring(str2.lastIndexOf('/')+1);
						Log.i("---Dao", "file--"+str3+"  path----"+str2);
						db.execSQL("insert into mp3_file(file,path) values(?,?)", new String[]{str3,str2});
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

	/*
    *按照作者得到歌手的歌曲数目 
    */
	public List<HashMap<String, String>> qurayArtistSongNum(List<String> list) {
		List<HashMap<String, String>> list_return = new ArrayList<HashMap<String, String>>();
		SQLiteDatabase db = hepler.getReadableDatabase();
		String sql = "create table mp3_artist(_id integer primary key,artist varchar(20),artist_num varchar(20))";
		db.execSQL(sql);
		for (String artist : list) {
			HashMap<String, String> map = new HashMap<String, String>();
			Cursor c = db.rawQuery("select * from mp3all where artist = ?",
					new String[] { artist });
			int count = c.getCount();
			String count_str = String.valueOf(count);
			map.put("artist", artist);
			map.put("artist_num", count_str);
			db.execSQL("insert into mp3_artist(artist,artist_num) values(?,?)", new Object[]{artist,count_str});
			list_return.add(map);
		}
		return list_return;
	}
	/*
	 * 按照歌曲的专辑来查询专辑中的歌曲的数目和作者
	 */
	public List<HashMap<String, String>> quraySpecial(List<String> list){
		List<HashMap<String, String>> list_return = new ArrayList<HashMap<String, String>>();
		SQLiteDatabase db = hepler.getReadableDatabase();
		String sql = "create table mp3_ablum(_id integer primary key,special varchar(20),artist_num varchar(20),artist varchar(20))";
		db.execSQL(sql);
		for (String special : list) {
			HashMap<String, String> map = new HashMap<String, String>();
			Cursor c = db.rawQuery("select * from mp3all where album = ?",
					new String[] { special });
			int count = c.getCount();
			String count_str = String.valueOf(count);
			map.put("special", special);
			map.put("artist_num", count_str);
			c.moveToFirst();
			map.put("artist", c.getString(2));
			db.execSQL("insert into mp3_ablum(special,artist_num,artist) values(?,?,?)", new Object[]{special,count_str,c.getString(2)});
			list_return.add(map);
		}
		return list_return;
		
	} 
	/*
	 * 加载数据库中的文件夹的信息
	 */
	public List<HashMap<String,String>> qurayFileFromSQL(){
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		SQLiteDatabase db = hepler.getReadableDatabase();
		Cursor c = db.rawQuery("select * from mp3_file", null);
		for (c.moveToFirst(); !c.isLast(); c.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			String str1 = c.getString(1);
			String str2 = c.getString(2);
			Log.i("---DaoSQL", "str1: "+str1+"str2  "+str2);
			map.put("file", str1);
			map.put("filePath", str2);
			list.add(map);
		}
		return list;		
	}
	/*
	 * 加载数据库中的专辑的信息
	 */
	public List<HashMap<String,String>> quraySpecialFromSQL(){
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		SQLiteDatabase db = hepler.getReadableDatabase();
		Cursor c = db.rawQuery("select * from mp3_ablum", null);
		for (c.moveToFirst(); !c.isLast(); c.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			String str1 = c.getString(1);
			String str2 = c.getString(2);
			String str3 = c.getString(3);
			Log.i("---DaoSQLSpecial", "str1: "+str1+"str2  "+str2+"  str3 "+str3);
			map.put("special", str1);
			map.put("artist_num", str2);
			map.put("artist", str3);
			list.add(map);
		}
		return list;		
	}
	/*
	 * 加载数据库中的歌手的信息
	 */
	public List<HashMap<String,String>> qurayArtistFromSQL(){
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		SQLiteDatabase db = hepler.getReadableDatabase();
		Cursor c = db.rawQuery("select * from mp3_artist", null);
		for (c.moveToFirst(); !c.isLast(); c.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			String str1 = c.getString(1);
			String str2 = c.getString(2);
			Log.i("---DaoSQLSpecial", "str1: "+str1+"str2  "+str2);
			map.put("artist", str1);
			map.put("artist_num", str2);
			list.add(map);
		}
		return list;		
	}
}
