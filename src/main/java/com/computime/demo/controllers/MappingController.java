package com.computime.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.computime.demo.services.MainService;
import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;

@Controller
public class MappingController {
	
	@Value("${spring.database.conffile}")
	String conffile ; 
	
	@Autowired
	MainService service ; 
	
	@GetMapping(path="/configmapping")
	  public String getMapping() {
		  return "configmapping.jsp" ; 
	  }
	
	@GetMapping(path="/mysqlsqlservermap")
	  public String mapmysqlsqlserver(Model model) {
		  
		  
		  PropertiesConfiguration properties;
		  	

		  
		  try {
			properties = new PropertiesConfiguration(conffile);
			
			DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ;  
			DatabaseMetaData metaData = mysqlDatasource.getConnection().getMetaData();
			
			DriverManagerDataSource sqlserverDatasource =  service.getsqlserverdatasource() ;
			DatabaseMetaData metaData2 = sqlserverDatasource.getConnection().getMetaData() ;
			
			ArrayList<String> mysqltables = new ArrayList<String>() ; 
			ArrayList<String> sqlservertables = new ArrayList<String>() ; 
			
			ResultSet tables1 = metaData.getTables(properties.getString("spring.mysqldatasource.db"), null, "%", new String[] { "TABLE" }); 
			while (tables1.next()) {
	            String tableName=tables1.getString("TABLE_NAME");
	            mysqltables.add(tableName) ; 
			
		} 
			ResultSet tables2 = metaData2.getTables(properties.getString("spring.sqlserverdatasource.db"), null, "%", new String[] { "TABLE" }); 
			while (tables2.next()) {
	            String tableName=tables2.getString("TABLE_NAME");
	            sqlservertables.add(tableName) ; 
			
		} 
			
			Iterator itr = mysqltables.iterator();
			while(itr.hasNext()) {
				if (itr.next().equals("hibernate_sequence")) {
					itr.remove();
				}
				
					
			}
			
			itr = sqlservertables.iterator(); 
			
			while(itr.hasNext()) {
				String s = (String) itr.next();
				if (s.equals("trace_xe_action_map") || s.equals("trace_xe_event_map")) {
					itr.remove();
				}
			}
			//System.out.println(mysqltables) ;
			//System.out.println(sqlservertables) ; 
			
			
			model.addAttribute("mysqltables", mysqltables) ; 
			model.addAttribute("sqlservertables", sqlservertables) ; 
			
			
			}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(ConfigurationException e1) {
			
		}
		  
		  
		  return "mysqlsqlserver.jsp" ; 
	  }
	
