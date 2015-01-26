package com.example.my3.util;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.example.my3.demo.Mp3Info;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

public class MediaInformation {
	/**
	 * 用于从数据库中查询歌曲的信息，保存在List当中
	 * 
	 * @return
	 */
	public static List<Mp3Info> getMp3Infos(Context context) {
		//新建一个查询对象
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		
		List<Mp3Info> mp3Infos = new ArrayList<Mp3Info>();
		//新建一个用来保存Mp3信息列表的对象
		for (int i = 0; i < cursor.getCount(); i++) {
			//不断循环，直到所有的文件都被遍历一遍
			cursor.moveToNext();
			//下移一位
			Mp3Info mp3Info = new Mp3Info();
			//新建一个可以用来保存一首歌曲的对象
			//获得音乐的一系列信息
			long id = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media._ID));	//音乐id
			String title = cursor.getString((cursor	
					.getColumnIndex(MediaStore.Audio.Media.TITLE))); // 音乐标题
			String artist = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.ARTIST)); // 艺术家
			String album = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.ALBUM));	//专辑
			String displayName = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
			long albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
			long duration = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media.DURATION)); // 时长
			long size = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media.SIZE)); // 文件大小
			String url = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DATA)); // 文件路径
			int isMusic = cursor.getInt(cursor
					.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)); // 是否为音乐
			if (isMusic != 0) { 
				// 只把音乐添加到集合当中
				mp3Info.setId(id);
				mp3Info.setTitle(title);
				mp3Info.setArtist(artist);
				mp3Info.setAlbum(album);
				mp3Info.setDisplayName(displayName);
				mp3Info.setAlbumId(albumId);
				mp3Info.setDuration(duration);
				mp3Info.setSize(size);
				mp3Info.setUrl(url);
				//将每首歌的信息加载到MP3Info的对象中去
				mp3Infos.add(mp3Info);
				//将每一首歌曲加载到歌曲的信息列表中去
			}
		}
		return mp3Infos;
		//将信息列表返回
	}
	
	/**
	 * 往List集合中添加Map对象数据，每一个Map对象存放一首音乐的所有属性
	 * @param mp3Infos
	 * @return
	 */
	public static List<HashMap<String, String>> getMusicMaps(List<Mp3Info> mp3Infos) 
	//将从手机音乐数据库中得到的信息转化为HashMap格式的键值对
	{
		List<HashMap<String, String>> mp3list = new ArrayList<HashMap<String, String>>();
		//新建一个键值对的list用来存放转换后的MP3信息
		for (Iterator iterator = mp3Infos.iterator(); iterator.hasNext();) //定义iterator首相得到MP3List的第一项，如果还有下一项的话就要继续执行。
		{
			Mp3Info mp3Info = (Mp3Info) iterator.next();
			//定义要转化的HashMapList的子项
			HashMap<String, String> map = new HashMap<String, String>();
			//将每一首歌的信息以键值对的形式存进去
			map.put("title", mp3Info.getTitle());
			map.put("Artist", mp3Info.getArtist());
			map.put("album", mp3Info.getAlbum());
			map.put("displayName", mp3Info.getDisplayName());
			map.put("albumId", String.valueOf(mp3Info.getAlbumId()));
			map.put("duration", formatTime(mp3Info.getDuration()));
			map.put("size", String.valueOf(mp3Info.getSize()));
			map.put("url", mp3Info.getUrl());
			//将得到的每一个HashMap存进MP3LIst当中去
			mp3list.add(map);
		}
		return mp3list;//返回转化完格式后的MP3列表
	}
	/**
	 * 格式化时间，将毫秒转换为分:秒格式
	 * @param time
	 * @return
	 */
	public static String formatTime(long time) {
		String min = time / (1000 * 60) + "";//后面加上一个“”表示将long型的数据强制转化为String类型
		String sec = time % (1000 * 60) + "";
		//如果计算得到的分钟不足两位数的话就要在前面加上一个“0”，否则的话的不用了
		if (min.length() < 2)
		 {
			min = "0" + time / (1000 * 60) + "";
		}
		 else 
		 {
			min = time / (1000 * 60) + "";
		}
		//因为1000*60=60000为5位数字，所以要保证数据的结果能够比较准确，就要将他们都转化为5为数的
		if (sec.length() == 4) 
		{
			sec = "0" + (time % (1000 * 60)) + "";
		} 
		else if (sec.length() == 3) 
		{
			sec = "00" + (time % (1000 * 60)) + "";
		}
		 else if (sec.length() == 2)
		 {
			sec = "000" + (time % (1000 * 60)) + "";
		}
		 else if (sec.length() == 1) 
		{
			sec = "0000" + (time % (1000 * 60)) + "";
		}
		//最后格式化地返回，截取分钟的前两位
		return min + ":" + sec.trim().substring(0, 2);
	}
	/**
	 * 获得所有歌手
	 */
	public List<String> getAllArtist (List<Mp3Info> mp3Infos)
	{
		List<String> artist = new ArrayList<String>();
		for (Iterator iterator = mp3Infos.iterator(); iterator.hasNext();)
		{
			Mp3Info mp3Info = (Mp3Info) iterator.next();
			artist.add(mp3Info.getArtist());
		}
		//得到所有的歌手信息
		for(int i=0;i<artist.size();i++)
		{
			String str1 = artist.get(i);
			for(int j=i+1;j<artist.size();j++)
			{
				String str2 = artist.get(j);
				if(str1.endsWith(str2))
				{
					artist.remove(j);
					j--;
				}
			}
		}
		return artist;
	}
	/**
	 * 获得所有的文件路径
	 */
	
	public List<String> getAllFile (List<Mp3Info> mp3Infos)
	{
		List<String> files = new ArrayList<String>();
		for (Iterator iterator = mp3Infos.iterator(); iterator.hasNext();)
		{
			Mp3Info mp3Info = (Mp3Info) iterator.next();
			files.add(mp3Info.getUrl().substring(0, mp3Info.getUrl().lastIndexOf("/")));
		}
		//得到所有的歌手信息
		for(int i=0;i<files.size();i++)
		{
			String str1 = files.get(i);
			for(int j=i+1;j<files.size();j++)
			{
				String str2 = files.get(j);
				if(str1.endsWith(str2))
				{
					files.remove(j);
					j--;
				}
			}
		}
		return files;
	}
}
