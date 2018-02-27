package com.platform.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 23653_000 on 2017/7/16.
 */

public class ApiResultMap {
    public static Map<Integer,String> map;

    public static void init(){
        map = new HashMap<Integer,String>();
        map.put(1,"JSON格式错误");
        map.put(2,"缺少必要参数");
        map.put(3,"未知的接口");
        map.put(4,"系统未知错误");
        map.put(5,"系统错误");
        map.put(6,"格式转换错误");
        map.put(7,"登录失败");
    }
    public static String get(Integer key){
        try{
            return map.get(key);
        }catch (Exception e){
            return null;
        }
    }
}
