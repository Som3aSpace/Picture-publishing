package com.yeshtery.picturepublishing.services;

import com.yeshtery.picturepublishing.model.dto.ImageDto;
import com.yeshtery.picturepublishing.model.dto.ResponseTemplete;
import com.yeshtery.picturepublishing.model.entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    public ImageDto uploadImage(ImageDto image, MultipartFile file) throws IOException;
    public List<ImageDto> getAllImagesByStatus(String status);
    public ResponseTemplete<ImageDto> getImage(int id);
    public ResponseTemplete<ImageDto> updateImage(int id, String status);

}
