package com.pojo;

import java.io.Serializable;

public class Assets implements Serializable {
    public final static double serialVersionUID = 100L;
    private Integer ass_id;
    private String ass_name;
    private String ass_model;
    private Integer ass_tyid;
    private String ass_fincode;
    private String ass_unit;
    private Integer ass_store = 0;
    private Double ass_prices = 0.0D;
    private Integer ass_num;
    public Integer getAss_id() {
        return ass_id;
    }

    public void setAss_id(Integer ass_id) {
        this.ass_id = ass_id;
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

    public Integer getAss_tyid() {
        return ass_tyid;
    }

    public void setAss_tyid(Integer ass_tyid) {
        this.ass_tyid = ass_tyid;
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

    public Double getAss_prices() {
        return ass_prices;
    }

    public void setAss_prices(Double ass_prices) {
        this.ass_prices = ass_prices;
    }

    public Integer getAss_num() {
        return ass_num;
    }

    public void setAss_num(Integer ass_assnum) {
        this.ass_num = ass_assnum;
    }
}
