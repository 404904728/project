package org.cq.controller;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.cq.SysConstant;
import org.cq.annotion.ActionAnn;
import org.cq.interceptor.AutoInjectInterceptor;
import org.cq.model.User;
import org.cq.service.FileService;
import org.cq.util.AjaxMsg;
import org.cq.util.Encrypt;
import org.cq.validator.LoginValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * User: hejian Date: 14-12-2 Time: 下午1:05 登录控制器
 */
public class LoginController extends Controller {

	@ActionAnn(toLogon=false)
	public void index() {
		render(SysConstant.LOGON_URL);
	}

	/**
	 * 登录
	 */
	@Before(LoginValidator.class)
	@ActionAnn(toLogon = false)
	public void on() {
		User db = User.dao.findByNo(getPara("no"));
		if (db == null) {
			redirect("/");
			return;
		}
		if (db.getStr("pwd_f").equals(Encrypt.md5(getPara("pwd")))) {
			User.dao.session(getSession(), db);
			redirect("/home");
		} else {
			redirect("/");
		}
	}

	/**
	 * 退出系统
	 */
	public void logout() {
		getSession().invalidate();
		//render(SysConstant.LOGON_URL);
		redirect("/login");
	}
	
	//@Before(AutoInjectInterceptor.class)
	@Before(Tx.class)
	public void save(){
		AjaxMsg am = new AjaxMsg(AjaxMsg.STATUS_SUCCESS, "保存成功");
		Map<String, String[]> paraMap = getParaMap();
		if(!new User().set("no_f", paraMap.get("name")[0]).set("name_f", paraMap.get("name")[0])
				.set("sex_f", paraMap.get("gender")[0]).set("pwd_f",Encrypt.md5(paraMap.get("password")[0])).save()){
			am.setStatus(AjaxMsg.STATUS_ERROR);
			am.setMsg("保存失败");
		}
		renderJson(am);
	}
	
	public void saveAttach(){
		renderJson(new FileService().file(getFile("avatar"),1L));
	}
}
