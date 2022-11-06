package com.newsnow.imagewrapper.service;

import com.newsnow.imagewrapper.domain.Task;
import com.newsnow.imagewrapper.repository.TaskRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResizeImageService {

    @Autowired
    private TaskRepository taskRepository;
    @Value("${baseUrl}")
    String baseUrl = "";
    @Value("${basePath}")
    String basePath = "";
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
    public Task resizeTask(InputStream inputStream,String imageName, Integer width, Integer height) throws IOException {
        Path baseFolder = Path.of(basePath);
        var newName = imageName + "-" + width + "-" + height + ".png";
        var url = baseUrl + newName;
        Task task = new Task.TaskBuilder()
                .url(url)
                .width(width)
                .height(height)
                .fileName(newName)
                .md5("asdfasdfas").build();


        File file = new File(baseFolder.toAbsolutePath() + File.separator + newName);

        Thumbnails.of(inputStream)
                .size(width, height)
                .keepAspectRatio(false)
                .outputFormat("png")
                .toFile(file);

        return taskRepository.create(task);
    }

    private String calculateMd5(InputStream inputStream){
//        byte[] bytesOfMessage = yourString.getBytes("UTF-8");
//
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        byte[] theMD5digest = md.digest(bytesOfMessage);
        return null;
    }

    public Optional<Task> searchTask(UUID taskid) {
        return taskRepository.selectTaskById(taskid);
    }
}
