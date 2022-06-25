package com.linmo.apsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import retrofit2.http.Body;

@Data
public class RequestBody {
    String personId, base64Data;

    public RequestBody(String personId, String base64Data) {
        this.personId = personId;
        this.base64Data = base64Data;
    }
}
