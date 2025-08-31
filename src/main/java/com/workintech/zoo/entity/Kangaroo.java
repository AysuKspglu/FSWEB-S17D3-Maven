package com.workintech.zoo.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kangaroo {
    private Integer id;     // <— Long yerine Integer
    private String name;
    private Double height;
    private Double weight;
    private String gender;
    private Boolean isAggressive; // getIsAggressive() üretilecek
}
