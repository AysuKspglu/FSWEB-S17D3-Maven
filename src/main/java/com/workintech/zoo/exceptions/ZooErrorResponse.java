package com.workintech.zoo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZooErrorResponse {
    // test: new ZooErrorResponse(404, "Not Found", now)
    private int status;
    private String message;
    private long timestamp;
}
