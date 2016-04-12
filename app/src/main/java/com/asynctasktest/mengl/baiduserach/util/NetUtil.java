package com.asynctasktest.mengl.baiduserach.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.os.Message;

public class NetUtil {
	public static void NetGet(final String path,final Handler handler){
		new Thread() {
			public void run() {
				try {
					URL url = new URL(
							FileUtil.getServerIp()+path);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(10000);
					conn.setRequestProperty(
							"User-Agent",
							"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; "
									+ "Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; "
									+ ".NET CLR 3.0.30729; Media Center PC 6.0; Shuame)");
					int responseCode = conn.getResponseCode();
					if (responseCode == 200) {
						InputStream inputStream = conn.getInputStream();

						ByteArrayOutputStream outStream = new ByteArrayOutputStream();
						byte[] buffer = new byte[1024];
						int len = -1;
						while ((len = inputStream.read(buffer)) != -1) {
							outStream.write(buffer, 0, len);
						}
						String result = new String(outStream.toByteArray(),
								"utf-8");

						// Bitmap bitmap =
						// BitmapFactory.decodeStream(inputStream);
						// iv.setImageBitmap(bitmap);
						// ���ô�����Ϣ��ģʽ ��view������Ϣ�������߳�
						Message msg = new Message();
						msg.what = 1;
						msg.obj = result;
						handler.sendMessage(msg);
					}
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
					
				}
			}
		}.start();
	}
}
