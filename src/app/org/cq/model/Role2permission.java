package org.cq.model;

import org.cq.annotion.Pojo;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created with IntelliJ IDEA.
 * User: hejian
 * Date: 14-12-2
 * Time: 下午12:14
 * 角色权限表
 */
@Pojo(table=true)
public class Role2permission extends Model<Role2permission> {

    public static final Role2permission dao = new Role2permission();

    private Long id;
    
    private Long role;
    
    private Long permission;
    
}
