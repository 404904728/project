package org.cq.model;

import org.cq.annotion.Pojo;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created with IntelliJ IDEA.
 * User: hejian
 * Date: 14-12-2
 * Time: 下午12:13
 * To change this template use File | Settings | File Templates.
 */
@Pojo(table=true)
public class Role extends Model<Role> {

    public static final Role dao = new Role();
    
    private Long id;
    
    private String name;
    
    private String desc;
    
    private boolean status = true;
    
}
