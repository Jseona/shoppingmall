package com.example.shoppingmall.DTO;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//원래는 관리자용 DTO와 이용자용 DTO를 따로 만들어서 관리해야 함.
public class ProductDTO {
    private Integer pid;            //번호

    @NotEmpty(message = "상품명은 생략할 수 없습니다.")
    private String product;         //상품명

    private String detail;          //상품설명
    private Integer quantity;       //재고량
    private Integer price;          //가격
    private Integer sale;           //세일여부
    private Integer state;          //신상품여부
    private String img;             //상품이미지 파일명
    private LocalDateTime regDate;  //등록일
    private LocalDateTime modDate;  //수정일
}
