package com.service.impl;

import com.dao.impl.UsersDaoImpl;
import com.db.MD5;
import com.pojo.Users;
import com.service.IUsersService;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UsersServiceImpl implements IUsersService {

    private Connection conn = null;
    private UsersDaoImpl usersDaoImpl = null;
    public UsersServiceImpl(Connection conn) {
        this.conn = conn;
        usersDaoImpl = new UsersDaoImpl(conn);
    }

    /**
     * @author Luke
     * 验证用户是否合法
     * @param u_name 用户名
     * @param u_pwd  密码
     * @return
     */
    @Override
    public boolean verify(String u_name, String u_pwd) {
        List<Users> list = new ArrayList<Users>();
        boolean flag = false;
        list = usersDaoImpl.findUsersByName(u_name);
        if(list != null) {
            Users users = list.get(0);
            try {
                if(MD5.validPassword(u_pwd, users.getU_pwd())) {
                    flag = true;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //closeConnection();
        return flag;
    }



    /**
     * 增加用户
     * @param users 用户资料
     * @return
     */
    @Override
    public boolean addUser(Users users) {
        boolean flag = false;
        flag =  usersDaoImpl.insertUsers(users);
        //closeConnection();
        return flag;
        //return false;
    }

    /**
     * 修改用户
     * @param u_name 用户名
     * @param users 用户信息
     * @return
     */

    @Override
    public boolean modifyUser(String u_name, Users users) {
        boolean flag = false;
        flag =  usersDaoImpl.updateUsersByName(u_name, users);
        //closeConnection();
        return flag;
    }

    /**
     * 查询用户信息
     * @param u_name 用户名
     * @return
     */
    @Override
    public List<Users> showUsers(String u_name) {
        List<Users> list = new ArrayList<Users>();
        list =  usersDaoImpl.findUsersByName(u_name);
        //closeConnection();
        return list;

    }

    /**
     * 以用户名为参照查询用户表
     * @param u_name 用户名，如果为空，则查询全部的记录
     * @return
     */
    @Override
    public List<Users> findUsersByUname(String u_name) {
        List<Users> list = new ArrayList<Users>();
        list = usersDaoImpl.findUsersByName(u_name);
        //closeConnection();
        return list;
    }

    /**
     * 复位用户密码
     * @author Luke
     * @param u_id 用户表id
     * @return
     */
    @Override
    public String resetUserPwd(Integer u_id) {
        String notice = "";
        if(usersDaoImpl.resetPwdById(u_id)) {
            notice = "复位密码成功";
        }else{
            notice = "复位密码失败";
        }
        //closeConnection();
        return notice;
    }

    @Override
    public String deleteUserById(Integer u_id) {
        String notice = "";
        if(usersDaoImpl.deleteUsersById(u_id)) {
            notice = "删除用户成功";
        }else {
            notice = "删除用户失败";
        }
        //closeConnection();
        return notice;
    }

    @Override
    public Users findUserById(Integer u_id) {
        Users users = null;
        users = usersDaoImpl.findUsersById(u_id);
        //closeConnection();
        return users;
    }

    /**
     * 修改用户
     * @author Luke
     * @param u_name 用户名
     * @param users
     * @return
     */
    @Override
    public String updateUsersByUserName(String u_name, Users users) {
        String notice = "";
        if(usersDaoImpl.updateUsersByName(u_name, users)){
            notice = "修改成功";
        }else{
            notice = "修改不成功";
        }
        //closeConnection();
        return notice;
    }

//    private void closeConnection() {
//        try {
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
