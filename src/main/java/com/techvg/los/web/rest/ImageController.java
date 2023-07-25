package com.techvg.los.web.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @Value("${upload.path}")
    private String uploadPath;

    @RequestMapping(value = "/api/uploads/files/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String filename) throws IOException {
        InputStream inputStream = null;

        Path filepath = Paths.get(uploadPath).resolve(filename);

        Resource resource = new UrlResource(filepath.toUri());

        inputStream = resource.getInputStream();

        System.out.println(filename);

        MediaType contentType = filename.contains(".png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;

        if (filename.contains(".png")) {
            contentType = MediaType.IMAGE_PNG;
        } else if (filename.contains(".jpeg")) {
            contentType = MediaType.IMAGE_JPEG;
        } else if (filename.contains(".pdf")) {
            contentType = MediaType.APPLICATION_PDF;
        } else if (filename.contains(".jpg")) {
            contentType = MediaType.IMAGE_JPEG;
        }

        return ResponseEntity.ok().contentType(contentType).body(new InputStreamResource(inputStream));
    }
}