	  @GetMapping(path="/mysqlmysql2map")
	  public String mapmysqlmysql(Model model) {
		  
		  
		  PropertiesConfiguration properties;
		  	

		  
		  try {
			properties = new PropertiesConfiguration(conffile);
			
			DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ;  
			DatabaseMetaData metaData = mysqlDatasource.getConnection().getMetaData();
			
			DriverManagerDataSource mysql2Datasource =  service.getmysql2datasource() ;
			DatabaseMetaData metaData2 = mysql2Datasource.getConnection().getMetaData() ;
			
			ArrayList<String> mysqltables = new ArrayList<String>() ; 
			ArrayList<String> mysql2tables = new ArrayList<String>() ; 
			
			ResultSet tables1 = metaData.getTables(properties.getString("spring.mysqldatasource.db"), null, "%", new String[] { "TABLE" }); 
			while (tables1.next()) {
	            String tableName=tables1.getString("TABLE_NAME");
	            mysqltables.add(tableName) ; 
			
		} 
			ResultSet tables2 = metaData2.getTables(properties.getString("spring.mysql2datasource.db"), null, "%", new String[] { "TABLE" }); 
			while (tables2.next()) {
	            String tableName=tables2.getString("TABLE_NAME");
	            mysql2tables.add(tableName) ; 
			
		} 
			
			Iterator itr = mysqltables.iterator();
			while(itr.hasNext()) {
				if (itr.next().equals("hibernate_sequence")) {
					itr.remove();
				}
				
					
			}
			
			itr = mysql2tables.iterator(); 
			
			while(itr.hasNext()) {
				String s = (String) itr.next();
				if (s.equals("hibernate_sequence")) {
					itr.remove();
				}
			}
			//System.out.println(mysqltables) ;
			//System.out.println(mysql2tables) ; 
			
			
			model.addAttribute("mysqltables", mysqltables) ; 
			model.addAttribute("mysql2tables", mysql2tables) ; 
			
			
			}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(ConfigurationException e1) {
			
		}
		  
		  
		  return "mysqlmysql2.jsp" ; 
	  }
	
	  
	  @GetMapping(path="/mysqlaccessmap")
	  public String mapmysqlaccess(Model model) {
		  
		  
		  PropertiesConfiguration properties;
		  	

		  
		  try {
			properties = new PropertiesConfiguration(conffile);
			DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ; 
			DatabaseMetaData metaData = mysqlDatasource.getConnection().getMetaData();
			ArrayList<String> mysqltables = new ArrayList<String>() ; 
			ArrayList<String> accesstables = new ArrayList<String>() ; 
			
			ResultSet tables1 = metaData.getTables(properties.getString("spring.mysqldatasource.db"), null, "%", new String[] { "TABLE" }); 
			while (tables1.next()) {
	            String tableName=tables1.getString("TABLE_NAME");
	            mysqltables.add(tableName) ; 
			
			} 
			
			Database db = DatabaseBuilder.open(new File(properties.getString("spring.access.path")));
			
			for (String str : db.getTableNames()) {
				accesstables.add(str) ; 
			}
			
			Iterator itr = mysqltables.iterator();
			while(itr.hasNext()) {
				if (itr.next().equals("hibernate_sequence")) {
					itr.remove();
				}
			}
			
			model.addAttribute("mysqltables", mysqltables) ; 
			model.addAttribute("accesstables", accesstables) ; 
				
					
			
			
			
			
			
		  }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(ConfigurationException e1) {
				
			}catch(IOException e) {
				System.out.println("Error access...");
			}
		  
		  return "mysqlaccess.jsp" ; 
		  
	  }
	  
	  @GetMapping(path="/sqlserveraccessmap")
	  public String mapsqlserveraccess(Model model) {
		  
		  
		  PropertiesConfiguration properties;
		  	

		  
		  try {
			properties = new PropertiesConfiguration(conffile);
			DriverManagerDataSource sqlserverDatasource = service.getsqlserverdatasource() ;
			DatabaseMetaData metaData = sqlserverDatasource.getConnection().getMetaData() ;
			ArrayList<String> sqlservertables = new ArrayList<String>() ; 
			ArrayList<String> accesstables = new ArrayList<String>() ; 
			 
			ResultSet tables = metaData.getTables(properties.getString("spring.sqlserverdatasource.db"), null, "%", new String[] { "TABLE" }); 
			while (tables.next()) {
	            String tableName=tables.getString("TABLE_NAME");
	            sqlservertables.add(tableName) ; 
			
		} 
			
			Database db = DatabaseBuilder.open(new File(properties.getString("spring.access.path")));
			
			for (String str : db.getTableNames()) {
				accesstables.add(str) ; 
			}
			
			Iterator itr = sqlservertables.iterator(); 
			
			while(itr.hasNext()) {
				String s = (String) itr.next();
				if (s.equals("trace_xe_action_map") || s.equals("trace_xe_event_map")) {
					itr.remove();
				}
			}
			
			model.addAttribute("sqlservertables", sqlservertables) ; 
			model.addAttribute("accesstables", accesstables) ; 
				
					
			
			
			
			
			
		  }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(ConfigurationException e1) {
				
			}catch(IOException e) {
				System.out.println("Error access...");
			}
		  
		  return "sqlserveraccess.jsp" ; 
		  
	  }
	  
