package com.yc.service;

import java.util.List;

import com.yc.bean.Type;

public interface TypeService {
	Type findTypeByTypeID(int typeid);
	
	List<Type> findAllType();
}
