package com.pojo;

import java.io.Serializable;
import java.util.Date;

public class Assets_vou implements Serializable {
    public final static double serialVersionUID = 100L;

    private Integer av_id;
    private String av_no;
    private Integer av_assid;
    private Integer av_finnum;
    private Date av_findate = new Date();
    private Date av_insttime = new Date();



    public Integer getAv_id() {
        return av_id;
    }

    public void setAv_id(Integer av_id) {
        this.av_id = av_id;
    }

    public String getAv_no() {
        return av_no;
    }

    public void setAv_no(String av_no) {
        this.av_no = av_no;
    }

    public Integer getAv_assid() {
        return av_assid;
    }

    public void setAv_assid(Integer av_assid) {
        this.av_assid = av_assid;
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


}
