package com.sparta.realestatefeed.dto;

import com.sparta.realestatefeed.entity.Apart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApartResponseDto {

    private Long id;
    private String apartName;
    private String address;
    private String area;
    private Long salePrice;
    private Boolean isSaled;
    private LocalDateTime modifiedAt;
    private Long userId;

    public ApartResponseDto(Apart apart) {
        this.id = apart.getId();
        this.apartName = apart.getApartName();
        this.address = apart.getAddress();
        this.area = apart.getArea();
        this.salePrice = apart.getSalePrice();
        this.isSaled = apart.getIsSaled();
        this.modifiedAt = apart.getModifiedAt();
        this.userId = apart.getUser().getId();
    }
}
