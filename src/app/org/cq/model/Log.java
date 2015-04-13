package org.cq.model;

import java.util.Date;

import org.cq.annotion.Pojo;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created with IntelliJ IDEA.
 * User: hejian
 * Date: 14-12-2
 * Time: 下午12:17
 * To change this template use File | Settings | File Templates.
 */
@Pojo(table=true)
public class Log extends Model<Log> {
    public static final Log dao = new Log();
    
    private Long id;
    
    private Long user;
    
    private String content;
    
    private Date date;
}