	  @PostMapping(path="/mysqlmysql2mapfields")
	  public String mapmysqlmysql2fields(Model model, @RequestParam String tableValue) {
		  
		  System.out.println(tableValue) ; 
		  PropertiesConfiguration properties;
		  
		  try {
		    DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ; 
		    DriverManagerDataSource mysql2Datasource = service.getmysql2datasource() ; 
			DatabaseMetaData metaData = mysqlDatasource.getConnection().getMetaData();  
			DatabaseMetaData metaData2 = mysql2Datasource.getConnection().getMetaData() ;
			properties = new PropertiesConfiguration(conffile);
			
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>() ;
			  String[] arr = tableValue.split(";") ; 
			  for (String str : arr) {
				  String[] str2 = str.split(",") ;
				  if (!str2[1].equals("No Mapping")) {
					  HashMap<String, Object> hash = new HashMap<String, Object>() ;
					  
					  ArrayList<String> mysqlfields = new ArrayList<String>() ; 
					  ArrayList<String> mysql2fields = new ArrayList<String>() ;
					  
					  hash.put("mysqltable", str2[0]) ; 
					  hash.put("mysql2table", str2[1]) ;
					  
					  
					  
					  //Now we need to get fields :(
					  
					  System.out.println(str2[0] + "  " +  str2[1]) ; 
					  
					  ResultSet columns = metaData.getColumns(properties.getString("spring.mysqldatasource.db"),  null,  str2[0], "%");
					 while (columns.next()) {
						  String columnName=columns.getString("COLUMN_NAME");
						  mysqlfields.add(columnName) ; 
						  
					  }
					  
					  //System.out.println(properties.getString("spring.seconddatasource.db"));
					  
					  ResultSet columns2 = metaData2.getColumns(properties.getString("spring.mysql2datasource.db"),  null, str2[1], "%");

					  while (columns2.next()) {
						  String columnName2= columns2.getString("COLUMN_NAME");
						  mysql2fields.add(columnName2) ; 
						  
					  }
					  
					  hash.put("mysqlfields", mysqlfields) ; 
					  hash.put("mysql2fields", mysql2fields) ; 
					  
					  list.add(hash) ; 
					  
					  
					  
				  }
				  
				  
				  
				  
				  
				  
			  }
			  
			  model.addAttribute("map", list) ;
			  System.out.println(list) ;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ConfigurationException e1) {
			
		}
		  
		  return "mysqlmysql2map.jsp"; 	 
		  
		  //[{mysqlfields=[id, badge, cache_fk, date_mouv, pointeuse, updated], mysqltable=mouvement, mysql2table=Mouvement, mysql2fields=[id, Authentication_Date, Authentication_Date_Time, Authentication_Time, Device_Name, Device_Serial_No, Direction, Person_Name, card]}, {mysqlfields=[id, badge, nom, prenom], mysqltable=user, mysql2table=Example, mysql2fields=[id, nom]}]
		  //[{mysqlfields=[id, badge, cache_fk, date_mouv, pointeuse, updated], mysqltable=mouvement, mysql2table=Example, mysql2fields=[id, nom]}]
	  }
	  
	  
	  
	  
	  @PostMapping(path="/mysqlsqlservermapfields")
	  public String mapmysqlsqlserverfields(Model model, @RequestParam String tableValue) {
		  
		  System.out.println(tableValue) ; 
		  PropertiesConfiguration properties;
		  
		  try {
		    DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ; 
		    DriverManagerDataSource sqlserverDatasource = service.getsqlserverdatasource() ; 
			DatabaseMetaData metaData = mysqlDatasource.getConnection().getMetaData();  
			DatabaseMetaData metaData2 = sqlserverDatasource.getConnection().getMetaData() ;
			properties = new PropertiesConfiguration(conffile);
			
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>() ;
			  String[] arr = tableValue.split(";") ; 
			  for (String str : arr) {
				  String[] str2 = str.split(",") ;
				  if (!str2[1].equals("No Mapping")) {
					  HashMap<String, Object> hash = new HashMap<String, Object>() ;
					  
					  ArrayList<String> mysqlfields = new ArrayList<String>() ; 
					  ArrayList<String> sqlserverfields = new ArrayList<String>() ;
					  
					  hash.put("mysqltable", str2[0]) ; 
					  hash.put("sqlservertable", str2[1]) ;
					  
					  
					  
					  //Now we need to get fields :(
					  
					  System.out.println(str2[0] + "  " +  str2[1]) ; 
					  
					  ResultSet columns = metaData.getColumns(properties.getString("spring.mysqldatasource.db"),  null,  str2[0], "%");
					 while (columns.next()) {
						  String columnName=columns.getString("COLUMN_NAME");
						  mysqlfields.add(columnName) ; 
						  
					  }
					  
					  //System.out.println(properties.getString("spring.seconddatasource.db"));
					  
					  ResultSet columns2 = metaData2.getColumns(properties.getString("spring.sqlserverdatasource.db"),  null, str2[1], "%");

					  while (columns2.next()) {
						  String columnName2= columns2.getString("COLUMN_NAME");
						  sqlserverfields.add(columnName2) ; 
						  
					  }
					  
					  hash.put("mysqlfields", mysqlfields) ; 
					  hash.put("sqlserverfields", sqlserverfields) ; 
					  
					  list.add(hash) ; 
					  
					  
					  
				  }
				  
				  
				  
				  
				  
				  
			  }
			  
			  model.addAttribute("map", list) ;
			  System.out.println(list) ;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ConfigurationException e1) {
			
		}
		  
		  return "mysqlsqlservermap.jsp"; 	 
		  
		  //[{mysqlfields=[id, badge, cache_fk, date_mouv, pointeuse, updated], mysqltable=mouvement, sqlservertable=Mouvement, sqlserverfields=[id, Authentication_Date, Authentication_Date_Time, Authentication_Time, Device_Name, Device_Serial_No, Direction, Person_Name, card]}, {mysqlfields=[id, badge, nom, prenom], mysqltable=user, sqlservertable=Example, sqlserverfields=[id, nom]}]
		  //[{mysqlfields=[id, badge, cache_fk, date_mouv, pointeuse, updated], mysqltable=mouvement, sqlservertable=Example, sqlserverfields=[id, nom]}]
	  }
	  
	  
	  
