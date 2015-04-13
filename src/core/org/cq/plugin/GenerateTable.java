package org.cq.plugin;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.cq.annotion.Column;

import com.mysql.jdbc.Connection;

/**
 * 
 * @author Administrator
 *
 */
public class GenerateTable {
	
	private static final String tabModeUpdate = "update";
	private static final String tabModeCreate = "create";
	private static final String tabModeNone = "none";
	
	public static boolean generateTableByClass(Class<?> cls,Connection conn,String tabMode,String schemaName) throws Exception{
		if(tabModeNone.equals(tabMode)){
			return false;
		}
		if(null == cls){
			return false;
		}
		String sql = "";
		StringBuffer stringBuffer = null;
		String tableName = cls.getSimpleName().toLowerCase()+"_t";
		Object objct = cls.newInstance();
		Object deault = null;
		int defaultLen = 0;
		if(tabModeCreate.equals(tabMode)){
			execute("DROP TABLE IF EXISTS  " + tableName, conn);
			stringBuffer = new StringBuffer("create table ");
		}else if(tabModeUpdate.equals(tabMode)){
			if("1".equals(search("select 1 from information_schema.TABLES where TABLE_SCHEMA = '"+schemaName+"' and TABLE_NAME = '"+tableName+"'",conn))){
					return updateTable(cls,conn,tableName,schemaName);
			}else{
				stringBuffer = new StringBuffer("create table ");
			}
		}else{
			return false;
		}
		stringBuffer.append(tableName +" (");
		Field[]  fs = cls.getDeclaredFields();
		if(fs.length < 1){
			 return false;
		}
		for (Field field : fs) {
			String fname = field.getName().toLowerCase();
			if(fname.equals("id")){
				stringBuffer.append(" id_f BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT, ");
			}else{
				field.setAccessible(true);
				deault = field.get(objct);
				String type = field.getType().getSimpleName();
				switch(type){
					case "int":
						stringBuffer.append(fname + "_f INT(20) " + (null == deault ? "NOT NULL " : "default  "+ deault));
						stringBuffer.append(",");
						break;
					case "Integer":
						stringBuffer.append(fname + "_f INT(20) DEFAULT " + (null == deault ? "NULL " : deault));
						stringBuffer.append(",");
						break;
					case "long":
						stringBuffer.append(fname + "_f BIGINT(20) " + (null == deault ? "NOT NULL " : "default  "+deault));
						stringBuffer.append(",");
						break;
					case "Long":
						stringBuffer.append(fname + "_f BIGINT(20) DEFAULT " + (null == deault ? "NULL " : deault));
						stringBuffer.append(",");
						break;
					case "Boolean":
						stringBuffer.append(fname + "_f BIT(1) DEFAULT " + (null == deault ? "NULL " : ("true".equals(String.valueOf(deault).toLowerCase()) ? 1 : 0)));
						stringBuffer.append(",");
						break;
					case "boolean":
						stringBuffer.append(fname + "_f BIT(1) " + (null == deault ? "NOT NULL " : "default  "+("true".equals(String.valueOf(deault).toLowerCase()) ? 1 : 0)));
						stringBuffer.append(",");
						break;
					case "String":
						Column col = field.getAnnotation(Column.class); //65001
						if(null != col){
							defaultLen = col.len();
							if(defaultLen > 65000){
								stringBuffer.append(fname
										+ "_f MEDIUMTEXT DEFAULT " + (null == deault ? "NULL " : "'"+ deault+"'"));
								stringBuffer.append(",");
								break;
							}
						}else{
							defaultLen = 255;
						}
						stringBuffer.append(fname
								+ "_f VARCHAR("+defaultLen+") DEFAULT " + (null == deault ? "NULL " : "'"+ deault+"'"));
						stringBuffer.append(",");
						break;
					case "Date":
						stringBuffer.append(fname + "_f DATETIME DEFAULT NULL ");
						stringBuffer.append(",");
						break;
					default:;
			}

		  }
		}
		if(stringBuffer.lastIndexOf(",") > 0){
			sql = stringBuffer.substring(0, stringBuffer.lastIndexOf(","));
		}else{
			return false;
		}
		sql += " ) engine=innodb default charset=utf8;";
		System.out.println(sql);
		execute(sql,conn);
		return true;
	}
	
