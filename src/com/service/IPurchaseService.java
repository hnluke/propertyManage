package com.service;

import com.pojo.Purchase;

import java.util.List;

public interface IPurchaseService {
    /**
     * 查询采购数据
     * @param purchase 采购单号 如果输入""则代表查询所有采购数据
     * @return
     */
    public List<Purchase> getPurchase(String purchase);

    /**
     * 从Excel文件中导入数据到采购数据表
     * @param path Excel的文件路径
     * @return
     */
    public boolean importExcelToDB(String path);

    /**
     * 将数据导出到Excel表
     * @param list
     * @param path
     * @return
     */
    public boolean exportReport(List<Purchase> list, String path);
}
