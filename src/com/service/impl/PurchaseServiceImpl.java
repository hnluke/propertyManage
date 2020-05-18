package com.service.impl;

import com.dao.impl.PurchaseDaoImpl;
import com.db.DBConnection;
import com.pojo.Purchase;
import com.service.IPurchaseService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseServiceImpl implements IPurchaseService {

    Connection conn = null;
    PurchaseDaoImpl pdl = null;
    public PurchaseServiceImpl(Connection conn) {
        this.conn = conn;
        pdl = new PurchaseDaoImpl(conn);
    }

    /**
     * @author Luke
     * 查询采购数据
     * @param purchase 采购单号 如果输入""则代表查询所有采购数据
     * @return
     */
    @Override
    public List<Purchase> getPurchase(String purchase) {
        List<Purchase> list = new ArrayList<Purchase>();
        list =  pdl.findPurchaseByPCode(purchase);
        //closeConnection();
        return list;
    }

    /**
     * 从Excel文件中导入数据至采购数据表
     * @author Luke
     * @param path Excel的文件路径
     * @return
     */
    @Override
    public boolean importExcelToDB(String path) {
        boolean flag = false;
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Purchase> list = new ArrayList<Purchase>();
        list = pdl.getAllByExcel(path);
        for(Purchase purchase : list) {
            if(!pdl.isExists(purchase.getP_code())) {
                pdl.insertPurchase(purchase);
            }else {
                pdl.updatePurchaseByPCode(purchase.getP_code(), purchase);
            }

        }
        flag = true;
        return flag;
    }

    /**
     * 将数据导出到Excel报表
     * @author Luke
     * @param list 采购对象集合
     * @param path 文件路径
     * @return
     */
    @Override
    public boolean exportReport(List<Purchase> list, String path) {
        boolean flag = false;
        flag = pdl.exportToExcel(list, path);
        return flag;
    }


    //    private void closeConnection() {
//        try {
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
