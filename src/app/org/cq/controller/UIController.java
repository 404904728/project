package org.cq.controller;

import com.jfinal.core.Controller;

public class UIController extends Controller {

	public void treeview() {

		render("/admin/ui_elements/treeview.html");
	}

	public void buttons_icons() {

		render("/admin/ui_elements/buttons_icons.html");
	}

	public void elements() {

		render("/admin/ui_elements/elements.html");
	}

	public void content_slider() {

		render("/admin/ui_elements/contentslider.html");
	}

	public void typography() {

		render("/admin/ui_elements/typography.html");
	}

	public void jquery_ui() {

		render("/admin/ui_elements/jquery_ui.html");
	}

	public void nestable_list() {

		render("/admin/ui_elements/nestable_list.html");
	}

	public void two_menu() {

		render("/admin/ui_elements/two_menu.html");
	}

	public void two_menu_1() {
		render("/admin/ui_elements/two_menu_1.html");
	}

	public void two_menu_2() {
		render("/admin/ui_elements/two_menu_2.html");
	}

	public void default_mobile_menu() {

		render("/admin/ui_elements/default_mobile_menu.html");
	}

	public void mobile_menu_2() {

		render("/admin/ui_elements/mobile_menu_2.html");
	}

	public void mobile_menu_3() {

		render("/admin/ui_elements/mobile_menu_3.html");
	}
}
