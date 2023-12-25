/*
 * Copyright (c) SR1GT my copyright message. 2023-2023. All rights reserved.
 */

package com.os.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class File {
    private String name;
    private String type;
    private Integer size;
    private String content;
    private Date create_time;
    private Date update_time;

    public File() {
        this.name = null;
        this.type = null;
        this.size = 0;
        this.content = "";

        Date date = new Date();
        this.create_time = date;
        this.update_time = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public void details() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("文件名称：" + this.getName() + "\n"
                + "文件类型：" + this.getType() + "\n"
                + "文件大小：" + this.getSize() + "\n"
                + "创建时间：" + sdf.format(this.getCreate_time()) + "\n"
                + "修改时间：" + sdf.format(this.getUpdate_time()) + "\n"
                + "文件内容：\n" + this.getContent());
    }
}
