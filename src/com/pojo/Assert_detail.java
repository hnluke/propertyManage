package com.pojo;

import java.io.Serializable;

public class Assert_detail implements Serializable {
    public final static long serialVersionUID = 100L;
    private Integer ad_id;
    private String ad_cardcode;                         // 卡片编号
    private String ad_serial;                           // 序列号
    private String ad_code ;                            // 资产编号
    private Integer ad_avid;                            // 凭证id号（与【资产凭证表】关联的字段）
    private String ad_avno ;                            // 凭证号
    private Integer ad_num = 0;                         // 数量
    private Integer ad_uid ;                            // 用户id（与【用户表】关联的字段）
    private String ad_status;                           // 资产状态
    private String ad_purcode;                          //  采购单号
    private Double ad_price = 0.0D;                     // 单价

    public Integer getAd_id() {
        return ad_id;
    }

    public void setAd_id(Integer ad_id) {
        this.ad_id = ad_id;
    }

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

    public Integer getAd_avid() {
        return ad_avid;
    }

    public void setAd_avid(Integer ad_avid) {
        this.ad_avid = ad_avid;
    }

    public String getAd_avno() {
        return ad_avno;
    }

    public void setAd_avno(String ad_avno) {
        this.ad_avno = ad_avno;
    }

    public Integer getAd_num() {
        return ad_num;
    }

    public void setAd_num(Integer ad_num) {
        this.ad_num = ad_num;
    }

    public Integer getAd_uid() {
        return ad_uid;
    }

    public void setAd_uid(Integer ad_uid) {
        this.ad_uid = ad_uid;
    }

    public String getAd_status() {
        return ad_status;
    }

    public void setAd_status(String ad_status) {
        this.ad_status = ad_status;
    }

    public String getAd_code() {
        return ad_code;
    }

    public void setAd_code(String ad_code) {
        this.ad_code = ad_code;
    }

    public String getAd_purcode() {
        return ad_purcode;
    }

    public void setAd_purcode(String ad_purcode) {
        this.ad_purcode = ad_purcode;
    }

    public Double getAd_price() {
        return ad_price;
    }

    public void setAd_price(Double ad_price) {
        this.ad_price = ad_price;
    }
}
