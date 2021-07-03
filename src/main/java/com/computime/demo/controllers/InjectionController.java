package com.computime.demo.controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.computime.demo.services.MainService;
import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

@Controller
public class InjectionController {
	@Value("${spring.database.conffile}")
	String conffile ; 
	
	@Autowired
	MainService service ; 
	
	@GetMapping(path="/injectsqlservermysql")
	  public String injectsqlservermysql() {
		  
		  try {
		      DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ; 
			  DriverManagerDataSource sqlserverDatasource = service.getsqlserverdatasource() ; 
			  ArrayList<HashMap<String, Object>> list = getMapping(0) ; 
			  ////System.out.println(list) ; 
			  Statement st = mysqlDatasource.getConnection().createStatement() ; 
		      Statement st2 = sqlserverDatasource.getConnection().createStatement() ;
			
			
			  for (HashMap<String, Object> map : list) {
				String mysqltable = (String)map.get("mysqltable") ;
				String sqlservertable = (String) map.get("sqlservertable") ;
				ArrayList<HashMap<String, String>> fieldsarr = (ArrayList<HashMap<String, String>>) map.get("fields") ; 
				//System.out.println(fieldsarr) ;
				//here we can check if data types are compatibles but we don't have time for that;
				String mysqlfields = "";
				String sqlserverfields ="" ;
				ArrayList<String> mysqlfieldsarr = new ArrayList<String>() ; 
				ArrayList<String> sqlserverfieldsarr = new ArrayList<String>() ; 
				ArrayList<Integer> mysqltypes = new ArrayList<Integer>() ;
				ArrayList<Integer> sqlservertypes = new ArrayList<Integer>() ; 
			
				int count =0; 
				
				//PreparedStatement pt = mysqlDatasource.getConnection().prepareStatement("select * from" + mysqltable) ; 
				//PreparedStatement pt2 = sqlserverDatasource.getConnection().prepareStatement("select * from" + sqlservertable) ; 
				
				for (HashMap<String, String> arr : fieldsarr) {
					String mysqlfield = arr.get("mysqlfield") ; 
					String sqlserverfield = arr.get("sqlserverfield") ;
					String mysqltype = arr.get("mysqltype") ; 
					String sqlservertype = arr.get("sqlservertype") ; 
					mysqlfields += mysqlfield + ", "  ;
					sqlserverfields+=sqlserverfield + ", " ;
					mysqlfieldsarr.add(mysqlfield) ; 
					sqlserverfieldsarr.add(sqlserverfield) ; 
					mysqltypes.add(Integer.parseInt(mysqltype)) ; 
					sqlservertypes.add(Integer.parseInt(sqlservertype)) ; 
					count +=1 ; 
					
					
					
					//insert with JDBC here should be fine, we'll still have type checks and access shit to deal with pff
				}
				
				if (mysqlfields.length()>0) {
					mysqlfields = mysqlfields.substring(0,mysqlfields.length()-2) ; 
					
				}
				if (sqlserverfields.length()>0) {
					sqlserverfields = sqlserverfields.substring(0, sqlserverfields.length()-2) ;
				}
				
				
				//System.out.println(mysqlfields + "         " + sqlserverfields) ;
				//System.out.println(list.size()) ;
				ResultSet rs = st.executeQuery("SELECT " + mysqlfields + " FROM " + mysqltable + ";");
				ResultSet rs2 = st2.executeQuery("SELECT " + sqlserverfields + " FROM " + sqlservertable + ";");
				
				if (count > 0) {
					while (rs2.next()) {
						String interogation = "" ; 
						if (count ==1 ) {
						interogation = "?" ;
						}
						else {
							interogation = "?,".repeat(count) ;
							interogation = interogation.substring(0, interogation.length() -1) ;
						}
						
						PreparedStatement pt = mysqlDatasource.getConnection().prepareStatement("INSERT INTO " + mysqltable + " (" +mysqlfields + ") VALUES ("+ interogation +")") ; 
						String query = "SELECT " + mysqlfields + " FROM " + mysqltable + " WHERE " ; 
						
						for (int i= 1 ; i<=count; i++) {
						
							if (type(mysqltypes.get(i-1))==0) {

								if (type(sqlservertypes.get(i-1))==0) {
									pt.setInt(i, rs2.getInt(sqlserverfieldsarr.get(i-1)));
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getInt(sqlserverfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getInt(sqlserverfieldsarr.get(i-1)) ; 
									}
								}
								else if (type(sqlservertypes.get(i-1))==5) {
									pt.setInt(i, (int)rs2.getDouble(sqlserverfieldsarr.get(i-1)));
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + (int)rs2.getDouble(sqlserverfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + (int)rs2.getDouble(sqlserverfieldsarr.get(i-1)) ; 
									}

								}
								else {
									pt.setInt(i, Integer.parseInt(rs2.getString(sqlserverfieldsarr.get(i-1))));
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "= \""  + rs2.getString(sqlserverfieldsarr.get(i-1)) + "\" and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=\"" + rs2.getString(sqlserverfieldsarr.get(i-1)) + "\"" ; 
									}
								}
								
							}
							else if (type(mysqltypes.get(i-1))==5) {
								
								if (type(sqlservertypes.get(i-1))==0) {
									pt.setDouble(i, rs2.getInt(sqlserverfieldsarr.get(i-1)));
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getInt(sqlserverfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getInt(sqlserverfieldsarr.get(i-1)) ; 
									}
								}
								else if (type(sqlservertypes.get(i-1))==5) {
									pt.setDouble(i, rs2.getDouble(sqlserverfieldsarr.get(i-1)));
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getDouble(sqlserverfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getDouble(sqlserverfieldsarr.get(i-1)) ; 
									}
								}
								else {
									pt.setDouble(i, Double.parseDouble(rs2.getString(sqlserverfieldsarr.get(i-1))));
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "= \""  + rs2.getString(sqlserverfieldsarr.get(i-1)) + "\" and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=\"" + rs2.getString(sqlserverfieldsarr.get(i-1)) + "\"" ; 
									}
								}
								
							}
							else if (type(mysqltypes.get(i-1))==1) {
								pt.setString(i, rs2.getString(sqlserverfieldsarr.get(i-1)));
								
								if (type(sqlservertypes.get(i-1))==0) {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getString(sqlserverfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getString(sqlserverfieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "= \""  + rs2.getString(sqlserverfieldsarr.get(i-1)) + "\" and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=\"" + rs2.getString(sqlserverfieldsarr.get(i-1)) + "\"" ; 
									}
								}
							}
						
							else if (type(mysqltypes.get(i-1))==2) {
								pt.setDate(i, rs2.getDate(sqlserverfieldsarr.get(i-1)));
								if (type(sqlservertypes.get(i-1))==0) {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getDate(sqlserverfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getDate(sqlserverfieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "= \""  + rs2.getDate(sqlserverfieldsarr.get(i-1)) + "\" and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=\"" + rs2.getDate(sqlserverfieldsarr.get(i-1)) + "\"" ; 
									}
								}
							}
							
							else if (type(mysqltypes.get(i-1))==3) {
								Calendar utcCalendar = Calendar.getInstance();
		
								pt.setTimestamp(i, rs2.getTimestamp(sqlserverfieldsarr.get(i-1)),utcCalendar) ; 
								if (type(sqlservertypes.get(i-1))==0) {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getString(sqlserverfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getString(sqlserverfieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "= \""  + rs2.getString(sqlserverfieldsarr.get(i-1)) + "\" and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=\"" + rs2.getString(sqlserverfieldsarr.get(i-1)) + "\"" ; 
									}
								}
							}else if (type(mysqltypes.get(i-1))==4) {
								pt.setTime(i, rs2.getTime(sqlserverfieldsarr.get(i-1))) ; 
								if (type(sqlservertypes.get(i-1))==0) {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getString(sqlserverfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getString(sqlserverfieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "= \""  + rs2.getString(sqlserverfieldsarr.get(i-1)) + "\" and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=\"" + rs2.getString(sqlserverfieldsarr.get(i-1)) + "\"" ; 
									}
								}
							}
							
							
						}
						//System.out.println(query) ;
						Statement st3 = mysqlDatasource.getConnection().createStatement() ; 
						ResultSet rs3 = st3.executeQuery(query + ";");
						if (rs3.next()) {

						}
						else {
							pt.execute() ; 
						}
						
						
						/*
						for (int i= 1 ; i<=count; i++) {
							ResultSet rs3 = st.executeQuery("SELECT " + mysqlfields + " FROM " + mysqltable + " WHERE " + where + ";");
							if (rs3.next()) {
							}
							else {
								if (type(sqlservertypes.get(i-1))==0) {
									pt.setInt(i, rs2.getInt(sqlserverfieldsarr.get(i-1)));
								}
								else if (type(sqlservertypes.get(i-1))==1) {
									pt.setString(i, rs2.getString(sqlserverfieldsarr.get(i-1)));
								}
							
								else if (type(sqlservertypes.get(i-1))==2) {
									pt.setDate(i, rs2.getDate(sqlserverfieldsarr.get(i-1)));
								}
							
							}
						}*/
						
						
						
					}
				}
				
				
				
				
			}
			
			//System.out.println(list) ; 
			return "success.jsp" ;
		  
		  }catch(SQLException e) {
			  e.printStackTrace();
		  }
		  
		  return "failure.jsp" ;
	  }
	  
	  @GetMapping(path="/injectmysql2mysql")
	  public String injectmysql2mysql() {
		  
		  try {
		      DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ; 
			  DriverManagerDataSource mysql2Datasource = service.getmysql2datasource() ; 
			  ArrayList<HashMap<String, Object>> list = getMapping(1) ; 
			  //System.out.println(list) ; 
			  Statement st = mysqlDatasource.getConnection().createStatement() ; 
		      Statement st2 = mysql2Datasource.getConnection().createStatement() ;
			
			
			  for (HashMap<String, Object> map : list) {
				String mysqltable = (String)map.get("mysqltable") ;
				String mysql2table = (String) map.get("mysql2table") ;
				ArrayList<HashMap<String, String>> fieldsarr = (ArrayList<HashMap<String, String>>) map.get("fields") ; 
				//System.out.println(fieldsarr) ;
				//here we can check if data types are compatibles but we don't have time for that;
				String mysqlfields = "";
				String mysql2fields ="" ;
				ArrayList<String> mysqlfieldsarr = new ArrayList<String>() ; 
				ArrayList<String> mysql2fieldsarr = new ArrayList<String>() ; 
				ArrayList<Integer> mysqltypes = new ArrayList<Integer>() ;
				ArrayList<Integer> mysql2types = new ArrayList<Integer>() ; 
			
				int count =0; 
				
				//PreparedStatement pt = mysqlDatasource.getConnection().prepareStatement("select * from" + mysqltable) ; 
				//PreparedStatement pt2 = mysql2Datasource.getConnection().prepareStatement("select * from" + mysql2table) ; 
				
				for (HashMap<String, String> arr : fieldsarr) {
					String mysqlfield = arr.get("mysqlfield") ; 
					String mysql2field = arr.get("mysql2field") ;
					String mysqltype = arr.get("mysqltype") ; 
					String mysql2type = arr.get("mysql2type") ; 
					mysqlfields += mysqlfield + ", "  ;
					mysql2fields+=mysql2field + ", " ;
					mysqlfieldsarr.add(mysqlfield) ; 
					mysql2fieldsarr.add(mysql2field) ; 
					mysqltypes.add(Integer.parseInt(mysqltype)) ; 
					mysql2types.add(Integer.parseInt(mysql2type)) ; 
					count +=1 ; 
					
					
					
					//insert with JDBC here should be fine, we'll still have type checks and access shit to deal with pff
				}
				
				if (mysqlfields.length()>0) {
					mysqlfields = mysqlfields.substring(0,mysqlfields.length()-2) ; 
					
				}
				if (mysql2fields.length()>0) {
					mysql2fields = mysql2fields.substring(0, mysql2fields.length()-2) ;
				}
				
				
				//System.out.println(mysqlfields + "         " + mysql2fields) ;
				//System.out.println(list.size()) ;
				ResultSet rs = st.executeQuery("SELECT " + mysqlfields + " FROM " + mysqltable + ";");
				ResultSet rs2 = st2.executeQuery("SELECT " + mysql2fields + " FROM " + mysql2table + ";");
				
				if (count > 0) {
					while (rs2.next()) {
						String interogation = "" ; 
						if (count ==1 ) {
						interogation = "?" ;
						}
						else {
							interogation = "?,".repeat(count) ;
							interogation = interogation.substring(0, interogation.length() -1) ;
						}
						
						PreparedStatement pt = mysqlDatasource.getConnection().prepareStatement("INSERT INTO " + mysqltable + " (" +mysqlfields + ") VALUES ("+ interogation +")") ; 
						String query = "SELECT " + mysqlfields + " FROM " + mysqltable + " WHERE " ; 
						
						for (int i= 1 ; i<=count; i++) {
						
							if (type(mysqltypes.get(i-1))==0) {

								if (type(mysql2types.get(i-1))==0) {
									pt.setInt(i, rs2.getInt(mysql2fieldsarr.get(i-1)));
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getInt(mysql2fieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getInt(mysql2fieldsarr.get(i-1)) ; 
									}
								}
								else if (type(mysql2types.get(i-1))==5) {
									pt.setInt(i, (int)rs2.getDouble(mysql2fieldsarr.get(i-1)));
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + (int)rs2.getDouble(mysql2fieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + (int)rs2.getDouble(mysql2fieldsarr.get(i-1)) ; 
									}

								}
								else {
									pt.setInt(i, Integer.parseInt(rs2.getString(mysql2fieldsarr.get(i-1))));
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "= \""  + rs2.getString(mysql2fieldsarr.get(i-1)) + "\" and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=\"" + rs2.getString(mysql2fieldsarr.get(i-1)) + "\"" ; 
									}
								}
								
							}
							else if (type(mysqltypes.get(i-1))==5) {
								
								if (type(mysql2types.get(i-1))==0) {
									pt.setDouble(i, rs2.getInt(mysql2fieldsarr.get(i-1)));
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getInt(mysql2fieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getInt(mysql2fieldsarr.get(i-1)) ; 
									}
								}
								else if (type(mysql2types.get(i-1))==5) {
									pt.setDouble(i, rs2.getDouble(mysql2fieldsarr.get(i-1)));
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getDouble(mysql2fieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getDouble(mysql2fieldsarr.get(i-1)) ; 
									}
								}
								else {
									pt.setDouble(i, Double.parseDouble(rs2.getString(mysql2fieldsarr.get(i-1))));
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "= \""  + rs2.getString(mysql2fieldsarr.get(i-1)) + "\" and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=\"" + rs2.getString(mysql2fieldsarr.get(i-1)) + "\"" ; 
									}
								}
								
							}
							else if (type(mysqltypes.get(i-1))==1) {
								pt.setString(i, rs2.getString(mysql2fieldsarr.get(i-1)));
								
								if (type(mysql2types.get(i-1))==0) {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getString(mysql2fieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getString(mysql2fieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "= \""  + rs2.getString(mysql2fieldsarr.get(i-1)) + "\" and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=\"" + rs2.getString(mysql2fieldsarr.get(i-1)) + "\"" ; 
									}
								}
							}
						
							else if (type(mysqltypes.get(i-1))==2) {
								pt.setDate(i, rs2.getDate(mysql2fieldsarr.get(i-1)));
								if (type(mysql2types.get(i-1))==0) {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getDate(mysql2fieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getDate(mysql2fieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "= \""  + rs2.getDate(mysql2fieldsarr.get(i-1)) + "\" and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=\"" + rs2.getDate(mysql2fieldsarr.get(i-1)) + "\"" ; 
									}
								}
							}
							
							else if (type(mysqltypes.get(i-1))==3) {
		
								pt.setTimestamp(i, rs2.getTimestamp(mysql2fieldsarr.get(i-1))) ; 
								if (type(mysql2types.get(i-1))==0) {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getString(mysql2fieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getString(mysql2fieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "= \""  + rs2.getString(mysql2fieldsarr.get(i-1)) + "\" and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=\"" + rs2.getString(mysql2fieldsarr.get(i-1)) + "\"" ; 
									}
								}
							}else if (type(mysqltypes.get(i-1))==4) {
								pt.setTime(i, rs2.getTime(mysql2fieldsarr.get(i-1))) ; 
								if (type(mysql2types.get(i-1))==0) {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getString(mysql2fieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=" + rs2.getString(mysql2fieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += mysqlfieldsarr.get(i-1) + "= \""  + rs2.getString(mysql2fieldsarr.get(i-1)) + "\" and " ; 
									}
									else {
										query += mysqlfieldsarr.get(i-1) + "=\"" + rs2.getString(mysql2fieldsarr.get(i-1)) + "\"" ; 
									}
								}
							}
							
							
						}
						//System.out.println(query) ;
						Statement st3 = mysqlDatasource.getConnection().createStatement() ; 
						ResultSet rs3 = st3.executeQuery(query + ";");
						if (rs3.next()) {

						}
						else {
							pt.execute() ; 
						}
						
						
						/*
						for (int i= 1 ; i<=count; i++) {
							ResultSet rs3 = st.executeQuery("SELECT " + mysqlfields + " FROM " + mysqltable + " WHERE " + where + ";");
							if (rs3.next()) {
							}
							else {
								if (type(mysql2types.get(i-1))==0) {
									pt.setInt(i, rs2.getInt(mysql2fieldsarr.get(i-1)));
								}
								else if (type(mysql2types.get(i-1))==1) {
									pt.setString(i, rs2.getString(mysql2fieldsarr.get(i-1)));
								}
							
								else if (type(mysql2types.get(i-1))==2) {
									pt.setDate(i, rs2.getDate(mysql2fieldsarr.get(i-1)));
								}
							
							}
						}*/
						
						
						
					}
				}
				
				
				
				
			}
			
			//System.out.println(list) ; 
			return "success.jsp" ; 
		  
		  }catch(SQLException e) {
			  e.printStackTrace() ; 
		  }
		  
		  return "failure.jsp" ;
	  }
	  
	  
	  @GetMapping(path="/injectmysqlsqlserver")
	  public String injectmysqlsqlserver() {
		  
		  try {
	      DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ; 
		  DriverManagerDataSource sqlserverDatasource = service.getsqlserverdatasource() ; 
		  PropertiesConfiguration properties = service.getproperties() ; 
		  //get the mapping
		  String mapsummary = properties.getString("spring.map.mysqlsqlserver") ;
		  Statement st = mysqlDatasource.getConnection().createStatement() ; 
	      Statement st2 = sqlserverDatasource.getConnection().createStatement() ;
	      ArrayList<HashMap<String, Object>> list = getMapping(0) ; 
			
	      for (HashMap<String, Object> map : list) {
				String mysqltable = (String)map.get("mysqltable") ;
				String sqlservertable = (String) map.get("sqlservertable") ;
				ArrayList<HashMap<String, String>> fieldsarr = (ArrayList<HashMap<String, String>>) map.get("fields") ; 
				//System.out.println(fieldsarr) ;
				//here we can check if data types are compatibles but we don't have time for that;
				String mysqlfields = "";
				String sqlserverfields ="" ;
				ArrayList<String> mysqlfieldsarr = new ArrayList<String>() ; 
				ArrayList<String> sqlserverfieldsarr = new ArrayList<String>() ; 
				ArrayList<Integer> mysqltypes = new ArrayList<Integer>() ;
				ArrayList<Integer> sqlservertypes = new ArrayList<Integer>() ; 
				//Statement st = mysqlDatasource.getConnection().createStatement() ; 
				//Statement st2 = sqlserverDatasource.getConnection().createStatement() ;
				
				int count =0; 
				
				//PreparedStatement pt = mysqlDatasource.getConnection().prepareStatement("select * from" + mysqltable) ; 
				//PreparedStatement pt2 = sqlserverDatasource.getConnection().prepareStatement("select * from" + sqlservertable) ; 
				
				for (HashMap<String, String> arr : fieldsarr) {
					String mysqlfield = arr.get("mysqlfield") ; 
					String sqlserverfield = arr.get("sqlserverfield") ;
					String mysqltype = arr.get("mysqltype") ; 
					String sqlservertype = arr.get("sqlservertype") ; 
					mysqlfields += mysqlfield + ", "  ;
					sqlserverfields+=sqlserverfield + ", " ;
					mysqlfieldsarr.add(mysqlfield) ; 
					sqlserverfieldsarr.add(sqlserverfield) ; 
					mysqltypes.add(Integer.parseInt(mysqltype)) ; 
					sqlservertypes.add(Integer.parseInt(sqlservertype)) ; 
					count +=1 ; 
					
					
					
					//insert with JDBC here should be fine, we'll still have type checks and access shit to deal with pff
				}
				
				if (mysqlfields.length()>0) {
					mysqlfields = mysqlfields.substring(0,mysqlfields.length()-2) ; 
					
				}
				if (sqlserverfields.length()>0) {
					sqlserverfields = sqlserverfields.substring(0, sqlserverfields.length()-2) ;
				}
				
				
				//System.out.println(mysqlfields + "         " + sqlserverfields) ;
				//System.out.println(list.size()) ;
				ResultSet rs = st.executeQuery("SELECT " + mysqlfields + " FROM " + mysqltable + ";");
				ResultSet rs2 = st2.executeQuery("SELECT " + sqlserverfields + " FROM " + sqlservertable + ";");
				
				if (count > 0) {
					while (rs.next()) {
						String interogation = "" ; 
						if (count ==1 ) {
						interogation = "?" ;
						}
						else {
							interogation = "?,".repeat(count) ;
							interogation = interogation.substring(0, interogation.length() -1) ;
						}
						
						PreparedStatement pt = sqlserverDatasource.getConnection().prepareStatement("INSERT INTO " + sqlservertable + " (" +sqlserverfields + ") VALUES ("+ interogation +")") ; 
						String query = "SELECT " + sqlserverfields + " FROM " + sqlservertable + " WHERE " ; 
						//System.out.println(count) ;
						for (int i= 1 ; i<=count; i++) {
						
							if (type(sqlservertypes.get(i-1))==0) {
								pt.setInt(i, rs.getInt(mysqlfieldsarr.get(i-1)));
								if (type(mysqltypes.get(i-1))==0) {
									pt.setInt(i, rs.getInt(mysqlfieldsarr.get(i-1)));
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getInt(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getInt(mysqlfieldsarr.get(i-1)) ; 
									}
								}else if (type(mysqltypes.get(i-1))==5){
									pt.setInt(i, (int)rs.getDouble(mysqlfieldsarr.get(i-1)));
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "=" + (int)rs.getDouble(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=" + (int)rs.getDouble(mysqlfieldsarr.get(i-1)) ; 
									}
								}
								else {
									pt.setInt(i, Integer.parseInt(rs.getString(mysqlfieldsarr.get(i-1))));
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "=\'" + rs.getInt(mysqlfieldsarr.get(i-1)) + "\'" + " and " ;                                                                                                                                                      
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=\'" + rs.getInt(mysqlfieldsarr.get(i-1)) + "\'" ; 
									}
								}
								
							}
							else if (type(sqlservertypes.get(i-1))==1) {
								pt.setString(i, rs.getString(mysqlfieldsarr.get(i-1)));
								
								if (type(mysqltypes.get(i-1))==0) {
									pt.setString(i, Integer.toString(rs.getInt(mysqlfieldsarr.get(i-1))));
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getInt(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getInt(mysqlfieldsarr.get(i-1)) ; 
									}
								}else if (type(mysqltypes.get(i-1))==5) {
									pt.setString(i, Double.toString(rs.getDouble(mysqlfieldsarr.get(i-1))));
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getDouble(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getDouble(mysqlfieldsarr.get(i-1)) ; 
									}
								}
								else {
									pt.setString(i, rs.getString(mysqlfieldsarr.get(i-1)));
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "= \'"  + rs.getString(mysqlfieldsarr.get(i-1)) + "\' and " ; 
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=\'" + rs.getString(mysqlfieldsarr.get(i-1)) + "\'" ; 
									}
								}
							}
						
							else if (type(sqlservertypes.get(i-1))==2) {
								pt.setDate(i, rs.getDate(mysqlfieldsarr.get(i-1)));
								if (type(mysqltypes.get(i-1))==0) {
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getDate(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getDate(mysqlfieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "=\'"  + rs.getString(mysqlfieldsarr.get(i-1)) + "\'and " ; 
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=\'" + rs.getString(mysqlfieldsarr.get(i-1))+"\'"  ;
										
									}
								}
							}
							else if (type(sqlservertypes.get(i-1))==3) {
								
								Calendar utcCalendar = Calendar.getInstance();
								pt.setTimestamp(i, rs.getTimestamp(mysqlfieldsarr.get(i-1), utcCalendar)) ; 
								if (type(mysqltypes.get(i-1))==0) {
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getString(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getString(mysqlfieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "= \'"  + rs.getString(mysqlfieldsarr.get(i-1)) + "\' and " ; 
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=\'" + rs.getString(mysqlfieldsarr.get(i-1)) + "\'" ; 
									}
								}
							}else if (type(sqlservertypes.get(i-1))==4) {
								pt.setTime(i, rs.getTime(mysqlfieldsarr.get(i-1))) ; 
								if (type(mysqltypes.get(i-1))==0) {
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getString(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getString(mysqlfieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "= \'"  + rs.getString(mysqlfieldsarr.get(i-1)) + "\' and " ; 
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=\'" + rs.getString(mysqlfieldsarr.get(i-1)) + "\'" ; 
									}
								}
							}else if (type(sqlservertypes.get(i-1))==5) {
								
								if (type(mysqltypes.get(i-1))==0) {
									pt.setDouble(i, rs.getInt(mysqlfieldsarr.get(i-1)));
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getInt(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getInt(mysqlfieldsarr.get(i-1)) ; 
									}
								}else if (type(mysqltypes.get(i-1))==5){
									pt.setDouble(i, (int)rs.getDouble(mysqlfieldsarr.get(i-1)));
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getDouble(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=" + rs.getDouble(mysqlfieldsarr.get(i-1)) ; 
									}
								}
								else {
									pt.setDouble(i, Double.parseDouble(rs.getString(mysqlfieldsarr.get(i-1))));
									if (i != count) {
										query += sqlserverfieldsarr.get(i-1) + "=\'" + rs.getString(mysqlfieldsarr.get(i-1)) + "\'" + " and " ;                                                                                                                                                      
									}
									else {
										query += sqlserverfieldsarr.get(i-1) + "=\'" + rs.getString(mysqlfieldsarr.get(i-1)) + "\'" ; 
									}
								}
								
							}
							
							
						}
						
						//System.out.println(query) ; 
						
						ResultSet rs3 = st2.executeQuery(query + ";");
						if (rs3.next()) {
							////System.out.println("no") ; 
						}
						else {
							pt.execute() ;
						}
						
						
						
						
					}
				}
				
				
				
				
			}
			
			//System.out.println(list) ; 
			return "success.jsp" ; 
		  
		  }catch(SQLException e) {
			  e.printStackTrace();
		  }
		  
		  return "failure.jsp" ;
	  }
	  
	  @GetMapping(path="/injectmysqlmysql2")
	  public String injectmysqlmysql2() {
		  
		  try {
	      DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ; 
		  DriverManagerDataSource mysql2Datasource = service.getmysql2datasource() ; 
		  PropertiesConfiguration properties = service.getproperties() ; 
		  //get the mapping
		  String mapsummary = properties.getString("spring.map.mysqlmysql2") ;
		  Statement st = mysqlDatasource.getConnection().createStatement() ; 
	      Statement st2 = mysql2Datasource.getConnection().createStatement() ;
	      ArrayList<HashMap<String, Object>> list = getMapping(1) ; 
			
	      for (HashMap<String, Object> map : list) {
				String mysqltable = (String)map.get("mysqltable") ;
				String mysql2table = (String) map.get("mysql2table") ;
				ArrayList<HashMap<String, String>> fieldsarr = (ArrayList<HashMap<String, String>>) map.get("fields") ; 
				//System.out.println(fieldsarr) ;
				//here we can check if data types are compatibles but we don't have time for that;
				String mysqlfields = "";
				String mysql2fields ="" ;
				ArrayList<String> mysqlfieldsarr = new ArrayList<String>() ; 
				ArrayList<String> mysql2fieldsarr = new ArrayList<String>() ; 
				ArrayList<Integer> mysqltypes = new ArrayList<Integer>() ;
				ArrayList<Integer> mysql2types = new ArrayList<Integer>() ; 
				//Statement st = mysqlDatasource.getConnection().createStatement() ; 
				//Statement st2 = mysql2Datasource.getConnection().createStatement() ;
				
				int count =0; 
				
				//PreparedStatement pt = mysqlDatasource.getConnection().prepareStatement("select * from" + mysqltable) ; 
				//PreparedStatement pt2 = mysql2Datasource.getConnection().prepareStatement("select * from" + mysql2table) ; 
				
				for (HashMap<String, String> arr : fieldsarr) {
					String mysqlfield = arr.get("mysqlfield") ; 
					String mysql2field = arr.get("mysql2field") ;
					String mysqltype = arr.get("mysqltype") ; 
					String mysql2type = arr.get("mysql2type") ; 
					mysqlfields += mysqlfield + ", "  ;
					mysql2fields+=mysql2field + ", " ;
					mysqlfieldsarr.add(mysqlfield) ; 
					mysql2fieldsarr.add(mysql2field) ; 
					mysqltypes.add(Integer.parseInt(mysqltype)) ; 
					mysql2types.add(Integer.parseInt(mysql2type)) ; 
					count +=1 ; 
					
					
					
					//insert with JDBC here should be fine, we'll still have type checks and access shit to deal with pff
				}
				
				if (mysqlfields.length()>0) {
					mysqlfields = mysqlfields.substring(0,mysqlfields.length()-2) ; 
					
				}
				if (mysql2fields.length()>0) {
					mysql2fields = mysql2fields.substring(0, mysql2fields.length()-2) ;
				}
				
				
				//System.out.println(mysqlfields + "         " + mysql2fields) ;
				//System.out.println(list.size()) ;
				ResultSet rs = st.executeQuery("SELECT " + mysqlfields + " FROM " + mysqltable + ";");
				ResultSet rs2 = st2.executeQuery("SELECT " + mysql2fields + " FROM " + mysql2table + ";");
				
				if (count > 0) {
					while (rs.next()) {
						String interogation = "" ; 
						if (count ==1 ) {
						interogation = "?" ;
						}
						else {
							interogation = "?,".repeat(count) ;
							interogation = interogation.substring(0, interogation.length() -1) ;
						}
						
						PreparedStatement pt = mysql2Datasource.getConnection().prepareStatement("INSERT INTO " + mysql2table + " (" +mysql2fields + ") VALUES ("+ interogation +")") ; 
						String query = "SELECT " + mysql2fields + " FROM " + mysql2table + " WHERE " ; 
						//System.out.println(count) ;
						for (int i= 1 ; i<=count; i++) {
						
							if (type(mysql2types.get(i-1))==0) {
								pt.setInt(i, rs.getInt(mysqlfieldsarr.get(i-1)));
								if (type(mysqltypes.get(i-1))==0) {
									pt.setInt(i, rs.getInt(mysqlfieldsarr.get(i-1)));
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getInt(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getInt(mysqlfieldsarr.get(i-1)) ; 
									}
								}else if (type(mysqltypes.get(i-1))==5){
									pt.setInt(i, (int)rs.getDouble(mysqlfieldsarr.get(i-1)));
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "=" + (int)rs.getDouble(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=" + (int)rs.getDouble(mysqlfieldsarr.get(i-1)) ; 
									}
								}
								else {
									pt.setInt(i, Integer.parseInt(rs.getString(mysqlfieldsarr.get(i-1))));
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "=\'" + rs.getInt(mysqlfieldsarr.get(i-1)) + "\'" + " and " ;                                                                                                                                                      
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=\'" + rs.getInt(mysqlfieldsarr.get(i-1)) + "\'" ; 
									}
								}
								
							}
							else if (type(mysql2types.get(i-1))==1) {
								pt.setString(i, rs.getString(mysqlfieldsarr.get(i-1)));
								
								if (type(mysqltypes.get(i-1))==0) {
									pt.setString(i, Integer.toString(rs.getInt(mysqlfieldsarr.get(i-1))));
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getInt(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getInt(mysqlfieldsarr.get(i-1)) ; 
									}
								}else if (type(mysqltypes.get(i-1))==5) {
									pt.setString(i, Double.toString(rs.getDouble(mysqlfieldsarr.get(i-1))));
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getDouble(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getDouble(mysqlfieldsarr.get(i-1)) ; 
									}
								}
								else {
									pt.setString(i, rs.getString(mysqlfieldsarr.get(i-1)));
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "= \'"  + rs.getString(mysqlfieldsarr.get(i-1)) + "\' and " ; 
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=\'" + rs.getString(mysqlfieldsarr.get(i-1)) + "\'" ; 
									}
								}
							}
						
							else if (type(mysql2types.get(i-1))==2) {
								pt.setDate(i, rs.getDate(mysqlfieldsarr.get(i-1)));
								if (type(mysqltypes.get(i-1))==0) {
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getDate(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getDate(mysqlfieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "=\'"  + rs.getString(mysqlfieldsarr.get(i-1)) + "\'and " ; 
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=\'" + rs.getString(mysqlfieldsarr.get(i-1))+"\'"  ;
										
									}
								}
							}
							else if (type(mysql2types.get(i-1))==3) {
								
								pt.setTimestamp(i, rs.getTimestamp(mysqlfieldsarr.get(i-1))) ; 
								if (type(mysqltypes.get(i-1))==0) {
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getString(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getString(mysqlfieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "= \'"  + rs.getString(mysqlfieldsarr.get(i-1)) + "\' and " ; 
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=\'" + rs.getString(mysqlfieldsarr.get(i-1)) + "\'" ; 
									}
								}
							}else if (type(mysql2types.get(i-1))==4) {
								pt.setTime(i, rs.getTime(mysqlfieldsarr.get(i-1))) ; 
								if (type(mysqltypes.get(i-1))==0) {
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getString(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getString(mysqlfieldsarr.get(i-1)) ; 
									}
								}
								else {
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "= \'"  + rs.getString(mysqlfieldsarr.get(i-1)) + "\' and " ; 
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=\'" + rs.getString(mysqlfieldsarr.get(i-1)) + "\'" ; 
									}
								}
							}else if (type(mysql2types.get(i-1))==5) {
								pt.setDouble(i, rs.getInt(mysqlfieldsarr.get(i-1)));
								if (type(mysqltypes.get(i-1))==0) {
									pt.setInt(i, rs.getInt(mysqlfieldsarr.get(i-1)));
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getInt(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getInt(mysqlfieldsarr.get(i-1)) ; 
									}
								}else if (type(mysqltypes.get(i-1))==5){
									pt.setDouble(i, rs.getDouble(mysqlfieldsarr.get(i-1)));
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getDouble(mysqlfieldsarr.get(i-1)) + " and " ; 
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=" + rs.getDouble(mysqlfieldsarr.get(i-1)) ; 
									}
								}
								else {
									pt.setDouble(i, Double.parseDouble(rs.getString(mysqlfieldsarr.get(i-1))));
									if (i != count) {
										query += mysql2fieldsarr.get(i-1) + "=\'" + rs.getString(mysqlfieldsarr.get(i-1)) + "\'" + " and " ;                                                                                                                                                      
									}
									else {
										query += mysql2fieldsarr.get(i-1) + "=\'" + rs.getString(mysqlfieldsarr.get(i-1)) + "\'" ; 
									}
								}
								
							}
							
							
						}
						
						//System.out.println(query) ; 
						
						ResultSet rs3 = st2.executeQuery(query + ";");
						if (rs3.next()) {
							////System.out.println("no") ; 
						}
						else {
							pt.execute() ;
						}
						
						
						
						
					}
				}
				
				
				
				
			}
			
			//System.out.println(list) ; 
			return "success.jsp" ; 
		  
		  }catch(SQLException e) {
			  e.printStackTrace();
		  }
		  
		  return "failure.jsp" ;
	  }
	  
	  @GetMapping(path="/injectaccessmysql")
	  public String injectaccessmysql() {
		  try {
		      DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ; 
		      Database db = service.getAccessDatabase() ; 
			  //get the mapping
			  Statement st = mysqlDatasource.getConnection().createStatement() ; 

			  
			  ArrayList<HashMap<String, Object>> list = getMapping(2) ; 
				
				
				
			for (HashMap<String, Object> map : list) {
					String mysqltable = (String)map.get("mysqltable") ;
					String accesstable = (String) map.get("accesstable") ;
					ArrayList<HashMap<String, String>> fieldsarr = (ArrayList<HashMap<String, String>>) map.get("fields") ; 
					//System.out.println(fieldsarr) ;
					//here we can check if data types are compatibles but we don't have time for that;
					String mysqlfields = "";
					String accessfields ="" ;
					
					
					int count =fieldsarr.size(); 
					
					//PreparedStatement pt = mysqlDatasource.getConnection().prepareStatement("select * from" + mysqltable) ; 
					//PreparedStatement pt2 = sqlserverDatasource.getConnection().prepareStatement("select * from" + sqlservertable) ; 
					
					for (HashMap<String, String> arr : fieldsarr) {
						String mysqlfield = arr.get("mysqlfield") ; 
						String accessfield = arr.get("accessfield") ; 
						mysqlfields += mysqlfield + ", "  ;
						accessfields += accessfield + ", " ; 
						
					}
					
					if (mysqlfields.length()>0) {
						mysqlfields = mysqlfields.substring(0,mysqlfields.length()-2) ; 
						
					}
					if (accessfields.length()>0) {
						accessfields = accessfields.substring(0, accessfields.length()-2) ;
					}
					
					
					//System.out.println(mysqlfields + "         " + accessfields) ;
					ResultSet rs = st.executeQuery("SELECT " + mysqlfields + " FROM " + mysqltable + ";");
					Table table = db.getTable(accesstable) ;
					Cursor cur = table.getDefaultCursor() ;
					/*while(cur.moveToNextRow()) {
						Row row = cur.getCurrentRow() ; 
						LocalDateTime ldt  = row.getLocalDateTime("CHECKTIME") ;
						Timestamp ts = Timestamp.valueOf(ldt) ;
						//System.out.println(row.getDouble("sn")) ; 
						//System.out.println(ldt) ; 
						//System.out.println(ts) ; 
					}*/
					
					if (count > 0) {
						
						while (cur.moveToNextRow()) {
							Row row = cur.getCurrentRow() ; 
							String interogation = "" ; 
							if (count ==1 ) {
							interogation = "?" ;
							}
							else {
								interogation = "?,".repeat(count) ;
								interogation = interogation.substring(0, interogation.length() -1) ;
							}
							
							PreparedStatement pt = mysqlDatasource.getConnection().prepareStatement("INSERT INTO " + mysqltable + " (" +mysqlfields + ") VALUES ("+ interogation +")") ; 
							String query = "SELECT " + mysqlfields + " FROM " + mysqltable + " WHERE " ; 
							int i = 0 ; 
							for (HashMap<String, String> arr : fieldsarr) {
								i++ ; 
								if (type(Integer.parseInt(arr.get("mysqltype")))==0) {
									
									if (type(Integer.parseInt(arr.get("accesstype")))==0) {
										pt.setInt(i, row.getInt(arr.get("accessfield")));
										if (i != count) {
											query += arr.get("mysqlfield") + "=" + row.getInt(arr.get("accessfield")) + " and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=" + row.getInt(arr.get("accessfield")) ; 
										}
									}
									else if (type(Integer.parseInt(arr.get("accesstype")))==5) {
										pt.setInt(i,row.getDouble(arr.get("accessfield")).intValue());
										if (i != count) {
											query += arr.get("mysqlfield") + "=" + row.getDouble(arr.get("accessfield")).intValue() + " and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=" + row.getDouble(arr.get("accessfield")).intValue() ; 
										}
									}
									else {
										pt.setInt(i,Integer.parseInt(row.getString(arr.get("accessfield"))));
										if (i != count) {
											query += arr.get("mysqlfield") + "= \""  + row.getString(arr.get("accessfield")) + "\" and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=\"" + row.getString(arr.get("accessfield")) + "\"" ; 
										}
									}
									
								}
								else if (type(Integer.parseInt(arr.get("mysqltype")))==1) {
									
									
									if (type(Integer.parseInt(arr.get("accesstype")))==0) {
										pt.setString(i, Integer.toString(row.getInt(arr.get("accessfield"))));
										if (i != count) {
											query += arr.get("mysqlfield") + "=" + row.getInt(arr.get("accessfield")) + " and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=" + row.getInt(arr.get("accessfield")) ; 
										}
									}
									else if (type(Integer.parseInt(arr.get("accesstype")))==5) {
										pt.setString(i, Double.toString(row.getDouble("accessfield")));
										if (i != count) {
											query += arr.get("mysqlfield") + "=" + row.getDouble(arr.get("accessfield")) + " and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=" + row.getDouble(arr.get("accessfield")) ; 
										}
									}
									else {
										pt.setString(i,  row.getString(arr.get("accessfield")));
										if (i != count) {
											query += arr.get("mysqlfield") + "= \""  + row.getString(arr.get("accessfield")) + "\" and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=\"" + row.getString(arr.get("accessfield")) + "\"" ; 
										}
									}
								}
							
								else if (type(Integer.parseInt(arr.get("mysqltype")))==2) {
									LocalDateTime ldt  = row.getLocalDateTime(arr.get("accessfield")) ;
									Timestamp ts = Timestamp.valueOf(ldt) ; 
									pt.setTimestamp(i, ts);
									if (type(Integer.parseInt(arr.get("accesstype")))==0) {
										if (i != count) {
											query += arr.get("mysqlfield") + "=" + ts + " and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=" + ts ; 
										}
									}
									else {
										if (i != count) {
											query += arr.get("mysqlfield") + "= \""  + ts + "\" and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=\"" + ts + "\"" ; 
										}
									}
								}
								else if (type(Integer.parseInt(arr.get("mysqltype")))==3) {
									
									Calendar utcCalendar = Calendar.getInstance();
									LocalDateTime ldt  = row.getLocalDateTime(arr.get("accessfield")) ;
									Timestamp ts = Timestamp.valueOf(ldt) ;
									//System.out.println(ts) ; 
									//System.out.println(ldt) ; 
									pt.setTimestamp(i, ts, utcCalendar) ; 
									if (type(Integer.parseInt(arr.get("accesstype")))==0) {
										if (i != count) {
											query += arr.get("mysqlfield") + "=" + ts + " and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=" + ts ; 
										}
									}
									else {
										if (i != count) {
											query += arr.get("mysqlfield") + "= \'"  + ts + "\' and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=\'" + ts+ "\'" ; 
										}
									}
								}else if (type(Integer.parseInt(arr.get("mysqltype")))==4) {
									Calendar utcCalendar = Calendar.getInstance();
									LocalDateTime ldt  = row.getLocalDateTime(arr.get("accessfield")) ;
									Timestamp ts = Timestamp.valueOf(ldt) ; 
									Time time = Time.valueOf(ldt.toLocalTime());
									pt.setTime(i, time, utcCalendar) ;  
									if (type(Integer.parseInt(arr.get("accesstype")))==0) {
										if (i != count) {
											query += arr.get("mysqlfield") + "=" + time + " and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=" +time ; 
										}
									}
									else {
										if (i != count) {
											query += arr.get("mysqlfield") + "= \'"  +time + "\' and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=\'" + time + "\'" ; 
										}
									}
								}else if (type(Integer.parseInt(arr.get("mysqltype")))==5) {
									
									if (type(Integer.parseInt(arr.get("accesstype")))==0) {
										pt.setDouble(i, row.getInt(arr.get("accessfield")));
										if (i != count) {
											query += arr.get("mysqlfield") + "=" + row.getInt(arr.get("accessfield")) + " and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=" + row.getInt(arr.get("accessfield")) ; 
										}
									}
									else if (type(Integer.parseInt(arr.get("accesstype")))==5) {
										pt.setDouble(i,row.getDouble(arr.get("accessfield")).intValue());
										if (i != count) {
											query += arr.get("mysqlfield") + "=" + row.getDouble(arr.get("accessfield")).intValue() + " and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=" + row.getDouble(arr.get("accessfield")).intValue() ; 
										}
									}
									else {
										pt.setDouble(i,Double.parseDouble(row.getString(arr.get("accessfield"))));
										if (i != count) {
											query += arr.get("mysqlfield") + "= \""  + row.getString(arr.get("accessfield")) + "\" and " ; 
										}
										else {
											query += arr.get("mysqlfield") + "=\"" + row.getString(arr.get("accessfield")) + "\"" ; 
										}
									}
									
								}
								
								
								
								
							}
							//System.out.println(query) ; 
							ResultSet rs3 = st.executeQuery(query + ";");
							if (rs3.next()) {
							}
							else {
								pt.execute() ; 
							}
							
							
							
							
							
							
						}
					}
					
					
					
					
				}
				
				//System.out.println(list) ; 
				return "success.jsp" ;
			  
			  }catch(SQLException e) {
				  e.printStackTrace(); 
			  }catch(IOException e) {
				  e.printStackTrace();
			  }
			  
			  return "failure.jsp" ;
		  }
	  
	  public ArrayList<HashMap<String, Object>> getMapping(int a){
		  if (a == 0) {//mysqlsqlserver
			  try {
			      DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ; 
				  DriverManagerDataSource sqlserverDatasource = service.getsqlserverdatasource() ; 
				  PropertiesConfiguration properties = service.getproperties() ; 
				  //get the mapping
				  String mapsummary = properties.getString("spring.map.mysqlsqlserver") ;
					
					String [] first = mapsummary.split("/")  ;
					ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>() ; 
					Statement st = mysqlDatasource.getConnection().createStatement() ; 
				    Statement st2 = sqlserverDatasource.getConnection().createStatement() ;
					
					for (String str : first) {
						String[] str1 = str.split(":") ;
						HashMap<String, Object> mapelement = new HashMap<String, Object>() ;
						ArrayList<HashMap<String, String>> fieldsarr = new ArrayList<HashMap<String, String>>() ; 
						
						String[] fi= str1[1].split("-") ;
						
						for (String str2 : fi) {
							HashMap<String, String> fields = new HashMap<String, String>() ; 
							String[] fi2 = str2.split(";") ; 
							if(!fi2[1].equals("No Mapping")) {
								fields.put("mysqlfield", fi2[0]) ; 
								fields.put("sqlserverfield", fi2[1]) ; 
								fields.put(fi2[0], fi2[1]) ;
								//ResultSet columns = metaData.getColumns(properties.getString("spring.mysqldatasource.db"), null, str1[0].split("\\*")[0], fi2[0]);
								//ResultSet columns2 = metaData2.getColumns(properties.getString("spring.sqlserverdatasource.db"), null, str1[0].split("\\*")[1], fi2[1]);
								ResultSet columns = st.executeQuery("SELECT " + fi2[0] + " FROM " + (str1[0].split("\\*"))[0] + ";") ;
								ResultSet columns2 = st2.executeQuery("SELECT " + fi2[1] + " FROM " + (str1[0].split("\\*"))[1] + ";");
								ResultSetMetaData col1 = columns.getMetaData() ; 
								
								fields.put("mysqltype", Integer.toString(col1.getColumnType(1))) ;
								//int sqlservertype = columns2.getType() ; 
								ResultSetMetaData col2 = columns2.getMetaData() ; 
								fields.put("sqlservertype", Integer.toString(col2.getColumnType(1))) ; 
								
								fieldsarr.add(fields) ; 
								
							}
						}
						
						mapelement.put("mysqltable", (str1[0].split("\\*"))[0]) ;
						mapelement.put("sqlservertable", (str1[0].split("\\*"))[1]) ;
						mapelement.put("fields", fieldsarr) ; 
						
						
						
						list.add(mapelement) ; 
						
					}
					return list ; 
			  }catch(SQLException e) {
				  e.printStackTrace();
			  }
		  }else if (a==1) {
			  try {
			      DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ; 
				  DriverManagerDataSource mysql2Datasource = service.getmysql2datasource() ; 
				  PropertiesConfiguration properties = service.getproperties() ; 
				  //get the mapping
				  String mapsummary = properties.getString("spring.map.mysqlmysql2") ;
					
					String [] first = mapsummary.split("/")  ;
					ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>() ; 
					Statement st = mysqlDatasource.getConnection().createStatement() ; 
				    Statement st2 = mysql2Datasource.getConnection().createStatement() ;
					
					for (String str : first) {
						String[] str1 = str.split(":") ;
						HashMap<String, Object> mapelement = new HashMap<String, Object>() ;
						ArrayList<HashMap<String, String>> fieldsarr = new ArrayList<HashMap<String, String>>() ; 
						
						String[] fi= str1[1].split("-") ;
						
						for (String str2 : fi) {
							HashMap<String, String> fields = new HashMap<String, String>() ; 
							String[] fi2 = str2.split(";") ; 
							if(!fi2[1].equals("No Mapping")) {
								fields.put("mysqlfield", fi2[0]) ; 
								fields.put("mysql2field", fi2[1]) ; 
								fields.put(fi2[0], fi2[1]) ;
								//ResultSet columns = metaData.getColumns(properties.getString("spring.mysqldatasource.db"), null, str1[0].split("\\*")[0], fi2[0]);
								//ResultSet columns2 = metaData2.getColumns(properties.getString("spring.mysql2datasource.db"), null, str1[0].split("\\*")[1], fi2[1]);
								ResultSet columns = st.executeQuery("SELECT " + fi2[0] + " FROM " + (str1[0].split("\\*"))[0] + ";") ;
								ResultSet columns2 = st2.executeQuery("SELECT " + fi2[1] + " FROM " + (str1[0].split("\\*"))[1] + ";");
								ResultSetMetaData col1 = columns.getMetaData() ; 
								
								fields.put("mysqltype", Integer.toString(col1.getColumnType(1))) ;
								//int mysql2type = columns2.getType() ; 
								ResultSetMetaData col2 = columns2.getMetaData() ; 
								fields.put("mysql2type", Integer.toString(col2.getColumnType(1))) ; 
								
								fieldsarr.add(fields) ; 
								
							}
						}
						
						mapelement.put("mysqltable", (str1[0].split("\\*"))[0]) ;
						mapelement.put("mysql2table", (str1[0].split("\\*"))[1]) ;
						mapelement.put("fields", fieldsarr) ; 
						
						
						
						list.add(mapelement) ; 
						
					}
					return list ; 
			  }catch(SQLException e) {
				  e.printStackTrace();
			  }
			  
		  }
		  else if(a==2) {//mysqlaccess
			  try {
			      DriverManagerDataSource mysqlDatasource = service.getmysqldatasource() ; 
				  PropertiesConfiguration properties = service.getproperties() ; 
				  //get the mapping
				  String mapsummary = properties.getString("spring.map.mysqlaccess") ;
				  Database db = service.getAccessDatabase() ; 
				  
				  
					
					String [] first = mapsummary.split("/")  ;
					ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>() ; 
					Statement st = mysqlDatasource.getConnection().createStatement() ; 
					
					for (String str : first) {
						String[] str1 = str.split(":") ;
						HashMap<String, Object> mapelement = new HashMap<String, Object>() ;
						ArrayList<HashMap<String, String>> fieldsarr = new ArrayList<HashMap<String, String>>() ; 
						
						String[] fi= str1[1].split("-") ;
						
						for (String str2 : fi) {
							HashMap<String, String> fields = new HashMap<String, String>() ; 
							String[] fi2 = str2.split(";") ; 
							if(!fi2[1].equals("No Mapping")) {
								fields.put("mysqlfield", fi2[0]) ; 
								fields.put("accessfield", fi2[1]) ; 
								fields.put(fi2[0], fi2[1]) ;
								//ResultSet columns = metaData.getColumns(properties.getString("spring.mysqldatasource.db"), null, str1[0].split("\\*")[0], fi2[0]);
								//ResultSet columns2 = metaData2.getColumns(properties.getString("spring.sqlserverdatasource.db"), null, str1[0].split("\\*")[1], fi2[1]);
								ResultSet columns = st.executeQuery("SELECT " + fi2[0] + " FROM " + (str1[0].split("\\*"))[0] + ";") ;
								Column col = db.getTable(str1[0].split("\\*")[1]).getColumn(fi2[1]) ; 
								ResultSetMetaData col1 = columns.getMetaData() ; 
								
								fields.put("mysqltype", Integer.toString(col1.getColumnType(1))) ;
								//int sqlservertype = columns2.getType() ; 

								fields.put("accesstype", Integer.toString(col.getSQLType())) ; 
								
								fieldsarr.add(fields) ; 
								
							}
						}
						
						mapelement.put("mysqltable", (str1[0].split("\\*"))[0]) ;
						mapelement.put("accesstable", (str1[0].split("\\*"))[1]) ;
						mapelement.put("fields", fieldsarr) ; 
						
						
						
						list.add(mapelement) ; 
						
					}
					return list ; 
			  }catch(SQLException e) {
				  e.printStackTrace();
			  }catch(IOException e) {
				  e.printStackTrace();
			  }
		  }
		  else if(a==3) {//sqlserveraccess
			  return null ; 
		  }
		  else {
			  return null ; 
		  }
		  
		  return null ; 
		  
	  }
	  
	  
	  
	  
	  
	  public int compatibletypes(int a, int b) {
		  int[][] compatibles ={{4, -5, -7, 3, 8, 6, 4, -6, 5, 2},{1,12,-15,-9, -16}, {91, 92, 93, 2014}} ;  
		  if (ArrayUtils.contains(compatibles[0], a) && ArrayUtils.contains(compatibles[0], b)) {
			  return 0 ; 
		  }else if (ArrayUtils.contains(compatibles[1], a) && ArrayUtils.contains(compatibles[1], b)){
			  return 1 ; 
		  }
		  else if (ArrayUtils.contains(compatibles[2], a) && ArrayUtils.contains(compatibles[2], b)) {
			  return 2 ; 
		  }
		  else {
			  return -1 ; 
		  }
		  
		
	  }
	  
	  public int type(int a) {
		  int[][] compatibles ={{4, -5, 5, 4, -6},{1,12,-15,-9, -16}, {91}, {93, 2014}, {92}, {8, 7,3, 6, 2}} ;  
		  if (ArrayUtils.contains(compatibles[0], a)) {
			  return 0 ; 
		  }else if (ArrayUtils.contains(compatibles[1], a)){
			  return 1 ; 
		  }
		  else if (ArrayUtils.contains(compatibles[2], a)) {
			  return 2 ; 
		  }
		  else if (ArrayUtils.contains(compatibles[3], a)) {
			  return 3 ; 
		  }else if (ArrayUtils.contains(compatibles[4], a)) {
			  return 4 ; 
		  }else if (ArrayUtils.contains(compatibles[5], a)) {
			  return 5 ; 
		  }
		  else {
			  return -1 ; 
		  }
	  }
	  /*Array: 2003Big int: -5Binary: -2Bit: -7Blob: 2004Boolean: 16Char: 1Clob: 2005Date: 91Datalink70Decimal: 3Distinct: 2001Double: 8Float: 6Integer: 4JavaObject: 2000Long var char: -16Nchar: -15
NClob: 2011Varchar: 12VarBinary: -3Tiny int: -6Time stamt with time zone: 2014Timestamp: 93Time: 92Struct: 2002SqlXml: 2009Smallint: 5Rowid: -8Refcursor: 2012Ref: 2006
Real: 7Nvarchar: -9Numeric: 2Null: 0Smallint: */
	  
	 /// @GetMapping(path = "/injectionconf")
	////  public String Injectionconf() {
	///	  return "injectionconf.jsp" ; 
	///  }
	  
	  @PostMapping(path = "/injectionconfsave")
	  public String Injectionconfsave(@RequestParam(value = "mysqlmysql2", required = false) String mysqlmysql2, @RequestParam(value = "mysql2mysql", required = false) String mysql2mysql, @RequestParam(value = "sqlservermysql", required = false)String sqlservermysql, @RequestParam(value = "mysqlsqlserver", required = false) String mysqlsqlserver, @RequestParam(value = "accessmysql", required = false)String accessmysql ){
		  PropertiesConfiguration properties = service.getproperties();

		  if (mysqlmysql2!=null) {
			  properties.setProperty("spring.timer.mysqlmysql2", 1);
		  }else {
			  properties.setProperty("spring.timer.mysqlmysql2", 0);
		  }
		  if (mysql2mysql!=null) {
			  properties.setProperty("spring.timer.mysql2mysql", 1);

			  
		  }else {
			  properties.setProperty("spring.timer.mysql2mysql", 0);
		  }
		  
		  if (accessmysql!=null) {
			  properties.setProperty("spring.timer.accessmysql", 1);
			  
		  }else {
			  properties.setProperty("spring.timer.accessmysql", 0);
		  }
		  if (mysqlsqlserver!=null) {
			  properties.setProperty("spring.timer.mysqlsqlserver", 1);
			  
		  }else {
			  properties.setProperty("spring.timer.mysqlsqlserver", 0);
		  }
		  if (sqlservermysql!=null) {
			  properties.setProperty("spring.timer.sqlservermysql", 1);
			  
		  }else {
			  properties.setProperty("spring.timer.sqlservermysql", 0);
		  }
		  
		  //properties.setProperty("spring.timer.seconds", seconds);
		  
		  try {
			properties.save();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
		  return "page.jsp" ; 
	  }
}
