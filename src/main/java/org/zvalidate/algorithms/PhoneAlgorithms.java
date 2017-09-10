package org.zvalidate.algorithms;

import org.zvalidate.annotation.Phone;

/**
 * PhoneAlgorithms
 *
 * @author ZhuJianXin
 * @date 2017/9/3
 */
public class PhoneAlgorithms implements Algorithms<Phone>{

    @Override
    public void addAlgorithmsAnnotationMap() {
        ALGORITHMS_HASH_MAP.put(Phone.class,this.getClass());
    }

    @Override
    public void addAlgorithmsAnnotationList() {
        customAnnotations.add(Phone.class);
    }

    @Override
    public String Validate(String inputVal) {
        String message = VALIDATE_STATUS;
        if(inputVal.length() == 11){
            if(inputVal.matches("[1][34578]\\\\d{9}")){
                return message;
            }
            message = "Phone Error";
        }else{
            message = "Phone Error";
        }
        return message;
    }
}
