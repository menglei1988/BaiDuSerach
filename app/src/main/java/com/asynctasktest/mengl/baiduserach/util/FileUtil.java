package com.asynctasktest.mengl.baiduserach.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * Title: FileUtil<br>
 * Description: �ļ�����������<br>
 * Company: CAREERS<br>
 * Copyright @ 2013 CAREERS .All rights reserved.<br>
 * Depend : TODO
 * 
 * @author mengl
 * @Modified by
 * @CreateDate 2014-8-20
 * @Version
 * @Revision
 * @ModifiedDate
 */
public class FileUtil {

	/**
	 * Title: getSDcardPath<br>
	 * Description: ��ȡ�ڴ�洢��·��<br>
	 * Depend : TODO <br>
	 * 
	 * @return
	 * @author mengl
	 * @Modified by
	 * @CreateDate 2014-8-20
	 * @Version
	 */
	public static String getDbStorePath() {
		String path = Environment.getDataDirectory() + "/data/"
				+ "com.example.fragmenttest" + "/";
		return path;
	}

	/**
	 * Title: getSDcardPath<br>
	 * Description: ��ȡ��ݴ洢��·��,sd������<br>
	 * Depend : TODO <br>
	 * 
	 * @return
	 * @author mengl
	 * @Modified by
	 * @CreateDate 2014-8-20
	 * @Version
	 */
	public static String getSDcardPath() {
		String path = Environment.getDataDirectory() + "/data/"
				+ "com.example.fragmenttest" + "/";

		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/";

		}
		return path;
	}

	/**
	 * 获取内置SD卡路径
	 * @return
	 */
	public static String getInnerSDCardPath() {
		String path =  Environment.getExternalStorageDirectory().getPath();
		return path;
	}

	/**
	 * 获取外置SD卡路径
	 * @return	应该就一条记录或空
	 */
	public static List getExtSDCardPath()
	{
		List lResult = new ArrayList();
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("extSdCard"))
				{
					String [] arr = line.split(" ");
					String path = arr[1];
					File file = new File(path);
					if (file.isDirectory())
					{
						lResult.add(path);
					}
				}
			}
			isr.close();
		} catch (Exception e) {
		}
		return lResult;
	}


	
	public static String getServerIp() {
		String ip = "http://123.57.233.190:8080";
		return ip;
	}
	public static String testIp() {
		String ip = "http://192.168.1.114:8088";
		return ip;
	}
	
	public static boolean saveBitmap(Bitmap bitmap,String storePath)
	{
//		if(null == bitmap || quality < 0 || quality > 100 || null == storePath || "".equals(storePath)) {
//			return false;
//		}
		File f = new File(storePath);
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
			stream.flush();
			stream.close();
//			bitmap.recycle();
			System.gc();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			if(null != f && f.exists()) {
				f.delete();
			}
		}
		return false;
	}
	
	//删除指定文件夹下所有文件
	//param path 文件夹完整绝对路径
	   public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	     }
	   
	   
	 //删除文件夹
	 //param folderPath 文件夹完整绝对路径
	   public static void delFolder(String folderPath) {
		     try {
		        delAllFile(folderPath); //删除完里面所有内容
		        String filePath = folderPath;
		        filePath = filePath.toString();
		        File myFilePath = new File(filePath);
		        myFilePath.delete(); //删除空文件夹
		     } catch (Exception e) {
		       e.printStackTrace(); 
		     }
		}
	   
	   public static Bitmap compressImage(Bitmap image) {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
			int options = 100;
			while ( baos.toByteArray().length / 1024>200) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩		
				baos.reset();//重置baos即清空baos
				image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
				options -= 5;//每次都减少10
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
			return bitmap;
		}
	}
	

