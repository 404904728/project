package org.cq.common;

import java.sql.SQLException;

import org.cq.beetl.BeetlRenderFactory;
import org.cq.controller.HomeController;
import org.cq.controller.LoginController;
import org.cq.interceptor.BaseInterceptor;
import org.cq.model.User;
import org.cq.plugin.BindRoutes;
import org.cq.plugin.TableBindPlugin;
import org.cq.service.InitService;
import org.cq.util.LogUtil;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;

public class BaseConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
        loadPropertyFile("/config.txt");
        LogUtil.info("configConstant");
        me.setDevMode(getPropertyToBoolean("devMode", false));
        me.setViewType(ViewType.OTHER);
        me.setMaxPostSize(1024 * 1024 * 100);// 10M
        me.setMainRenderFactory(new BeetlRenderFactory());
	}

	@Override
	public void configHandler(Handlers arg0) {
		arg0.add(new ContextPathHandler("contextPath"));
	}
	
	@Override
	public void configInterceptor(Interceptors arg0) {
		arg0.add(new BaseInterceptor());
	}

	@Override
	public void configPlugin(Plugins arg0) {
		 LogUtil.info("加载C3p0Plugin");
	        C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"),
	                getProperty("user"), getProperty("password").trim());
	        c3p0Plugin.start();
	        String url = getProperty("jdbcUrl") + "&user=" + getProperty("user")
	                + "&password=" + getProperty("password");
	        TableBindPlugin atbp = new TableBindPlugin(c3p0Plugin,url,
                    getProperty("tabMode"), getProperty("tableschema"));
	        atbp.addScanPackages("org.cq.model");
	        arg0.add(atbp);
	        LogUtil.info("加载ActiveRecordPlugin");
	        arg0.add(new EhCachePlugin());
	}

	@Override
	public void configRoute(Routes arg0) {
		arg0.add("/", HomeController.class);
		arg0.add("/login", LoginController.class);
		arg0.add(new BindRoutes().addScanPackages("org.cq.controller")
				.addExcludeClasses(HomeController.class,LoginController.class));
	}
	
	@Override
	public void afterJFinalStart() {
        if (User.dao.hasUser()) {
        } else {
            boolean b = Db.tx(new IAtom() {
                @Override
                public boolean run() throws SQLException {
                    InitService.service.read("initdata-data");
                    LogUtil.info("数据初始化成功...");
                    return true;
                }
            });
        }

		
		super.afterJFinalStart();
	}
	
    public static void main(String[] args) {
    	JFinal.start("web", 8081, "/", 2);
    }
}
