# ZValidate
<h1>基础SpringMVC的参数校验框架</h1>


使用方式：</br>
添加pom依赖
```
 <dependency>
      <groupId>org.zvalidate</groupId>
      <artifactId>org-zvalidate</artifactId>
      <version>1.0.0</version>
  </dependency>
```
springMVC配置拦截器如下：
```
<mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/ValidateTest/*"/>
            <bean class="org.zvalidate.interceptor.ValidateInterceptor"/>
        </mvc:interceptor>
</mvc:interceptors>
```

控制器直接添加注解：
```
@Controller
@RequestMapping("/ValidateTest")
public class TestCtr {

    @RequestMapping("/Phone")
    @ResponseBody
    public String Phone(@Phone String phone){
        return phone;
    }

    @RequestMapping("/IDCard")
    @ResponseBody
    public String IDCard(@IDCard String idcard){
        return idcard;
    }

    @RequestMapping("/EMail")
    @ResponseBody
    public String EMail(@Email String email){
        return email;
    }

    @RequestMapping("/SqlInject")
    @ResponseBody
    public String TestValidate(@SqlInject String sql){
        return sql;
    }

    @RequestMapping("/Custom")
    @ResponseBody
    public String Custom(@Custom String custom){
        return custom;
    }
}
```
添加自定义注解实现方式：</br>
<strong>第一步：定义注解</strong>
```
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Custom {

}
```
<strong>第二步：实现Algorithms接口：</strong>
```
public class CustomAlgorithms implements Algorithms<Custom>{

    //必须实现固定格式ALGORITHMS_HASH_MAP.put(Custom.class,this.getClass());
    public void addAlgorithmsAnnotationMap() {
        ALGORITHMS_HASH_MAP.put(Custom.class,this.getClass());
    }
    //必须实现固定格式customAnnotations.add(Custom.class);
    public void addAlgorithmsAnnotationList() {
        customAnnotations.add(Custom.class);
    }
    //参数校验具体算法
    public String Validate(String s) {
        if(s.equals("custom")){
            return VALIDATE_STATUS;
        }
        return "Custom ERROR";
    }
}
```


