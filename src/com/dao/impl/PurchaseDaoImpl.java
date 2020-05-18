package com.dao.impl;

import com.dao.IPurchaseDao;
import com.mchange.v2.c3p0.ConnectionCustomizer;
import com.pojo.Purchase;
import jxl.Sheet;
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
import java.util.List;

public class PurchaseDaoImpl implements IPurchaseDao {
    Connection conn = null;
    PreparedStatement ps = null;

    public PurchaseDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * 依据采购单号查找采购单数据
     * @author Luke
     * @param p_code
     * @return

     */
    @Override
    public List<Purchase> findPurchaseByPCode(String p_code) {
        Purchase purchase = null;
        List<Purchase> list = new ArrayList<Purchase>();
        String sql = "";
        if ("".equals(p_code)) {
            sql = "select * from purchase";
        } else {
            sql = "select * from purchase where p_code = '" + p_code + "'";
        }
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                purchase = new Purchase();
                purchase.setP_id(rs.getInt(1));
                purchase.setP_code(rs.getString(2));
                purchase.setP_assname(rs.getString(3));
                purchase.setP_unit(rs.getString(4));
                purchase.setP_num(rs.getInt(5));
                purchase.setP_type(rs.getString(6));
                purchase.setP_model(rs.getString(7));
                purchase.setP_vouno(rs.getString(8));
                purchase.setP_prices(rs.getDouble(9));
                list.add(purchase);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return list;
    }

