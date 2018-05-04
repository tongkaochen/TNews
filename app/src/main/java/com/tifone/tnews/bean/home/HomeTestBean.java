package com.tifone.tnews.bean.home;

import java.util.UUID;

/**
 * Created by tongkao.chen on 2018/5/4.
 */

public class HomeTestBean {
    public String name;
    public String id;
    public HomeTestBean(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
