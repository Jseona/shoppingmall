package com.example.shoppingmall.Service;

import com.example.shoppingmall.DTO.ProductDTO;
import com.example.shoppingmall.Entity.ProductEntity;
import com.example.shoppingmall.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
//기본 CRUD 구현, 메소드명은 파일명과 동일하게 작성
public class ProductService {
    //파일이 저장될 경로
    @Value("${imgUploadLocation}")
    private String imgUploadLocation;

    //작업할 레포지토리
    private final ProductRepository productRepository;
    //파일저장을 위한 클래스
    private final FileService fileService;

    //변환작업을 위한
    private final ModelMapper modelMapper = new ModelMapper();

    //삭제(Delete)
    public void remove(int id) throws Exception {
        //물리적 위치에 저장된 이미지를 삭제
        //(레코드를 삭제해 버리면 이미지 이름도 없어지기 때문에 파일 삭제 불가능, 반드시 파일 먼저 삭제하고 레코드 삭제하기)
        ProductEntity read = productRepository.findById(id).orElseThrow();  //조회->저장
        fileService.deleteFile(imgUploadLocation, read.getImg());

        //레코드 삭제
        productRepository.deleteById(id);
    }
    //개별조회(Read)
    public ProductDTO detail(int id) throws Exception {
        ProductEntity read = productRepository.findById(id).orElseThrow();  //조회->저장

        ProductDTO productDTO = modelMapper.map(read, ProductDTO.class);

        return productDTO;
    }
    //전체조회(Read)
    public List<ProductDTO> list() throws Exception {
        List<ProductEntity> productEntities = productRepository.findAll();

        List<ProductDTO> productDTOS = Arrays.asList(
                modelMapper.map(productEntities, ProductDTO[].class));

        return productDTOS;
    }
    //삽입(Create)
    public void register(ProductDTO productDTO, MultipartFile imgFile) throws Exception {
        String originalFileName = imgFile.getOriginalFilename();  //저장할 파일명
        String newFileName = "";  //새로 만든 파일명
        if (originalFileName != null) {  //파일이 존재하면
            newFileName = fileService.uploadFile(imgUploadLocation,
                    originalFileName, imgFile.getBytes());
        }
        productDTO.setImg(newFileName);  //새로운 파일명을 재등록

        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);

        productRepository.save(productEntity);
    }
    //수정(Update)
    public void modify(ProductDTO productDTO, MultipartFile imgFile) throws Exception {
        //기존파일 삭제
        //개별조회
        ProductEntity read = productRepository.findById(productDTO.getPid()).orElseThrow();  //조회->저장

        String deleteFile = read.getImg();

        //삽입
        String originalFileName = imgFile.getOriginalFilename();  //저장할 파일명
        String newFileName = "";  //새로 만든 파일명

        if (originalFileName.length() != 0) {  //파일이 존재하면
            if (deleteFile.length() != 0) {
                //기존 파일 삭제
                fileService.deleteFile(imgUploadLocation, deleteFile);
            }

            //새로운 파일 업로드
            newFileName = fileService.uploadFile(imgUploadLocation,
                    originalFileName, imgFile.getBytes());
            productDTO.setImg(newFileName);  //변경 이미지를 업로드 시 새로운 파일명을 데이터베이스에 재등록
        }

        productDTO.setPid(read.getPid());


        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);

        productRepository.save(productEntity);
    }
}
