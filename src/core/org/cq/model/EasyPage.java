package org.cq.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;

/**
 * easy数据表格
 * 
 * @param <T>
 * @author 何建
 */
public class EasyPage<T> {

	private int page;

	private List<T> rows;

	private int total;

	private Object footer;

	public List<T> getRows() {
		if (rows == null) {// 防止rows为null时，前台报错
			rows = new ArrayList<T>();
		}
		return rows;
	}

	public EasyPage() {
		super();
	}

	public EasyPage(Page<T> page) {
		super();
		this.rows = page.getList();
		this.total = page.getTotalRow();
	}

	public EasyPage(List<T> rows, int total) {
		super();
		this.rows = rows;
		this.total = total;
	}

	public EasyPage(List<T> rows, int total, Object footer) {
		super();
		this.rows = rows;
		this.total = total;
		this.footer = footer;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Object getFooter() {
		return footer;
	}

	public void setFooter(Object footer) {
		this.footer = footer;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page + 1;
	}
}
