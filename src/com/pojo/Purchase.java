package com.pojo;

import java.io.Serializable;
import java.util.Date;

public class Purchase implements Serializable {
    public final static long serialVersionUID = 100L;

    private Integer p_id;
    private String p_code;
    private String p_assname;
    private Integer p_num;
    private String p_type;
    private String p_model;
    private String p_vouno;
    private String p_unit;
    private Double p_prices = 0.0D;


    public Integer getP_id() {
        return p_id;
    }

    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public String getP_assname() {
        return p_assname;
    }

    public void setP_assname(String p_assname) {
        this.p_assname = p_assname;
    }

    public Integer getP_num() {
        return p_num;
    }

    public void setP_num(Integer p_num) {
        this.p_num = p_num;
    }

    public String getP_type() {
        return p_type;
    }

    public void setP_type(String p_type) {
        this.p_type = p_type;
    }

    public String getP_model() {
        return p_model;
    }

    public void setP_model(String p_model) {
        this.p_model = p_model;
    }

    public String getP_vouno() {
        return p_vouno;
    }

    public void setP_vouno(String p_vouno) {
        this.p_vouno = p_vouno;
    }

    public String getP_unit() {
        return p_unit;
    }

    public void setP_unit(String p_unit) {
        this.p_unit = p_unit;
    }

    public Double getP_prices() {
        return p_prices;
    }

    public void setP_prices(Double p_prices) {
        this.p_prices = p_prices;
    }
}
