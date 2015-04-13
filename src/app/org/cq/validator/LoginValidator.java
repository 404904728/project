package org.cq.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class LoginValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		// validateString(field, minLen, maxLen, errorKey, errorMessage);
		validateRequired("no", "noMsg", "请输入用户名");
        validateRequired("pwd", "pwdMsg", "请输入密码");

	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara("no");
        c.render("/app/login/login.html");
	}

}
