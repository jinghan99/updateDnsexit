package com.girl.update;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

/**
 * 动态 更新 ip 域名
 * @author: jinghan
 * @date: 2019/8/16  10:46
 */
@EnableScheduling
@SpringBootApplication
public class UpdateApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpdateApplication.class, args);
	}
	@PostConstruct
	void started() {
//		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		System.out.println(new Date());
	}
}

