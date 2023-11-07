package com.example.shoppingmall.Entity;

import lombok.*;

import javax.persistence.*;

@Entity

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "goods")
@SequenceGenerator(
        name = "goods_SEQ", sequenceName = "goods_SEQ",
        initialValue = 1, allocationSize = 1
)
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "goods_SEQ")
    private Integer pid;        //번호

    @Column(name = "product", length = 100)
    private String product;     //상품명

    @Column(name = "detail", length = 200)
    private String detail;      //상품설명

    @Column(name = "quantity")
    private Integer quantity;   //재고량

    @Column(name = "price")
    private Integer price;      //가격

    @Column(name = "sale")
    private Integer sale;       //세일여부

    @Column(name = "state")
    private Integer state;      //신상품여부

    @Column(name = "img")
    private String img;         //상품이미지 파일명
}
