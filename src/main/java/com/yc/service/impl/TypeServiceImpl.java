package com.yc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.bean.Type;
import com.yc.bean.TypeExample;
import com.yc.dao.TypeMapper;
import com.yc.service.TypeService;

@Service
public class TypeServiceImpl implements TypeService {
	@Resource
	TypeMapper mt;

	@Override
	public Type findTypeByTypeID(int typeid) {
		TypeExample te = new TypeExample();
		te.createCriteria().andTypeIdEqualTo(typeid);
		Type type = mt.selectByPrimaryKey(typeid);
		return type;
	}

	@Override
	public List<Type> findAllType() {
		List<Type> list = mt.selectByExample(null);
		return list;
	}

}
