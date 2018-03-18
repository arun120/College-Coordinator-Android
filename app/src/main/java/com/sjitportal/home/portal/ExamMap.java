package com.sjitportal.home.portal;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fluffy on 18-03-2018.
 */
public class ExamMap {

    static Map<String,String> map;

    static{
        map = new HashMap<>();
        map.put("IAE1","unit1");
        map.put("IAE2","unit2");
        map.put("IAE3","unit3");
        map.put("IAE4","unit4");

    }

    public static String get(String key){
        return map.get(key);
    }
}
