package org.cq.util;

public class AjaxMsg {

    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_ERROR = 1;

    public int status = 0;

    public String msg;

    public Object data;

    public AjaxMsg() {
    }

    public AjaxMsg(int status) {
        super();
        this.status = status;
        this.msg = status == STATUS_SUCCESS ? "操作成功" : "操作失败";
    }

    public AjaxMsg(int status, String msg) {
        super();
        this.status = status;
        this.msg = msg;
    }

    public AjaxMsg(int status, String msg, Object data) {
        super();
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
