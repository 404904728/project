package org.cq.controller;

import com.jfinal.core.Controller;

public class HomeController extends Controller {
	
	 public void index() {
	    render("/admin/index/index.html");
	 }
	 
}
