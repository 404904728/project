package org.cq.model;

import org.cq.annotion.Pojo;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created with IntelliJ IDEA.
 * User: hejian
 * Date: 14-12-2
 * Time: 下午12:16
 * To change this template use File | Settings | File Templates.
 */
@Pojo(table=true)
public class User2role extends Model<User2role> {

    public static final User2role dao = new User2role();
    
    private Long id;
    
    private Long user;
    
    private Long role;
}
