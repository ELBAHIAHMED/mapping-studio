package com.computime.demo.services;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;

@Service
public class MainService {
	
	@Value("${spring.database.conffile}")
	String conffile ; 
	
	public DriverManagerDataSource getmysqldatasource() {
		try {
		PropertiesConfiguration properties = new PropertiesConfiguration(conffile);
		DriverManagerDataSource mysqlDatasource = new DriverManagerDataSource();
		mysqlDatasource.setUrl(properties.getString("spring.mysqldatasource.url"));
		mysqlDatasource.setUsername(properties.getString("spring.mysqldatasource.username"));
		mysqlDatasource.setPassword(properties.getString("spring.mysqldatasource.password")) ;
		mysqlDatasource.setDriverClassName("com.mysql.cj.jdbc.Driver"); 
		mysqlDatasource.setCatalog(properties.getString("spring.mysqldatasource.db")); 
		return mysqlDatasource ; 
		}catch(ConfigurationException e) {
			return null ; 
		}
		
	}
	
	public DriverManagerDataSource getmysql2datasource() {
		try {
		PropertiesConfiguration properties = new PropertiesConfiguration(conffile);
		DriverManagerDataSource mysqlDatasource = new DriverManagerDataSource();
		mysqlDatasource.setUrl(properties.getString("spring.mysqldatasource.url"));
		mysqlDatasource.setUsername(properties.getString("spring.mysqldatasource.username"));
		mysqlDatasource.setPassword(properties.getString("spring.mysqldatasource.password")) ;
		mysqlDatasource.setDriverClassName("com.mysql.cj.jdbc.Driver"); 
		mysqlDatasource.setCatalog(properties.getString("spring.mysql2datasource.db")); 
		return mysqlDatasource ; 
		}catch(ConfigurationException e) {
			return null ; 
		}
		
	}
	
	
	
	public DriverManagerDataSource getsqlserverdatasource() {
		try {
		PropertiesConfiguration properties = new PropertiesConfiguration(conffile);
		DriverManagerDataSource sqlserverDatasource = new DriverManagerDataSource();
		sqlserverDatasource.setUrl(properties.getString("spring.sqlserverdatasource.url"));
		sqlserverDatasource.setUsername(properties.getString("spring.sqlserverdatasource.username"));
		sqlserverDatasource.setPassword(properties.getString("spring.sqlserverdatasource.password")) ;
		sqlserverDatasource.setCatalog(properties.getString("spring.sqlserverdatasource.db"));
		sqlserverDatasource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
		return sqlserverDatasource ; 
		}catch(ConfigurationException e) {
			return null ; 
		}
		
	}
	
	public Database getAccessDatabase() {
		PropertiesConfiguration properties = getproperties() ; 
		Database db;
		try {
			db = DatabaseBuilder.open(new File(properties.getString("spring.access.path")));
			return db ; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ; 
		}
		
	}
	
	public PropertiesConfiguration getproperties() {
		try {
			PropertiesConfiguration properties = new PropertiesConfiguration(conffile) ;
			return properties ; 
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ; 
		} 
		
	}
}
