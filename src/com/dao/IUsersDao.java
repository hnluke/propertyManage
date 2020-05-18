package com.dao;

import com.pojo.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public interface IUsersDao {
    /**
     * @按用户名来查找表users中的记录
     * @author Luke
     * @param u_name 如果u_name是空字串，则查询整个users表
     * @return 返回一个Users的pojo对象的集体List
     */
    public List<Users>  findUsersByName(String u_name);

    /**
     * 按用户id来查找用户表
     * @param u_id
     * @return
     */
    public Users findUsersById(Integer u_id);

    /**
     * 更新users表中用户名为u_name的记录
     * @param u_name 用户名
     * @param users 用给定的Users对象去更新数据表中相应的记录
     * @return true代表更新成功，false代表更新失败
     */
    public boolean updateUsersByName(String u_name, Users users);



    /**
     * 删除用户名为u_name的记录
     * @param u_name 用户名
     * @return true代表删除成功，false代表删除失败
     */
    public boolean deleteUsersByName(String u_name);

    /**
     * 插入用户记录到users表
     * @param users 用户名
     * @return  true代表插入成功，false代表插入失败
     */
    public boolean insertUsers(Users users);

    /**
     * 删除用户
     * @param u_id 用户表id
     * @return
     */
    public boolean deleteUsersById(Integer u_id);

    /**
     * 复位用户密码
     * @param u_id 用户表id
     * @return
     */
    public boolean resetPwdById(Integer u_id);

}
