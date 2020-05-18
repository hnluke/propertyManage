package com.dao.impl;

import com.dao.IUsersDao;
import com.db.MD5;
import com.pojo.Users;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDaoImpl implements IUsersDao {

    private Connection conn = null;
    private PreparedStatement ps = null;

    public UsersDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * @author Luke
     * @param u_name 如果u_name是空字串，则查询整个users表
     * @return
     */
    @Override
    public List<Users> findUsersByName(String u_name) {

        List<Users> list = new ArrayList<Users>();
        String sql = "";
        String sql_tail = "";
        if (!"".equals(u_name)) {
            sql_tail = " where u_name = '" + u_name + "'";
        }
        sql = "select * from users" + sql_tail;
        Users users = null;
        list = new ArrayList<Users>();
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                users = new Users();
                users.setU_id(rs.getInt(1));
                users.setU_name(rs.getString(2));
                users.setU_pwd(rs.getString(3));
                users.setU_prio(rs.getString(4));
                users.setU_tele(rs.getString(5));
                users.setU_lock(rs.getBoolean(6));
                list.add(users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return list;
    }

    /**
     *  @author Luke
     * @param u_name 用户名
     * @param users 用给定的Users对象去更新数据表中相应的记录
     * @return
     */
    @Override
    public boolean updateUsersByName(String u_name, Users users) {
        boolean flag = false;
        String pwd = null;
        try {
            // 加密密码
            pwd = MD5.getEncryptedPwd(users.getU_pwd());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String sql = "update users set  u_pwd = '" + pwd + "', u_prio = '"
                + users.getU_prio() + "', u_tele = '" + users.getU_tele()
                + "', u_lock = " + users.getU_lock()
                + " where u_name = '" + u_name + "'";
        try {
            ps = conn.prepareStatement(sql);
            if(ps.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return flag;
    }

    /**
     * 删除记录
     * @param u_name 用户名
     * @return
     */
    @Override
    public boolean deleteUsersByName(String u_name) {
        boolean flag = false;
        String sql = "delete from users where u_name = '" + u_name + "'";
        try {
            ps = conn.prepareStatement(sql);
            if(ps.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();

        return flag;

    }

    /**
     * 插入记录
     * @param users 用户名
     * @return
     */
    @Override
    public boolean insertUsers(Users users) {
        boolean flag = false;
        String pwd = "";
        try {
            // 对密码进行加密
            pwd = MD5.getEncryptedPwd(users.getU_pwd());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sql = "insert into users (u_name, u_pwd, u_prio, u_tele, u_lock) values ('"
                + users.getU_name() + "', '" + pwd + "','" + users.getU_prio()
                + "', '" + users.getU_tele() + "', " + users.getU_lock() + ")";

        try {
            ps = conn.prepareStatement(sql);
            if(ps.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeStatement();
        return flag;
    }

    /**
     * 复位用户密码
     * @author Luke
     * @param u_id 用户表id
     * @return
     */
    @Override
    public boolean resetPwdById(Integer u_id) {
        boolean flag = false;
        String u_pwd = "888";
        try {
            // 对密码进行加密
            u_pwd = MD5.getEncryptedPwd(u_pwd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sql = "update users set u_pwd = '" + u_pwd + "' where u_id = " + u_id;
        try {
            ps = conn.prepareStatement(sql);
            if(ps.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return flag;
    }

    /**
     * 删除用户
     * @author
     * @param u_id 用户表id
     * @return
     */
    @Override
    public boolean deleteUsersById(Integer u_id) {
        boolean flag = false;
        String sql = "delete from users where u_id = " + u_id;
        try {
            ps = conn.prepareStatement(sql);
            if(ps.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Users findUsersById(Integer u_id) {
        Users users = null;
        String sql = "select * from users where u_id = " + u_id;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                users = new Users();
                users.setU_id(rs.getInt("u_id"));
                users.setU_name(rs.getString("u_name"));
                users.setU_pwd(rs.getString("u_pwd"));
                users.setU_prio(rs.getString("u_prio"));
                users.setU_tele(rs.getString("u_tele"));
                users.setU_lock(rs.getBoolean("u_lock"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void closeStatement() {
        try {
            if(ps != null){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ps = null;
    }
}
