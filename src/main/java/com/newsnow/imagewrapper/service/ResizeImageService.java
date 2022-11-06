package com.newsnow.imagewrapper.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
public class ResizeImageService {

    public BufferedImage resizeBlocking(InputStream inputStream, Integer width, Integer height) throws IOException {

        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(width);
        Objects.requireNonNull(height);

        return Thumbnails.of(inputStream)
                .size(width, height)
                .keepAspectRatio(false)
                .outputFormat("png")
                .asBufferedImage();

    }
}
