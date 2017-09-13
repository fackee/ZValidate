package org.zvalidate.algorithms;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Algorithms
 *
 * @author ZhuJianXin
 * @date 2017/9/3
 */
public interface Algorithms<T extends Annotation> {

    static final String VALIDATE_STATUS = "OK";

    //自定义注解集合
    public final List<Class<? extends Annotation>> customAnnotations = new ArrayList<>();

    //自定义注解与注解相对应的过滤算法映射
    public final Map<Class<? extends Annotation>,Class<? extends Algorithms>> ALGORITHMS_HASH_MAP = new ConcurrentHashMap<>();

    //添加自定义注解
    public void addAlgorithmsAnnotationMap();

    //添加自定义注解与相应过滤算法映射
    public void addAlgorithmsAnnotationList();

    //具体过滤算法实现
    public String Validate(String inputVal);
}
