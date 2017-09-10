package org.zvalidate.algorithms;

import org.zvalidate.annotation.Email;

/**
 * Created by xz on 2017/9/10.
 */
public class EmailAlgorithms implements Algorithms<Email>{
    @Override
    public void addAlgorithmsAnnotationMap() {
        ALGORITHMS_HASH_MAP.put(Email.class,this.getClass());
    }

    @Override
    public void addAlgorithmsAnnotationList() {
        customAnnotations.add(Email.class);
    }

    @Override
    public String Validate(String inputVal) {
        if(inputVal.matches("^\\w+@\\w+\\.(com|cn)")){
            return VALIDATE_STATUS;
        }
        return "E-Mail Format Error";
    }
}
