package com.linmo.apsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import retrofit2.http.Body;

@Data
@ToString
public class RequestBody {
    String personId, imgdata;

    public RequestBody(String personId, String imgdata) {
        this.personId = personId;
        this.imgdata = imgdata;
    }
}
