package com.yc.util;

import java.lang.reflect.Field;
import java.util.Map;

public class Utils {
	public static void transformBeanToMap(Object object,Map<String, Object>map) 
	        throws IllegalArgumentException, IllegalAccessException {
	        Field[] declaredFields = object.getClass().getDeclaredFields();
	        for(Field field:declaredFields){
	            field.setAccessible(true);
	            Object value = field.get(object);
	            map.put(field.getName(), value);
	        }
	    }
	
}