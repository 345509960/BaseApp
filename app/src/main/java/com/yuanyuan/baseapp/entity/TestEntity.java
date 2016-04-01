package com.yuanyuan.baseapp.entity;

/**
 * Created by Administrator on 2016/4/1.
 */
public class TestEntity
{
    private String name;
    private String password;

    public TestEntity(String name, String password) {
        this.name = name;
        this.password = password;
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
}
