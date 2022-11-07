package com.yeshtery.picturepublishing.services.serviceImpl;

import com.yeshtery.picturepublishing.model.dto.ImageDto;
import com.yeshtery.picturepublishing.model.dto.ResponseTemplete;
import com.yeshtery.picturepublishing.model.entity.ImageEntity;
import com.yeshtery.picturepublishing.model.entity.StatusEntity;
import com.yeshtery.picturepublishing.model.entity.UserEntity;
import com.yeshtery.picturepublishing.model.enums.Enums;
import com.yeshtery.picturepublishing.repositories.ImageRepository;
import com.yeshtery.picturepublishing.repositories.StatusRepository;
import com.yeshtery.picturepublishing.repositories.UserRepository;
import com.yeshtery.picturepublishing.services.ImageService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Value("${image.uploadDirectory}")
    private String UPLOAD_DIRECTORY;

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ImageDto uploadImage(ImageDto imageDto, MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        ImageEntity imageEntity = new ImageEntity();
        StatusEntity statusEntity = statusRepository.getByStatus(Enums.STATUS_STATE_PENDING.toString());
        UserEntity userEntity = userRepository.getByUserName(currentPrincipalName);

        File fTemp = File.createTempFile(currentPrincipalName,file.getOriginalFilename());
        String fileName = fTemp.getName();
        fTemp.deleteOnExit();
        String filePath = Paths.get(System.getProperty("user.dir")+UPLOAD_DIRECTORY, fileName).toString();


        try{
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            stream.write(file.getBytes());
            stream.close();
        }
        catch (IOException e){
            throw new IOException("Could not save image file: " + fileName, e);
        }

        imageEntity.setCategory(imageDto.getCategory());
        imageEntity.setDescription(imageDto.getDescription());
        imageEntity.setFilePath(filePath);
        imageEntity.setStatusEntity(statusEntity);
        imageEntity.setUserEntity(userEntity);
        ImageEntity result = imageRepository.save(imageEntity);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(result,ImageDto.class);
    }

    @Override
    public List<ImageDto> getAllImagesByStatus(String status) {
        StatusEntity statusEntity = statusRepository.getByStatus(status);
        List<ImageEntity> imageEntities = imageRepository.getAllByStatusEntity(statusEntity);
        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<ImageDto>>(){}.getType();
        return modelMapper.map(imageEntities,listType);
    }

    @Override
    public ResponseTemplete<ImageDto> getImage(int id) throws EntityNotFoundException{
        ResponseTemplete<ImageDto> responseTemplete = new ResponseTemplete<>();

        try {
            ImageDto imageDto = new ModelMapper().map(imageRepository.getById(id),ImageDto.class);
            responseTemplete.setStatus(true);
            responseTemplete.setData(imageDto);
        }
        catch (Exception e){
            responseTemplete.setStatus(false);
            responseTemplete.setMessage(e.getMessage());
        }
        return responseTemplete;
    }

    @Override
    public ResponseTemplete<ImageDto> updateImage(int id, String status) throws EntityNotFoundException{
        ResponseTemplete<ImageDto> responseTemplete = new ResponseTemplete<>();
        ModelMapper modelMapper = new ModelMapper();
        try {
            ImageEntity imageEntity = imageRepository.getById(id);
            ImageDto imageDto = new ImageDto();
            if(status.equals(Enums.STATUS_ACTION_ACCEPT.toString())){
                imageEntity.setStatusEntity(statusRepository.getByStatus(Enums.STATUS_STATE_ACCEPTED.toString()));
                imageDto = modelMapper.map(imageRepository.save(imageEntity),ImageDto.class);
                responseTemplete.setMessage("The image is approved");
            }
            else if(status.equals(Enums.STATUS_ACTION_REJECT.toString())){
                if(imageEntity.getFilePath() != null){
                    boolean isExist = Files.deleteIfExists(Paths.get(imageEntity.getFilePath()));
                    imageEntity.setStatusEntity(statusRepository.getByStatus(Enums.STATUS_STATE_REJECTED.toString()));
                    imageEntity.setFilePath(null);
                    imageDto = modelMapper.map(imageRepository.save(imageEntity),ImageDto.class);
                    if(isExist){
                        responseTemplete.setMessage("The image is rejected");
                    }
                    else {
                        responseTemplete.setMessage("The image is rejected,however the file wasn't exist");
                    }
                }
                else {
                    responseTemplete.setMessage("This image is deleted before");
                }

            }
            else {
                responseTemplete.setMessage("no specific process had chosen please revise controller");
            }
            responseTemplete.setStatus(true);
            responseTemplete.setData(imageDto);
        }
        catch (Exception e){
            responseTemplete.setStatus(false);
            responseTemplete.setMessage(e.getMessage());
        }
        return responseTemplete;
    }

}
