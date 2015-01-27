package com.example.my3.util;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.provider.MediaStore.Files;

/*
 * ContentProvide更新类
 */
public class MediaScanner {

	private MediaScannerConnection mediaScancon = null;
	private MusicScannerConnectionClient client = null;
	private String filePath = null;
	private String fileType = null;
	private String[] filePaths = null;

	/*
	 * 构建构造方法，用来生成新的对象，并将处理的方法加载到连接中去
	 */
	public MediaScanner(Context context) {
		if (client == null) {
			client = new MusicScannerConnectionClient();
		}
		if (mediaScancon == null) {
			mediaScancon = new MediaScannerConnection(context, client);
		}
	}

	public void ScannerFile(String filePath, String type) {
		this.filePath = filePath;
		this.fileType = type;
		mediaScancon.connect();
	}

	public void ScannerFiles(String[] filesPath, String type) {
		this.filePaths = filesPath;
		this.fileType = type;
		mediaScancon.connect();
	}

	/*
	 * 更新时候传进去的client
	 */
	public class MusicScannerConnectionClient implements
			MediaScannerConnectionClient {

		@Override
		public void onMediaScannerConnected() {
			if (filePath != null) {
				mediaScancon.scanFile(filePath, fileType);
			}
			if (filePaths != null) {
				for (String file_Path : filePaths) {
					mediaScancon.scanFile(file_Path, fileType);
				}
			}
			filePath = null;
			filePaths = null;
			fileType = null;

		}

		@Override
		public void onScanCompleted(String path, Uri uri) {
			// TODO Auto-generated method stub
			mediaScancon.disconnect();
		}

	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String[] getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(String[] filePaths) {
		this.filePaths = filePaths;
	}

}
