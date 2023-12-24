package com.os.demo;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Application {
    public static void help() {
        System.out.println("cat name type                  查看文件name.type详情。\n" +
                "cd ..                          返回上一级文件夹。\n" +
                "cd name                        打开文件夹name。\n" +
                "copy name type                 复制文件name.type。\n" +
                "edit name type                 编辑文件name.type。\n" +
                "find name                      查找文件夹name。\n" +
                "find name type                 查找文件name.type。\n" +
                "help                           查看命令帮助文档\n" +
                "ls                             查看当前文件夹内容。\n" +
                "mkdir name                     新建文件夹name。\n" +
                "rm name                        删除文件夹name。\n" +
                "rm name type                   删除文件name.type。\n" +
                "rn old_name new_name           重命名文件夹。\n" +
                "rn old_name new_name type      重命名文件。\n" +
                "rt old_type new_type name：    修改文件类型。\n" +
                "touch name type：              新建文件name.type。");
    }

    public static void main(String[] args) {
        System.out.println("简单文件管理系统启动中...");
        Folder root = new Folder();
        root.setName("root");
        Folder current = root;
        System.out.println("系统启动完成，首次使用可以通过 help 命令查看所有命令");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            if (Objects.equals(command, "exit")) {
                break;
            }
            String[] parts = command.split(" ");
            switch (parts[0]) {
                case "cat":
                    current.showFile(parts[1], parts[2]);
                    break;
                case "cd":
                    if (Objects.equals(parts[1], "..")) {
                        if (current.getParent() == null) {
                            System.out.println("已到达根目录！");
                        } else {
                            current = current.getParent();
                        }
                    } else {
                        current = current.openFolder(parts[1]);
                    }
                    break;
                case "copy":
                    current.copyFile(parts[1], parts[2]);
                    break;
                case "edit":
                    current.editFile(parts[1], parts[2]);
                    break;
                case "find":
                    if (parts.length == 2) {
                        Folder result = current.selectFolder(parts[1]);
                        if (result == null) {
                            System.out.println("文件夹" + parts[1] + "不存在！");
                        } else {
                            System.out.println("查找结果：" + result.getName());
                        }
                    } else {
                        File result = current.selectFile(parts[1], parts[2]);
                        if (result == null) {
                            System.out.println("文件" + parts[1] + "." + parts[2] + "不存在！");
                        } else {
                            System.out.println("文件" + parts[1] + "." + parts[2] + "存在！");
                        }
                    }
                    break;
                case "help":
                    help();
                    break;
                case "ls":
                    current.show();
                    break;
                case "mkdir":
                    current.createFolder(parts[1]);
                    break;
                case "rm":
                    if (parts.length == 2) {
                        current.deleteFolder(parts[1]);
                    } else {
                        current.deleteFile(parts[1], parts[2]);
                    }
                    break;
                case "rn":
                    if (parts.length == 3) {
                        current.renameFolder(parts[1], parts[2]);
                    } else {
                        current.renameFile(parts[1], parts[2], parts[3]);
                    }
                    break;
                case "rt":
                    current.retypeFile(parts[1], parts[2], parts[3]);
                    break;
                case "touch":
                    current.createFile(parts[1], parts[2]);
                    break;
                default:
                    System.out.println("命令错误，可以输入命令help查看帮助文档");
            }
        }
        System.out.println("简单文件管理系统关闭");
    }
}
