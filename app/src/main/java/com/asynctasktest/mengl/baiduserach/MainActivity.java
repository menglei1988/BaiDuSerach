package com.asynctasktest.mengl.baiduserach;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.asynctasktest.mengl.baiduserach.util.FileUtil;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.List;

public class MainActivity extends Activity {

    ListView lv1;
    MainAdapter ma;
    List<CiTiao> cts;
    EditText editText;
    ProgressDialog pb;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.Ext.init(this.getApplication());
        System.out.print("");
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()


                .setDbName("aa.db")
                        // 不设置dbDir时, 默认存储在app的私有目录.
                .setDbDir(new File(FileUtil.getInnerSDCardPath() + "/baidusearch")) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.

//                .setDbDir(new File("/storage/sdcard1/baidusearch")) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.

                .setDbVersion(2)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb();
                    }
                });

        final DbManager db = x.getDb(daoConfig);


        lv1 = (ListView) findViewById(R.id.lv1);
        editText = (EditText) findViewById(R.id.et1);

        load("中", db);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String ss = editText.getText().toString();
                if (!ss.equals(""))
                    load(ss, db);
            }
        });
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent webIntent = new Intent(MainActivity.this, WebActivity.class);
                webIntent.putExtra("path", cts.get(position).getPath().toString());
                startActivity(webIntent);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    void load(String text, DbManager db) {
        try {
            cts = db.selector(CiTiao.class).where("name", "LIKE", "%" + text + "%").findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (cts != null && cts.size() > 0) {
            ma = new MainAdapter(cts, this);
            lv1.setAdapter(ma);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.asynctasktest.mengl.baiduserach/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.asynctasktest.mengl.baiduserach/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
