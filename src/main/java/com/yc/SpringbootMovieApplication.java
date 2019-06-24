package com.yc;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.yc.dao.MovieMapper;
import com.yc.util.PortUtil;

@SpringBootApplication
@MapperScan("com.yc.dao")
@EnableCaching
public class SpringbootMovieApplication {
	
	static {
		 PortUtil.checkPort(6379,"Redis 服务端",true);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootMovieApplication.class, args);
	}
}
