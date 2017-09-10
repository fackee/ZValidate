package org.zvalidate.algorithms;

import org.zvalidate.annotation.IDCard;

import java.lang.annotation.Inherited;
import java.util.HashMap;
import java.util.Map;

/**
 * IDCardAlgorothms
 *
 * @author ZhuJianXin
 * @date 2017/9/3
 */
public class IDCardAlgorithms implements Algorithms<IDCard>{


    @Override
    public void addAlgorithmsAnnotationMap() {
        ALGORITHMS_HASH_MAP.put(IDCard.class,this.getClass());
    }

    @Override
    public void addAlgorithmsAnnotationList() {
        customAnnotations.add(IDCard.class);
    }

    @Override
    public String Validate(String inputVal) {
        String message = VALIDATE_STATUS;
        if(inputVal.length() == 18){
            inputVal = inputVal.toUpperCase();
            String newStr = inputVal.substring(0,17);
            int[] wi = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
            int sigma=0;
            for(int i=0;i<17;i++){
                sigma+= (Integer.parseInt(newStr.substring(i,i+1)))*wi[i];
            }
            char[] checkCodeArr={1,0,'X',9,8,7,6,5,4,3,2};
            int newCode=checkCodeArr[ (sigma % 11) ];
            newStr+=newCode;
            message = newStr.equals(inputVal) ? VALIDATE_STATUS : "IDCard ERROR";
        }else{
            message = "IDCard ERROR";
        }
        return message;
    }
}
