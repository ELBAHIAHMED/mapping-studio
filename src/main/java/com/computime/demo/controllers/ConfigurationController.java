package com.computime.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;

@Controller
public class ConfigurationController {

	@Value("${spring.database.conffile}")
	String conffile;

	@GetMapping(path = "/dbconfig")
	public String dbsconfig(Model model) {
		try {
			String mysqldefaultdb, mysqldefaulthost, mysqldefaultport, mysqldefaultdb2;
			PropertiesConfiguration properties = new PropertiesConfiguration(conffile);
			model.addAttribute("defaultmysqlurl", properties.getString("spring.mysqldatasource.url"));
			String[] url = properties.getString("spring.mysqldatasource.url").split("/");
			if (url.length != 1) {
				System.out.println(Arrays.toString(url));
				String[] splithostport = url[2].split(":");
				mysqldefaulthost = splithostport[0];
				mysqldefaultport = splithostport[1];
				mysqldefaultdb = properties.getString("spring.mysqldatasource.db");
				// mysqldefaultdb = url[3].split("\\?")[0] ;
				mysqldefaultdb2 = properties.getString("spring.mysql2datasource.db");
				model.addAttribute("mysqldefaulthost", mysqldefaulthost);
				model.addAttribute("mysqldefaultport", mysqldefaultport);
				model.addAttribute("mysqldefaultdb", mysqldefaultdb);
				model.addAttribute("mysqldefaultdb2", mysqldefaultdb2);
				model.addAttribute("mysqldefaultusername", properties.getString("spring.mysqldatasource.username"));
				model.addAttribute("mysqldefaultpassword", properties.getString("spring.mysqldatasource.password"));
			}

			url = properties.getString("spring.sqlserverdatasource.url").split("/");

			if (url.length != 1) {
				String[] url2 = url[2].split(";");
				String[] url3 = url2[1].split("=");
				String sqlserverdefaultdb, sqlserverdefaulthost, sqlserverdefaultport, sqlserverdefaultdb2;
				sqlserverdefaulthost = url[2].split(":")[0];
				sqlserverdefaultport = url[2].split(":")[1].split(";")[0];
				// sqlserverdefaultdb = url3[1] ;
				sqlserverdefaultdb = properties.getString("spring.sqlserverdatasource.db");
				sqlserverdefaultdb2 = properties.getString("spring.sqlserverdatasource2.db");
				model.addAttribute("defaultsqlserverurl", properties.getString("spring.sqlserverdatasource.url"));
				model.addAttribute("sqlserverdefaulthost", sqlserverdefaulthost);
				model.addAttribute("sqlserverdefaultport", sqlserverdefaultport);
				model.addAttribute("sqlserverdefaultdb", sqlserverdefaultdb);
				model.addAttribute("sqlserverdefaultdb2", sqlserverdefaultdb2);
				model.addAttribute("sqlserverdefaultusername",
						properties.getString("spring.sqlserverdatasource.username"));
				model.addAttribute("sqlserverdefaultpassword",
						properties.getString("spring.sqlserverdatasource.password"));
			}

			model.addAttribute("defaultaccesspath", properties.getString("spring.access.path"));
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "config.jsp";
	}

	@PostMapping(path = "/databaseconfig")
	public String postconfig(Model model, @RequestParam(defaultValue="default", required=false) String accesspath, @RequestParam(defaultValue="default", required=false) String mysqlhost,
				@RequestParam(defaultValue="default", required=false) int mysqlport, @RequestParam(defaultValue="3306", required=false) String mysqldb, @RequestParam(defaultValue="default", required=false) String mysqlusername,
				@RequestParam(defaultValue="default", required=false) String mysqlpassword, @RequestParam(defaultValue="default", required=false) String sqlserverhost, @RequestParam(defaultValue="447", required=false) int sqlserverport,
				@RequestParam(defaultValue="default", required=false) String sqlserverdb, @RequestParam(defaultValue="default", required=false) String sqlserverusername,
				@RequestParam(defaultValue="default", required=false) String sqlserverpassword, @RequestParam(defaultValue="default", required=false) String mysqldb2, @RequestParam(defaultValue="default", required=false) String sqlserverdb2) {
		ArrayList<String> errors = new ArrayList<String>();
		// boolean b = false ;
		int count = 0;
		PropertiesConfiguration properties;

		try {
			if (!accesspath.equals("default")) {
			properties = new PropertiesConfiguration(conffile);
			Database db = DatabaseBuilder.open(new File(accesspath));
			properties.setProperty("spring.access.path", accesspath);
			properties.save();
			count += 1;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			errors.add("Access path not found");
			System.out.println(e);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String url = "jdbc:mysql://" + mysqlhost + ":" + mysqlport + "/" + mysqldb
				+ "?serverTimezone=UTC&useLegacyDatetimeCode=false";
		System.out.println(url);
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(mysqlusername);
		dataSource.setPassword(mysqlpassword);
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

		try {
			dataSource.getConnection();
			properties = new PropertiesConfiguration(conffile);
			properties.setProperty("spring.mysqldatasource.url", url);
			properties.setProperty("spring.mysqldatasource.username", mysqlusername);
			properties.setProperty("spring.mysqldatasource.password", mysqlpassword);
			properties.setProperty("spring.mysqldatasource.db", mysqldb);
			properties.save();
			count += 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			errors.add("MySQL DB 1 connection error... Details : " + e.toString());
			System.out.println(e);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		url = "jdbc:mysql://" + mysqlhost + ":" + mysqlport + "/" + mysqldb
				+ "?serverTimezone=UTC&useLegacyDatetimeCode=false";
		dataSource = new DriverManagerDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(mysqlusername);
		dataSource.setPassword(mysqlpassword);
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		try {
			dataSource.getConnection();
			properties = new PropertiesConfiguration(conffile);
			properties.setProperty("spring.mysqldatasource.url", url);
			properties.setProperty("spring.mysqldatasource.username", mysqlusername);
			properties.setProperty("spring.mysqldatasource.password", mysqlpassword);
			properties.setProperty("spring.mysql2datasource.db", mysqldb2);
			properties.save();
			count += 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			errors.add("MySQL DB 2 connection error... Details : " + e.toString());
			System.out.println(e);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!sqlserverhost.equals("default")) {
		DriverManagerDataSource dataSource2 = new DriverManagerDataSource();
		String url2 = "jdbc:sqlserver://" + sqlserverhost + ":" + sqlserverport + ";" + "databaseName=" + sqlserverdb;
		dataSource2.setUrl(url2);
		System.out.println(url2);
		dataSource2.setUsername(sqlserverusername);
		dataSource2.setPassword(sqlserverpassword);
		dataSource2.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		try {
			dataSource2.getConnection();
			properties = new PropertiesConfiguration(conffile);
			properties.setProperty("spring.sqlserverdatasource.url", url2);
			properties.setProperty("spring.sqlserverdatasource.username", sqlserverusername);
			properties.setProperty("spring.sqlserverdatasource.password", sqlserverpassword);
			properties.setProperty("spring.sqlserverdatasource.db", sqlserverdb);
			properties.save();
			count += 1;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			errors.add("SQLServer connection error... Details : " + e.toString());
			System.out.println(e);

		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dataSource2 = new DriverManagerDataSource();
		url2 = "jdbc:sqlserver://" + sqlserverhost + ":" + sqlserverport + ";" + "databaseName=" + sqlserverdb2;
		dataSource2.setUrl(url2);
		System.out.println(url2);
		dataSource2.setUsername(sqlserverusername);
		dataSource2.setPassword(sqlserverpassword);
		dataSource2.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		try {
			dataSource2.getConnection();
			properties = new PropertiesConfiguration(conffile);
			properties.setProperty("spring.sqlserverdatasource.url", url2);
			properties.setProperty("spring.sqlserverdatasource.username", sqlserverusername);
			properties.setProperty("spring.sqlserverdatasource.password", sqlserverpassword);
			properties.setProperty("spring.sqlserverdatasource2.db", sqlserverdb2);
			properties.save();
			count += 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			errors.add("SQLServer connection error... Details : " + e.toString());
			System.out.println(e);

		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		model.addAttribute("errors", errors);
		if (count == 0) {
			model.addAttribute("result", "Configuration failed totally");
			return "configmapping.jsp";
		}

		else {
			model.addAttribute("result",
					Integer.toString(5 - count) + "Errors,  " + Integer.toString(count) + "Configurations succeded !");
			// restartEndpoint.restart() ;
			return "configmapping.jsp";
		}

	}

}
