package com.dao.impl;

import com.dao.IAssets_vouDao;
import com.pojo.Assets_vou;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Assets_vouDaoImpl implements IAssets_vouDao {
    Connection conn = null;
    PreparedStatement ps = null;

    public Assets_vouDaoImpl(Connection conn) {
        this.conn = conn;
    }
    /**
     * @author Luke
     *  依据凭证号来查找资产
     * @param av_no 凭证号
     * @return @return 返回资产凭证对象
     */
    @Override
    public Assets_vou findAssetsVouByAvno(String av_no) {
        Assets_vou av = null;
        String sql = "select * from assets_vou where av_no = '" + av_no + "'";
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                av = new Assets_vou();
                av.setAv_id(rs.getInt(1));
                av.setAv_no(rs.getString(2));
                av.setAv_assid(rs.getInt(3));
                av.setAv_finnum(rs.getInt(4));
                av.setAv_findate(rs.getDate(5));
                av.setAv_insttime(rs.getTimestamp(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return av;
    }

    /**
     * 依据表id来查找资产表数据
     * @param av_id 凭证id
     * @return
     */
    @Override
    public Assets_vou findAssetsVouById(Integer av_id) {
        Assets_vou av = null;
        String sql = "select * from assets_vou where av_id = " + av_id ;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                av = new Assets_vou();
                av.setAv_id(rs.getInt(1));
                av.setAv_no(rs.getString(2));
                av.setAv_assid(rs.getInt(3));
                av.setAv_finnum(rs.getInt(4));
                av.setAv_findate(rs.getDate(5));
                av.setAv_insttime(rs.getTimestamp(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return av;
    }

    /**
     * @author Luke
     * 插入资产凭证
     * @param assets_vou    资产凭证对象
     * @return 插入成功返回true, 否则返回false
     */
    @Override
    public boolean insertAssetsVou(Assets_vou assets_vou) {
        boolean flag = false;
        String sql = "insert into assets_vou (av_no, av_assid, av_finnum) "
                + "values ('" + assets_vou.getAv_no() + "', " + assets_vou.getAv_assid()
                + ", " + assets_vou.getAv_finnum() + ")";
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
     * 更新资产凭证表
     * @author Luke
     * @param av_no 资产凭证号
     * @param assets_vou  资产凭证对象
     * @return 更新成功返回true, 否则返回false
     */
    @Override
    public boolean updateAssetsVouByAvno(String av_no, Assets_vou assets_vou) {
        boolean flag = false;
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "update assets_vou set av_assid = " + assets_vou.getAv_assid() + ", av_finnum = "
            + assets_vou.getAv_finnum() + " where av_no = '" + av_no + "'";
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
