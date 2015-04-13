package org.cq.plugin;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cq.annotion.Pojo;

import com.jfinal.kit.PathKit;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.activerecord.Model;
import com.mysql.jdbc.Connection;

public class TableBindPlugin extends ActiveRecordPlugin{
	 protected final Logger log = Logger.getLogger(getClass());

	    @SuppressWarnings("rawtypes")
	    private List<Class<? extends Model>> excludeClasses = new ArrayList<Class<? extends Model>>();
	    private List<String> includeJars =new ArrayList<String>();
	    private boolean autoScan = true;
	    private boolean includeAllJarsInLib = false;
	    private List<String> scanPackages = new ArrayList<String>();
	    private String classpath = PathKit.getRootClassPath();
	    private String libDir = PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator + "lib";
	    private String url = null;
	    private String tableModel = null;
	    private String schemaName = null;
	    public TableBindPlugin(IDataSourceProvider dataSourceProvider) {
	        this(DbKit.MAIN_CONFIG_NAME, dataSourceProvider);
	    }

	    public TableBindPlugin(String configName, IDataSourceProvider dataSourceProvider) {
	    	super(configName, dataSourceProvider);
	    }

	    public TableBindPlugin(IDataSourceProvider c3p0Plugin,String url, String tableModel,
				String schemaName) {
	    	   this(DbKit.MAIN_CONFIG_NAME, c3p0Plugin);
	    	   this.url = url;
	    	   this.tableModel = tableModel;
	    	   this.schemaName = schemaName;
		}

		/**
	     * 添加需要扫描的包，默认为扫描所有包
	     *
	     * @param packages
	     * @return
	     */
	    public TableBindPlugin addScanPackages(String... packages) {
	        for (String pkg : packages) {
	            scanPackages.add(pkg);
	        }
	        return this;
	    }

	    @SuppressWarnings({"rawtypes", "unchecked"})
	    public TableBindPlugin addExcludeClasses(Class<? extends Model>... clazzes) {
	        for (Class<? extends Model> clazz : clazzes) {
	            excludeClasses.add(clazz);
	        }
	        return this;
	    }

	    @SuppressWarnings("rawtypes")
	    public TableBindPlugin addExcludeClasses(List<Class<? extends Model>> clazzes) {
	        if (clazzes != null) {
	            excludeClasses.addAll(clazzes);
	        }
	        return this;
	    }

	    public TableBindPlugin addJars(List<String> jars) {
	        if (jars != null) {
	            includeJars.addAll(jars);
	        }
	        return this;
	    }

	    public TableBindPlugin addJars(String... jars) {
	        if (jars != null) {
	            for (String jar : jars) {
	                includeJars.add(jar);
	            }
	        }
	        return this;
	    }

	    @SuppressWarnings({"unchecked", "rawtypes"})
	    @Override
	    public boolean start() {
	    	if (!autoScan) {
	    		return super.start();
	    	}
	        List<Class<? extends Model>> modelClasses = ClassSearcher.of(Model.class).libDir(libDir).classpath(classpath)
	                .scanPackages(scanPackages).injars(includeJars).includeAllJarsInLib(includeAllJarsInLib).search();
	        Pojo pojo;
	        Connection currentDbConnection = getCurrentDbConnection();
	        for (Class modelClass : modelClasses) {
	            if (excludeClasses.contains(modelClass)) {
	                continue;
	            }
	            pojo = (Pojo) modelClass.getAnnotation(Pojo.class);
	            String tableName = modelClass.getSimpleName().toLowerCase();
	            if (pojo == null) {
	            } else {
	                if(pojo.table()){
	                	 try {
	                         GenerateTable.generateTableByClass(modelClass, currentDbConnection,
	                                 tableModel, schemaName);
	                     } catch (Exception e) {
	                         System.out.println("generate table exception..........");
	                     }
	                }
	                this.addMapping(tableName+"_t","id_f",modelClass);
	            }
	        }
	        
	        if (null != currentDbConnection) {
	            try {
	            	currentDbConnection.close();
	            } catch (SQLException e) {
	            }
	        }
	        
	        return super.start();
	    }
	    
	    /**
	     * 并未关闭
	     *
	     * @return
	     */
	    private Connection getCurrentDbConnection() {
	    	 Connection conn = null;
	         try {
	             Class.forName("com.mysql.jdbc.Driver");
	             conn = (Connection) DriverManager.getConnection(url);
	             return conn;
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	         return conn;
	    }
	    
	    @Override
	    public boolean stop() {
	        return super.stop();
	    }

	    public TableBindPlugin autoScan(boolean autoScan) {
	        this.autoScan = autoScan;
	        return this;
	    }

	    public TableBindPlugin classpath(String classpath) {
	        this.classpath = classpath;
	        return this;
	    }

	    public TableBindPlugin libDir(String libDir) {
	        this.libDir = libDir;
	        return this;
	    }
	    public TableBindPlugin includeAllJarsInLib(boolean includeAllJarsInLib) {
	        this.includeAllJarsInLib = includeAllJarsInLib;
	        return this;
	    }

		public String getTableModel() {
			return tableModel;
		}

		public void setTableModel(String tableModel) {
			this.tableModel = tableModel;
		}

		public String getSchemaName() {
			return schemaName;
		}

		public void setSchemaName(String schemaName) {
			this.schemaName = schemaName;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
}
