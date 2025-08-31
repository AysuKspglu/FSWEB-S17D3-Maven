package com.workintech.zoo.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Koala {
    private Integer id;     // <— Long yerine Integer
    private String name;
    private Double weight;
    private Double sleepHour; // test double bekliyor
    private String gender;
}
