package org.zvalidate.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * AddCustomAnnotationProxy
 *
 * @author ZhuJianXin
 * @date 2017/9/4
 */
public class AddCustomAnnotationProxy implements InvocationHandler{

    private Object proxied;

    public AddCustomAnnotationProxy(Object proxied){
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(proxied,args);
    }
}
