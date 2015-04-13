package org.cq.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MobileUtil {
	 //查询手机号码所属地区
	 public static String getMobileAddress(String mobile) throws Exception
	  {
	   String address = "";
	   try
	   {
	    mobile = mobile.trim();
	    if (mobile.matches("^(13|15|18|17)\\d{9}$") || mobile.matches("^(013|015|018|017)\\d{9}$")) //以13，15，18开头，后面九位全为数字
	   {
	     String url = "http://www.ip138.com:8080/search.asp?action=mobile&mobile=" + mobile;
	     URLConnection connection = (URLConnection) new URL(url).openConnection();
	     connection.setDoOutput(true);
	     InputStream os = connection.getInputStream();
	     Thread.sleep(100);
	     int length = os.available();
	     byte[] buff = new byte[length];
	     os.read(buff);
	     String s = new String(buff, "gbk");
	     int len = s.indexOf("卡号归属地");
	     s = s.substring(len, len+100);
	     len = s.lastIndexOf("</TD>");
	     address = s.substring(0, len);
	     len = address.lastIndexOf(">");
	     address = address.substring(len+1, address.length());
	     address = address.replace("&nbsp;", ",");
	     address = address.replace("d> -->", "");
	     address = address.replace(" -->", "");
	     address = address.replace("-->", "");
	     s = null;
	     buff = null;
	     os.close();
	     connection = null;
	    }
	   }
	   catch(Exception e)
	   {
	    address = "未知";
	    System.out.println("手机所属地查询失败====================");
	   }
	   return address;
	  }
	 
	 public static void main(String[] args) throws Exception {
		System.out.println(getMobileAddress("15820479818"));
	}
}
