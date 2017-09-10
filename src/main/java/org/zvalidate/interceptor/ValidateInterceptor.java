package org.zvalidate.interceptor;

import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zvalidate.algorithms.Algorithms;
import org.zvalidate.algorithms.AlgorithmsFactory;
import org.zvalidate.scan.ScanClassesWithValidateAnnotation;
import org.zvalidate.scan.ScanValidateAnnotation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * ValidateInterceptor
 *
 * @author ZhuJianXin
 * @date 2017/9/4
 */
public class ValidateInterceptor implements HandlerInterceptor{

    private static Field requestField;

    private static Field parametersParseField;

    private static Field coyoteRquestField;

    private static Field parametersField;

    private static Field linkedHashMapField;

    private static final List<Class> classWithAnnotationList = new ArrayList<>();

    //初始化信息
    static {

        try {
            //通过反射获取paramHashValues
            Class clazz = Class.forName("org.apache.catalina.connector.RequestFacade");
            requestField = clazz.getDeclaredField("request");
            requestField.setAccessible(true);

            parametersParseField = requestField.getType().getDeclaredField("parametersParsed");
            parametersParseField.setAccessible(true);

            coyoteRquestField = requestField.getType().getDeclaredField("coyoteRequest");
            coyoteRquestField.setAccessible(true);

            parametersField = coyoteRquestField.getType().getDeclaredField("parameters");
            parametersField.setAccessible(true);

            linkedHashMapField = parametersField.getType().getDeclaredField("paramHashValues");
            linkedHashMapField.setAccessible(true);

            //扫描jar包获取框架中已经定义的注解和通过当前类加载器扫描当前项目获取用户自定义的注解
            ScanValidateAnnotation.initValidateAnnotationList();
            //初始化注解一起相对应的算法映射
            ScanValidateAnnotation.initValidateAnnotationMap();
            //获取所有带有注解的控制器
            classWithAnnotationList.addAll(
                    new ScanClassesWithValidateAnnotation()
                            .getClassWithAnnotationList(Algorithms.customAnnotations));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取paramHashValues来修改request中的parameter值，进行参数校验并返回提示
    private Map<String, ArrayList<String>> getRequestMap(HttpServletRequest request){
        try {
            Object innerRequest = requestField.get(request);
            parametersParseField.setBoolean(innerRequest, true);
            Object coyoteRequestObject = coyoteRquestField.get(innerRequest);
            Object parameterObject = parametersField.get(coyoteRequestObject);
            return (Map<String, ArrayList<String>>)linkedHashMapField.get(parameterObject);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.EMPTY_MAP;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse, Object o) throws Exception {
        //拦截具体的控制器
        if(o.getClass().getName().equals("org.springframework.web.method.HandlerMethod")){
            HandlerMethod handler = (HandlerMethod)o;
            Class clazz = handler.getBean().getClass();
            //判断控制器是否带有参数校验注解
            if(classWithAnnotationList.contains(clazz)){
                MethodParameter[] methodParameters = handler.getMethodParameters();
                for(MethodParameter methodParameter : methodParameters){
                    String inputValue = httpServletRequest
                            .getParameter(methodParameter.getParameterName());
                    Annotation[] annotations = methodParameter.getParameterAnnotations();
                    //判断具体函数中的参数是否带有注解
                    if(inputValue !=null && annotations!=null && annotations.length>0){
                        for(Annotation annotation : annotations){
                            //判断注解是否是参数校验注解
                            if(Algorithms.customAnnotations.contains(annotation.annotationType())){
                                //获取paramHashValues
                                Map<String, ArrayList<String>> paramMap = getRequestMap(httpServletRequest);
                                //获取注解对应的具体算法
                                Algorithms algorithms = AlgorithmsFactory.createAlgorithms(annotation.annotationType());
                                //判断参数是否合法
                                if(!algorithms.Validate(inputValue).equals(Algorithms.VALIDATE_STATUS)){
                                    //不合法修改parameter，返回相应提示
                                    ArrayList<String> values = paramMap.get(methodParameter.getParameterName());
                                    values.clear();
                                    values.add("Parameter ERROR");
                                    paramMap.replace(methodParameter.getParameterName(),values);
                                }
                            }
                        }
                    }

                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
