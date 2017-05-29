package com.stjepano.website.modules.files.services;

import com.stjepano.website.components.WebsiteConfig;
import com.stjepano.website.modules.files.model.WebsiteFile;
import com.stjepano.website.modules.files.model.WebsiteFileInfo;
import com.stjepano.website.modules.files.model.WebsiteFilePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The file service handles all file operations for the website.
 */
@Service
public class FileService implements ResourceLoaderAware {

    @Autowired
    private WebsiteConfig websiteConfig;

    private ResourceLoader resourceLoader;

    /** Upload file to given directory */
    public void upload(MultipartFile file, WebsiteFilePath uploadPath) {


    }

    /** List all files in given directory */
    public List<WebsiteFile> list(WebsiteFilePath uploadPath) {
        Path path = path(uploadPath);
        try {
            return Files.list(path).map(p -> WebsiteFileInfo.createFromRealPath(websiteConfig.getUploadFolder(), p)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Can not list content of '" + path.toString() + "' directory!", e);
        }
    }

    /** Returns file info for give file, if file exists */
    public Optional<WebsiteFile> get(WebsiteFilePath uploadPath) {
        Path path = path(uploadPath);
        if (!Files.exists(path)) {
            return Optional.empty();
        }
        return Optional.of(WebsiteFileInfo.createFromRealPath(websiteConfig.getUploadFolder(), path));
    }

    /** Returns resource for given file if file exista */
    public Optional<Resource> getAsResource(WebsiteFilePath uploadPath) {
        Path path = path(uploadPath);
        if (!Files.exists(path)) {
            return Optional.empty();
        }
        return Optional.of(resourceLoader.getResource("file:" + path.toAbsolutePath().toString()));
    }

    /** Deletes specified files */
    public void delete(Set<WebsiteFilePath> files) {

    }

    /** Copies files to given path */
    public void copyTo(Set<WebsiteFilePath> files, WebsiteFilePath uploadPath) {

    }

    /** Moves files to given upload path */
    public void moveTo(Set<WebsiteFilePath> files, WebsiteFilePath uploadPath) {

    }

    /** Creates new directory */
    public void createDirectory(WebsiteFilePath uploadPath) {

    }

    /** Deletes directory and all files in it */
    public void deleteDirectory(WebsiteFilePath uploadPath) {

    }

    private Path path(WebsiteFilePath uploadPath) {
        return Paths.get(websiteConfig.getUploadFolder(), uploadPath.toString());
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
