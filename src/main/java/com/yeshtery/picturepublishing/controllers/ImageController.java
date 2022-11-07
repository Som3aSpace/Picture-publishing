package com.yeshtery.picturepublishing.controllers;

import com.yeshtery.picturepublishing.model.dto.ImageDto;
import com.yeshtery.picturepublishing.model.dto.ResponseTemplete;
import com.yeshtery.picturepublishing.model.enums.Enums;
import com.yeshtery.picturepublishing.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Value("${image.uploadDirectory}")
    private String UPLOAD_DIRECTORY;
    @Autowired
    private ImageService imageService;


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/upload")
    public ResponseTemplete<ImageDto> uploadImage(@RequestParam("category") String category
            ,@RequestParam("description") String description , @RequestParam("image")  MultipartFile file) throws IOException {
        long fileSize = file.getSize();
        String fileType = file.getContentType();
        if(fileSize > 2e+6 || !(Objects.equals(fileType, Enums.IMG_TYPE_JPG.toString()) ||
                Objects.equals(fileType, Enums.IMG_TYPE_PNG.toString()) ||
                Objects.equals(fileType, Enums.IMG_TYPE_GIF.toString()))) {
            return new ResponseTemplete<>(false,"file should be less than 2MB image of types JPEG/PNG/GIF",null);
        }
        File theDir = new File(System.getProperty("user.dir")+UPLOAD_DIRECTORY);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        ImageDto imageDto = new ImageDto();
        imageDto.setCategory(category);
        imageDto.setDescription(description);
        return new ResponseTemplete<>(true,"your image has been successfully uploaded",imageService.uploadImage(imageDto,file));
    }

    @GetMapping("/list")
    public List<ImageDto> getAllApprovedImages(){
        return imageService.getAllImagesByStatus(Enums.STATUS_STATE_ACCEPTED.toString());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/pending")
    public List<ImageDto> getAllPendingImages(){
        return imageService.getAllImagesByStatus(Enums.STATUS_STATE_PENDING.toString());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseTemplete<ImageDto> getImage(@PathVariable int id){
        return imageService.getImage(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseTemplete<ImageDto> acceptImageStatus(@PathVariable int id){
        return imageService.updateImage(id,Enums.STATUS_ACTION_ACCEPT.toString());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseTemplete<ImageDto> rejectImageStatus(@PathVariable int id){
        return imageService.updateImage(id,Enums.STATUS_ACTION_REJECT.toString());
    }

}
