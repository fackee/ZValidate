package org.zvalidate.algorithms;

import org.zvalidate.annotation.SqlInject;

import java.util.regex.Pattern;

/**
 * Created by xz on 2017/9/10.
 */
public class SqlInjectAlgorithms implements Algorithms<SqlInject>{
    @Override
    public void addAlgorithmsAnnotationMap() {
        ALGORITHMS_HASH_MAP.put(SqlInject.class,this.getClass());
    }

    @Override
    public void addAlgorithmsAnnotationList() {
        customAnnotations.add(SqlInject.class);
    }

    @Override
    public String Validate(String inputVal) {
        String message = VALIDATE_STATUS;
        String pattern = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
                + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
        Pattern sqlPattern = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
        sqlPattern.matcher(inputVal).find();
       if(sqlPattern.matcher(inputVal).find()){
           message = "SqlInject Error";
       }
        return message;
    }
}
