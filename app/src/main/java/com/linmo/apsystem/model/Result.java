package com.linmo.apsystem.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Result {

    private String code, msg, desc;
    private double face_distances, time_sum;
}