    /**
     * 删除采购表数据
     * @author Luke
     * @param p_id 表id
     * @return
     */
    @Override
    public boolean deletePurchaseByPCode(Integer p_id) {
        boolean flag = false;
        String sql = "delete from purchase where p_id = " + p_id;
        try {
            ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return flag;
    }

    @Override
    public Purchase findPurchaseByPurId(Integer id) {
        Purchase purchase = null;
        String sql = "";
        if (id == -1) {
            sql = "select * from purchase";
        } else {
            sql = "select * from purchase where p_id = " + id;
        }
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                purchase = new Purchase();
                purchase.setP_id(rs.getInt(1));
                purchase.setP_code(rs.getString(2));
                purchase.setP_assname(rs.getString(3));
                purchase.setP_unit(rs.getString(4));
                purchase.setP_num(rs.getInt(5));
                purchase.setP_type(rs.getString(6));
                purchase.setP_model(rs.getString(7));
                purchase.setP_vouno(rs.getString(8));
                purchase.setP_prices(rs.getDouble("p_prices"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeStatement();
        return purchase;
    }

    /**
     * 向采购表插入数据
     *
     * @param purchase 采购表对象
     * @return
     * @author Luke
     */
    @Override
    public boolean insertPurchase(Purchase purchase) {
        boolean flag = false;
        String sql = sql = "insert into purchase (p_code, p_assname, p_unit, p_num, p_type," +
                " p_model, p_vouno, p_prices) values ('" + purchase.getP_code() +
                "', '" + purchase.getP_assname() + "', '" + purchase.getP_unit() +
                "', " + purchase.getP_num() + ", '" + purchase.getP_type() +
                "', '" + purchase.getP_model() + "', '" + purchase.getP_vouno() +
                "', " + purchase.getP_prices() + ")";
        try {
            ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() > 0)
                flag = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 更新采购表数据
     * @author Luke
     * @param p_code   采购单号
     * @param purchase 采购表对象
     * @return
     * @author Luke
     */
    @Override
    public boolean updatePurchaseByPCode(String p_code, Purchase purchase) {
        boolean flag = false;
        String sql = "update purchase set p_assname = '" + purchase.getP_assname() +
                "', p_unit = '" + purchase.getP_unit() + "', p_num = " + purchase.getP_num() +
                ", p_type = '" + purchase.getP_type() + "', p_model = '" + purchase.getP_model() +
                "', p_vouno = '" + purchase.getP_vouno() + "', p_prices = " + purchase.getP_prices() +
                " where p_code = '" + p_code + "'";
        try {
            ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return flag;
    }

    /**
     * 将Excel的数据转化为pojo对象的List
     *
     * @param path 文件路径
     * @return
     */
    public List<Purchase> getAllByExcel(String path) {
        List<Purchase> list = new ArrayList<Purchase>();
        try {
            Workbook rwb = Workbook.getWorkbook(new File(path));
            Sheet rs = rwb.getSheet("采购单");// 或者rwb.getSheet(0)
            int clos = rs.getColumns();// 得到所有的列
            int rows = rs.getRows();// 得到所有的行
            System.out.println(clos + " rows:" + rows);
            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < clos; j++) {
                    // 第一个是列数，第二个是行数
                    //j = j + 1;
                    String p_code = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
                    //if (userId == null || "".equals(userId))userId = "0";
                    // 所以这里得j++
                    String p_assname = rs.getCell(j++, i).getContents();
                    String p_unit = rs.getCell(j++, i).getContents();
                    String p_num = rs.getCell(j++, i).getContents();
                    String p_type = rs.getCell(j++, i).getContents();
                    String p_model = rs.getCell(j++, i).getContents();
                    String p_vouno = rs.getCell(j++, i).getContents();
                    String p_prices = rs.getCell(j++, i).getContents();
                    Purchase purchase = new Purchase();
                    purchase.setP_code(p_code);
                    purchase.setP_assname(p_assname);
                    purchase.setP_unit(p_unit);
                    purchase.setP_num(Integer.parseInt(p_num));
                    purchase.setP_type(p_type);
                    purchase.setP_model(p_model);
                    purchase.setP_vouno(p_vouno);
                    purchase.setP_prices(Double.parseDouble(p_prices));

                    list.add(purchase);
                }
            }
        } catch (Exception e) {
            list = null;
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean isExists(String p_code) {
        boolean flag = false;
        String sql = "select count(*) from purchase where p_code = '" + p_code + "'";
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    flag = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 将数据导出到Excel报表
     * @param list
     * @param path
     * @return
     */
    @Override
    public boolean exportToExcel(List<Purchase> list, String path) {
        boolean flag = false;
        WritableWorkbook wwb = null;

        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            wwb = Workbook.createWorkbook(file);
            WritableSheet ws = wwb.createSheet("采购数据表", 0);
            Label labelP_code = new Label(0, 0, "采购编号");//表示第
            Label labelP_assname = new Label(1, 0, "资产名称");
            Label labelP_unit = new Label(2, 0, "单位");
            Label labelP_num = new Label(3, 0, "数量");
            Label labelP_type = new Label(4, 0, "资产类别");
            Label labelP_model = new Label(5, 0, "规格型号");
            Label labelP_vouno = new Label(6, 0, "凭证号");
            Label labelP_prices = new Label(7, 0, "总价格");
            ws.addCell(labelP_code);
            ws.addCell(labelP_assname);
            ws.addCell(labelP_unit);
            ws.addCell(labelP_num);
            ws.addCell(labelP_type);
            ws.addCell(labelP_model);
            ws.addCell(labelP_vouno);
            ws.addCell(labelP_prices);
            for (int i = 0; i < list.size(); i++) {
                Label labelP_code_i = new Label(0, i + 1, list.get(i).getP_code() + "");
                Label labelP_assname_i = new Label(1, i + 1, list.get(i).getP_assname());
                Label labelP_unit_i = new Label(2, i + 1, list.get(i).getP_unit());
                Label labelP_num_i = new Label(3, i + 1, list.get(i).getP_num() + "");
                Label labelP_type_i = new Label(4, i + 1, list.get(i).getP_type() + "");
                Label labelP_mode_i = new Label(5, i + 1, list.get(i).getP_model() + "");
                Label labelP_vouno_i = new Label(6, i + 1, list.get(i).getP_vouno() + "");
                Label labelP_prices_i = new Label(7, i + 1, list.get(i).getP_prices() + "");
                ws.addCell(labelP_code_i);
                ws.addCell(labelP_assname_i);
                ws.addCell(labelP_unit_i);
                ws.addCell(labelP_num_i);
                ws.addCell(labelP_type_i);
                ws.addCell(labelP_mode_i);
                ws.addCell(labelP_vouno_i);
                ws.addCell(labelP_prices_i);
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
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ps = null;
    }
}
