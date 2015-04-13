package org.cq.model;

import org.cq.annotion.Pojo;

import com.jfinal.plugin.activerecord.Model;

/**
 * 附件
 * 
 * @author hejian
 *
 */
@SuppressWarnings("serial")
@Pojo(table=true)
public class Attach extends Model<Attach> {

	public static final Attach dao = new Attach();
	
	private Long id;
	
	private String date;
	
	private String desc;
	
	private String fileName;
	
	private Long relId;
	
	private String relType;
	
	private Long size;
	
	private String suffix;
	
	private Long user;
}
