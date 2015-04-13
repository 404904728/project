package org.cq.model;

import org.cq.annotion.Pojo;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created with IntelliJ IDEA.
 * User: hejian
 * Date: 14-12-2
 * Time: 下午12:14
 * To change this template use File | Settings | File Templates.
 */
@Pojo(table = true)
public class Permission extends Model<Permission> {

    public static final Permission dao = new Permission();
    
    private Long id;
    
    private String name;
    
    private String desc;
}
