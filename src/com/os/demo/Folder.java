/*
 * Copyright (c) SR1GT my copyright message. 2023-2023. All rights reserved.
 */

package com.os.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class Folder {   // 文件夹类，使用二叉树
    private Folder parent;
    private String name;
    private ArrayList<Folder> children;
    private ArrayList<File> files;

    // region 构造方法与getter、setter
    public Folder() {
        this.parent = null;
        this.name = null;
        this.children = new ArrayList<>();
        this.files = new ArrayList<>();
    }

    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Folder> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Folder> children) {
        this.children = children;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    // endregion
    // region 文件夹操作
    // 按名称查询文件夹
    public Folder selectFolder(String name) {
        for (Folder folder : this.getChildren()) {
            if (folder.getName().equals(name)) {
                return folder;
            }
        }
        return null;
    }

    // 获取当前目录
    public StringBuilder getCurrentDirectory() {
        Folder tmp = this;
        ArrayList<String> path = new ArrayList<>();
        while (tmp != null) {
            path.add("/" + tmp.getName());
            tmp = tmp.getParent();
        }
        Collections.reverse(path);
        StringBuilder sb = new StringBuilder();
        for (String name : path) {
            sb.append(name);
        }
        return sb;
    }

    // 查看文件夹内容
    public void show() {
        System.out.print("当前目录：");
        System.out.println(this.getCurrentDirectory());

        System.out.print("文件夹：");
        for (Folder folder : this.getChildren()) {
            System.out.print(folder.getName() + " ");
        }
        System.out.println();

        System.out.print("文件：");
        for (File file : this.getFiles()) {
            System.out.print(file.getName() + "." + file.getType() + " ");
        }
        System.out.println();
    }

    // 打开文件夹
    public Folder openFolder(String name) {
        Folder result = this.selectFolder(name);
        if (result != null) {
            return result;
        }
        System.out.println("文件夹" + name + "不存在，打开失败！");
        return this;
    }

    // 新建文件夹
    public void createFolder(String name) {
        if (this.selectFolder(name) != null) {
            System.out.println("文件夹" + name + "已存在，无法新建！");
            return;
        }
        Folder folder = new Folder();
        folder.setParent(this);
        folder.setName(name);
        this.children.add(folder);
        System.out.println("新文件夹 " + name + " 新建成功");
    }

    // 重命名文件夹
    public void renameFolder(String oldName, String newName) {
        Folder result_old = this.selectFolder(oldName);
        if (result_old == null) {
            System.out.println("文件夹" + oldName + "不存在，重命名失败！");
            return;
        }
        Folder result_new = this.selectFolder(newName);
        if (result_new != null) {
            System.out.println("文件夹" + oldName + "已存在，重命名失败！");
            return;
        }
        result_old.setName(newName);
        System.out.println("文件夹" + oldName + "已成功重命名为" + newName);
    }

    // 删除文件夹
    public void deleteFolder(String name) {
        Folder result = this.selectFolder(name);
        if (result == null) {
            System.out.println("文件夹" + name + "不存在，删除失败！");
            return;
        }
        if (result.children.size() != 0 || result.files.size() != 0) {
            System.out.print("文件夹" + name + "中存在文件/文件夹，删除该文件夹后其中文件无法保留，是否继续删除（y/n）：");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.next();
            if (choice.charAt(0) == 'n') {
                System.out.println("删除取消！");
                return;
            }
            System.out.println("已确认，正在删除...");
        }
        this.children.remove(result);
        System.out.println("文件夹" + name + "已成功删除！");
    }

    // endregion
    // region 文件操作
    // 按名称与类型查找文件
    public File selectFile(String name, String type) {
        for (File file : this.getFiles()) {
            if (file.getName().equals(name) && file.getType().equals(type)) {
                return file;
            }
        }
        return null;
    }

    // 查看文件详情
    public void showFile(String name, String type) {
        File file = this.selectFile(name, type);
        if (file == null) {
            System.out.println("文件" + name + "." + type + "不存在！");
            return;
        }
        file.details();
    }

    // 新建文件
    public void createFile(String name, String type) {
        File result = this.selectFile(name, type);
        if (result != null) {
            System.out.println("文件" + name + "." + type + "已存在！");
            return;
        }
        File file = new File();
        file.setName(name);
        file.setType(type);
        this.files.add(file);
        System.out.println("文件" + name + "." + type + "新建成功！");
    }

    // 重命名文件
    public void renameFile(String oldName, String newName, String type) {
        File result_old = this.selectFile(oldName, type);
        if (result_old == null) {
            System.out.println("文件" + oldName + "." + type + "不存在！");
            return;
        }
        File result_new = this.selectFile(newName, type);
        if (result_new != null) {
            System.out.println("文件" + newName + "." + type + "已存在！");
            return;
        }
        result_old.setName(newName);
        result_old.setUpdate_time(new Date());
        System.out.println("文件" + oldName + "." + type + "已成功修改为" + newName + "." + type);
    }

    // 修改文件类型
    public void retypeFile(String oldType, String newType, String name) {
        File result_old = this.selectFile(name, oldType);
        if (result_old == null) {
            System.out.println("文件" + name + "." + oldType + "不存在！");
            return;
        }
        File result_new = this.selectFile(name, newType);
        if (result_new != null) {
            System.out.println("文件" + name + "." + newType + "已存在！");
            return;
        }
        result_old.setType(newType);
        result_old.setUpdate_time(new Date());
        System.out.println("文件" + name + "." + oldType + "已成功修改为" + name + "." + newType);
    }

    // 删除文件
    public void deleteFile(String name, String type) {
        File result = this.selectFile(name, type);
        if (result == null) {
            System.out.println("文件" + name + "." + type + "不存在！");
            return;
        }
        this.files.remove(result);
        System.out.println("文件" + name + "." + type + "已成功删除！");
    }

    // 复制文件
    public void copyFile(String name, String type) {
        File result = this.selectFile(name, type);
        if (result == null) {
            System.out.println("文件" + name + "." + type + "不存在！");
            return;
        }
        for (int i = 1; ; i++) {
            String newName = name + "(" + i + ")";
            if (this.selectFile(newName, type)==null) {
                File file = new File();
                file.setName(newName);
                file.setType(type);
                file.setSize(result.getSize());
                file.setContent(result.getContent());
                this.files.add(file);
                System.out.println("文件" + name + "." + type + "复制结果为" + newName + "." + type);
                return;
            }
        }
    }

    // 编辑文件
    public void editFile(String name, String type) {
        File result = this.selectFile(name, type);
        if (result == null) {
            System.out.println("文件" + name + "." + type + "不存在！");
            return;
        }
        System.out.println("原文件内容：" + result.getContent());
        System.out.println("**********警告：编辑文件仅支持单行字符串覆写操作**********");
        System.out.print("输入新内容：");
        Scanner scanner = new Scanner(System.in);
        String content = scanner.nextLine();
        System.out.println("保存中...");
        result.setContent(content);
        result.setSize(content.length());
        result.setUpdate_time(new Date());
        System.out.println("文件" + name + "." + type + "已成功写入以下内容：" + content);
    }
    // endregion
}
