package com.yuanyuan.baseapp.entity;

import com.lyc.love.baselib.view.recyclerview.SuctionTopDecoration;

/**
 * Created by Administrator on 2016/4/1.
 */
public class TestEntity implements SuctionTopDecoration.SuctionTopDecorationGroup {
    private String name;
    private String password;
    private String type;

    public TestEntity(String name, String password, String type) {
        this.name = name;
        this.password = password;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public String getSuctionTopDecorationGroup() {
        return type;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
