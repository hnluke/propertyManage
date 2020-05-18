package com.pojo;

import java.io.Serializable;

public class Asserts_type implements Serializable {
    public final static long serialVersionUID = 100L;

    private Integer asty_id;
    private String asty_name;

    public Integer getAsty_id() {
        return asty_id;
    }

    public void setAsty_id(Integer asty_id) {
        this.asty_id = asty_id;
    }

    public String getAsty_name() {
        return asty_name;
    }

    public void setAsty_name(String asty_name) {
        this.asty_name = asty_name;
    }
}
