package org.cq.plugin;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Logger;

public class BindRoutes extends Routes { 


    private boolean autoScan = true; 


    private List<Class<? extends Controller>> excludeClasses = new ArrayList<Class<? extends Controller>>(); 


    private boolean includeAllJarsInLib; 


    private List<String> includeJars = new ArrayList<String>(); 


    private List<String> scanPackages = new ArrayList<String>();


    private String suffix = "Controller"; 


    public BindRoutes autoScan(boolean autoScan) { 
        this.autoScan = autoScan; 
        return this; 
    } 


    public BindRoutes addExcludeClasses(Class<? extends Controller>... clazzes) { 
        if (clazzes != null) { 
            for (Class<? extends Controller> clazz : clazzes) { 
                excludeClasses.add(clazz); 
            } 
        } 
        return this; 
    } 


    public BindRoutes addExcludeClasses(List<Class<? extends Controller>> clazzes) { 
        excludeClasses.addAll(clazzes); 
        return this; 
    } 
    
	/**
     * 添加需要扫描的包，默认为扫描所有包
     *
     * @param packages
     * @return
     */
    public BindRoutes addScanPackages(String... packages) {
        for (String pkg : packages) {
            scanPackages.add(pkg);
        }
        return this;
    }


    public BindRoutes addJars(String... jars) { 
        if (jars != null) { 
            for (String jar : jars) { 
                includeJars.add(jar); 
            } 
        } 
        return this; 
    } 


    @Override 
    @SuppressWarnings({ "rawtypes", "unchecked" }) 
    public void config() { 
    	 if (!autoScan) { 
             return; 
         } 
        List<Class<? extends Controller>> controllerClasses = ClassSearcher.of(Controller.class).scanPackages(scanPackages)
                .includeAllJarsInLib(includeAllJarsInLib).injars(includeJars).search(); 
        String key = null;
        for (Class controller : controllerClasses) { 
            if (excludeClasses.contains(controller)) { 
                continue; 
            } 
            key = controllerKey(controller);
            if(StrKit.isBlank(key)){
            	continue;
            }
            this.add(key, controller); 
        } 
    } 


    private String controllerKey(Class<Controller> clazz) { 
      if(clazz.getSimpleName().endsWith(suffix)){
          String controllerKey = "/admin/" + clazz.getSimpleName().toLowerCase(); 
          controllerKey = controllerKey.substring(0, controllerKey.indexOf(suffix.toLowerCase())); 
          return controllerKey;  
      }
      return null;

     } 
 
     public BindRoutes includeAllJarsInLib(boolean includeAllJarsInLib) { 
         this.includeAllJarsInLib = includeAllJarsInLib; 
         return this; 
     } 
 

     public BindRoutes suffix(String suffix) { 
         this.suffix = suffix; 
         return this; 
     } 
}
