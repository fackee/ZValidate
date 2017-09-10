package org.zvalidate.scan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ScanClassesWihtValidateAnnotation
 *
 * @author ZhuJianXin
 * @date 2017/9/3
 */
public class ScanClassesWithValidateAnnotation {
    private final List<Class> classWithAnnotationList;

    private List<Class<?>> classList;

    public ScanClassesWithValidateAnnotation(){
        this.classList = ScanClass.getClassList();
        classWithAnnotationList = new ArrayList<>(classList.size());
    }

    //获取带有参数校验注解的所有类
    public List<Class> getClassWithAnnotationList(List<Class<? extends Annotation>> customAnnotations){
        classWithAnnotationList.addAll(classList
                .stream().filter(clazz ->
        clazz!=null && classHasAnnotation(clazz,customAnnotations))
                .collect(Collectors.toList()));
        return classWithAnnotationList;
    }

    private boolean classHasAnnotation(Class clazz,List<Class<? extends Annotation>> customAnnotations){
        boolean flag = false;
        Method[] methods = clazz.getMethods();

        for(Method method : methods){
            Parameter[] parameters = method.getParameters();
            for(Parameter parameter : parameters){
                if(parameter.getAnnotations() != null |
                        parameter.getAnnotations().length >0){
                    Annotation[] annotations = parameter.getAnnotations();
                    for(Annotation annotation : annotations){
                        Class annclazz = annotation.annotationType();
                        if(annotation!=null && customAnnotations.contains(annclazz)){
                            flag = true;
                        }
                    }
                }
            }
        }

        return flag;
    }

}
