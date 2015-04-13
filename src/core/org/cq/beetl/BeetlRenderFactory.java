package org.cq.beetl;

import java.io.IOException;
import java.util.Properties;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.jfinal.BeetlRender;

import com.jfinal.render.IMainRenderFactory;
import com.jfinal.render.JspRender;
import com.jfinal.render.Render;

public class BeetlRenderFactory implements IMainRenderFactory {

	public static String viewExtension = ".html";
	public static GroupTemplate groupTemplate = null;

	public BeetlRenderFactory() {
		if (groupTemplate != null) {
			groupTemplate.close();
		}
		try {
			Configuration cfg = null;
			Properties ps = new Properties();
			ps.load(Configuration.class
					.getResourceAsStream("/org/cq/beetl/beetl.properties"));
			cfg = new Configuration(ps);
			WebAppResourceLoader resourceLoader = new WebAppResourceLoader();
			groupTemplate = new GroupTemplate(resourceLoader, cfg);
			//Map<String, Object> shared = new HashMap<String, Object>();
			//shared.put("ctx", "hmq");
			//groupTemplate.setSharedVars(shared);
		} catch (IOException e) {
			throw new RuntimeException("加载GroupTemplate失败", e);
		}

	}

	public Render getRender(String view) {
		if (view.substring(view.lastIndexOf(".")).equals(".html")) {
			return new BeetlRender(groupTemplate, view);
		} else {
			return new JspRender(view);
		}

	}
	
	public String getViewExtension() {
		return viewExtension;
	}

}