	public static boolean updateTable(Class<?> cls,Connection conn,String tableName,String schemaName) throws Exception{
		Field[]  fs = cls.getDeclaredFields();
		if(fs.length < 1){
			 return false;
		}
		Object objct = cls.newInstance();
		Object deault = null;
		int defaultLen = 0;
		StringBuffer stringBuffer = new StringBuffer();
		for (Field field : fs) {
			String fname = field.getName().toLowerCase()+"_f";
			if("1".equals(search("select 1 from information_schema.COLUMNS where column_name = '"+fname+"' AND TABLE_NAME = '"
					+tableName+"' and TABLE_SCHEMA = '"+schemaName+"'", conn))){
			}else{
				if(fname.equals("id")){
					stringBuffer.append(" alter table "+tableName+" add id_f BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT; ");
					stringBuffer = new StringBuffer();
				}else{
					field.setAccessible(true);
					deault = field.get(objct);
					String type = field.getType().getSimpleName();
					switch(type){
						case "int":
							stringBuffer.append(" alter table "+tableName+" add "+ fname + " INT(20) " + (null == deault ? "NOT NULL " : "default  "+ deault));
							stringBuffer.append(";");
							break;
						case "Integer":
							stringBuffer.append(" alter table "+tableName+" add "+fname + " INT(20) DEFAULT " + (null == deault ? "NULL " : deault));
							stringBuffer.append(";");
							break;
						case "long":
							stringBuffer.append(" alter table "+tableName+" add "+fname + " BIGINT(20) " + (null == deault ? "NOT NULL " : "default  "+deault));
							stringBuffer.append(";");
							break;
						case "Long":
							stringBuffer.append(" alter table "+tableName+" add "+fname + " BIGINT(20) DEFAULT " + (null == deault ? "NULL " : deault));
							stringBuffer.append(";");
							break;
						case "Boolean":
							stringBuffer.append(" alter table "+tableName+" add "+fname + " BIT(1) DEFAULT " + (null == deault ? "NULL " : ("true".equals(String.valueOf(deault).toLowerCase()) ? 1 : 0)));
							stringBuffer.append(";");
							break;
						case "boolean":
							stringBuffer.append(" alter table "+tableName+" add "+fname + " BIT(1) " + (null == deault ? "NOT NULL " : "default  "+("true".equals(String.valueOf(deault).toLowerCase()) ? 1 : 0)));
							stringBuffer.append(";");
							break;
						case "String":
							Column col = field.getAnnotation(Column.class); //65001
							if(null != col){
								defaultLen = col.len();
								if(defaultLen > 65000){
									stringBuffer.append(fname
											+ "_f MEDIUMTEXT DEFAULT " + (null == deault ? "NULL " : "'"+ deault+"'"));
									stringBuffer.append(",");
									break;
								}
							}else{
								defaultLen = 255;
							}
							stringBuffer.append(fname
									+ "_f VARCHAR("+defaultLen+") DEFAULT " + (null == deault ? "NULL " : "'"+ deault+"'"));
							stringBuffer.append(",");
							break;
						case "Date":
							stringBuffer.append(" alter table "+tableName+" add "+fname + " DATETIME DEFAULT NULL ");
							stringBuffer.append(";");
							break;
						default:;
					}
					if(stringBuffer.length() > 10){
						execute(stringBuffer.toString(),conn);
						stringBuffer = new StringBuffer();
					}
			 }
			}
		}
		return true;
	}
	
	
	public static Object search(String sql,Connection conn){
		 Statement stmt = null;
		try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                  return rs.getString(1);
           }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally{
        	try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        return null;
	}
	
	
	public static int[] executeMutiple(String[] sql,Connection conn){
		 Statement stmt = null;
		try{
          stmt = conn.createStatement();
          for(String s : sql){
        	  stmt.addBatch(s); 
          }
          return stmt.executeBatch();
      }  catch (Exception e) {
          e.printStackTrace();
      } finally{
      		try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
      }
      return null;
	}
	
	public static int execute(String sql,Connection conn){
		 Statement stmt = null;
		try{
           stmt = conn.createStatement();
           return stmt.executeUpdate(sql);
       }  catch (Exception e) {
           e.printStackTrace();
       } finally{
       		try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
       }
       return -1;
	}
}
