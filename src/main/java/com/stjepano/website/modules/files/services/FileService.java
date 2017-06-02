package com.stjepano.website.modules.files.services;

import com.stjepano.website.components.WebsiteConfig;
import com.stjepano.website.modules.files.model.WebsiteFile;
import com.stjepano.website.modules.files.model.WebsiteFilePath;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
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
    public void upload(MultipartFile file, WebsiteFilePath websiteFilePath) {


    }

    /** List all files in given directory */
    public List<WebsiteFile> list(WebsiteFilePath websiteFilePath) {
        if (!websiteFilePath.isDirectory()) {
            return Collections.emptyList();
        }

        Path realDir = realPath(websiteFilePath);

        try {
            return Files.list(realDir)
                    .map(realFile -> WebsiteFile.create(websiteFilePath.resolveFile(realFile.getFileName().toString()), realFile))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Could not list content of directory '" + realDir.toString() + "'!");
        }
    }

    /** Returns file info for give websiteFilePath, if file exists */
    public Optional<WebsiteFile> get(WebsiteFilePath websiteFilePath) {
        Path realPath = realPath(websiteFilePath);
        if (!Files.exists(realPath)) {
            return Optional.empty();
        }

        return Optional.of(WebsiteFile.create(websiteFilePath, realPath));
    }

    /** Returns resource for given file if file exista */
    public Optional<Resource> getAsResource(WebsiteFilePath websiteFilePath) {
        Path path = realPath(websiteFilePath);
        if (!Files.exists(path)) {
            return Optional.empty();
        }
        return Optional.of(resourceLoader.getResource("file:" + path.toAbsolutePath().toString()));
    }

    /** Deletes specified files */
    public void delete(Set<WebsiteFilePath> files) {
        final Set<Path> paths = files.stream().map(f -> realPath(f)).collect(Collectors.toSet());
        paths.forEach(p -> {
            if (!Files.exists(p)) return;
            if (Files.isDirectory(p)) {
                // delete directory and all files recursively
                try {
                    FileUtils.deleteDirectory(p.toFile());
                } catch (Exception e) {
                    throw new RuntimeException("Could not delete directory '" + p.toString() + "'!", e);
                }
            } else {
                try {
                    Files.deleteIfExists(p);
                } catch (Exception e) {
                    throw new RuntimeException("Could not delete file '"+ p.toString() +"'", e);
                }
            }
        });

    }

    /** Copies files to given path */
    public void copyTo(Set<WebsiteFilePath> files, WebsiteFilePath uploadPath) {
        final Path destDir = realPath(uploadPath);
        if (!Files.exists(destDir)) {
            throw new IllegalArgumentException("Directory '" + uploadPath.toString() + "' does not exist!");
        }
        final Set<Path> paths = files.stream().map(f -> realPath(f)).collect(Collectors.toSet());
        paths.forEach(p -> {
            if (!Files.exists(p)) return;
            if (Files.isDirectory(p)) {
                try {
                    FileUtils.copyDirectory(p.toFile(), new File(destDir.toFile(), p.getFileName().toString()));
                } catch (Exception e) {
                    throw new RuntimeException("Could not copy file '" + p.toString() + "' to directory '" + destDir.toString() + "'!", e);
                }
            } else {
                try {
                    FileUtils.copyFile(p.toFile(), new File(destDir.toFile(), p.getFileName().toString()));
                } catch (Exception e) {
                    throw new RuntimeException("Could not copy file '" + p.toString() + "' to directory '" + destDir.toString() + "'!", e);
                }
            }
        });
    }

    /** Moves files to given upload path */
    public void moveTo(Set<WebsiteFilePath> files, WebsiteFilePath uploadPath) {
        final Path destDir = realPath(uploadPath);
        if (!Files.exists(destDir)) {
            throw new IllegalArgumentException("Directory '" + uploadPath.toString() + "' does not exist!");
        }
        final Set<Path> paths = files.stream().map(f -> realPath(f)).collect(Collectors.toSet());
        paths.forEach(p -> {
            if (!Files.exists(p)) return;
            if (Files.isDirectory(p)) {
                try {
                    FileUtils.moveDirectory(p.toFile(), new File(destDir.toFile(), p.getFileName().toString()));
                } catch (Exception e) {
                    throw new RuntimeException("Could not copy file '" + p.toString() + "' to directory '" + destDir.toString() + "'!", e);
                }
            } else {
                try {
                    FileUtils.moveFile(p.toFile(), new File(destDir.toFile(), p.getFileName().toString()));
                } catch (Exception e) {
                    throw new RuntimeException("Could not copy file '" + p.toString() + "' to directory '" + destDir.toString() + "'!", e);
                }
            }
        });
    }

    /** Creates new directory */
    public void createDirectory(WebsiteFilePath uploadPath) {
        if (!uploadPath.isDirectory()) {
            throw new IllegalArgumentException("Upload path '" + uploadPath.toString() + "' is not representing a directory!");
        }
        final Path realPath = realPath(uploadPath);
        if (Files.exists(realPath)) {
            throw new IllegalArgumentException(uploadPath.toString() + " already exist!");
        }

        try {
            Files.createDirectory(realPath);
        } catch (Exception e) {
            throw new RuntimeException("Could not create directory '" + realPath.toString() + "'!", e);
        }
    }

    /** Deletes directory and all files in it */
    public void deleteDirectory(WebsiteFilePath uploadPath) {
        if (!uploadPath.isDirectory()) {
            throw new IllegalArgumentException("Upload path '" + uploadPath.toString() + "' is not representing a directory!");
        }
        final Path realPath = realPath(uploadPath);
        if (!Files.exists(realPath)) {
            throw new IllegalArgumentException(uploadPath.toString() + " does not exist!");
        }
        if (!Files.isDirectory(realPath)) {
            throw new IllegalArgumentException(uploadPath.toString() + " is not a directory!");
        }

        try {
            FileUtils.deleteDirectory(realPath.toFile());
        } catch (Exception e) {
            throw new RuntimeException("Could not delete directory '" + realPath.toString() + "'!", e);
        }
    }

    private Path realPath(WebsiteFilePath websiteFilePath) {
        String websiteFilePathStr = websiteFilePath.toString();
        if (websiteFilePathStr.startsWith("/")) {
            websiteFilePathStr = "." + websiteFilePathStr;
        }
        return Paths.get(websiteConfig.getUploadFolder(), websiteFilePathStr).normalize();
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
