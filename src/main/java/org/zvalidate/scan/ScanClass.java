package org.zvalidate.scan;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by xz on 2017/8/27.
 */
public class ScanClass {

    private static final String PACKAGE_PATH_POINT = ".";

    private static final String PACKAGE_PATH_SEPARATOR = "/";

    private static final String URL_PROTOCAL_FILE = "file";

    private static final String URL_PROTOCAL_JAR = "jar";

    private final static String CLASS_FILE_END = ".class";

    private final static String CLASS_FILE_POINT = PACKAGE_PATH_POINT;

    private static final ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();


    public static  ClassLoader getCurrentClassLoader(){
        return currentClassLoader;
    }

    //扫描所有类
    public static final List<Class<?>> getClassList(){
        List<Class<?>> result = new ArrayList<>();
        //扫描框架自身Jar中的所有类
        result.addAll(IteratorClassList("org/zvalidate"));
        //扫描当前项目中的所有类
        result.addAll(IteratorClassList(new File(currentClassLoader
               .getResource("").getPath()).list()[0]));
        return result;
    }

    //扫描jar包和当前类加载器
    private static final List<Class<?>> IteratorClassList(String packageName){
        List<Class<?>> classList = new ArrayList<Class<?>>();
        try{
            Enumeration<URL> urlList = getCurrentClassLoader()
                    .getResources(packageName);
            while (urlList.hasMoreElements()){
                URL url = urlList.nextElement();

                if(url != null){
                    String protocol = url.getProtocol();

                    if(URL_PROTOCAL_FILE.equals(protocol)){
                        String packagePath = url.getPath().replaceAll("%20", " ");

                        addClass(classList,packagePath,packageName);
                    }else if(URL_PROTOCAL_JAR.equals(protocol)){
                        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                        while (jarEntryEnumeration.hasMoreElements()){
                            JarEntry jarEntry = jarEntryEnumeration.nextElement();
                            String jarName = jarEntry.getName();
                            if(jarName.endsWith(".class")){
                                jarName = jarName.replaceAll(PACKAGE_PATH_SEPARATOR,PACKAGE_PATH_POINT)
                                        .replace(".class","");
                                classList.add(Class.forName(jarName,false,getCurrentClassLoader()));
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return classList;
    }

    //递归添加所有类
    private static void addClass(List<Class<?>> classList,String packagePath,String packageName){
        try{
            File[] fileList = new File(packagePath).listFiles(file ->
                    file.isFile() && file.getName().endsWith(CLASS_FILE_END) || file.isDirectory()
            );

            for(File file : fileList){
                String fileName = file.getName();

                if(file.isFile()){
                    String className = fileName.substring(0,fileName.lastIndexOf(CLASS_FILE_POINT));
                    if(className.length() > 0){
                        className = packageName + PACKAGE_PATH_POINT + className;
                    }
                    Integer start = className.lastIndexOf("classes/");
                    className = className.substring(start+8,className.length()).replaceAll(PACKAGE_PATH_SEPARATOR,PACKAGE_PATH_POINT);
                    Class<?> clazz = loadClass(className,false);
                    classList.add(clazz);
                }else{
                    String subPackagePath = fileName;
                    if(!packagePath.endsWith("/")){
                        subPackagePath = packagePath + PACKAGE_PATH_SEPARATOR + subPackagePath;
                    }else{
                        subPackagePath = packagePath + subPackagePath;
                    }

                    String subPackageName = fileName;
                    if(!packagePath.endsWith("/")){
                        subPackageName = packagePath + PACKAGE_PATH_POINT + subPackageName;
                    }else{
                        subPackageName = packagePath.substring(0,packagePath.length()-1) + PACKAGE_PATH_POINT + subPackageName;
                    }
                    addClass(classList,subPackagePath,subPackageName);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Class<?> loadClass(String className, boolean initialize) {
        Class<?> clazz = null;
        try{
            clazz = Class.forName(className,initialize,getCurrentClassLoader());
        }catch (Exception e){
            e.printStackTrace();
        }
        return clazz;
    }
}
