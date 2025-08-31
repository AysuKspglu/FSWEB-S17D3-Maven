package com.workintech.zoo.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Koala {
    private Integer id;
    private String name;
    private Double sleepHour; // ÖNCE sleepHour
    private Double weight;    // SONRA weight
    private String gender;
}
