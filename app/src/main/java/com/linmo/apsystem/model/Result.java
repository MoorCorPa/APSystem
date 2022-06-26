package com.linmo.apsystem.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Result {

    private String code, msg, desc;
    private double face_distances, time_sum;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getFace_distances() {
        return face_distances;
    }

    public void setFace_distances(double face_distances) {
        this.face_distances = face_distances;
    }

    public double getTime_sum() {
        return time_sum;
    }

    public void setTime_sum(double time_sum) {
        this.time_sum = time_sum;
    }
}
