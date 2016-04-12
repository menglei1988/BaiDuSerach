package com.asynctasktest.mengl.baiduserach;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by mengl on 16-3-16.
 */

@Table(name = "tbl1")
public class CiTiao {

    @Column(name = "name" )
    private String name;

    @Column(name = "path")
    private String path;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path ) {
        this.path = path;
    }
}
