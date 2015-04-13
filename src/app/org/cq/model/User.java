package org.cq.model;

import javax.servlet.http.HttpSession;

import org.cq.annotion.Pojo;

import com.jfinal.plugin.activerecord.Model;

@Pojo(table = true)
public class User extends Model<User>{
	/**
	 * 
	 */
    public static final User dao = new User();
	
    private Long id;
	
	private String name;
	
	private String code;
	
	private String no;
	
	private String pwd;
	
	private int sex = 0;
	
	private String tel;
	
	private Long dept;
	
	/**
	 * 根据帐号查询用户
	 * 
	 * @param no
	 * @return
	 */
	public User findByNo(String no) {
		return User.dao
				.findFirst(
						"select u.id_f,u.name_f,u.pwd_f,d.id_f as did_f,d.name_f as dname_f from user_t u left join dept_t d on u.dept_f=d.id_f where u.no_f=?",
						no);
	}

	/**
	 * 把用户保存到session中
	 * 
	 * @param session
	 * @param session
	 */
	public void session(HttpSession session, User user) {
		SessionModel sm = new SessionModel();
		sm.setUserId(user.getLong("id_f"));
		sm.setUserName(user.getStr("name_f"));
		sm.setDeptId(user.getLong("did_f"));
		sm.setDeptName(user.getStr("dname_f"));
		session.setAttribute("sessionModel", sm);
	}

	public boolean hasUser() {
		return null != dao.findFirst("select 1 from user_t") ? true : false;
	}

	public SessionModel sessionModel(HttpSession session) {
		return (SessionModel) session.getAttribute("sessionModel");
	}
}
