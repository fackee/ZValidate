package org.zvalidate.algorithms;

import java.lang.annotation.Annotation;

/**
 * AlgorithmsFactory
 *
 * @author ZhuJianXin
 * @date 2017/9/3
 */
public class AlgorithmsFactory {


    //根据注解类型创建相应的过滤算法
    public static Algorithms createAlgorithms(Class<? extends Annotation> annotation){
        Algorithms algorithms = null;
        try{
            Class<? extends Algorithms> clazz = Algorithms.ALGORITHMS_HASH_MAP.get(annotation);
            algorithms = (Algorithms) Class.forName(clazz.getName()).newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return algorithms;
    }
}
