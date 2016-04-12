package com.asynctasktest.mengl.baiduserach;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import com.asynctasktest.mengl.baiduserach.util.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by mengl on 16-3-17.
 */
public class WebActivity extends Activity {

    WebView wv;
    String path;

    private ProgressDialog _processBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        wv = (WebView) findViewById(R.id.wv1);
        wv.getSettings().setDefaultTextEncodingName("UTF-8");
        path = this.getIntent().getStringExtra("path").toString();
        _processBar = new ProgressDialog(this);
        _processBar.setMessage("百度百科资料加载中，请稍后。。。");

//        int i=1;
//        String loadpath = "file:///"+FileUtil.getInnerSDCardPath()+"/baidusearch/htmls"+i+"/htmls"+i+"/"+path+".htm";
//        File file = new File(loadpath);
//        if(file.exists())





//        String loadpath = "file:///"+FileUtil.getInnerSDCardPath()+"/baidusearch/htmls1/htmls1"+path+".htm";
//        File file = new File("");
//        if(!file.exists())
//            loadpath = "file:///"+FileUtil.getInnerSDCardPath()+"/baidusearch/htmls30/htmls30/"+path+".htm";
//        wv.loadUrl(loadpath);
    }

    private String fmtString(String str){
        String notice = "";
        try{
            notice = URLEncoder.encode(str, "utf-8");
        }catch(UnsupportedEncodingException ex){
        }
        return notice;
    }

    @Override
    protected void onResume() {
        super.onResume();

        ProgressDAsyncTask asyncTask = new ProgressDAsyncTask(_processBar,wv,path);
        asyncTask.execute();
    }
}