	  @PostMapping(path="/mysqlaccessmapfields")
	  public String mapmysqlaccessfields(Model model, @RequestParam String tableValue) {
		  
		  System.out.println(tableValue) ; 
		  PropertiesConfiguration properties;
		  
		  try {
			DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ; 
			DatabaseMetaData metaData = mysqlDatasource.getConnection().getMetaData();  
			properties = new PropertiesConfiguration(conffile);
			Database db = DatabaseBuilder.open(new File(properties.getString("spring.access.path")));
			//Connection conn = mysqlDatasource.getConnection() ; 
			//Statement st = conn.createStatement()
			
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>() ;
			  String[] arr = tableValue.split(";") ; 
			  for (String str : arr) {
				  String[] str2 = str.split(",") ;
				  if (!str2[1].equals("No Mapping")) {
					  HashMap<String, Object> hash = new HashMap<String, Object>() ;
					  
					  ArrayList<String> mysqlfields = new ArrayList<String>() ; 
					  ArrayList<String> accessfields = new ArrayList<String>() ;
					  
					  hash.put("mysqltable", str2[0]) ; 
					  hash.put("accesstable", str2[1]) ;
					  
					  
					  
					  //Now we need to get fields :(
					  
					  System.out.println(str2[0] + "  " +  str2[1]) ; 
					  
					  ResultSet columns = metaData.getColumns(properties.getString("spring.mysqldatasource.db"),  null,  str2[0], "%");
					 while (columns.next()) {
						  String columnName=columns.getString("COLUMN_NAME");
						  mysqlfields.add(columnName) ; 
						  
					  }
					  
					  //System.out.println(properties.getString("spring.seconddatasource.db"));
					  Table table = db.getTable(str2[1]) ;
					  List<? extends Column> columns2 = table.getColumns();


					  for(Column obj : columns2) {
						  String columnName2= obj.getName() ;
						  accessfields.add(columnName2) ; 
						  
					  }
					  
					  hash.put("mysqlfields", mysqlfields) ; 
					  hash.put("accessfields", accessfields) ; 
					  
					  list.add(hash) ; 
					  
					  
					  
				  }
				  
				  
				  
				  
				  
				  
			  }
			  
			  model.addAttribute("map", list) ;
			  System.out.println(list) ;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ConfigurationException e1) {
			
		}catch(IOException e) {
			System.out.println("Error access...");
		}
		  
		  return "mysqlaccessmap.jsp"; 	 
		  
		  //[{mysqlfields=[id, badge, cache_fk, date_mouv, pointeuse, updated], mysqltable=mouvement, sqlservertable=Mouvement, sqlserverfields=[id, Authentication_Date, Authentication_Date_Time, Authentication_Time, Device_Name, Device_Serial_No, Direction, Person_Name, card]}, {mysqlfields=[id, badge, nom, prenom], mysqltable=user, sqlservertable=Example, sqlserverfields=[id, nom]}]
		  //[{mysqlfields=[id, badge, cache_fk, date_mouv, pointeuse, updated], mysqltable=mouvement, sqlservertable=Example, sqlserverfields=[id, nom]}]
	  }
	  
	  
	  @PostMapping(path="/sqlserveraccessmapfields")
	  public String mapsqlserveraccessfields(Model model, @RequestParam String tableValue) {
		  
		  System.out.println(tableValue) ; 
		  PropertiesConfiguration properties;
		  
		  try {
			DriverManagerDataSource sqlserverDatasource = service.getsqlserverdatasource() ;   
			DatabaseMetaData metaData = sqlserverDatasource.getConnection().getMetaData();  
			properties = new PropertiesConfiguration(conffile);
			Database db = DatabaseBuilder.open(new File(properties.getString("spring.access.path")));
			//Connection conn = mysqlDatasource.getConnection() ; 
			//Statement st = conn.createStatement()
			
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>() ;
			  String[] arr = tableValue.split(";") ; 
			  for (String str : arr) {
				  String[] str2 = str.split(",") ;
				  if (!str2[1].equals("No Mapping")) {
					  HashMap<String, Object> hash = new HashMap<String, Object>() ;
					  
					  ArrayList<String> sqlserverfields = new ArrayList<String>() ; 
					  ArrayList<String> accessfields = new ArrayList<String>() ;
					  
					  hash.put("sqlservertable", str2[0]) ; 
					  hash.put("accesstable", str2[1]) ;
					  
					  
					  
					  //Now we need to get fields :(
					  
					  System.out.println(str2[0] + "  " +  str2[1]) ; 
					  
					  ResultSet columns = metaData.getColumns(properties.getString("spring.sqlserverdatasource.db"),  null,  str2[0], "%");
					 while (columns.next()) {
						  String columnName=columns.getString("COLUMN_NAME");
						  sqlserverfields.add(columnName) ; 
						  
					  }
					  
					  //System.out.println(properties.getString("spring.seconddatasource.db"));
					  Table table = db.getTable(str2[1]) ;
					  List<? extends Column> columns2 = table.getColumns();


					  for(Column obj : columns2) {
						  String columnName2= obj.getName() ;
						  accessfields.add(columnName2) ; 
						  
					  }
					  
					  hash.put("sqlserverfields", sqlserverfields) ; 
					  hash.put("accessfields", accessfields) ; 
					  
					  list.add(hash) ; 
					  
					  
					  
				  }
				  
				  
				  
				  
				  
				  
			  }
			  
			  model.addAttribute("map", list) ;
			  System.out.println(list) ;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ConfigurationException e1) {
			
		}catch(IOException e) {
			System.out.println("Error access...");
		}
		  
		  return "sqlserveraccessmap.jsp"; 	 
		  
		  //[{mysqlfields=[id, badge, cache_fk, date_mouv, pointeuse, updated], mysqltable=mouvement, sqlservertable=Mouvement, sqlserverfields=[id, Authentication_Date, Authentication_Date_Time, Authentication_Time, Device_Name, Device_Serial_No, Direction, Person_Name, card]}, {mysqlfields=[id, badge, nom, prenom], mysqltable=user, sqlservertable=Example, sqlserverfields=[id, nom]}]
		  //[{mysqlfields=[id, badge, cache_fk, date_mouv, pointeuse, updated], mysqltable=mouvement, sqlservertable=Example, sqlserverfields=[id, nom]}]
	  }
	  
	  @PostMapping(path = "savemapping")
	  public String savemapping(@RequestParam String save, @RequestParam String type) {
		  PropertiesConfiguration properties;
		  
		  try {
			properties = new PropertiesConfiguration(conffile) ;
			if (type.equals("mysqlsqlserver")) {
				properties.setProperty("spring.map.mysqlsqlserver", save);
				properties.save();
			}
			else if (type.equals("mysqlaccess")) {
				properties.setProperty("spring.map.mysqlaccess", save);
				properties.save();
			}
			else if (type.equals("sqlserveraccess")) {
				properties.setProperty("spring.map.sqlserveraccess", save);
				properties.save();
			}
			else if (type.equals("mysqlmysql2")) {
				properties.setProperty("spring.map.mysqlmysql2", save);
				properties.save();
			}
			
			
			System.out.println(properties.getString("spring.map.mysqlsqlserver"));
			System.out.println(save);
			
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
		  
		  
		  
		  
		  return "page.jsp" ;
	  }

}
