package com.dao.impl;

import com.dao.IQueryAssetsDao;
import com.pojo.AllAssetsItem;
import com.pojo.Purchase;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryAssetsDaoImpl implements IQueryAssetsDao {
    Connection conn = null;
    PreparedStatement ps = null;
    String sql =  "(select F.ad_cardcode as ad_cardcode, F.ad_code as ad_code, F.ad_serial as ad_serial, " +
            "E.ass_name as ass_name, E.asty_name as asty_name, E.ass_model as ass_model, " +
            "E.ass_unit as ass_unit, F.ad_num as ad_num, E.ass_fincode as ass_fincode, E.av_insttime as av_insttime," +
            "E.av_findate as av_findate, F.ad_avno as ad_avno, E.av_no as ad_avno2 ,E.av_finnum as av_finnum, G.u_name as u_name, " +
            "F.ad_status as ad_status, E.ass_store as ass_store, F.ad_id as ad_id, F.ad_price as ad_price, " +
            "E.ass_prices as ass_prices, E.ass_num as ass_num "+
            "from assert_detail F " +
            "LEFT JOIN users G on F.ad_uid = G.u_id " +
            "LEFT JOIN " +
            "(select D.*, C.ass_name, C.ass_model, C.ass_fincode, C.ass_unit, C.ass_store, C.asty_name, C.ass_prices," +
            "C.ass_num " +
            "from assets_vou D LEFT JOIN " +
            "(select B.asty_name, A.* " +
            "from assets A Left JOIN asserts_type B " +
            "on A.ass_tyid = B.asty_id) C " +
            "on D.av_assid = C.ass_id) E " +
            "on F.ad_avid = E.av_id) H";;

    public QueryAssetsDaoImpl(Connection conn) {
        this.conn = conn;
    }
    /**
     *
     * @param field 字段名称
     * @param value  值
     * @param flag 暂时保留，一般传""
     * @param index 第几页
     * @return
     */
    @Override
    public List<AllAssetsItem> findAssetsBy(String field, String value, String flag, int index) {
        List<AllAssetsItem> list = null;
        String sql_head = "select * from ";
        String sqlQuery = "";

        String tail = "";
        if("".equals(field)) {
            tail = "";
        }else
        {
            tail = " where H." + field + " like '%" + value + "%'";
        }

        sqlQuery = sql_head + sql + tail  + " order by H.ad_id " ;
        try {
            ps = conn.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            list = new ArrayList<AllAssetsItem>();
            while(rs.next()) {

                AllAssetsItem aai = new AllAssetsItem();
                aai.setAd_cardcode(rs.getString(1));
                aai.setAd_code(rs.getString(2));
                aai.setAd_serial(rs.getString(3));
                aai.setAss_name(rs.getString(4));
                aai.setAsty_name(rs.getString("asty_name"));
                aai.setAss_model(rs.getString(6));
                aai.setAss_unit(rs.getString(7));
                aai.setAd_num(rs.getInt(8));
                aai.setAss_fincode(rs.getString(9));
                aai.setAv_insttime(rs.getTimestamp(10));
                aai.setAv_findate(rs.getTimestamp(11));
                aai.setAd_avno(rs.getString(12));
                aai.setAv_finnum(rs.getInt(14));
                aai.setU_name(rs.getString(15));
                aai.setAd_status(rs.getString(16));
                aai.setAss_store(rs.getInt(17));
                aai.setAd_id(rs.getInt(18));
                aai.setAd_price(rs.getDouble("ad_price"));
                aai.setAss_prices(rs.getDouble("ass_prices"));
                aai.setAss_num(rs.getInt("ass_num"));
                list.add(aai);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeStatement();
        return list;
    }

    @Override
    public int queryAssetsCount(String field, String value, String flag, int index) {
        int recordCount = 0;
        String sql_head_count = "select count(*) from ";
        String sqlCount = "";
        String tail = "";
        if("".equals(field)) {
            tail = "";
        }else
        {
            tail = " where H." + field + " like '%" + value + "%'";
        }

        sqlCount = sql_head_count + sql + tail  + " order by H.ad_id ";
        try {
            ps = conn.prepareStatement(sqlCount);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                recordCount = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recordCount;
    }

    /**
     * 将数据导出到Excel报表
     * @param list 资产列表集体List
     * @param path 文件保存路径
     * @return
     */
    @Override
    public boolean exportToExcel(List<AllAssetsItem> list, String path) {
        boolean flag = false;
        WritableWorkbook wwb = null;
        // 如果没有此文件，则新建一个
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            // 创建工作薄
            wwb = Workbook.createWorkbook(file);
            WritableSheet ws = wwb.createSheet("资产数据表", 0);
            Label labelAd_code = new Label(0, 0, "资产编号");      //表示第0列
            Label labelAd_cardcode = new Label(1, 0, "卡片编号");
            Label labelAd_serial = new Label(2, 0, "序列号");
            Label labelAss_name = new Label(3, 0, "资产名称");
            Label labelAsty_name = new Label(4, 0, "资产类别");
            Label labelAss_model = new Label(5, 0, "规格型号");
            Label labelAss_unit = new Label(6, 0, "单位");
            Label labelAd_price = new Label(7, 0, "单价");
            Label labelAd_vouno = new Label(8, 0, "凭证号");
            Label labelAss_fincode = new Label(9, 0, "财务编码");
            Label labelAv_findate = new Label(10, 0, "入帐日期");
            Label labelAv_insttime = new Label(11, 0, "入库日期");
            Label labelU_name = new Label(12, 0, "使用人");
            Label labelAd_status = new Label(13, 0, "资产状态");
            ws.addCell(labelAd_code);
            ws.addCell(labelAd_cardcode);
            ws.addCell(labelAd_serial);
            ws.addCell(labelAss_name);
            ws.addCell(labelAsty_name);
            ws.addCell(labelAss_model);
            ws.addCell(labelAss_unit);
            ws.addCell(labelAd_price);
            ws.addCell(labelAd_vouno);
            ws.addCell(labelAss_fincode);
            ws.addCell(labelAv_findate);
            ws.addCell(labelAv_insttime);
            ws.addCell(labelU_name);
            ws.addCell(labelAd_status);
            for (int i = 0; i < list.size(); i++) {
                Label labelAd_code_i = new Label(0, i + 1, list.get(i).getAd_code() + "");
                Label labelAd_cardcode_i = new Label(1, i + 1, list.get(i).getAd_cardcode());
                Label labeAd_serial_i = new Label(2, i + 1, list.get(i).getAd_serial());
                Label labeAss_name_i = new Label(3, i + 1, list.get(i).getAss_name() + "");
                Label labelAsty_name_i = new Label(4, i + 1, list.get(i).getAsty_name() + "");
                Label labelAss_model_i = new Label(5, i + 1, list.get(i).getAss_model() + "");
                Label labelAss_unit_i = new Label(6, i + 1, list.get(i).getAss_unit() + "");
                Label labelAd_price_i = new Label(7, i + 1, list.get(i).getAd_price() + "");
                Label labelP_Ad_avno_i = new Label(8, i + 1, list.get(i).getAd_avno() + "");
                Label labelAss_fincode_i = new Label(9, i + 1, list.get(i).getAss_fincode() + "");
                Label labelAv_findate_i = new Label(10, i + 1, list.get(i).getAv_findate() + "");
                Label labelAv_insttime_i = new Label(11, i + 1, list.get(i).getAv_insttime() + "");
                Label labelU_name_i = new Label(12, i + 1, list.get(i).getU_name() + "");
                Label labelAd_status_i = new Label(13, i + 1, list.get(i).getAd_status() + "");

                ws.addCell(labelAd_code_i);
                ws.addCell(labelAd_cardcode_i);
                ws.addCell(labeAd_serial_i);
                ws.addCell(labeAss_name_i);
                ws.addCell(labelAsty_name_i);
                ws.addCell(labelAss_model_i);
                ws.addCell(labelAss_unit_i);
                ws.addCell(labelAd_price_i);
                ws.addCell(labelP_Ad_avno_i);
                ws.addCell(labelAss_fincode_i);
                ws.addCell(labelAv_findate_i);
                ws.addCell(labelAv_insttime_i);
                ws.addCell(labelU_name_i);
                ws.addCell(labelAd_status_i);

            }
            //写进文档
            wwb.write();
            // 关闭Excel工作簿对象
            wwb.close();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
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
