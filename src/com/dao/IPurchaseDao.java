package com.dao;

import com.pojo.Purchase;

import java.util.List;

public interface IPurchaseDao {

    /**
     * 依据采购单来查找采购数据表
     * @author Luke
     * @param p_code 采购单号
     * @return
     */
    public List<Purchase> findPurchaseByPCode(String p_code);

    /**
     * 依据采购表id来查找采购数据表
     * @author Luke
     * @param id
     * @return
     */

    public Purchase findPurchaseByPurId(Integer id);

    /**
     * 判断采购表中是否存在提供的采购单
     * @author Luke
     * @param p_code 采购单号
     * @return
     */
    public boolean isExists(String p_code);


    /**
     * 删除已入库的采购单
     * @author Luke
     * @param p_id 采购单id
     * @return
     */
    public boolean deletePurchaseByPCode(Integer p_id);

    /**
     * 依据采购单号向采购表插入数据
     * @author Luke
     * @param purchase 采购表对象
     * @return
     */
    public boolean insertPurchase(Purchase purchase);

    /**
     * 依据采购单号更新采购数据
     * @author Luke
     * @param p_code
     * @param purchase
     * @return
     */
    public boolean updatePurchaseByPCode(String p_code, Purchase purchase);

    /**
     * 将Excel数据转化为pojo对象的List
     * @author Luke
     * @param path 文件路径
     * @return 返回purchase数据表对象
     */
    public List<Purchase> getAllByExcel(String path);

    public boolean exportToExcel(List<Purchase> list, String path);



}
