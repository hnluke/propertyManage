package com.service;

import com.pojo.Users;

import java.sql.Connection;
import java.util.List;

public interface IUsersService {
    /**
     * @author Luke
     * 验证用户是否为合法用户
     * @param u_name 用户名
     * @param u_pwd  密码
     * @return true表示为合法用户,false为非法用户
     */
    public boolean verify(String u_name, String u_pwd);

    /**
     * @author Luke
     * 新增用户
     * @param users 用户资料
     * @return true:新增成功 false:新增失败
     */
    public boolean addUser(Users users);

    /**
     * 修改用户资料
     * @param users 用户资料
     * @return true:修改成功 false:修改失败
     */
    public boolean modifyUser(String u_name, Users users);

    /**
     * 查看用户信息
     * @param u_name 用户名
     * @return 返回用户信息集合
     */
    public List<Users> showUsers(String u_name);

    /**
     * 按照用户名来查询用户表，并返回用户对象集合
     * @param u_name 用户名，如果为空，则查询全部的记录
     * @return
     */
    public List<Users> findUsersByUname(String u_name);

    /**
     * 按照用户id来查找用户，并返回用户表对象
     * @author Luke
     * @param u_id 用户id
     * @return
     */
    public Users findUserById(Integer u_id);

    /**
     * 复位用户密码
     * @author Luke
     * @param u_id 用户表id
     * @return
     */
    public String resetUserPwd(Integer u_id);

    /**
     * 删除用户
     * @author Luke
     * @param u_id 用户表id
     * @return
     */
    public String deleteUserById(Integer u_id);

    /**
     * 依照用户名来修改用户信息
     * @param u_name 用户名
     * @return
     */
    public String updateUsersByUserName(String u_name, Users users);

}
