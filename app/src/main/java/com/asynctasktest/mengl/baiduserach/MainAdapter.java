package com.asynctasktest.mengl.baiduserach;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mengl on 16-3-16.
 */
public class MainAdapter extends BaseAdapter{

    private List<CiTiao> cts;
    private   Context cont;


    public   MainAdapter(List<CiTiao> cts,Activity cont){
        this.cts = cts;
        this.cont = cont;
    }

    @Override
    public int getCount() {
        return cts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private final class ListItemView {
        private TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemView listItemView = null;
        if (convertView == null) {
            listItemView = new ListItemView();

            convertView = View.inflate(cont, R.layout.layout, null);
            listItemView.name = (TextView) convertView.findViewById(R.id.tv1);
            convertView.setTag(listItemView);
        }else{
            listItemView = (ListItemView) convertView.getTag();
        }

        listItemView.name.setText(cts.get(position).getName());
        return convertView;
    }
}
