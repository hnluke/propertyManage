package com.test;

import com.db.DBConnection;
import com.factory.AssetsDaoFactory;
import com.pojo.Users;
import javafx.scene.input.DataFormat;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.time.ZonedDateTime.now;

public class TestData {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnect("c3p0");
        //Timestamp t= new Timestamp(120000021);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String now = sdf.format("2020-02-03 09:44:23");
        String now = "2020-02-03 09:44:23";
        Date d = new Date();
        String sql = "insert into test (t_date) values (" + d + ")";
        //String sql = "select t_date from test";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
           // ResultSet rs = ps.executeQuery();
            ps.executeUpdate();
           // rs.next();
           // Date str = rs.getTimestamp(1);
            //System.out.println(str);
//            if (ps.executeUpdate() > 0) {
//                System.out.println("插入成功");
//            }else{
//                System.out.println("插入失败");
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        Users users = new Users();
//        users.setU_name("Luke");
//        users.setU_pwd("123");
//        users.setU_prio("S");
//        users.setU_lock(false);
//        users.setU_tele("13342571025");
//        try {
//            if(AssetsDaoFactory.getUsersServiceImpl().addUser(users)){
//                System.out.println("插入成功");
//            }else{
//                System.out.println("插入失败");
//            }
//        } catch (Exception e) {
//            System.out.println("Error");
//            e.printStackTrace();
//        }
//        String user = "luke";
//        String pwd = "123";
//        if(AssetsDaoFactory.getUsersServiceImpl().verify(user, pwd)) {
//            System.out.println("登录成功");
//        }else{
//            System.out.println("登录失败");
//        }
    }
}
