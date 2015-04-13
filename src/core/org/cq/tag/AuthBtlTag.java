package org.cq.tag;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.beetl.core.Tag;
import org.cq.SysConstant;

public class AuthBtlTag extends Tag {

	@Override
	public void render() {
		try {
			if (this.args.length == 1) {
				Long permissionId = Long.parseLong(String.valueOf(args[0]));
				// HttpSession session = ((ServletRequestAttributes)
				// RequestContextHolder
				// .getRequestAttributes()).getRequest().getSession();
				HttpServletRequest request = (HttpServletRequest) this.ctx
						.getGlobal("request");
				@SuppressWarnings("unchecked")
				Set<String> setPermission = (Set<String>) request.getSession()
						.getAttribute(SysConstant.SESSION_PERMISSION);
				if (setPermission.contains(permissionId.toString())) {
					this.doBodyRender();
				}
			} else {
				throw new Exception("auth标签函数参数不正确，请检查");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
