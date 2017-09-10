package org.zvalidate.algorithms;

import org.zvalidate.annotation.NotNull;
import org.zvalidate.annotation.Number;

/**
 * Created by xz on 2017/9/10.
 */
public class NemberAlgorithms implements Algorithms<Number>{
    @Override
    public void addAlgorithmsAnnotationMap() {
        ALGORITHMS_HASH_MAP.put(NotNull.class,this.getClass());
    }

    @Override
    public void addAlgorithmsAnnotationList() {
        customAnnotations.add(Number.class);
    }

    @Override
    public String Validate(String inputVal) {
        String message = VALIDATE_STATUS;
        for(char ch : inputVal.toCharArray()){
            if(!Character.isDigit(ch)){
                message = "Number Error";
            }
        }
        return message;
    }
}
