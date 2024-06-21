package com.sparta.realestatefeed.entity;


import com.sparta.realestatefeed.dto.ApartRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Apart extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apart_id")
    private Long id;

    @Column(name = "apart_name", length = 50)
    private String apartName;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "area", length = 255)
    private String area;

    @Column(name = "sale_price")
    private Long salePrice;

    @Column(name = "is_saled")
    private Boolean isSaled;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Apart(ApartRequestDto requestDto, User user) {
        this.apartName = requestDto.getApartName();
        this.address = requestDto.getAddress();
        this.area = requestDto.getArea();
        this.salePrice = requestDto.getSalePrice();
        this.isSaled = requestDto.getIsSaled();
        this.user = user;
    }

    public void update(ApartRequestDto requestDto) {
        this.apartName = requestDto.getApartName();
        this.address = requestDto.getAddress();
        this.area = requestDto.getArea();
        this.salePrice = requestDto.getSalePrice();
        this.isSaled = requestDto.getIsSaled();
    }
}
