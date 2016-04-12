package com.asynctasktest.mengl.baiduserach;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.webkit.WebView;

import com.asynctasktest.mengl.baiduserach.util.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by mengl on 16-4-6.
 */
public class ProgressDAsyncTask  extends AsyncTask{

    ProgressDialog progressDialog;
    String path;
    WebView wv;
    String content = "";

    public ProgressDAsyncTask(ProgressDialog progressDialog,WebView wv,String path) {
        super();
        this.progressDialog = progressDialog;
        this.path = path;
        this.wv = wv;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        for(int i=1;i<=41;i++){
            try {
                String j = String.valueOf(i);
                ZipFile zipFile = new ZipFile( FileUtil.getInnerSDCardPath()+"/baidusearch/htmls"+j+".zip");
                ZipEntry ze = zipFile.getEntry(path+".htm");

                if(ze!=null){
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(zipFile.getInputStream(ze)));


                    String line;
                    while ((line = br.readLine()) != null) {
                        content = content+line;
                    }

                    break;
                }

//            if(file.exists()){
//                wv.loadUrl( "file:///"+FileUtil.getInnerSDCardPath()+"/baidusearch/htmls"+j+"/htmls"+j+"/"+path+".htm");
//                break;
//            }
            } catch (IOException e) {
                continue;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        wv.loadData(content, "text/html; charset=UTF-8", null);
        progressDialog.dismiss();
    }

    //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }


    @Override
    protected void onProgressUpdate(Object[] values) {
    }
}
