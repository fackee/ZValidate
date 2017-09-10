package org.zvalidate.algorithms;

import org.zvalidate.annotation.NotNull;

/**
 * Created by xz on 2017/9/10.
 */
public class NotNullAlgorithms implements Algorithms<NotNull>{
    @Override
    public void addAlgorithmsAnnotationMap() {
        ALGORITHMS_HASH_MAP.put(NotNull.class,this.getClass());
    }

    @Override
    public void addAlgorithmsAnnotationList() {
        customAnnotations.add(NotNull.class);
    }

    @Override
    public String Validate(String inputVal) {
        if(inputVal == null | inputVal.length() == 0){
            return "NotNull Error";
        }
        return VALIDATE_STATUS;
    }
}
