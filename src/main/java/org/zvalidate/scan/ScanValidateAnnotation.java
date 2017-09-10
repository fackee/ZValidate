package org.zvalidate.scan;

import org.zvalidate.algorithms.Algorithms;
import org.zvalidate.proxy.AddCustomAnnotationProxy;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ScanValidateAnnotation
 *
 * @author ZhuJianXin
 * @date 2017/9/3
 */
public class ScanValidateAnnotation {

    //jdk动态代理添加自定义注解
    public static void initValidateAnnotationList()throws ClassNotFoundException, IllegalAccessException, InstantiationException{
        List<Class<?>> algorithmsList = getAlgorithmsList();
        for(Class<?> clazz : algorithmsList){
            Algorithms algorithms = (Algorithms) Proxy.newProxyInstance(
                    Algorithms.class.getClassLoader(),
                    new Class[]{Algorithms.class},
                    new AddCustomAnnotationProxy(Class.forName(clazz.getName()).newInstance()));
            algorithms.addAlgorithmsAnnotationList();
        }
    }

    //jdk动态代理添加自定义注解及对应的校验算法映射
    public static void initValidateAnnotationMap() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<Class<?>> algorithmsList = getAlgorithmsList();
        for(Class<?> clazz : algorithmsList){
            Algorithms algorithms = (Algorithms) Proxy.newProxyInstance(
                    Algorithms.class.getClassLoader(),
                    new Class[]{Algorithms.class},
                    new AddCustomAnnotationProxy(Class.forName(clazz.getName()).newInstance()));
            algorithms.addAlgorithmsAnnotationMap();
        }
    }

    public static List<Class<?>> getAlgorithmsList(){
        return  ScanClass.getClassList()
                .stream()
                .filter(clazz->
                        !clazz.isInterface() && clazz!=null && Algorithms.class.isAssignableFrom(clazz))
                .collect(Collectors.toList());
    }

}
