package com.service.impl;

import com.dao.impl.*;
import com.pojo.*;
import com.service.IAssetsService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssetsServiceImpl implements IAssetsService {
    Connection conn = null;
    PurchaseDaoImpl purchaseDao = null;
    Asserts_typeDaoImpl asserts_typeDao = null;
    AssetsDaoImpl assetsDao = null;
    Assets_vouDaoImpl assets_vouDao = null;
    Assert_detailDaoImpl assert_detailDao = null;
    QueryAssetsDaoImpl queryAssetsDao = null;

    public AssetsServiceImpl(Connection conn) {
        this.conn = conn;
        purchaseDao = new PurchaseDaoImpl(conn);
        asserts_typeDao = new Asserts_typeDaoImpl(conn);
        assetsDao = new AssetsDaoImpl(conn);
        assets_vouDao = new Assets_vouDaoImpl(conn);
        assert_detailDao = new Assert_detailDaoImpl(conn);
        queryAssetsDao = new QueryAssetsDaoImpl(conn);
    }

    /**
     * 采购入库
     * @param purchaseId 采购数据表id
     * @return 成功入库返回true, 否则返回false
     */
    @Override
    public boolean inStore(Integer purchaseId) {
        boolean flag = false;
        boolean trace = false;
        boolean autocommit = false;
        Purchase purchase = purchaseDao.findPurchaseByPurId(purchaseId);
        //Integer p_id = purchase.getP_id();
        String p_code = purchase.getP_code();
        String p_assname = purchase.getP_assname();
        Integer p_num = purchase.getP_num();
        String p_type = purchase.getP_type();
        String p_model = purchase.getP_model();
        String p_vouno = purchase.getP_vouno();
        String p_unit = purchase.getP_unit();
        Double p_prices = purchase.getP_prices();
        Double ad_price = p_prices / p_num;
        //资产类别表对象
        Asserts_type asserts_type= new Asserts_type();
        // 资产表对象
        Assets assets = new Assets();
        // 资产凭证表对象
        Assets_vou assets_vou = new Assets_vou();
        // 资产明细表对象
        Assert_detail assert_detail = new Assert_detail();
        try {
            // 获取初始化的提交方式
            autocommit = conn.getAutoCommit();
            // 设置自动提交为false, 开始事务
            conn.setAutoCommit(false);
            if(purchase != null) {
                // 1. 先判断 [资产类别] 表有没有相应的类别名称，如果没有则插入
                if((asserts_type = asserts_typeDao.findAssetstypeByTypename(p_type)) == null) {
                    asserts_type = new Asserts_type();
                    asserts_type.setAsty_name(p_type);
                    asserts_typeDao.insertAssetsType(asserts_type);
                }
                // 2. 然后判断 [资产表] 是否存在同资产名称，如果没有则插入，如果有的话，就更改库存数量

                if((assets = assetsDao.findAssetsByAss_name(p_assname)) == null) {
                        assets = new Assets();
                        assets.setAss_name(p_assname);
                        assets.setAss_model(p_model);
                        assets.setAss_fincode(p_vouno);
                        assets.setAss_unit(p_unit);
                        assets.setAss_store(p_num);
                        assets.setAss_num(p_num);
                        assets.setAss_prices(p_prices);

                    // 开始插入记录到资产表
                        assetsDao.insertAsserts(assets);
                }else{
                    // 如果有同一资产存在，则修改库存
                    assets.setAss_store(assets.getAss_store() + p_num);
                    assets.setAss_num(assets.getAss_num() + p_num);
                    assets.setAss_prices(assets.getAss_prices() + p_prices);
                    assetsDao.updateAssertsByAssName(assets.getAss_name(), assets);
                }
                // 3. 在[资产凭证]表里查询凭证号，如果没有此凭证号则插入相应的信息到资产凭证和资产明细表，否则不进行操作
                if (assets_vouDao.findAssetsVouByAvno(p_vouno) == null) {
                    // 再次查询[资产表]以获得资产id
                    conn.commit();

                    assets_vou = new Assets_vou();
                    assets_vou.setAv_no(p_vouno);
                    assets_vou.setAv_finnum(p_num);
                    assets_vou.setAv_findate(new Date());
                    assets_vou.setAv_insttime(new Date());
                    trace = assets_vouDao.insertAssetsVou(assets_vou);
                    // 重新查询【资产类别表】【资产表】【资产凭证表】来获出表的主键id来产生各种编码值
                    asserts_type = asserts_typeDao.findAssetstypeByTypename(p_type);
                    assets = assetsDao.findAssetsByAss_name(p_assname);
                    assets_vou = assets_vouDao.findAssetsVouByAvno(p_vouno);
                    for(int i= 0 ; i < p_num; i++) {
                        // 生成卡片编号
                        String ad_cardcode = autoCreateCode(assets, i);
                        // 生成序列号
                        String ad_serial = autoCreateCode("S", i);
                        // 生成资产编号
                        String ad_code = autoCreateCode(asserts_type, assets, assets_vou, i);
                        assert_detail.setAd_cardcode(ad_cardcode);
                        assert_detail.setAd_serial(ad_serial);
                        assert_detail.setAd_code(ad_code);
                        assert_detail.setAd_avid(assets_vou.getAv_id());
                        assert_detail.setAd_uid(null);
                        assert_detail.setAd_num(1);
                        assert_detail.setAd_status("在库(未确认)");
                        assert_detail.setAd_purcode(p_code);
                        assert_detail.setAd_price(ad_price);
                        trace = assert_detailDao.insertAssets(assert_detail);
                    }
                }else{
                    conn.rollback();
                }

                conn.commit();
                asserts_type = asserts_typeDao.findAssetstypeByTypename(p_type);
                assets = assetsDao.findAssetsByAss_name(p_assname);
                assets_vou = assets_vouDao.findAssetsVouByAvno(p_vouno);
                //conn.setAutoCommit(true);
                //开始更新外键关联的id字段
                //先找[资产类别表]，得到资产类别id,将它填入[资产表]形成关联
                //asserts_type = asserts_typeDao.findAssetstypeByTypename(p_type);
                if(asserts_type != null) {
                    Integer ass_tyid = asserts_type.getAsty_id();
                    if (assets != null) {

                        assets.setAss_tyid(ass_tyid);
                        // 将资产类别id填入资产表
                        assets.setAss_store(0);
                        assets.setAss_prices(0.0D);
                        assets.setAss_num(0);
                        trace = assetsDao.updateAssertsByAssName(p_assname, assets);

                        // 查找【资产表】id, 将它填入【资产凭证表】形成关联
                        if(assets_vou != null) {
                            Integer av_assid = assets.getAss_id();
                            assets_vou.setAv_assid(av_assid);
                            // 更新【资产凭证表】,写入资产表id与【资产表形成关联】
                            trace = assets_vouDao.updateAssetsVouByAvno(p_vouno, assets_vou);

                            Integer ad_avid = assets_vou.getAv_id();
                            // 更新【资产明细表】凭证号id, 与资产凭证表形成关联
                            trace =assert_detailDao.updateAssertsVouAdavidByPurcode(p_code, ad_avid);


                            flag = true;
                        }
                    }
                }
            }
            purchaseDao.deletePurchaseByPCode(purchaseId);
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                flag = false;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        // 恢复数据库的提交方式
        try {
            conn.setAutoCommit(autocommit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //closeConnection();
        return flag;
    }

    // 生成卡片编码
    private String autoCreateCode(Assets assets, int i) {
        return String.format("%04d", assets.getAss_id())
                + String.format("%07d", i);

    }

    //生成序列号
    private String autoCreateCode(String s, int i) {
        return s + String.format("%010d", i);
    }

    // 生成资产编号
    private String autoCreateCode(Asserts_type asserts_type, Assets assets, Assets_vou assets_vou, int i) {
        return String.format("%02d", asserts_type.getAsty_id())
                + String.format("%02d", assets.getAss_id())
                + String.format("%03d", assets_vou.getAv_assid())
                + String.format("%04d", i);
    }


    /**
     * 资产查询
     * @param from 字段名
     * @param to 值
     * @param flag 暂时保留
     * @param index 第几页
     * @return
     */
    @Override
    public List<AllAssetsItem> findAssets(String from, String to, String flag, int index) {
        List<AllAssetsItem> list = new ArrayList<AllAssetsItem>();

        list =  queryAssetsDao.findAssetsBy(from, to, flag, index);
        //closeConnection();
        return list;
    }
    /**
     * @author Luke
     * 财务确认
     * @param av_vou 凭证号
     * @return
     */
    @Override
    public String financeConfirm(String av_vou) {
        String str = "";
        Assets_vou assets_vou = assets_vouDao.findAssetsVouByAvno(av_vou);
        if(assets_vou != null) {
            if(assert_detailDao.updateAssertsVouByAdAvid(assets_vou.getAv_id(), av_vou)) {
                str = "确认成功";
            }else{
                str = "确认失败";
            }

        }else{
            str = "凭证号错误";
        }
        //closeConnection();
        return str;
    }

    /**
     * 资产异动
     * @param ad_id 资产明细id
     * @param ad_uid 用户表id
     * @param tages 异动的操作，包括"领用", "归还", "返修", "报废", "销帐"
     * @return
     */
    @Override
    public boolean assertsUse(Integer ad_id, Integer ad_uid, String tages) {
        boolean flag = false;
        try {
            conn.setAutoCommit(false);
            // 更新资产明细表
            assert_detailDao.updateAssertdetailStatusById(ad_id, ad_uid, tages);
            // 获得资产凭证id
            Assert_detail assert_detail = assert_detailDao.findAssetsDetailById(ad_id);
            // 得到资产凭证对象
            Assets_vou assets_vou = assets_vouDao.findAssetsVouById(assert_detail.getAd_avid());
            // 获得资产表对象
            Assets assets = assetsDao.findAssetsById(assets_vou.getAv_assid());
            // 更新资产表
            assetsDao.updateAssetsStoreById(assets, assert_detail.getAd_price(), tages);
            conn.commit();
            flag = true;
        } catch (SQLException e) {
            try {
                flag = false;
                conn.rollback();
            } catch (SQLException ex) {
                flag = false;
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        //closeConnection();
        return flag;
    }

    /**
     * 得到表记录数
     * @param field 字段名
     * @param val 值
     * @param flag 暂时保留
     * @param index 第几页
     * @return
     */
    @Override
    public int getAssetsCount(String field, String val, String flag, int index) {
        int recordCount = 0;
        recordCount =  queryAssetsDao.queryAssetsCount(field, val ,flag, index);
        //closeConnection();
        return recordCount;
    }

    /**
     * 导出资产数据到Excel表
     * @author Luke
     * @param list  资产数据列表
     * @param filePath 文件路径
     * @return
     */
    @Override
    public String exportAssetsToExcel(List<AllAssetsItem> list, String filePath) {
        String notice = "";
        //String basePath = PurchaseServlet.class.getClassLoader().getResource("//").getPath();
        String basePath = "e:\\tmp\\";
        if(list.size() < 1) {
            notice = "无数据";
        }else{
            if(queryAssetsDao.exportToExcel(list, basePath +filePath)) {

                notice = "导入成功, 请察看" + basePath + filePath;
            }else{
                notice = "导入失败";
            }
        }

        return notice;
    }

}
