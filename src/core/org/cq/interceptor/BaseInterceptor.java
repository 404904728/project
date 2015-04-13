package org.cq.interceptor;

import org.cq.annotion.ActionAnn;
import org.cq.model.SessionModel;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

/**
 * 登录拦截
 *
 * @author hejian
 *
 */
public class BaseInterceptor implements Interceptor {

    @Override
    public void intercept(ActionInvocation ai) {
        ActionAnn annotation = ai.getController().getClass().getAnnotation(ActionAnn.class);
        if (annotation != null && annotation.toLogon() == false) {
            ai.invoke();
            return;
        }
        ActionAnn actionAnn = ai.getMethod().getAnnotation(ActionAnn.class);
        if (actionAnn == null) {
            if (!logon(ai)) {
                return;
            }
            ai.invoke();
        } else {
            if (!actionAnn.method().equals("")) {
                if (!ai.getController().getRequest().getMethod().toUpperCase()
                        .equals(actionAnn.method().toUpperCase())) {
                    ai.getController().renderHtml("您的请求不合法");
                    return;
                }
            }
            if (actionAnn.toLogon() == true) {
                if (!logon(ai)) {
                    return;
                }
                ai.invoke();
            } else {
                ai.invoke();
            }
        }
    }

    /**
     * 判断是否session登录
     *
     * @param ai
     * @return
     */
    public boolean logon(ActionInvocation ai) {
        SessionModel sessionModel = (SessionModel) ai.getController()
                .getSession().getAttribute("sessionModel");
        if (sessionModel == null) {// 没有登录的访问
            ai.getController().redirect("/login");
        	//ai.getController().render(SysConstant.LOGON_URL);
            return false;
        } else {// 已经登录则进行下去
            return true;
        }
    }
}
