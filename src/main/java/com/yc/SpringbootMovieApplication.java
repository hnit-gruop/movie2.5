package com.yc;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.yc.dao.MovieMapper;
import com.yc.util.PortUtil;

@SpringBootApplication
@MapperScan("com.yc.dao")
@EnableCaching
@EnableScheduling
public class SpringbootMovieApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootMovieApplication.class, args);
	}
}
