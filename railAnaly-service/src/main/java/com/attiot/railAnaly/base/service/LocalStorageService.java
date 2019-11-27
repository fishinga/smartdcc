package com.attiot.railAnaly.base.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Log4j
public class LocalStorageService {

    @Value("${upload.main.dir}")
    private String LOCAL_STORAGE_DIR;

    protected ResourceLoader resourceLoader = new FileSystemResourceLoader();

    public long checkResource(Object... coordinates) throws IOException {
        Resource resource = resourceLoader.getResource(getResourceLocation(coordinates));
        return resource.exists() ? resource.lastModified() : 0;
    }

    public InputStream loadResource(Object... coordinates) throws IOException {
        Resource resource = resourceLoader.getResource(getResourceLocation(coordinates));
        if (!resource.exists()) {
            createResource(resource);
        }
        return resource.getInputStream();
    }


    public Resource loadResourceObj(Object... coordinates) throws IOException {
        Resource resource = resourceLoader.getResource(getResourceLocation(coordinates));
        if (!resource.exists()) {
            createResource(resource);
        }
        return resource;
    }

    private int[] parseStringSize(String size) {
        int[] wh = new int[2];
        String[] parts = StringUtils.split((String) size, "x");
        wh[0] = Integer.parseInt(parts[0]);
        wh[1] = Integer.parseInt(parts[1]);
        return wh;
    }

    public long persistResource(InputStream inputStream, Object... coordinates) throws IOException {
        boolean append = false;
        String path = getResourceLocation(coordinates);
        Resource resource = resourceLoader.getResource(getResourceLocation(coordinates));
        if (!resource.exists()) {
            createResource(resource);
        } else {
            append = true;
        }
        FileOutputStream outputStream =new FileOutputStream(resource.getFile(), append);
        FileCopyUtils.copy(inputStream, outputStream);
        outputStream.close();
        return resource.lastModified();
    }

    public void removeSizedResource(Object size, Object... coordinates) throws IOException {
        Resource sizedResource = resourceLoader.getResource(getSizedResourceLocation(size, coordinates));
        if (sizedResource.exists()) {
            boolean isDelete = sizedResource.getFile().delete();
        }
    }

    public void cleanSizedResource(Object... coordinates) throws IOException {
        Resource resource = resourceLoader.getResource(getResourceLocation(coordinates));
        String fileName = resource.getFilename();
        File fileDir = resource.getFile().getParentFile();
        for (File file : fileDir.listFiles()) {
            if (file.getName().startsWith(fileName + "_")) {
                boolean isDelete = file.delete();
            }
        }
    }

    public long checkSizedResource(Object size, Object... coordinates) throws IOException {
        Resource sizedResource = resourceLoader.getResource(getSizedResourceLocation(size, coordinates));
        return sizedResource.exists() ? sizedResource.lastModified() : 0;
    }

    private void createResource(Resource resource) throws IOException {
        File file = resource.getFile();
        boolean isMkdirs = file.getParentFile().mkdirs();
        boolean isCreateNew = file.createNewFile();
    }

    private String getSizedResourceLocation(Object size, Object... coordinates) {
        String resourceLocation = getResourceLocation(coordinates);
        int pos = resourceLocation.lastIndexOf('.');
        return resourceLocation.substring(0, pos) + "_" + size + resourceLocation.substring(pos);
    }

    private String getResourceLocation(Object... coordinates) {
        StringBuilder pathBuilder = new StringBuilder(LOCAL_STORAGE_DIR);
        log.info("class:LocalStorageService中getResourceLocation方法得到根路径：" + pathBuilder.toString());
        for (Object coordinate : coordinates) {
            pathBuilder.append(File.separator).append(coordinate);
        }
        log.info("class:LocalStorageService中getResourceLocation方法得到文件绝对路径：" + pathBuilder.toString());
        return pathBuilder.toString();
    }
}
