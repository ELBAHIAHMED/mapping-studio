package com.computime.demo;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.computime.demo.controllers.InjectionController;
import com.computime.demo.services.MainService;

@SpringBootApplication
@EnableScheduling
public class ComputimeInjApplication {

	@Autowired
	InjectionController injection ; 
	
	@Autowired
	MainService service ; 

	public static void main(String[] args) {
		SpringApplication.run(ComputimeInjApplication.class, args);
		
		
	}
	
	

	
	@Scheduled(fixedRate = 2000)
	public void check() {
		PropertiesConfiguration properties = service.getproperties();
		if (Integer.parseInt(properties.getString("spring.timer.mysqlmysql2")) == 1) {
			injection.injectmysqlmysql2() ; 
		}
		if (Integer.parseInt(properties.getString("spring.timer.mysql2mysql")) == 1) {
			injection.injectmysql2mysql() ; 
		}
		if (Integer.parseInt(properties.getString("spring.timer.sqlservermysql")) == 1) {
			injection.injectsqlservermysql() ; 
		}
		if (Integer.parseInt(properties.getString("spring.timer.mysqlsqlserver")) == 1) {
			injection.injectsqlservermysql() ; 
		}
		if (Integer.parseInt(properties.getString("spring.timer.accessmysql")) == 1) {
			injection.injectaccessmysql() ; 
		}
		
	}

}
