package com.newsnow.imagewrapper.api;

import com.newsnow.imagewrapper.service.ResizeImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class ResizeImageController {

    private final ResizeImageService resizeImageService;

    public ResizeImageController(@Autowired ResizeImageService resizeImageService) {
        this.resizeImageService = resizeImageService;
    }

    @PostMapping(path = "/task", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InputStreamResource> resize(@RequestParam MultipartFile image,
                                                      @RequestParam Integer width,
                                                      @RequestParam Integer height) throws IOException {


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = resizeImageService.resizeBlocking(
                image.getInputStream(),
                width,
                height);
        ImageIO.write(bufferedImage, "png", outputStream);
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        MediaType contentType = MediaType.IMAGE_PNG;


        return ResponseEntity.ok()
                .contentType(contentType)
                .body(new InputStreamResource(inputStream));
    }

}