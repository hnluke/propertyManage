package com.pojo;

import java.io.Serializable;
import java.util.Date;

public class AllAssetsItem implements Serializable {

    public final static long serialVersionUID = 100;
    private Integer ad_id;
    private String ad_cardcode;                     // 卡片编号
    private String ad_serial;                       // 序列号
    private String ad_code;                         // 资产编号
    private String ad_avno;                         // 凭证号
    private Integer ad_num;                         // 数量
    private String asty_name;                       // 资产类别
    private String ass_name;                        // 资产名称
    private String ass_model;                       // 规格型号
    private String ass_fincode ;                    // 财务编码
    private String ass_unit;                        // 单位
    private Integer ass_store = 0;                  // 库存
    private Integer av_finnum = 0;                  // 入帐数量
    private Date av_findate = new Date();           // 入帐日期
    private Date av_insttime = new Date();          // 入库日期
    private String u_name;                          // 用户名
    private String ad_status;                       // 资产状态
    private Double ass_prices;                      // 总价格
    private Integer ass_num;                        // 资产数
    private Double ad_price;                        // 单价

    public String getAd_cardcode() {
        return ad_cardcode;
    }

    public void setAd_cardcode(String ad_cardcode) {
        this.ad_cardcode = ad_cardcode;
    }

    public String getAd_serial() {
        return ad_serial;
    }

    public void setAd_serial(String ad_serial) {
        this.ad_serial = ad_serial;
    }

    public String getAd_code() {
        return ad_code;
    }

    public void setAd_code(String ad_code) {
        this.ad_code = ad_code;
    }

    public String getAd_avno() {
        return ad_avno;
    }

    public void setAd_avno(String ad_avno) {
        this.ad_avno = ad_avno;
    }


    public String getAsty_name() {
        return asty_name;
    }

    public void setAsty_name(String asty_name) {
        this.asty_name = asty_name;
    }

    public String getAss_name() {
        return ass_name;
    }

    public void setAss_name(String ass_name) {
        this.ass_name = ass_name;
    }

    public String getAss_model() {
        return ass_model;
    }

    public void setAss_model(String ass_model) {
        this.ass_model = ass_model;
    }

    public String getAss_fincode() {
        return ass_fincode;
    }

    public void setAss_fincode(String ass_fincode) {
        this.ass_fincode = ass_fincode;
    }

    public String getAss_unit() {
        return ass_unit;
    }

    public void setAss_unit(String ass_unit) {
        this.ass_unit = ass_unit;
    }

    public Integer getAss_store() {
        return ass_store;
    }

    public void setAss_store(Integer ass_store) {
        this.ass_store = ass_store;
    }

    public Integer getAv_finnum() {
        return av_finnum;
    }

    public void setAv_finnum(Integer av_finnum) {
        this.av_finnum = av_finnum;
    }

    public Date getAv_findate() {
        return av_findate;
    }

    public void setAv_findate(Date av_findate) {
        this.av_findate = av_findate;
    }

    public Date getAv_insttime() {
        return av_insttime;
    }

    public void setAv_insttime(Date av_insttime) {
        this.av_insttime = av_insttime;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public Integer getAd_num() {
        return ad_num;
    }

    public void setAd_num(Integer ad_num) {
        this.ad_num = ad_num;
    }

    public String getAd_status() {
        return ad_status;
    }

    public void setAd_status(String ad_status) {
        this.ad_status = ad_status;
    }

    public Integer getAd_id() {
        return ad_id;
    }

    public void setAd_id(Integer ad_id) {
        this.ad_id = ad_id;
    }

    public Double getAss_prices() {
        return ass_prices;
    }

    public void setAss_prices(Double ass_prices) {
        this.ass_prices = ass_prices;
    }

    public Integer getAss_num() {
        return ass_num;
    }

    public void setAss_num(Integer ass_num) {
        this.ass_num = ass_num;
    }

    public Double getAd_price() {
        return ad_price;
    }

    public void setAd_price(Double ad_price) {
        this.ad_price = ad_price;
    }
}
