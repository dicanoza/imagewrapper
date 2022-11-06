package com.newsnow.imagewrapper.service;

import com.newsnow.imagewrapper.domain.Task;
import com.newsnow.imagewrapper.repository.TaskRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

@Service
public class ResizeImageService {

    @Autowired
    private TaskRepository taskRepository;
    @Value("${baseUrl}")
    private String baseUrl;
    @Value("${basePath}")
    private String basePath;

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

    public Task resizeTask(InputStream inputStream, String imageName, Integer width, Integer height) throws IOException {
        Path baseFolder = Path.of(basePath);
        var newName = imageName + "-" + width + "-" + height + ".png";
        var url = baseUrl + newName;

        File oldFile = new File(baseFolder.toAbsolutePath() + File.separator + "tmp" + File.separator + imageName);
        File newFile = new File(baseFolder.toAbsolutePath() + File.separator + newName);

        Files.copy(
                inputStream,
                oldFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        IOUtils.closeQuietly(inputStream);

        try (InputStream secondStream = new FileInputStream(oldFile)) {
            Task task = new Task.TaskBuilder()
                    .url(url)
                    .width(width)
                    .height(height)
                    .fileName(newName)
                    .md5(md5Hex(secondStream)).build();


            Thumbnails.of(oldFile)
                    .size(width, height)
                    .keepAspectRatio(false)
                    .outputFormat("png")
                    .toFile(newFile);

            return taskRepository.create(task);
        }
    }

    public Optional<Task> searchTask(UUID taskid) {
        return taskRepository.selectTaskById(taskid);
    }
}
