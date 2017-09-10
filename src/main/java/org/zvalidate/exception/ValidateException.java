//package org.zvalidate.exception;
//
//import java.lang.annotation.Annotation;
//
///**
// * ValidateException
// *
// * @author ZhuJianXin
// * @date 2017/9/8
// */
//public class ValidateException extends Exception{
//
//    private static final long serialVersionUID = 1L;
//
//    public ValidateException(){
//        super();
//    }
//
//    public ValidateException(String s){
//        super(s);
//    }
//
//    public ValidateException(String message,Throwable throwable){
//        super(message,throwable);
//    }
//
//    public ValidateException(Throwable throwable){
//        super(throwable);
//    }
//    private String AlgoritgmsCourseCase(Annotation annotation){
//        String message = "Validate Exception";
//        switch (annotation.getClass().getName()){
//            case "Phone":
//                message = "Phone Exception";
//                break;
//            case "IDCard":
//                message = "IDCard Exception";
//                break;
//            default:
//                break;
//        }
//        return message;
//    }
//}
