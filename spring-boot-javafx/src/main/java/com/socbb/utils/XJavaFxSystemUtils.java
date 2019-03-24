package com.socbb.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

@Slf4j
public class XJavaFxSystemUtils {

    /**
     * @Title: addJarByLibs
     * @Description: 添加libs中jar包到系统中
     */
    public static void addJarByLibs() {
        try {
            // 系统类库路径
            File libPath = new File("libs/");
            // 获取所有的.jar和.zip文件
            File[] jarFiles = libPath.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".jar") || name.endsWith(".zip");
                }
            });
            if (jarFiles != null) {
                // 从URLClassLoader类中获取类所在文件夹的方法
                // 对于jar文件，可以理解为一个存放class文件的文件夹
                Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                boolean accessible = method.isAccessible(); // 获取方法的访问权限
                try {
                    if (accessible == false) {
                        method.setAccessible(true); // 设置方法的访问权限
                    }
                    // 获取系统类加载器
                    URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                    for (File file : jarFiles) {
                        URL url = file.toURI().toURL();
                        try {
                            method.invoke(classLoader, url);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    method.setAccessible(accessible);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
