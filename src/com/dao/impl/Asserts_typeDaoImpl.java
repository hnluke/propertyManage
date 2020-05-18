package com.dao.impl;

import com.dao.IAsserts_typeDao;
import com.pojo.Asserts_type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Asserts_typeDaoImpl implements IAsserts_typeDao {
    Connection conn = null;
    PreparedStatement ps = null;

    public Asserts_typeDaoImpl(Connection conn) {
        this.conn = conn;

    }
    /**
     * @author Luke
     * 插入资产类别
     * @param asserts_type 资产类型
     * @return
     */
    @Override
    public boolean insertAssetsType(Asserts_type asserts_type) {
        boolean flag = false;
        String asty_name = "";
        asty_name = asserts_type.getAsty_name();
        String sql = "insert into asserts_type (asty_name) values ('" + asty_name + "')";
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
     * @author Luke
     * 依据资产名称查找资产类别表
     * @param asty_name
     * @return
     */
    @Override
    public Asserts_type findAssetstypeByTypename(String asty_name) {
        Asserts_type at = null;
        String sql = "select * from asserts_type where asty_name = '" + asty_name + "'";
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                at = new Asserts_type();
                at.setAsty_id(rs.getInt(1));
                at.setAsty_name(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return at;
    }

    /**
     * 关闭statement
     */
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


