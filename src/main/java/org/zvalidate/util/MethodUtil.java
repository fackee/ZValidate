package org.zvalidate.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xz on 2017/8/28.
 */
public class MethodUtil {

    private final Map<Parameter,List<Annotation>> parameterAnnotationMap =
            new HashMap<>(10);


    //获取函数中的参数与其带有的注解映射
    public  Map<Parameter,List<Annotation>> getParameterAnnotationMap(Method method){
        Parameter[] parameters = method.getParameters();

        for(Parameter parameter : parameters){
            if(parameter.getAnnotations() != null |
                    parameter.getAnnotations().length > 0){
                Annotation[] annotations = parameter.getAnnotations();
                List<Annotation> annotaionList = new ArrayList<>(annotations.length);
                for(Annotation annotation : annotations){
                    annotaionList.add(annotation);
                }
                parameterAnnotationMap.put(parameter,annotaionList);
            }
        }
        return parameterAnnotationMap;
    }




}
