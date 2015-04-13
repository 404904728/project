package org.cq.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cq.annotion.Pojo;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

/**
 * User: hejian Date: 14-12-2 Time: 下午12:19 <br>
 */
@Pojo(table = true)
public class Dept extends Model<Dept> {


	public static final Dept dao = new Dept();
	
	private Long id;
	
	private String code;
	
	private String name;
	
	private Long parent;
	
	private String tel;
	
	private int order;
	
	private int type;
	
	/**
	 * 教师端↓<br>
	 * 系统管理模块↓<br>
	 * 学生管理↓<br>
	 * 查询班级组织结构
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findDept() {
		List<Map<String, Object>> listMap = new ArrayList<>();
		// 查询阶段
		List<Record> list = Db
				.find("select id_f,name_f from  dept_t  d where d.type_f=2 order by d.order_f asc");
		for (Record dept : list) {
			Map<String, Object> map = new HashMap<>();
			map.put("id_f", dept.getLong("id_f"));
			map.put("name_f", dept.getStr("name_f"));
			// 年级
			List<Record> listNJ = Db
					.find("select id_f,name_f from  dept_t  d where d.type_f=3 and d.parent_f=? order by d.order_f asc",
							dept.getLong("id_f"));
			for (Record d : listNJ) {
				List<Dept> listBJ = dao
						.find("select id_f,name_f from  dept_t  d where d.type_f=4 and d.parent_f=? order by d.order_f asc",
								d.getLong("id_f"));
				d.set("children", listBJ);
			}

			map.put("children", listNJ);
			listMap.add(map);
		}
		return listMap;
	}

	/**
	 * 
	 * 教师端↓<br>
	 * 系统管理模块↓<br>
	 * 学生管理↓<br>
	 * 查询班级组织结构
	 * 
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> findDeptUseZtree(String id) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		if (id == null) {//
			// 查询阶段
			List<Record> listJD = Db
					.find("select id_f,name_f from  dept_t  d where d.type_f=2 order by d.order_f asc");
			for (Record r : listJD) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", 2 + ":" + r.getLong("id_f"));
				map.put("name", r.getStr("name_f"));
				map.put("open", false);
				map.put("isParent", "true");
				// map.put("icon", "./res/img/icon/school.png");
				// map.put("children", "[]");
				listMap.add(map);
			}
			return listMap;
		} else {// 如果不为空
			String deptType = id.split(":")[0];
			Long deptId = Long.parseLong(id.split(":")[1]);
			// 年级、班级
			List<Record> list_NJ_BJ = Db
					.find("select id_f,name_f,type_f from  dept_t  d where  d.parent_f=? order by d.order_f asc",
							deptId);
			for (Record r : list_NJ_BJ) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", r.get("type_f") + ":" + r.getLong("id_f"));
				map.put("name", r.getStr("name_f"));
				map.put("open", deptType.equals("2") ? false : true);
				map.put("isParent", deptType.equals("2") ? true : false);
				listMap.add(map);
			}
			return listMap;
		}
	}
}
