package com.sparta.realestatefeed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartRequestDto {

    private String apartName;
    private String address;
    private String area;
    private Long salePrice;
    private Boolean isSaled;
    private Long userId; // 사용자 ID 연관 처리?
}
