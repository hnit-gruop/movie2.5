package com.yc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yc.service.RedisService;

@Component
public class RedisSchedule {
	
	@Autowired
	RedisService redisService;
	
	//每半小时备份一次数据
	@Scheduled(fixedRate=1800000,initialDelay=60000)
	public void flushToMysql() {
		redisService.flushToMysql();
	}
}
