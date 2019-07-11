package com.xingray.sample.page.main;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing@baidu.com
 * @date : 2019/7/11 16:27
 */
public class Test {
    private String name;
    private String id;

    public Test(String name, String id) {
        this.name = name;
        this.id = id;
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

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
