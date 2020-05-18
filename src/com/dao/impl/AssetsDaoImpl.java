package com.dao.impl;

import com.dao.IAssetsDao;
import com.pojo.Assets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssetsDaoImpl implements IAssetsDao {

    Connection conn = null;
    PreparedStatement ps = null;

    public AssetsDaoImpl(Connection conn) {
        this.conn = conn;
    }
    /**
     * @author Luke
     * 依照资产名称查找资产数据
     * @param ass_name 资产名称
     * @return 资产对象
     */
    @Override
    public Assets findAssetsByAss_name(String ass_name) {
        Assets assets = null;
        String sql = "select * from assets where ass_name = '" + ass_name + "'";
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                assets = new Assets();
                assets.setAss_id(rs.getInt(1));
                assets.setAss_name(rs.getString(2));
                assets.setAss_tyid(rs.getInt(3));
                assets.setAss_model(rs.getString(4));
                assets.setAss_fincode(rs.getString(5));
                assets.setAss_unit(rs.getString(6));
                assets.setAss_store(rs.getInt(7));
                assets.setAss_prices(rs.getDouble("ass_prices"));
                assets.setAss_num(rs.getInt("ass_num"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return assets;
    }

    /**
     * 依据表id来查找资产表
     * @author Luke
     * @param ass_id
     * @return
     */
    @Override
    public Assets findAssetsById(Integer ass_id) {
        Assets assets = null;
        String sql = "select * from assets where ass_id = " + ass_id;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                assets = new Assets();
                assets.setAss_id(rs.getInt(1));
                assets.setAss_name(rs.getString(2));
                assets.setAss_tyid(rs.getInt(3));
                assets.setAss_model(rs.getString(4));
                assets.setAss_fincode(rs.getString(5));
                assets.setAss_unit(rs.getString(6));
                assets.setAss_store(rs.getInt(7));
                assets.setAss_prices(rs.getDouble("ass_prices"));
                assets.setAss_num(rs.getInt("ass_num"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return assets;
    }

    /**
     * @author Luke
     * 插入资产
     * @param assets  资产对象
     * @return 插入成功返回true, 否则返回false
     */
    @Override
    public boolean insertAsserts(Assets assets) {
        boolean flag = false;
        String sql = "insert into assets (ass_name, ass_model, ass_fincode, ass_unit, ass_store, ass_prices, ass_num) "
                + "values ('" + assets.getAss_name() + "', '" + assets.getAss_model()
                + "', '" + assets.getAss_fincode() + "', '" + assets.getAss_unit()
                + "', " + assets.getAss_store() + ", " + assets.getAss_prices() + ", " + assets.getAss_num() + ")";
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
     * 依据资产名称更新资产
     * @param ass_name 资产名称
     * @param assets 资产对象
     * @return
     */
    @Override
    public boolean updateAssertsByAssName(String ass_name, Assets assets) {
        boolean flag = false;
        String sql = "update assets set ass_model = '" + assets.getAss_model() + "', ass_tyid = "
                + assets.getAss_tyid() + ", ass_fincode = '" + assets.getAss_fincode() + "', ass_unit ='"
                + assets.getAss_unit() + "', ass_store = ass_store + " + assets.getAss_store()
                + ", ass_prices = ass_prices + " + assets.getAss_prices() + ", ass_num = ass_num + "
                + assets.getAss_num() +  ", ass_tyid = "
                + assets.getAss_tyid() + " where ass_name = '" + ass_name + "'";
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
     *  资产异动
     * @author Luke
     * @param assets 资产表对象 销帐时只更新库存数量和总金额
     * @param price 价格
     * @param tag 标识是哪一种异动
     * @return
     */
    @Override
    public boolean updateAssetsStoreById(Assets assets, double price, String tag) {
        boolean flag = false;
        String sql = "";
        String sql_tail = " where ass_id = " + assets.getAss_id();
        if("领用".equals(tag)) {
            sql = "update assets set ass_store = ass_store - 1";
        }else if("归还".equals(tag)) {
            sql = "update assets set ass_store = ass_store + 1";
        }else if("返修".equals(tag)) {
            sql = "update assets set ass_store = ass_store + 1";
        }else if("报废".equals(tag)) {
            sql = "update assets set  ass_num = ass_num - 1, ass_prices = ass_prices - "
                    + price;
        }else if("销帐".equals(tag)) {
            sql = "update assets set ass_num = ass_num - 1, ass_prices = ass_prices - " + price;
        }

        sql = sql + sql_tail;
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
