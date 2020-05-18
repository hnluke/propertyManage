package com.dao.impl;

import com.dao.IAssert_detailDao;
import com.pojo.Assert_detail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Assert_detailDaoImpl implements IAssert_detailDao {

    Connection conn = null;
    PreparedStatement ps = null;

    public Assert_detailDaoImpl(Connection conn) {
        this.conn = conn;
    }
    /**
     *  按照资产编号来查询资产明细表
     *  @author Luke
     * @param ad_code 资产编号
     * @return 资产明细对象
     */
    @Override
    public Assert_detail findAssetsByAdCode(String ad_code) {
        Assert_detail ad = null;
        String sql = "select * from assert_detail where ad_code = '" + ad_code + "'";
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                ad = new Assert_detail();
                ad.setAd_id(rs.getInt(1));
                ad.setAd_cardcode(rs.getString(2));
                ad.setAd_serial(rs.getString(3));
                ad.setAd_code(rs.getString(4));
                ad.setAd_avid(rs.getInt(5));
                ad.setAd_avno(rs.getString(6));
                ad.setAd_num(rs.getInt(7));
                ad.setAd_uid(rs.getInt(8));
                ad.setAd_status(rs.getString(9));
                ad.setAd_purcode(rs.getString(10));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return ad;
    }

    /**
     * 以id为关键字查询数据表assert_detail
     * @author Luke
     * @param ad_id 资产明细id
     * @return
     */
    @Override
    public Assert_detail findAssetsDetailById(Integer ad_id) {
        Assert_detail ad = null;
        String sql = "select * from assert_detail where ad_id = " + ad_id ;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                ad = new Assert_detail();
                ad.setAd_id(rs.getInt(1));
                ad.setAd_cardcode(rs.getString(2));
                ad.setAd_serial(rs.getString(3));
                ad.setAd_code(rs.getString(4));
                ad.setAd_avid(rs.getInt(5));
                ad.setAd_avno(rs.getString(6));
                ad.setAd_num(rs.getInt(7));
                ad.setAd_uid(rs.getInt(8));
                ad.setAd_status(rs.getString(9));
                ad.setAd_purcode(rs.getString(10));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return ad;
    }

    /**
     * @author Luke
     * @param ad_code 资产编号
     * @param assert_detail 资产明细对象
     * @return
     */
    @Override
    public boolean updateAssertsByAdCode(String ad_code, Assert_detail assert_detail) {
        boolean flag = false;
        Integer ad_avid = assert_detail.getAd_avid();
        String ad_avno = assert_detail.getAd_avno();
        Integer ad_num = assert_detail.getAd_num();
        Integer ad_uid = assert_detail.getAd_uid();
        String ad_status = assert_detail.getAd_status();
        String ad_purcode = assert_detail.getAd_purcode();
        String sql = "update assert_detail set ad_avid = " + ad_avid
                + ", ad_avno = '" + ad_avno + "', ad_num = " + ad_num
                + ", ad_uid = " + ad_uid + ", ad_status = '" + ad_status
                + "', '" + ad_purcode + "' where ad_avno = '" + ad_code + "'";

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
     * 根据采购单号来更新资产明细表
     * @author Luke
     * @param ad_purcode    采购单号
     * @param assert_detail 资产明细表对象
     * @return
     */
    public boolean updateAssertsByPurCode(String ad_purcode, Assert_detail assert_detail) {
        boolean flag = false;
        Integer ad_avid = assert_detail.getAd_avid();
        String ad_avno = assert_detail.getAd_avno();
        Integer ad_num = assert_detail.getAd_num();
        Integer ad_uid = assert_detail.getAd_uid();
        String ad_status = assert_detail.getAd_status();
        //String ad_purcode = assert_detail.getAd_purcode();
        String sql = "update assert_detail set ad_avid = " + ad_avid
                + ", ad_avno = '" + ad_avno + "', ad_num = " + ad_num
                + ", ad_uid = " + ad_uid + ", ad_status = '" + ad_status
                + "' where ad_purcode = '" + ad_purcode + "'";

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
     * 插入资产明细
     * @param assert_detail 资产明细对象
     * @return
     */
    @Override
    public boolean insertAssets(Assert_detail assert_detail) {
        boolean flag = false;
        String ad_cardcode = assert_detail.getAd_cardcode();
        String ad_serial = assert_detail.getAd_serial();
        String ad_code = assert_detail.getAd_code();
        //Integer ad_avid = assert_detail.getAd_avid();   // 凭证号id
        //String ad_avno = assert_detail.getAd_avno();    // 凭证号
        Integer ad_num = assert_detail.getAd_num();     // 数量
        Integer ad_uid = assert_detail.getAd_uid();     // 用户id
        String ad_status = assert_detail.getAd_status();    // 资产状态
        String ad_purcode = assert_detail.getAd_purcode();  // 采购单号
        Double ad_price = assert_detail.getAd_price();
        String sql = "insert into assert_detail (ad_cardcode, ad_serial, ad_code, ad_num, ad_status, ad_purcode, ad_price) values ('" +
             ad_cardcode + "', '" + ad_serial + "', '" + ad_code + "', " + ad_num + ", '" + ad_status
                + "', '" + ad_purcode + "', " + ad_price + ")";

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

    /**
     * @author Luke
     * 根据凭证id去更新对应的凭证号，资产凭证表一致
     * @param ad_avid   凭证id
     * @param vou 凭证号
     * @return
     */
    @Override
    public boolean updateAssertsVouByAdAvid(Integer ad_avid, String vou) {
        boolean flag = false;
        String sql = "update assert_detail set ad_avno = '" + vou + "', ad_status = '在库(已确认)' " +
                "where ad_avid = " + ad_avid;
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
     * 依据 采购单号更新资产明细表凭证id
     * @param ad_purcode    采购单号
     * @param av_id     凭证id
     * @return
     */
    public boolean updateAssertsVouAdavidByPurcode(String ad_purcode, Integer av_id) {
        boolean flag = false;
        String sql = "update assert_detail set ad_avid = " + av_id +
                " where ad_purcode = '" + ad_purcode + "'";
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
     * 依据id来更新资产明细表的资产状态
     * @author
     * @param ad_id     表id
     * @param ad_uid    用户id
     * @param tag       "领用","归还","返修", "报废", "销帐"
     * @return
     */
    public boolean updateAssertdetailStatusById(Integer ad_id, Integer ad_uid, String tag) {
        boolean flag = false;
        String sql = "";
        String sql_tail = " where ad_id = " + ad_id;
        if("领用".equals(tag)) {
            sql = "update assert_detail set ad_status = '出库(领用)', ad_uid = " + ad_uid;
        }else if("归还".equals(tag)) {
            sql = "update assert_detail set ad_status = '在库(归还)', ad_uid = null";
        }else if("返修".equals(tag)) {
            sql = "update assert_detail set ad_status = '在库(返修)', ad_uid = null";

        }else if("报废".equals(tag)) {
            sql = "update assert_detail set ad_status = '报废'";

        }else if("销帐".equals(tag)) {
            sql = "update assert_detail set ad_status = '销帐'";

        }

        sql = sql + sql_tail;
        try {
            ps = conn.prepareStatement(sql);
            if(ps.executeUpdate() > 0){
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return flag;
    }

    /**
     * 关闭Statement
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
