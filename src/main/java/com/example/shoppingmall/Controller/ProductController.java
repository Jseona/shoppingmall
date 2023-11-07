package com.example.shoppingmall.Controller;

import com.example.shoppingmall.DTO.ProductDTO;
import com.example.shoppingmall.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //목록
    @GetMapping("/product/list")
    public String listPage(Model model) throws Exception {
        List<ProductDTO> productDTOS = productService.list();

        model.addAttribute("productDTOS", productDTOS);
        return "product/list";
    }

    //상세보기
    @GetMapping("/product/detail")
    public String detailPage(int id, Model model) throws Exception {
        ProductDTO productDTO = productService.detail(id);

        model.addAttribute("productDTO", productDTO);
        return "product/detail";
    }

    //삽입
    @GetMapping("/product/register")
    public String registerPage(Model model) throws Exception {
        ProductDTO productDTO = new ProductDTO();  //검증을 위한 빈 DTO

        model.addAttribute("productDTO", productDTO);
        return "product/register";
    }
    @PostMapping("/product/register")
    public String registerProc(@Valid ProductDTO productDTO,
                               MultipartFile imgFile,
                               BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return "/product/register";
        }

        productService.register(productDTO, imgFile);  //신규등록 처리

        return "redirect:/product/list";
    }

    //수정
    @GetMapping("/product/modify")
    public String modifyPage(int id, Model model) throws Exception {
        ProductDTO productDTO = productService.detail(id);

        model.addAttribute("productDTO", productDTO);
        return "product/modify";
    }
    @PostMapping("/product/modify")
    public String modifyProc(ProductDTO productDTO, MultipartFile imgFile) throws Exception {
        productService.modify(productDTO, imgFile);

        return "redirect:/product/list";
    }

    //삭제
    @GetMapping("/product/remove")
    public String removeProc(int id) throws Exception {
        productService.remove(id);

        return "redirect:/product/list";
    }

    //사용자 목록
    @GetMapping("/product/productlist")
    public String productListPage(Model model) throws Exception {
        List<ProductDTO> productDTOS = productService.list();

        model.addAttribute("productDTOS", productDTOS);
        return "product/productlist";
    }

    //사용자 상세보기
    @GetMapping("/product/productdetail")
    public String productDetailPage(int id, Model model) throws Exception {
        ProductDTO productDTO = productService.detail(id);

        model.addAttribute("productDTO", productDTO);
        return "product/productdetail";
    }
}
