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
	 * ���ڴ����ݿ��в�ѯ��������Ϣ��������List����
	 * 
	 * @return
	 */
	public static List<Mp3Info> getMp3Infos(Context context) {
		//�½�һ����ѯ����
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		
		List<Mp3Info> mp3Infos = new ArrayList<Mp3Info>();
		//�½�һ����������Mp3��Ϣ�б�Ķ���
		for (int i = 0; i < cursor.getCount(); i++) {
			//����ѭ����ֱ�����е��ļ���������һ��
			cursor.moveToNext();
			//����һλ
			Mp3Info mp3Info = new Mp3Info();
			//�½�һ��������������һ�׸����Ķ���
			//������ֵ�һϵ����Ϣ
			long id = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media._ID));	//����id
			String title = cursor.getString((cursor	
					.getColumnIndex(MediaStore.Audio.Media.TITLE))); // ���ֱ���
			String artist = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.ARTIST)); // ������
			String album = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.ALBUM));	//ר��
			String displayName = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
			long albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
			long duration = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media.DURATION)); // ʱ��
			long size = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media.SIZE)); // �ļ���С
			String url = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DATA)); // �ļ�·��
			int isMusic = cursor.getInt(cursor
					.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)); // �Ƿ�Ϊ����
			if (isMusic != 0) { 
				// ֻ��������ӵ����ϵ���
				mp3Info.setId(id);
				mp3Info.setTitle(title);
				mp3Info.setArtist(artist);
				mp3Info.setAlbum(album);
				mp3Info.setDisplayName(displayName);
				mp3Info.setAlbumId(albumId);
				mp3Info.setDuration(duration);
				mp3Info.setSize(size);
				mp3Info.setUrl(url);
				//��ÿ�׸����Ϣ���ص�MP3Info�Ķ�����ȥ
				mp3Infos.add(mp3Info);
				//��ÿһ�׸������ص���������Ϣ�б���ȥ
			}
		}
		return mp3Infos;
		//����Ϣ�б���
	}
	
	/**
	 * ��List���������Map�������ݣ�ÿһ��Map������һ�����ֵ���������
	 * @param mp3Infos
	 * @return
	 */
	public static List<HashMap<String, String>> getMusicMaps(List<Mp3Info> mp3Infos) 
	//�����ֻ��������ݿ��еõ�����Ϣת��ΪHashMap��ʽ�ļ�ֵ��
	{
		List<HashMap<String, String>> mp3list = new ArrayList<HashMap<String, String>>();
		//�½�һ����ֵ�Ե�list�������ת�����MP3��Ϣ
		for (Iterator iterator = mp3Infos.iterator(); iterator.hasNext();) //����iterator����õ�MP3List�ĵ�һ����������һ��Ļ���Ҫ����ִ�С�
		{
			Mp3Info mp3Info = (Mp3Info) iterator.next();
			//����Ҫת����HashMapList������
			HashMap<String, String> map = new HashMap<String, String>();
			//��ÿһ�׸����Ϣ�Լ�ֵ�Ե���ʽ���ȥ
			map.put("title", mp3Info.getTitle());
			map.put("Artist", mp3Info.getArtist());
			map.put("album", mp3Info.getAlbum());
			map.put("displayName", mp3Info.getDisplayName());
			map.put("albumId", String.valueOf(mp3Info.getAlbumId()));
			map.put("duration", formatTime(mp3Info.getDuration()));
			map.put("size", String.valueOf(mp3Info.getSize()));
			map.put("url", mp3Info.getUrl());
			//���õ���ÿһ��HashMap���MP3LIst����ȥ
			mp3list.add(map);
		}
		return mp3list;//����ת�����ʽ���MP3�б�
	}
	/**
	 * ��ʽ��ʱ�䣬������ת��Ϊ��:���ʽ
	 * @param time
	 * @return
	 */
	public static String formatTime(long time) {
		String min = time / (1000 * 60) + "";//�������һ��������ʾ��long�͵�����ǿ��ת��ΪString����
		String sec = time % (1000 * 60) + "";
		//�������õ��ķ��Ӳ�����λ���Ļ���Ҫ��ǰ�����һ����0��������Ļ��Ĳ�����
		if (min.length() < 2)
		 {
			min = "0" + time / (1000 * 60) + "";
		}
		 else 
		 {
			min = time / (1000 * 60) + "";
		}
		//��Ϊ1000*60=60000Ϊ5λ���֣�����Ҫ��֤���ݵĽ���ܹ��Ƚ�׼ȷ����Ҫ�����Ƕ�ת��Ϊ5Ϊ����
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
		//����ʽ���ط��أ���ȡ���ӵ�ǰ��λ
		return min + ":" + sec.trim().substring(0, 2);
	}
	/**
	 * ������и���
	 */
	public List<String> getAllArtist (List<Mp3Info> mp3Infos)
	{
		List<String> artist = new ArrayList<String>();
		for (Iterator iterator = mp3Infos.iterator(); iterator.hasNext();)
		{
			Mp3Info mp3Info = (Mp3Info) iterator.next();
			artist.add(mp3Info.getArtist());
		}
		//�õ����еĸ�����Ϣ
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
	 * ������е��ļ�·��
	 */
	
	public List<String> getAllFile (List<Mp3Info> mp3Infos)
	{
		List<String> files = new ArrayList<String>();
		for (Iterator iterator = mp3Infos.iterator(); iterator.hasNext();)
		{
			Mp3Info mp3Info = (Mp3Info) iterator.next();
			files.add(mp3Info.getUrl().substring(0, mp3Info.getUrl().lastIndexOf("/")));
		}
		//�õ����еĸ�����Ϣ
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
